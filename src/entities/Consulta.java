package entities;

import java.sql.Timestamp;

public class Consulta {
	
	private Medico medico;
	private Paciente paciente;
	private Timestamp dataHora;
	
	private Timestamp dataHoraFim;
	private double valor;
	private StatusConsulta status;
	
	public Consulta() {
		this.status = StatusConsulta.AGENDADA;
	}
	
	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	
	public Medico getMedico() {
		return this.medico;
	}
	
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	public Paciente getPaciente() {
		return this.paciente;
	}
	
	public void setDataIni(Timestamp data_hora) {
		this.dataHora = data_hora;
	}
	
	public Timestamp getDataIni() {
		return this.dataHora;
	}
	
	public void setDataFim(Timestamp data_hora_fim) {
		this.dataHoraFim = data_hora_fim;
	}
	
	public Timestamp getDataFim() {
		return this.dataHoraFim;
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
