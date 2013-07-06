package br.gov.go.sectec.portalemprego.web.actions;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import br.gov.go.sectec.portalemprego.comum.exception.NegocioException;

import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>
 * <b>Title:</b> PremiumAction.java
 * </p>
 * 
 * <p>
 * <b>Description:</b>
 * </p>
 * 
 * <p>
 * <b>Company: </b> Premium Consultoria e Sistemas
 * </p>
 * 
 * @author Silv�nio
 * 
 * @version 1.0.0
 */
public class PremiumAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;

	protected static final String ALERT = "alert";

	protected static final String SUCCESS = "success";

	protected static final String LOGIN = "login";

	protected static final String OPERACAO_SUCESSO = "Opera��o realizada com sucesso!";

	private Map<String, Object> session;

	/**
	 * M�todo respons�vel por por adicionar mensagem de sucesso.
	 * 
	 * @author Silv�nio
	 * 
	 * @param mensagem
	 */
	protected void adicionarMensagemSucesso(final String mensagem) {

		this.addActionMessage(mensagem);

	}

	/**
	 * M�todo respons�vel por adicionar mensagem de valida��o.
	 * 
	 * @author Silv�nio
	 * 
	 * @param mensagem
	 */
	protected void adicionarMensagemValidacao(final String mensagem) {

		this.addActionError(mensagem);
	}

	/**
	 * M�todo respons�vel por adicionar v�rias mensagens de valida��o.
	 * 
	 * @author Silv�nio
	 * 
	 * @param mensagens
	 */
	protected void adicionarMensagemValidacao(final List<String> mensagens) {

		for (final String mensagem : mensagens) {

			this.adicionarMensagemValidacao(mensagem);
		}

	}

	/**
	 * M�todo respons�vel por adicionar mensagem de valida��o.
	 * 
	 * @author Silv�nio
	 * 
	 * @param ne
	 */
	protected void adicionarMensagemValidacao(final NegocioException ne) {

		if (ne.isList()) {

			this.adicionarMensagemValidacao(ne.getMensagens());

		} else {

			this.adicionarMensagemValidacao(ne.getMensagem());

		}

	}

	/**
	 * M�todo respons�vel por obter dados da sess�o.
	 *
	 * @author Silv�nio
	 *
	 * @param key
	 * 
	 * @return objeto
	 */
	protected Object obterDaSessao(final String key) {

		return this.getSession().get(key);

	}

	/**
	 * M�todo respons�vel por incluir dados na sessao.
	 *
	 * @author Silv�nio
	 *
	 * @param key
	 * 
	 * @param value
	 */
	protected void incluirSessao(final String key, final Object value) {

		this.getSession().put(key, value);

	}
	
	/**
	 * M�todo respons�vel por remover atributos da sessao.
	 *
	 * @author Silv�nio
	 *
	 * @param key
	 */
	public void removerSessao(final String key){
		
		
		getSession().remove(key);
		
	}

	@Override
	public void setSession(final Map<String, Object> session) {

		this.session = session;
	}

	public Map<String, Object> getSession() {

		return this.session;
	}

	public class ProcessingReturn implements Serializable {
		
		private static final long serialVersionUID = -622751383002173103L;
		
		private boolean success = true;
		private String errorMsg;
		
		public ProcessingReturn() {
		}
		
		public ProcessingReturn(String errorMsg) {
			this.errorMsg = errorMsg;
			this.success = false;
		}
		
		public boolean getSuccess() {
			return success;
		}
		public String getErrorMsg() {
			return errorMsg;
		}
	}
}
