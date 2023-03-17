package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.converter.ScheduleDTOConverter;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repo.CustomerRepo;
import com.udacity.jdnd.course3.critter.repo.EmployeeRepo;
import com.udacity.jdnd.course3.critter.repo.PetRepo;
import com.udacity.jdnd.course3.critter.repo.ScheduleRepo;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private ScheduleDTOConverter scheduleDTOConverter;

    @Autowired
    private PetRepo petRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private EmployeeRepo employeeRepo;
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = this.scheduleDTOConverter.convertDTOToSchedule(scheduleDTO);
        this.scheduleRepo.save(schedule);
        return this.scheduleDTOConverter.convertScheduleToDTO(schedule);
    }


    public List<ScheduleDTO> getScheduleForPet(Long petId){
        Pet pet = this.petRepo.findById(petId).get();
       List <Schedule> schedule = this.scheduleRepo.findScheduleByPets(pet);
       return schedule.stream().map(scheduleDTOConverter::convertScheduleToDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleForEmployee(Long employeeId){
        Employee employee = this.employeeRepo.findById(employeeId).get();
        List <Schedule> schedules = this.scheduleRepo.findScheduleByEmployees(employee);
        return schedules.stream().map(scheduleDTOConverter::convertScheduleToDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleForCustomer(Long customerId){
        Customer customer = this.customerRepo.findById(customerId).get();
        List<Pet> pets = customer.getPet();
        List<Schedule> schedules = new LinkedList<>();

        pets.forEach(pet -> {
            List<Schedule> petsOnSchedule = this.scheduleRepo.findScheduleByPets(pet);
            schedules.addAll(petsOnSchedule);
        });
        return schedules.stream().map(scheduleDTOConverter::convertScheduleToDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getAllSchedules(){
        List<Schedule> scheduleList = this.scheduleRepo.findAll();
        return scheduleList.stream().map(scheduleDTOConverter::convertScheduleToDTO).collect(Collectors.toList());
    }
}
