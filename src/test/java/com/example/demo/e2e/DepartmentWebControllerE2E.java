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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentWebControllerE2E {

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
	public static void setupClass() 
	{
			
		// setup Chrome Driver
		WebDriverManager.chromedriver().setup();
		
	}
    
    @BeforeAll
    static void beforeAll() {
        mysql.start();
       	ChromeOptions options = new ChromeOptions();
    	options.addArguments("--headless"); 
    	driver = new ChromeDriver(options);
    }

    @AfterAll
    static void afterAll() {
        driver.quit();
        mysql.stop();
    }


    @Test
    void testAddDepartment() {
        driver.get("http://localhost:" + port + "/departments/");
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10L)); 
        WebElement addDepartmentLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add Department")));
        addDepartmentLink.click();
        WebElement departmentNameInput = driver.findElement(By.name("name"));
        departmentNameInput.sendKeys("HR");
        departmentNameInput.submit();
        assertTrue(driver.getPageSource().contains("HR"));
    }



    @Test
    void testEditDepartment() {
        testAddDepartment();
        WebElement addedDepartment = driver.findElement(By.xpath("//td[text()='HR']"));
        addedDepartment.findElement(By.xpath("../td/a[text()='Edit']")).click();
        WebElement editDepartmentNameInput = driver.findElement(By.name("name"));
        editDepartmentNameInput.clear();
        editDepartmentNameInput.sendKeys("IT");
        editDepartmentNameInput.submit();
        assertTrue(driver.getPageSource().contains("IT"));
    }

    @Test
    void testDeleteDepartment() {
        testAddDepartment();
        WebElement addedDepartment = driver.findElement(By.xpath("//td[text()='HR']"));
        addedDepartment.findElement(By.xpath("../td/a[text()='Delete']")).click();
        assertEquals(0, driver.findElements(By.xpath("//td[text()='HR']")).size());
    }
}
