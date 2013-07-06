package br.gov.go.sectec.portalemprego.negocio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.TipoDeficienciaDao;
import br.gov.go.sectec.portalemprego.core.entidade.TipoDeficiencia;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoDeficienciaDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoTelefoneDTO;

@Repository("tipoDeficienciaDao")
public class TipoDeficienciaDaoImpl extends PersistenciaGenericaJpa<TipoDeficiencia, Long> implements TipoDeficienciaDao {


	@Override
	public List<TipoDeficienciaDTO> listarTipoDeficiencia() {

		final Criteria criteria = this.novoCriteria();

		criteria.setProjection(Projections.projectionList().add(Projections.property("idTipoDeficiencia").as("idDTO")).add(Projections.property("dsTipoDeficiencia").as("nomeDTO")));

		criteria.setResultTransformer(Transformers.aliasToBean(TipoTelefoneDTO.class));

		criteria.addOrder(Order.asc("dsTipoDeficiencia"));

		return criteria.list();
	}

	@Override
	public TipoDeficiencia obterTipoDeficienciaPorIdAluno(Long idAluno) {
		
		final Criteria criteria = this.novoCriteria();
		
		criteria.createAlias("alunos", "alunos");
		
		criteria.add(Restrictions.eq("alunos.idAluno", idAluno));
		
		criteria.setMaxResults(1);

		return (TipoDeficiencia) criteria.uniqueResult();
	}


}
