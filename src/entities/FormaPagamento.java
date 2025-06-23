package entities;

public class FormaPagamento {
	
	private int id;
	private String descricao;
	
	public FormaPagamento() {
		
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}
}
