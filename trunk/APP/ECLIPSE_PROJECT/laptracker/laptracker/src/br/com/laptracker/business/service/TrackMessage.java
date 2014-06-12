package br.com.laptracker.business.service;

import java.text.SimpleDateFormat;

import br.com.laptracker.business.entities.UserTO;
import br.com.laptracker.business.exceptions.AppException;

public interface TrackMessage {

	// sample: 	2012 12 20 07 49 12
	//			yyyy MM dd HH mm ss
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
	void insert(UserTO user, String xmlContent) throws AppException;
}
