package dev.timoangerer.messaging.templating;

import java.util.HashMap;
import java.util.Map;

public class GeneralTemplateVariables {

    public static Map<String, String> getBitcoinPrice() {
        Map<String, String> map = new HashMap<>();
        map.put("bitcoin", "42000");
        return map;
    }

    // ... other general templating variables 

    public static Map<String, String> getAllGeneralVariables() {
        Map<String, String> map = new HashMap<>();
        map.putAll(getBitcoinPrice());
        // ... other general template variables.

        return map;
    }
}
