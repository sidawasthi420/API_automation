package com.sabre.cucumber.runner;

import io.cucumber.testng.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/main/java/com/sabre/cucumber/features/VPSGetCard.feature"},
		glue = {"com.sabre.cucumber.stepdefs"},
		plugin = {"html:target/cucumber-html-report", "json:target/cucumber.json",
				 "pretty:target/cucumber-pretty.txt","usage:target/cucumber-usage.json", 
		"junit:target/cucumber-results.xml"},
        tags = "@VPSTesting_GCP"
		)
	
public class test_runner extends AbstractTestNGCucumberTests{
	
	private TestNGCucumberRunner testNGCucumberRunner;


    @BeforeClass( alwaysRun = true )
    public void setUpClass()  {
        testNGCucumberRunner = new TestNGCucumberRunner( this.getClass() );
    }

    @Test( groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features" )
    public void feature( PickleWrapper pickleWrapper,FeatureWrapper cucumberFeature ) throws Throwable {
        testNGCucumberRunner.runScenario( pickleWrapper.getPickle());
    }

    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass( alwaysRun = true )
    public void tearDownClass()  {
        testNGCucumberRunner.finish();
    }
}