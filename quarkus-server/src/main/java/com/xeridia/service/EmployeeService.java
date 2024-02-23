package com.xeridia.service;

import com.xeridia.model.Employee;
import com.xeridia.repository.EmployeeRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EmployeeService {

    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @WithTransaction
    public Uni<Employee> persist(Employee employee) {
        return employeeRepository.persist(employee);
    }

    @WithSession
    public Uni<List<Employee>> listAll() {
        return employeeRepository.listAll();
    }
}