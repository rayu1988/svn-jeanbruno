package br.com.barganhas.web.phaselistener;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

@SuppressWarnings("serial")
public class AppPhaseListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent phaseEvent) {
		FacesContext context = phaseEvent.getFacesContext();
		if (context.getViewRoot() == null) {
			try {
				context.getExternalContext().redirect("/xhtml/exceptions/expiredException.jsf");
				context.responseComplete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent phaseEvent) { }

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
