package com.umedia.CloudGate.jsp;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/")
public class HomeController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	JdbcUserDetailsManager jdbcUserDetailsManager;
	/*@Autowired
	SocialControllerUtil util;*/

	// redirect to login page
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage() {
		
		return "login";
	}
	
	// redirect to login page
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String authenticateLogin() {
		// is authenticated?
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		
		if (auth != null) {
			 Boolean val2 = auth.isAuthenticated();
			String name = auth.getName();
			System.out.printf("%s is login", name);
		}
		return "home";
	}

	 /*@RequestMapping("/login")
	    public String login(HttpServletRequest request, Principal currentUser, Model model) {
	        //util.setModel(request, currentUser, model);
	        return "login";
	    }*/
	 
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		// is authenticated?
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			// Boolean val2 = auth.isAuthenticated();
			String name = auth.getName();
			System.out.printf("%s is login", name);
		}
		return "home";
	}

	/*

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "login";
	}*/

	@RequestMapping(value = "/addrole/{role}")
	public String addRole(@PathVariable(value = "role") String role) {
		// add a new role to existing and login user
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		user = jdbcUserDetailsManager.loadUserByUsername(user.getUsername());
		Collection<GrantedAuthority> allowedOperations = new ArrayList<GrantedAuthority>();
		for (GrantedAuthority element : user.getAuthorities()) {
			allowedOperations.add(element);
		}
		allowedOperations.add(new SimpleGrantedAuthority(role));
		UserDetails nuser = new User(user.getUsername(), user.getPassword(),
				user.isEnabled(), user.isAccountNonExpired(),
				user.isCredentialsNonExpired(), user.isAccountNonLocked(),
				allowedOperations);
		jdbcUserDetailsManager.updateUser(nuser);
		// (GrantedAuthority)new SimpleGrantedAuthority(role)

		// jdbcUserDetailsManager.createGroup(role, new
		// SimpleGrantedAuthority("ROLE_"+role));
		// jdbcUserDetailsManager.add(user.getUsername(), role);

		return "home";
	}
}
