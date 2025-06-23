package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import customExceptions.ObjetoNaoExisteException;
import entities.Medico;

public class MedicoDAO {
		
	private Connection conn;
	
	public MedicoDAO(Connection conn) {
		this.conn = conn;
	}
	
	public int cadastrar(Medico medico) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = this.conn.prepareStatement(
					"""
						INSERT INTO
							medicos(
								crm,
								nome,
								cbo,
								telefone,
								estado,
								cidade,
								bairro,
								rua,
								numero,
								duracao_consulta,
								cep
						)
					VALUES (?,?,?,?,?,?,?,?,?,?,?);
				"""
					);
			
			st.setString(1, medico.getCrm());
			st.setString(2, medico.getNome());
			st.setString(3, medico.getEspecialidade().getCbo());
			st.setString(4, medico.getTelefone());
			st.setString(5, medico.getEstado());
			st.setString(6, medico.getCidade());
			st.setString(7, medico.getBairro());
			st.setString(8, medico.getRua());
			st.setInt(9, medico.getNumero());
			st.setTime(10, medico.getDuracaoConsulta());
			st.setString(11, medico.getCep());
			
			return st.executeUpdate();
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int atualizar(Medico medico) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = this.conn.prepareStatement(
					"""
						UPDATE
							medicos
						SET
								nome = ?,
								cbo = ?,
								telefone = ?,
								estado = ?,
								cidade = ?,
								bairro = ?,
								rua = ?,
								numero = ?,
								duracao_consulta = ?,
								cep = ?
						WHERE 
							crm = ?;
				"""
					);
			
			st.setString(1, medico.getNome());
			st.setString(2, medico.getEspecialidade().getCbo());
			st.setString(3, medico.getTelefone());
			st.setString(4, medico.getEstado());
			st.setString(5, medico.getCidade());
			st.setString(6, medico.getBairro());
			st.setString(7, medico.getRua());
			st.setInt(8, medico.getNumero());
			st.setTime(9, medico.getDuracaoConsulta());
			st.setString(10, medico.getCep());
			
			st.setString(11, medico.getCrm());
			
			return st.executeUpdate();
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public Medico getByCRM(String crm) throws SQLException, ObjetoNaoExisteException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		Medico medico = null;
		try {
			
			st = this.conn.prepareStatement(
					"""
						SELECT 
							crm,
							nome,
							cbo,
							telefone,
							estado,
							cidade,
							bairro,
							rua,
							numero,
							duracao_consulta,
							cep 
						FROM 
							medicos 
						WHERE 
							crm = ?;
				"""
					);
			
			st.setString(1, crm);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				medico = new Medico();
				
				medico.setCrm(crm);
				
				medico.setNome(rs.getString("nome"));
				medico.getEspecialidade().setCbo(rs.getString("cbo"));
				medico.setTelefone(rs.getString("telefone"));
				medico.setEstado(rs.getString("estado"));
				medico.setCidade(rs.getString("cidade"));
				medico.setBairro(rs.getString("bairro"));
				medico.setRua(rs.getString("rua"));
				medico.setNumero(rs.getInt("numero"));
				medico.setDuracaoConsulta(rs.getTime("duracao_consulta"));
				medico.setCep(rs.getString("cep"));
				
				return medico;
			} else {
				throw new ObjetoNaoExisteException(medico);
			}
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Medico> getAll() throws SQLException, ObjetoNaoExisteException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Medico> listaMedicos = new ArrayList<Medico>();
		Medico medico = null;
		
		try {
			
			st = this.conn.prepareStatement(
					"""
						SELECT 
							crm,
							nome,
							cbo,
							telefone,
							estado,
							cidade,
							bairro,
							rua,
							numero,
							duracao_consulta,
							cep 
						FROM 
							medicos;
				"""
					);
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				medico = new Medico();
				
				medico.setCrm(rs.getString("crm"));
				medico.setNome(rs.getString("nome"));
				medico.getEspecialidade().setCbo(rs.getString("cbo"));
				medico.setTelefone(rs.getString("telefone"));
				medico.setEstado(rs.getString("estado"));
				medico.setCidade(rs.getString("cidade"));
				medico.setBairro(rs.getString("bairro"));
				medico.setRua(rs.getString("rua"));
				medico.setNumero(rs.getInt("numero"));
				medico.setDuracaoConsulta(rs.getTime("duracao_consulta"));
				medico.setCep(rs.getString("cep"));
				
				listaMedicos.add(medico);
			}
			
			if(listaMedicos.size()==0) {
				throw new ObjetoNaoExisteException(medico);
			}
			
			return listaMedicos;
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}	
	}
}
