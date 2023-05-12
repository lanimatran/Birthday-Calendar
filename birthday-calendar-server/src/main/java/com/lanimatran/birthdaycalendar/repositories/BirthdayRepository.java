package com.lanimatran.birthdaycalendar.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lanimatran.birthdaycalendar.classes.Birthday;

@Repository
public interface BirthdayRepository extends JpaRepository<Birthday, Long>{
	List<Birthday> findByUserID(long userID);
	Birthday findByID(long ID);
	List<Birthday> findByIsProcessed(boolean isProcessed);
}
