package br.gov.go.sectec.portalemprego.negocio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.EstadoCivilDao;
import br.gov.go.sectec.portalemprego.core.entidade.EstadoCivil;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.EstadoCivilDTO;

@Repository("estadoCivilDao")
public class EstadoCivilDaoImpl extends PersistenciaGenericaJpa<EstadoCivil, Long> implements EstadoCivilDao {

	@Override
	public List<EstadoCivilDTO> listarEstadoCivil() {

		Criteria criteria = this.novoCriteria();


		criteria.setProjection(Projections.projectionList().add(Projections.property("idEstadoCivil").as("idDTO")).add(Projections.property("dsEstadoCivil").as("nomeDTO")));

		
		criteria.setResultTransformer(Transformers.aliasToBean(EstadoCivilDTO.class));
		
		criteria.addOrder(Order.asc("dsEstadoCivil"));

		return criteria.list();
	}

}
