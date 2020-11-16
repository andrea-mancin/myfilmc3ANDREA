package it.course.exam.myfilmc3ANDREA.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "COUNTRY")
@Data @AllArgsConstructor @NoArgsConstructor
public class Country {
	
	@Id
	@Column(name = "COUNTRY_ID", length = 2)
	private String countryId;
	
	@Column(length = 40, nullable = false)
	private String countryName;

}
