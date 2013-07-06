package br.gov.go.sectec.portalemprego.negocio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.TipoTelefoneDao;
import br.gov.go.sectec.portalemprego.core.entidade.TipoTelefone;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoTelefoneDTO;

@Repository("tipoTelefoneDao")
public class TipoTelefoneDaoImpl extends PersistenciaGenericaJpa<TipoTelefone, Long> implements TipoTelefoneDao {

	/**
	 * Método responsável por listar Tipos de telefone.
	 * 
	 * @author Silvânio
	 * 
	 * @return lista de telefone.
	 */
	@Override
	public List<TipoTelefoneDTO> listarTipoTelefone() {

		final Criteria criteria = this.novoCriteria();

		criteria.addOrder(Order.asc("dsTipoTelefone"));

		criteria.setProjection(Projections.projectionList().add(Projections.property("idTipoTelefone").as("idDTO")).add(Projections.property("dsTipoTelefone").as("nomeDTO")));

		criteria.setResultTransformer(Transformers.aliasToBean(TipoTelefoneDTO.class));

		return criteria.list();
	}

}
