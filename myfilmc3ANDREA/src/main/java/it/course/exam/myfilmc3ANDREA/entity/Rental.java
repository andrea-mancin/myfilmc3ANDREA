package it.course.exam.myfilmc3ANDREA.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RENTAL")
@Data @AllArgsConstructor @NoArgsConstructor
public class Rental {

	@EmbeddedId
	@Column(name = "RENTAL_ID")
	private RentalId rentalId;
	
	private Date rentalReturn;
	
}
