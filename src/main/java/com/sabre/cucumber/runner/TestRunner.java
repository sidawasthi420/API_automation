package com.sabre.cucumber.runner;

import com.sabre.ngpq.lite.cucumber.runner.BaseTestRunner;
import com.sabre.ngpq.lite.services.base.SubstitutorService;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@CucumberOptions(
        features = {"src/main/java/com/sabre/cucumber/features"},
        glue = {"com.sabre.cucumber.stepdefs"},
        plugin = {"json:target/cucumber.json",
                  "html:target/cucumber.html"},
        tags = "@VPSTesting_GCP")

public class TestRunner extends BaseTestRunner {

    @Parameters({"sprintId", "env", "productArea", "product", "component", "cucumber.options", "uapZone", "jenkinsBuild",
            "sonarQubeUri", "livereporting", "phase", "rallyIntegration", "enableWiremockServer", "jenkinsJobName"})
    @BeforeTest(alwaysRun = true)
    public void setUpTest(@Optional("Cucumber_Sandbox") String sprintId, @Optional("INT") String env,
                          @Optional("Payments_OS") String productArea, @Optional("Payments") String product,
                          @Optional("Selenium") String component, @Optional("") String tags,
                          @Optional("Green") String uapZone, @Optional("NoBuild") String jenkinsBuild,
                          @Optional("NoSonarQubeUri") String sonarQubeUri,
                          @Optional("false") String livereporting, @Optional("FFT") String phase,
                          @Optional("false") String rallyIntegration, @Optional("false") String enableWiremockServer,
                          @Optional("NoJob") String jenkinsJobName) {
        reportingMetaData.setSprintId(sprintId);
        reportingMetaData.setEnv(env);
        reportingMetaData.setProductArea(productArea);
        reportingMetaData.setProduct(product);
        reportingMetaData.setComponent(component);
        reportingMetaData.setUapZone(uapZone);
        reportingMetaData.setJenkinsBuild(jenkinsBuild);
        reportingMetaData.setSonarQubeUri(sonarQubeUri);
        reportingMetaData.setLivereporting(livereporting);
        reportingMetaData.setPhase(phase);
        reportingMetaData.setRallyIntegration(rallyIntegration);
        reportingMetaData.setEnableWiremockServer(enableWiremockServer);
        reportingMetaData.setJenkinsJob(jenkinsJobName);
        reportingMetaData.setEnv(env);

        SubstitutorService.setEnvValue(env);
        System.setProperty("OSEnv", env);
        System.out.println("OS NAME: " + System.getProperty("os.name").toLowerCase());
    }

}