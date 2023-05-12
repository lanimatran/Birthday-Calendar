package com.lanimatran.birthdaycalendar;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import com.lanimatran.birthdaycalendar.error_handler.exceptions.InvalidJwtTokenException;
import com.lanimatran.birthdaycalendar.jwt.JwtTokenUtil;
import com.lanimatran.birthdaycalendar.repositories.BirthdayRepository;
import com.lanimatran.birthdaycalendar.classes.Birthday;
import com.lanimatran.birthdaycalendar.classes.DTOs.BirthdayDTO;
import com.lanimatran.birthdaycalendar.classes.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfiguration.class)
public class BirthdayControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	BirthdayRepository birthdayRepository;
	
	@MockBean
	JwtTokenUtil tokenUtil;	
	
	@Test
	void getBirthdaysShouldReturnStatusOk() throws Exception {
		List<Birthday> testBirthdayList = setUpBirthdayList();
		when(tokenUtil.extractIDFromHeader(anyString())).thenReturn((long)0);
		when(birthdayRepository.findByUserID(anyLong())).thenReturn(testBirthdayList);		
		this.mockMvc.perform(get("/birthdays").header("Authorization", "")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json("[{\"id\":0,\"firstName\":null,\"lastName\":null,\"middleName\":null,\"birthDay\":0,\"birthMonth\":0,"
					+ "\"willRemindOneDayPrior\":false,\"willRemindOneWeekPrior\":false,\"willRemindCustomTime\":false,\"customReminder\":null}]"));
	}
	
	@Test
	void getBirthdaysShouldReturnStatusUnauthorized() throws Exception {
		when(tokenUtil.extractIDFromHeader(anyString())).thenThrow(new InvalidJwtTokenException());	
		this.mockMvc.perform(get("/birthdays").header("Authorization", "")).andDo(print())
			.andExpect(status().isUnauthorized());			
	}
	
	@Test
	void getBirthdayByIDShouldReturnStatusOk() throws Exception {
		when(tokenUtil.extractIDFromHeader(anyString())).thenReturn((long)0);
		when(birthdayRepository.findByID(anyLong())).thenReturn(setUpBirthday());		
		this.mockMvc.perform(get("/birthdays/0").header("Authorization", "")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json("{\"id\":0,\"firstName\":null,\"lastName\":null,\"middleName\":null,\"birthDay\":0,\"birthMonth\":0,"
					+ "\"willRemindOneDayPrior\":false,\"willRemindOneWeekPrior\":false,\"willRemindCustomTime\":false,\"customReminder\":null}"));
	}
	
	@Test
	void getBirthdayByIDShouldReturnStatusNotFound() throws Exception {
		when(tokenUtil.extractIDFromHeader(anyString())).thenReturn((long)0);
		when(birthdayRepository.findByID(anyLong())).thenReturn(null);		
		this.mockMvc.perform(get("/birthdays/0").header("Authorization", "")).andDo(print())
			.andExpect(status().isNotFound());
	}
	
	@Test
	void getBirthdayByIDShouldReturnStatusForbidden() throws Exception {
		Birthday notMatchedTestBirthday = new Birthday();
		User testUser = new User();
		testUser.setID(new Long(1));
		notMatchedTestBirthday.setUser(testUser);
		when(tokenUtil.extractIDFromHeader(anyString())).thenReturn((long)0);
		when(birthdayRepository.findByID(anyLong())).thenReturn(notMatchedTestBirthday);	
		this.mockMvc.perform(get("/birthdays/0").header("Authorization", "")).andDo(print())
			.andExpect(status().isForbidden());
	}
	
	@Test
	void createBirthdayShouldReturnStatusCreated() throws Exception {
		when(tokenUtil.extractIDFromHeader(anyString())).thenReturn((long)0);
		when(birthdayRepository.findByID(anyLong())).thenReturn(null);
		when(birthdayRepository.save(any(Birthday.class))).thenReturn(new Birthday());
		this.mockMvc.perform(post("/birthdays").header("Authorization", "")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(new BirthdayDTO()))).andDo(print())
			.andExpect(status().isCreated());
	}
	
	@Test
	void deleteBirthdayByIDShouldReturnStatusOk() throws Exception {
		when(tokenUtil.extractIDFromHeader(anyString())).thenReturn((long)0);
		when(birthdayRepository.findByID(anyLong())).thenReturn(new Birthday());
		this.mockMvc.perform(delete("/birthdays/0").header("Authorization", "")).andExpect(status().isOk());
	}
	
	@Test
	void deleteBirthdayByIDShouldReturnStatusForbidden() throws Exception {
		when(tokenUtil.extractIDFromHeader(anyString())).thenReturn((long)123456);
		when(birthdayRepository.findByID(anyLong())).thenReturn(new Birthday());
		this.mockMvc.perform(delete("/birthdays/0").header("Authorization", "")).andExpect(status().isForbidden());
	}
	
	@Test
	void deleteBirthdayByIDShouldReturnStatusNotFound() throws Exception {
		when(tokenUtil.extractIDFromHeader(anyString())).thenReturn((long)0);
		when(birthdayRepository.findByID(anyLong())).thenReturn(null);
		this.mockMvc.perform(delete("/birthdays/0").header("Authorization", "")).andExpect(status().isNotFound());
	}
	
	@Test
	void updateBirthdayByIDShouldReturnStatusOk() throws Exception {
		when(tokenUtil.extractIDFromHeader(anyString())).thenReturn((long)0);
		when(birthdayRepository.findByID(anyLong())).thenReturn(new Birthday());
		when(birthdayRepository.save(any(Birthday.class))).thenReturn(new Birthday());
		this.mockMvc.perform(delete("/birthdays/0").header("Authorization", "")).andExpect(status().isOk());
	}
	
	@Test
	void updateBirthdayByIDShouldReturnStatusForbidden() throws Exception {
		when(tokenUtil.extractIDFromHeader(anyString())).thenReturn((long)123456);
		when(birthdayRepository.findByID(anyLong())).thenReturn(new Birthday());
		when(birthdayRepository.save(any(Birthday.class))).thenReturn(new Birthday());
		this.mockMvc.perform(delete("/birthdays/0").header("Authorization", "")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(new Birthday()))).andDo(print())
			.andExpect(status().isForbidden());
	}
	
	@Test
	void updateBirthdayByIDShouldReturnStatusNotFound() throws Exception {
		when(tokenUtil.extractIDFromHeader(anyString())).thenReturn((long)0);
		when(birthdayRepository.findByID(anyLong())).thenReturn(null);
		when(birthdayRepository.save(any(Birthday.class))).thenReturn(new Birthday());
		this.mockMvc.perform(put("/birthdays/0").header("Authorization", "")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(new Birthday()))).andDo(print())
			.andExpect(status().isNotFound());
	}
	
	private List<Birthday> setUpBirthdayList() {
		List<Birthday> list = new ArrayList<Birthday>();
		list.add(new Birthday());
		return list;
	}
	
	private Birthday setUpBirthday() {
		Birthday testBirthday =  new Birthday();
		User testUser = new User();
		testUser.setID(new Long(0));
		testBirthday.setUser(testUser);
		return testBirthday;
	}
}
