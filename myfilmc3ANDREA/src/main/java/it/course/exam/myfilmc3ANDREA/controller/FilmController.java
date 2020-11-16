package it.course.exam.myfilmc3ANDREA.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.course.exam.myfilmc3ANDREA.payload.request.FilmRequest;
import it.course.exam.myfilmc3ANDREA.repository.FilmRepository;

@RestController
public class FilmController {

	@Autowired
	FilmRepository filmRepository;
	
	@PostMapping("add-update-film")
	private ResponseEntity<?> addUpdateFilm(@RequestBody FilmRequest filmRequest, HttpServletRequest request) {
		
		return new ResponseEntity<Object>(request, null);
		
	}
	
}
