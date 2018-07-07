package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CloudinaryConfig cloudc;
    
    @Autowired
    UserDetailsService userDetailsService;



    @RequestMapping("/addfriend")
    public String addroom(Model model)
    {
        model.addAttribute("aFriend", new Friend());

        return "addfriend";
    }
      @GetMapping("/displayfriend")
     public String displayFriend(Model model)
    {
    	model.addAttribute("friends",getCurrentUser().getFriends());
        return "displayfriend";
    }
      
      @GetMapping("/register")
     public String registerUser(Model model)
    {
        model.addAttribute("newUser",new User());
        return "register";
    }

    @PostMapping("/register")
    public String addNewUser(@ModelAttribute("newUser") User newUser, BindingResult result, Model model)
    {

        if(result.hasErrors())
        {
            System.out.println(result.toString());
            return "register";
        }
        else{
            //Create a new ordinary user
            model.addAttribute(newUser.getUsername()+" created");
            Role r = roleRepository.findByRole("USER");
            newUser.addRole(r);
            userRepository.save(newUser);
            return "index";
        }
    }

    @RequestMapping("/savefriend")
    public String savePet(@Valid @ModelAttribute("aFriend") Friend friend, BindingResult result, Model model, @RequestParam("file")MultipartFile file){
        System.out.println(result.toString());


        
        
        if(file.isEmpty()){
            return "redirect:/addfriend";
        }
      User user=getCurrentUser();
        try{
            Map uploadResult=cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype","auto"));
            friend.setUrlImage(uploadResult.get("url").toString());    
            user.getFriends().add(friend);
            userRepository.save(user);
            
        }catch (IOException e){
            e.printStackTrace();
            return "redirect:/addfriend";
        }

//        model.addAttribute("friends",friendRepositroy.findAllByFilledByOrderByRankOfFriend(friend.getFilledBy()));
        model.addAttribute("friends",user.getFriends());

        return "displayfriend";
    }



private User getCurrentUser()
{
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String userName=auth.getName();
    User user = userRepository.findUserClassByUsername(userName);
    return user;
}



}
