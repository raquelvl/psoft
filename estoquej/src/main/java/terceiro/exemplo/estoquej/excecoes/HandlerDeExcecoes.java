package terceiro.exemplo.estoquej.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import springfox.documentation.annotations.ApiIgnore;
import terceiro.exemplo.estoquej.dtos.DetalhesDoProblema;

@RestControllerAdvice
public class HandlerDeExcecoes {

	private static String ADICIONA_PRODUTO_URI = "https://servidor:8080/v1/api/produtos";
	private static String FALHA_DE_LOGIN_URI = "https://servidor:8080/auth/login";
	private static String GERENCIA_DE_USUARIOS_URI = "https://servidor:8080/auth/usuarios";

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

	@ExceptionHandler(LoginInvalidoException.class)
	public ResponseEntity<DetalhesDoProblema> lidaComLoginInvalidoException(LoginInvalidoException lie) {
		// System.out.println(pie.getMessage());
		DetalhesDoProblema problema = new DetalhesDoProblema();
		problema.setStatus(HttpStatus.OK.value());
		problema.setTitle(lie.getTitulo());
		problema.setType(FALHA_DE_LOGIN_URI);
		problema.setDetail(lie.getDetalhes());
		return ResponseEntity.status(HttpStatus.OK).body(problema);
	}

	@ExceptionHandler(OperacaoNaoAutorizadaException.class)
	public ResponseEntity<DetalhesDoProblema> lidaComOperacaoNaoAutorizadaException(
			OperacaoNaoAutorizadaException onae) {
		// System.out.println(pie.getMessage());
		DetalhesDoProblema problema = new DetalhesDoProblema();
		problema.setStatus(HttpStatus.FORBIDDEN.value());
		problema.setTitle(onae.getTitulo());
		problema.setType(GERENCIA_DE_USUARIOS_URI);
		problema.setDetail(onae.getDetalhes());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problema);
	}

	@ExceptionHandler(SecurityException.class)
	public ResponseEntity<DetalhesDoProblema> lidaComTokenInvalido(
			SecurityException se) {
		// System.out.println(pie.getMessage());
		DetalhesDoProblema problema = new DetalhesDoProblema();
		problema.setStatus(HttpStatus.FORBIDDEN.value());
		problema.setTitle("Token invalido, inexistente ou expirado");
		problema.setType(GERENCIA_DE_USUARIOS_URI);
		problema.setDetail("O token passado é inválido e a funcionalidade precisa de autenticacao/autorização.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}

}
