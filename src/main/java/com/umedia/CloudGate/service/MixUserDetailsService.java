package com.umedia.CloudGate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

import com.umedia.CloudGate.model.UserProfile;

public class MixUserDetailsService implements SocialUserDetailsService {

	@Autowired
	private IUserRepository userRepository;
	private UserDetailsService userDetailsService;

    public MixUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    
	@Override
	public SocialUserDetails loadUserByUserId(String userId)
			throws UsernameNotFoundException {
		//try to load user, for local account
		UserDetails userDetails = null;
		try {
			userDetails = userDetailsService.loadUserByUsername(userId);
		} catch (UsernameNotFoundException e) {
			// try social account
			UserProfile profile = userRepository.getUserProfileByUserId(userId);
			if(profile != null)
			{
				userDetails = userDetailsService.loadUserByUsername(profile.getEmail());
			}
		}
		
		if(userDetailsService == null)
		{
			throw new UsernameNotFoundException(userId);
		}
		
		return new SocialUser(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
	}



}
