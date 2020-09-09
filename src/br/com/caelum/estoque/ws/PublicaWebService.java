package br.com.caelum.estoque.ws;

import javax.xml.ws.Endpoint;

public class PublicaWebService {

	public static void main(String[] args) {
		EstoqueWs service = new EstoqueWs();
		String url = "http://localhost:8080/estoquews";
		
		Endpoint.publish(url, service);
		
	}

}
