package gui;

import javax.swing.JInternalFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import java.awt.Component;
import javax.swing.Box;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import entities.FormaPagamento;
import entities.Paciente;
import service.PacienteService;
import service.BuscaCEP;
import service.ConfiguracoesSistema;
import service.FormaPagamentoService;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.border.BevelBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import customExceptions.ObjetoNaoExisteException;
import javax.swing.LayoutStyle.ComponentPlacement;

public class EditPaciente extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private Path novaFotoSelecionada;
	private boolean modoAtualizacao = false;
	private Paciente pacienteAtual = null;
	
	private EcraPrincipal janelaMae;
	private final JTextField txtIdPaciente = new JTextField();
	private JTextField txtNome;
	private JTextField txtCidade;
	private JTextField txtBairro;
	private JTextField txtRua;
	private JTextField txtNumero;
	private JComboBox<String> cbSexo;
	private JButton btnAlterarFoto;
	private JFormattedTextField ftxTelefone;
	private JFormattedTextField ftxDataNascimento;
	private JComboBox<FormaPagamento> cbFormaPag;
	private JFormattedTextField ftxCep;
	private JComboBox<String> cbEstado;
	private JButton btnAplicar;
	private JButton btnProcuraPaciente;
	
	private static final String[] UFs = {
		    "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO",
		    "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI",
		    "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"
		};
	
	private JLabel lblFotoPaciente;
	private JLabel lblCarregando; 
	
	private List<JComponent> grupoHabilitavel;
	private List<JComponent> grupoCEP;
	
	public void fecharJanela() {
		this.dispose();
		this.janelaMae.mntmAddPaciente.setEnabled(true);
	}
	
	private void buscaCEP() {
		BuscaCEP bcep = new BuscaCEP(
					this.ftxCep.getText(),
					this.cbEstado,
					this.txtCidade,
					this.txtBairro,
					this.txtRua,
					this.lblCarregando
				);
		bcep.start();
	}
	
	private void atualizarImagem(Path caminhoImagem) {
	    ImageIcon icone = new ImageIcon(caminhoImagem.toString());
	    Image img = icone.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
	    lblFotoPaciente.setIcon(new ImageIcon(img));
	}
	
	public void popularComboBoxUF() {
	    this.cbEstado.removeAllItems();
	    for (String uf : UFs) {
	        this.cbEstado.addItem(uf);
	    }
	}
	
	private void habilitarCampos() {
	    for (JComponent comp : grupoHabilitavel) {
	        comp.setEnabled(true);
	    }
	}
	
	private void populaDadosPaciente(Paciente paciente) {
	    if (paciente == null || paciente.getId() == 0) {
	        return; // paciente inválido ou novo
	    }

	    txtIdPaciente.setText(String.valueOf(paciente.getId()));
	    txtNome.setText(paciente.getNome());
	    
	    char sexo = paciente.getSexo();
	    switch (sexo) {
	        case 'M' -> cbSexo.setSelectedItem("Masculino");
	        case 'F' -> cbSexo.setSelectedItem("Feminino");
	        default -> cbSexo.setSelectedItem("Não Informar");
	    }

	    ftxTelefone.setText(paciente.getTelefone());

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    LocalDate data = paciente.getDataNascimento().toLocalDate();
	    ftxDataNascimento.setText(data.format(formatter));

	    cbFormaPag.setSelectedItem(paciente.getFormaPag());

	    ftxCep.setText(paciente.getCep());
	    cbEstado.setSelectedItem(paciente.getEstado());
	    txtCidade.setText(paciente.getCidade());
	    txtBairro.setText(paciente.getBairro());
	    txtRua.setText(paciente.getRua());
	    txtNumero.setText(String.valueOf(paciente.getNumero()));

	    if (paciente.getFoto() != null && Path.of(ConfiguracoesSistema.getCaminhoImagens(),paciente.getFoto()).toFile().exists()) {
	        ImageIcon icone = new ImageIcon(Path.of(ConfiguracoesSistema.getCaminhoImagens(),paciente.getFoto()).toString());
	        Image img = icone.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
	        lblFotoPaciente.setIcon(new ImageIcon(img));
	    }

	    pacienteAtual = paciente;
	    habilitarCampos();
	}
	
	private Paciente getPacienteFromForm() throws IOException {
	    Paciente paciente = (pacienteAtual != null) ? pacienteAtual : new Paciente();
	    
	    paciente.setNome(txtNome.getText());
	    paciente.setSexo(cbSexo.getSelectedIndex() == 1 ? 'M' : cbSexo.getSelectedIndex() == 2 ? 'F' : 'N');
	    paciente.setTelefone(ftxTelefone.getText());
	    
	    String textoData = ftxDataNascimento.getText();
		// precisa transformar para java.sql.Date:
		LocalDate localDate = LocalDate.parse(textoData, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Date sqlDate = Date.valueOf(localDate);
		paciente.setDataNascimento(sqlDate);

	    paciente.setFormaPag((FormaPagamento) cbFormaPag.getSelectedItem());
	    paciente.setCep(ftxCep.getText());
	    paciente.setEstado((String) cbEstado.getSelectedItem());
	    paciente.setCidade(txtCidade.getText());
	    paciente.setBairro(txtBairro.getText());
	    paciente.setRua(txtRua.getText());
	    paciente.setNumero(Integer.parseInt(txtNumero.getText()));

	    // FOTO: se for nova, salva e define o caminho
	    if (novaFotoSelecionada != null) {
	        String nomeArquivo = paciente.getId() + ".png";
	        Path destino = Path.of(ConfiguracoesSistema.getCaminhoImagens(),nomeArquivo);
	        Files.copy(novaFotoSelecionada, destino, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
	        paciente.setFoto(nomeArquivo);
	    }

	    return paciente;
	}


	/**
	 * Create the frame.
	 */
	public EditPaciente(EcraPrincipal janelaMae) {
		this.janelaMae = janelaMae;
		this.initComponentes();
		this.populaCbSexo();
		this.populaCbFormasPag();
		this.popularComboBoxUF();
		
		for(JComponent comp : this.grupoHabilitavel) {
			comp.setEnabled(false);
		}
	}
	
	private void initComponentes() {
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
		
		btnAplicar = new JButton("Aplicar");
		btnAplicar.setPreferredSize(new Dimension(150, 23));
		btnAplicar.setMaximumSize(new Dimension(150, 23));
		btnAplicar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnAplicar.addActionListener(e -> {
			salvaAlteracoes();
		});

		
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
		txtIdPaciente.setPreferredSize(new Dimension(200, 20));
		txtIdPaciente.setMinimumSize(new Dimension(200, 20));
		txtIdPaciente.addActionListener(e -> {
			procuraPacienteOuCria();
		});


		panel_1.add(txtIdPaciente);
		txtIdPaciente.setHorizontalAlignment(SwingConstants.CENTER);
		txtIdPaciente.setSize(new Dimension(100, 20));
		txtIdPaciente.setMaximumSize(new Dimension(200, 30));
		txtIdPaciente.setColumns(10);
		
		btnProcuraPaciente = new JButton("Procurar");
		panel_1.add(btnProcuraPaciente);
		btnProcuraPaciente.addActionListener(e -> {
		    abrirSeletorPaciente();
		});
		
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
		lblFotoPaciente = new JLabel(iconeRedimensionado);
		lblFotoPaciente.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblFotoPaciente.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_3.add(lblFotoPaciente);
		lblFotoPaciente.setVerticalAlignment(SwingConstants.TOP);
		
		btnAlterarFoto = new JButton("Mudar Foto");
		btnAlterarFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAlterarFoto.addActionListener(e -> {
		    alterarFoto();
		});

		
		panel_3.add(btnAlterarFoto);
		
		JPanel zonaDados = new JPanel();
		dadosPaciente.setRightComponent(zonaDados);
		
		JLabel lblNome = new JLabel("Nome do Paciente:");
		
		txtNome = new JTextField();
		txtNome.setColumns(10);
		
		JLabel lblSexo = new JLabel("Sexo:");
		
		cbSexo = new JComboBox<String>();
		
		JLabel lblTelefone = new JLabel("Telefone:");
		
		MaskFormatter maskTelefone;
		try {
			maskTelefone = new MaskFormatter("(##) #####-####");maskTelefone.setPlaceholderCharacter('_');
			ftxTelefone = new JFormattedTextField();
			ftxTelefone.setFormatterFactory(new DefaultFormatterFactory(maskTelefone));
		} catch (ParseException e1) {
			JOptionPane.showMessageDialog(ftxCep, e1.getMessage(), "Erro: Se vira amigão", JOptionPane.ERROR_MESSAGE);
		}
		
		
		JLabel lblDataNascimento = new JLabel("Data de Nascimento:");
		
		MaskFormatter maskData;
		try {
			maskData = new MaskFormatter("##/##/####");
			maskData.setPlaceholderCharacter('_');
			ftxDataNascimento = new JFormattedTextField();
			ftxDataNascimento.setFormatterFactory(new DefaultFormatterFactory(maskData));
		} catch (ParseException e2) {
			JOptionPane.showMessageDialog(ftxCep, e2.getMessage(), "Erro: Se vira amigão", JOptionPane.ERROR_MESSAGE);
		}
		
		JLabel lblFormaPagamento = new JLabel("Forma de Pagamento:");
		
		cbFormaPag = new JComboBox<FormaPagamento>();
		
		JLabel lblCep = new JLabel("CEP:");
		
		try {
		    MaskFormatter cepMask = new MaskFormatter("#####-###");
		    cepMask.setPlaceholderCharacter('_');
		    ftxCep = new JFormattedTextField(cepMask);
		} catch (ParseException e) {
		    JOptionPane.showMessageDialog(ftxCep, e.getMessage(), "Erro: Se vira amigão", JOptionPane.ERROR_MESSAGE);
		}
		ftxCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscaCEP();
			}
		});
		
		JLabel lblEstado = new JLabel("Estado:");
		
		cbEstado = new JComboBox<String>();
		
		JLabel lblCidade = new JLabel("Cidade:");
		
		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		
		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		
		JLabel lblRua = new JLabel("Rua:");
		lblRua.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtRua = new JTextField();
		txtRua.setColumns(10);
		
		JLabel lblNumeroRua = new JLabel("Numero:");
		
		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		
		ImageIcon gifzin = new ImageIcon(getClass().getResource("/resources/loadingIconRedim.gif"));
		lblCarregando = new JLabel(gifzin);
		lblCarregando.setVisible(false);
		lblCarregando.setHorizontalAlignment(SwingConstants.LEFT);
		lblCarregando.setAlignmentY(Component.TOP_ALIGNMENT);
		
		lblCarregando.setVerticalAlignment(SwingConstants.TOP);
		lblCarregando.setBorder(null);
		
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
						.addComponent(txtBairro, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtRua, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtNumero, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, 409, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbEstado, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_zonaDados.createParallelGroup(Alignment.TRAILING)
							.addGroup(Alignment.LEADING, gl_zonaDados.createSequentialGroup()
								.addGroup(gl_zonaDados.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(ftxCep, Alignment.LEADING)
									.addComponent(cbFormaPag, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(ftxDataNascimento, Alignment.LEADING)
									.addComponent(ftxTelefone, Alignment.LEADING)
									.addComponent(cbSexo, Alignment.LEADING, 0, 137, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblCarregando))
							.addComponent(txtCidade, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)))
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
					.addGroup(gl_zonaDados.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_zonaDados.createSequentialGroup()
							.addGroup(gl_zonaDados.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCep)
								.addComponent(ftxCep, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_zonaDados.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblEstado)
								.addComponent(cbEstado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblCarregando, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
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
					.addGap(253))
		);
		zonaDados.setLayout(gl_zonaDados);
		
		this.grupoHabilitavel = new ArrayList<JComponent>();

		this.grupoHabilitavel.add(lblNome);
		this.grupoHabilitavel.add(txtNome);
		
		this.grupoHabilitavel.add(lblSexo);
		this.grupoHabilitavel.add(cbSexo);
		
		this.grupoHabilitavel.add(lblFotoPaciente);
		this.grupoHabilitavel.add(btnAlterarFoto);
		
		this.grupoHabilitavel.add(lblTelefone);
		this.grupoHabilitavel.add(ftxTelefone);
		
		this.grupoHabilitavel.add(lblDataNascimento);
		this.grupoHabilitavel.add(ftxDataNascimento);
		
		this.grupoHabilitavel.add(lblFormaPagamento);
		this.grupoHabilitavel.add(cbFormaPag);
		
		this.grupoHabilitavel.add(lblCep);
		this.grupoHabilitavel.add(ftxCep);
		
		this.grupoHabilitavel.add(lblEstado);
		this.grupoHabilitavel.add(cbEstado);
		
		this.grupoHabilitavel.add(lblCidade);
		this.grupoHabilitavel.add(txtCidade);
		
		this.grupoHabilitavel.add(lblBairro);
		this.grupoHabilitavel.add(txtBairro);
		
		this.grupoHabilitavel.add(lblRua);
		this.grupoHabilitavel.add(txtRua);
		
		this.grupoHabilitavel.add(lblNumeroRua);
		this.grupoHabilitavel.add(txtNumero);
		
		this.grupoHabilitavel.add(btnAplicar);
		
		this.grupoCEP = new ArrayList<JComponent>();
		
		this.grupoCEP.add(lblEstado);
		this.grupoCEP.add(cbEstado);
		
		this.grupoCEP.add(lblCidade);
		this.grupoCEP.add(txtCidade);
		
		this.grupoCEP.add(lblBairro);
		this.grupoCEP.add(txtBairro);
		
		this.grupoCEP.add(lblRua);
		this.grupoCEP.add(txtRua);
		
		this.grupoCEP.add(lblNumeroRua);
		this.grupoCEP.add(txtNumero);
	}
	
	private void salvaAlteracoes() {
	    try {
	        Paciente paciente = getPacienteFromForm();
	        PacienteService service = new PacienteService();

	        if (modoAtualizacao) {
	            service.atualizar(paciente);
	            JOptionPane.showMessageDialog(this, "Paciente atualizado com sucesso.");
	        } else {
	            service.cadastrar(paciente);
	            JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso.");
	        }

	        //fecharJanela();

	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private void procuraPacienteOuCria() {
	    try {
	        String input = txtIdPaciente.getText().trim();
	        if (input.isEmpty()) {
	            throw new ObjetoNaoExisteException(null);
	        }

	        int id = Integer.parseInt(input);
	        pacienteAtual = new PacienteService().buscarPorID(id);
	        modoAtualizacao = true;
	        populaDadosPaciente(pacienteAtual);
	    } catch (ObjetoNaoExisteException ex) {
	        try {
	            int prox = new PacienteService().proxID();
	            txtIdPaciente.setText(String.valueOf(prox));
	            pacienteAtual = new Paciente();
	            pacienteAtual.setId(prox);
	            modoAtualizacao = false;
	        } catch (SQLException | IOException err) {
	            JOptionPane.showMessageDialog(this, "Erro ao obter próximo ID: " + err.getMessage());
	        }
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "ID inválido.", JOptionPane.ERROR_MESSAGE);
	    }
	    
	    for (JComponent c : grupoHabilitavel) c.setEnabled(true);
	    txtIdPaciente.setEnabled(false);
	}

	private void abrirSeletorPaciente() {
		SeletorPacienteDialog seletor = new SeletorPacienteDialog(
	        (Frame) SwingUtilities.getWindowAncestor(btnProcuraPaciente),
	        new PacienteService()
	    );

	    seletor.setVisible(true);

	    Paciente selecionado = seletor.getPacienteSelecionado();
	    populaDadosPaciente(selecionado);
	}

	private void alterarFoto() {
		JFileChooser chooser = new JFileChooser();
	    chooser.setDialogTitle("Escolher Foto do Paciente");
	    chooser.setAcceptAllFileFilterUsed(false);
	    chooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagens (*.png, *.jpg, *.jpeg)", "png", "jpg", "jpeg"));

	    int resultado = chooser.showOpenDialog(this);
	    if (resultado == JFileChooser.APPROVE_OPTION) {
	        File arquivoSelecionado = chooser.getSelectedFile();
	        Path caminho = arquivoSelecionado.toPath();

	        // Atualiza o atributo e o preview
	        novaFotoSelecionada = caminho;
	        atualizarImagem(caminho);
	    }
	}

	private void populaCbSexo() {

		this.cbSexo.addItem("Não Informar");
		this.cbSexo.addItem("Masculino");
		this.cbSexo.addItem("Feminino");
	}
	
	private void populaCbFormasPag() {
		
		try {
			List<FormaPagamento> listaFpag = new FormaPagamentoService().buscarTudo();
			
			for(FormaPagamento fpag : listaFpag) {
				this.cbFormaPag.addItem(fpag);
			}
			this.cbFormaPag.setSelectedIndex(-1);
			
		} catch (SQLException | IOException | ObjetoNaoExisteException e) {
			
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao buscar dados de Formas de Pagamento", JOptionPane.ERROR_MESSAGE); 
		}
	}
}	