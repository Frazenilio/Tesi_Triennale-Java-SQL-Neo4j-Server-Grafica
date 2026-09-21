package gestioneFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import utility.FakeProposta;

public class LogProposteRepository extends JsonRepository<FakeProposta> {

	private FakePropostaRepository fakeRepo;
	
	public LogProposteRepository(String filePath) {
		
		super(filePath, new TypeReference<FakeProposta>(){});
		
		this.fakeRepo= new FakePropostaRepository(this.getFilePath());
	}
	
	@Override
	public void save(FakeProposta entities) throws IOException {
		List<FakeProposta> log = this.getFakeRepo().load(); //per fare un append mi serve tutto cio' che c'e' nel file 
		if(log==null) log=new ArrayList<FakeProposta>();
		
		log.add(entities);
		this.getFakeRepo().save(log);;		
	}
	
	public FakePropostaRepository getFakeRepo() {
		return fakeRepo;
	}

}
