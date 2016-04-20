package com.umedia.CloudGate.jsp;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/social")
public class SocialController {

	@RequestMapping(value = "/")
	public String isRegistered(Model model) {
		//user go here after social login
		
		//if there is a local user found, retrieve and redirect to home
		//else redirect to register with social page
		return "register";
	}
}
