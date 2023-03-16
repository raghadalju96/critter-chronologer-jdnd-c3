package com.udacity.jdnd.course3.critter.repo;

import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepo extends JpaRepository<Schedule, Long> {

    List<Schedule> findScheduleByPets(Pet pet);
    List<Schedule> findScheduleByEmployees(Employee employee);
}
