package it.course.exam.myfilmc3ANDREA.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import it.course.exam.myfilmc3ANDREA.payload.response.FilmResponse;
import it.course.exam.myfilmc3ANDREA.repository.FilmRepository;

@Service
public class FilmService {
	
	@Autowired
	FilmRepository filmRepository;
	
	public List<FilmResponse> pagedFilmResponseOfAllFilms(int pagNo, int pagSize, String direction, String sortBy) {
		
		Pageable pageable = PageRequest.of(pagNo, pagSize, Sort.by(Direction.valueOf(direction.toUpperCase()), sortBy));
		
		Page<FilmResponse> pagedResult = filmRepository.jpqlFindAllFilmsOrderByTitleAscPaged(pageable);
		
		if (pagedResult.hasContent()) 
			return pagedResult.getContent();
		
		return new ArrayList<FilmResponse>();
		
	}

}
