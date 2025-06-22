package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import java.awt.Window.Type;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class EcraPrincipal {

	private JFrame frmSistemaDeGesto;
	public JDesktopPane janelaPrincipal;
	public JMenuItem mntmAddPaciente;
	public JMenuItem mntmFormasPag;
	public JMenuItem mntmMedicos;
	public JMenuItem mntmEspecialidade;
	public JMenuItem mntmTipoExames;
	public JMenuItem mntmNovaConsulta;
	public JMenuItem mntmEditarConsulta;
	public JMenuItem mntmNovoExame;
	public JMenuItem mntmEditarExames;
	public JMenuItem mntmRelAgendaMedico;
	public JMenuItem mntmRelAgendaExames;
	public JMenuItem mntmRelHistoricoPaciente;
	public JMenuItem mntmMostrarSobre;
	private JButton btnLogoff;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
							UIManager.getSystemLookAndFeelClassName()
							);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					EcraPrincipal window = new EcraPrincipal();
					window.frmSistemaDeGesto.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Métodos de inserção de janelas
	 */
	public void abrirAgendaConsulta() {
		AgendaConsulta agendaConsulta = new AgendaConsulta(this);
		this.mntmRelAgendaMedico.setEnabled(false);
		this.janelaPrincipal.add(agendaConsulta);
	}

	public void abrirAgendaExame() {
		AgendaExame agendaExame = new AgendaExame(this);
		this.mntmRelAgendaExames.setEnabled(false);
		this.janelaPrincipal.add(agendaExame);
	}

	public void abrirEditConsulta() {
		EditConsulta editConsulta = new EditConsulta(this);
		this.mntmEditarConsulta.setEnabled(false);
		this.janelaPrincipal.add(editConsulta);
	}

	public void abrirEditEspecialidade() {
		EditEspecialidade editEspecialidade = new EditEspecialidade(this);
		this.mntmEspecialidade.setEnabled(false);
		this.janelaPrincipal.add(editEspecialidade);
	}

	public void abrirEditExame() {
		EditExame editExame = new EditExame(this);
		this.mntmEditarExames.setEnabled(false);
		this.janelaPrincipal.add(editExame);
	}

	public void abrirEditFormaPag() {
		EditFormaPag editFormaPag = new EditFormaPag(this);
		this.mntmFormasPag.setEnabled(false);
		this.janelaPrincipal.add(editFormaPag);
	}

	public void abrirEditMedico() {
		EditMedico editMedico = new EditMedico(this);
		this.mntmMedicos.setEnabled(false);
		this.janelaPrincipal.add(editMedico);
	}

	public void abrirEditPaciente() {
		EditPaciente editPaciente = new EditPaciente(this);
		this.mntmAddPaciente.setEnabled(false);
		editPaciente.setVisible(true);
		try {
			editPaciente.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.janelaPrincipal.add(editPaciente);
	}

	public void abrirEditTipoExame() {
		EditTipoExame editTipoExame = new EditTipoExame(this);
		this.mntmTipoExames.setEnabled(false);
		this.janelaPrincipal.add(editTipoExame);
	}

	/**
	 * Create the application.
	 */
	public EcraPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSistemaDeGesto = new JFrame();
		frmSistemaDeGesto.setTitle("Sistema de Gestão de Clinicas");
		frmSistemaDeGesto.setType(Type.POPUP);
		frmSistemaDeGesto.setBounds(100, 100, 450, 300);
		frmSistemaDeGesto.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frmSistemaDeGesto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSistemaDeGesto.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane divJanelaPrincipal = new JSplitPane();
		frmSistemaDeGesto.getContentPane().add(divJanelaPrincipal, BorderLayout.WEST);
		
		JSplitPane divAbaLAteral = new JSplitPane();
		divAbaLAteral.setResizeWeight(1.0);
		divJanelaPrincipal.setLeftComponent(divAbaLAteral);
		divAbaLAteral.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		JPanel panel = new JPanel();
		panel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel.setMaximumSize(new Dimension(32767, 60));
		divAbaLAteral.setRightComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel_2.setMaximumSize(new Dimension(32767, 30));
		panel.add(panel_2, BorderLayout.NORTH);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_1, BorderLayout.NORTH);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_2, BorderLayout.SOUTH);
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel_5.add(panel_1);
		panel_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel_1.setMaximumSize(new Dimension(32767, 30));
		
		JLabel lblUserLabel = new JLabel("Usuário:");
		lblUserLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel_1.add(lblUserLabel);
		
		JLabel lblUserName = new JLabel("[nome usuario]");
		lblUserName.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel_1.add(lblUserName);
		
		btnLogoff = new JButton("Trocar Usuário");
		panel_5.add(btnLogoff);
		btnLogoff.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogoff.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_2, BorderLayout.EAST);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_3, BorderLayout.WEST);
		
		JPanel panel_3 = new JPanel();
		divAbaLAteral.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel_3.add(verticalStrut, BorderLayout.NORTH);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut, BorderLayout.WEST);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut_1, BorderLayout.EAST);
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		JLabel lblNotificacoes = new JLabel("Notificações");
		lblNotificacoes.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_4.add(lblNotificacoes);
		
		JPanel visaoUsuario = new JPanel();
		divJanelaPrincipal.setRightComponent(visaoUsuario);
		visaoUsuario.setLayout(new BoxLayout(visaoUsuario, BoxLayout.Y_AXIS));
		
		janelaPrincipal = new JDesktopPane();
		frmSistemaDeGesto.getContentPane().add(janelaPrincipal, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		frmSistemaDeGesto.setJMenuBar(menuBar);
		
		JMenu mnMenuCadastros = new JMenu("Cadastro");
		menuBar.add(mnMenuCadastros);
		
		JMenu mnSubMenuPacientes = new JMenu("Pacientes");
		mnMenuCadastros.add(mnSubMenuPacientes);
		
		mntmAddPaciente = new JMenuItem("Adicionar/Editar");
		mntmAddPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirEditPaciente();
			}
		});
		mnSubMenuPacientes.add(mntmAddPaciente);
		
		mntmFormasPag = new JMenuItem("Formas de Pagamento");
		mnSubMenuPacientes.add(mntmFormasPag);
		
		JMenu mnSubMenuMedicos = new JMenu("Medicos");
		mnMenuCadastros.add(mnSubMenuMedicos);
		
		mntmMedicos = new JMenuItem("Adicionar/Editar");
		mnSubMenuMedicos.add(mntmMedicos);
		
		mntmEspecialidade = new JMenuItem("Especialidades");
		mnSubMenuMedicos.add(mntmEspecialidade);
		
		JMenu mnSubMenuExames = new JMenu("Exames");
		mnMenuCadastros.add(mnSubMenuExames);
		
		mntmTipoExames = new JMenuItem("Adicionar/Editar");
		mnSubMenuExames.add(mntmTipoExames);
		
		JMenu mnMenuAgenda = new JMenu("Agenda");
		menuBar.add(mnMenuAgenda);
		
		JMenu mnSubMenuConsulta = new JMenu("Consultas");
		mnMenuAgenda.add(mnSubMenuConsulta);
		
		mntmNovaConsulta = new JMenuItem("Agendar");
		mnSubMenuConsulta.add(mntmNovaConsulta);
		
		mntmEditarConsulta = new JMenuItem("Editar");
		mnSubMenuConsulta.add(mntmEditarConsulta);
		
		JMenu mnSubMenuAgExames = new JMenu("Exames");
		mnMenuAgenda.add(mnSubMenuAgExames);
		
		mntmNovoExame = new JMenuItem("Agendar");
		mnSubMenuAgExames.add(mntmNovoExame);
		
		mntmEditarExames = new JMenuItem("Editar");
		mnSubMenuAgExames.add(mntmEditarExames);
		
		JMenu mnMenuRelatorios = new JMenu("Relatórios");
		menuBar.add(mnMenuRelatorios);
		
		mntmRelAgendaMedico = new JMenuItem("Agenda de médicos");
		mnMenuRelatorios.add(mntmRelAgendaMedico);
		
		mntmRelAgendaExames = new JMenuItem("Agenda de Exames");
		mnMenuRelatorios.add(mntmRelAgendaExames);
		
		mntmRelHistoricoPaciente = new JMenuItem("Histórico de Paciente");
		mnMenuRelatorios.add(mntmRelHistoricoPaciente);
		
		JMenu mnMenuSobre = new JMenu("Sobre");
		menuBar.add(mnMenuSobre);
		
		mntmMostrarSobre = new JMenuItem("Sistema");
		mnMenuSobre.add(mntmMostrarSobre);
	}
}
