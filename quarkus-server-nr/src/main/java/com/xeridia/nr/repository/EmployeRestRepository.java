package com.xeridia.nr.repository;

import com.xeridia.nr.model.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "employees", path = "/api/admin/data-rest/employees")
public interface EmployeRestRepository extends PagingAndSortingRepository<Employee, Long> {
}
