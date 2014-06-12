package br.com.laptracker.business.service.impl;

import javax.jws.WebService;

import br.com.laptracker.business.entities.UserTO;
import br.com.laptracker.business.service.ImportTrackMessage;
import br.com.laptracker.business.service.User;
import br.com.laptracker.commons.enums.ImportTrackMessageResult;
import br.com.laptracker.web.service.ServiceBusinessFactory;

@WebService
public class ImportTrackMessageImpl implements ImportTrackMessage {

	@Override
	public ImportTrackMessageResult importTrackMessage(String nickname, String password, String xmlContent) {
		try {
			UserTO user = new UserTO();
			user.setNickname(nickname);
			user.setPassword(password);
			User serviceUser = ServiceBusinessFactory.getInstance().getUser();
			if ((user = serviceUser.validateLogin(user)) != null) {
				ServiceBusinessFactory.getInstance().getTrackMessage().insert(user, xmlContent);
				return ImportTrackMessageResult.SUCCESS;
			} else {
				return ImportTrackMessageResult.LOGIN_FAILED;
			}
		} catch (Exception e) {
			return ImportTrackMessageResult.FAIL;
		}
	}

	@Override
	public int teste() {
		return 121;
	}

}
