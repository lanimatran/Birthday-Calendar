package com.lanimatran.birthdaycalendar.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lanimatran.birthdaycalendar.classes.DTOs.UserDTO;
import com.lanimatran.birthdaycalendar.classes.DTOs.CreateUserRequestDTO;
import com.lanimatran.birthdaycalendar.classes.User;
import com.lanimatran.birthdaycalendar.error_handler.exceptions.EmailNotAvailableException;
import com.lanimatran.birthdaycalendar.error_handler.exceptions.UsernameNotAvailableException;
import com.lanimatran.birthdaycalendar.services.UserService;
import com.lanimatran.birthdaycalendar.utility.ModelMapperUtil;


@RestController
@CrossOrigin(origins="http://localhost:3000")
public class UserController {
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@RequestBody CreateUserRequestDTO createUserRequest) {
		User user = ModelMapperUtil.map(createUserRequest, User.class);
		User created = userService.createUser(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getID()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	
	@GetMapping("/checkEmail")
	public ResponseEntity<Void> checkAvailableEmail(@RequestParam("email") String email) {
		if(!userService.isEmailAvailable(email))	throw new EmailNotAvailableException();
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/checkUsername")
	public ResponseEntity<Void> checkAvailableUsername(@RequestParam("username") String username) {
		if(!userService.isUsernameAvailable(username))	throw new UsernameNotAvailableException();
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/test") 
	public String test() {
		return "Hello, World!";
	}
	
	@GetMapping("/users/{idOrUsername}")
	public UserDTO getUser(@RequestHeader("Authorization") String header, @PathVariable String idOrUsername) {
		User user = userService.getUserByIDOrUsername(header, idOrUsername);
		return ModelMapperUtil.map(user, UserDTO.class);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<Void> updateUser(@RequestHeader("Authorization") String header, @PathVariable long ID, @RequestBody UserDTO userDTO) {
		userDTO.setID(ID);
		userService.updateUser(header, userDTO);
		return ResponseEntity.ok().build();
	}
}
