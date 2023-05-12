package com.lanimatran.birthdaycalendar.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lanimatran.birthdaycalendar.classes.Birthday;
import com.lanimatran.birthdaycalendar.classes.DTOs.BirthdayDTO;
import com.lanimatran.birthdaycalendar.services.BirthdayService;
import com.lanimatran.birthdaycalendar.utility.ModelMapperUtil;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BirthdayController {
	@Autowired
		private BirthdayService birthdayService;

	public BirthdayController(BirthdayService birthdayService) {
		super();
		this.birthdayService = birthdayService;
	}
	
	@GetMapping("/birthdays")
	public List<BirthdayDTO> getBirthdayList(@RequestHeader("Authorization") String header) {
		List<Birthday> list = birthdayService.getBirthdayList(header);
		List<BirthdayDTO> retList = ModelMapperUtil.mapAll(list, BirthdayDTO.class);
		return retList;
	}
	
	@GetMapping("/birthdays/{ID}")
	public BirthdayDTO getBirthdayByID(@RequestHeader("Authorization") String header, @PathVariable long ID) {
		Birthday birthday = birthdayService.getBirthdayByID(ID, header);
		BirthdayDTO retBirthday = ModelMapperUtil.map(birthday, BirthdayDTO.class);
		return retBirthday;
	}
	
	@PostMapping("/birthdays")
	public ResponseEntity<Void> createBirthday(@RequestHeader("Authorization") String header, @RequestBody BirthdayDTO birthdayDTO) {
		Birthday birthday = ModelMapperUtil.map(birthdayDTO, Birthday.class);
		Birthday created = birthdayService.createBirthday(birthday, header);
		if (created == null)	return ResponseEntity.status(HttpStatus.CONFLICT).build();
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getID()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/birthdays/{ID}")
	public ResponseEntity<Void> updateBirthday(@RequestHeader("Authorization") String header, @RequestBody BirthdayDTO birthdayDTO, @PathVariable long ID) {
		birthdayService.updateBirthday(ID, birthdayDTO, header);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/birthdays/{ID}")
	public ResponseEntity<Void> deleteBirthday(@RequestHeader("Authorization") String header, @PathVariable long ID) {
		birthdayService.deleteBirthdayByID(ID, header);
		return ResponseEntity.ok().build();
	}
}
