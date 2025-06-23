package gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import customExceptions.ObjetoNaoExisteException;
import entities.Paciente;
import service.PacienteService;

public class SeletorPacienteDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField campoBusca;
    private JTable tabela;
    private Paciente pacienteSelecionado;

    public SeletorPacienteDialog(Frame dono, PacienteService service) {
        super(dono, "Selecionar Paciente", true);
        setSize(500, 400);
        setLocationRelativeTo(dono);

        campoBusca = new JTextField(30);
        JButton botaoBuscar = new JButton("Buscar");

        tabela = new JTable();
        JScrollPane scroll = new JScrollPane(tabela);

        botaoBuscar.addActionListener(e -> {
            String termo = campoBusca.getText().trim();
            List<Paciente> lista = null;
			try {
				lista = new PacienteService().buscarPorNome(termo);
				preencherTabela(lista);
			} catch (SQLException | IOException | ObjetoNaoExisteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // você cria esse método
        });

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int linha = tabela.getSelectedRow();
                    pacienteSelecionado = ((PacienteTableModel) tabela.getModel()).getPacienteAt(linha);
                    dispose();
                }
            }
        });

        JPanel painelTopo = new JPanel();
        painelTopo.add(new JLabel("Nome:"));
        painelTopo.add(campoBusca);
        painelTopo.add(botaoBuscar);

        add(painelTopo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    public Paciente getPacienteSelecionado() {
        return pacienteSelecionado;
    }

    private void preencherTabela(List<Paciente> pacientes) {
        tabela.setModel(new PacienteTableModel(pacientes));
    }
}
