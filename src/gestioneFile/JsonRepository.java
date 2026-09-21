package gestioneFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import errori.ErroreFileNonConforme;

public class JsonRepository<T> implements Repository<T> {

    private String filePath;
    private TypeReference<T> clazz;
    
    
	public JsonRepository(String filePath, TypeReference<T> clazz) {
		super();
		this.filePath = filePath;
		this.clazz = clazz;
	}
	
	@Override
	public void save(T entities) throws IOException {
		fromEntitiesToJSON(filePath, entities);
	}

	@Override
	public T load() throws IOException {
		return fromJsonToGeneric(filePath, clazz);
	}

	public String getFilePath() {
		return filePath;
	}

	protected T fromJsonToGeneric(String file, final TypeReference<T> typeReference) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		T result = null;

		try {
			result = mapper.readValue(getJSONFromFile(file), typeReference);
		} 
		catch (Exception e) {throw new ErroreFileNonConforme(file);}

		return result;
	}
	
	protected String getJSONFromFile(String filename) throws IOException {
		String jsonText = "";
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))){		

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				jsonText += line + "\n";
			}
			bufferedReader.close();
		}
		return jsonText;
	}
	
	protected <T> void fromEntitiesToJSON(String fileName, T entities) throws IOException  {
				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(SerializationFeature.INDENT_OUTPUT);
				mapper.writeValue(createFileJson(fileName), entities);
	}

	protected static File createFileJson(String fileName) throws IOException {
		File myObj = null;
			myObj = new File(fileName);
			myObj.createNewFile();
	
		return myObj;
	}
}
