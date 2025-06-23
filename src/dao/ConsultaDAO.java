package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import customExceptions.ObjetoNaoExisteException;
import entities.Consulta;
import entities.Medico;
import entities.Paciente;
import entities.StatusConsulta;

public class ConsultaDAO {
	
	private Connection conn;
	
	public ConsultaDAO(Connection conn) {
		this.conn = conn;
	}
	
	public int cadastrar(Consulta consulta) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			st = this.conn.prepareStatement(
					"""
						INSERT INTO
							consultas(
								crm,
								id_paciente,
								data_hora,
								data_hora_fim,
								valor,
								status
							)
						VALUES (?,?,?,?,?,?);
					"""
					);
			st.setString(1, consulta.getMedico().getCrm());
			st.setInt(2, consulta.getPaciente().getId());
			st.setTimestamp(3, consulta.getDataIni());
			st.setTimestamp(4, consulta.getDataFim());
			st.setDouble(5, consulta.getValor());
			st.setInt(6, consulta.getStatusConsulta().getIndice());
			
			return st.executeUpdate();
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int atualizar(Consulta consulta) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			st = this.conn.prepareStatement(
					"""
						UPDATE 
							consultas
						SET	
							data_hora_fim = ?,
							valor = ?,
							status = ?
						WHERE
							crm = ? AND
							id_paciente = ? AND
							data_hora = ?;
					"""
					);
			st.setTimestamp(1, consulta.getDataFim());
			st.setDouble(2, consulta.getValor());
			st.setInt(3, consulta.getStatusConsulta().getIndice());
			
			st.setString(4, consulta.getMedico().getCrm());
			st.setInt(5, consulta.getPaciente().getId());
			st.setTimestamp(6, consulta.getDataIni());
			
			return st.executeUpdate();
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int reagendar(Consulta consulta, Timestamp novaDataHora) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			st = this.conn.prepareStatement(
					"""
						UPDATE 
							consultas
						SET	
							data_hora = ?
						WHERE
							crm = ? AND
							id_paciente = ? AND
							data_hora = ?;
					"""
					);
			st.setTimestamp(1, novaDataHora);
			
			st.setString(2, consulta.getMedico().getCrm());
			st.setInt(3, consulta.getPaciente().getId());
			st.setTimestamp(4, consulta.getDataIni());
			
			return st.executeUpdate();
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public Consulta getConsulta(Medico medico, Paciente paciente, Timestamp dataHora) throws SQLException, ObjetoNaoExisteException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		Consulta consulta = new Consulta();
		
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
						consultas
					WHERE
						crm = ? AND
						id_paciente = ? AND
						data_hora = ?;
					"""
					);
			st.setString(1, medico.getCrm());
			st.setInt(2, paciente.getId());
			st.setTimestamp(3, dataHora);
			
			rs = st.executeQuery();
			if(rs.next()) {
				consulta.setMedico(medico);
				consulta.setPaciente(paciente);
				consulta.setDataIni(dataHora);
				
				consulta.setDataFim(rs.getTimestamp("data_hora_fim"));
				consulta.setValor(rs.getDouble("valor"));
				consulta.setStatus(StatusConsulta.getStat(rs.getInt("status")));
			} else {
				throw new ObjetoNaoExisteException(consulta);
			}
			
			return consulta;
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Consulta> getConsultasMedico(Medico medico, Timestamp inicioIntervalo, Timestamp finalIntervalo) throws SQLException, ObjetoNaoExisteException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Consulta> consultas = new ArrayList<Consulta>();
		
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
						consultas
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
				Consulta consulta = new Consulta();
				
				consulta.setMedico(medico);
				
				consulta.getPaciente().setId(rs.getInt("id_paciente"));
				consulta.setDataIni(rs.getTimestamp("data_hora"));
				consulta.setDataFim(rs.getTimestamp("data_hora_fim"));
				consulta.setValor(rs.getDouble("valor"));
				consulta.setStatus(StatusConsulta.getStat(rs.getInt("status")));
				
				consultas.add(consulta);
			}
			
			return consultas;
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
}
