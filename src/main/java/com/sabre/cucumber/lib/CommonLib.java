package com.sabre.cucumber.lib;

import static java.sql.DriverManager.getConnection;
import com.sabre.cucumber.state.TestContext;
import com.sabre.ngpq.lite.services.base.BrowserService;
import com.sabre.ngpq.lite.services.base.AssertionService;
import com.sabre.ngpq.platform.services.DBService;
import org.hamcrest.Matchers;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonLib {

    private TestContext testContext;
    private WebDriver driver;
    private BrowserService browserService;
    private AssertionService assertionService;
    private DBService dbService;
    public static final String RESOURCE_PATH = "src/main/resources";

    public CommonLib(TestContext testContext) {
        this.testContext = testContext;
        this.browserService = testContext.baseTestContext.getBrowserService();
        this.driver = testContext.baseTestContext.getBrowserService().driver;
    }

    public void highlightElement(WebElement element) {
        for (int i = 0; i < 2; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: yellow; border: 2px solid yellow;");
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
        }
    }

    public Matcher getMatchers(String sourceString, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(sourceString);
        return matcher;
    }

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
        dbService.getHostname();
        dbService.getUsername();
        dbService.getPassword();
        dbService.getPort();
    }

    public ResultSet retrieveResultSetForQuery(String sqlQuery) {

        ResultSet resultSet = null;

        try {
            String dbHostUrl = getSubstitutedValue("PMTAPConfig","dbHostUrl");
            String dbPortNumber = getSubstitutedValue("PMTAPConfig","dbPortNumber");
            String dbConnectProtocol = getSubstitutedValue("PMTAPConfig","dbConnectProtocol");
            String dbServiceName = getSubstitutedValue("PMTAPConfig","dbServiceName");

            String username = getSubstitutedValue("PMTAPConfig","dbUserId");
            String pwd = getSubstitutedValue("PMTAPConfig","dbPassword");

            String url = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(HOST="+dbHostUrl+")(PORT="+dbPortNumber
                    +")(PROTOCOL="+dbConnectProtocol+"))(CONNECT_DATA=(SERVICE_NAME="+dbServiceName+")))";

            Connection conn = getConnection(url, username, pwd);
            if (conn != null) {
                PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
                resultSet = preparedStatement.executeQuery();

            } else {
                assertionService.assertThat("Failed to connect to DB", conn, Matchers.is(null));
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public String getSubstitutedValue(String configFileName, String key){
//        SubstitutorService substitutorService = new SubstitutorService();
        String value = "";
        try {
            value = loadFromResources(configFileName).get(key);
            //value = substitutorService.substituteValueNoReporting("{"+configFileName + "."+ key+"}");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public Map<String, String> loadFromResources(String fileName){

        Map<String, String> mapFromResources = new HashMap<>();
        String filePath = getAbsolutePathOfFileInGivenPath(RESOURCE_PATH, fileName);
        try{
            if(!filePath.isEmpty()) {
                InputStream input = new FileInputStream(filePath);
                Properties prop = new Properties();
                prop.load(input);
                for (String key : prop.stringPropertyNames())
                    mapFromResources.put(key, prop.getProperty(key));
            }else
                throw new FileNotFoundException(fileName + " in path "+RESOURCE_PATH);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return mapFromResources;
    }

    public String getAbsolutePathOfFileInGivenPath(String filePath, String fileName){
        String absoluteFilePath = "";
        File folder = new File(filePath);
        File[] files = folder.listFiles();
        for(File file : files){
            if(file.isFile() && file.getName().contains(fileName)) {
                return file.getAbsolutePath();
            }
            else if(file.isDirectory())
                absoluteFilePath = getAbsolutePathOfFileInGivenPath(file.getAbsolutePath(), fileName);
        }
        return absoluteFilePath;
    }

}
