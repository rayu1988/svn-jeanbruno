package br.com.laptracker.web.service;

import java.io.Serializable;

import br.com.laptracker.business.service.LapTrack;
import br.com.laptracker.business.service.TrackMessage;
import br.com.laptracker.business.service.User;
import br.com.laptracker.business.service.impl.LapTrackBO;
import br.com.laptracker.business.service.impl.TrackMessageBO;
import br.com.laptracker.business.service.impl.UserBO;

@SuppressWarnings("serial")
public class ServiceBusinessFactory implements Serializable {
	
	private ServiceBusinessFactory() { }
	
	private static ServiceBusinessFactory INSTANCE = new ServiceBusinessFactory();
	
	public static ServiceBusinessFactory getInstance() {
		return INSTANCE;
	}
	
	public User getUser() {
		return new UserBO();
	}
	
	public LapTrack getLapTrack() {
		return new LapTrackBO();
	}
	
	public TrackMessage getTrackMessage() {
		return new TrackMessageBO();
	}
}
