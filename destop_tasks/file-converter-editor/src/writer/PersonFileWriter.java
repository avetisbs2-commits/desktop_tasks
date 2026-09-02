package writer;

import model.Person;

import java.io.File;
import java.util.ArrayList;

public interface PersonFileWriter {

    void write(File file, ArrayList<Person> people) throws Exception;
}

