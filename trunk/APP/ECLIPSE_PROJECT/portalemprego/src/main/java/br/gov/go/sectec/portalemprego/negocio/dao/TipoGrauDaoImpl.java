package br.gov.go.sectec.portalemprego.negocio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.TipoGrauDao;
import br.gov.go.sectec.portalemprego.core.entidade.TipoGrau;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoGrauDTO;

@Repository("tipoGrauDao")
public class TipoGrauDaoImpl extends PersistenciaGenericaJpa<TipoGrau, Long> implements TipoGrauDao {

	@Override
	public List<TipoGrauDTO> listarTipograu() {

		final Criteria criteria = this.novoCriteria();

		criteria.setProjection(Projections.projectionList().add(Projections.property("idTipoGrau").as("idDTO")).add(Projections.property("dsTipoGrau").as("nomeDTO")));

		criteria.setResultTransformer(Transformers.aliasToBean(TipoGrauDTO.class));

		criteria.addOrder(Order.asc("dsTipoGrau"));

		return criteria.list();
	}

}
