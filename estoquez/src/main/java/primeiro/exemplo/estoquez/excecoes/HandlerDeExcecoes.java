package primeiro.exemplo.estoquez.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import primeiro.exemplo.estoquez.dtos.DetalhesDoProblema;

@RestControllerAdvice
public class HandlerDeExcecoes {

	private static String ADICIONA_PRODUTO_URI = "https://servidor:8080/v1/api/produtos";

	@ExceptionHandler(ProdutoInvalidoException.class)
	public ResponseEntity<DetalhesDoProblema> lidaComProdutoInvalidoException(ProdutoInvalidoException pie) {
		// System.out.println(pie.getMessage());
		DetalhesDoProblema problema = new DetalhesDoProblema();
		problema.setStatus(HttpStatus.BAD_REQUEST.value());
		problema.setTitle(pie.getTitulo());
		problema.setType(ADICIONA_PRODUTO_URI);
		problema.setDetail(pie.getDetalhes());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}

	@ExceptionHandler(ProdutoJaExisteException.class)
	public ResponseEntity<DetalhesDoProblema> lidaComProdutoJaExisteException(ProdutoJaExisteException pjee) {
		// System.out.println(pjee.getMessage());
		DetalhesDoProblema problema = new DetalhesDoProblema();
		problema.setStatus(HttpStatus.BAD_REQUEST.value());
		problema.setTitle(pjee.getTitulo());
		problema.setType(ADICIONA_PRODUTO_URI);
		problema.setDetail(pjee.getDetalhes());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}
	
	@ExceptionHandler(CampoInvalidoException.class)
	public ResponseEntity<DetalhesDoProblema> lidaComCampoInvalidoException(CampoInvalidoException cie) {
		// System.out.println(pie.getMessage());
		DetalhesDoProblema problema = new DetalhesDoProblema();
		problema.setStatus(HttpStatus.BAD_REQUEST.value());
		problema.setTitle(cie.getTitulo());
		problema.setType(ADICIONA_PRODUTO_URI);
		problema.setDetail(cie.getDetalhes());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}
	
	@ExceptionHandler(ProdutoNaoEncontradoException.class)
	public ResponseEntity<DetalhesDoProblema> lidaComProdutoNaoEncontradoException(ProdutoNaoEncontradoException pnee) {
		// System.out.println(pie.getMessage());
		DetalhesDoProblema problema = new DetalhesDoProblema();
		problema.setStatus(HttpStatus.NOT_FOUND.value());
		problema.setTitle(pnee.getTitulo());
		problema.setType(ADICIONA_PRODUTO_URI);
		problema.setDetail(pnee.getDetalhes());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
	}
	
	@ExceptionHandler(NovoValorInvalidoException.class)
	public ResponseEntity<DetalhesDoProblema> lidaComNovoValorInvalidoException(NovoValorInvalidoException nvie) {
		// System.out.println(pie.getMessage());
		DetalhesDoProblema problema = new DetalhesDoProblema();
		problema.setStatus(HttpStatus.BAD_REQUEST.value());
		problema.setTitle(nvie.getTitulo());
		problema.setType(ADICIONA_PRODUTO_URI);
		problema.setDetail(nvie.getDetalhes());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}
}
