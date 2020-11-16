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

import it.course.exam.myfilmc3ANDREA.payload.request.FilmRequest;
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

	@Column(length = 4, nullable = false)
	private int releaseYear;

	@Column(length = 128, nullable = false)
	private String title;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID", nullable = false)
	private Country country;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID", nullable = false)
	private Language language;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "FILM_ACTOR", joinColumns = {
			@JoinColumn(name = "FILM_ID", referencedColumnName = "FILM_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "ACTOR_ID", referencedColumnName = "ACTOR_ID") })
	private Set<Actor> actors;
	
	public static Film createEntityFromRequest(FilmRequest filmRequest, Country country, Language language, Set<Actor> actors) {
		return new Film(
				filmRequest.getFilmId(),
				filmRequest.getDescription(),
				filmRequest.getReleaseYear().getValue(),
				filmRequest.getTitle(),
				country,
				language,
				actors);
	}

}
