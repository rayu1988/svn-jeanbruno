package br.gov.go.sectec.portalemprego.comum.utilitario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.gov.go.sectec.portalemprego.comum.exception.NegocioException;

/**
 * <p>
 * <b>Title:</b> BusinessUtils.java
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
@Component
public class MensagemNegocioUtils {

	private final List<String> mensagens = new ArrayList<String>();

	public void execute() {

		if (ValidatorUtil.isNotEmpty(this.mensagens)) {

			throw new NegocioException(this.mensagens);

		}

	}

	public void add(final String msg) {

		this.mensagens.add(msg);

	}

	public void validar(final String str, final String msg) {

		if (ValidatorUtil.isBlank(str)) {
			this.add(msg);
		}
	}

	public void validar(final Date date, final String msg) {

		if (date == null) {
			this.add(msg);
		}
	}

	public void validar(final Integer value, final String msg) {

		if (value == null) {

			this.add(msg);

		}
	}

	public boolean validarEntidade(final Object ob, final String msg) {

		if (ob == null) {

			this.add(msg);

			return false;
		}
		return true;
	}

	public void validar(final Object ob, final String msg) {

		if (ob == null) {

			this.add(msg);

		}
	}

	@SuppressWarnings("rawtypes")
	public void validar(final List list, final String msg) {

		if (list == null || list.isEmpty()) {

			this.add(msg);

		}
	}

	@SuppressWarnings("rawtypes")
	public void validar(final Set set, final String msg) {

		if (set == null || set.isEmpty()) {

			this.add(msg);

		}
	}
}
