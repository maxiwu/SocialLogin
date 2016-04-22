package com.umedia.CloudGate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umedia.CloudGate.model.RegistrationForm;
import com.umedia.CloudGate.model.User;
import com.umedia.CloudGate.model.UserConnection;
import com.umedia.CloudGate.model.UserProfile;

@Service
public class RepositoryUserService implements IUserService {

	private PasswordEncoder passwordEncoder;

    private IUserRepository repository;

    @Autowired
    public RepositoryUserService(PasswordEncoder passwordEncoder, IUserRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @Transactional
    @Override
    public User registerNewUserAccount(RegistrationForm userAccountData) throws DuplicateEmailException {
        //LOGGER.debug("Registering new user account with information: {}", userAccountData);

        if (emailExist(userAccountData.getEmail())) {
            //LOGGER.debug("Email: {} exists. Throwing exception.", userAccountData.getEmail());
            throw new DuplicateEmailException("The email address: " + userAccountData.getEmail() + " is already in use.");        	
        }

        //LOGGER.debug("Email: {} does not exist. Continuing registration.", userAccountData.getEmail());

        String encodedPassword = encodePassword(userAccountData);

        User.Builder user = User.getBuilder()
                .email(userAccountData.getEmail())
                .firstName(userAccountData.getFirstName())
                .lastName(userAccountData.getLastName())
                .username(userAccountData.getUsername())
                .password(encodedPassword);

        if (userAccountData.isSocialSignIn()) {
            user.signInProvider(userAccountData.getSignInProvider());
        }

        User registered = user.build();

        //LOGGER.debug("Persisting new user with information: {}", registered);
        repository.createUser(registered);
        
        return registered;
    }

    private boolean emailExist(String email) {
        //LOGGER.debug("Checking if email {} is already found from the database.", email);

        User user = repository.findByEmail(email);

        if (user != null) {
            //LOGGER.debug("User account: {} found with email: {}. Returning true.", user, email);
            return true;
        }

        //LOGGER.debug("No user account found with email: {}. Returning false.", email);

        return false;
    }

    private String encodePassword(RegistrationForm dto) {
        String encodedPassword = null;

        /*if (dto.isNormalRegistration()) {
            //LOGGER.debug("Registration is normal registration. Encoding password.");
            encodedPassword = passwordEncoder.encode(dto.getPassword());
        }*/
        
        //include both registration type
        encodedPassword = passwordEncoder.encode(dto.getPassword());

        return encodedPassword;
    }

	@Override
	public UserProfile getProfileByUsername(String username) {
		return repository.getUserProfileByUsername(username);
		
	}
	
	@Override
	public UserConnection getConnectionByUsername(String username) {
		return repository.getConnectionByUsername(username);
		
	}

}
