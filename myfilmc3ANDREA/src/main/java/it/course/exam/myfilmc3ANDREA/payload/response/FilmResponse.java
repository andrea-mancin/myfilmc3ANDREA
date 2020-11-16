package it.course.exam.myfilmc3ANDREA.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FilmResponse {

	private String filmId;
	
	private String title;
	
	private String description;
	
	private int releaseYear;
	
	private String languageName;
	
	private String countryName;	
	
}
