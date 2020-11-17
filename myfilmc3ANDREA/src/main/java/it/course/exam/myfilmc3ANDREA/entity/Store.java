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
@Table(name = "STORE")
@Data @AllArgsConstructor @NoArgsConstructor
public class Store {
	
	@Id
	@Column(name = "STORE_ID", length = 10)
	private String storeId;
	
	@NaturalId
	@Column(length = 50, nullable = false)
	private String storeName;
	
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "INVENTORY",
//		joinColumns = @JoinColumn(name = "STORE_ID", referencedColumnName= "STORE_ID"),
//		inverseJoinColumns = @JoinColumn(name = "FILM_ID", referencedColumnName= "FILM_ID"))
//	Set<Film> inventory;
	
}