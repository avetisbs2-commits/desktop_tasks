package writer;

import model.Person;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Writes an {@link ArrayList} of {@link Person} out as CSV text.
 *
 * PROVIDED CODE (serialize step) — students do not need to change this.
 * The UI collects the edited data from the input fields / table into the
 * people list and simply hands it to this writer for the chosen format.
 */
public class CsvPersonWriter implements PersonFileWriter {

    @Override
    public void write(File file, ArrayList<Person> people) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("id,firstName,lastName,email,age");
            writer.newLine();

            for (Person person : people) {
                writer.write(escape(String.valueOf(person.getId())));
                writer.write(",");
                writer.write(escape(person.getFirstName()));
                writer.write(",");
                writer.write(escape(person.getLastName()));
                writer.write(",");
                writer.write(escape(person.getEmail()));
                writer.write(",");
                writer.write(escape(String.valueOf(person.getAge())));
                writer.newLine();
            }
        }
    }

    /**
     * Wraps a value in double quotes when it contains a comma, quote or
     * newline, so the CSV stays readable back in {@code CsvPersonParser}.
     */
    private String escape(String value) {
        if (value == null) {
            return "";
        }
        boolean needsQuotes = value.contains(",") || value.contains("\"")
                || value.contains("\n") || value.contains("\r");
        if (!needsQuotes) {
            return value;
        }
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
