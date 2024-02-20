package com.xeridia.nr.service;

import com.xeridia.nr.model.Employee;
import com.xeridia.nr.repository.EmployeeRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;


@ApplicationScoped
public class EmployeeService {

    @Inject
    EntityManager em;

    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> listAll() {
        return employeeRepository.listAll();
    }


    @Transactional
    public void persist(Employee employee) {
        employeeRepository.persist(employee);
    }
}