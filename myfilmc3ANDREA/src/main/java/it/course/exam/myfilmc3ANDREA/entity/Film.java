package it.course.exam.myfilmc3ANDREA.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FILM")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {

	@Id
	@Column(name = "FILM_ID", length = 10)
	private String filmId;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Size(min = 4, max = 4)
	private Integer releaseYear;

	@Size(max = 128)
	private String title;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	private Country countryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	private Language languageId;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "FILM_ACTOR", joinColumns = {
			@JoinColumn(name = "FILM_ID", referencedColumnName = "FILM_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "ACTOR_ID", referencedColumnName = "ACTOR_ID") })
	private Set<Actor> actorId;

}
