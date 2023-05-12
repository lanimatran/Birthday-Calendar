package com.lanimatran.birthdaycalendar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lanimatran.birthdaycalendar.classes.User;
import com.lanimatran.birthdaycalendar.classes.DTOs.UserDTO;
import com.lanimatran.birthdaycalendar.error_handler.exceptions.UnauthorizedException;
import com.lanimatran.birthdaycalendar.error_handler.exceptions.UserAlreadyExistsException;
import com.lanimatran.birthdaycalendar.jwt.JwtTokenUtil;
import com.lanimatran.birthdaycalendar.repositories.UserRepository;
import com.lanimatran.birthdaycalendar.utility.Utility;

import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {	
	private UserRepository userRepository;	
	@Autowired  
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private JwtTokenUtil jwtTokenUtil;
	
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenUtil jwtTokenUtil) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	@Transactional
	public User createUser(User user) {
		if (userRepository.findByEmail(user.getEmail()) != null)	throw new UserAlreadyExistsException();
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setCreatedDate(Utility.getCurrentDateTime());
		user.setModifiedDate(Utility.getCurrentDateTime());
		
		User created = userRepository.save(user);
		
		return created;
	}
	
	public boolean isEmailAvailable(String email) {
		System.out.println(email);
		if (userRepository.findByEmail(email) == null)	return true;
		System.out.println("Hello World");
		return false;
	}
	
	public boolean isUsernameAvailable(String username) {
		if (userRepository.findByUsername(username) == null)	return true;
		return false;
	}
	
	/**
	 * This is a service method being called by GET /users/{idOrUsername}
	 * @param header Authorization header passed in
	 * @param idOrUsername This is either long ID or String username
	 * @return User object with Id or username requested
	 * @throws UnauthorizedException if requests fail and will be handled by Exception handler
	 */
	public User getUserByIDOrUsername(String header, String idOrUsername) {
		try {
			long userID = Long.parseLong(idOrUsername.trim());
			//if parsed successfully, ID was user ID
			User userRequested = getAuthorizedUserByID(header, userID);
			if (userRequested != null)	return userRequested;
			else throw new UnauthorizedException();
		}
		catch (NumberFormatException nfe) {
			//else, ID was username
			String username = idOrUsername;
			User userRequested = getAuthorizedUserByUsername(header, username);
			if (userRequested != null)	return userRequested;
			else throw new UnauthorizedException();			
	    }
	}
	
	public void updateUser(String header, UserDTO userDTO) {
		User user = getAuthorizedUserByID(header, userDTO.getID());
		if (user != null) {
			user.updateFromDTO(userDTO);
			userRepository.save(user);
		}
		else {
			throw new UnauthorizedException();
		}
	}
	
	/**
	 * This is a public helper method. Avoid using this function for general purposes since this bypass all authorization and validation
	 * @param ID
	 * @return User object with ID requested
	 */
	public User getUserByID(Long ID) {
		return userRepository.findByID(ID);
	}
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	/**
	 * This helper method return an User object with corresponding user ID if Authorization header is valid
	 * @param header This is the value of Authorization header to validate
	 * @param ID This is ID of the user being requested
	 * @return valid User object if header and ID are valid, else null
	 */
	private User getAuthorizedUserByID(String header, long ID) {
		long userIDFromToken = jwtTokenUtil.extractIDFromHeader(header);
		User user = getUserByID(userIDFromToken);
		if (user != null &&  user.getID().longValue() == ID)	return user;
		return null;
	}
	
	/**
	 * This helper method return an User object with corresponding username if Authorization header is valid
	 * @param header This is the value of Authorization header to validate
	 * @param username This is username of the user being requested
	 * @return valid User object if header and username are valid, else null
	 */
	private User getAuthorizedUserByUsername(String header, String username) {
		long userIDFromToken = jwtTokenUtil.extractIDFromHeader(header);
		User user = getUserByID(userIDFromToken);
		if (user != null &&  user.getUsername().equals(username))	return user;
		return null;
	}

	/** 
	 * This is NOT a helper method. This method is required to satisfy UserDetailsService interface and will be used by AuthenticationManager.
	 * Do NOT call or modify this function unless you know exactly what you are doing.
	 */
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

	
}
