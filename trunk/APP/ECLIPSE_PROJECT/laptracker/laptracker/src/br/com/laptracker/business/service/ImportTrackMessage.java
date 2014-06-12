package br.com.laptracker.business.service;

import javax.jws.WebParam;
import javax.jws.WebService;

import br.com.laptracker.commons.enums.ImportTrackMessageResult;

@WebService
public interface ImportTrackMessage {

	ImportTrackMessageResult importTrackMessage(
			@WebParam(name="nickname") String nickname,
			@WebParam(name="password") String password,
			@WebParam(name="xmlContent") String xmlContent
	);
	
	int teste();
	
}
