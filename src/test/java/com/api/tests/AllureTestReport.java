package com.api.tests;

import io.qameta.allure.*;
import org.testng.annotations.BeforeSuite;

@Epic("API Automation")
@Feature("Allure Reporting Integration")
public class AllureTestReport {

    @BeforeSuite
    @Step("Initialize Allure Report")
    public void setupAllure() {
        // This is where you'd initialize environment properties or logging if needed
        System.out.println("Starting Allure Test Reporting...");
    }
}
