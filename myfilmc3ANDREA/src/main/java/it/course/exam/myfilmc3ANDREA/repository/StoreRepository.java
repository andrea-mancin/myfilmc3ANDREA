package it.course.exam.myfilmc3ANDREA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.course.exam.myfilmc3ANDREA.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
	
//	@Query(value = "SELECT new it.course.exam.myfilmc3ANDREA.payload.response.SimpleFilmResponse ("
//			+ "f.filmId, s.storeName) " 
//			+ "FROM Store s " 
//			+ "LEFT JOIN Film f ON s.storeId = f.filmId " 
//			+ "WHERE f.filmId = :filmId")
//	Optional<SimpleFilmResponse> sqlFindFilmInStoreByFilmId(String filmId);
		
}
