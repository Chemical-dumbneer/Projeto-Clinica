package customExceptions;

public class ObjetoNaoExisteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ObjetoNaoExisteException(Object obj) {
	    super(obj == null ? "Objeto não encontrado." : "Objeto do tipo " + obj.getClass().getSimpleName() + " não encontrado.");
	}
}
