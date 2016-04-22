package com.umedia.CloudGate.util;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.umedia.CloudGate.model.User;


public class SecurityUtil {
	
	public static void logInUser(User user) {
        //LOGGER.info("Logging in user: {}", user);

		//why is this builder does not have username?
        ExampleUserDetails userDetails = ExampleUserDetails.getBuilder()
                .firstName(user.getFirstName())
                .id(user.getId())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .socialSignInProvider(user.getSignInProvider())                
                .build();
        //LOGGER.debug("Logging in principal: {}", userDetails);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		//Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //LOGGER.info("User: {} has been logged in.", userDetails);
    }
}
