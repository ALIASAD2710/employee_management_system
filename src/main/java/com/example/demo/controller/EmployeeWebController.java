package com.example.demo.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Employee;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeWebController {

	private final EmployeeService employeeService;
	private final DepartmentService departmentService;

	public EmployeeWebController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }
	
	private static final String REDIRECT_TO_EMPLOYEES = "redirect:/employees/";

	@GetMapping("/")
	public String getAllEmployees(Model model) {
		model.addAttribute("employees", employeeService.getAllEmployee());
		return "employees";
	}

	@GetMapping("/add")
	public String showAddEmployeeForm(Model model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("departments", departmentService.getAllDepartments());
		return "add-employee";
	}

	@PostMapping("/add")
	public String addEmployee(@ModelAttribute("employee") Employee employee) {
		employeeService.saveEmployee(employee);
		return REDIRECT_TO_EMPLOYEES;
	}

	@GetMapping("/edit/{id}")
	public String showEditEmployeeForm(@PathVariable("id") int id, Model model) {
		Optional<Employee> employeeOptional = employeeService.getEmployeeById(id);
		employeeOptional.ifPresent(employee -> model.addAttribute("employee", employee));
		return "edit-employee";
	}

	@PostMapping("/edit/{id}")
	public String editEmployee(@PathVariable("id") int id, @ModelAttribute("employee") Employee employee) {
	    Employee updatedEmployee = new Employee(id, employee.getFirstName(), employee.getLastName(), employee.getDepartment());
	    employeeService.saveEmployee(updatedEmployee);
	    return REDIRECT_TO_EMPLOYEES;
	}


	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable("id") int id) {
		employeeService.deleteEmployeeById(id);
		return REDIRECT_TO_EMPLOYEES;
	}

}
