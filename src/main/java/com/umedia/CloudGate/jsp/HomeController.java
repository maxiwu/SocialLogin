package com.umedia.CloudGate.jsp;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

	// redirect to login page
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome() {
		return "home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		
		return "login";
	}
	
	// redirect to login page
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginok() {
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

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String createUser(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			HttpServletRequest request) {

		// SaltSource saltSource = null;
		// Object salt = null;

		jdbcUserDetailsManager.deleteUser("name");

		// empty grant
		Collection<GrantedAuthority> allowedOperations = new ArrayList<GrantedAuthority>();
		allowedOperations.add(new SimpleGrantedAuthority("ROLE_USER"));

		// User 'name' has no authorities and will be treated as 'not found'

		// enabled, account not expired, credential non expired, account non
		// locked, collection of authorities
		UserDetails user = new User(username, password, true, true, true, true,
				allowedOperations);

		// calculate what hashedPassword would be in this configuration
		String hashedpassword = passwordEncoder.encode(user.getPassword());
		UserDetails puser = new User(username, hashedpassword, true, true,
				true, true, allowedOperations);

		jdbcUserDetailsManager.createUser(puser);

		return "login";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "login";
	}

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
