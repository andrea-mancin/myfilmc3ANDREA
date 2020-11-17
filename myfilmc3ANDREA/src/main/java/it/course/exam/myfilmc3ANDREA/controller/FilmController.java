package it.course.exam.myfilmc3ANDREA.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.course.exam.myfilmc3ANDREA.entity.Actor;
import it.course.exam.myfilmc3ANDREA.entity.Country;
import it.course.exam.myfilmc3ANDREA.entity.Film;
import it.course.exam.myfilmc3ANDREA.entity.Language;
import it.course.exam.myfilmc3ANDREA.payload.request.FilmRequest;
import it.course.exam.myfilmc3ANDREA.payload.response.CustomResponse;
import it.course.exam.myfilmc3ANDREA.payload.response.FilmResponse;
import it.course.exam.myfilmc3ANDREA.payload.response.SimpleFilmResponse;
import it.course.exam.myfilmc3ANDREA.repository.ActorRepository;
import it.course.exam.myfilmc3ANDREA.repository.CountryRepository;
import it.course.exam.myfilmc3ANDREA.repository.FilmRepository;
import it.course.exam.myfilmc3ANDREA.repository.InventoryRepository;
import it.course.exam.myfilmc3ANDREA.repository.LanguageRepository;
import it.course.exam.myfilmc3ANDREA.repository.StoreRepository;
import it.course.exam.myfilmc3ANDREA.service.FilmService;

@RestController
public class FilmController {

	@Autowired
	FilmRepository filmRepository;

	@Autowired
	LanguageRepository languageRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	ActorRepository actorRepository;

	@Autowired
	StoreRepository storeRepository;

	@Autowired
	InventoryRepository inventoryRepository;

	@Autowired
	FilmService filmService;

	@PostMapping("add-update-film")
	private ResponseEntity<?> addUpdateFilm(@RequestBody @Valid FilmRequest filmRequest, HttpServletRequest request) {

		Optional<Film> searchedFilm = filmRepository.findByFilmId(filmRequest.getFilmId());

		/* Se il film non è presente dato il suo ID -> procedura di aggiunta */
		if (!searchedFilm.isPresent()) {

			/* CONTROLLI SULL'ESISTENZA DI COUNTRY, LANGUAGE E ACTORS */
			Optional<Country> searchedCountry = countryRepository.findByCountryId(filmRequest.getCountryId());

			if (!searchedCountry.isPresent())
				return new ResponseEntity<Object>("FilmController: no such country found.", HttpStatus.NOT_FOUND);

			Optional<Language> searchedLanguage = languageRepository.findByLanguageId(filmRequest.getLanguageId());

			if (!searchedLanguage.isPresent())
				return new ResponseEntity<Object>("FilmController: no such language found.", HttpStatus.NOT_FOUND);

			Set<Actor> searchedActors = actorRepository.jpqlFindByActorIdIn(filmRequest.getActorsId());

			if (searchedActors.isEmpty())
				return new ResponseEntity<Object>("FilmController: no such actor(s) found.", HttpStatus.NOT_FOUND);
			/* FINE CONTROLLI */

			Country country = searchedCountry.get();
			Language language = searchedLanguage.get();
			Set<Actor> actors = searchedActors;

			Film filmToAdd = Film.createEntityFromRequest(filmRequest, country, language, actors);

			filmRepository.save(filmToAdd);

			return new ResponseEntity<Object>("FilmController: film saved.", HttpStatus.OK);

		} else {

			Boolean changes = false;

			Film filmToUpdate = searchedFilm.get();

			// Se il COUNTRY_ID è differente...
			if (!filmRequest.getCountryId().equals(filmToUpdate.getCountry().getCountryId())) {

				Optional<Country> searchedCountry = countryRepository.findByCountryId(filmRequest.getCountryId());

				if (!searchedCountry.isPresent())
					return new ResponseEntity<Object>("FilmController: no such country found.", HttpStatus.NOT_FOUND);

				Country country = searchedCountry.get();

				filmToUpdate.setCountry(country);

				changes = true;

			}

			// Se il LANGUAGE_ID è differente...
			if (!filmRequest.getLanguageId().equals(filmToUpdate.getLanguage().getLanguageId())) {

				Optional<Language> searchedLanguage = languageRepository.findByLanguageId(filmRequest.getLanguageId());

				if (!searchedLanguage.isPresent())
					return new ResponseEntity<Object>("FilmController: no such language found.", HttpStatus.NOT_FOUND);

				Language language = searchedLanguage.get();

				filmToUpdate.setLanguage(language);

				changes = true;

			}

			// Se il set di Actor è differente...
			if (!filmRequest.getCountryId().equals(filmToUpdate.getCountry().getCountryId())) {

				Set<Actor> searchedActors = actorRepository.jpqlFindByActorIdIn(filmRequest.getActorsId());

				if (searchedActors.isEmpty())
					return new ResponseEntity<Object>("FilmController: no such actor(s) found.", HttpStatus.NOT_FOUND);

				Set<Actor> actors = searchedActors;

				filmToUpdate.setActors(actors);

				changes = true;

			}

			// Se la descrizione è differente...
			if (!filmRequest.getDescription().equals(filmToUpdate.getDescription())) {
				filmToUpdate.setDescription(filmRequest.getDescription());
				changes = true;
			}

			// Se l'anno è differente...
			if (filmRequest.getReleaseYear().getValue() != filmToUpdate.getReleaseYear()) {
				filmToUpdate.setReleaseYear(filmRequest.getReleaseYear().getValue());
				changes = true;
			}

			// Se il titolo è differente...
			if (!filmRequest.getTitle().equals(filmToUpdate.getTitle())) {
				filmToUpdate.setTitle(filmRequest.getTitle());
				changes = true;
			}

			if (changes) {
				filmRepository.save(filmToUpdate);
				return new ResponseEntity<Object>("FilmController: film updated", HttpStatus.OK);
			}

			return new ResponseEntity<Object>("FilmController: no update needed.", HttpStatus.OK);

		}

	}

	@GetMapping("get-film/{filmId}")
	private ResponseEntity<?> getFilm(@PathVariable String filmId, HttpServletRequest request) {

		Optional<FilmResponse> searchedFilm = filmRepository.jpqlFindByFilmById(filmId);

		if (!searchedFilm.isPresent())
			return new ResponseEntity<CustomResponse>(
					new CustomResponse(404, "NOT_FOUND", "FilmController: no such film found with given ID", request),
					HttpStatus.NOT_FOUND);

		return new ResponseEntity<CustomResponse>(new CustomResponse(200, "OK", searchedFilm, request), HttpStatus.OK);

	}

	@GetMapping("get-films-paged-by-title-asc")
	private ResponseEntity<?> getFilmsPagedByTitleAsc(@RequestParam(defaultValue = "0") int pagNo,
			@RequestParam(defaultValue = "10") int pagSize, HttpServletRequest request) {

		List<FilmResponse> filmResponsePaged = filmService.pagedFilmResponseOfAllFilms(pagNo, pagSize, "ASC", "title");

		if (filmResponsePaged.isEmpty())
			return new ResponseEntity<CustomResponse>(
					new CustomResponse(404, "NOT_FOUND", "FilmController: no film(s) found.", request), HttpStatus.OK);

		return new ResponseEntity<CustomResponse>(new CustomResponse(200, "OK", filmResponsePaged, request),
				HttpStatus.OK);

	}

	@GetMapping("find-film-in-store/{filmId}") // RESPONSE: film_id, store_name
	private ResponseEntity<?> findFilmInStore(@PathVariable String filmId, HttpServletRequest request) {

		Optional<SimpleFilmResponse> inventory = inventoryRepository.jpqlFindFilmInStoreByFilmId(filmId);
		if (inventory.isPresent())
			return new ResponseEntity<CustomResponse>(new CustomResponse(200, "OK", inventory, request), HttpStatus.OK);

		return new ResponseEntity<CustomResponse>(new CustomResponse(404, "NOT_FOUND", "FilmController: film not found in any store", request), HttpStatus.OK);
	}

}
