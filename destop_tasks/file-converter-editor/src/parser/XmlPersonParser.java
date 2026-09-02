package parser;

import model.Person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Reads an XML file shaped like:
 *
 * <people>
 *   <person>
 *     <id>1</id>
 *     <firstName>John</firstName>
 *     ...
 *   </person>
 * </people>
 *
 * PROVIDED CODE (deserialize step) — students do not need to change this.
 */
public class XmlPersonParser implements PersonFileParser {

    @Override
    public ArrayList<Person> read(File file) throws Exception {
        String content = readAll(file);
        ArrayList<Person> people = new ArrayList<>();

        int searchFrom = 0;
        while (true) {
            int start = content.indexOf("<person>", searchFrom);
            if (start < 0) {
                break;
            }
            int end = content.indexOf("</person>", start);
            if (end < 0) {
                throw new Exception("Broken XML: a <person> element is missing its </person> closing tag.");
            }

            String block = content.substring(start + "<person>".length(), end);

            int id = parseInt(getTagValue(block, "id"), "id");
            String firstName = getTagValue(block, "firstName");
            String lastName = getTagValue(block, "lastName");
            String email = getTagValue(block, "email");
            int age = parseInt(getTagValue(block, "age"), "age");

            people.add(new Person(id, firstName, lastName, email, age));

            searchFrom = end + "</person>".length();
        }

        if (people.isEmpty()) {
            throw new Exception("The XML file contains no <person> elements.");
        }

        return people;
    }

    /** Returns the text between <tag> and </tag> inside the given block. */
    private String getTagValue(String block, String tag) throws Exception {
        String openTag = "<" + tag + ">";
        String closeTag = "</" + tag + ">";

        int start = block.indexOf(openTag);
        int end = block.indexOf(closeTag);
        if (start < 0 || end < 0 || end < start) {
            throw new Exception("Broken XML: missing <" + tag + "> element inside a <person>.");
        }

        return unescape(block.substring(start + openTag.length(), end).trim());
    }

    /** Reverses the five XML entity escapes produced by XmlPersonWriter. */
    private String unescape(String value) {
        return value
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&quot;", "\"")
                .replace("&apos;", "'")
                .replace("&amp;", "&");
    }

    private int parseInt(String value, String field) throws Exception {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new Exception("Invalid " + field + " '" + value + "' in XML (must be a whole number).");
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
