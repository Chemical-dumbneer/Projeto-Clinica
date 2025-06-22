package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import customExceptions.ObjetoNaoExisteException;
import dao.BancoDados;
import dao.ConsultaDAO;
import entities.Consulta;
import entities.Medico;
import entities.Paciente;

public class ConsultaService {
	
	public ConsultaService() {
		
	}
	
	public void cadastrar(Consulta consulta) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new ConsultaDAO(conn).cadastrar(consulta);
	}
	
	public void reagendar(Consulta consulta, Timestamp novaData) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new ConsultaDAO(conn).reagendar(consulta, novaData);
	}
	
	public void atualizar(Consulta consulta) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new ConsultaDAO(conn).atualizar(consulta);
	}
	
	public Consulta buscarConsulta(Medico medico, Paciente paciente, Timestamp dataHora) throws SQLException, IOException, ObjetoNaoExisteException {
		
		Connection conn = BancoDados.conectar();
		Consulta consulta = new ConsultaDAO(conn).getConsulta(medico, paciente, dataHora);
		this.montar(consulta);
		
		return consulta;
	}
	
	public List<Consulta> buscarConsultasMedico(Medico medico, Timestamp inicioIntervalo, Timestamp finalIntervalo) throws SQLException, IOException, ObjetoNaoExisteException {
		
		Connection conn = BancoDados.conectar();
		List<Consulta> listaConsultas = new ConsultaDAO(conn).getConsultasMedico(medico, inicioIntervalo, finalIntervalo);
		
		for(Consulta consulta : listaConsultas) {
			this.montar(consulta);
		}
		
		return listaConsultas;
	}
	
	private Consulta montar(Consulta consulta) throws SQLException, IOException, ObjetoNaoExisteException {
		
		Medico md = new MedicoService().buscarPorCRM(consulta.getMedico().getCrm());
		Paciente pc = new PacienteService().buscarPorID(consulta.getPaciente().getId());
		
		consulta.setMedico(md);
		consulta.setPaciente(pc);
		
		return consulta;
	}
}
