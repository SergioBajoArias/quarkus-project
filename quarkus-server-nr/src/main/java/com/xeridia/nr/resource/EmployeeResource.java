package com.xeridia.nr.resource;

import com.xeridia.nr.model.Employee;
import com.xeridia.nr.service.EmployeeService;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/employees")
public class EmployeeResource {

    EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> listAll() {
        return employeeService.listAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void persist(Employee employee) {
        employeeService.persist(employee);
    }
}
