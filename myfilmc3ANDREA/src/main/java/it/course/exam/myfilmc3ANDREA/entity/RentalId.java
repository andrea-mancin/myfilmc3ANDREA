package it.course.exam.myfilmc3ANDREA.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data @AllArgsConstructor @NoArgsConstructor
public class RentalId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7056355014283331947L;

	@Column(name = "RENTAL_DATE", nullable = false)
	private Date rentalDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMAIL", nullable = false)
	private Customer customerEmail;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FILM_ID", nullable = false)
	private Film filmId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STORE_ID", nullable = false)
	private Store storeId;
	
}
