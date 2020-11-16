package it.course.exam.myfilmc3ANDREA.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.course.exam.myfilmc3ANDREA.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

	Optional<Country> findByCountryId(String countryId);
	
}
