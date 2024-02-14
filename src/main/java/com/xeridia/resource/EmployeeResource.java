package com.xeridia.resource;

import com.xeridia.model.Employee;
import com.xeridia.service.EmployeeService;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/employees")
public class EmployeeResource {

    EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Employee> persist(Employee employee) {
        return employeeService.persist(employee);
    }


}
