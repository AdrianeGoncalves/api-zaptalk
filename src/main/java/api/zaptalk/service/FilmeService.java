package api.zaptalk.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import api.zaptalk.exception.ObjetoNaoEncontradoException;
import api.zaptalk.model.Ator;
import api.zaptalk.model.Filme;

@Service
public class FilmeService {

	private List<Filme> filmes = new ArrayList<>();

	private List<Ator> atores = new ArrayList<>();

	@PostConstruct
	public void inicializaFilmes() {
		
		//CRIA OS ATORES
		Ator carrieFisher = new Ator(1L, "Carrie Fisher(Princesa Leia)");
		Ator harrisonFord = new Ator(2L, "Harrison Ford(Han Solo)");
		Ator alecGuinness = new Ator(3L, "Alec Guinness(Obi-Wan Kenobi)");
		Ator anthonyDaniels = new Ator(4L, "Anthony Daniels(C-3PO)");
		Ator liamNeeson = new Ator(5L, "Liam Neeson( Qui-Gon Jinn)");
		Ator peterMayhew = new Ator(6L, "Peter Mayhew(Chewbacca)");
		Ator haydenChristensen = new Ator(7L, "Hayden Christensen(Darth Vader)");
		Ator kennyBaker = new Ator(8L, "Kenny Baker(R2-D2)");
		Ator nataliePortman = new Ator(9L, "Natalie Portman(Padmé)");
		Ator markHamill = new Ator(10L, "Mark Hamill(Luke Skywalker,)");
		Ator adamDriver = new Ator(11L, "Adam Driver(Kylo Ren)");
		Ator daisyRidley = new Ator(12L, "Daisy Ridley(Rey)");
		
		//ADICIONA OS ATORES
		atores.add(carrieFisher);
		atores.add(harrisonFord);
		atores.add(alecGuinness);
		atores.add(anthonyDaniels);
		atores.add(liamNeeson);
		atores.add(peterMayhew);
		atores.add(haydenChristensen);
		atores.add(kennyBaker);
		atores.add(nataliePortman);
		atores.add(markHamill);
		atores.add(adamDriver);
		atores.add(daisyRidley);
		
		//CRIA OS FILMES
		Filme umaNovaEsperanca = new Filme(1L, "Star Wars - Uma nova esperança",
				"--");
		umaNovaEsperanca.addAtor(new Ator[] { carrieFisher, harrisonFord, alecGuinness });

		Filme imperioContraAtaca = new Filme(2L, "Star Wars - O Império Contra-Ataca",
				"---");
		imperioContraAtaca.addAtor(new Ator[] { carrieFisher, harrisonFord, alecGuinness });

		Filme retornoJedi = new Filme(3L, "Star Wars - O retorno de Jedi",
				"---");
		retornoJedi.addAtor(new Ator[] { anthonyDaniels});

		Filme ameacaFantasma = new Filme(4L, "Star Wars - A Ameaça Fantasma",
				"---");
		ameacaFantasma.addAtor(liamNeeson, nataliePortman, anthonyDaniels);
		
		Filme ataqueClone = new Filme(4L, "Star Wars - Ataque dos Clones",
				"---");
		ataqueClone.addAtor(nataliePortman, anthonyDaniels);
		
		Filme vingancaSith = new Filme(4L, "Star Wars - A Vingança dos Sith",
				"---");
		vingancaSith.addAtor(haydenChristensen, nataliePortman, kennyBaker);
		
		Filme despertaForca = new Filme(4L, "Star Wars - O Despertar da Força",
				"---");
		despertaForca.addAtor(peterMayhew, harrisonFord, anthonyDaniels);
		
		Filme rogueOne = new Filme(4L, "Star Wars - Rogue One",
				"---");
		rogueOne.addAtor(anthonyDaniels);
		
		Filme ultimoJedi = new Filme(4L, "Star Wars - Os Últimos Jedi",
				"---");
		ultimoJedi.addAtor(markHamill, adamDriver, daisyRidley);
		
		//ADICIONA OS FILME
		filmes.add(umaNovaEsperanca);
		filmes.add(imperioContraAtaca);
		filmes.add(retornoJedi);
		filmes.add(ameacaFantasma);
		filmes.add(ataqueClone);
		filmes.add(vingancaSith);
		filmes.add(despertaForca);
		filmes.add(rogueOne);
		filmes.add(ultimoJedi);
	}
	
	// RETORNA A LISTA DE TODOS OS FILMES
	public List<Filme> todosFilmes() {
		return filmes;
	}

	// RETORNA A LISTA DE TODOS OS ATORES
	public List<Ator> todosAtores() {
		return atores;
	}

	// BUSCA UM FILME POR ID
	public Filme filme(Long id) {
		return filmes.stream().filter(filme -> id.equals(filme.getId())).findAny().orElse(null);
	}

	// BUSCA UM ATOR POR ID
	public Ator ator(Long id) {
		return atores.stream().filter(filme -> id.equals(filme.getId())).findAny().orElse(null);
	}
	
	// CRIA NOVO ATOR
	public void novo(Ator novo) {
		if (StringUtils.isEmpty(novo.getNome())) {
			throw new IllegalArgumentException("É necessário informar o nome do ator.");
		}

		Long quantidade = (long) atores.size();
		novo.setId(quantidade + 1);

		atores.add(novo);
	}
	
	// DELETA ATOR
	public void apagarAtor(Long id) {
		Ator ator = ator(id);
		if (ator != null) {
			atores.remove(ator);
		} else {
			throw new ObjetoNaoEncontradoException("Não é possível remover o ator pois ele não existe.");
		}
	}
}
