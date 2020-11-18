package it.course.exam.myfilmc3ANDREA.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CustomerRequest {
	
	@NotBlank
	@Email
	@Size(max = 50)
	private String email;
	
	@NotBlank
	@Size(max = 45)
	private String firstName;
	
	@NotBlank
	@Size(max = 45)
	private String lastName;

}
