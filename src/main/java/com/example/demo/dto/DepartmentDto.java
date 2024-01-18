package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.Employee;

import jakarta.persistence.OneToMany;

public class DepartmentDto 
{
	
	private int id;

    private String name;
    
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	

	public DepartmentDto() {
		super();
		
	}

	public DepartmentDto(int id, String name, List<Employee> employees) 
	{
		super();
		this.id = id;
		this.name = name;
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", employees=" + employees + "]";
	}


}
