package projsoft.ufcg.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import projsoft.ufcg.entidades.Produto;
import projsoft.ufcg.servicos.ProdutosService;

@RestController
public class ProdutosController {

	private ProdutosService produtosService;

	//obrigatorio ter esse construtor, caso contrario chama um construtor
	//default e o serviço fica null
	public ProdutosController(ProdutosService produtosService) {
		super();
		this.produtosService = produtosService;
	}

	@PostMapping("/produtos")
	public ResponseEntity<Produto> adicionaProduto(@RequestBody Produto produto) {
		return new ResponseEntity<Produto>(produtosService.adicionaProduto(produto), HttpStatus.CREATED);
	}

	@GetMapping("/produtos/{id}")
	public ResponseEntity<Produto> getProduto(@PathVariable Long id) {
		Optional<Produto> produto = produtosService.getProduto(id);
		if (produto.isPresent())
			return new ResponseEntity<Produto>(produto.get(), HttpStatus.OK);
		return new ResponseEntity<Produto>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/produtos")
	public ResponseEntity<List<Produto>> getProdutos() {
		return new ResponseEntity<List<Produto>>(produtosService.getProdutos(), HttpStatus.OK);
	}

}
