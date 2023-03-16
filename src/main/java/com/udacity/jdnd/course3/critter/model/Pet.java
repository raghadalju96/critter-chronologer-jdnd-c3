package com.udacity.jdnd.course3.critter.model;


import com.udacity.jdnd.course3.critter.pet.PetType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Pet extends Common{



    private PetType type;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private LocalDate birthDate;
    private String notes;

    @ManyToMany
    private List<Schedule> scheduleList;
}
