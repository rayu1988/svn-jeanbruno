package br.com.laptracker.business.persistence;

import java.sql.PreparedStatement;
import java.sql.Timestamp;

import br.com.laptracker.business.entities.LapTO;
import br.com.laptracker.business.entities.LapTrackTO;
import br.com.laptracker.business.entities.TrackMessageTO;
import br.com.laptracker.business.entities.UserTO;
import br.com.laptracker.business.exceptions.AppException;
import br.com.laptracker.business.persistence.control.PersistenceControl;

public class TrackMessagePO extends PersistenceControl {

	public void insert(UserTO user, TrackMessageTO trackMessage) {
		try {
			for (LapTrackTO lapTrack : trackMessage.getListLapTrack()) {
				StringBuffer sqlLapTrack = new StringBuffer();
				sqlLapTrack.append(" insert into LAP_TRACK (id_user, lap_type, date, distance_unit, distance_value, total_time_format");
				sqlLapTrack.append(" , total_time_value, distance_divisor_unit, distance_divisor_value, break_time) ");
				sqlLapTrack.append(" values ");
				sqlLapTrack.append(" (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
				
				PreparedStatement psLapTrack = this.prepareStatement(sqlLapTrack.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
				psLapTrack.setInt(1, user.getId().intValue());
				psLapTrack.setInt(2, lapTrack.getLapType().ordinal());
				psLapTrack.setTimestamp(3, new Timestamp(lapTrack.getDate().getTime()));
				psLapTrack.setInt(4, lapTrack.getDistanceUnit().ordinal());
				psLapTrack.setString(5, lapTrack.getDistanceValue());
				psLapTrack.setString(6, lapTrack.getTotalTimeFormat());
				psLapTrack.setString(7, lapTrack.getTotalTimeValue());
				psLapTrack.setInt(8, lapTrack.getDistanceDivisorUnit().ordinal());
				psLapTrack.setString(9, lapTrack.getDistanceDivisorValue());
				psLapTrack.setString(10, lapTrack.getBreakTime());
				
				psLapTrack.executeUpdate();
				lapTrack.setId(this.getIdInsert(psLapTrack));
				
				for (LapTO lap : lapTrack.getListLaps()) {
					StringBuffer sqlLap = new StringBuffer();
					sqlLap.append(" insert into LAP (id_lap_track, identifier, format, time, distance_unit, distance_value, break_time) ");
					sqlLap.append(" values ");
					sqlLap.append(" (?, ?, ?, ?, ?, ?, ?) ");
					
					PreparedStatement psLap = this.prepareStatement(sqlLap.toString());
					psLap.setLong(1, lapTrack.getId());
					psLap.setString(2, lap.getIdentifier());
					psLap.setString(3, lap.getFormat());
					psLap.setString(4, lap.getTime());
					if (lap.getDistanceUnit() != null) {
						psLap.setInt(5, lap.getDistanceUnit().ordinal());
					} else {
						psLap.setNull(5, 0);
					}
					psLap.setString(6, lap.getDistanceValue());
					psLap.setString(7, lap.getBreakTime());
					psLap.executeUpdate();
				}
			}
			
			this.commit();
		} catch (Exception e) {
			this.rollback();
			throw new AppException(e);
		} finally {
			this.close();
		}
	}
	
}
