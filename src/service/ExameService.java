package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import customExceptions.ObjetoNaoExisteException;
import dao.BancoDados;
import dao.ExameDAO;
import entities.Exame;
import entities.Medico;
import entities.Paciente;
import entities.TipoExame;

public class ExameService {
	public ExameService() {
		
	}
	
	public void cadastrar(Exame exame) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new ExameDAO(conn).cadastrar(exame);
	}
	
	public void reagendar(Exame exame, Timestamp novaData) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new ExameDAO(conn).reagendar(exame, novaData);
	}
	
	public void atualizar(Exame exame) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new ExameDAO(conn).atualizar(exame);
	}
	
	public Exame buscarExame(Medico medico, Paciente paciente, TipoExame tipoExame, Timestamp dataHora) throws SQLException, IOException, ObjetoNaoExisteException {
		
		Connection conn = BancoDados.conectar();
		Exame exame = new ExameDAO(conn).getExame(medico, paciente, tipoExame, dataHora);
		this.montar(exame);
		
		return exame;
	}
	
	public List<Exame> buscarExamesMedico(Medico medico, Timestamp inicioIntervalo, Timestamp finalIntervalo) throws SQLException, IOException, ObjetoNaoExisteException {
		
		Connection conn = BancoDados.conectar();
		List<Exame> listaExames = new ExameDAO(conn).getExamesMedico(medico, inicioIntervalo, finalIntervalo);
		
		for(Exame exame : listaExames) {
			this.montar(exame);
		}
		
		return listaExames;
	}
	
	public List<Exame> buscarExamesTipos(TipoExame tipoExame, Timestamp inicioIntervalo, Timestamp finalIntervalo) throws SQLException, IOException, ObjetoNaoExisteException {
		
		Connection conn = BancoDados.conectar();
		List<Exame> listaExames = new ExameDAO(conn).getExamesTipo(tipoExame, inicioIntervalo, finalIntervalo);
		
		for(Exame exame : listaExames) {
			this.montar(exame);
		}
		
		return listaExames;
	}
	
	private Exame montar(Exame exame) throws SQLException, IOException, ObjetoNaoExisteException {
		
		Medico md = new MedicoService().buscarPorCRM(exame.getMedico().getCrm());
		Paciente pc = new PacienteService().buscarPorID(exame.getPaciente().getId());
		TipoExame te = new TipoExameService().buscarPorID(exame.getTipoExame().getId());
		
		exame.setMedico(md);
		exame.setPaciente(pc);
		exame.setTipoExame(te);
		
		return exame;
	}
}
