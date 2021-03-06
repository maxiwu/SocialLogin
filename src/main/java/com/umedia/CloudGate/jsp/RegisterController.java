package com.umedia.CloudGate.jsp;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import com.umedia.CloudGate.model.RegistrationForm;
import com.umedia.CloudGate.model.SocialMediaProviders;
import com.umedia.CloudGate.model.SocialProfile;
import com.umedia.CloudGate.model.User;
import com.umedia.CloudGate.service.DuplicateEmailException;
import com.umedia.CloudGate.service.IUserService;
import com.umedia.CloudGate.util.SecurityUtil;

@Controller
@RequestMapping(value = "/user")
@SessionAttributes("social")
public class RegisterController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//this object should be responsible for jdbc access!
	@Autowired
	JdbcUserDetailsManager jdbcUserDetailsManager;
	@Autowired
	ProviderSignInUtils providerSignInUtils;
	
	//@Autowired
	private IUserService service;

    @Autowired
    public RegisterController(IUserService service) {
        this.service = service;
    }

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(WebRequest request, Model model) {
		// if login with social account, save profile to model
		Connection<?> connection = providerSignInUtils
				.getConnectionFromSession(request);
		if (connection != null) {
			RegistrationForm registration = createRegistrationDTO(connection);			
			model.addAttribute("social", registration);
			SocialProfile profile = createSocialProfileDTO(connection);
			model.addAttribute("connection", profile);			
		}
		else
		{
			//hanle no social information
			model.addAttribute("social", new RegistrationForm());
		}

		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String createUser(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@ModelAttribute("social") RegistrationForm social,
			WebRequest request) {

		// if user register with social, need to setup a connection in database
		/*if (social != null) {
			System.out.println(social.toString());
		} else {
			System.out.println("create local user");
		}*/
		
		//this should add password and generate unique user Id
		social.setPassword(password);
		social.setUsername(username);
		User newRegUser = createUserAccount(social);
		
		if (newRegUser == null) {
			//email is not unique, already have one in database
            return "user/register";
        }
		//login with the new registrated account
		SecurityUtil.logInUser(newRegUser);
		
		com.umedia.CloudGate.model.UserProfile profile = service.getProfileByUsername(username);
		
		//postsignup ID! is the ID for connection
		if(profile != null)
		{
		providerSignInUtils.doPostSignUp(profile.getUserId(), request);
		}
 
        return "home";
		
	}

	// DTO = data transfer object?
	private RegistrationForm createRegistrationDTO(Connection<?> connection) {
		RegistrationForm dto = new RegistrationForm();

		if (connection != null) {
			UserProfile socialMediaProfile = connection.fetchUserProfile();
			dto.setEmail(socialMediaProfile.getEmail());
			dto.setFirstName(socialMediaProfile.getFirstName());
			dto.setLastName(socialMediaProfile.getLastName());

			ConnectionKey providerKey = connection.getKey();
			dto.setSignInProvider(SocialMediaProviders.valueOf(providerKey
					.getProviderId().toUpperCase()));
		}

		return dto;
	}
	
	private SocialProfile createSocialProfileDTO(Connection<?> connection) {
		SocialProfile dto = new SocialProfile();

		if (connection != null) {			
			dto.setDisplayName(connection.getDisplayName());
			dto.setImageUrl(connection.getImageUrl());
			dto.setProfileUrl(connection.getProfileUrl());
		}

		return dto;
	}
	
	 private User createUserAccount(RegistrationForm userAccountData) {
	        User registered = null;
	 
	        try {
	            registered = service.registerNewUserAccount(userAccountData);
	        }
	        catch (DuplicateEmailException ex) {
	            /*addFieldError(
	                    "user",
	                    "email",
	                    userAccountData.getEmail(),
	                    "NotExist.user.email",
	                    result);*/
	        }
	 
	        return registered;
	    }
}
