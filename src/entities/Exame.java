package entities;

import java.sql.Timestamp;

public class Exame {
	
	private Medico medico;
	private Paciente paciente;
	private Timestamp dataHora;
	private TipoExame tipoExame;
	
	private Timestamp dataHoraFim;
	private double valor;
	private StatusExame status;
	
	public Exame() {
		this.status = StatusExame.AGENDADO;
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
	
	public void setTipoExame(TipoExame tipoExame) {
		this.tipoExame = tipoExame;
	}
	
	public TipoExame getTipoExame() {
		return this.tipoExame;
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
	
	public void setStatus(StatusExame status) {
		this.status = status;
	}
	
	public StatusExame getStatusConsulta() {
		return this.status;
	}
}
