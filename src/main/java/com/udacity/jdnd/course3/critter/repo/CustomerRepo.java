package com.udacity.jdnd.course3.critter.repo;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    //List<CustomerDTO> findAllCustomer();

    Customer findCustomerByPetId(Long petId);

}