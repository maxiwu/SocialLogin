package com.umedia.CloudGate.service;


import org.springframework.social.connect.DuplicateConnectionException;

import com.umedia.CloudGate.model.RegistrationForm;
import com.umedia.CloudGate.model.User;
import com.umedia.CloudGate.model.UserConnection;
import com.umedia.CloudGate.model.UserProfile;

public interface IUserService {

	/**
	 * Creates a new user account to the service.
	 * 
	 * @param userAccountData
	 *            The information of the created user account.
	 * @return The information of the created user account.
	 * @throws DuplicateEmailException
	 *             Thrown when the email address is found from the database.
	 */
	public User registerNewUserAccount(RegistrationForm userAccountData)
			throws DuplicateEmailException;
	
	public UserProfile getProfileByUsername(String username);
	public UserConnection getConnectionByUsername(String username);
}
