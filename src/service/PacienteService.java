package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import customExceptions.ObjetoNaoExisteException;
import dao.BancoDados;
import dao.PacienteDAO;
import entities.FormaPagamento;
import entities.Paciente;

public class PacienteService {
	
	public PacienteService() {
		
	}
	
	public void cadastrar(Paciente paciente) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new PacienteDAO(conn).cadastrar(paciente);
	}
	
	public void atualizar(Paciente paciente) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new PacienteDAO(conn).atualizar(paciente);
	}
	
	public Paciente buscarPorID(int id) throws SQLException, IOException, ObjetoNaoExisteException {
		
		Connection conn = BancoDados.conectar();
		Paciente pc = new PacienteDAO(conn).getByID(id);
		
		FormaPagamento fpag = new FormaPagamentoService().buscarPorID(pc.getFormaPag().getID());
		pc.setFormaPag(fpag);
		
		return pc;
	}
	
	public List<Paciente> buscarTudo() throws SQLException, IOException, ObjetoNaoExisteException {
		
		Connection conn = BancoDados.conectar();
		List<Paciente> pacientes = new ArrayList<Paciente>();
		
		pacientes = new PacienteDAO(conn).getAll();
		
		for(Paciente pc : pacientes) {
			FormaPagamento fpag = new FormaPagamentoService().buscarPorID(pc.getFormaPag().getID());
			pc.setFormaPag(fpag);
		}
		
		return pacientes;
	}
	
	public List<Paciente> buscarPorNome(String trechoNome) throws SQLException, IOException, ObjetoNaoExisteException {
		
		Connection conn = BancoDados.conectar();
		List<Paciente> pacientes = new ArrayList<Paciente>();
		
		pacientes = new PacienteDAO(conn).getByName(trechoNome);
		
		for(Paciente pc : pacientes) {
			FormaPagamento fpag = new FormaPagamentoService().buscarPorID(pc.getFormaPag().getID());
			pc.setFormaPag(fpag);
		}
		
		return pacientes;
	}
	
	public int ProxID() throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		return new PacienteDAO(conn).getNextID();
	}
}
