# File Converter & Editor

## Goal

Complete the unfinished methods and build a desktop application that can:

```text
Upload JSON / XML / CSV
        ↓
Read file text
        ↓
Create Person objects
        ↓
Show/edit data
        ↓
Validate
        ↓
Choose output format
        ↓
Create JSON / XML / CSV file
```

The UI and project structure are already created. The important parsing, conversion, validation, and export logic is intentionally left incomplete.


## What Is Already Provided

The **conversion logic is finished for you**. You do not write any JSON / XML /
CSV parsing or formatting. These classes are complete and should not be changed:

- `parser.JsonPersonParser`, `parser.XmlPersonParser`, `parser.CsvPersonParser`
  — turn file text into an `ArrayList<Person>` (deserialize).
- `writer.JsonPersonWriter`, `writer.XmlPersonWriter`, `writer.CsvPersonWriter`
  — turn an `ArrayList<Person>` into file text (serialize).

Study their `read(...)` / `write(...)` methods and the `PersonFileParser` /
`PersonFileWriter` interfaces so you understand what you are calling.

## Student Tasks

Your job is to **wire the UI to the provided converters**. Complete only the
TODO methods in:

- `ui.FileConverterFrame`

The data flow you implement:

```text
file  --(provided parser)-->  ArrayList<Person>  -->  JTable   (upload)
JTable/input fields  -->  ArrayList<Person>  --(provided writer)-->  file   (export)
```

## Methods To Complete

### FileConverterFrame

```java
private FileFormat detectFormat(File file)
private void uploadFile()
private PersonFileParser getParser(FileFormat format)
private void refreshTable()
private void updatePeopleFromTable()
private void addPerson()
private void removeSelectedPerson()
private boolean validatePeople()
private PersonFileWriter getWriter(FileFormat format)
private void exportFile()
```

- `getParser` / `getWriter` — pick the right provided class for the `FileFormat`
  (this is the polymorphism / abstraction part).
- `uploadFile` — choose a file, detect its format, call the parser, fill the table.
- `exportFile` — read the input fields into the `people` list, validate, then call
  the writer for the format chosen in the combo box.

## How To Test

Use the example files under:

```text
examples/
```

### Test 1

Upload:

```text
people.json
```

Confirm that 3 people appear in the table.

### Test 2

Change:

```text
John
```

to:

```text
Johnny
```

Export as:

```text
CSV
```

Open the generated CSV and confirm the value changed.

### Test 3

Upload:

```text
people.csv
```

Export as:

```text
XML
```

### Test 4

Upload:

```text
people.xml
```

Export as:

```text
JSON
```

## Error Cases To Test

Students should test:

```text
Unsupported file extension
Invalid age
Duplicate ID
Duplicate email
Broken JSON
Broken XML
Invalid CSV row
Empty file
```

The application should show a readable error message instead of crashing.


