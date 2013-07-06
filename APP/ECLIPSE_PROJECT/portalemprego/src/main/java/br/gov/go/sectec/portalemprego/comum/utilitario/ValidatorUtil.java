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
 * <b>Description:</b> Classe respons�vel pela valida��o
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
public class ValidatorUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * M�todo respon�vel por verificar se objeto � nulo.
	 * 
	 * @param object
	 *            Objeto para valida��o
	 * 
	 * @return boolean true or false.
	 */
	public static boolean isNull(final Object object) {

		return object == null;
	}

	/**
	 * M�todo respon�vel por verificar se objeto n�o � nulo.
	 * 
	 * @param object
	 *            Objeto para valida��o�ão
	 * 
	 * @return boolean true or false.
	 */
	public static boolean isNotNull(final Object object) {

		return object != null;
	}

	/**
	 * M�todo respon�vel por verificar se vazio.
	 * 
	 * @param str
	 *            Objeto para valida��o
	 * 
	 * @return boolean true or false.
	 */
	public static boolean isEmpty(final String str) {

		return ValidatorUtil.isNull(str) || str.isEmpty();
	}

	/**
	 * M�todo respon�vel por verificar se n�o � vazio.
	 * 
	 * @param str
	 *            String para valida��o
	 * 
	 * @return boolean true or false.
	 */
	public static boolean isNotEmpty(final String str) {

		return ValidatorUtil.isNotNull(str) && !str.isEmpty();
	}

	/**
	 * M�todo respon�vel por verificar se a lista n�o � vazia.
	 * 
	 * @param lista
	 *            List para valida��o
	 * 
	 * @return boolean true or false.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(final List lista) {

		return ValidatorUtil.isNull(lista) || lista.isEmpty();
	}

	/**
	 * M�todo respon�vel por verificar se a lista n�o � vazia.
	 * 
	 * @param lista
	 *            List para valida��o
	 * 
	 * @return boolean true or false.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(final List lista) {

		return !ValidatorUtil.isEmpty(lista);
	}

	/**
	 * M�todo respon�vel por verificar as seguintes situa��es:
	 * 
	 * StringUtils.isBlank(null) = true StringUtils.isBlank("") = true StringUtils.isBlank(" ") =
	 * true StringUtils.isBlank("bob") = false StringUtils.isBlank("  bob  ") = false
	 * 
	 * @param str
	 *            String que ir� ser verificada.
	 * 
	 * @return bolean true or false.
	 */
	public static boolean isBlank(final String str) {

		return StringUtils.isBlank(str);
	}
}
