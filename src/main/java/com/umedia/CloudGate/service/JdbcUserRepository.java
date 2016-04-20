package com.umedia.CloudGate.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;




import com.umedia.CloudGate.model.User;
import com.umedia.CloudGate.model.UserProfile;

@Repository
public class JdbcUserRepository implements IUserRepository {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcUserRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	public void createUser(User user) {
		// save user
		jdbcTemplate
				.update("INSERT into users(username,password,enabled) values(?,?,true)",
						user.getUsername(), user.getPassword());
		// save authorities
		jdbcTemplate.update(
				"INSERT into authorities(username,authority) values(?,?)",
				user.getUsername(), "USER");
		// save profile
		// this userId is for linking with connection

		String uuid = UUID.randomUUID().toString();

		jdbcTemplate
				.update("INSERT into userProfile(userId, email, firstName, lastName, name, username, socialProvider) values(?,?,?,?,?,?,?)",
						uuid, user.getEmail(), user.getFirstName(),
						user.getLastName(), user.getName(), user.getUsername(),
						user.getSignInProvider().toString());

	}

	public UserProfile getUserProfileByUsername(String username) {

		return jdbcTemplate.queryForObject(
				"select * from UserProfile where username = ?",
				new RowMapper<UserProfile>() {
					public UserProfile mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new UserProfile(rs.getString("userId"), rs.getString("name"), rs
								.getString("firstName"), rs
								.getString("lastName"), rs.getString("email"),
								username);
					}
				}, username);

	}
	
	public UserProfile getUserProfileByUserId(String userId) {

		return jdbcTemplate.queryForObject(
				"select * from UserProfile where userId = ?",
				new RowMapper<UserProfile>() {
					public UserProfile mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new UserProfile(userId, rs.getString("name"), rs
								.getString("firstName"), rs
								.getString("lastName"), rs.getString("email"),
								rs.getString("username"));
					}
				}, userId);

	}

}
