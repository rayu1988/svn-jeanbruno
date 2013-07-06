package br.gov.go.sectec.portalemprego.negocio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.CidadeDao;
import br.gov.go.sectec.portalemprego.core.entidade.Cidade;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CidadeDTO;

@Repository("cidadeDao")
public class CidadeDaoImpl extends PersistenciaGenericaJpa<Cidade, Long> implements CidadeDao {

	/**
	 * Método responsável por obter a cidade pelo id de uf.
	 * 
	 * @author Silvânio
	 * 
	 * @param idUF
	 *            identificador uf.
	 * 
	 * @return lida de uf.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CidadeDTO> obterCidadePorIdUf(final Long idUF) {

		final Criteria criteria = this.novoCriteria();

		criteria.createAlias("uf", "uf");

		criteria.add(Restrictions.eq("uf.idUf", idUF));

		criteria.setProjection(Projections.projectionList().add(Projections.property("idCidade").as("idDTO")).add(Projections.property("dsNome").as("nomeDTO")));

		criteria.setResultTransformer(Transformers.aliasToBean(CidadeDTO.class));

		criteria.addOrder(Order.asc("dsNome"));

		return criteria.list();
	}

	/**
	 * Método responsável por sigla do estado
	 * 
	 * @author Silvânio
	 * 
	 * @param string
	 * 
	 * @return List<CidadeDTO>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CidadeDTO> obterCidadePorSiglaUf(String siglaUf) {

		final Criteria criteria = this.novoCriteria();

		criteria.createAlias("uf", "uf");

		criteria.add(Restrictions.eq("uf.dsSigla", siglaUf));

		criteria.setProjection(Projections.projectionList().add(Projections.property("idCidade").as("idDTO")).add(Projections.property("dsNome").as("nomeDTO")));

		criteria.setResultTransformer(Transformers.aliasToBean(CidadeDTO.class));

		criteria.addOrder(Order.asc("dsNome"));

		return criteria.list();
	}
}
