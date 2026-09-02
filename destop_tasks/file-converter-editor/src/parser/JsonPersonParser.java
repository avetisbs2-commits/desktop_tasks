package parser;

import model.Person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Reads a JSON file shaped like an array of flat objects:
 *
 * [
 *   { "id": 1, "firstName": "John", "lastName": "Smith",
 *     "email": "john@test.com", "age": 25 },
 *   ...
 * ]
 *
 * PROVIDED CODE (deserialize step) — students do not need to change this.
 * It is a small hand-written scanner (no external JSON library) that only
 * needs to understand the flat "Person" objects this app produces.
 */
public class JsonPersonParser implements PersonFileParser {

    private String text;
    private int pos;

    @Override
    public ArrayList<Person> read(File file) throws Exception {
        text = readAll(file);
        pos = 0;

        ArrayList<Person> people = new ArrayList<>();

        skipWhitespace();
        expect('[');
        skipWhitespace();

        if (peek() == ']') {
            throw new Exception("The JSON file contains an empty array (no people).");
        }

        while (true) {
            HashMap<String, String> fields = parseObject();

            int id = parseInt(fields.get("id"), "id");
            String firstName = require(fields, "firstName");
            String lastName = require(fields, "lastName");
            String email = require(fields, "email");
            int age = parseInt(fields.get("age"), "age");

            people.add(new Person(id, firstName, lastName, email, age));

            skipWhitespace();
            char c = next();
            if (c == ',') {
                skipWhitespace();
                continue;
            }
            if (c == ']') {
                break;
            }
            throw new Exception("Broken JSON: expected ',' or ']' but found '" + c + "'.");
        }

        if (people.isEmpty()) {
            throw new Exception("The JSON file contains no people.");
        }

        return people;
    }

    /** Parses one { "key": value, ... } object into a key -> value map. */
    private HashMap<String, String> parseObject() throws Exception {
        HashMap<String, String> fields = new HashMap<>();

        skipWhitespace();
        expect('{');
        skipWhitespace();

        if (peek() == '}') {
            next();
            return fields;
        }

        while (true) {
            skipWhitespace();
            String key = parseString();
            skipWhitespace();
            expect(':');
            skipWhitespace();
            String value = parseValue();

            fields.put(key, value);

            skipWhitespace();
            char c = next();
            if (c == ',') {
                continue;
            }
            if (c == '}') {
                break;
            }
            throw new Exception("Broken JSON: expected ',' or '}' but found '" + c + "'.");
        }

        return fields;
    }

    /** A value is either a quoted string or a bare number. */
    private String parseValue() throws Exception {
        skipWhitespace();
        if (peek() == '"') {
            return parseString();
        }

        StringBuilder sb = new StringBuilder();
        while (pos < text.length()) {
            char c = text.charAt(pos);
            if (c == ',' || c == '}' || c == ']' || Character.isWhitespace(c)) {
                break;
            }
            sb.append(c);
            pos++;
        }
        if (sb.length() == 0) {
            throw new Exception("Broken JSON: expected a value near position " + pos + ".");
        }
        return sb.toString();
    }

    private String parseString() throws Exception {
        expect('"');
        StringBuilder sb = new StringBuilder();
        while (pos < text.length()) {
            char c = text.charAt(pos++);
            if (c == '"') {
                return sb.toString();
            }
            if (c == '\\') {
                if (pos >= text.length()) {
                    break;
                }
                char escaped = text.charAt(pos++);
                switch (escaped) {
                    case 'n': sb.append('\n'); break;
                    case 't': sb.append('\t'); break;
                    case 'r': sb.append('\r'); break;
                    case '"': sb.append('"'); break;
                    case '\\': sb.append('\\'); break;
                    case '/': sb.append('/'); break;
                    default: sb.append(escaped); break;
                }
            } else {
                sb.append(c);
            }
        }
        throw new Exception("Broken JSON: a string value is missing its closing quote.");
    }

    private String require(HashMap<String, String> fields, String key) throws Exception {
        String value = fields.get(key);
        if (value == null) {
            throw new Exception("Broken JSON: a person object is missing the '" + key + "' field.");
        }
        return value;
    }

    private int parseInt(String value, String field) throws Exception {
        if (value == null) {
            throw new Exception("Broken JSON: a person object is missing the '" + field + "' field.");
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new Exception("Invalid " + field + " '" + value + "' in JSON (must be a whole number).");
        }
    }

    private void expect(char expected) throws Exception {
        char c = next();
        if (c != expected) {
            throw new Exception("Broken JSON: expected '" + expected + "' but found '" + c + "'.");
        }
    }

    private char next() throws Exception {
        if (pos >= text.length()) {
            throw new Exception("Broken JSON: file ended earlier than expected.");
        }
        return text.charAt(pos++);
    }

    private char peek() throws Exception {
        if (pos >= text.length()) {
            throw new Exception("Broken JSON: file ended earlier than expected.");
        }
        return text.charAt(pos);
    }

    private void skipWhitespace() {
        while (pos < text.length() && Character.isWhitespace(text.charAt(pos))) {
            pos++;
        }
    }

    private String readAll(File file) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        return sb.toString();
    }
}
