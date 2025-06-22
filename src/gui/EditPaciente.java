package gui;

import javax.swing.JInternalFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import java.awt.Component;
import javax.swing.Box;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;

import entities.FormaPagamento;

import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.border.BevelBorder;

public class EditPaciente extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private EcraPrincipal janelaMae;
	private final JTextField textField = new JTextField();
	private JTextField txtNome;
	private JTextField txtCidade;
	private JTextField txtBairro;
	private JTextField txtRua;
	private JTextField txtNumero;
	
	public void fecharJanela() {
		this.dispose();
		this.janelaMae.mntmAddPaciente.setEnabled(true);
	}
	/**
	 * Create the frame.
	 */
	public EditPaciente(EcraPrincipal janelaMae) {
		this.janelaMae = janelaMae;
		setTitle("Editar Cadastro de Paciente");
		setBounds(100, 100, 818, 608);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(32767, 80));
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_1, BorderLayout.NORTH);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_2, BorderLayout.SOUTH);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.EAST);
		panel_2.setMaximumSize(new Dimension(100, 60));
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.setEnabled(false);
		btnAplicar.setPreferredSize(new Dimension(150, 23));
		btnAplicar.setMaximumSize(new Dimension(150, 23));
		btnAplicar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_2.add(btnAplicar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnCancelar.setPreferredSize(new Dimension(150, 23));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecharJanela();
			}
		});
		panel_2.add(btnCancelar);
		btnCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCancelar.setMaximumSize(new Dimension(150, 23));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue, BorderLayout.WEST);
		
		JPanel panel_1 = new JPanel();
		panel_1.setMaximumSize(new Dimension(32767, 20));
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblIdPaciente = new JLabel("Código do Paciente:");
		panel_1.add(lblIdPaciente);
		panel_1.add(textField);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setSize(new Dimension(100, 20));
		textField.setMaximumSize(new Dimension(200, 30));
		textField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		getContentPane().add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);
		
		JSplitPane dadosPaciente = new JSplitPane();
		scrollPane.setViewportView(dadosPaciente);
		
		JPanel zonaFoto = new JPanel();
		dadosPaciente.setLeftComponent(zonaFoto);
		zonaFoto.setLayout(new BorderLayout(0, 0));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		zonaFoto.add(horizontalStrut, BorderLayout.WEST);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		zonaFoto.add(horizontalStrut_1, BorderLayout.EAST);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		zonaFoto.add(verticalStrut, BorderLayout.NORTH);
		
		Component verticalGlue = Box.createVerticalGlue();
		zonaFoto.add(verticalGlue, BorderLayout.SOUTH);
		
		JPanel panel_3 = new JPanel();
		zonaFoto.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		
		ImageIcon icone = new ImageIcon(getClass().getResource("/resources/defaultUserImage.png"));
		Image img = icone.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		ImageIcon iconeRedimensionado = new ImageIcon(img);
		JLabel lblFotoPaciente = new JLabel(iconeRedimensionado);
		lblFotoPaciente.setEnabled(false);
		lblFotoPaciente.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblFotoPaciente.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_3.add(lblFotoPaciente);
		lblFotoPaciente.setVerticalAlignment(SwingConstants.TOP);
		
		JButton btnAlterarFoto = new JButton("Mudar Foto");
		btnAlterarFoto.setEnabled(false);
		btnAlterarFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_3.add(btnAlterarFoto);
		
		JPanel zonaDados = new JPanel();
		dadosPaciente.setRightComponent(zonaDados);
		
		JLabel lblNome = new JLabel("Nome do Paciente:");
		lblNome.setEnabled(false);
		
		txtNome = new JTextField();
		txtNome.setEnabled(false);
		txtNome.setColumns(10);
		
		JLabel lblSexo = new JLabel("Sexo:");
		lblSexo.setEnabled(false);
		
		JComboBox<?> cbSexo = new JComboBox<Object>();
		cbSexo.setEnabled(false);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setEnabled(false);
		
		JFormattedTextField ftxTelefone = new JFormattedTextField();
		ftxTelefone.setEnabled(false);
		
		JLabel lblDataNascimento = new JLabel("Data de Nascimento:");
		lblDataNascimento.setEnabled(false);
		
		JFormattedTextField ftxDataNascimento = new JFormattedTextField();
		ftxDataNascimento.setEnabled(false);
		
		JLabel lblFormaPagamento = new JLabel("Forma de Pagamento:");
		lblFormaPagamento.setEnabled(false);
		
		JComboBox<FormaPagamento> cbFormaPag = new JComboBox<FormaPagamento>();
		cbFormaPag.setEnabled(false);
		
		JLabel lblCep = new JLabel("CEP:");
		lblCep.setEnabled(false);
		
		JFormattedTextField ftxCep = new JFormattedTextField();
		ftxCep.setEnabled(false);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setEnabled(false);
		
		JComboBox<?> cbEstado = new JComboBox<Object>();
		cbEstado.setEnabled(false);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setEnabled(false);
		
		txtCidade = new JTextField();
		txtCidade.setEnabled(false);
		txtCidade.setColumns(10);
		
		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setEnabled(false);
		lblBairro.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtBairro = new JTextField();
		txtBairro.setEnabled(false);
		txtBairro.setColumns(10);
		
		JLabel lblRua = new JLabel("Rua:");
		lblRua.setEnabled(false);
		lblRua.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtRua = new JTextField();
		txtRua.setEnabled(false);
		txtRua.setColumns(10);
		
		JLabel lblNumeroRua = new JLabel("Numero:");
		lblNumeroRua.setEnabled(false);
		
		txtNumero = new JTextField();
		txtNumero.setEnabled(false);
		txtNumero.setColumns(10);
		GroupLayout gl_zonaDados = new GroupLayout(zonaDados);
		gl_zonaDados.setHorizontalGroup(
			gl_zonaDados.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_zonaDados.createSequentialGroup()
					.addGap(43)
					.addGroup(gl_zonaDados.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNumeroRua)
						.addComponent(lblRua, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblBairro, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCidade)
						.addComponent(lblEstado)
						.addComponent(lblCep)
						.addComponent(lblFormaPagamento)
						.addComponent(lblDataNascimento)
						.addComponent(lblNome)
						.addComponent(lblTelefone)
						.addComponent(lblSexo))
					.addGap(18)
					.addGroup(gl_zonaDados.createParallelGroup(Alignment.LEADING)
						.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, 409, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_zonaDados.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(ftxCep, Alignment.LEADING)
							.addComponent(cbFormaPag, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(ftxDataNascimento, Alignment.LEADING)
							.addComponent(ftxTelefone, Alignment.LEADING)
							.addComponent(cbSexo, Alignment.LEADING, 0, 137, Short.MAX_VALUE))
						.addComponent(cbEstado, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtCidade, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtBairro, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtRua, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtNumero, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(263))
		);
		gl_zonaDados.setVerticalGroup(
			gl_zonaDados.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_zonaDados.createSequentialGroup()
					.addGap(41)
					.addGroup(gl_zonaDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNome))
					.addGap(18)
					.addGroup(gl_zonaDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSexo)
						.addComponent(cbSexo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_zonaDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTelefone)
						.addComponent(ftxTelefone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_zonaDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDataNascimento)
						.addComponent(ftxDataNascimento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_zonaDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFormaPagamento)
						.addComponent(cbFormaPag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_zonaDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCep)
						.addComponent(ftxCep, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_zonaDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEstado)
						.addComponent(cbEstado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_zonaDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCidade)
						.addComponent(txtCidade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_zonaDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBairro)
						.addComponent(txtBairro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_zonaDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRua)
						.addComponent(txtRua, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_zonaDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumeroRua)
						.addComponent(txtNumero, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(253, Short.MAX_VALUE))
		);
		zonaDados.setLayout(gl_zonaDados);

	}
}
