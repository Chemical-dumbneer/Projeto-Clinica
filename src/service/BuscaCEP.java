package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.json.JSONObject;

/*
 * essa classe inteira é uma abominação, favor não dar muita importancia. 
 * Ela apenas busca os dados de localização a partir do CEP usando uma API dos correios, ah e faz isso assíncronamente. Não me pergunte pq.
 */

public class BuscaCEP extends Thread {
	private String cep;
	
	private JComboBox<String> cbEstado;
	private JTextField txtCidade;
	private JTextField txtBairro;
	private JTextField txtRua;
	private JLabel gif;
	
	public BuscaCEP(String cep, JComboBox<String> cbEstado, JTextField txtCidade, JTextField txtBairro, JTextField txtRua, JLabel gif) {
		
		this.cep = cep;
		this.cbEstado = cbEstado;
		this.txtCidade = txtCidade;
		this.txtBairro = txtBairro;
		this.txtRua = txtRua;
		this.gif = gif;
	}
	
	public void run() {
		this.gif.setVisible(true);
		String json;
		try {
			System.out.println("Buscando dados do CEP: " + this.cep);
			json = buscarCEP(this.cep);
			System.out.println("Resposta: " + json);
			JSONObject obj = new JSONObject(json);
			this.txtRua.setText(obj.getString("logradouro"));
			this.txtRua.setEnabled(false);
			
			this.txtBairro.setText(obj.getString("bairro"));
			this.txtBairro.setEnabled(false);
			
			this.txtCidade.setText(obj.getString("localidade"));
			this.txtCidade.setEnabled(false);
			
			this.cbEstado.setSelectedItem(obj.getString("uf"));
			this.cbEstado.setEnabled(false);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao buscar o CEP", JOptionPane.ERROR_MESSAGE);
		} finally {
			this.gif.setVisible(false);
		}
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
}

