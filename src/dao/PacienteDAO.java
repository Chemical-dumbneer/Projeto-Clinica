package dao;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import customExceptions.ObjetoNaoExisteException;
import entities.Paciente;

public class PacienteDAO {
	
	private Connection conn;
	
	public PacienteDAO(Connection conn) {
		this.conn = conn;
	}
	
	public int cadastrar(Paciente paciente) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = this.conn.prepareStatement(
					"""
						INSERT INTO
							pacientes(
								id_paciente,
								nome,
								sexo,
								foto,
								telefone,
								data_nascimento,
								id_forma_pag,
								estado,
								cidade,
								bairro,
								rua,
								numero,
								cep
							)
						VALUES (?,?,?,?,?,?,?,?,?,?,?,?);
					"""
					);
			
			st.setInt(0, paciente.getId());
			st.setString(1, paciente.getNome());
			st.setString(2, String.valueOf(paciente.getSexo()));
			st.setString(3, paciente.getFoto().toString().replace(File.separatorChar, '/'));
			st.setString(4, paciente.getTelefone());
			st.setDate(5, paciente.getDataNascimento());
			st.setInt(6, paciente.getFormaPag().getID());
			st.setString(7, paciente.getEstado());
			st.setString(8, paciente.getCidade());
			st.setString(9, paciente.getBairro());
			st.setString(10, paciente.getRua());
			st.setInt(11, paciente.getNumero());
			st.setString(12, paciente.getCep());
			
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
							max(id_paciente) as id 
						FROM 
							pacientes;
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
	
	public int atualizar(Paciente paciente) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = this.conn.prepareStatement(
					"""
						UPDATE
							pacientes
						SET
							nome = ?,
							sexo = ?,
							foto = ?,
							telefone = ?,
							data_nascimento = ?,
							id_forma_pag = ?,
							estado = ?,
							cidade = ?,
							bairro = ?,
							rua = ?,
							numero = ?,
							cep = ?
						WHERE
							id_paciente = ?;
					"""
					);
			
			st.setString(0, paciente.getNome());
			st.setString(1, String.valueOf(paciente.getSexo()));
			st.setString(2, paciente.getFoto().toString().replace(File.separatorChar, '/'));
			st.setString(3, paciente.getTelefone());
			st.setDate(4, paciente.getDataNascimento());
			st.setInt(5, paciente.getFormaPag().getID());
			st.setString(6, paciente.getEstado());
			st.setString(7, paciente.getCidade());
			st.setString(8, paciente.getBairro());
			st.setString(9, paciente.getRua());
			st.setInt(10, paciente.getNumero());
			st.setString(11, paciente.getCep());
			
			st.setInt(12, paciente.getId());
			
			return st.executeUpdate();
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public Paciente getByID(int id) throws SQLException, ObjetoNaoExisteException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		Paciente paciente = null;
		try {
			
			st = this.conn.prepareStatement(
					"""
						SELECT 
							id_paciente,
							nome,
							sexo,
							foto,
							telefone,
							data_nascimento,
							id_forma_pag,
							estado,
							cidade,
							bairro,
							rua,
							numero,
							cep
						FROM 
							pacientes 
						WHERE 
							id_paciente = ?;
					"""
					);
			
			st.setInt(0, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				paciente = new Paciente();
				
				paciente.setId(id);
				
				paciente.setNome(rs.getString("nome"));
				paciente.setSexo(rs.getString("sexo").charAt(0));
				paciente.setFoto(Paths.get(rs.getString("foto")));
				paciente.setTelefone(rs.getString("telefone"));
				paciente.setDataNascimento(rs.getDate("data_nascimento"));
				paciente.getFormaPag().setID(rs.getInt("id_forma_pag"));
				paciente.setEstado(rs.getString("estado"));
				paciente.setCidade(rs.getString("cidade"));
				paciente.setBairro(rs.getString("bairro"));
				paciente.setRua(rs.getString("rua"));
				paciente.setNumero(rs.getInt("numero"));
				paciente.setCep(rs.getString("cep"));
				
				return paciente;
			} else {
				throw new ObjetoNaoExisteException(paciente);
			}
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Paciente> getAll() throws SQLException, ObjetoNaoExisteException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Paciente> listaPacientes = new ArrayList<Paciente>();
		Paciente paciente = null;
		
		try {
			
			st = this.conn.prepareStatement(
					"""
						SELECT 
							id_paciente,
							nome,
							sexo,
							foto,
							telefone,
							data_nascimento,
							id_forma_pag,
							estado,
							cidade,
							bairro,
							rua,
							numero,
							cep
						FROM 
							pacientes
					"""
					);
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				paciente = new Paciente();
				
				paciente.setId(rs.getInt("id_paciente"));
				paciente.setNome(rs.getString("nome"));
				paciente.setSexo(rs.getString("sexo").charAt(0));
				paciente.setFoto(Paths.get(rs.getString("foto")));
				paciente.setTelefone(rs.getString("telefone"));
				paciente.setDataNascimento(rs.getDate("data_nascimento"));
				paciente.getFormaPag().setID(rs.getInt("id_forma_pag"));
				paciente.setEstado(rs.getString("estado"));
				paciente.setCidade(rs.getString("cidade"));
				paciente.setBairro(rs.getString("bairro"));
				paciente.setRua(rs.getString("rua"));
				paciente.setNumero(rs.getInt("numero"));
				paciente.setCep(rs.getString("cep"));
				
				listaPacientes.add(paciente);
			}
			
			if(listaPacientes.size()==0) {
				throw new ObjetoNaoExisteException(paciente);
			}
			
			return listaPacientes;
			
		} finally {
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}	
	}
}
