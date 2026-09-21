package gestioneFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Repository<T> {

	void save(T array) throws IOException;
	T load() throws FileNotFoundException, IOException;

}
