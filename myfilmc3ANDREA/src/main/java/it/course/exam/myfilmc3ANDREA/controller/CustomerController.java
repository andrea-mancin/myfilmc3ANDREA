package it.course.exam.myfilmc3ANDREA.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import it.course.exam.myfilmc3ANDREA.entity.Customer;
import it.course.exam.myfilmc3ANDREA.payload.request.CustomerRequest;
import it.course.exam.myfilmc3ANDREA.payload.response.CustomRESTponse;
import it.course.exam.myfilmc3ANDREA.payload.response.CustomerResponse;
import it.course.exam.myfilmc3ANDREA.repository.CustomerRepository;
import it.course.exam.myfilmc3ANDREA.repository.RentalRepository;
import it.course.exam.myfilmc3ANDREA.repository.StoreRepository;

@RestController
@Validated
public class CustomerController {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	RentalRepository rentalRepository;
	
	@Autowired
	StoreRepository storeRepository;
	
	@PostMapping("add-update-customer") // @RequestBody CustomerRequest
	public ResponseEntity<?> addUpdateCustomer(@Valid @RequestBody CustomerRequest customerRequest, HttpServletRequest request) {
		
		boolean exists = customerRepository.existsById(customerRequest.getEmail());
		
		if (exists) {
			Customer customerToUpdate = customerRepository.findById(customerRequest.getEmail()).get();
			boolean changes = false;
			
			if (!customerToUpdate.getFirstName().equals(customerRequest.getFirstName())) {
				customerToUpdate.setFirstName(customerRequest.getFirstName());
				changes = true;
			}
			
			if (!customerToUpdate.getLastName().equals(customerRequest.getLastName())) {
				customerToUpdate.setLastName(customerRequest.getLastName());
				changes = true;
			}
			
			if (changes)
				return new ResponseEntity<CustomRESTponse>(
						new CustomRESTponse(200, "OK", "CustomerController: customer updated.", request),
						HttpStatus.OK);
							
			return new ResponseEntity<CustomRESTponse>(
					new CustomRESTponse(200, "OK", "CustomerController: customer already exists.", request),
					HttpStatus.OK);
			
		}
		
		String email = customerRequest.getEmail();
		String firstName = customerRequest.getFirstName();
		String lastName = customerRequest.getLastName();
		
		Customer customerToAdd = new Customer(email, firstName, lastName);
		
		customerRepository.save(customerToAdd);
			
		return new ResponseEntity<CustomRESTponse>(
				new CustomRESTponse(201, "CREATED", "CustomerController: new customer created.", request),
				HttpStatus.CREATED);
		
	}
	
	@GetMapping("/get-all-customers-by-store/{storeId}") // @return CustomerResponse: email, firstName, lastName
	public ResponseEntity<?> getAllCustomersByStore(@PathVariable @NotBlank @Size(max = 10) String storeId, HttpServletRequest request) {
		
		// Verifica se lo storeId passato è valido
		if (!storeRepository.existsById(storeId))
			return new ResponseEntity<CustomRESTponse>(
					new CustomRESTponse(404, "NOT FOUND", "CustomerController: store doesn't exist.", request),
					HttpStatus.NOT_FOUND);
		
		List<CustomerResponse> response = rentalRepository.jpqlFindAllCustomersByStoreId(storeId);
		
		if (response.isEmpty())
			return new ResponseEntity<CustomRESTponse>(
					new CustomRESTponse(404, "NOT FOUND", "CustomerController: no customer(s) found.", request),
					HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<CustomRESTponse>(
				new CustomRESTponse(200, "OK", response, request),
				HttpStatus.OK);
		
	}

}
