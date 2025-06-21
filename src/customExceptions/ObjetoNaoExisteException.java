package customExceptions;

public class ObjetoNaoExisteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ObjetoNaoExisteException(Object objetoNulo) {
		super("O Objeto do tipo " + objetoNulo.getClass() + " não foi encontrado na base de dados ou não existe.");
	}

}
