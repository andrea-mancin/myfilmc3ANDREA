package it.course.exam.myfilmc3ANDREA.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class RentalService {

	public Date fromCalendarToDateRentalToReturn() {

		int year = 9999;
		int day = 31;
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		
		return calendar.getTime();

	}

}
