package br.ufpb.minicurso.exemplo4.v2.controladores;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpb.minicurso.exemplo4.v2.dtos.SaudacaoDTO;
import br.ufpb.minicurso.exemplo4.v2.entidades.Saudacao;
import br.ufpb.minicurso.exemplo4.v2.servicos.SaudacoesService;


/*
 * Resumo da API REST
 * 
 * GET /v1/api/saudacoes
 * retorna uma saudação: saudacao (String) que é sempre "Oi" e um 
 * nome (String) que pode ser o padrão "Ser Humano" ou pode ser passado 
 * como parâmetro da requisição. 
 * 
 *  GET /v1/api/saudacoes/hora
 *  retorna uma saudação com base na hora: "Bom dia" quando está de manhã, 
 *  "Boa tarde" quando está de tarde e "Boa noite" quando está de noite. O 
 *  nome retornado pode ser o padrão "Ser Humano" ou o nome recebido no
 *  parâmetro da requisição.
 *  
 *  POST /v1/api/saudacoes/alternativa
 *  permite que uma nova saudação alternativa seja adicionada ao sistema. 
 *  Não há limite para o número de saudações alternativas que podem ser 
 *  adicionadas (e isso pode ser um problema). Não há persistência, quando
 *  o sistema reiniciar as saudações alternativas serão perdidas. Também 
 *  retorna um nome. O nome retornado pode ser o padrão "Ser Humano" ou o 
 *  nome recebido no parâmetro da requisição.
 *  
 *  GET /v1/api/saudacoes/alternativa/ultima
 *  retorna última saudação cadastrada. O nome retornado pode ser 
 *  o padrão "Ser Humano" ou o nome recebido no parâmetro da requisição.
 *  
 *  GET /v1/api/saudacoes/alternativa/{id}
 *  retorna idésima saudação cadastrada e um nome. O nome retornado pode ser 
 *  o padrão "Ser Humano" ou o nome recebido no parâmetro da requisição. Se 
 *  o id passado não corresponder a uma saudação válida o código de resposta
 *  HTTP 403 é retornado junto com uma saudação nula.
 *  
 *  GET /v1/api/saudacoes/alternativa
 *  retorna todas as saudacoes cadastradas. O nome associado a cada saudação 
 *  retornada pode ser nulo.
 *  
 *  GET /v1/api/saudacoes/alternativa/{expressao}
 *  retorna todas as saudacoes cadastradas que contem a expressao passada. O nome 
 *  associado a cada saudação retornada pode ser nulo.
 */

@RestController
public class SaudacoesController {

	@Autowired
	private SaudacoesService saudacoesService;

	@GetMapping("/saudacoes")
	public ResponseEntity<SaudacaoDTO> getSaudacao(
			@RequestParam(value = "nome", defaultValue = "Ser Humano") String nome) {
		return new ResponseEntity<SaudacaoDTO>(saudacoesService.getSaudacao(nome), HttpStatus.OK);
	}

	@GetMapping("/saudacoes/hora")
	public ResponseEntity<SaudacaoDTO> getSaudacaoTemporal(
			@RequestParam(value = "nome", defaultValue = "Ser Humano") String nome) {
		return new ResponseEntity<SaudacaoDTO>(saudacoesService.getSaudacaoTemporal(nome), HttpStatus.OK);
	}

	//tem que estar autenticado
	@PostMapping("/saudacoes/alternativa")
	public ResponseEntity<Saudacao> setNovaSaudacao(@RequestBody Saudacao novaSaudacao, @RequestHeader("Authorization") String autorizacao) {
		return new ResponseEntity<Saudacao>(saudacoesService.setNovaSaudacao(novaSaudacao, autorizacao), HttpStatus.CREATED);
	}

	@GetMapping("/saudacoes/alternativa/ultima")
	public ResponseEntity<Saudacao> getNovaSaudacao(
			@RequestParam(value = "nome", defaultValue = "Ser Humano") String nome) {
		try {
			return new ResponseEntity<Saudacao>(saudacoesService.getNovaSaudacao(nome), HttpStatus.OK);
		} catch (NoSuchElementException aiobe) {
			return new ResponseEntity<Saudacao>(new Saudacao(null, null), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/saudacoes/alternativa/{id}")
	public ResponseEntity<Saudacao> getNovaSaudacao(@PathVariable Integer id,
			@RequestParam(value = "nome", defaultValue = "Ser Humano") String nome) {
		try {
			return new ResponseEntity<Saudacao>(saudacoesService.updateNovaSaudacao(nome, id), HttpStatus.OK);
		} catch (NoSuchElementException aiobe) {
			return new ResponseEntity<Saudacao>(new Saudacao(null, null), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/saudacoes/alternativa")
	public ResponseEntity<List<Saudacao>> getTodasAsSaudacoesAlternativas() {
		return new ResponseEntity<List<Saudacao>>(saudacoesService.getSaudacoesAlternativas(), HttpStatus.OK);
	}
	
	@GetMapping("/saudacoes/alternativa/usuario")
	public ResponseEntity<List<Saudacao>> getTodasAsSaudacoesAlternativasDoUsuario(@RequestHeader("Authorization") String autorizacao) {
		return new ResponseEntity<List<Saudacao>>(saudacoesService.getSaudacoesAlternativasDoUsuario(autorizacao), HttpStatus.OK);
	}

	@GetMapping("/saudacoes/alternativa/{expressao}")
	public ResponseEntity<List<Saudacao>> getTodasAsSaudacoesAlternativas(@PathVariable String expressao) {
		return new ResponseEntity<List<Saudacao>>(saudacoesService.getSaudacoesAlternativas(expressao), HttpStatus.OK);
	}
	
	@GetMapping("/saudacoes/alternativa2/{expressao}")
	public ResponseEntity<List<Saudacao>> getTodasAsSaudacoesAlternativasComQuery(@PathVariable String expressao) {
		return new ResponseEntity<List<Saudacao>>(saudacoesService.getSaudacoesAlternativasComQuery(expressao), HttpStatus.OK);
	}


}
