package br.com.caelum.estoque.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.ResponseWrapper;

import br.com.caelum.estoque.AutorizacaoException;
import br.com.caelum.estoque.modelo.item.Filtro;
import br.com.caelum.estoque.modelo.item.Filtros;
import br.com.caelum.estoque.modelo.item.Item;
import br.com.caelum.estoque.modelo.item.ItemDao;
import br.com.caelum.estoque.modelo.item.ItemValidador;
import br.com.caelum.estoque.modelo.item.ListaItens;
import br.com.caelum.estoque.modelo.usuario.TokenDao;
import br.com.caelum.estoque.modelo.usuario.TokenUsuario;

@WebService
public class EstoqueWS {
	private ItemDao dao = new ItemDao();

	@WebMethod(operationName = "todosOsItens")
	@ResponseWrapper(localName = "itens")
	@WebResult(name = "item")
//	@RequestWrapper(localName="listaItens")
	public ListaItens getItens(@WebParam(name = "filtros") Filtros filtros) {

		List<Filtro> lista = filtros.getLista();
		List<Item> result = dao.todosItens(lista);

		return new ListaItens(result);

	}

	@WebMethod(operationName = "cadastrarItem")
	public Item cadastrarItem(@WebParam(name = "item") Item item, @WebParam(name = "token", header = true) TokenUsuario token) throws AutorizacaoException {
		System.out.println("Cadastrar item " + item);

		if (!new TokenDao().ehValido(token)) {
			throw new AutorizacaoException("Autorizacao falhou");
		}
		
		//Lança uma exception do tipo itemvalidador.
        new ItemValidador(item).validate();


		this.dao.cadastrar(item);
		return item;
	}

}
