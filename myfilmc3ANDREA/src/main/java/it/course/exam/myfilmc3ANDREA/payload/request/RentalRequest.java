package it.course.exam.myfilmc3ANDREA.payload.request;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class RentalRequest {

	@PastOrPresent
	private Date rentalDate;
	
	@NotBlank
	@Email
	@Size(max = 50)
	private String customerEmail;
	
	@NotBlank
	@Size(max = 10)
	private String filmId;
	
	@NotBlank
	@Size(max = 10)
	private String storeId;
	
	@Future
	private Date rentalReturn;
	
	public RentalRequest(String customerEmail, String filmId, String storeId) {
		super();
		this.customerEmail = customerEmail;
		this.filmId = filmId;
		this.storeId = storeId;
	}
	
	public RentalRequest(String customerEmail, String filmId, String storeId, Date rentalReturn) {
		super();
		this.customerEmail = customerEmail;
		this.filmId = filmId;
		this.storeId = storeId;
		this.rentalReturn = rentalReturn;
	}
	
}
