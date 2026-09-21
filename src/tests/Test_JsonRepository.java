package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;

import gestioneFile.JsonRepository;
import utility.Tupla;

class Test_JsonRepository {

	@Test
	void test_load() throws IOException {
		JsonRepository<Tupla<Integer,String>> repo = new JsonRepository<Tupla<Integer,String>>(
				"Progetto_Ingegneria_SW_B/src/tests/JsonRepositoryLoadFileTest.json", 
				new TypeReference<Tupla<Integer,String>>(){});
		
		Tupla<Integer,String> t = new Tupla<Integer,String>(1,"ciao");
		Tupla<Integer,String> loaded= repo.load();
		
		assertEquals(t.getFirst(),(loaded.getFirst()));
		assertEquals(t.getSecond(),(loaded.getSecond()));

	}

	@Test
	void test_save() throws IOException {
		JsonRepository<Tupla<Integer,String>> repo = new JsonRepository<Tupla<Integer,String>>(
				"Progetto_Ingegneria_SW_B/src/tests/JsonRepositorySaveFileTest.json", 
				new TypeReference<Tupla<Integer,String>>(){});
		
		Tupla<Integer,String> t = new Tupla<Integer,String>(5,"matteo");

		repo.save(t);
		Tupla<Integer,String> loaded= repo.load();

		assertEquals(t.getFirst(),(loaded.getFirst()));
		assertEquals(t.getSecond(),(loaded.getSecond()));
	}
}
