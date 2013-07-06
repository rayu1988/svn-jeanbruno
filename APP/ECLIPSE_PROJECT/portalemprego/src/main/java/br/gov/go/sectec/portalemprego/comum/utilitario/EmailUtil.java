package br.gov.go.sectec.portalemprego.comum.utilitario;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * <b>Title:</b> EmailUtil.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Classe respons�vel por validar email
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
public class EmailUtil {

	/**
	 * M�todo respons�vel por validar email.
	 *
	 * @author Silv�nio
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
