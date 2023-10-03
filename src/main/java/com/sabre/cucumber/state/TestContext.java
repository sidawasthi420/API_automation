package com.sabre.cucumber.state;

import com.sabre.ngpq.cucumber.state.BaseTestContext;
import com.sabre.ngpq.domain.model.global.Global;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestContext {
    private Pattern pattern1 = Pattern.compile("TD\\.(\\w+\\d*)");
    private Pattern pattern2 = Pattern.compile("\\#\\{\\@(\\w+)\\}");

    public BaseTestContext baseTestContext;
    public Global global;

    public TestContext(BaseTestContext baseTestContext) {
        this.baseTestContext = baseTestContext;
        this.global = baseTestContext.getGlobal();
    }

    public String getTestData(String key){
        Matcher matcher1 = pattern1.matcher(key);
        Matcher matcher2 = pattern2.matcher(key);

        if (matcher2.find()) {
            key = this.baseTestContext.getSubstitutorService().populatePayload(key,global.getDataMap());
        } else if (matcher1.find()) {
            key = matcher1.group(1);
            ArrayList<String> scenarios = global.getFeatureData().get("Scenario");
            for (int i=0; i<scenarios.size();i++) {
                if (scenarios.get(i).contains(global.getDataMap().get("scenarioName"))) {
                    key = global.getFeatureData().get(key).get(i);
                    break;
                }
            }
        } else
            System.out.println("ERROR: Value not found for: "+key);

        return key;
    }
}

