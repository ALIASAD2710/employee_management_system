package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;
import com.example.demo.utils.ErrorDetail;
import com.example.demo.utils.ValidationErrorResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

	private final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@PostMapping("/save")
	public ResponseEntity<Object> saveDepartment(@Valid @RequestBody Department department,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<ErrorDetail> validationErrors = bindingResult.getFieldErrors().stream()
					.map(error -> new ErrorDetail(error.getField(), error.getDefaultMessage())).toList();
			ValidationErrorResponse errorResponse = new ValidationErrorResponse("Validation errors", validationErrors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
		try {
			Department savedDepartment = departmentService.saveDepartment(department);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while saving the department.");
		}
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<Department>> getAllDepartments() {
		List<Department> departments = departmentService.getAllDepartments();
		return new ResponseEntity<>(departments, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable int id) {
		Optional<Department> optionalDepartment = departmentService.getDepartmentById(id);
		if (optionalDepartment.isPresent()) {
			return ResponseEntity.ok(optionalDepartment.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/delete/{id}")
	public void deleteDepartmentById(@PathVariable int id) {
		departmentService.deleteDepartmentById(id);

	}

}