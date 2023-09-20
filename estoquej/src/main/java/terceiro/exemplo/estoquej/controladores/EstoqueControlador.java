package terceiro.exemplo.estoquej.controladores;

import java.util.Collection;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import terceiro.exemplo.estoquej.dtos.ItemDeAtualizacaoDeProduto;
import terceiro.exemplo.estoquej.dtos.ProdutoDTO;
import terceiro.exemplo.estoquej.entidades.Produto;
import terceiro.exemplo.estoquej.servicos.ServicoDeEstoque;

@RestController
public class EstoqueControlador {
	@Autowired
	private ServicoDeEstoque servicoDeEstoque;

	public EstoqueControlador(ServicoDeEstoque servicoDeEstoque) {
		super();
		this.servicoDeEstoque = servicoDeEstoque;
	}

	/*
	 * buscar produto pelo nome adicionar item de compra remover item de compra
	 * atualizar quantidade de item de compra fechar pedido
	 * 
	 */

	@ApiOperation(value = "Cadastra um novo produto no estoque. Produtos não podem ser repetidos. Aqpenas usuários autenticados podem cadastrar novos produtos.")
	@RequestMapping(value = "/v1/api/produtos", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Retorna o produto adicionado, incluindo seu código"),
			@ApiResponse(responseCode = "401", description = "Usuário não tem permissão para acessar este recurso"),
			@ApiResponse(responseCode = "400", description = "Produto já existe no estoque, atualize os dados do produto que já existe")
	})
	public ResponseEntity<Produto> adicionaProdutosNoEstoque(@RequestBody ProdutoDTO produto) {
		return new ResponseEntity<Produto>(servicoDeEstoque.adicionaProdutos(produto), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/v1/api/produtos", method = RequestMethod.GET, produces="application/json")
	@ApiOperation(value = "Recupera todos os produtos já cadastrados no estoque. É possível limitar a busca para produtos que contenham um certo padrão no nome.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna os produtos cadastrados")
	})
	public ResponseEntity<Collection<Produto>> recuperaProdutosDoEstoque(
			@Parameter(name = "busca", description = "Filtra a busca por esse nome (ou parte de nome) do produto.", in = ParameterIn.QUERY)
			@RequestParam(value="busca", required=false) String padrao) {
		return new ResponseEntity<Collection<Produto>>(servicoDeEstoque.recuperaProdutos(Optional.ofNullable(padrao)), HttpStatus.OK);
	}
	
	@PatchMapping("/v1/api/produtos/{id}")
	@ApiOperation(value = "Atualiza um produto do estoque; é preciso saber o identificador do produto para atualizá-lo. " +
			"Produtos não podem ser repetidos. Aqpenas usuários autenticados podem cadastrar novos produtos.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna o produto atualizado com sucesso"),
			@ApiResponse(responseCode = "401", description = "Usuário não tem permissão para acessar este recurso"),
			@ApiResponse(responseCode = "400", description = "Produto não existe no estoque")
	})
	public ResponseEntity<Produto> atualizaProduto(@Parameter(name = "id", description = "ID do produto (numérico)", in = ParameterIn.PATH, example = "1", required = true)
													   @PathVariable long id, @RequestBody ItemDeAtualizacaoDeProduto itemDeAtualizacao) {
		return new ResponseEntity<Produto>(servicoDeEstoque.atualizaProduto(id, itemDeAtualizacao), HttpStatus.OK);
		
	}
	
	@GetMapping("/v1/api/produtos/{id}")
	public ResponseEntity<Produto> recuperaProduto(@PathVariable long id) {
		return new ResponseEntity<Produto>(servicoDeEstoque.recuperaProduto(id), HttpStatus.OK);
		
	}
}
