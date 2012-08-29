package br.com.barganhas.web.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.barganhas.commons.Util;

public class EmailValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String emailValue = (String)value;
		Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = pattern.matcher(emailValue);
		if (!m.matches()) {
			throw new ValidatorException(new FacesMessage(Util.getMessageResourceString("errorInvalidEmailAddress")));
		}
	}

}
