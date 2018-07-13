package api.zaptalk.model;

import java.util.ArrayList;
import java.util.List;

public class Filme {

	private Long id;

	private String nome;

	private List<Ator> atores = new ArrayList<>();

	private String sinopse;
	
	public Filme() {}

	public Filme(Long id, String nome, String sinopse) {
		this.id = id;
		this.nome = nome;
		this.sinopse = sinopse;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public List<Ator> getAtores() {
		return atores;
	}

	public void addAtor(Ator... atores) {
		for (Ator ator : atores) {
			this.atores.add(ator);
		}
	}

}