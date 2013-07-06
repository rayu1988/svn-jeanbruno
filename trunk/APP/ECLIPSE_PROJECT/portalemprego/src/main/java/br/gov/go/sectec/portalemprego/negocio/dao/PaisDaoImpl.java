package br.gov.go.sectec.portalemprego.negocio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.PaisDao;
import br.gov.go.sectec.portalemprego.core.entidade.Pais;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.PaisDTO;

@Repository("paisDao")
public class PaisDaoImpl extends PersistenciaGenericaJpa<Pais, Long> implements PaisDao {

	/**
	 * Método responsável por listar pais ordenado pelo nome.
	 * 
	 * @author Silvânio
	 * 
	 * @return Lista de paises.
	 */
	@Override
	public List<PaisDTO> listarPaisOrdenado() {

		final Criteria criteria = this.novoCriteria();

		criteria.setProjection(Projections.projectionList().add(Projections.property("idPais").as("idDTO")).add(Projections.property("dsNome").as("nomeDTO")));

		criteria.setResultTransformer(Transformers.aliasToBean(PaisDTO.class));

		criteria.addOrder(Order.asc("dsNome"));

		return criteria.list();
	}

}
