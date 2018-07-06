package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private FriendRepositroy friendRepositroy;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CloudinaryConfig cloudc;

//    @RequestMapping("/")
//    public String homePage(Model model)
//    {
//
//        model.addAttribute("roles",roleRepository.findAll());
//
//        return "register";
//    }

    @RequestMapping("/addfriend")
    public String addroom(Model model)
    {
        model.addAttribute("aFriend", new Friend());

        return "addfriend";
    }
      @GetMapping("/")
     public String registerUser(Model model)
    {
        model.addAttribute("newUser",new User());
        return "register";
    }

    @PostMapping("/")
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
            return "redirect:/addfriend";
        }
    }

    @RequestMapping("/savefriend")
    public String savePet(@Valid @ModelAttribute("aFriend") Friend friend, BindingResult result, Model model, @RequestParam("file")MultipartFile file){
        System.out.println(result.toString());
        if(file.isEmpty()){
            return "redirect:/addfriend";
        }
        try{
            Map uploadResult=cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype","auto"));
            friend.setUrlImage(uploadResult.get("url").toString());
            friendRepositroy.save(friend);
        }catch (IOException e){
            e.printStackTrace();
            return "redirect:/addfriend";
        }

        friendRepositroy.save(friend);
//        model.addAttribute("friends",friendRepositroy.findAllByFilledByOrderByRankOfFriend(friend.getFilledBy()));
        model.addAttribute("friends",friendRepositroy.findAll());

        return "displayfriend";
    }



/*
    @RequestMapping("/detail/{id}")
    public String showJob(@PathVariable("id") long id, Model model){

        model.addAttribute("rooms", roomRipository.findById(id).get());
        return "displaydetail";


    }*/



}
