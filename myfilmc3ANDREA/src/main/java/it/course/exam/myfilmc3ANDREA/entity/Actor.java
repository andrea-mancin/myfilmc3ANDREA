package it.course.exam.myfilmc3ANDREA.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACTOR")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actor {

	@Id
	@Column(name = "ACTOR_ID", length = 10)
	private String actorId;

	@Column(length = 45)
	private String firstName;

	@Column(length = 45)
	private String lastName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "countryId")
	private Country countryId;

}
