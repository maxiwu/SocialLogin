package com.umedia.CloudGate.service;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.umedia.CloudGate.model.User;
import com.umedia.CloudGate.model.UserProfile;

/*public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
}*/

public interface IUserRepository {

    public User findByEmail(String email);
    
    public void createUser(User user);
    
    public UserProfile getUserProfileByUsername(String username);
    public UserProfile getUserProfileByUserId(String userId);
}