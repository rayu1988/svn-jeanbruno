package br.gov.go.sectec.portalemprego.negocio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.UfDao;
import br.gov.go.sectec.portalemprego.core.entidade.Uf;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.UfDTO;

@Repository("ufDao")
public class UfDaoImpl extends PersistenciaGenericaJpa<Uf, Long> implements UfDao {

	/**
	 * Método responsável por retornar lista de ufs.
	 * 
	 * @author Silvânio
	 * 
	 * @param idPais
	 *            identificador do pais.
	 * 
	 * @return lista de uf.
	 */
	@Override
	public List<UfDTO> obterPorIdPais(final Long idPais) {

		final Criteria criteria = this.novoCriteria();

		criteria.createAlias("pais", "pais");

		criteria.add(Restrictions.eq("pais.idPais", idPais));

		criteria.setProjection(Projections.projectionList().add(Projections.property("idUf").as("idDTO")).add(Projections.property("dsNome").as("siglaDTO")));

		criteria.setResultTransformer(Transformers.aliasToBean(UfDTO.class));

		criteria.addOrder(Order.asc("dsSigla"));

		return criteria.list();
	}

}
