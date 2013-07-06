package br.gov.go.sectec.portalemprego.comum.utilitario;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * <b>Title:</b> EmailUtil.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Classe responsável por validar email
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
public class EmailUtil {

	/**
	 * Método responsável por validar email.
	 *
	 * @author Silvânio
	 *
	 * @param email
	 * 
	 * @return
	 */
	public static Boolean validarEmail(final String email) {

		final Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		
		final Matcher m = p.matcher(email);
		
		final boolean isCaracteresEncontrados = m.matches();
		
		return isCaracteresEncontrados;
	}
}
