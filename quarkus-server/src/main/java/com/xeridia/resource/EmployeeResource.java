package com.xeridia.resource;

import com.xeridia.model.Employee;
import com.xeridia.service.EmployeeService;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Path("/api/admin/employees")
@RequestScoped
public class EmployeeResource {

    EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Employee> persist(@Context SecurityContext ctx, Employee employee) {
        return employeeService.persist(employee);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Employee>> listAll() {
        return employeeService.listAll();
    }
}
