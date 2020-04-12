package br.com.caelum.estoque;

import java.util.Date;

import javax.xml.ws.WebFault;

import br.com.caelum.estoque.modelo.usuario.InfoFault;

@WebFault(name = "AutorizacaoFault", messageName = "Autorizacao Fault")
public class AutorizacaoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AutorizacaoException(String mensagem) {
		super(mensagem);
	}

	public InfoFault getFaultInfo() {
		return new InfoFault("Token inválido", new Date());
	}

}
