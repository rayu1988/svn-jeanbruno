package br.com.barganhas.business.services.persistencies;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.SalesTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.persistencies.management.AppPersistencyManagement;

@SuppressWarnings("serial")
@Repository
public class SalesPO extends AppPersistencyManagement {

	@SuppressWarnings("unchecked")
	public List<SalesTO> list() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select SALES from ").append(SalesTO.class.getName()).append(" SALES ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		return query.list();
	}
	
	public SalesTO insert(SalesTO sales) {
		this.getHibernateDao().insert(sales);
		return sales;
	}
	
	public SalesTO save(SalesTO sales) {
		this.getHibernateDao().update(sales);
		return sales;
	}

	public SalesTO consult(SalesTO sales) {
		return this.getHibernateDao().consult(sales);
	}

	public void delete(SalesTO sales) {
		this.getHibernateDao().delete(sales);
	}
	
	public SalesTO consultBySalesCode(String salesCode) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select SALES from ").append(SalesTO.class.getName()).append(" SALES ");
		hql.append(" where SALES.salesCode = :salesCode ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		query.setString("salesCode", salesCode);
		
		SalesTO sales = (SalesTO) query.uniqueResult();
		
		if (sales == null) {
			throw new AppException("advertisementInvalidSalesCode");
		}
		
		return sales;
	}
}
