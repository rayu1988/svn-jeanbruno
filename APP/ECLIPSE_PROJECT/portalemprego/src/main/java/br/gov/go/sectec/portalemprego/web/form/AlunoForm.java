package br.gov.go.sectec.portalemprego.web.form;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.gov.go.sectec.portalemprego.comum.enumerator.SexoEnum;
import br.gov.go.sectec.portalemprego.core.entidade.Aluno;

/**
 * <p>
 * <b>Title:</b> AlunoForm.java
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
 * @author Joffre
 * 
 * @version 1.0.0
 */
@Component
public class AlunoForm {

	private String teste;

	
	public String getTeste() {
	
		return teste;
	}

	
	public void setTeste(String teste) {
	
		this.teste = teste;
	}
	
}
