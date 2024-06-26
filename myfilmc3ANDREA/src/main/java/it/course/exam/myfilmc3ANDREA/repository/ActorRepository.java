package it.course.exam.myfilmc3ANDREA.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.course.exam.myfilmc3ANDREA.entity.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, String> {

	@Query(value = "SELECT a FROM Actor a "
			+ "LEFT JOIN FETCH a.country "
			+ "WHERE a.actorId IN (:actorsId)")
	Set<Actor> jpqlFindByActorIdIn(Set<String> actorsId);

	@Query(value = "SELECT a FROM Actor a "
			+ "LEFT JOIN FETCH a.country "
			+ "WHERE a.lastName IN (:actorLastnames)")
	Set<Actor> jpqlFindByLastNameIn(Set<String> actorLastnames);

	@Query(value = "SELECT actorId FROM Actor a WHERE a.lastName IN (:actorLastnames)")
	Set<String> jpqlFindActorsIdByLastName(Set<String> actorLastnames);
	
}
