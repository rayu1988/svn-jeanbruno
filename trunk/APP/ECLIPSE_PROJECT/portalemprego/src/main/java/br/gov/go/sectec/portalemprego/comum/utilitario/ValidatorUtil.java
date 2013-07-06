package br.gov.go.sectec.portalemprego.comum.utilitario;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * <p>
 * <b>Title:</b> ValidatorUtil.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Classe responsável pela validação
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
public class ValidatorUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Método responável por verificar se objeto é nulo.
	 * 
	 * @param object
	 *            Objeto para validação
	 * 
	 * @return boolean true or false.
	 */
	public static boolean isNull(final Object object) {

		return object == null;
	}

	/**
	 * Método responável por verificar se objeto não é nulo.
	 * 
	 * @param object
	 *            Objeto para validação§Ã£o
	 * 
	 * @return boolean true or false.
	 */
	public static boolean isNotNull(final Object object) {

		return object != null;
	}

	/**
	 * Método responável por verificar se vazio.
	 * 
	 * @param str
	 *            Objeto para validação
	 * 
	 * @return boolean true or false.
	 */
	public static boolean isEmpty(final String str) {

		return ValidatorUtil.isNull(str) || str.isEmpty();
	}

	/**
	 * Método responável por verificar se não é vazio.
	 * 
	 * @param str
	 *            String para validação
	 * 
	 * @return boolean true or false.
	 */
	public static boolean isNotEmpty(final String str) {

		return ValidatorUtil.isNotNull(str) && !str.isEmpty();
	}

	/**
	 * Método responável por verificar se a lista não é vazia.
	 * 
	 * @param lista
	 *            List para validação
	 * 
	 * @return boolean true or false.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(final List lista) {

		return ValidatorUtil.isNull(lista) || lista.isEmpty();
	}

	/**
	 * Método responável por verificar se a lista não é vazia.
	 * 
	 * @param lista
	 *            List para validação
	 * 
	 * @return boolean true or false.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(final List lista) {

		return !ValidatorUtil.isEmpty(lista);
	}

	/**
	 * Método responável por verificar as seguintes situações:
	 * 
	 * StringUtils.isBlank(null) = true StringUtils.isBlank("") = true StringUtils.isBlank(" ") =
	 * true StringUtils.isBlank("bob") = false StringUtils.isBlank("  bob  ") = false
	 * 
	 * @param str
	 *            String que irá ser verificada.
	 * 
	 * @return bolean true or false.
	 */
	public static boolean isBlank(final String str) {

		return StringUtils.isBlank(str);
	}
}
