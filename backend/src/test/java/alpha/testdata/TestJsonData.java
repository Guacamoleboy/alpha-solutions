package alpha.testdata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public final class TestJsonData {

    // Attributes
    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    // _________________________________________________________________________________________________________________
    // Singleton

    private TestJsonData() {

    }

    // _________________________________________________________________________________________________________________

    public static String body(String fileName, String scenario, Map<String, ?> values) {
        try {
            JsonNode body = scenario(fileName, scenario).deepCopy();
            substitute(body, values);
            return MAPPER.writeValueAsString(body);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not serialize test JSON body: " + fileName, exception);
        }
    }

    // _________________________________________________________________________________________________________________

    public static String bodyFromSample(String fileName, String scenario, String sampleName,
                                        Map<String, ?> overrides) {
        JsonNode sample = sample(fileName, sampleName);
        Map<String, Object> values = MAPPER.convertValue(
                sample, new TypeReference<LinkedHashMap<String, Object>>() {});
        Object emailPrefix = values.remove("emailPrefix");
        if (emailPrefix != null) {
            values.put("email", emailPrefix + "." + UUID.randomUUID() + "@testmail.dk");
        }
        Object requestPassword = values.remove("requestPassword");
        if (requestPassword != null) {
            values.put("password", requestPassword);
        }
        values.remove("role");
        values.remove("passwordHashed");
        values.remove("phone");
        values.remove("dateOfBirth");
        values.remove("gender");
        values.remove("lastPlayed");
        values.remove("wrongPassword");
        values.putAll(overrides);
        return body(fileName, scenario, values);
    }

    // _________________________________________________________________________________________________________________

    public static JsonNode sample(String fileName, String sampleName) {
        String resource = "/testdata/data/" + fileName;
        try (InputStream stream = TestJsonData.class.getResourceAsStream(resource)) {
            if (stream == null) {
                throw new IllegalArgumentException("Missing test JSON resource: " + resource);
            }
            JsonNode sample = MAPPER.readTree(stream).path("samples").path(sampleName);
            if (sample.isMissingNode()) {
                throw new IllegalArgumentException("Missing sample '" + sampleName + "' in " + resource);
            }
            return sample;
        } catch (IOException exception) {
            throw new IllegalStateException("Could not load test JSON resource: " + resource, exception);
        }
    }

    // _________________________________________________________________________________________________________________

    private static JsonNode scenario(String fileName, String scenarioName) {
        String resource = "/testdata/data/" + fileName;
        try (InputStream stream = TestJsonData.class.getResourceAsStream(resource)) {
            if (stream == null) {
                throw new IllegalArgumentException("Missing test JSON resource: " + resource);
            }
            JsonNode scenario = MAPPER.readTree(stream).path(scenarioName);
            if (scenario.isMissingNode()) {
                throw new IllegalArgumentException("Missing scenario '" + scenarioName + "' in " + resource);
            }
            return scenario;
        } catch (IOException exception) {
            throw new IllegalStateException("Could not load test JSON resource: " + resource, exception);
        }
    }

    // _________________________________________________________________________________________________________________

    private static void substitute(JsonNode node, Map<String, ?> values) {
        if (node instanceof ObjectNode object) {
            object.fields().forEachRemaining(entry -> {
                JsonNode value = entry.getValue();
                if (value.isTextual() && value.textValue().startsWith("$")) {
                    String key = value.textValue().substring(1);
                    if (!values.containsKey(key)) {
                        throw new IllegalArgumentException("Missing test JSON value: " + key);
                    }
                    object.set(entry.getKey(), MAPPER.valueToTree(values.get(key)));
                } else {
                    substitute(value, values);
                }
            });
        } else if (node instanceof ArrayNode array) {
            array.forEach(value -> substitute(value, values));
        }
    }

}