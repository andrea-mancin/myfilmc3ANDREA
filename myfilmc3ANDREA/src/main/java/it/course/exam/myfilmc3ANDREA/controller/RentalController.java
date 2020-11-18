package it.course.exam.myfilmc3ANDREA.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.course.exam.myfilmc3ANDREA.entity.Customer;
import it.course.exam.myfilmc3ANDREA.entity.Film;
import it.course.exam.myfilmc3ANDREA.entity.Rental;
import it.course.exam.myfilmc3ANDREA.entity.RentalId;
import it.course.exam.myfilmc3ANDREA.entity.Store;
import it.course.exam.myfilmc3ANDREA.payload.request.RentalRequest;
import it.course.exam.myfilmc3ANDREA.payload.response.CustomRESTponse;
import it.course.exam.myfilmc3ANDREA.payload.response.CustomerResponse;
import it.course.exam.myfilmc3ANDREA.payload.response.FilmRentResponse;
import it.course.exam.myfilmc3ANDREA.payload.response.FilmRentResponseNr;
import it.course.exam.myfilmc3ANDREA.repository.CustomerRepository;
import it.course.exam.myfilmc3ANDREA.repository.FilmRepository;
import it.course.exam.myfilmc3ANDREA.repository.InventoryRepository;
import it.course.exam.myfilmc3ANDREA.repository.LanguageRepository;
import it.course.exam.myfilmc3ANDREA.repository.RentalRepository;
import it.course.exam.myfilmc3ANDREA.repository.StoreRepository;
import it.course.exam.myfilmc3ANDREA.service.RentalService;

@RestController
@Validated
public class RentalController {

	@Autowired
	RentalRepository rentalRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	FilmRepository filmRepository;

	@Autowired
	StoreRepository storeRepository;

	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	LanguageRepository languageRepository;

	@Autowired
	RentalService rentalService;

	@PostMapping("/add-update-rental") // add: inserimento rental ; update: aggiornamento solo della data restituzione
	public ResponseEntity<?> addUpdateRental(@Valid @RequestBody RentalRequest rentalRequest,
			HttpServletRequest request) {

		/* Controlli sull'esistenza di Customer, Film e Store */
		if (!customerRepository.existsById(rentalRequest.getCustomerEmail()))
			return new ResponseEntity<CustomRESTponse>(
					new CustomRESTponse(404, "NOT FOUND", "RentalController: customer doesn't exist.", request),
					HttpStatus.NOT_FOUND);
		if (!filmRepository.existsById(rentalRequest.getFilmId()))
			return new ResponseEntity<CustomRESTponse>(
					new CustomRESTponse(404, "NOT FOUND", "RentalController: film doesn't exist.", request),
					HttpStatus.NOT_FOUND);
		if (!storeRepository.existsById(rentalRequest.getStoreId()))
			return new ResponseEntity<CustomRESTponse>(
					new CustomRESTponse(404, "NOT FOUND", "RentalController: store doesn't exist.", request),
					HttpStatus.NOT_FOUND);

		Customer customer = customerRepository.findById(rentalRequest.getCustomerEmail()).get();
		Film film = filmRepository.findById(rentalRequest.getFilmId()).get();
		Store store = storeRepository.findById(rentalRequest.getStoreId()).get();

		/* UPDATE */
		boolean rentalExists = rentalRepository
				.existsByRentalIdCustomerEmailAndRentalIdStoreStoreIdAndRentalIdFilmFilmIdAndRentalIdRentalDateIsNotNullAndRentalReturnAfter(
						customer.getEmail(), store.getStoreId(), film.getFilmId(), new Date());

		// Restituzione
		if (rentalExists) {
			Optional<Rental> searchedRental = rentalRepository
					.findByRentalIdCustomerEmailAndRentalIdStoreStoreIdAndRentalIdFilmFilmIdAndRentalIdRentalDateIsNotNullAndRentalReturnAfter(
							customer.getEmail(), store.getStoreId(), film.getFilmId(), new Date());

			Rental rentalToUpdate = searchedRental.get();

			rentalToUpdate.setRentalReturn(new Date());

			rentalRepository.save(rentalToUpdate);

			return new ResponseEntity<CustomRESTponse>(
					new CustomRESTponse(200, "OK", "RentalController: rental returned", request), HttpStatus.OK);
		}

		/* ADD */

		boolean existsInInventory = inventoryRepository
				.existsByInventoryIdFilmFilmIdAndInventoryIdStoreStoreId(film.getFilmId(), store.getStoreId());

		if (!existsInInventory)
			return new ResponseEntity<CustomRESTponse>(new CustomRESTponse(404, "NOT FOUND",
					"RentalController: film can't be found in specified store.", request), HttpStatus.NOT_FOUND);

		RentalId rentalId = new RentalId(new Date(), customer, film, store);

		Rental rentalToAdd = new Rental(rentalId, rentalService.fromCalendarToDateRentalToReturn());

		rentalRepository.save(rentalToAdd);

		return new ResponseEntity<CustomRESTponse>(
				new CustomRESTponse(200, "OK", "RentalController: new rental added.", request), HttpStatus.OK);

	}

	/**
	 * 
	 * @param startDate formatted like "yyyy-MM-dd"
	 * @param endDate   formatted like "yyyy-MM-dd"
	 * @param request
	 * @return ResponseEntity<?>
	 */
	@GetMapping("get-rentals-number-in-date-range") // @return : int
	public ResponseEntity<?> getRentalsNumberInDateRange(@RequestParam String startDate, @RequestParam String endDate,
			HttpServletRequest request) {

		LocalDate localDateStart = LocalDate.parse(startDate);
		LocalDate localDateEnd = LocalDate.parse(endDate);

		Date startDateForSQL = Date.from(localDateStart.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		Date endDateForSQL = Date.from(localDateEnd.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

		int total = rentalRepository.sqlCountByRentalsNumberInDateRange(startDateForSQL, endDateForSQL);

		return new ResponseEntity<CustomRESTponse>(new CustomRESTponse(200, "OK",
				"RentalController: number of rental(s) in specified period " + total, request), HttpStatus.OK);

	}

	@GetMapping("find-all-films-rent-by-one-customer/{customerId}") // @return: FilmRentResponse : film_id, title
																	// (contare anche i film non ancora retituiti)
	public ResponseEntity<?> findAllFilmsRentByOneCustomer(@PathVariable @NotBlank @Size(max = 50) String customerId,
			HttpServletRequest request) {

		if (!customerRepository.existsById(customerId))
			return new ResponseEntity<CustomRESTponse>(new CustomRESTponse(404, "NOT FOUND",
					"RentalController: specified customer doesn't exist", request), HttpStatus.NOT_FOUND);

		List<FilmRentResponse> response = rentalRepository.jpqlFindAllFilmsRentByOneCustomer(customerId);

		if (response.isEmpty())
			return new ResponseEntity<CustomRESTponse>(
					new CustomRESTponse(200, "OK", "RentalController: specified customer never rent a film", request),
					HttpStatus.OK);

		return new ResponseEntity<CustomRESTponse>(new CustomRESTponse(200, "OK", response, request), HttpStatus.OK);

	}

	@GetMapping("find-film-with-max-number-of-rent") // @return: FilmRentResponseNr : film_id, title, number_of_rents
	public ResponseEntity<?> findFilmWithMaxNumberOfRent(HttpServletRequest request) {

		List<FilmRentResponseNr> response = rentalRepository.jpqlFindFilmWithMaxNumberOfRent();

		if (response.isEmpty())
			return new ResponseEntity<CustomRESTponse>(new CustomRESTponse(200, "OK", "RentalController: no results", request),
					HttpStatus.OK);
			
		return new ResponseEntity<CustomRESTponse>(new CustomRESTponse(200, "OK", response.get(0), request),
				HttpStatus.OK);

	}

	@GetMapping("get-customers-who-rent-films-by-language-film/{languageId}") // @return: CustomerResponse: email,
																				// firstname, lastname
	public ResponseEntity<?> getCustomersWhoRentFilmsByLanguageFilm(
			@PathVariable @NotBlank @Size(min = 2, max = 2) String languageId, HttpServletRequest request) {
		
		boolean languageExists = languageRepository.existsById(languageId);
		
		if (!languageExists)
			return new ResponseEntity<CustomRESTponse>(new CustomRESTponse(404, "NOT FOUND",
					"RentalController: language not found", request), HttpStatus.NOT_FOUND);
		
		List<CustomerResponse> response = rentalRepository.jpqlGetCustomersWhoRentFilmsByLanguageFilm(languageId);
		
		if (response.isEmpty())
			return new ResponseEntity<CustomRESTponse>(new CustomRESTponse(200, "OK",
					"RentalController: no film with given language ID has been rent", request), HttpStatus.OK);
		
		return new ResponseEntity<CustomRESTponse>(new CustomRESTponse(200, "OK",
				response, request), HttpStatus.OK);
		
	}
}
