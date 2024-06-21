import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class JsonComparator {

    public static void main(String[] args) throws IOException {
        String json1 = "{\n  \"name\": \"John\", \n\"age\": 30, \"city\": \"New York\" }";
        String json2 = "{ \"name\": \"John\", \"age\": 30, \"city\": \"New York\" }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode tree1 = mapper.readTree(json1);
        JsonNode tree2 = mapper.readTree(json2);

        boolean areEqual = compareJsonNodes(tree1, tree2);
        System.out.println("Are JSONs equal? " + areEqual);
    }

    public static boolean compareJsonNodes(JsonNode node1, JsonNode node2) {
        if (node1.isObject() && node2.isObject()) {
            ObjectNode obj1 = (ObjectNode) node1;
            ObjectNode obj2 = (ObjectNode) node2;

            Iterator<Map.Entry<String, JsonNode>> fields1 = obj1.fields();
            while (fields1.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields1.next();
                String key = entry.getKey();
                if (!obj2.has(key)) {
                    return false;
                }
                if (!compareJsonNodes(entry.getValue(), obj2.get(key))) {
                    return false;
                }
            }
            return obj1.size() == obj2.size();
        } else if (node1.isArray() && node2.isArray()) {
            if (node1.size() != node2.size()) {
                return false;
            }
            for (int i = 0; i < node1.size(); i++) {
                if (!compareJsonNodes(node1.get(i), node2.get(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return node1.asText().equals(node2.asText());
        }
    }
}
