package br.com.barganhas.business.services.persistencies;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.FileTempTO;
import br.com.barganhas.business.services.persistencies.management.AppPersistencyManagement;

@SuppressWarnings("serial")
@Repository
public class FileTempPO extends AppPersistencyManagement {

	public void clearTempFiles() {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date yesterday = calendar.getTime();
		
		StringBuffer hql = new StringBuffer();
		hql.append(" delete from ").append(FileTempTO.class.getName()).append(" FILE_TEMP ");
		hql.append(" where FILE_TEMP.sinceDate = :sinceDate ");
		
		Query query = this.getHibernateDao().createQuery(hql.toString());
		query.setDate("sinceDate", yesterday);
		query.executeUpdate();
	}
}
