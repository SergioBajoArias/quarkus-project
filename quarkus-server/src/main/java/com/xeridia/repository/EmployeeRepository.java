package com.xeridia.repository;

import com.xeridia.model.Employee;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepositoryBase<Employee, Long> {

}
