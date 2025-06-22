package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import customExceptions.ObjetoNaoExisteException;
import dao.BancoDados;
import dao.EspecialidadeDAO;
import entities.Especialidade;

public class EspecialidadeService {
	
	public EspecialidadeService() {
		
	}
	
	public void cadastrar(Especialidade especialidade) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new EspecialidadeDAO(conn).cadastrar(especialidade);
	}
	
	public void atualizar(Especialidade especialidade) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new EspecialidadeDAO(conn).atualizar(especialidade);
	}
	
	public Especialidade buscarPorCBO(String cbo) throws SQLException, IOException, ObjetoNaoExisteException {
		
		Connection conn = BancoDados.conectar();
		return new EspecialidadeDAO(conn).getByCBO(cbo);
	}
	
	public List<Especialidade> buscarTudo() throws SQLException, IOException, ObjetoNaoExisteException {
		
		Connection conn = BancoDados.conectar();
		return new EspecialidadeDAO(conn).getAll();
	}
}
