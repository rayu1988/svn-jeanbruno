package br.com.barganhas.web.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.com.tatu.helper.GeneralsHelper;

import br.com.barganhas.commons.Util;

public class EmailValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String emailValue = (String)value;
		if (!GeneralsHelper.isEmailOk(emailValue)) {
			throw new ValidatorException(new FacesMessage(Util.getMessageResourceString("errorInvalidEmailAddress")));
		}
	}
}
