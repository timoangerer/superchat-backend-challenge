package dev.timoangerer.messaging.templating;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeneralTemplateVariables {

    public static Map<String, String> getBitcoinPrice() {
        Map<String, String> map = new HashMap<>();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.coindesk.com/v1/bpi/currentprice/USD.json")).GET().build();
            HttpResponse<String> response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());


            JsonNode parent= new ObjectMapper().readTree(response.body());
            String price = parent.path("bpi").path("USD").path("rate").asText();
            
            map.put("bitcoin", price + "$");
            return map;
        } catch (Exception e) {
            // Do proper execption handling...
            System.out.println(e);
            return null;
        }
    }

    // ... other general templating variables

    public static Map<String, String> getAll() {
        Map<String, String> map = new HashMap<>();
        map.putAll(getBitcoinPrice());
        // ... other general template variables.

        return map;
    }
}
