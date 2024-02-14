package com.xeridia.service;

import com.xeridia.model.Employee;
import com.xeridia.repository.EmployeeRepository;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

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
}