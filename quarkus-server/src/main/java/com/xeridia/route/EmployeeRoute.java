package com.xeridia.route;

import com.xeridia.model.Employee;
import com.xeridia.service.EmployeeService;
import io.quarkus.vertx.web.ReactiveRoutes;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@RouteBase(path = "/api/admin/route/employees", produces = ReactiveRoutes.APPLICATION_JSON)
@ApplicationScoped
public class EmployeeRoute {

    EmployeeService employeeService;

    public EmployeeRoute(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Route(methods = Route.HttpMethod.GET, path = "")
    public Uni<List<Employee>> listAll() {
        return employeeService.listAll();
    }
}
