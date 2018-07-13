package api.zaptalk.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import api.zaptalk.exception.ObjetoNaoEncontradoException;
import api.zaptalk.model.Ator;
import api.zaptalk.model.Filme;
import api.zaptalk.service.FilmeService;

@RestController
public class FilmeResource {

	@Autowired
	private FilmeService service;

	@RequestMapping(value = "/filmes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Map<String, Object>> filmes() {
		Map<String, Object> retorno = new HashMap<>();
		List<Filme> todosFilmes = service.todosFilmes();
		retorno.put("dados", todosFilmes);
		retorno.put("quantidade", todosFilmes.size());
		return new ResponseEntity<>(retorno, HttpStatus.OK);
	}

	@RequestMapping(value = "/filmes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Filme> filmePorId(@PathVariable Long id) {
		Filme filme = service.filme(id);
		HttpStatus status = HttpStatus.OK;
		if (filme == null) {
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<Filme>(filme, status);
	}

	@RequestMapping(value = "/atores", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Map<String, Object>> atores() {
		
		Map<String, Object> retorno = new HashMap<>();
		List<Ator> todosAtores = service.todosAtores();
		retorno.put("dados", todosAtores);
		retorno.put("quantidade", todosAtores.size());
		
		return new ResponseEntity<>(retorno, HttpStatus.OK);
	}

	@RequestMapping(value = "/atores/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Ator> atorPorId(@PathVariable Long id) {
		Ator ator = service.ator(id);
		HttpStatus status = HttpStatus.OK;
		if (ator == null) {
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<Ator>(ator, status);
	}

	@RequestMapping(value = "/atores/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> apagarAtor(@PathVariable Long id) {
		try {
			service.apagarAtor(id);
			return new ResponseEntity<>("Ator removido com sucesso.", HttpStatus.OK);
		} catch (ObjetoNaoEncontradoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/atores", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> novoAtor(@RequestBody Ator ator) {
		try {
			service.novo(ator);
			return new ResponseEntity<>("Ator criado com sucesso.", HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}