package entities;

public enum StatusConsulta {
	AGENDADA(0),
	REALIZADA(1),
	PAGA(2),
	CANCELADA(3);
	
	private int index;
	
	private StatusConsulta(int indice) {
		this.index = indice;
	}
	
	public int getIndice() {
		return this.index;
	}
}
