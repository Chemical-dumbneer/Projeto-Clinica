package gui;

import javax.swing.JInternalFrame;

public class EditTipoExame extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private EcraPrincipal janelaMae;

	/**
	 * Create the frame.
	 */
	public EditTipoExame(EcraPrincipal janelaMae) {
		this.janelaMae = janelaMae;
		setBounds(100, 100, 450, 300);

	}

}
