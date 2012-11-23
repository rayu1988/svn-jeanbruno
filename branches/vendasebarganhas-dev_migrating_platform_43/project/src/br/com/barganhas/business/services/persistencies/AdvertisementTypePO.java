package br.com.barganhas.business.services.persistencies;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.services.persistencies.management.AppPersistencyManagement;

@SuppressWarnings("serial")
@Repository
public class AdvertisementTypePO extends AppPersistencyManagement {

	@SuppressWarnings("unchecked")
	public List<AdvertisementTypeTO> list() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select ADVERTISEMENT_TYPE.id, ADVERTISEMENT_TYPE.title, ADVERTISEMENT_TYPE.value, ADVERTISEMENT_TYPE.score, ADVERTISEMENT_TYPE.totalPictures ");
		hql.append(" , ADVERTISEMENT_TYPE.daysToExpire from ").append(AdvertisementTypeTO.class.getName()).append(" ADVERTISEMENT_TYPE ");

		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		return query.list();
	}
	
	public AdvertisementTypeTO insert(AdvertisementTypeTO advertisementType) {
		this.getHibernateDao().insert(advertisementType);
		return advertisementType;
	}
	
	public AdvertisementTypeTO save(AdvertisementTypeTO advertisementType) {
		this.getHibernateDao().update(advertisementType);
		return advertisementType;
	}

	public AdvertisementTypeTO consult(AdvertisementTypeTO advertisementType) {
		return this.getHibernateDao().consult(advertisementType);
	}

	public void delete(AdvertisementTypeTO advertisementType) {
		this.getHibernateDao().delete(advertisementType);
	}
}
