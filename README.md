Sonar Cloud [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ALIASAD2710_employee_management_system&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=ALIASAD2710_employee_management_system)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=ALIASAD2710_employee_management_system&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=ALIASAD2710_employee_management_system)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=ALIASAD2710_employee_management_system&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=ALIASAD2710_employee_management_system)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=ALIASAD2710_employee_management_system&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=ALIASAD2710_employee_management_system)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=ALIASAD2710_employee_management_system&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=ALIASAD2710_employee_management_system)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=ALIASAD2710_employee_management_system&metric=coverage)](https://sonarcloud.io/summary/new_code?id=ALIASAD2710_employee_management_system)
Code Coverall [![Coverage Status](https://coveralls.io/repos/github/ALIASAD2710/employee_management_system/badge.svg?branch=main)](https://coveralls.io/github/ALIASAD2710/employee_management_system?branch=main)
GitHub Actions [![Java CI with Maven](https://github.com/ALIASAD2710/employee_management_system/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/ALIASAD2710/employee_management_system/actions/workflows/maven-publish.yml)

<h1>Employee Management System</h1>

<p>Employee Management System is a web application built using Java version 17, Maven, and Spring Boot. It provides a RESTful API for managing employees and a simple HTML frontend for interacting with the API.</p>

<ol>
  <li>Clone the repository: <code>git clone https://github.com/ALIASAD2710/employee_management_system.git</code></li>
  <li>Import the project into Eclipse:
    <ul>
      <li>Open Eclipse and select <strong>File -> Import</strong>.</li>
      <li>Choose <strong>Maven -> Existing Maven Projects</strong> and click <strong>Next</strong>.</li>
      <li>Browse to the directory where you cloned the repository and select the project.</li>
      <li>Click <strong>Finish</strong> to import the project into Eclipse.</li>
    </ul>
  </li>
 </ol>
<h2>API Endpoints</h2>

<p>The REST API provides the following endpoints:</p>


<ul>
  <li><strong>POST localhost:5566/api/employees/save</strong>: Creates a new Employee.</li>
<li><strong>GET localhost:5566/api/employees/getAll</strong>: Retrieves all Employee.</li>
<li><strong>GET localhost:5566/api/employees/{id}</strong>: Retrieves a specific Employee by ID.</li>
<li><strong>Delete localhost:5566/api/employees/delete/{id}</strong>: Delete Employee by ID.</li>
<li><strong>POST localhost:5566/api/department/save</strong>: Creates a new Department.</li>
<li><strong>GETlocalhost:5566/api/department/getAll</strong>: Retrieves all Department.</li>
<li><strong>GETlocalhost:5566/api/department/{id}</strong>: Retrieves a specific Department by ID.</li>
<li><strong>Deletelocalhost:5566/api/department/delete/{id}</strong>: Delete Department by ID.</li>
</ul>


<h2>HTML Frontend</h2>

<p>The HTML frontend allows users to interact with the API using a user-friendly interface. It is accessible at <a href="localhost:5566/employees/">localhost:5566/employees/</a>.</p>

