package it.course.exam.myfilmc3ANDREA.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data @AllArgsConstructor @NoArgsConstructor
public class InventoryId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3495838509848225148L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FILM_ID")
	private Film film;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STORE_ID")
	private Store store;
}
