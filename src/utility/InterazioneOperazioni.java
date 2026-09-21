package utility;

/**
 * Enum che contiene i valori predefiniti per le interazioni con l'utente
 */
public enum InterazioneOperazioni {
	
	NO_EVENT(-1), CONFIRM_OPERATION(1), CANCEL_OPERATION(0);
	
	private int val;

    InterazioneOperazioni(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
    
    public String getValAsString() {
    	return "" + val;
    }
}
