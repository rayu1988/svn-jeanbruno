package br.gov.go.sectec.portalemprego.negocio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.RamoAtividadeDao;
import br.gov.go.sectec.portalemprego.core.entidade.RamoAtividade;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.PaisDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.RamoAtividadeDTO;
import br.gov.go.sectec.portalemprego.negocio.dao.PersistenciaGenericaJpa;

@Repository("ramoAtividadeDao")
public class RamoAtividadeDaoImpl extends PersistenciaGenericaJpa<RamoAtividade, Long> implements RamoAtividadeDao {

	/**
	 * Método responsável por lsitar os ramos ordenados
	 *
	 * @author Silvânio
	 *
	 * @return lista de RamoAtividadeDTO.
	 */
	@Override
	public List<RamoAtividadeDTO> listarOrdenado() {

		Criteria criteria = this.novoCriteria();
		
		criteria.setProjection(Projections.projectionList().add(Projections.property("idRamoAtividade").as("idDTO")).add(Projections.property("dsRamoAtividade").as("nomeDTO")));

		criteria.setResultTransformer(Transformers.aliasToBean(RamoAtividadeDTO.class));
		
		criteria.addOrder(Order.asc("dsRamoAtividade"));

		return criteria.list();
	}


}
