package br.com.laptracker.ws.client;

import br.com.laptracker.business.service.ImportTrackMessageProxy;
import br.com.laptracker.business.service.ImportTrackMessageResult;

public class ClientWrapper {

	private String 			portAddress;
	
	public ClientWrapper(String portAddress) {
		if (portAddress ==  null || portAddress.isEmpty()) {
			throw new IllegalArgumentException("portAddress is mandatory");
		}
		this.portAddress = portAddress;
	}
	
	public ImportTrackMessageResult importTrackMessage(String nickname, String password, String xmlContent) {
		try {
			return new ImportTrackMessageProxy(this.portAddress).getImportTrackMessage().importTrackMessage(nickname, password, xmlContent);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
