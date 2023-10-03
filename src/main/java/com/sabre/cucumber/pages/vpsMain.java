package com.sabre.cucumber.pages;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.sabre.cucumber.state.TestContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

public class vpsMain {

    private TestContext testContext;
    private SecureRandom random;

    public vpsMain(TestContext testContext) {
        this.testContext = testContext;
    }

    public static String updateVPSAPIParameter(String jsonBody, String parameterName, String parameterValue)
    {
        Configuration configuration = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                .mappingProvider(new JacksonMappingProvider()).build();

        DocumentContext json = JsonPath.using(configuration).parse(jsonBody);
        String jsonUpdate = json.set(parameterName, parameterValue).jsonString();
        return jsonUpdate;
    }

    public static String getVPSAPIParameter(String jsonBody, String parameterName)
    {
        Configuration configuration = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                .mappingProvider(new JacksonMappingProvider()).build();
        DocumentContext json = JsonPath.using(configuration).parse(jsonBody);
        String value = json.read(parameterName).toString();
        try {
            value = value.replace("[\"", "").replace("\"]", "");
        }catch(Exception ex)
        {
        }
        return value;
    }

    public static String currentDateTime()
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        //sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        return sdf.format(date);
    }

    public static long currentDateTimeMilliSecond()
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String date1 = sdf.format(date);
        LocalDateTime localDateTime = LocalDateTime.parse(date1,
                DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss") );
        long millis = localDateTime.atZone(ZoneId.of("Asia/Kolkata")).toInstant().toEpochMilli();
        return millis;
    }

    public String generatePnr() {
        String pnr = RandomStringUtils.randomAlphabetic(6).toUpperCase();
        return pnr;
    }
}
