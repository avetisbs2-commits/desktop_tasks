package parser;

import model.Person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Reads a CSV file and turns each data row into a {@link Person}.
 *
 * PROVIDED CODE (deserialize step) — students do not need to change this.
 * The file text is read here and converted into Person objects; the UI
 * only has to call this parser and show the result in the table.
 */
public class CsvPersonParser implements PersonFileParser {

    @Override
    public ArrayList<Person> read(File file) throws Exception {
        ArrayList<Person> people = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean headerSkipped = false;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                // First non-empty line is the header row (id,firstName,...).
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] columns = splitCsvLine(line);
                if (columns.length != 5) {
                    throw new Exception("Invalid CSV row at line " + lineNumber
                            + " (expected 5 columns, found " + columns.length + "): " + line);
                }

                int id = parseInt(columns[0], "id", lineNumber);
                String firstName = columns[1].trim();
                String lastName = columns[2].trim();
                String email = columns[3].trim();
                int age = parseInt(columns[4], "age", lineNumber);

                people.add(new Person(id, firstName, lastName, email, age));
            }
        }

        if (people.isEmpty()) {
            throw new Exception("The CSV file contains no people.");
        }

        return people;
    }

    private int parseInt(String value, String field, int lineNumber) throws Exception {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new Exception("Invalid " + field + " '" + value.trim()
                    + "' at line " + lineNumber + " (must be a whole number).");
        }
    }

    /**
     * Splits one CSV line by commas while respecting values wrapped in
     * double quotes (so an email or name containing a comma stays intact).
     */
    private String[] splitCsvLine(String line) {
        ArrayList<String> values = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                // A doubled quote ("") inside a quoted value means one quote.
                if (insideQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    insideQuotes = !insideQuotes;
                }
            } else if (c == ',' && !insideQuotes) {
                values.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        values.add(current.toString());

        return values.toArray(new String[0]);
    }
}
