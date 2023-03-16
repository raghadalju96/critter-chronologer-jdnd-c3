package com.udacity.jdnd.course3.critter.model;


import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class Common {


    @Id
    @GeneratedValue
    private Long id;

    @Nationalized
    private String name;

}
