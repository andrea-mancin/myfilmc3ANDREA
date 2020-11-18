package it.course.exam.myfilmc3ANDREA.repository;

import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.course.exam.myfilmc3ANDREA.entity.Film;
import it.course.exam.myfilmc3ANDREA.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {

	Optional<Store> findByStoreId(@NotBlank @Size(max = 10) String storeId);
	
}
