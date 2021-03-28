package dev.timoangerer.messaging.templating;

import java.util.HashMap;
import java.util.Map;

import dev.timoangerer.contact.model.Contact;

// Get template variables from specific models/classes
public class ModelTemplateVariables {
    public static Map<String, String> contactToMap(Contact contact) {
        Map<String, String> map = new HashMap<>();
        map.put("name", contact.getName());
        map.put("email", contact.getEmail());
        return map;
    }
}
