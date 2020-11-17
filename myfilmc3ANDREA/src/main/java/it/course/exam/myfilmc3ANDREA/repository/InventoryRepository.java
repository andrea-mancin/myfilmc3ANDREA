package it.course.exam.myfilmc3ANDREA.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.course.exam.myfilmc3ANDREA.entity.Inventory;
import it.course.exam.myfilmc3ANDREA.entity.InventoryId;
import it.course.exam.myfilmc3ANDREA.payload.response.SimpleFilmResponse;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, InventoryId> {

	@Query(value = "SELECT new it.course.exam.myfilmc3ANDREA.payload.response.SimpleFilmResponse( "
			+ "f.filmId, s.storeName) "
			+ "FROM Inventory i "
			+ "LEFT JOIN Store s ON i.inventoryId.store = s.storeId "
			+ "LEFT JOIN Film f ON i.inventoryId.film = f.filmId "
			+ "WHERE f.filmId = :filmId")
	Optional<SimpleFilmResponse> jpqlFindFilmInStoreByFilmId(String filmId);
	
}
