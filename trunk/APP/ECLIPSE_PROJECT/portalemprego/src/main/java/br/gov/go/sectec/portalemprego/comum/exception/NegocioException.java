package br.gov.go.sectec.portalemprego.comum.exception;

import java.util.List;

/**
 * <p>
 * <b>Title:</b> NegocioException.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Classe de Exception de negocio.
 * </p>
 * 
 * <p>
 * <b>Company: </b> Premium Consultoria e Sistemas
 * </p>
 * 
 * @author Silvânio
 * 
 * @version 1.0.0
 */
public class NegocioException extends RuntimeException {

	private boolean list;

	private String mensagem;

	private List<String> mensagens;

	/**
	 * SerialVersion.
	 */
	private static final long serialVersionUID = 1L;

	public NegocioException( final String mensagem ) {

		super(mensagem);
		
		this.mensagem = mensagem;
	}

	public NegocioException() {

		super();
	}

	public NegocioException( final List<String> mensagens) {
		
		super(mensagens.toString());
		
		this.mensagens = mensagens;
		
		this.list = Boolean.TRUE;
	}

	public NegocioException( final List<String> mensagens, final Throwable cause ) {

		super(cause);

		this.mensagens = mensagens;

		this.list = Boolean.TRUE;
	}

	public NegocioException( final String mensagem, final Throwable cause ) {

		super(mensagem, cause);

		this.mensagem = mensagem;

		this.list = Boolean.FALSE;
	}

	public NegocioException( final Throwable cause ) {

		super(cause);
	}

	
	public boolean isList() {
	
		return list;
	}

	
	public void setList(boolean list) {
	
		this.list = list;
	}

	
	public String getMensagem() {
	
		return mensagem;
	}

	
	public void setMensagem(String mensagem) {
	
		this.mensagem = mensagem;
	}

	
	public List<String> getMensagens() {
	
		return mensagens;
	}

	
	public void setMensagens(List<String> mensagens) {
	
		this.mensagens = mensagens;
	}
}
