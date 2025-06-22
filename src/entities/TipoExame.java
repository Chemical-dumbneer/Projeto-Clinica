package entities;

import java.sql.Time;

public class TipoExame {
	
	private int id;
	private String nome;
	private Time duracao;
	private String orientacoes;
	private double valor;
	
	public TipoExame() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Time getDuracao() {
		return duracao;
	}

	public void setDuracao(Time duracao) {
		this.duracao = duracao;
	}

	public String getOrientacoes() {
		return orientacoes;
	}

	public void setOrientacoes(String orientacoes) {
		this.orientacoes = orientacoes;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
}
