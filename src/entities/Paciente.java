package entities;

import java.nio.file.Path;
import java.sql.Date;

public class Paciente {
	
	private int id;
	private String nome;
	private char sexo;
	private Path foto;
	private String telefone;
	private Date dataNascimento;
	private FormaPagamento formaPag;
	private String cep;
	private String estado;
	private String cidade;
	private String bairro;
	private String rua;
	private int numero;
	
	public Paciente() {
		this.formaPag = new FormaPagamento();
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

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public Path getFoto() {
		return foto;
	}

	public void setFoto(Path foto) {
		this.foto = foto;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public FormaPagamento getFormaPag() {
		return formaPag;
	}

	public void setFormaPag(FormaPagamento formaPag) {
		this.formaPag = formaPag;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
}
