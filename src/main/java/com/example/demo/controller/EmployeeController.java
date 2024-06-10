package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import com.example.demo.utils.ErrorDetail;
import com.example.demo.utils.ValidationErrorResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PostMapping("/save")
	public ResponseEntity<Object> saveEmployee(@Valid @RequestBody Employee employee, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        List<ErrorDetail> validationErrors = bindingResult.getFieldErrors().stream()
	                .map(error -> new ErrorDetail(error.getField(), error.getDefaultMessage()))
	                .toList();
	        
	        ValidationErrorResponse errorResponse = new ValidationErrorResponse("Validation errors", validationErrors);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    }

	    try {
	        Employee savedEmployee = employeeService.saveEmployee(employee);
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving the employee.");
	    }
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<Employee>> getAllDepartments() {
		List<Employee> employee = employeeService.getAllEmployee();
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
		Optional<Employee> employeeOptional = employeeService.getEmployeeById(id);
		if (employeeOptional.isPresent()) {
			return ResponseEntity.ok(employeeOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/delete/{id}")
	public void deleteEmployeeById(@PathVariable int id) {
		employeeService.deleteEmployeeById(id);
	}

	

}
