package entities;

public enum StatusExame {
	AGENDADO(0),
	REALIZADO(1),
	PAGO(2),
	CANCELADO(3);
	
	private int index;
	
	private StatusExame(int indice) {
		this.index = indice;
	}
	
	public int getIndice() {
		return this.index;
	}
}
