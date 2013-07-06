package br.gov.go.sectec.portalemprego.negocio.bo;

import java.io.Serializable;
import java.util.List;

import br.gov.go.sectec.portalemprego.comum.interfacedao.PersistenciaGenerica;


/**
 * <p>
 * <b>Title:</b> PremiumBO.java
 * </p>
 * 
 * <p>
 * <b>Description:</b>
 * </p>	
 * 	
 * <p>	
 * <b>Company: </b> Premium Consultoria e Sistemas
 * </p>	
 * 	
 * @author Silvânio
 * 
 * @version 1.0.0
 */
public abstract class PremiumBOImpl<T, ID extends Serializable> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public T consultar(ID id) {

		return getDAO().consultar(id);
	}

	public List<T> listar() {

		return getDAO().listar();
	}

	public T alterar(T entity) {

		return getDAO().alterar(entity);
	}

	public void excluir(T entity) {

		getDAO().excluir(entity);
		
	}

	public void incluir(T entity) {
		
		getDAO().incluir(entity);
		
	}
	
	abstract PersistenciaGenerica<T, ID> getDAO();
		
}
