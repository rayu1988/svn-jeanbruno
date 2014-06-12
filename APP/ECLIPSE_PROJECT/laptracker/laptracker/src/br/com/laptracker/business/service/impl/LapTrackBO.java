package br.com.laptracker.business.service.impl;

import java.util.List;

import br.com.laptracker.business.entities.LapTrackTO;
import br.com.laptracker.business.entities.UserTO;
import br.com.laptracker.business.exceptions.AppException;
import br.com.laptracker.business.persistence.LapTrackPO;
import br.com.laptracker.business.service.LapTrack;

public class LapTrackBO implements LapTrack {

	@Override
	public List<LapTrackTO> listLapTrack(UserTO user) {
		return new LapTrackPO().listLapTrack(user);
	}

	@Override
	public void removeLapTrack(LapTrackTO lapTrack) {
		if (lapTrack == null || lapTrack.getId() == null) {
			throw new AppException(new IllegalArgumentException("The parameter lapTrack is null or its id attribute is null."));
		}
		
		new LapTrackPO().removeLapTrack(lapTrack);
	}

	@Override
	public LapTrackTO consult(LapTrackTO lapTrack) {
		return new LapTrackPO().consult(lapTrack);
	}
	
}
