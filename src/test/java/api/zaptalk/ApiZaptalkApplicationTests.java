package api.zaptalk;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import api.zaptalk.model.Filme;
import io.restassured.response.Response;

public class ApiZaptalkApplicationTests {

	/* VERIFICAÇÃO DE CABEÇALHO DE RESPOSTA
	 * VALIDA SE A CHAMADA VEM COM UM STATUS HTTP 200 OK	
	 * E SE O CONTENT-TYPE É APPLICATION/JSON
	*/
	@Test
	public void todosFilmesVerificaCabecalhosResposta() {
		get("/filmes").
			then().
				statusCode(200).
			and().
			contentType(JSON);
	}
	
	/* VERIFIAÇÃO DE DADOS DA RESPOSTA
	 * VALIDA SE O JSON DE RETORNO VEM COM OS CAMPOS (DADOS E QUANTIDADES)	
	*/
	@Test
	public void todosFilmesRetornaListaFilmesEQuantidade() {
		get("/filmes").
			then().
				body("dados", notNullValue()).
				and().
				body("dados", not(empty())).
				and().
				body("quantidade",notNullValue());
	}

	/* UTILIZAÇÃO DO JSONPATH PARA A VERIFICAÇÃO DE VALORES*/
	@Test
	public void todosFilmesVerificaAtributosFilmes() {
		List<Filme> filmes = 
				get("/filmes").
				getBody().
				jsonPath().
				getList("dados", Filme.class);

		assertThat(filmes, everyItem(hasProperty("id", notNullValue())));
		assertThat(filmes, everyItem(hasProperty("nome", notNullValue())));
		assertThat(filmes, everyItem(hasProperty("sinopse", notNullValue())));
		assertThat(filmes, everyItem(hasProperty("atores", not(empty()))));
	}

	/* VERIFICAÇÃO DE CONSISTÊNCIA VALORES DOS CAMPOS */
	@Test
	public void todosFilmesVerificaQuantidadeIgualTamanhoListaRetornada() {
		Response response = 
				get("/filmes").
				then().
				extract().
				response();
		Integer quantidade = response.path("quantidade");
		List<Object> lista = response.jsonPath().getList("dados");

		assertEquals(Integer.valueOf(lista.size()), quantidade);
	}
	
	/* VERIFICAÇÃO DE ID*/
	@Test
	public void filmePorIdValido() {
		get("/filmes/1").
			then().
				statusCode(200).
				and().
				body("id", equalTo(1));
	}
	
	/* VERIFICAÇÃO DE RESPOSTA INVÁLIDA*/
	@Test
	public void filmePorIdInvalidoRetorna404() {
		get("/filmes/100").
			then().
				statusCode(404);
	}

	/* VERIFICAÇÃO DE GRAVAÇÃO DE UM NOVO ATOR */
	@Test
	public void gravaNovoAtor() {
		given().
			contentType(JSON).
			body("{\"nome\" : \"Darth Vader\"}").
			when().
				post("/atores").
					then().
						statusCode(201).log().all().and().body(equalTo("Ator criado com sucesso."));

	}

	/* VERIFICAÇÃO DE QUANTIDADE DE ATORES */
	@Test
	public void apagaAtorExistente() {
		Integer atoresAntes = 
				get("/atores").
					then().
					extract().
					response().
					path("quantidade");

		delete("/atores/{id}", 1).
			then().
				statusCode(200).
				and().
				body(equalTo("Ator removido com sucesso."));

		Integer atoresDepois = 
				get("/atores").
					then().
					extract().
					response().
					path("quantidade");
		assertTrue(atoresDepois == Integer.valueOf(atoresAntes - 1));
	}
}