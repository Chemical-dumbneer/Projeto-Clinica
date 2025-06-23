package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import customExceptions.ObjetoNaoExisteException;
import entities.Especialidade;

public class EspecialidadeDAO {
	
	private Connection conn;
	
	public EspecialidadeDAO(Connection conn) {
		this.conn = conn;
	}
	
	public int cadastrar(Especialidade espec) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = this.conn.prepareStatement(
					"""
						INSERT INTO
							especialidades(
								cbo,
								descricao
							)
						VALUES (?,?);
					"""
					);
			
			st.setString(1, espec.getCbo());
			st.setString(2, espec.getDescricao());
			
			return st.executeUpdate();
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int atualizar(Especialidade espec) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = this.conn.prepareStatement(
					"""
						UPDATE
							especialidades
						SET
							descricao = ?
						WHERE
							cbo = ?;
					"""
					);
			
			st.setString(1, espec.getDescricao());
			st.setString(2, espec.getCbo());
			
			return st.executeUpdate();
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public Especialidade getByCBO(String cbo) throws SQLException, ObjetoNaoExisteException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		Especialidade espec = null;
		try {
			
			st = this.conn.prepareStatement(
					"""
						SELECT 
							cbo, 
							descricao 
						FROM 
							especialidades 
						WHERE 
							cbo = ?;
					"""
					);
			
			st.setString(1, cbo);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				espec = new Especialidade();
				
				espec.setCbo(rs.getString("cbo"));
				espec.setDescricao(rs.getString("descricao"));
				
				return espec;
			} else {
				throw new ObjetoNaoExisteException(espec);
			}
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Especialidade> getAll() throws SQLException, ObjetoNaoExisteException{
		
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Especialidade> listaEspecs = new ArrayList<Especialidade>();
		Especialidade espec = null;
		
		try {
			
			st = this.conn.prepareStatement(
					"""
						SELECT 
							cbo, 
							descricao 
						FROM 
							especialidades;
					"""
					);
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				espec = new Especialidade();
				
				espec.setCbo(rs.getString("cbo"));
				espec.setDescricao(rs.getString("descricao"));
				
				listaEspecs.add(espec);
			}
			
			if(listaEspecs.size()==0) {
				throw new ObjetoNaoExisteException(espec);
			}
			
			return listaEspecs;
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		} 	
	}
}
