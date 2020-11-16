package it.course.exam.myfilmc3ANDREA.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.course.exam.myfilmc3ANDREA.entity.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, String >{

	Optional<Language> findByLanguageId(String languageId);
	
}
