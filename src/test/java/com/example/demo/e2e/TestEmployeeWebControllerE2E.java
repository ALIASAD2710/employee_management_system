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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.example.demo.dbcon.DbBase;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestEmployeeWebControllerE2E extends DbBase {

    @LocalServerPort
    private int port;

    private static WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeAll
    static void setupDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterAll
    static void teardownDriver() {
        driver.quit();
    }

    @Test
    void testAddEmployee() {
        // Given
        driver.get("http://localhost:" + port + "/employees/");
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10L));
        WebElement addEmployeeLink = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add Employee")));

        // When
        addEmployeeLink.click();
        WebElement firstNameInput = driver.findElement(By.name("firstName"));
        firstNameInput.sendKeys("Asad");
        WebElement lastNameInput = driver.findElement(By.name("lastName"));
        lastNameInput.sendKeys("Ali");
        WebElement departmentInput = driver.findElement(By.name("department"));
        departmentInput.sendKeys("");
        departmentInput.submit();

        // Then
        assertTrue(driver.getPageSource().contains("Asad"));
        assertTrue(driver.getPageSource().contains("Ali"));
        assertTrue(driver.getPageSource().contains(""));
    }

    @Test
    void testEditEmployee() {
        // Given
        testAddEmployee();

        // When
        WebElement addedEmployee = driver.findElement(By.xpath("//td[text()='Asad']"));
        addedEmployee.findElement(By.xpath("../td/a[text()='Edit']")).click();
        WebElement editLastNameInput = driver.findElement(By.name("lastName"));
        editLastNameInput.clear();
        editLastNameInput.sendKeys("Khan");
        editLastNameInput.submit();

        // Then
        assertTrue(driver.getPageSource().contains("Asad"));
        assertTrue(driver.getPageSource().contains("Khan"));
    }

    
    @Test
	void testDeleteEmployee() {
    	// Given
		testAddEmployee();
		// When
		WebElement addedEmployee = driver.findElement(By.xpath("//td[text()='Asad']"));
		addedEmployee.findElement(By.xpath("../td/a[text()='Delete']")).click();
		// Then
		assertEquals(0, driver.findElements(By.xpath("//td[text()='Asad']")).size());
	}
}
