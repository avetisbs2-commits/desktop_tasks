package writer;

import model.Person;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Writes an {@link ArrayList} of {@link Person} out as XML text.
 *
 * PROVIDED CODE (serialize step) — students do not need to change this.
 */
public class XmlPersonWriter implements PersonFileWriter {

    @Override
    public void write(File file, ArrayList<Person> people) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("<people>");
            writer.newLine();

            for (Person person : people) {
                writer.write("    <person>");
                writer.newLine();
                writeTag(writer, "id", String.valueOf(person.getId()));
                writeTag(writer, "firstName", person.getFirstName());
                writeTag(writer, "lastName", person.getLastName());
                writeTag(writer, "email", person.getEmail());
                writeTag(writer, "age", String.valueOf(person.getAge()));
                writer.write("    </person>");
                writer.newLine();
            }

            writer.write("</people>");
            writer.newLine();
        }
    }

    private void writeTag(BufferedWriter writer, String tag, String value) throws Exception {
        writer.write("        <" + tag + ">" + escape(value) + "</" + tag + ">");
        writer.newLine();
    }

    /** Escapes the five XML special characters so the output stays valid. */
    private String escape(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
