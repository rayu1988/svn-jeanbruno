package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.io.Serializable;
import java.util.List;

public interface NegocioGenerico<T, ID extends Serializable> {

	/**
	 * Consulta uma entidade por chave primária
	 * 
	 * @param id
	 *            a chave primária
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
