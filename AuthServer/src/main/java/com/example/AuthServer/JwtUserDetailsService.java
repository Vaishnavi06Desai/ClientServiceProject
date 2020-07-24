package com.example.AuthServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	RestClient client;

	Log log = new Log();

	@Value("${DBusername}")
	String DBUsername;

	@Value("${password}")
	String DBPassword;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Log log = new Log();
//		log.setLogString("Authenticating user...checking if user exists in database...");
//		log.setSender("Authentication Server");
//		client.Logger(log);
		if (DBUsername.equals(username)) {
//			log.setLogString("Authenticating user...User Exists. Returning user details...");
//			log.setSender("Authentication Server");
//			client.Logger(log);
			return new User(DBUsername, DBPassword,
					new ArrayList<>());
		} else {
			log.setLogString("Authenticating user..." + "\n" + "Error: User with given username not found." + username);
			log.setSender("Authentication Server");
			client.Logger(log);
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}