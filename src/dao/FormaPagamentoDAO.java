package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import customExceptions.ObjetoNaoExisteException;
import entities.FormaPagamento;

public class FormaPagamentoDAO {
	
	private Connection conn;
	
	public FormaPagamentoDAO(Connection conn) {
		this.conn = conn;
	}
	
	public int cadastrar(FormaPagamento fpag) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = this.conn.prepareStatement(
					"""
						INSERT INTO
							formas_pagamentos(
								id_forma_pag,
								descricao
							)
						VALUES (?,?);
					"""
					);
			
			st.setInt(1, fpag.getID());
			st.setString(2, fpag.getDescricao());
			
			return st.executeUpdate();
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int atualizar(FormaPagamento fpag) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = this.conn.prepareStatement(
					"""
						UPDATE
							formas_pagamentos
						SET
							descricao = ?
						WHERE
							id_forma_pag = ?;
					"""
					);
			
			st.setString(1, fpag.getDescricao());
			st.setInt(2, fpag.getID());
			
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
							max(id_forma_pag) as id 
						FROM 
							formas_pagamentos;
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
	
	public FormaPagamento getByID(int id) throws SQLException, ObjetoNaoExisteException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		FormaPagamento fpag = null;
		try {
			
			st = this.conn.prepareStatement(
					"""
						SELECT 
							id_forma_pag, 
							descricao 
						FROM 
							formas_pagamentos 
						WHERE 
							id_forma_pag = ?;
					"""
					);
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				fpag = new FormaPagamento();
				
				fpag.setID(rs.getInt("id_forma_pag"));
				fpag.setDescricao(rs.getString("descricao"));
				
				return fpag;
			} else {
				throw new ObjetoNaoExisteException(fpag);
			}
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<FormaPagamento> getAll() throws SQLException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		List<FormaPagamento> listaFormas = new ArrayList<FormaPagamento>();
		FormaPagamento fpag = null;
		
		try {
			
			st = this.conn.prepareStatement(
					"""
						SELECT 
							id_forma_pag, 
							descricao 
						FROM 
							formas_pagamentos;
					"""
					);
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				fpag = new FormaPagamento();
				
				fpag.setID(rs.getInt("id_forma_pag"));
				fpag.setDescricao(rs.getString("descricao"));
				
				listaFormas.add(fpag);
			}
			
			return listaFormas;
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		} 	
	}
}
