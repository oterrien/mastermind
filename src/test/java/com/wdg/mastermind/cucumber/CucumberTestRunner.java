package com.wdg.mastermind.cucumber;

import junit5.CucumberExtension;
import cucumber.api.CucumberOptions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.stream.Stream;

@Tag("cucumber")
@CucumberOptions(
        plugin = {"pretty"},
        features = "src/test/resources/",
        tags = {"~@Ignore"},
        glue = "com.wdg.mastermind.cucumber")
public class CucumberTestRunner {

    @ExtendWith(CucumberExtension.class)
    @TestFactory
    public Stream<DynamicTest> runCucumber(Stream<DynamicTest> scenarios) {
        return scenarios;
    }
}