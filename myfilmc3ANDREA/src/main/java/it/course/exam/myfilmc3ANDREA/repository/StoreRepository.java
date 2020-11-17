package it.course.exam.myfilmc3ANDREA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.course.exam.myfilmc3ANDREA.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
	
	

}
