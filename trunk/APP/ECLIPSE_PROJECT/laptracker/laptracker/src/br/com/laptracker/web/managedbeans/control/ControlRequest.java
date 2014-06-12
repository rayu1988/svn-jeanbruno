package br.com.laptracker.web.managedbeans.control;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.com.tatu.helper.parameter.Parameter;

import br.com.laptracker.commons.RequestMessage;
import br.com.laptracker.web.managedbeans.AppManagedBean;


public class ControlRequest implements Serializable {

	private static final long serialVersionUID = 2858561992068844192L;

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
