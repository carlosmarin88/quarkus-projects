package org.acme.dto;

import java.util.Collections;
import java.util.Set;


public class User {

    public String username;
	public String password;
    

    public Set<Role> roles;

    public User(String userUsername, String userPassword, Set<Role> roles) {
        this.username = userUsername;
        this.password = userPassword;
        this.roles = roles;
    }
    
    public static User findByUsername(String username) {

		//if using Panache pattern (extends or PanacheEntity PanacheEntityBase)
		//return find("username", username).firstResult();


		
		String userUsername = "user";

		//generated from password encoder
		String userPassword = "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=";

		String adminUsername = "admin";

		//generated from password encoder
		String adminPassword = "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=";
		
		if (username.equals(userUsername)) {
			return new User(userUsername, userPassword, Collections.singleton(Role.USER));
		} else if (username.equals(adminUsername)) {
			return new User(adminUsername, adminPassword, Collections.singleton(Role.ADMIN));
		} else {
			return null;
		}
    }
    
}
