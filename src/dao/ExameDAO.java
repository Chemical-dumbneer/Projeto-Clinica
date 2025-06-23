package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import customExceptions.ObjetoNaoExisteException;
import entities.Exame;
import entities.Medico;
import entities.Paciente;
import entities.TipoExame;
import entities.StatusExame;

public class ExameDAO {
	private Connection conn;
	
	public ExameDAO(Connection conn) {
		this.conn = conn;
	}
	
	public int cadastrar(Exame exame) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			st = this.conn.prepareStatement(
					"""
						INSERT INTO
							exames(
								crm,
								id_paciente,
								id_exame,
								data_hora,
								data_hora_fim,
								valor,
								status
							)
						VALUES (?,?,?,?,?,?,?);
					"""
					);
			st.setString(1, exame.getMedico().getCrm());
			st.setInt(2, exame.getPaciente().getId());
			st.setInt(3, exame.getTipoExame().getId());
			st.setTimestamp(4, exame.getDataIni());
			st.setTimestamp(5, exame.getDataFim());
			st.setDouble(6, exame.getValor());
			st.setInt(7, exame.getStatusConsulta().getIndice());
			
			return st.executeUpdate();
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int atualizar(Exame exame) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			st = this.conn.prepareStatement(
					"""
						UPDATE 
							exames
						SET	
							data_hora_fim = ?,
							valor = ?,
							status = ?
						WHERE
							crm = ? AND
							id_paciente = ? AND
							id_exame = ? AND
							data_hora = ?;
					"""
					);
			st.setTimestamp(1, exame.getDataFim());
			st.setDouble(2, exame.getValor());
			st.setInt(3, exame.getStatusConsulta().getIndice());
			
			st.setString(4, exame.getMedico().getCrm());
			st.setInt(5, exame.getPaciente().getId());
			st.setInt(6, exame.getTipoExame().getId());
			st.setTimestamp(7, exame.getDataIni());
			
			return st.executeUpdate();
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int reagendar(Exame exame, Timestamp novaDataHora) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			st = this.conn.prepareStatement(
					"""
						UPDATE 
							exames
						SET	
							data_hora = ?
						WHERE
							crm = ? AND
							id_paciente = ? AND
							data_hora = ? AND
							id_exame = ?;
					"""
					);
			st.setTimestamp(1, novaDataHora);
			
			st.setString(2, exame.getMedico().getCrm());
			st.setInt(3, exame.getPaciente().getId());
			st.setTimestamp(4, exame.getDataIni());
			st.setInt(5, exame.getTipoExame().getId());
			
			return st.executeUpdate();
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public Exame getExame(Medico medico, Paciente paciente, TipoExame tipoExame, Timestamp dataHora) throws SQLException, ObjetoNaoExisteException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		Exame exame = new Exame();
		
		try {
			st = this.conn.prepareStatement(
					"""
					SELECT
						crm,
						id_paciente,
						data_hora,
						data_hora_fim,
						valor,
						status
					FROM
						exames
					WHERE
						crm = ? AND
						id_paciente = ? AND
						data_hora = ? AND
						id_exame = ?;
					"""
					);
			st.setString(1, medico.getCrm());
			st.setInt(2, paciente.getId());
			st.setTimestamp(3, dataHora);
			st.setInt(4, tipoExame.getId());
			
			rs = st.executeQuery();
			if(rs.next()) {
				exame.setMedico(medico);
				exame.setPaciente(paciente);
				exame.setDataIni(dataHora);
				
				exame.setDataFim(rs.getTimestamp("data_hora_fim"));
				exame.setValor(rs.getDouble("valor"));
				exame.setStatus(StatusExame.getStat(rs.getInt("status")));
			} else {
				throw new ObjetoNaoExisteException(exame);
			}
			
			return exame;
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Exame> getExamesMedico(Medico medico, Timestamp inicioIntervalo, Timestamp finalIntervalo) throws SQLException, ObjetoNaoExisteException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Exame> exames = new ArrayList<Exame>();
		
		try {
			st = this.conn.prepareStatement(
					"""
					SELECT
						crm,
						id_paciente,
						id_exame,
						data_hora,
						data_hora_fim,
						valor,
						status
					FROM
						exames
					WHERE
						crm = ? AND
						data_hora BETWEEN ? AND ?;
					"""
					);
			st.setString(1, medico.getCrm());
			st.setTimestamp(2, inicioIntervalo);
			st.setTimestamp(3, finalIntervalo);
			
			rs = st.executeQuery();
			while(rs.next()) {
				Exame exame = new Exame();
				
				exame.setMedico(medico);
				
				exame.getTipoExame().setId(rs.getInt("id_exame"));
				exame.getPaciente().setId(rs.getInt("id_paciente"));
				exame.setDataIni(rs.getTimestamp("data_hora"));
				exame.setDataFim(rs.getTimestamp("data_hora_fim"));
				exame.setValor(rs.getDouble("valor"));
				exame.setStatus(StatusExame.getStat(rs.getInt("status")));
				
				exames.add(exame);
			}
			
			return exames;
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Exame> getExamesTipo(TipoExame tipoExame, Timestamp inicioIntervalo, Timestamp finalIntervalo) throws SQLException, ObjetoNaoExisteException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Exame> exames = new ArrayList<Exame>();
		
		try {
			st = this.conn.prepareStatement(
					"""
					SELECT
						crm,
						id_paciente,
						id_exame,
						data_hora,
						data_hora_fim,
						valor,
						status
					FROM
						exames
					WHERE
						id_exame = ? AND
						data_hora BETWEEN ? AND ?;
					"""
					);
			st.setInt(1, tipoExame.getId());
			st.setTimestamp(2, inicioIntervalo);
			st.setTimestamp(3, finalIntervalo);
			
			rs = st.executeQuery();
			while(rs.next()) {
				Exame exame = new Exame();
				
				exame.setTipoExame(tipoExame);
				exame.getMedico().setCrm(rs.getString("crm"));
				exame.getPaciente().setId(rs.getInt("id_paciente"));
				exame.setDataIni(rs.getTimestamp("data_hora"));
				exame.setDataFim(rs.getTimestamp("data_hora_fim"));
				exame.setValor(rs.getDouble("valor"));
				exame.setStatus(StatusExame.getStat(rs.getInt("status")));
				
				exames.add(exame);
			}
			
			return exames;
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
}
