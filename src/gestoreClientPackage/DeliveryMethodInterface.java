package gestoreClientPackage;

import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import requestPackage.Request;
import responsePackage.Response;

/**
 * Interface usata per passare il metodo di consegna di {@link Request} e ricevimento {@link Response}
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public interface DeliveryMethodInterface {

	/**
	 * Metodo per mandare una {@link Request} e riceverne una {@link Response}
	 * @param <R> Tipo della {@link Response} attesa
	 * @param request {@link Request} da inviare
	 * @return {@link Response} attesa
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	<R extends Response> R deliveryRequestAndGetResponse(Request request) 
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply;
}
