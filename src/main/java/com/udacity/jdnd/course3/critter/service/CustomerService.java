package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.converter.CustomerDTOConverter;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.repo.CustomerRepo;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;


    @Autowired
    private CustomerDTOConverter customerDTOConverter;

    public CustomerDTO saveCustomer(CustomerDTO customerDTO){
        Customer customer = this.customerDTOConverter.convertDTOToCustomer(customerDTO);
        this.customerRepo.save(customer);
        return this.customerDTOConverter.convertCustomerToDTO(customer);
    }

    public List<CustomerDTO> getAllCustomer(){
       List<Customer> customers = this.customerRepo.findAll();
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        customers.stream().forEach(customer -> {
            customerDTOList.add(this.customerDTOConverter.convertCustomerToDTO(customer));
        });

        return customerDTOList;
    }

    public CustomerDTO getOwnerByPet(Long petId){
       Customer customer = this.customerRepo.findCustomerByPetId(petId);
       return this.customerDTOConverter.convertCustomerToDTO(customer);
    }

}
