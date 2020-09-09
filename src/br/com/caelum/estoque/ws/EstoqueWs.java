package br.com.caelum.estoque.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

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
public class EstoqueWs {
	
	
	private ItemDao dao = new ItemDao();
	
	@WebMethod(operationName = "TodosOsItens") //ALTERA O NOME DA OPERATION
	@WebResult(name="itens") //ALTERA O NOME DO RETURN
	public ListaItens getItens(@WebParam(name="filtros") Filtros filtros){		
		List<Filtro> listaFiltos = filtros.getLista();
		System.out.println("Chamando getItens()");
		return new ListaItens(dao.todosItens(listaFiltos));
	}
	
	@WebMethod(operationName = "CadastrarItem") 
	@WebResult(name="item")	
	public Item cadastrarItem(
			@WebParam(name="token", header = true) TokenUsuario token, 
			@WebParam(name="item") Item item) 
					throws AutorizacaoException {
		
		System.out.println("Cadastrando item "+item);
		
		boolean valido = new TokenDao().ehValido(token);
		
		if(!valido) {
			throw new AutorizacaoException("Autorização falhou.");
		}
		
		this.dao.cadastrar(item);
		return item;
	}
	
}
