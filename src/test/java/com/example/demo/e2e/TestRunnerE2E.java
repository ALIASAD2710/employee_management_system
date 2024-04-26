package com.example.demo.e2e;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources", glue = "com.example.demo.e2e")
public class TestRunnerE2E {

}
