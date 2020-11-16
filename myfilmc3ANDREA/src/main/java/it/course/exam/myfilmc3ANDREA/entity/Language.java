package it.course.exam.myfilmc3ANDREA.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LANGUAGE")
@Data @AllArgsConstructor @NoArgsConstructor
public class Language {
	
	@Id
	@Size(min = 2, max = 2)
	@Column(columnDefinition = "VARCHAR(2)")
	private String languageId;
	
	@Size(max = 40)
	private String languageName;

}
