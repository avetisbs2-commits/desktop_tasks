package ui;

import enums.FileFormat;
import model.Person;
import parser.CsvPersonParser;
import parser.JsonPersonParser;
import parser.PersonFileParser;
import parser.XmlPersonParser;
import writer.CsvPersonWriter;
import writer.JsonPersonWriter;
import writer.PersonFileWriter;
import writer.XmlPersonWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;

public class FileConverterFrame extends JFrame {

    private final ArrayList<Person> people = new ArrayList<>();

    private JLabel selectedFileLabel;
    private JLabel detectedFormatLabel;
    private JTable peopleTable;
    private DefaultTableModel tableModel;
    private JComboBox<FileFormat> outputFormatComboBox;

    public FileConverterFrame() {
        setTitle("File Converter & Editor");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createComponents();
        createLayout();
    }

    private void createComponents() {
        selectedFileLabel = new JLabel("Selected file: none");
        detectedFormatLabel = new JLabel("Detected format: none");

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "First Name", "Last Name", "Email", "Age"},
                0
        );

        peopleTable = new JTable(tableModel);
        peopleTable.setFillsViewportHeight(true);

        outputFormatComboBox = new JComboBox<>(FileFormat.values());
    }

    private void createLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("File"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton uploadButton = new JButton("Upload File");
        uploadButton.addActionListener(e -> uploadFile());

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearData());

        buttonPanel.add(uploadButton);
        buttonPanel.add(clearButton);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(selectedFileLabel);
        infoPanel.add(detectedFormatLabel);

        topPanel.add(buttonPanel, BorderLayout.WEST);
        topPanel.add(infoPanel, BorderLayout.CENTER);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("People"));
        tablePanel.add(new JScrollPane(peopleTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add Person");
        addButton.addActionListener(e -> addPerson());

        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> removeSelectedPerson());

        JButton validateButton = new JButton("Validate");
        validateButton.addActionListener(e -> validatePeople());

        actionPanel.add(addButton);
        actionPanel.add(removeButton);
        actionPanel.add(validateButton);

        JPanel exportPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton exportButton = new JButton("Export File");
        exportButton.addActionListener(e -> exportFile());

        exportPanel.add(new JLabel("Output Format:"));
        exportPanel.add(outputFormatComboBox);
        exportPanel.add(exportButton);

        bottomPanel.add(actionPanel, BorderLayout.WEST);
        bottomPanel.add(exportPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // PROVIDED: detects the format from the file extension.
    private FileFormat detectFormat(File file) {
        String name = file.getName().toLowerCase();
        if (name.endsWith(".json")) {
            return FileFormat.JSON;
        }
        if (name.endsWith(".xml")) {
            return FileFormat.XML;
        }
        if (name.endsWith(".csv")) {
            return FileFormat.CSV;
        }
        return null; // unsupported extension
    }

    // PROVIDED: the full upload flow — choose a file, detect its format,
    // pick the matching parser, and load the people into the `people` list.
    // The only thing left for you is refreshTable(): show the loaded people
    // in the JTable.
    private void uploadFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return; // user cancelled
        }

        File file = chooser.getSelectedFile();

        FileFormat format = detectFormat(file);
        if (format == null) {
            JOptionPane.showMessageDialog(this,
                    "Unsupported file type. Please choose a .json, .xml or .csv file.",
                    "Unsupported file", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            PersonFileParser parser = getParser(format);
            ArrayList<Person> loaded = parser.read(file);

            people.clear();
            people.addAll(loaded);

            selectedFileLabel.setText("Selected file: " + file.getName());
            detectedFormatLabel.setText("Detected format: " + format
                    + "  (" + people.size() + " people loaded)");

            refreshTable(); // TODO (student): map `people` into the table rows
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not read the file:\n" + ex.getMessage(),
                    "Read error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // PROVIDED: returns the parser that matches the format (polymorphism).
    private PersonFileParser getParser(FileFormat format) {
        switch (format) {
            case JSON:
                return new JsonPersonParser();
            case XML:
                return new XmlPersonParser();
            case CSV:
                return new CsvPersonParser();
            default:
                return null;
        }
    }

    private void refreshTable() {
        // TODO (student):
        // The upload has already filled the `people` ArrayList for you.
        // Map that data into the UI here:
        // 1. Clear current table rows: tableModel.setRowCount(0);
        // 2. Loop through `people`.
        // 3. For each Person add a row with:
        //    id, firstName, lastName, email, age
        //    using tableModel.addRow(new Object[]{ ... });
    }

    private void updatePeopleFromTable() {
        // TODO:
        // Read current JTable values.
        // Update the matching Person objects in people ArrayList.
    }

    private void addPerson() {
        // TODO:
        // Create a new empty/default Person.
        // Add it to people ArrayList.
        // Refresh the table.
    }

    private void removeSelectedPerson() {
        // TODO:
        // Find selected table row.
        // Remove the matching Person from people ArrayList.
        // Refresh the table.
    }

    private boolean validatePeople() {
        // TODO:
        // Validate:
        // id > 0
        // firstName is not empty
        // lastName is not empty
        // email is not empty
        // age >= 0
        // Prevent duplicate IDs.
        // Prevent duplicate emails.
        return false;
    }

    private PersonFileWriter getWriter(FileFormat format) {
        // TODO:
        // Return the correct writer based on format.
        // JSON -> JsonPersonWriter
        // XML  -> XmlPersonWriter
        // CSV  -> CsvPersonWriter
        return null;
    }

    private void exportFile() {
        // TODO:
        // Synchronize table values.
        // Validate data.
        // Get selected output format.
        // Open save dialog.
        // Get correct writer.
        // Write file.
        // Show success message.
    }

    private void clearData() {
        people.clear();
        tableModel.setRowCount(0);
        selectedFileLabel.setText("Selected file: none");
        detectedFormatLabel.setText("Detected format: none");
        JOptionPane.showMessageDialog(this, "Data cleared.");
    }
}

