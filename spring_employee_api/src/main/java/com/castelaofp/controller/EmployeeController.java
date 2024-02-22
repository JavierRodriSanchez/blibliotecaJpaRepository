package com.castelaofp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.castelaofp.dto.EmployeeDto;
import com.castelaofp.mapper.EmployeeMapper;
import com.castelaofp.model.Employee;
import com.castelaofp.service.EmployeeService;

import jakarta.validation.Valid;

/**
 * http://localhost:8080/v3/api-docs http://localhost:8080/swagger-ui/index.html
 * 
 */

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;

	@GetMapping("/allEmployee")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public List<EmployeeDto> findAll() {

		return EmployeeMapper.toDto(employeeService.findAll());

	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long employeeId) {
		EmployeeDto dto = null;
		try {
			dto = EmployeeMapper.toDto(employeeService.getById(employeeId).get());

		} catch (Exception e) {

			return responseNotFound(employeeId);
		}

		return new ResponseEntity<>(dto, HttpStatus.BAD_GATEWAY);

	}

	@GetMapping("/employee/name")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public List<Employee> findByName(@RequestParam(name = "name") String name) {
		
		return employeeService.findByName(name);
		
	}

	@PostMapping("/create")
	public ResponseEntity<EmployeeDto> create(@Valid @RequestBody EmployeeDto employeeDto) {

		Employee employee1 = EmployeeMapper.toEntity(employeeDto);
		EmployeeDto dto;
		
		dto = EmployeeMapper.toDto(employeeService.create(employee1));
		

		return new ResponseEntity<>(dto, HttpStatus.CREATED);

	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody EmployeeDto employeeDto) {
		Employee entity;
		EmployeeDto dto;
		try {

			entity = EmployeeMapper.toEntity(employeeDto);
			dto = EmployeeMapper.toDto(employeeService.update(employeeId, entity).get());
		} catch (Exception e) {
			return responseNotFound(employeeId);
		}

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long employeeId) {

		boolean valido = employeeService.delete(employeeId);
		if (!valido) {
			return new ResponseEntity<>(responseNotFound(employeeId), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);

	}

	private ResponseEntity<?> responseNotFound(long id) {
		String mensaje = "El empleado con id : " + id + " no encontrado";

		return new ResponseEntity<>(new ErrorResponse(mensaje), HttpStatus.NOT_FOUND);

	}
}