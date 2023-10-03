package com.sabre.cucumber.stepdefs;

import com.sabre.cucumber.lib.CommonLib;
import com.sabre.cucumber.pages.vpsMain;
import com.sabre.cucumber.state.TestContext;
import com.sabre.ngpq.lite.services.base.AssertionService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class VPSGetCardJsonNewFieldsStepDef {
    private CommonLib commonLib;
    private vpsMain vps_main;
    private Response response;
    private static String paymentReferenceNumber;
    private AssertionService assertionService;

    public VPSGetCardJsonNewFieldsStepDef(TestContext testContext){
        this.commonLib = new CommonLib(testContext);
        this.assertionService = testContext.baseTestContext.getAssertionService();
        this.vps_main = new vpsMain(testContext);
    }

    @Given("^User sets the baseURI and basePath for GETCardJsonNewFields request$")
    public void userSetsTheBaseURIAndBasePathForGETCardJsonNewFieldsRequest() throws Exception{
        String VPSEnvironment = commonLib.getSubstitutedValue("VPSConfig","environment");

        if(VPSEnvironment.equalsIgnoreCase("Integration")) {
            String VPSBaseURLInt = commonLib.getSubstitutedValue("VPSConfig", "baseURLInt");
            System.out.println(VPSBaseURLInt);
            RestAssured.baseURI = VPSBaseURLInt;
        }
        else if(VPSEnvironment.equalsIgnoreCase("Certification")) {
            String VPSBaseURLCert = commonLib.getSubstitutedValue("VPSConfig", "baseURLCert");
            System.out.println(VPSBaseURLCert);
            RestAssured.baseURI = VPSBaseURLCert;
        }

        String VPSApisPath = commonLib.getSubstitutedValue("VPSConfig","restApisBasePath");
        System.out.println(VPSApisPath);
        RestAssured.basePath = VPSApisPath;
    }

    @Then("^User sends the API request for GETCardJsonNewFields request$")
    public void userSendsTheAPIRequestForGETCardJsonNewFieldsRequest() throws Exception {

        String accessToken = SoapUICommonStepDef.access_token;
        String token = "Bearer "+accessToken;
        Map<String,String> requestHeaders = new HashMap<>();
        requestHeaders.put("authorization",token);
        requestHeaders.put("content-type","application/json");
        requestHeaders.put("cache-control","no-cache");

        response = given()
                .headers(requestHeaders)
                .accept("*/*")
                .body(SoapUICommonStepDef.bodyParameter).relaxedHTTPSValidation()
                .when()
                .post()
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();

        assertionService.assertThat("Verify status code matches", String.valueOf(statusCode), CoreMatchers.containsString("200"));
    }

    @And("^Store the paymentReferenceNumber and display on console for GET Card JsonNewFields request$")
    public void storeThePaymentReferenceNumberAndDisplayOnConsoleForGETCardJsonNewFieldsRequest() {
        String bodyObj = response.getBody().asString();
        System.out.println(bodyObj);
        paymentReferenceNumber = vps_main.getVPSAPIParameter(bodyObj,"PaymentRS..paymentReferenceNumber");
        String code = vps_main.getVPSAPIParameter(bodyObj,"PaymentRS..paymentResult.code");

        assertionService.assertThat("Verify paymentResult code matches", code, CoreMatchers.containsString("SUCCESS"));

        System.out.println("paymentReferenceNumber for GETCardJsonNewFields Request :- " + paymentReferenceNumber);
    }
}
