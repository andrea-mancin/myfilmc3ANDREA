package it.course.exam.myfilmc3ANDREA.payload.request;

import java.time.Year;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmRequest {
	
	@NotBlank
	@Size(min = 10, max = 10)
	private String filmId;
	
	@NotBlank
	private String description;

	@JsonFormat(pattern = "yyyy")
	@PastOrPresent
	private Year releaseYear;
	
	@NotBlank
	@Size(max = 128)
	private String title;
	
	@NotBlank
	@Size(min = 2, max = 2)
	private String countryId;
	
	@NotBlank
	@Size(min = 2, max = 2)
	private String languageId;
	
	private Set<String> actorsId;

}
