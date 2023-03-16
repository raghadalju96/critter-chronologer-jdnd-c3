package com.udacity.jdnd.course3.critter.converter;


import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repo.EmployeeRepo;
import com.udacity.jdnd.course3.critter.repo.PetRepo;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleDTOConverter {


    private final PetService petService;
    private final EmployeeService employeeService;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private PetRepo petRepo;

    public ScheduleDTOConverter(PetService petService, EmployeeService employeeService) {
        this.petService = petService;
        this.employeeService = employeeService;
    }

    public ScheduleDTO convertScheduleToDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Long> employeeIds = new ArrayList<>();
        List<Long> petIds = new ArrayList<>();

        if (schedule.getEmployees() != null){
            schedule.getEmployees().forEach(employee -> employeeIds.add(employee.getId()));
        }

        if (schedule.getPets() != null){
            schedule.getPets().forEach(pet -> petIds.add(pet.getId()));
        }

        scheduleDTO.setEmployeeIds(employeeIds);
        scheduleDTO.setPetIds(petIds);

        return scheduleDTO;
    }

    public Schedule convertDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Long> petIds = scheduleDTO.getPetIds();

        List<Employee> employees = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();

        if (employeeIds != null){
            employeeIds.forEach(employeeId -> {
                try {
                    employees.add(this.employeeRepo.findById(employeeId).get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if (petIds != null){
            petIds.forEach(petId -> {
                try {
                    pets.add(this.petRepo.findById(petId).get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return schedule;
    }
}
