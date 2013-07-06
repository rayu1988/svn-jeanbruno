package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import br.gov.go.sectec.portalemprego.core.entidade.DTO.TelefoneDTO;

/**
 * Repositзrio Genжrio para prover o bрsico das operaушes CRUD
 * 
 * @author Jose Ferreira de Souza Filho
 * @author $LastChangedBy:
 * @version $LastChangedRevision:
 * @param <T>
 *            o tipo da Entidade
 * @param <ID>
 *            a PK da Entidade
 */
public interface PersistenciaGenerica<T, ID extends Serializable> {

	/**
	 * Retorna a classe Class da entidade
	 * 
	 * @return a classe Class
	 */
	Class<T> getEntityClass();

	/**
	 * Consulta uma entidade por chave primрria
	 * 
	 * @param id
	 *            a chave primрria
	 * @return a entidade
	 */
	T consultar(final ID id);

	/**
	 * Lista todas a entidade
	 * 
	 * @return a lista das entidades
	 */
	List<T> listar();

	/**
	 * Lista todas as entidades
	 * 
	 * @param orderByColumn
	 *            columna para ser feito o order by
	 * @return lista de entidades
	 */
	List<T> listar(String orderByColumn);

	/**
	 * Find entities based on an example
	 * 
	 * @param exampleInstance
	 *            the example
	 * @return the list of entities
	 */
	List<T> findByExample(final T exampleInstance);

	/**
	 * Find using a named query
	 * 
	 * @param queryName
	 *            the name of the query
	 * @param params
	 *            the query parameters
	 * @return the list of entities
	 */
	List<T> consultarPorNamedQuery(final String queryName, Object... params);

	/**
	 * Find using a named query
	 * 
	 * @param queryName
	 *            the name of the query
	 * @param params
	 *            the query parameters
	 * @return the list of entities
	 */
	List<T> consultarPorNamedQueryAndNamedParams(final String queryName,
			final Map<String, ? extends Object> params);

	/**
	 * Count all entities
	 * 
	 * @return the number of entities
	 */
	int countAll();

	/**
	 * Count entities based on an example
	 * 
	 * @param exampleInstance
	 *            the search criteria
	 * @return the number of entities
	 */
	int countByExample(final T exampleInstance);

	/**
	 * save an entity. This can be either a INSERT or UPDATE in the database
	 * 
	 * @param entity
	 *            the entity to save
	 * @return the saved entity
	 */
	void incluir(final T entity);

	/**
	 * save an entity. This can be either a INSERT or UPDATE in the database
	 * 
	 * @param entity
	 *            the entity to save
	 * @return the saved entity
	 */
	T alterar(final T entity);

	/**
	 * delete an entity from the database
	 * 
	 * @param entity
	 *            the entity to delete
	 */
	void excluir(final T entity);

}
