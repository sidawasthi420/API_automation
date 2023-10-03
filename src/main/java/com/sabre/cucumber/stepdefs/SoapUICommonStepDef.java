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
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SoapUICommonStepDef {
    private CommonLib commonLib;
    private Response response;
    public static String recordLocatorNewValue;
    public static String access_token;
    private vpsMain vps_main;
    public static String bodyParameter;
    private AssertionService assertionService;
    public static String deployementID;

    public SoapUICommonStepDef(TestContext testContext){
        this.commonLib = new CommonLib(testContext);
        this.vps_main = new vpsMain(testContext);
        this.assertionService = testContext.baseTestContext.getAssertionService();
    }

    @Given("^User sets the baseURI and basePath for generate token$")
    public void userSetsTheBaseURIAndBasePathForGenerateToken() throws Exception{
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

        String VPSTokenPath = commonLib.getSubstitutedValue("VPSConfig","tokenBasePath");
        System.out.println(VPSTokenPath);
        RestAssured.basePath = VPSTokenPath;
    }

    @And("^User sends the API request to generate token$")
    public void userSendsTheAPIRequestToToken() throws Exception {

        Map<String,String> requestHeaders = new HashMap<>();
        requestHeaders.put("authorization","Basic VmpFNmNHRjViV1Z1ZERwd1lYbHRaVzUwT2xOaFluSmw6Y0dGNWJUSXdNRFk9");
        requestHeaders.put("content-type","application/x-www-form-urlencoded");
        requestHeaders.put("cache-control","no-cache");

        HashMap map = new HashMap();
        map.put("grant_type","client_credentials");

        response = given()
                .headers(requestHeaders)
                .accept("*/*")
                .formParams(map).relaxedHTTPSValidation()
                .when()
                .post()
                .then()
                .extract().response();
    }

    @And("^Store the token and display on console$")
    public void storeTheTokenAndDisplayOnConsole() {
        String bodyObj = response.getBody().asString();
        //System.out.println(bodyObj);
        JsonPath jpath = new JsonPath(bodyObj);
        access_token = jpath.getString("access_token");
        System.out.println();
        System.out.println("Access token of non Dev :- " + access_token);
        System.out.println();
    }

    @Then("^Update body parameter of request in yyyy-MM-ddTHH:mm:ssZ format for \"([^\"]*)\"$")
    public void updateBodyParameterOfRequest(String fileName, DataTable dataTable) throws IOException {
        String jsonBody = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"\\src\\main\\java\\com\\sabre\\cucumber\\pages\\"+fileName+".txt")));

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
                case "DEPLOYEMENTID":
                    String deploymentIdJsonPath = "$.PaymentRQ..deploymentId";
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, deploymentIdJsonPath, deployementID);
                    break;
                case "DEPARTUREDATETIME":
                    String departureDateTimeJsonPath = "$.PaymentRQ..departureDateTime";
                    String departureDateTimeNewValue = vps_main.currentDateTime();
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, departureDateTimeJsonPath, departureDateTimeNewValue);
                    break;
                case "ARRIVALDATETIME":
                    String arrivalDateTimeJsonPath = "$.PaymentRQ..arrivalDateTime";
                    String arrivalDateTimeNewValue = vps_main.currentDateTime();
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, arrivalDateTimeJsonPath, arrivalDateTimeNewValue);
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

    @Then("^Update body parameter of request in millisecond datetime format for \"([^\"]*)\"$")
    public void updateBodyParameterOfRequestInMillisecond(String fileName, DataTable dataTable) throws IOException {
        String jsonBody = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"\\src\\main\\java\\com\\sabre\\cucumber\\pages\\"+fileName+".txt")));

        //List<String> data = dataTable.asList();

        List<List<String>> data = dataTable.asLists(String.class);
        for (String entry : data.get(0)) {
            switch (entry.toUpperCase()) {
                case "SYSTEMDATETIME":
                    String systemDateTimeJsonPath = "$.PaymentRQ..systemDateTime";
                    String systemDateTimeNewValue = String.valueOf(vps_main.currentDateTimeMilliSecond());
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, systemDateTimeJsonPath, systemDateTimeNewValue);
                    break;
                case "LOCALDATETIME":
                    String localDateTimeJsonPath = "$.PaymentRQ..localDateTime";
                    String localDateTimeNewValue = String.valueOf(vps_main.currentDateTimeMilliSecond());
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, localDateTimeJsonPath, localDateTimeNewValue);
                    break;
                case "CHECKINDATE":
                    String checkInDateTimeJsonPath = "$.PaymentRQ..checkInDate";
                    String checkInDateTimeNewValue = String.valueOf(vps_main.currentDateTimeMilliSecond());
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, checkInDateTimeJsonPath, checkInDateTimeNewValue);
                    break;
                case "CHECKOUTDATE":
                    String checkOutDateTimeJsonPath = "$.PaymentRQ..checkOutDate";
                    String checkOutDateTimeNewValue = String.valueOf(vps_main.currentDateTimeMilliSecond());
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, checkOutDateTimeJsonPath, checkOutDateTimeNewValue);
                    break;
                case "RECORDLOCATOR":
                    String recordLocatorJsonPath = "$.PaymentRQ..recordLocator";
                    recordLocatorNewValue = vps_main.generatePnr();
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, recordLocatorJsonPath, recordLocatorNewValue);
                    break;
                case "DEPLOYEMENTID":
                    String deploymentIdJsonPath = "$.PaymentRQ..deploymentId";
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, deploymentIdJsonPath, deployementID);
                    break;
                case "DEPARTUREDATETIME":
                    String departureDateTimeJsonPath = "$.PaymentRQ..departureDateTime";
                    String departureDateTimeNewValue = String.valueOf(vps_main.currentDateTimeMilliSecond());
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, departureDateTimeJsonPath, departureDateTimeNewValue);
                    break;
                case "ARRIVALDATETIME":
                    String arrivalDateTimeJsonPath = "$.PaymentRQ..arrivalDateTime";
                    String arrivalDateTimeNewValue = String.valueOf(vps_main.currentDateTimeMilliSecond());
                    jsonBody = vps_main.updateVPSAPIParameter(jsonBody, arrivalDateTimeJsonPath, arrivalDateTimeNewValue);
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

    @Then("^Store the deployementID from GETCard request$")
    public void storeTheDeployementIDFromGETCardRequest() throws IOException {
        String jsonBody = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"\\src\\main\\java\\com\\sabre\\cucumber\\pages\\getCard.txt")));
        List<String> data = new LinkedList<String>();
        data.add("systemDateTime");
        data.add("localDateTime");
        data.add("checkInDate");
        data.add("checkOutDate");
        data.add("recordLocator");
        for (String entry : data) {
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
                default:
            }
        }

        bodyParameter = jsonBody;
        System.out.println();
        System.out.println("Input Paramters:- ");
        System.out.println(bodyParameter);
        System.out.println();

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

        assertionService.assertThat("Verify status code matches for GETCard Request", String.valueOf(statusCode), CoreMatchers.containsString("200"));

        String bodyObj = response.getBody().asString();
        System.out.println(bodyObj);
        deployementID = vps_main.getVPSAPIParameter(bodyObj,"PaymentRS..virtualPaymentResult.deploymentId");
        String code = vps_main.getVPSAPIParameter(bodyObj,"PaymentRS..paymentResult.code");

        assertionService.assertThat("Verify paymentResult code matches", code, CoreMatchers.containsString("SUCCESS"));

        System.out.println("deployementID for GETCard Request :- " + deployementID);
    }
}