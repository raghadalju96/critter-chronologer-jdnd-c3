package com.udacity.jdnd.course3.critter.repo;

import com.udacity.jdnd.course3.critter.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepo extends JpaRepository<Pet, Long> {

    List<Pet> findPetsByCustomerId(Long customerId);
}
