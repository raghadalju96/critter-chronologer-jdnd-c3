package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.converter.EmployeeDTOConverter;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.repo.EmployeeRepo;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;


    @Autowired
    private EmployeeDTOConverter employeeDTOConverter;


    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO){
        Employee employee = this.employeeDTOConverter.convertDTOToEmployee(employeeDTO);
        this.employeeRepo.save(employee);
        return this.employeeDTOConverter.convertEmployeeToDTO(employee);
    }

    public EmployeeDTO getEmployee(Long employeeId){
        Employee employee = this.employeeRepo.findById(employeeId).get();
        return this.employeeDTOConverter.convertEmployeeToDTO(employee);

    }

    public void setAvailability(Set<DayOfWeek> daysAvailable,  long employeeId) throws Exception{
        Employee employee = this.employeeRepo.findById(employeeId).orElseThrow(() -> new Exception("Employee with id "+employeeId+" not found"));
        employee.setDaysAvailable(daysAvailable);
        this.employeeRepo.save(employee);
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO){
        LocalDate localDate = employeeRequestDTO.getDate();
        HashSet<EmployeeSkill> skills = new HashSet<>(employeeRequestDTO.getSkills());
        Set<Employee> availableEmployees = this.employeeRepo.findEmployeeByDaysAvailable(localDate.getDayOfWeek());
        Set<Employee> employeesWithSkills = new HashSet<>();

        availableEmployees.forEach(employee -> {
         boolean matchedSkills = employee.getSkills().containsAll(skills);

         if (matchedSkills){
             employeesWithSkills.add(employee);
         }
        });

      return employeesWithSkills.stream().map(employeeDTOConverter::convertEmployeeToDTO).collect(Collectors.toList());
    }




}
