package parser;

import model.Person;

import java.io.File;
import java.util.ArrayList;

public interface PersonFileParser {

    ArrayList<Person> read(File file) throws Exception;
}

