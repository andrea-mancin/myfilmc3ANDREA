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
@Table(name = "LANGUAGE")
@Data @AllArgsConstructor @NoArgsConstructor
public class Language {
	
	@Id
	@Column(name = "LANGUAGE_ID", length = 2)
	private String languageId;
	
	@NaturalId
	@Column(length = 40, nullable = false)
	private String languageName;

}
