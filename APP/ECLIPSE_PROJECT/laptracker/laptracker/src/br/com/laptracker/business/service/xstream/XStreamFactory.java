package br.com.laptracker.business.service.xstream;

import java.io.File;

import br.com.laptracker.business.entities.LapTO;
import br.com.laptracker.business.entities.LapTrackTO;
import br.com.laptracker.business.entities.TrackMessageTO;
import br.com.laptracker.business.service.xstream.converter.DateConverter;
import br.com.laptracker.business.service.xstream.converter.DistanceUnitConverter;
import br.com.laptracker.business.service.xstream.converter.LapTypeConverter;

import com.thoughtworks.xstream.XStream;

public class XStreamFactory {

	private XStream 						xStream;
	private static XStreamFactory 			xStreamFactory;
	
	private XStreamFactory() {
		this.xStream = new XStream();
		this.build();
	}
	
	public static XStreamFactory getInstance() {
		if (xStreamFactory == null) {
			xStreamFactory = new XStreamFactory();
		}
		return xStreamFactory;
	}
	
	private void build() {
		xStream.alias("trackmessage", TrackMessageTO.class);
		xStream.alias("laptrack", LapTrackTO.class);
		xStream.alias("lap", LapTO.class);
		
		xStream.addImplicitCollection(TrackMessageTO.class, "listLapTrack");
		xStream.addImplicitCollection(LapTrackTO.class, "listLaps");
		
		xStream.aliasField("id", LapTrackTO.class, "date");
		xStream.aliasField("tipo", LapTrackTO.class, "lapType");
		xStream.aliasField("distanciaUnidade", LapTrackTO.class, "distanceUnit");
		xStream.aliasField("distanciaValor", LapTrackTO.class, "distanceValue");
		xStream.aliasField("tempoTotalFormato", LapTrackTO.class, "totalTimeFormat");
		xStream.aliasField("tempoTotalValor", LapTrackTO.class, "totalTimeValue");
		xStream.aliasField("tempoDescanso", LapTrackTO.class, "breakTime");
		xStream.aliasField("divisaoDistanciaUnidade", LapTrackTO.class, "distanceDivisorUnit");
		xStream.aliasField("divisaoDistanciaValor", LapTrackTO.class, "distanceDivisorValue");
		xStream.aliasField("id", LapTO.class, "identifier");
		xStream.aliasField("tempoFormato", LapTO.class, "format");
		xStream.aliasField("tempo", LapTO.class, "time");
		xStream.aliasField("tempoDescanso", LapTO.class, "breakTime");
		xStream.aliasField("UnidadeDistancia", LapTO.class, "distanceUnit");
		xStream.aliasField("distancia", LapTO.class, "distanceValue");
		
		xStream.registerLocalConverter(LapTrackTO.class, "lapType", new LapTypeConverter());
		xStream.registerLocalConverter(LapTrackTO.class, "date", new DateConverter());
		xStream.registerLocalConverter(LapTrackTO.class, "distanceUnit", new DistanceUnitConverter());
		xStream.registerLocalConverter(LapTrackTO.class, "distanceDivisorUnit", new DistanceUnitConverter());
		xStream.registerLocalConverter(LapTO.class, "distanceUnit", new DistanceUnitConverter());
	}
	
	public String toXML(Object obj) {
		return xStream.toXML(obj);
	}
	
	public TrackMessageTO fromXML(File file) {
		return (TrackMessageTO) xStream.fromXML(file);
	}
	
	public TrackMessageTO fromXML(String xmlContent) {
		return (TrackMessageTO) xStream.fromXML(xmlContent);
	}
	
}
