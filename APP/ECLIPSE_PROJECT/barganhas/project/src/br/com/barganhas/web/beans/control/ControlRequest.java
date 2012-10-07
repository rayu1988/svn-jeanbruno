package br.com.barganhas.web.beans.control;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.com.tatu.helper.checking.Parameter;

import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.web.beans.AppManagedBean;

public abstract class ControlRequest implements Serializable {

	private static final long serialVersionUID = -1156195375897029091L;

	public void setRequestParameter(HttpServletRequest request, String alias, Object value) {
		Parameter.check(request).notNull();
		request.setAttribute(alias, value);
	}
	
	public void setRequestMessage(HttpServletRequest request, RequestMessage requestMessage) {
		this.setRequestMessages(request, Arrays.asList(new RequestMessage[]{requestMessage}));
	}
	
	public void setRequestMessages(HttpServletRequest request, List<RequestMessage> requestMessages) {
		this.setRequestParameter(request,AppManagedBean.LIST_TEXT_MESSAGES, requestMessages);
		this.setRequestParameter(request, AppManagedBean.SHOW_MESSAGE_BOX, Boolean.TRUE);
	}
}
