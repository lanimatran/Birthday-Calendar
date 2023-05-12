package com.lanimatran.birthdaycalendar;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.lanimatran.birthdaycalendar.classes.DTOs.CreateUserRequestDTO;
import com.lanimatran.birthdaycalendar.classes.User;
import com.lanimatran.birthdaycalendar.error_handler.exceptions.InvalidJwtTokenException;
import com.lanimatran.birthdaycalendar.error_handler.exceptions.InvalidObjectException;
import com.lanimatran.birthdaycalendar.jwt.JwtTokenUtil;
import com.lanimatran.birthdaycalendar.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfiguration.class)
class UserControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserRepository userRepository;
	
	
	@MockBean
	JwtTokenUtil tokenUtil;	

	
	@Test 
	void createUserShouldReturnStatusCreated() throws Exception {
		CreateUserRequestDTO createUserRequest = new CreateUserRequestDTO("testUsername", "testEmail@test.com", "testPassword", "testFirstName", "testLastName");
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(userRepository.save(any(User.class))).thenReturn(new User());
		this.mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(createUserRequest)))
					.andExpect(status().isCreated());
	}

	@Test 
	void createUserShouldReturnStatusConflict() throws Exception {
		CreateUserRequestDTO createUserRequest = new CreateUserRequestDTO("testUsername", "testEmail@test.com", "testPassword", "testFirstName", "testLastName");
		when(userRepository.findByEmail(anyString())).thenReturn(new User());
		when(userRepository.save(any(User.class))).thenReturn(new User());
		this.mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(createUserRequest)))
					.andExpect(status().isConflict());
	}
	
	@Test 
	void createUserShouldReturnStatusBadRequest() throws Exception {		
		CreateUserRequestDTO createUserRequest = new CreateUserRequestDTO("testUsername", "testEmail@test.com", "testPassword", "testFirstName", "testLastName");
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(userRepository.save(any(User.class))).thenThrow(new InvalidObjectException());
		this.mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(createUserRequest)))
					.andExpect(status().isBadRequest());
	}
	
	
	@Test 
	void verifyRegistrationTokenShouldReturnUnauthorized() throws Exception {
		when(tokenUtil.extractIDFromToken(anyString())).thenThrow(new InvalidJwtTokenException());
		this.mockMvc.perform(get("/users/verify?token=test")).andExpect(status().isUnauthorized());
	}
	
	
	@Test 
	void verifyRegistrationTokenShouldReturnUnauthorized2() throws Exception {
		when(tokenUtil.extractIDFromToken(anyString())).thenReturn((long) 0);
		when(userRepository.findByID(anyLong())).thenReturn(new User());
		when(tokenUtil.validateToken(anyString(), any(User.class))).thenThrow(new InvalidJwtTokenException());
		this.mockMvc.perform(get("/users/verify?token=test")).andExpect(status().isUnauthorized());
	}
	
	@Test 
	void checkAvailableEmailShouldReturnOk() throws Exception {
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		this.mockMvc.perform(post("/checkEmail?email=test@test.com")).andExpect(status().isOk());
	}
	
	@Test 
	void checkAvailableEmailShouldReturnConflict() throws Exception {
		when(userRepository.findByEmail(anyString())).thenReturn(new User());
		this.mockMvc.perform(post("/checkEmail?email=test@test.com")).andExpect(status().isConflict());
	}
}
