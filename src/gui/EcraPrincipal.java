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
import javax.swing.JList;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.SpringLayout;

public class EcraPrincipal {

	private JFrame frmSistemaDeGesto;

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
		frmSistemaDeGesto.getContentPane().add(divJanelaPrincipal);
		
		JSplitPane divAbaLAteral = new JSplitPane();
		divJanelaPrincipal.setLeftComponent(divAbaLAteral);
		divAbaLAteral.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		JPanel panel = new JPanel();
		divAbaLAteral.setRightComponent(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		JLabel lblUserLabel = new JLabel("Usuário:");
		panel_1.add(lblUserLabel);
		
		JLabel lblUserName = new JLabel("[nome usuario]");
		panel_1.add(lblUserName);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		JButton btnLogoff = new JButton("Trocar Usuário");
		panel_2.add(btnLogoff);
		
		JPanel panel_3 = new JPanel();
		divAbaLAteral.setLeftComponent(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		JLabel lblNotificacoes = new JLabel("Notificações");
		panel_4.add(lblNotificacoes);
		
		JPanel visaoUsuario = new JPanel();
		divJanelaPrincipal.setRightComponent(visaoUsuario);
		visaoUsuario.setLayout(new BoxLayout(visaoUsuario, BoxLayout.Y_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		visaoUsuario.add(horizontalGlue);
		
		JDesktopPane desktopPane = new JDesktopPane();
		visaoUsuario.add(desktopPane);
		
		JMenuBar menuBar = new JMenuBar();
		frmSistemaDeGesto.setJMenuBar(menuBar);
		
		JMenu mnMenuCadastros = new JMenu("Cadastro");
		menuBar.add(mnMenuCadastros);
		
		JMenu mnSubMenuPacientes = new JMenu("Pacientes");
		mnMenuCadastros.add(mnSubMenuPacientes);
		
		JMenuItem mntmAddPaciente = new JMenuItem("Adicionar/Editar");
		mnSubMenuPacientes.add(mntmAddPaciente);
		
		JMenuItem mntmFormasPag = new JMenuItem("Formas de Pagamento");
		mnSubMenuPacientes.add(mntmFormasPag);
		
		JMenu mnSubMenuMedicos = new JMenu("Medicos");
		mnMenuCadastros.add(mnSubMenuMedicos);
		
		JMenuItem mntmMedicos = new JMenuItem("Adicionar/Editar");
		mnSubMenuMedicos.add(mntmMedicos);
		
		JMenuItem mntmEspecialidade = new JMenuItem("Especialidades");
		mnSubMenuMedicos.add(mntmEspecialidade);
		
		JMenu mnSubMenuExames = new JMenu("Exames");
		mnMenuCadastros.add(mnSubMenuExames);
		
		JMenuItem mntmTipoExames = new JMenuItem("Adicionar/Editar");
		mnSubMenuExames.add(mntmTipoExames);
		
		JMenu mnMenuAgenda = new JMenu("Agenda");
		menuBar.add(mnMenuAgenda);
		
		JMenu mnSubMenuConsulta = new JMenu("Consultas");
		mnMenuAgenda.add(mnSubMenuConsulta);
		
		JMenuItem mntmNovaConsulta = new JMenuItem("Agendar");
		mnSubMenuConsulta.add(mntmNovaConsulta);
		
		JMenuItem mntmEditarConsulta = new JMenuItem("Editar");
		mnSubMenuConsulta.add(mntmEditarConsulta);
		
		JMenu mnSubMenuAgExames = new JMenu("Exames");
		mnMenuAgenda.add(mnSubMenuAgExames);
		
		JMenuItem mntmNovoExame = new JMenuItem("Agendar");
		mnSubMenuAgExames.add(mntmNovoExame);
		
		JMenuItem mntmEditarExames = new JMenuItem("Editar");
		mnSubMenuAgExames.add(mntmEditarExames);
		
		JMenu mnMenuRelatorios = new JMenu("Relatórios");
		menuBar.add(mnMenuRelatorios);
		
		JMenuItem mntmRelAgendaMedico = new JMenuItem("Agenda de médicos");
		mnMenuRelatorios.add(mntmRelAgendaMedico);
		
		JMenuItem mntmRelAgendaExames = new JMenuItem("Agenda de Exames");
		mnMenuRelatorios.add(mntmRelAgendaExames);
		
		JMenuItem mntmRelHistoricoPaciente = new JMenuItem("Histórico de Paciente");
		mnMenuRelatorios.add(mntmRelHistoricoPaciente);
		
		JMenu mnMenuSobre = new JMenu("Sobre");
		menuBar.add(mnMenuSobre);
		
		JMenuItem mntmMostrarSobre = new JMenuItem("Sistema");
		mnMenuSobre.add(mntmMostrarSobre);
	}
}
