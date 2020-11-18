package it.course.exam.myfilmc3ANDREA.controller;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import it.course.exam.myfilmc3ANDREA.entity.Film;
import it.course.exam.myfilmc3ANDREA.entity.Inventory;
import it.course.exam.myfilmc3ANDREA.entity.InventoryId;
import it.course.exam.myfilmc3ANDREA.entity.Store;
import it.course.exam.myfilmc3ANDREA.payload.response.CustomRESTponse;
import it.course.exam.myfilmc3ANDREA.repository.FilmRepository;
import it.course.exam.myfilmc3ANDREA.repository.InventoryRepository;
import it.course.exam.myfilmc3ANDREA.repository.StoreRepository;

@RestController
@Validated
public class InventoryController {

	@Autowired
	FilmRepository filmRepository;

	@Autowired
	StoreRepository storeRepository;

	@Autowired
	InventoryRepository inventoryRepository;

	@PostMapping("/add-film-to-store/{storeId}/{filmId}")
	@Transactional
	public ResponseEntity<?> addFilmToStore(@PathVariable @NotBlank @Size(max = 10) String storeId,
			@PathVariable @NotBlank @Size(max = 10) String filmId, HttpServletRequest request) {

		if (!filmRepository.existsById(filmId))
			return new ResponseEntity<CustomRESTponse>(
					new CustomRESTponse(404, "NOT FOUND", "InventoryController: film doens't exist", request), HttpStatus.NOT_FOUND);
		
		if (!storeRepository.existsById(storeId))
			return new ResponseEntity<CustomRESTponse>(
					new CustomRESTponse(404, "NOT FOUND", "InventoryController: store doens't exist", request), HttpStatus.NOT_FOUND);
		
		
		boolean existsInInventory = inventoryRepository.existsByInventoryIdFilmFilmIdAndInventoryIdStoreStoreId(filmId, storeId);

		if (existsInInventory)
			return new ResponseEntity<CustomRESTponse>(
					new CustomRESTponse(200, "OK", "InventoryController: film already in store", request), HttpStatus.OK);
		
		/* ELSE */
		Film filmToAdd = filmRepository.findByFilmId(filmId).get();
		
		Store store = storeRepository.findByStoreId(storeId).get();
		
		Inventory inventory = new Inventory(new InventoryId(filmToAdd, store));
		
		inventoryRepository.save(inventory);
		
		return new ResponseEntity<CustomRESTponse>(
				new CustomRESTponse(201, "CREATED", "InventoryController: film added to store", request), HttpStatus.CREATED);
	
		
	}

}
