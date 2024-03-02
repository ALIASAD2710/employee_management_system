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
			// validation errors
			List<String> validationErrors = bindingResult.getFieldErrors().stream()
					.map(error -> error.getField() + ": " + error.getDefaultMessage()).toList();

			// error message
			String errorMessage = "Validation errors: " + String.join(", ", validationErrors);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}

		Employee savedEmployee = employeeService.saveEmployee(employee);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
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

	@GetMapping("/department/{id}")
	public ResponseEntity<List<Employee>> getEmployeesByDepartmentId(@PathVariable int id) {
		List<Employee> employee = employeeService.getEmployeesByDepartmentId(id);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteEmployeeById(@PathVariable int id) {
		employeeService.deleteEmployeeById(id);
	}

	

}
