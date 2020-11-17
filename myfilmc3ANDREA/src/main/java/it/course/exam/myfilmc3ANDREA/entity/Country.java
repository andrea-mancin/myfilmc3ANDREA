package it.course.exam.myfilmc3ANDREA.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

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
	
	@NaturalId
	@Column(name = "COUNTRY_NAME", length = 40, nullable = false)
	private String countryName;

}
