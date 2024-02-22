package com.castelaofp.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.castelaofp.model.Employee;

@Primary //Indicamos que sea la implementacion escogida por defecto
@Repository
public interface EmployeeJpaRepository  extends JpaRepository<Employee, Long>, EmployeeRepository  {
									
							
											
					
}