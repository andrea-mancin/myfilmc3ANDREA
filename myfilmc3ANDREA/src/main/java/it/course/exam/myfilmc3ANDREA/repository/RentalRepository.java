package it.course.exam.myfilmc3ANDREA.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.course.exam.myfilmc3ANDREA.entity.Rental;
import it.course.exam.myfilmc3ANDREA.entity.RentalId;
import it.course.exam.myfilmc3ANDREA.payload.response.CustomerResponse;
import it.course.exam.myfilmc3ANDREA.payload.response.FilmRentResponse;
import it.course.exam.myfilmc3ANDREA.payload.response.FilmRentResponseNr;

@Repository
public interface RentalRepository extends JpaRepository<Rental, RentalId> {

	@Query(value = "SELECT DISTINCT new it.course.exam.myfilmc3ANDREA.payload.response.CustomerResponse ("
			+ "r.rentalId.customer.email, c.firstName, c.lastName) " + "FROM Rental r "
			+ "LEFT JOIN Customer c ON r.rentalId.customer.email = c.email "
			+ "WHERE r.rentalId.store.storeId = :storeId")
	List<CustomerResponse> jpqlFindAllCustomersByStoreId(String storeId);

	@Query(value = "SELECT COUNT(*) FROM rental " + "WHERE rental_date " + "BETWEEN :startDate "
			+ "AND :endDate", nativeQuery = true)
	int sqlCountByRentalsNumberInDateRange(Date startDate, Date endDate);

	boolean existsByRentalIdCustomerEmailAndRentalIdStoreStoreIdAndRentalIdFilmFilmIdAndRentalIdRentalDateIsNotNullAndRentalReturnAfter(
			String email, String storeId, String filmId, Date returnDate);

	Optional<Rental> findByRentalIdCustomerEmailAndRentalIdStoreStoreIdAndRentalIdFilmFilmIdAndRentalIdRentalDateIsNotNullAndRentalReturnAfter(
			String email, String storeId, String filmId, Date rentalReturn);

	@Query(value = "SELECT new it.course.exam.myfilmc3ANDREA.payload.response.FilmRentResponse("
			+ "r.rentalId.film.filmId, f.title) "
			+ "FROM Rental r "
			+ "LEFT JOIN Film f ON r.rentalId.film.filmId = f.filmId "
			+ "WHERE r.rentalId.customer.email = :customerId")
	List<FilmRentResponse> jpqlFindAllFilmsRentByOneCustomer(String customerId);
	
	@Query(value = "SELECT new it.course.exam.myfilmc3ANDREA.payload.response.CustomerResponse("
			+ "r.rentalId.customer.email, c.firstName, c.lastName) "
			+ "FROM Rental r "
			+ "LEFT JOIN Customer c ON r.rentalId.customer.email = c.email "
			+ "LEFT JOIN Film f ON r.rentalId.film.filmId = f.filmId "
			+ "WHERE f.language.languageId = :languageId")
	List<CustomerResponse> jpqlGetCustomersWhoRentFilmsByLanguageFilm(String languageId);
	
	@Query(value = "SELECT new it.course.exam.myfilmc3ANDREA.payload.response.FilmRentResponseNr("
			+ "r.rentalId.film.filmId, f.title, COUNT(r.rentalId.film.filmId) AS nr) "
			+ "FROM Rental r "
			+ "LEFT JOIN Film f ON r.rentalId.film.filmId = f.filmId "
			+ "GROUP BY r.rentalId.film.filmId "
			+ "ORDER BY nr DESC")
	List<FilmRentResponseNr> jpqlFindFilmWithMaxNumberOfRent();
	
}
