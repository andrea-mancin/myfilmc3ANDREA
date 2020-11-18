package it.course.exam.myfilmc3ANDREA.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FilmRentResponse {
	
	private String filmId;
	private String title;

}
