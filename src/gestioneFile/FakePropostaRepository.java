package gestioneFile;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import utility.FakeProposta;

public class FakePropostaRepository extends JsonRepository<List<FakeProposta>> {

	public FakePropostaRepository(String filePath) {
		super(filePath, new TypeReference<List<FakeProposta>>(){});
	}
}
