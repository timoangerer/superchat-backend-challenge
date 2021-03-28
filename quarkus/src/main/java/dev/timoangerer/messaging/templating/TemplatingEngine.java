package dev.timoangerer.messaging.templating;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;

public class TemplatingEngine {

    private Map<String, String> templateVariables = new HashMap<>();

    public String renderTemplate(String template) throws IllegalArgumentException {
        StringSubstitutor stringSubstitutor = new StringSubstitutor(this.templateVariables);
        stringSubstitutor.setEnableUndefinedVariableException(true);

        String renderedTemplate = stringSubstitutor.replace(template);
        
        return renderedTemplate;
    }

    public void addVariables(Map<String, String> variables) {
        this.templateVariables.putAll(variables);
    }
}

