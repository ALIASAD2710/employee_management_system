package com.example.demo.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.github.bonigarcia.wdm.WebDriverManager;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeWebControllerE2E {

    @LocalServerPort
    private int port;

    @Container
    private static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:5.7")
            .withDatabaseName("mydb")
            .withUsername("root")
            .withPassword("password")
            .withReuse(true);

    private static WebDriver driver;

    @BeforeClass
    public static void setupClass() {

        // setup Chrome Driver
        WebDriverManager.chromedriver().setup();

    }

    @BeforeAll
    static void beforeAll() {
        mysql.start();
        WebDriverManager.chromedriver().browserVersion("125.0.6422.60").setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
    }

    @AfterAll
    static void afterAll() {
        driver.quit();
        mysql.stop();
    }

    @Test
    void testAddEmployee() {
        driver.get("http://localhost:" + port + "/employees/");
        driver.findElement(By.linkText("Add Employee")).click();
        WebElement firstNameInput = driver.findElement(By.name("firstName"));
        firstNameInput.sendKeys("Asad");
        WebElement lastNameInput = driver.findElement(By.name("lastName"));
        lastNameInput.sendKeys("Ali");
        WebElement departmentInput = driver.findElement(By.name("department"));
        departmentInput.sendKeys(""); 
        departmentInput.submit();
        assertTrue(driver.getPageSource().contains("Asad"));
        assertTrue(driver.getPageSource().contains("Ali"));
        assertTrue(driver.getPageSource().contains(""));
    }


    @Test
    void testEditEmployee() {
        testAddEmployee();
        WebElement addedEmployee = driver.findElement(By.xpath("//td[text()='Asad']"));
        addedEmployee.findElement(By.xpath("../td/a[text()='Edit']")).click();
        WebElement editLastNameInput = driver.findElement(By.name("lastName"));
        editLastNameInput.clear();
        editLastNameInput.sendKeys("Khan");
        editLastNameInput.submit();
        assertTrue(driver.getPageSource().contains("Asad"));
        assertTrue(driver.getPageSource().contains("Khan"));
    }

    @Test
    void testDeleteEmployee() {
        testAddEmployee();
        WebElement addedEmployee = driver.findElement(By.xpath("//td[text()='Asad']"));
        addedEmployee.findElement(By.xpath("../td/a[text()='Delete']")).click();
        assertEquals(0, driver.findElements(By.xpath("//td[text()='Asad']")).size());
    }
}
