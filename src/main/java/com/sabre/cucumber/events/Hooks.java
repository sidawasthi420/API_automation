package com.sabre.cucumber.events;

import com.sabre.cucumber.state.TestContext;
import io.cucumber.java.After;

public class Hooks {

    private TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @After(order = 40)
    public void cucumberTearDown() {
        //Close Browser
        if (testContext.baseTestContext.getBrowserService().driver != null) {
            testContext.baseTestContext.getBrowserService().closeDriver();
        }
    }

}
