package it.course.exam.myfilmc3ANDREA.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.course.exam.myfilmc3ANDREA.entity.Store;
import it.course.exam.myfilmc3ANDREA.payload.request.StoreRequest;
import it.course.exam.myfilmc3ANDREA.payload.response.CustomRESTponse;
import it.course.exam.myfilmc3ANDREA.repository.FilmRepository;
import it.course.exam.myfilmc3ANDREA.repository.InventoryRepository;
import it.course.exam.myfilmc3ANDREA.repository.StoreRepository;

@RestController
@Validated
public class StoreController {
	
	@Autowired
	StoreRepository storeRepository;
	
	@Autowired
	FilmRepository filmRepository;
	
	@Autowired
	InventoryRepository inventoryRepository;

	@PostMapping("/add-update-store")  // @RequestBody StoreRequest
	public ResponseEntity<?> addUpdateStore(@Valid @RequestBody StoreRequest storeRequest, HttpServletRequest request) {
	
		boolean storeExists = storeRepository.existsById(storeRequest.getStoreId());
		
		if (storeExists) {
			
			Optional<Store> searchedStore = storeRepository.findById(storeRequest.getStoreId());
			
			if (!searchedStore.get().getStoreName().equals(storeRequest.getStoreName())) {
				searchedStore.get().setStoreName(storeRequest.getStoreName());
			
				storeRepository.save(searchedStore.get());
				
				return new ResponseEntity<CustomRESTponse>(
						new CustomRESTponse(200, "OK", "StoreConstroller: store name updated", request),
						HttpStatus.OK);
			}
			
			return new ResponseEntity<CustomRESTponse>(
					new CustomRESTponse(200, "OK", "StoreConstroller: store already present, no need to update", request),
					HttpStatus.OK);
		} else {
		
		Store storeToAdd = new Store(storeRequest.getStoreId(), storeRequest.getStoreName());
		
		storeRepository.save(storeToAdd);
			
		return new ResponseEntity<CustomRESTponse>(
				new CustomRESTponse(201, "CREATED", "StoreController: new store added", request),
				HttpStatus.CREATED);
		
		}
		
	}
	
}
