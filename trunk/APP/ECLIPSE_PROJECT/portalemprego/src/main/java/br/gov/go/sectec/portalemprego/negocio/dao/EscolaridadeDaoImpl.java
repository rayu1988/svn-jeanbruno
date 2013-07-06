package br.gov.go.sectec.portalemprego.negocio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.EscolaridadeDao;
import br.gov.go.sectec.portalemprego.core.entidade.Escolaridade;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.EscolaridadeDTO;

@Repository("escolaridadeDao")
public class EscolaridadeDaoImpl extends PersistenciaGenericaJpa<Escolaridade, Long> implements EscolaridadeDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<EscolaridadeDTO> listarEscolaridade() {

		final Criteria criteria = this.novoCriteria();

		criteria.setProjection(Projections.projectionList().add(Projections.property("idEscolaridade").as("idDTO")).add(Projections.property("dsEscolaridade").as("nomeDTO")));

		criteria.setResultTransformer(Transformers.aliasToBean(EscolaridadeDTO.class));

		criteria.addOrder(Order.asc("dsEscolaridade"));

		return criteria.list();
	}

}
