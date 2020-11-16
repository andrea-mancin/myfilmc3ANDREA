package it.course.exam.myfilmc3ANDREA.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CUSTOMER")
@Data @AllArgsConstructor @NoArgsConstructor
public class Customer {
	
	@Id
	@Column(length = 50)
	private String email;
	
	@Column(length = 45, nullable = false)
	private String firstName;
	
	@Column(length = 45, nullable = false)
	private String lastName;

}
