package com.example.demo.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
class TestDepartmentWebControllerE2E extends DbBase {

    @LocalServerPort
    private int port;

    private static WebDriver driver;

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
    void testAddDepartment() {
        // Given
        driver.get("http://localhost:" + port + "/departments/");
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10L));
        WebElement addDepartmentLink = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add Department")));

        // When
        addDepartmentLink.click();
        WebElement departmentNameInput = driver.findElement(By.name("name"));
        departmentNameInput.sendKeys("HR");
        departmentNameInput.submit();

        // Then
        assertTrue(driver.getPageSource().contains("HR"));
    }

    @Test
    void testEditDepartment() {
        // Given
        testAddDepartment();

        // When
        WebElement addedDepartment = driver.findElement(By.xpath("//td[text()='HR']"));
        addedDepartment.findElement(By.xpath("../td/a[text()='Edit']")).click();
        WebElement editDepartmentNameInput = driver.findElement(By.name("name"));
        editDepartmentNameInput.clear();
        editDepartmentNameInput.sendKeys("IT");
        editDepartmentNameInput.submit();

        // Then
        assertTrue(driver.getPageSource().contains("IT"));
    }

    
    @Test
    void testDeleteDepartment() {
    	// Given
        driver.get("http://localhost:" + port + "/departments/");
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10L));
        WebElement addDepartmentLink = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add Department")));
        // When
        addDepartmentLink.click();
        WebElement departmentNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
        departmentNameInput.sendKeys("HR");
        departmentNameInput.submit();
        // Verify
        assertTrue(driver.getPageSource().contains("HR"));
        driver.get("http://localhost:" + port + "/departments/");
        WebElement addedDepartment = driver.findElement(By.xpath("//td[text()='HR']"));
        addedDepartment.findElement(By.xpath("../td/a[text()='Delete']")).click();
        // Assert
        assertEquals(0, driver.findElements(By.xpath("//td[text()='HR']")).size());
    }
}
