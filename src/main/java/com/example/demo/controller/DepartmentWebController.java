package com.example.demo.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;

@Controller
@RequestMapping("/departments")
public class DepartmentWebController {

	private final DepartmentService departmentService;

	public DepartmentWebController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@GetMapping("/")
	public String showAllDepartments(Model model) {
		model.addAttribute("departments", departmentService.getAllDepartments());
		return "departments";
	}

	@GetMapping("/add")
	public String showAddDepartmentForm(Model model) {
		model.addAttribute("department", new Department());
		return "add-department";
	}

	@PostMapping("/add")
	public String addDepartment(@ModelAttribute("department") Department department) {
		departmentService.saveDepartment(department);
		return "redirect:/departments/";
	}

	@GetMapping("/edit/{id}")
	public String showEditDepartmentForm(@PathVariable("id") int id, Model model) {
		Optional<Department> departmentOptional = departmentService.getDepartmentById(id);
		departmentOptional.ifPresent(department -> model.addAttribute("department", department));
		return "edit-department";
	}

	@PostMapping("/edit/{id}")
	public String editDepartment(@PathVariable("id") int id, @ModelAttribute("department") Department department) {
		department.setId(id);
		departmentService.saveDepartment(department);
		return "redirect:/departments/";
	}

	@GetMapping("/delete/{id}")
	public String deleteDepartment(@PathVariable("id") int id) {
		departmentService.deleteDepartmentById(id);
		return "redirect:/departments/";
	}

}
