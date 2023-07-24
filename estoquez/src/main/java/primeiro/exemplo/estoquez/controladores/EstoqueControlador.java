package primeiro.exemplo.estoquez.controladores;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import primeiro.exemplo.estoquez.dtos.ItemDeAtualizacaoDeProduto;
import primeiro.exemplo.estoquez.dtos.ProdutoDTO;
import primeiro.exemplo.estoquez.entidades.Produto;
import primeiro.exemplo.estoquez.servicos.ServicoDeEstoque;

@RestController
public class EstoqueControlador {
	@Autowired
	private ServicoDeEstoque servicoDeEstoque;

	/*
	 * buscar produto pelo nome adicionar item de compra remover item de compra
	 * atualizar quantidade de item de compra fechar pedido
	 * 
	 */

	@PostMapping("/v1/api/produtos")
	public ResponseEntity<Produto> adicionaProdutosNoEstoque(@RequestBody ProdutoDTO produto) {
		return new ResponseEntity<Produto>(servicoDeEstoque.adicionaProdutos(produto), HttpStatus.CREATED);
	}
	
	@GetMapping("/v1/api/produtos")
	public ResponseEntity<Collection<Produto>> recuperaProdutosDoEstoque(@RequestParam(value="busca", required=false) String padrao) {
		return new ResponseEntity<Collection<Produto>>(servicoDeEstoque.recuperaProdutos(Optional.ofNullable(padrao)), HttpStatus.OK);
	}
	
	@PatchMapping("/v1/api/produtos/{id}")
	public ResponseEntity<Produto> atualizaProduto(@PathVariable long id, @RequestBody ItemDeAtualizacaoDeProduto itemDeAtualizacao) {
		return new ResponseEntity<Produto>(servicoDeEstoque.atualizaProduto(id, itemDeAtualizacao), HttpStatus.OK);
		
	}
	
	@GetMapping("/v1/api/produtos/{id}")
	public ResponseEntity<Produto> recuperaProduto(@PathVariable long id) {
		return new ResponseEntity<Produto>(servicoDeEstoque.recuperaProduto(id), HttpStatus.OK);
		
	}
}
