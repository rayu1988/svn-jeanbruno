package br.com.laptracker.business.service;

import java.util.List;

import br.com.laptracker.business.entities.LapTrackTO;
import br.com.laptracker.business.entities.UserTO;


public interface LapTrack {

	List<LapTrackTO> listLapTrack(UserTO user);
	void removeLapTrack(LapTrackTO lapTrack);
	LapTrackTO consult(LapTrackTO lapTrack);
	
}
