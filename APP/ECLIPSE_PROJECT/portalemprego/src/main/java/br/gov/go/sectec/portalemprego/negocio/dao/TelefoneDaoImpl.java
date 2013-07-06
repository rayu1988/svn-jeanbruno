package br.gov.go.sectec.portalemprego.negocio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.TelefoneDao;
import br.gov.go.sectec.portalemprego.comum.utilitario.ValidatorUtil;
import br.gov.go.sectec.portalemprego.core.entidade.Telefone;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TelefoneDTO;

@Repository("telefoneDao")
public class TelefoneDaoImpl extends PersistenciaGenericaJpa<Telefone, Long> implements TelefoneDao {

	@Override
	public List<TelefoneDTO> listarPorIdAluno(final Long idAluno) {

		final Criteria criteria = this.novoCriteria();

		criteria.createAlias("tipoTelefone", "tipoTelefone");

		criteria.createAlias("aluno", "aluno");

		this.addCriteria(criteria, "aluno.idAluno", idAluno);

		criteria.setProjection(Projections.projectionList().add(Projections.property("idTelefone").as("idDTO")).add(Projections.property("nuDdd").as("nuDdd"))
				.add(Projections.property("nuDdi").as("nuDdi")).add(Projections.property("nuTelefone").as("numeroDTO")).add(Projections.property("nuTelefone").as("numeroDTO"))
				.add(Projections.property("tipoTelefone.idTipoTelefone").as("idTipo")).add(Projections.property("tipoTelefone.dsTipoTelefone").as("dsTipo")));

		criteria.setResultTransformer(Transformers.aliasToBean(TelefoneDTO.class));

		return criteria.list();
	}

	@Override
	public void removerTodostelefonesPorIdAluno(final Long idAluno) {

		final Criteria criteria = this.novoCriteria();

		criteria.createAlias("aluno", "aluno");

		this.addCriteria(criteria, "aluno.idAluno", idAluno);

		final List<Telefone> lista = criteria.list();

		if (ValidatorUtil.isNotEmpty(lista)) {

			this.removeAll(lista);

		}

	}

}
