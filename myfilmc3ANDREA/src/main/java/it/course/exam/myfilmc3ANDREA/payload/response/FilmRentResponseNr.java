package it.course.exam.myfilmc3ANDREA.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FilmRentResponseNr {
	
	private String filmId;
	private String title;
	private Long number_of_rents;

}
