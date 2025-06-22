package entities;

public class Especialidade {
	
	private String cbo;
	private String descricao;
	
	public Especialidade() {
			
	}
	
	public Especialidade(String cbo, String descricao) {
		this.cbo = cbo;
		this.descricao = descricao;
	}
	
	public void setCbo(String cbo) {
		this.cbo = cbo;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getID() {
		return this.cbo;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
}
