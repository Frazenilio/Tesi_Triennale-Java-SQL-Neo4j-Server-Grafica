package gestioneDatabase;

public enum FilePath {

	GERARCHIE("Progetto_Ingegneria_SW_B/src/files_JSON/Gerarchie.json"),
	CONFIGURATORI("Progetto_Ingegneria_SW_B/src/files_JSON/Configuratori.json"),
	COMPRENSORI("Progetto_Ingegneria_SW_B/src/files_JSON/Comprensori.json"),
	FRUITORI("Progetto_Ingegneria_SW_B/src/files_JSON/Fruitori.json"),
	PROPOSTE("Progetto_Ingegneria_SW_B/src/files_JSON/Proposte.json"),
	LOG_PROPOSTE("Progetto_Ingegneria_SW_B/src/files_JSON/LogProposte.json"),
	INSIEMI_CHIUSI("Progetto_Ingegneria_SW_B/src/files_JSON/InsiemiChiusi.json");

	private final String path;

	FilePath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
