package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import customExceptions.ObjetoNaoExisteException;
import dao.BancoDados;
import dao.TipoExameDAO;
import entities.TipoExame;

public class TipoExameService {
	
	public TipoExameService() {
		
	}
	
	public void cadastrar(TipoExame tipoExame) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new TipoExameDAO(conn).cadastrar(tipoExame);
	}
	
	public void atualizar(TipoExame tipoExame) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new TipoExameDAO(conn).atualizar(tipoExame);
	}
	
	public TipoExame buscarPorID(int id) throws SQLException, IOException, ObjetoNaoExisteException {
		
		Connection conn = BancoDados.conectar();
		return new TipoExameDAO(conn).getByID(id);
	}
	
	public List<TipoExame> buscarTudo() throws SQLException, IOException, ObjetoNaoExisteException {
		
		Connection conn = BancoDados.conectar();
		return new TipoExameDAO(conn).getAll();
	}
	
	public int ProxID() throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		return new TipoExameDAO(conn).getNextID();
	}
}
