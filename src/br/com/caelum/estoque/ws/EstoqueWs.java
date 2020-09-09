package br.com.caelum.estoque.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import br.com.caelum.estoque.modelo.item.Filtro;
import br.com.caelum.estoque.modelo.item.Filtros;
import br.com.caelum.estoque.modelo.item.Item;
import br.com.caelum.estoque.modelo.item.ItemDao;
import br.com.caelum.estoque.modelo.item.ListaItens;
import br.com.caelum.estoque.modelo.usuario.AutorizacaoException;
import br.com.caelum.estoque.modelo.usuario.TokenDao;
import br.com.caelum.estoque.modelo.usuario.TokenUsuario;
import br.com.caelum.estoque.modelo.usuario.Usuario;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
/*No caso de ParameterStyle.BARE, o m�todo precisa ser especificado na anota��o @WebMethod no parametro action
quando h� m�todos com mesma assinatura. Wrapped e Bare s�o configura��es utilizadas pelo JAX-WS em conjunto com 
Document para dizer que queremos embrulhar a mensagem ou n�o.
O estilo encoded deve ser evitado devido a problemas de compatibilidade, valida��o e desempenho*/

public class EstoqueWs {

	private ItemDao dao = new ItemDao();

	@WebMethod(operationName = "TodosOsItens") // ALTERA O NOME DA OPERATION
	@WebResult(name = "itens") // ALTERA O NOME DO RETURN
	public ListaItens getItens(@WebParam(name = "filtros") Filtros filtros) {
		List<Filtro> listaFiltos = filtros.getLista();
		System.out.println("Chamando getItens()");
		return new ListaItens(dao.todosItens(listaFiltos));
	}

	@WebMethod(action = "CadastrarItem", operationName = "CadastrarItem")
	@WebResult(name = "item")
	public Item cadastrarItem(@WebParam(name = "token", header = true) TokenUsuario token,
			@WebParam(name = "item") Item item) throws AutorizacaoException {

		System.out.println("Cadastrando item " + item);

		boolean valido = new TokenDao().ehValido(token);

		if (!valido) {
			throw new AutorizacaoException("Autoriza��o falhou.");
		}

		this.dao.cadastrar(item);
		return item;
	}

	
	@WebMethod(operationName = "CadastrarItem2") 
	@WebResult(name="item")	
	public Item cadastrarItem2(
			@WebParam(name="token", header = true) TokenUsuario token, 
			@WebParam(name="item") Item item) 
					throws AutorizacaoException {
			return item;
	}
}
