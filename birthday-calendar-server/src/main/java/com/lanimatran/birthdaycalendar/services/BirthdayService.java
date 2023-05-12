package com.lanimatran.birthdaycalendar.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lanimatran.birthdaycalendar.classes.Birthday;
import com.lanimatran.birthdaycalendar.classes.User;
import com.lanimatran.birthdaycalendar.classes.DTOs.BirthdayDTO;
import com.lanimatran.birthdaycalendar.error_handler.exceptions.ResourceNotFoundException;
import com.lanimatran.birthdaycalendar.error_handler.exceptions.UnauthorizedException;
import com.lanimatran.birthdaycalendar.jwt.JwtTokenUtil;
import com.lanimatran.birthdaycalendar.repositories.BirthdayRepository;

import jakarta.transaction.Transactional;

@Service
public class BirthdayService {

	
	private BirthdayRepository birthdayRepository;
	private JwtTokenUtil jwtTokenUtil;
	
	
	public BirthdayService(BirthdayRepository birthdayRepository, JwtTokenUtil jwtTokenUtil) {
		super();
		this.birthdayRepository = birthdayRepository;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	public List<Birthday> getBirthdayList(String authorizationHeader) {
		long userID = jwtTokenUtil.extractIDFromHeader(authorizationHeader);
		return birthdayRepository.findByUserID(userID);
	}
	
	public Birthday getBirthdayByID(long ID, String authorizationHeader) {		
		return getBirthdayByIDIfAuthorized(ID, authorizationHeader);
	}
	
	public void deleteBirthdayByID(long ID, String authorizationHeader) {		
		getBirthdayByIDIfAuthorized(ID, authorizationHeader);
		birthdayRepository.deleteById(ID);
	}

	@Transactional
	public Birthday createBirthday(Birthday bd, String header) {
		long userID = jwtTokenUtil.extractIDFromHeader(header);
		if (bd.getID() != null)	bd.setID(null);
		bd.setUser(new User(userID));
		Birthday created = birthdayRepository.save(bd);
		return created;
	}
	
	public void updateBirthday(long ID, BirthdayDTO bdDTO, String authorizationHeader) {
		Birthday authorized = getBirthdayByIDIfAuthorized(ID, authorizationHeader);
		authorized.updateFromDTO(bdDTO);
		authorized.setProcessed(false);
		birthdayRepository.save(authorized);
	}

	
	private Birthday getBirthdayByIDIfAuthorized(long birthdayID, String authorizationHeader) {
		Birthday birthday = birthdayRepository.findByID(birthdayID);
		if (birthday == null)	throw new ResourceNotFoundException();
		long userID = jwtTokenUtil.extractIDFromHeader(authorizationHeader);
		if (birthday.getUser().getID() != userID)	throw new UnauthorizedException();
		return birthday;
	}
}
