package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import customExceptions.ObjetoNaoExisteException;
import entities.TipoExame;

public class TipoExameDAO {
	
	private Connection conn;
	
	public TipoExameDAO(Connection conn) {
		this.conn = conn;
	}
	
	public int cadastrar(TipoExame tipoExame) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = this.conn.prepareStatement(
					"""
						INSERT INTO
							tipo_exames(
								id_exame,
								nome,
								duracao_prev,
								orientacoes,
								valor
							)
						VALUES (?,?,?,?,?);
					"""
					);
			
			st.setInt(0, tipoExame.getId());
			st.setString(1, tipoExame.getNome());
			st.setTime(2, tipoExame.getDuracao());
			st.setString(3, tipoExame.getOrientacoes());
			st.setDouble(4, tipoExame.getValor());
			
			return st.executeUpdate();
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int atualizar(TipoExame tipoExame) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = this.conn.prepareStatement(
					"""
						UPDATE
							tipo_exames
						SET
							nome = ?,
							duracao_prev = ?,
							orientacoes = ?,
							valor = ?
						WHERE
							id_exame = ?;
					"""
					);
			
			st.setString(0, tipoExame.getNome());
			st.setTime(1, tipoExame.getDuracao());
			st.setString(2, tipoExame.getOrientacoes());
			st.setDouble(3, tipoExame.getValor());

			st.setInt(4, tipoExame.getId());
			
			return st.executeUpdate();
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int getNextID() throws SQLException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = this.conn.prepareStatement(
					"""
						SELECT
							max(id_exame) as id 
						FROM 
							tipo_exames;
					"""
					);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("id") + 1;
			} else {
				return 1;
			}
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public TipoExame getByID(int id) throws SQLException, ObjetoNaoExisteException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		TipoExame tipoExame = null;
		try {
			
			st = this.conn.prepareStatement(
					"""
						SELECT 
							id_exame,
							nome,
							duracao_prev,
							orientacoes,
							valor 
						FROM 
							tipo_exames 
						WHERE 
							id_exame = ?;
					"""
					);
			
			st.setInt(0, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				tipoExame = new TipoExame();
				
				tipoExame.setId(rs.getInt("id_exame"));
				tipoExame.setNome(rs.getString("nome"));
				tipoExame.setDuracao(rs.getTime("duracao_prev"));
				tipoExame.setOrientacoes(rs.getString("orientacoes"));
				tipoExame.setValor(rs.getDouble("valor"));
				
				return tipoExame;
			} else {
				throw new ObjetoNaoExisteException(tipoExame);
			}
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<TipoExame> getAll() throws SQLException, ObjetoNaoExisteException{
		
		PreparedStatement st = null;
		ResultSet rs = null;
		List<TipoExame> listaTipos = new ArrayList<TipoExame>();
		TipoExame tipoExame = null;
		
		try {
			
			st = this.conn.prepareStatement(
					"""
						SELECT 
							id_exame,
							nome,
							duracao_prev,
							orientacoes,
							valor 
						FROM 
							tipo_exames;
					"""
					);
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				tipoExame = new TipoExame();
				
				tipoExame.setId(rs.getInt("id_exame"));
				tipoExame.setNome(rs.getString("nome"));
				tipoExame.setDuracao(rs.getTime("duracao_prev"));
				tipoExame.setOrientacoes(rs.getString("orientacoes"));
				tipoExame.setValor(rs.getDouble("valor"));
				
				listaTipos.add(tipoExame);
			}
			
			if(listaTipos.size()==0) {
				throw new ObjetoNaoExisteException(tipoExame);
			}
			
			return listaTipos;
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}	
	}
}
