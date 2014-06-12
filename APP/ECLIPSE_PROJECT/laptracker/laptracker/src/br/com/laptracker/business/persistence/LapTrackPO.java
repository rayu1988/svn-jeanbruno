package br.com.laptracker.business.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.laptracker.business.entities.LapTO;
import br.com.laptracker.business.entities.LapTrackTO;
import br.com.laptracker.business.entities.UserTO;
import br.com.laptracker.business.exceptions.AppException;
import br.com.laptracker.business.persistence.control.PersistenceControl;
import br.com.laptracker.commons.enums.DistanceUnit;
import br.com.laptracker.commons.enums.LapType;

public class LapTrackPO extends PersistenceControl {

	public List<LapTrackTO> listLapTrack(UserTO user) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select id_lap_track, lap_type, date, distance_unit, distance_value ");
			sql.append(" , total_time_format, total_time_value from LAP_TRACK where id_user = ? ");
			sql.append(" order by date desc, id_lap_track desc ");
			
			PreparedStatement ps = this.prepareStatement(sql.toString());
			ps.setLong(1, user.getId());
			
			ResultSet result = ps.executeQuery();
			List<LapTrackTO> returning = new ArrayList<LapTrackTO>();
			while (result.next()) {
				LapTrackTO lapTrack = new LapTrackTO();
				lapTrack.setId(result.getLong("id_lap_track"));
				lapTrack.setLapType(LapType.values()[result.getInt("lap_type")]);
				lapTrack.setDate(new Date(result.getTimestamp("date").getTime()));
				Integer distanceUnit = result.getInt("distance_unit");
				if (distanceUnit != null) {
					lapTrack.setDistanceUnit(DistanceUnit.values()[distanceUnit]);
				}
				lapTrack.setDistanceValue(result.getString("distance_value"));
				lapTrack.setTotalTimeFormat(result.getString("total_time_format"));
				lapTrack.setTotalTimeValue(result.getString("total_time_value"));
				
				returning.add(lapTrack);
			}

			this.commit();
			
			return returning;
		} catch (Exception e) {
			this.rollback();
			throw new AppException(e);
		} finally {
			this.close();
		}
	}

	public void removeLapTrack(LapTrackTO lapTrack) {
		try {
			PreparedStatement ps = null;
			
			// removing LAPS related to this LAPTRACK
			ps = this.prepareStatement(" delete from LAP where id_lap_track = ? ");
			ps.setLong(1, lapTrack.getId());
			ps.executeUpdate();
			
			// removing the properly LAPTRACK
			ps = this.prepareStatement(" delete from LAP_TRACK where id_lap_track = ? ");
			ps.setLong(1, lapTrack.getId());
			ps.executeUpdate();
			
			this.commit();
		} catch (Exception e) {
			this.rollback();
			throw new AppException(e);
		} finally {
			this.close();
		}
	}
	
	public LapTrackTO consult(LapTrackTO lapTrack) {
		try {
			StringBuffer lapTrackerSql = new StringBuffer();
			lapTrackerSql.append(" select id_lap_track, lap_type, date, distance_unit, distance_value ");
			lapTrackerSql.append(" , total_time_format, total_time_value, distance_divisor_unit, distance_divisor_value ");
			lapTrackerSql.append(" , distance_unit, break_time ");
			lapTrackerSql.append(" from LAP_TRACK where id_lap_track = ? ");
			
			PreparedStatement ps = null;
			ResultSet result = null;
			
			ps = this.prepareStatement(lapTrackerSql.toString());
			ps.setLong(1, lapTrack.getId());
			
			result = ps.executeQuery();
			LapTrackTO returning = new LapTrackTO();
			if (result.next()) {
				returning.setId(result.getLong("id_lap_track"));
				returning.setLapType(LapType.values()[result.getInt("lap_type")]);
				returning.setDate(new Date(result.getTimestamp("date").getTime()));
				Integer distanceUnit = result.getInt("distance_unit");
				if (distanceUnit != null) {
					returning.setDistanceUnit(DistanceUnit.values()[distanceUnit]);
				}
				returning.setDistanceValue(result.getString("distance_value"));
				returning.setTotalTimeFormat(result.getString("total_time_format"));
				returning.setTotalTimeValue(result.getString("total_time_value"));
				returning.setDistanceDivisorUnit(DistanceUnit.values()[result.getInt("distance_divisor_unit")]);
				returning.setDistanceDivisorValue(result.getString("distance_divisor_value"));
				returning.setBreakTime(result.getString("break_time"));
			}
			
			StringBuffer lapSql = new StringBuffer();
			lapSql.append(" select identifier, format, time, distance_unit, distance_value, break_time ");
			lapSql.append(" from LAP where id_lap_track = ? ");
			
			ps = this.prepareStatement(lapSql.toString());
			ps.setLong(1, lapTrack.getId());
			
			result = ps.executeQuery();
			while (result.next()) {
				LapTO lap = new LapTO();
				lap.setIdentifier(result.getString("identifier"));
				lap.setFormat(result.getString("format"));
				lap.setTime(result.getString("time"));
				lap.setDistanceValue(result.getString("distance_value"));
				Integer distanceUnit = result.getInt("distance_unit");
				if (distanceUnit != null) {
					lap.setDistanceUnit(DistanceUnit.values()[distanceUnit]);
				}
				lap.setBreakTime(result.getString("break_time"));
				
				returning.getListLaps().add(lap);
			}
			
			this.commit();
			
			return returning;
		} catch (Exception e) {
			this.rollback();
			throw new AppException(e);
		} finally {
			this.close();
		}
	}
}
