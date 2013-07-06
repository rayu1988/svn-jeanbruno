package br.gov.go.sectec.portalemprego.negocio.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.gov.go.sectec.portalemprego.comum.interfacedao.PersistenciaGenerica;
import br.gov.go.sectec.portalemprego.comum.utilitario.ValidatorUtil;

/**
 * JPA implementation of the GenericRepository. Note that this implementation also expects Hibernate
 * as JPA implementation. That's because we like the Criteria API.
 * 
 * @author José Ferreira de Souza Filho
 * @author $LastChangedBy: jlust $
 * @version $LastChangedRevision: 257 $
 * @param <T>
 *            The persistent type
 * @param <ID>
 *            The primary key type
 */
public class PersistenciaGenericaJpa<T, ID extends Serializable> implements PersistenciaGenerica<T, ID> {

	private final Class<T> persistentClass;

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public PersistenciaGenericaJpa() {

		this.persistentClass = (Class<T>) ( (ParameterizedType) getClass().getGenericSuperclass() ).getActualTypeArguments()[0];
	}

	public PersistenciaGenericaJpa( final Class<T> persistentClass ) {

		super();
		this.persistentClass = persistentClass;
	}

	public EntityManager getEntityManager() {

		return entityManager;
	}

	/**
	 * @see be.bzbit.framework.domain.repository.GenericRepository#countAll()
	 */
	@Override
	public int countAll() {

		return countByCriteria();
	}

	/**
	 * @see be.bzbit.framework.domain.repository.GenericRepository#countByExample(java.lang.Object)
	 */
	@Override
	public int countByExample(final T exampleInstance) {

		Session session = (Session) entityManager.getDelegate();
		Criteria crit = session.createCriteria(getEntityClass());
		crit.setProjection(Projections.rowCount());
		crit.add(Example.create(exampleInstance));

		return (Integer) crit.list().get(0);
	}

	/**
	 * @see be.bzbit.framework.domain.repository.GenericRepository#listar()
	 */
	@Override
	public List<T> listar() {

		return findByCriteria();
	}
	
	/**
	 * Método responsável por adicionar dados na criteria.
	 *
	 * @author Silvânio
	 *
	 * @param criteria
	 * 
	 * @param key
	 * 
	 * @param value
	 */
	protected void addCriteria(Criteria criteria, String key, Object value) {

		if(ValidatorUtil.isNotNull(value)){
			
			criteria.add(Restrictions.eq(key, value));
			
		}
		
	}
	/**
	 * Método responsável por adicionar dados na criteria.
	 *
	 * @author Silvânio
	 *
	 * @param criteria
	 * 
	 * @param key
	 * 
	 * @param value
	 */
	protected void addCriteria(Criteria criteria, String key, Long value) {
		
		if(ValidatorUtil.isNotNull(value) && value > new Long(0)){
			
			criteria.add(Restrictions.eq(key, value));
			
		}
		
	}
	/**
	 * Método responsável por adicionar dados na criteria.
	 *
	 * @author Silvânio
	 *
	 * @param criteria
	 * 
	 * @param key
	 * 
	 * @param value
	 */
	protected void addCriteria(Criteria criteria, String key, Integer value) {
		
		if(ValidatorUtil.isNotNull(value) && value > 0){
			
			criteria.add(Restrictions.eq(key, value));
			
		}
		
	}
	/**
	 * Método responsável por adicionar dados na criteria.
	 *
	 * @author Silvânio
	 *
	 * @param criteria
	 * 
	 * @param key
	 * 
	 * @param value
	 */
	protected void addCriteria(Criteria criteria, String key, String value) {
		
		if(!ValidatorUtil.isBlank(value)){
			
			criteria.add(Restrictions.eq(key, value));
			
		}
		
		
	}

	@SuppressWarnings("unchecked")
	public List<T> listar(String orderByColumn) {

		Session session = (Session) entityManager.getDelegate();
		Criteria criteria = session.createCriteria(getEntityClass());
		String[] colunas = orderByColumn.split(",");
		for (int i = 0; i < colunas.length; i++) {
			criteria.addOrder(Order.asc(colunas[i]));
		}
		final List<T> result = criteria.list();
		return result;
	}

	/**
	 * Método responsável por criar uma criteria.
	 *
	 * @author Silvânio
	 *
	 * @return Criteria.
	 */
	public Criteria novoCriteria() {

		Session session = (Session) entityManager.getDelegate();
		
		Criteria criteria = session.createCriteria(getEntityClass());
		
		return criteria;
	}

	/**
	 * @see be.bzbit.framework.domain.repository.GenericRepository#findByExample(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByExample(final T exampleInstance) {

		Session session = (Session) entityManager.getDelegate();
		Criteria crit = session.createCriteria(getEntityClass());
		final List<T> result = crit.list();
		return result;
	}

	/**
	 * @see be.bzbit.framework.domain.repository.GenericRepository#consultar(java.io.Serializable)
	 */
	@Override
	public T consultar(final ID id) {

		final T result = entityManager.find(persistentClass, id);
		return result;
	}

	/**
	 * @see be.bzbit.framework.domain.repository.GenericRepository#consultarPorNamedQuery(java.lang.String,
	 *      java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> consultarPorNamedQuery(final String name, Object... params) {

		javax.persistence.Query query = entityManager.createNamedQuery(name);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		final List<T> result = (List<T>) query.getResultList();
		return result;
	}

	/**
	 * @see be.bzbit.framework.domain.repository.GenericRepository#consultarPorNamedQueryAndNamedParams(java.lang.String,
	 *      java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> consultarPorNamedQueryAndNamedParams(final String name, final Map<String, ? extends Object> params) {

		javax.persistence.Query query = entityManager.createNamedQuery(name);
		for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
		final List<T> result = (List<T>) query.getResultList();
		entityManager.close();
		return result;
	}

	/**
	 * @see be.bzbit.framework.domain.repository.GenericRepository#getEntityClass()
	 */
	@Override
	public Class<T> getEntityClass() {

		return persistentClass;
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	protected List<T> findByCriteria(final Criterion... criterion) {

		return findByCriteria(-1, -1, criterion);
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(final int firstResult, final int maxResults, final Criterion... criterion) {

		Session session = (Session) entityManager.getDelegate();
		Criteria crit = session.createCriteria(getEntityClass());
		for (final Criterion c : criterion) {
			crit.add(c);
		}
		if (firstResult > 0) {
			crit.setFirstResult(firstResult);
		}
		if (maxResults > 0) {
			crit.setMaxResults(maxResults);
		}
		final List<T> result = crit.list();
		return result;
	}

	protected int countByCriteria(Criterion... criterion) {

		Session session = (Session) entityManager.getDelegate();
		Criteria crit = session.createCriteria(getEntityClass());
		crit.setProjection(Projections.rowCount());
		for (final Criterion c : criterion) {
			crit.add(c);
		}
		return (Integer) crit.list().get(0);
	}

	/**
	 * @see be.bzbit.framework.domain.repository.GenericRepository#excluir(java.lang.Object)
	 */
	@Override
	public void excluir(T entity) {

		entityManager.remove(entityManager.merge(entity));
	}

	/**
	 * @see be.bzbit.framework.domain.repository.GenericRepository#incluir(java.lang.Object)
	 */
	@Override
	public void incluir(T entity) {

		entityManager.persist(entity);
	}

	
	public Session getSession(){
		
		Session session = (Session) entityManager.getDelegate();
		
		return session;
	}
	
	public void removeAll(List<T> lista){
		
		for(T t : lista){
			
			excluir(t);
			
		}
		
	}
	
	/**
	 * @see be.bzbit.framework.domain.repository.GenericRepository#incluir(java.lang.Object)
	 */
	@Override 
	public T alterar(T entity) {

		entity =  entityManager.merge(entity);
		
		return entity;
	}
}
