package com.sabre.cucumber.stepdefs;

import com.sabre.cucumber.lib.CommonLib;
import com.sabre.cucumber.pages.vpsMain;
import com.sabre.cucumber.state.TestContext;
import com.sabre.ngpq.lite.services.base.AssertionService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class VPSCancelNotificationStepDef {
    private CommonLib commonLib;
    private vpsMain vps_main;
    private Response response;
    public static String paymentReferenceNumber;
    private AssertionService assertionService;
    public static String bodyParameter;
    public static String recordLocatorNewValue;

    public VPSCancelNotificationStepDef(TestContext testContext){
        this.commonLib = new CommonLib(testContext);
        this.assertionService = testContext.baseTestContext.getAssertionService();
        this.vps_main = new vpsMain(testContext);
    }

    @Given("^User sets the baseURI and basePath for CancelNotification request$")
    public void userSetsTheBaseURIAndBasePathForCancelNotificationRequest() throws Exception{
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

    @Then("^User sends the API request for CancelNotification request$")
    public void userSendsTheAPIRequestToCancelNotificationRequest() throws Exception {

        String accessToken = SoapUICommonStepDef.access_token;
        String token = "Bearer "+accessToken;
        Map<String,String> requestHeaders = new HashMap<>();
        requestHeaders.put("authorization",token);
        requestHeaders.put("content-type","application/json");
        requestHeaders.put("cache-control","no-cache");

        response = given()
                .headers(requestHeaders)
                .accept("*/*")
                .body(bodyParameter).relaxedHTTPSValidation()
                .when()
                .post()
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();

        assertionService.assertThat("Verify status code matches", String.valueOf(statusCode), CoreMatchers.containsString("200"));
    }

    @And("^Store the paymentReferenceNumber and display on console for CancelNotification request$")
    public void storeThePaymentReferenceNumberAndDisplayOnConsoleForCancelNotificationRequest() {
        String bodyObj = response.getBody().asString();
        System.out.println(bodyObj);
        paymentReferenceNumber = vps_main.getVPSAPIParameter(bodyObj,"PaymentRS..paymentReferenceNumber");
        String code = vps_main.getVPSAPIParameter(bodyObj,"PaymentRS..paymentResult.code");

        assertionService.assertThat("Verify paymentResult code matches", code, CoreMatchers.containsString("SUCCESS"));

        System.out.println("paymentReferenceNumber for CancelNotification Request :- " + paymentReferenceNumber);
    }

    @Then("^Update the body parameter of CancelNotification request in yyyy-MM-ddTHH:mm:ssZ format for \"([^\"]*)\"$")
    public void updateBodyParameterOfRequest(String fileName, DataTable dataTable) throws IOException {
        String jsonBody = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\src\\main\\java\\com\\sabre\\cucumber\\pages\\" + fileName + ".txt")));

        //List<String> data = dataTable.asList();

        List<List<String>> data = dataTable.asLists(String.class);
        for (String entry : data.get(0)) {
            switch (entry.toUpperCase()) {
                case "SYSTEMDATETIME":
                    String systemDateTimeJsonPath = "$.PaymentRQ..systemDateTime";
                    String systemDateTimeNewValue = vps_main.currentDateTime();
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, systemDateTimeJsonPath, systemDateTimeNewValue);
                    break;
                case "LOCALDATETIME":
                    String localDateTimeJsonPath = "$.PaymentRQ..localDateTime";
                    String localDateTimeNewValue = vps_main.currentDateTime();
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, localDateTimeJsonPath, localDateTimeNewValue);
                    break;
                case "CHECKINDATE":
                    String checkInDateTimeJsonPath = "$.PaymentRQ..checkInDate";
                    String checkInDateTimeNewValue = vps_main.currentDateTime();
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, checkInDateTimeJsonPath, checkInDateTimeNewValue);
                    break;
                case "CHECKOUTDATE":
                    String checkOutDateTimeJsonPath = "$.PaymentRQ..checkOutDate";
                    String checkOutDateTimeNewValue = vps_main.currentDateTime();
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, checkOutDateTimeJsonPath, checkOutDateTimeNewValue);
                    break;
                case "RECORDLOCATOR":
                    String recordLocatorJsonPath = "$.PaymentRQ..recordLocator";
                    recordLocatorNewValue = vps_main.generatePnr();
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, recordLocatorJsonPath, recordLocatorNewValue);
                    break;
                case "PAYMENTREFERENCENUMBER":
                    String paymentReferenceNumberJsonPath = "$.PaymentRQ..paymentReferenceNumber";
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, paymentReferenceNumberJsonPath, VPSConfirmationStepDef.paymentReferenceNumber);
                    break;
                case "DEPLOYEMENTID":
                    String deploymentIdJsonPath = "$.PaymentRQ..deploymentId";
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, deploymentIdJsonPath, SoapUICommonStepDef.deployementID);
                    break;
                default:
            }
        }

        bodyParameter = jsonBody;
        System.out.println();
        System.out.println("Input Paramters:- ");
        System.out.println(bodyParameter);
        System.out.println();
    }
}