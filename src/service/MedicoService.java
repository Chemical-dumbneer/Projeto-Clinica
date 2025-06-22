package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import customExceptions.ObjetoNaoExisteException;
import dao.BancoDados;
import dao.MedicoDAO;
import entities.Especialidade;
import entities.Medico;

public class MedicoService {
	
	public MedicoService() {
		
	}
	
	public void cadastrar(Medico medico) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new MedicoDAO(conn).cadastrar(medico);
	}
	
	public void atualizar(Medico medico) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new MedicoDAO(conn).atualizar(medico);
	}
	
	public Medico buscarPorCRM(String crm) throws SQLException, IOException, ObjetoNaoExisteException {
		
		Connection conn = BancoDados.conectar();
		Medico md = new MedicoDAO(conn).getByCRM(crm);
		
		Especialidade esp = new EspecialidadeService().buscarPorCBO(md.getEspecialidade().getCbo());
		md.setEspecialidade(esp);
		
		return md;
	}
	
	public List<Medico> buscarTudo() throws SQLException, IOException, ObjetoNaoExisteException {
		
		Connection conn = BancoDados.conectar();
		List<Medico> medicos = new ArrayList<Medico>();
		
		medicos = new MedicoDAO(conn).getAll();
		
		for(Medico md : medicos) {
			Especialidade esp = new EspecialidadeService().buscarPorCBO(md.getEspecialidade().getCbo());
			md.setEspecialidade(esp);
		}
		
		return medicos;
	}
}
