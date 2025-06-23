package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entities.Paciente;

public class PacienteTableModel extends AbstractTableModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final List<Paciente> pacientes;
    private final String[] colunas = {"ID", "Nome", "Data Nascimento"};

    public PacienteTableModel(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public int getRowCount() {
        return pacientes.size();
    }

    public int getColumnCount() {
        return colunas.length;
    }

    public String getColumnName(int col) {
        return colunas[col];
    }

    public Object getValueAt(int row, int col) {
        Paciente p = pacientes.get(row);
        return switch (col) {
            case 0 -> p.getId();
            case 1 -> p.getNome();
            case 2 -> p.getDataNascimento();
            default -> "";
        };
    }

    public Paciente getPacienteAt(int row) {
        return pacientes.get(row);
    }
}
