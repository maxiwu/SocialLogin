package com.umedia.CloudGate.model;

/*import javax.persistence.Column;
 import javax.persistence.EnumType;
 import javax.persistence.Enumerated;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;*/

import org.apache.commons.lang3.builder.ToStringBuilder;

public class User {

	// @Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// @Column(name = "email", length = 100, nullable = false, unique = true)
	private String email;

	// @Column(name = "first_name", length = 100,nullable = false)
	private String firstName;

	// @Column(name = "last_name", length = 100, nullable = false)
	private String lastName;

	// @Column(name = "password", length = 255)
	private String password;
	
	private Boolean isSocial;

	// @Enumerated(EnumType.STRING)
	// @Column(name = "role", length = 20, nullable = false)
	private Role role;

	// @Enumerated(EnumType.STRING)
	// @Column(name = "sign_in_provider", length = 20)
	private SocialMediaProviders signInProvider;

	private String username;

	public User() {

	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public Long getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public Role getRole() {
		return role;
	}

	public String getName() {

		// Ok, lets try with first and last name...
		String name = firstName;

		if (lastName != null) {
			if (name == null) {
				name = lastName;
			} else {
				name += " " + lastName;
			}
		}

		// Try with email if still null
		if (name == null) {
			name = email;
		}

		// If still null set name to UNKNOWN
		if (name == null) {
			name = "UNKNOWN";
		}
		return name;

	}

	public SocialMediaProviders getSignInProvider() {
		return signInProvider;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id)
				// .append("creationTime", this.getCreationTime())
				.append("email", email).append("firstName", firstName)
				.append("lastName", lastName)
				// .append("modificationTime", this.getModificationTime())
				.append("signInProvider", this.getSignInProvider())
				// .append("version", this.getVersion())
				.toString();
	}

	public Boolean getIsSocial() {
		return isSocial;
	}

	public void setIsSocial(Boolean isSocial) {
		this.isSocial = isSocial;
	}

	public static class Builder {

		private User user;

		public Builder() {
			user = new User();
			user.role = Role.ROLE_USER;
		}
		
		public Builder username(String username)
		{
			user.username = username;
			return this;
		}

		public Builder email(String email) {
			user.email = email;
			return this;
		}

		public Builder firstName(String firstName) {
			user.firstName = firstName;
			return this;
		}

		public Builder lastName(String lastName) {
			user.lastName = lastName;
			return this;
		}

		public Builder password(String password) {
			user.password = password;
			return this;
		}

		public Builder signInProvider(SocialMediaProviders signInProvider) {
			user.signInProvider = signInProvider;
			return this;
		}

		public User build() {
			if(user.signInProvider != null)
			{user.setIsSocial(true);}
			else
			{user.setIsSocial(false);}
			return user;
		}
	}
}