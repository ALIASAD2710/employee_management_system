package com.example.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.EmployeeService;

@SpringBootTest
class EmployeeWebControllerTest {

	@Mock
	private EmployeeService employeeService;

	@Mock
	private DepartmentService departmentService;

	@InjectMocks
	private EmployeeWebController employeeWebController;

	@Test
	void getAllEmployees() throws Exception {
		// Create
		Employee employee1 = new Employee(1, "test1", "lastname1", null);
		Employee employee2 = new Employee(2, "test2", "lastname2", null);
		when(employeeService.getAllEmployee()).thenReturn(Arrays.asList(employee1, employee2));
		// Mock
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(employeeWebController).build();

		// GET request
		mockMvc.perform(get("/employees/")).andExpect(status().isOk()).andExpect(view().name("employees"))
				.andExpect(model().attributeExists("employees"))
				.andExpect(model().attribute("employees", Arrays.asList(employee1, employee2)));
	}

	@Test
	void showAddEmployeeForm() throws Exception {
		// Create
		Department department1 = new Department(1, "Sales", null);
		Department department2 = new Department(2, "Hr", null);
		when(departmentService.getAllDepartments()).thenReturn(Arrays.asList(department1, department2));
		// Mock
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(employeeWebController).build();

		mockMvc.perform(get("/employees/add")).andExpect(status().isOk()).andExpect(view().name("add-employee"))
				.andExpect(model().attributeExists("employee")).andExpect(model().attributeExists("departments"));
	}

	@Test
	void addEmployee() throws Exception {
		// Create
		Employee employee = new Employee(1, "test1", "lastname1", null);
		// Mock
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(employeeWebController).build();

		// POST request
		mockMvc.perform(post("/employees/add").flashAttr("employee", employee)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/employees/"));

		// Verify
		verify(employeeService, times(1)).saveEmployee(employee);
	}

	@Test
	void showEditEmployeeForm() throws Exception {
		// Create
		Employee employee = new Employee(1, "test1", "lastname1", null);
		when(employeeService.getEmployeeById(1)).thenReturn(Optional.of(employee));
		// Mock
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(employeeWebController).build();

		// GET request
		mockMvc.perform(get("/employees/edit/{id}", 1)).andExpect(status().isOk())
				.andExpect(view().name("edit-employee")).andExpect(model().attributeExists("employee"))
				.andExpect(model().attribute("employee", employee));
	}

	@Test
	void editEmployee() throws Exception {
		// Create
		Employee employee = new Employee();
		employee.setFirstName("test1");
		employee.setLastName("lastname1");

		int id = 1;
		// Mock
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(employeeWebController).build();

		// POST request
		mockMvc.perform(post("/employees/edit/{id}", id).flashAttr("employee", employee))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/employees/"));

		// Verify
		ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);
		verify(employeeService, times(1)).saveEmployee(employeeCaptor.capture());

		Employee capturedEmployee = employeeCaptor.getValue();
		assertEquals(id, capturedEmployee.getId());
		assertEquals("test1", capturedEmployee.getFirstName());
		assertEquals("lastname1", capturedEmployee.getLastName());
	}

	@Test
	void deleteEmployee() throws Exception {

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(employeeWebController).build();

		// GET request
		mockMvc.perform(get("/employees/delete/{id}", 1)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/employees/"));

		// Verify
		verify(employeeService, times(1)).deleteEmployeeById(1);
	}

}
