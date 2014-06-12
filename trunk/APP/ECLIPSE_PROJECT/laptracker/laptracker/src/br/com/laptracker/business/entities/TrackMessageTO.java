package br.com.laptracker.business.entities;

import java.util.List;

@SuppressWarnings("serial")
public class TrackMessageTO extends TransferObject {

	private List<LapTrackTO> 			listLapTrack;

	// GETTERS AND SETTERS //
	public List<LapTrackTO> getListLapTrack() {
		return listLapTrack;
	}
	
	public void setListLapTrack(List<LapTrackTO> listLapTrack) {
		this.listLapTrack = listLapTrack;
	}
	
}
