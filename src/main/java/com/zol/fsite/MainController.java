package com.zol.fsite;

import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cloudinary.utils.ObjectUtils;
import com.zol.fsite.model.Friend;
import com.zol.fsite.model.Role;
import com.zol.fsite.model.User;
import com.zol.fsite.repository.RoleRepository;
import com.zol.fsite.repository.UserRepository;
import com.zol.fsite.service.CloudinaryConfig;

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
	public String addroom(Model model) {
		model.addAttribute("aFriend", new Friend());

		return "addfriend";
	}

	@GetMapping("/displayfriend")
	public String displayFriend(Model model) {
		model.addAttribute("friends", getCurrentUser().getFriends());
		return "displayfriend";
	}

	@GetMapping("/register")
	public ModelAndView registerUser(ModelAndView modelAndView, User user) {
		modelAndView.addObject("newUser", user);
		modelAndView.setViewName("register");
		return modelAndView;

	}

	@PostMapping("/register")
	public ModelAndView addNewUser(@ModelAttribute("newUser") User newUser, BindingResult result, Model model, ModelAndView modelAndView) {
		User userExists = userRepository.findByEmail(newUser.getEmail());
		if (userExists != null) {
			modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
			modelAndView.setViewName("register");
			result.reject("email");
		}
		else
		{
			 userExists = userRepository.findByUserName(newUser.getUserName());
				if (userExists != null) {
					modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a user registered with the user Name provided.");
					modelAndView.setViewName("register");
					result.reject("userName");
				}
		}


		if (result.hasErrors()) {
			modelAndView.setViewName("register");
		} else {
			// Create a new ordinary user
			modelAndView.addObject(newUser.getUserName() + " created");
			Role r = roleRepository.findByRole("USER");
			newUser.addRole(r);
			userRepository.save(newUser);
			modelAndView.setViewName("index");
		}
		return modelAndView;
	}

	@RequestMapping("/savefriend")
	public String savePet(@Valid @ModelAttribute("aFriend") Friend friend, BindingResult result, Model model,
			@RequestParam("file") MultipartFile file) {
		System.out.println(result.toString());

		if (file.isEmpty()) {
			return "redirect:/addfriend";
		}
		User user = getCurrentUser();
		try {
			Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
			friend.setUrlImage(uploadResult.get("url").toString());
			user.getFriends().add(friend);
			userRepository.save(user);

		} catch (IOException e) {
			e.printStackTrace();
			return "redirect:/addfriend";
		}

		// model.addAttribute("friends",friendRepositroy.findAllByFilledByOrderByRankOfFriend(friend.getFilledBy()));
		model.addAttribute("friends", user.getFriends());

		return "displayfriend";
	}

	private User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		User user = userRepository.findUserClassByUserName(userName);
		return user;
	}

}
