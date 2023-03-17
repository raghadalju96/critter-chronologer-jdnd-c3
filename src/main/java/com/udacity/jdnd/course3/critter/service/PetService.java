package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.converter.PetDTOConverter;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repo.CustomerRepo;
import com.udacity.jdnd.course3.critter.repo.PetRepo;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepo petRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PetDTOConverter petDTOConverter;

//    public PetDTO savePet(PetDTO petDTO){
//        Pet pet =  this.petDTOConverter.convertDTOToPet(petDTO);
//        Customer customer = customerRepo.findById(petDTO.getOwnerId()).get();
//        pet.setCustomer(customer);
//        petRepo.save(pet);
//        return this.petDTOConverter.convertPetToDTO(pet);
//
//    }


    public PetDTO savePet(PetDTO petDTO){
        Pet petConverted =  this.petDTOConverter.convertDTOToPet(petDTO);
        Customer customer = customerRepo.findById(petDTO.getOwnerId()).get();
        petConverted.setCustomer(customer);
        Pet pet =  petRepo.save(petConverted);
      //  Customer owner = pet.getCustomer();
        customer.addPet(pet);
        customerRepo.save(customer);
        return this.petDTOConverter.convertPetToDTO(pet);

    }

    public List<PetDTO> getPets(){
        List<Pet> pets = this.petRepo.findAll();
        List<PetDTO> petDTOList = new ArrayList<>();

        pets.stream().forEach(pet -> {

            petDTOList.add(this.petDTOConverter.convertPetToDTO(pet));
        });

        return petDTOList;
    }

    public PetDTO getPet(Long petId){
        Pet pet = this.petRepo.findById(petId).orElseThrow(RuntimeException::new);
        return this.petDTOConverter.convertPetToDTO(pet);
    }


    public List<PetDTO> getPetsByOwner(Long ownerId){
        List<Pet> pets = this.petRepo.findPetsByCustomerId(ownerId);
        List<PetDTO> petDTOList = new ArrayList<>();

        pets.stream().forEach(pet -> {
            petDTOList.add(this.petDTOConverter.convertPetToDTO(pet));
        });

        return petDTOList;
    }
}
