package utility;

/**
 * Enum per contenere i campi dei database per una migliore gestione
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public enum NomiDiEPerDatabase {

	COMPRENSORI_DB_NOMEDB("comprensori_db"),
	COMPRENSORI_DB_ID("comprensorio_id"),
	COMPRENSORI_DB_CONFIGURATOREID("configuratore_id"),
	COMPRENSORI_DB_LISTACOMUNI("lista_comuni"),
	
	CONFIGURATORI_DB_NOMEDB("configuratori_db"),
	CONFIGURATORI_DB_ID("id"),
	CONFIGURATORI_DB_USERNAME("username"),
	CONFIGURATORI_DB_PASSWORD("password"),
	CONFIGURATORI_DB_NOME("nome"),
	CONFIGURATORI_DB_COGNOME("cognome"),
	
	FRUITORI_DB_NOMEDB("fruitore_db"),
	FRUITORI_DB_ID("id"),
	FRUITORI_DB_USERNAME("username"),
	FRUITORI_DB_PASSWORD("password"),
	FRUITORI_DB_EMAIL("email"),
	FRUITORI_DB_COMPRENSORIOID("comprensorio_appartenenza_id"),
	FRUITORI_DB_NOME("nome"),
	FRUITORI_DB_COGNOME("cognome"),
	
	PROPOSTA_DB_NOMEDB("proposta_db"),
	PROPOSTA_DB_ID("proposta_id"),
	PROPOSTA_DB_RICHIESTAID("uuid_richiesta"),
	PROPOSTA_DB_OFFERTAID("uuid_offerta"),
	PROPOSTA_DB_PROPRIETARIOID("id_proprietario"),
	PROPOSTA_DB_DURATAOFFERTA("durata_offerta"),
	PROPOSTA_DB_DURATARICHIESTA("durata_richiesta"),
	PROPOSTA_DB_STATO("stato"),
	
	INSIEMICHIUSI_DB_NOMEDB("insiemi_chiusi_db"),
	INSIEMICHIUSI_DB_ID("insieme_id"),
	INSIEMICHIUSI_DB_LISTAPROPOSTE("lista_proposte"),
	INSIEMICHIUSI_DB_COMPRENSORIOID("comprensorio_id"),
	
	GERARCHIA_DB_NOMEDB("gerarchia"),
	GERARCHIA_DB_ID("uuid"),
	GERARCHIA_DB_NOMECATEGORIA("nome"),
	GERARCHIA_DB_PROPRIETARIOID("proprietario"),
	GERARCHIA_DB_CAMPO("campo"),
	GERARCHIA_DB_DOMINIO("dominio"),
	GERARCHIA_DB_FATTOREDICONVERSIONE("fdc");
	
	private String val;

	NomiDiEPerDatabase(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
