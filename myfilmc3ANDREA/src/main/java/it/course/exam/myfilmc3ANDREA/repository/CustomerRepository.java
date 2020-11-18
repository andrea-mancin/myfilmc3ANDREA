package it.course.exam.myfilmc3ANDREA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.course.exam.myfilmc3ANDREA.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

}
