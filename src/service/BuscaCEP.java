package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/*
 * essa classe inteira é uma abominação, favor não dar muita importancia. 
 * Ela apenas busca os dados de localização a partir do CEP usando uma API dos correios
 */

public class BuscaCEP {
	private String uf;
	private String cidade;
	private String bairro;
	private String rua;
	
	public BuscaCEP(String cep) throws IOException {
		
		String json = buscarCEP("01001000");
		JSONObject obj = new JSONObject(json);
		this.rua = obj.getString("logradouro");
		this.bairro = obj.getString("bairro");
		this.cidade = obj.getString("localidade");
		this.uf = obj.getString("uf");
	}
	
	@SuppressWarnings("deprecation")
	private String buscarCEP(String cep) throws IOException {
	    URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
	    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
	    conexao.setRequestMethod("GET");

	    BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
	    StringBuilder resposta = new StringBuilder();
	    String linha;
	    while ((linha = leitor.readLine()) != null) {
	        resposta.append(linha);
	    }
	    leitor.close();
	    return resposta.toString();
	}
	
	public String getEstado() {
		return this.uf;
	}
	
	public String getCidade() {
		return this.cidade;
	}
	
	public String getBairro() {
		return this.bairro;
	}
	
	public String getRua() {
		return this.rua;
	}
}

