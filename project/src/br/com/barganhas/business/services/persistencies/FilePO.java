package br.com.barganhas.business.services.persistencies;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.services.persistencies.management.AppPersistencyManagement;

@SuppressWarnings("serial")
@Repository
public class FilePO extends AppPersistencyManagement {

	@SuppressWarnings("unchecked")
	public List<FileTO> list() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select FILE from ").append(FileTO.class.getName()).append(" FILE ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		return query.list();
	}
	
	public FileTO insert(FileTO file) {
		this.getHibernateDao().insert(file);
		return file;
	}
	
	public FileTO save(FileTO file) {
		this.getHibernateDao().update(file);
		return file;
	}

	public FileTO consult(FileTO file) {
		return this.getHibernateDao().consult(file);
	}
	
	public void delete(FileTO file) {
		this.getHibernateDao().delete(file);
	}
}
