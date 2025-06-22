package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import customExceptions.ObjetoNaoExisteException;
import dao.BancoDados;
import dao.FormaPagamentoDAO;
import entities.FormaPagamento;

public class FormaPagamentoService {
	
	public FormaPagamentoService() {
		
	}
	
	public void cadastrar(FormaPagamento formaPag) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new FormaPagamentoDAO(conn).cadastrar(formaPag);
	}
	
	public void atualizar(FormaPagamento formaPag) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new FormaPagamentoDAO(conn).atualizar(formaPag);
	}
	
	public FormaPagamento buscarPorID(int id) throws SQLException, IOException, ObjetoNaoExisteException {
		
		Connection conn = BancoDados.conectar();
		return new FormaPagamentoDAO(conn).getByID(id);
	}
	
	public List<FormaPagamento> buscarTudo() throws SQLException, IOException, ObjetoNaoExisteException {
		
		Connection conn = BancoDados.conectar();
		return new FormaPagamentoDAO(conn).getAll();
	}
	
	public int ProxID() throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		return new FormaPagamentoDAO(conn).getNextID();
	}
}
