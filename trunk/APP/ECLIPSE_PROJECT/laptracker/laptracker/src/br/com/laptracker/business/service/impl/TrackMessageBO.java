package br.com.laptracker.business.service.impl;

import br.com.laptracker.business.entities.TrackMessageTO;
import br.com.laptracker.business.entities.UserTO;
import br.com.laptracker.business.exceptions.AppException;
import br.com.laptracker.business.persistence.TrackMessagePO;
import br.com.laptracker.business.service.TrackMessage;
import br.com.laptracker.business.service.xstream.XStreamFactory;

public class TrackMessageBO implements TrackMessage {

	@Override
	public void insert(UserTO user, String xmlContent) throws AppException {
		try {
			TrackMessageTO trackMessage = XStreamFactory.getInstance().fromXML(xmlContent);
			new TrackMessagePO().insert(user, trackMessage);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

}
