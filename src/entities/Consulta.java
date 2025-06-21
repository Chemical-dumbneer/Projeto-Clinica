package entities;

import java.sql.Date;

public class Consulta {
	
	private String crm;
	private int id_paciente;
	private Date data_hora;
	
	private Date data_hora_fim;
	private double valor;
	private StatusConsulta status;
	
	public Consulta() {
		this.status = StatusConsulta.AGENDADA;
	}
	
	public void setCrm(String crm) {
		this.crm = crm;
	}
	
	public String getCrm() {
		return this.crm;
	}
	
	public void setIdPaciente(int idPaciente) {
		this.id_paciente = idPaciente;
	}
	
	public int getIdPaciente() {
		return this.id_paciente;
	}
	
	public void setDataIni(Date data_hora) {
		this.data_hora = data_hora;
	}
	
	public Date getDataIni() {
		return this.data_hora;
	}
	
	public void setDataFim(Date data_hora_fim) {
		this.data_hora_fim = data_hora_fim;
	}
	
	public Date getDataFim() {
		return this.data_hora_fim;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public double getValor() {
		return this.valor;
	}
	
	public void setStatus(StatusConsulta status) {
		this.status = status;
	}
	
	public StatusConsulta getStatusConsulta() {
		return this.status;
	}
}
