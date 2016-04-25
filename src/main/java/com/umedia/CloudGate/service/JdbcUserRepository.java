package com.umedia.CloudGate.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.umedia.CloudGate.model.User;
import com.umedia.CloudGate.model.UserConnection;
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
				user.getUsername(), user.getRole().toString());
		// save profile
		// this userId is for linking with connection

		if (user.getIsSocial()) {
			String uuid = UUID.randomUUID().toString();

			jdbcTemplate
					.update("INSERT into userProfile(userId, email, firstName, lastName, name, username, socialProvider) values(?,?,?,?,?,?,?)",
							uuid, user.getEmail(), user.getFirstName(), user
									.getLastName(), user.getName(), user
									.getUsername(), user.getSignInProvider()
									.toString());
		}

	}

	public UserProfile getUserProfileByUsername(String username) {

		/*
		 * return jdbcTemplate.queryForObject(
		 * "select * from UserProfile where username = ?", new
		 * RowMapper<UserProfile>() { public UserProfile mapRow(ResultSet rs,
		 * int rowNum) throws SQLException { return new
		 * UserProfile(rs.getString("userId"), rs .getString("name"),
		 * rs.getString("firstName"), rs.getString("lastName"),
		 * rs.getString("email"), username); } }, username);
		 */
		final String q = username;
		String qstr = "select * from UserProfile where username = ?";
		List<UserProfile> profile = jdbcTemplate.query(qstr,
				new RowMapper<UserProfile>() {
					public UserProfile mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new UserProfile(rs.getString("userId"), rs
								.getString("name"), rs.getString("firstName"),
								rs.getString("lastName"),
								rs.getString("email"), q);
					}
				}, q);
		if (profile.isEmpty()) {
			return null;
		} else if (profile.size() == 1) { // list contains exactly 1 element
			return profile.get(0);
		} else { // list contains more than 1 elements
			// your wish, you can either throw the exception or return 1st
			// element.
			throw new IncorrectResultSizeDataAccessException(qstr, 1,
					profile.size());
		}
	}

	public UserProfile getUserProfileByUserId(String userId) {

		final String id = userId;
		String qstr = "select * from UserProfile where userId = ?";
		List<UserProfile> profile = jdbcTemplate.query(qstr,
				new RowMapper<UserProfile>() {
					public UserProfile mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new UserProfile(id, rs.getString("name"), rs
								.getString("firstName"), rs
								.getString("lastName"), rs.getString("email"),
								rs.getString("username"));
					}
				}, id);
		if (profile.isEmpty()) {
			return null;
		} else if (profile.size() == 1) { // list contains exactly 1 element
			return profile.get(0);
		} else { // list contains more than 1 elements
			// your wish, you can either throw the exception or return 1st
			// element.
			throw new IncorrectResultSizeDataAccessException(qstr, 1,
					profile.size());
		}
	}

	public UserConnection getConnectionByUsername(String username) {
		// join table!
		String qstr = "SELECT * FROM users a INNER JOIN userprofile b ON a.username = b.username INNER JOIN userconnection c ON b.userId = c.userId where a.username = ?";

		List<UserConnection> uc = jdbcTemplate.query(qstr,
				new RowMapper<UserConnection>() {
					public UserConnection mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new UserConnection(rs.getString("c.userId"), rs
								.getString("c.providerId"), rs
								.getString("c.providerUserId"), rs
								.getInt("c.rank"), rs
								.getString("c.displayName"), rs
								.getString("c.profileUrl"), rs
								.getString("c.imageUrl"), rs
								.getString("c.accessToken"), rs
								.getString("c.secret"), rs
								.getString("c.refreshToken"), rs
								.getLong("c.expireTime"));
					}
				}, username);
		if (uc.isEmpty()) {
			return null;
		} else if (uc.size() == 1) { // list contains exactly 1 element
			return uc.get(0);
		} else { // list contains more than 1 elements
			// your wish, you can either throw the exception or return 1st
			// element.
			throw new IncorrectResultSizeDataAccessException(qstr, 1, uc.size());
		}
		// return null;
	}
}
