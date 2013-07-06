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
 * @author Silvânio
 * 
 * @version 1.0.0
 */
public class PremiumAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;

	protected static final String ALERT = "alert";

	protected static final String SUCCESS = "success";

	protected static final String LOGIN = "login";

	protected static final String OPERACAO_SUCESSO = "Operação realizada com sucesso!";

	private Map<String, Object> session;

	/**
	 * Método responsável por por adicionar mensagem de sucesso.
	 * 
	 * @author Silvânio
	 * 
	 * @param mensagem
	 */
	protected void adicionarMensagemSucesso(final String mensagem) {

		this.addActionMessage(mensagem);

	}

	/**
	 * Método responsável por adicionar mensagem de validação.
	 * 
	 * @author Silvânio
	 * 
	 * @param mensagem
	 */
	protected void adicionarMensagemValidacao(final String mensagem) {

		this.addActionError(mensagem);
	}

	/**
	 * Método responsável por adicionar várias mensagens de validação.
	 * 
	 * @author Silvânio
	 * 
	 * @param mensagens
	 */
	protected void adicionarMensagemValidacao(final List<String> mensagens) {

		for (final String mensagem : mensagens) {

			this.adicionarMensagemValidacao(mensagem);
		}

	}

	/**
	 * Método responsável por adicionar mensagem de validação.
	 * 
	 * @author Silvânio
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
	 * Método responsável por obter dados da sessão.
	 *
	 * @author Silvânio
	 *
	 * @param key
	 * 
	 * @return objeto
	 */
	protected Object obterDaSessao(final String key) {

		return this.getSession().get(key);

	}

	/**
	 * Método responsável por incluir dados na sessao.
	 *
	 * @author Silvânio
	 *
	 * @param key
	 * 
	 * @param value
	 */
	protected void incluirSessao(final String key, final Object value) {

		this.getSession().put(key, value);

	}
	
	/**
	 * Método responsável por remover atributos da sessao.
	 *
	 * @author Silvânio
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
