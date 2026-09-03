package writer;

import model.Person;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Writes an {@link ArrayList} of {@link Person} out as a JSON array.
 *
 * PROVIDED CODE (serialize step) — students do not need to change this.
 * The produced text is the same shape JsonPersonParser reads back in.
 */
public class JsonPersonWriter implements PersonFileWriter {

    @Override
    public void write(File file, ArrayList<Person> people) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("[");
            writer.newLine();

            for (int i = 0; i < people.size(); i++) {
                Person person = people.get(i);

                writer.write("  {");
                writer.newLine();
                writer.write("    \"id\": " + person.getId() + ",");
                writer.newLine();
                writer.write("    \"firstName\": \"" + escape(person.getFirstName()) + "\",");
                writer.newLine();
                writer.write("    \"lastName\": \"" + escape(person.getLastName()) + "\",");
                writer.newLine();
                writer.write("    \"email\": \"" + escape(person.getEmail()) + "\",");
                writer.newLine();
                writer.write("    \"age\": " + person.getAge());
                writer.newLine();

                // No trailing comma after the last object in the array.
                writer.write(i < people.size() - 1 ? "  }," : "  }");
                writer.newLine();
            }

            writer.write("]");
            writer.newLine();
        }
    }

    /** Escapes the characters JSON strings are not allowed to contain raw. */
    private String escape(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
