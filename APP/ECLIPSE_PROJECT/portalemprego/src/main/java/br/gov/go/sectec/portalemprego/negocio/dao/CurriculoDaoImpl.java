package br.gov.go.sectec.portalemprego.negocio.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.CurriculoDao;
import br.gov.go.sectec.portalemprego.comum.utilitario.ValidatorUtil;
import br.gov.go.sectec.portalemprego.core.entidade.Curriculo;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CurriculoAlunoDTO;

@Repository("curriculoDao")
public class CurriculoDaoImpl extends PersistenciaGenericaJpa<Curriculo, Long> implements CurriculoDao {

	@Override
	public Long obterQtdCurriculoPorArea(final Long idAreaInteresse) {

		final Criteria criteria = this.novoCriteria();

		criteria.createAlias("aluno", "aluno");

		criteria.createAlias("aluno.cargoInteresse", "cargoInteresse");

		criteria.createAlias("cargoInteresse.areaInteresse", "areaInteresse");

		this.addCriteria(criteria, "areaInteresse.idAreaInteresse", idAreaInteresse);

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		criteria.setProjection(Projections.rowCount());

		if (criteria.list().get(0) == null) {

			return new Long(0);

		}

		final Long total = Long.valueOf(( criteria.list().get(0) ).toString());

		return total;
	}

	@Override
	public Integer obterQtdCurriculoPorCargo_Area(final Integer idCargo, final Integer idAreaInteresse) {

		// Criar código para retornar a quantidade de curriculos por cargo, combase na Area
		// selecionada
		// Em SQL ficaria algo assim:
		// SELECT COUNT(*) FROM adm_aluno WHERE id_area_interesse=? AND id_cargo_interesse=idCargo;
		return null;
	}

	@Override
	public Curriculo obterPorIdAluno(final Long idAluno) {

		final Criteria criteria = this.novoCriteria();

		criteria.createAlias("aluno", "aluno");

		this.addCriteria(criteria, "aluno.idAluno", idAluno);

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		criteria.setMaxResults(1);

		return (Curriculo) criteria.uniqueResult();
	}

	@Override
	public Curriculo alterar(final Curriculo entity) {

		final Curriculo curriculo = super.alterar(entity);

		this.getEntityManager().flush();

		return curriculo;
	}

	@Override
	public Long obterQtdCurriculoPorCargo(final Long idCargo) {

		final Criteria criteria = this.novoCriteria();

		criteria.createAlias("aluno", "aluno");

		criteria.createAlias("aluno.cargoInteresse", "cargoInteresse");

		this.addCriteria(criteria, "cargoInteresse.idCargoInteresse", idCargo);

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		criteria.setProjection(Projections.rowCount());

		if (criteria.list().get(0) == null) {

			return new Long(0);

		}

		final Long total = Long.valueOf(( criteria.list().get(0) ).toString());

		return total;
	}

	@Override
	public List<CurriculoAlunoDTO> listarCurriculoAluno(final Long idCidadeSelecao, final Integer idTipoSexo, final Integer idTipoFaixaEtaria, final Long idCargo) {

		final Criteria criteria = this.novoCriteria();

		criteria.createAlias("aluno", "aluno");

		criteria.createAlias("aluno.cargoInteresse", "cargoInteresse");

		criteria.createAlias("cargoInteresse.areaInteresse", "areaInteresse");

		criteria.createAlias("aluno.endereco", "endereco");

		criteria.createAlias("endereco.cidade", "cidade");

		this.addCriteria(criteria, "cidade.idCidade", idCidadeSelecao);

		this.addCriteria(criteria, "aluno.tpSexo", idTipoSexo);

		this.addCriteria(criteria, "cargoInteresse.idCargoInteresse", idCargo);

		if (ValidatorUtil.isNotNull(idTipoFaixaEtaria)) {

			this.addRestrictionFaixaEtaria(idTipoFaixaEtaria, criteria);

		}

		final Projection projList = Projections.projectionList().add(Projections.property("idCurriculo").as("idCurriculo")).add(Projections.property("aluno.idAluno").as("idAluno"))
				.add(Projections.property("aluno.dsNome").as("aluno"));

		criteria.setProjection(projList);

		criteria.addOrder(Order.asc("aluno.dsNome"));

		criteria.setProjection(Projections.distinct(projList));

		criteria.setResultTransformer(Transformers.aliasToBean(CurriculoAlunoDTO.class));

		return criteria.list();
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @param idTipoFaixaEtaria
	 * @param criteria
	 */
	private void addRestrictionFaixaEtaria(final Integer idTipoFaixaEtaria, final Criteria criteria) {

		Calendar inicio = null;

		Calendar fim = null;

		switch (idTipoFaixaEtaria) {

			case 1:

				inicio = Calendar.getInstance();

				inicio.add(Calendar.YEAR, -18);

				criteria.add(Restrictions.between("aluno.dtNascimento", inicio.getTime(), new Date()));

				break;

			case 2:

				inicio = Calendar.getInstance();

				fim = Calendar.getInstance();

				fim.add(Calendar.YEAR, -18);

				inicio.add(Calendar.YEAR, -20);

				criteria.add(Restrictions.between("aluno.dtNascimento", inicio.getTime(), fim.getTime()));

				break;

			case 3:

				inicio = Calendar.getInstance();

				fim = Calendar.getInstance();

				fim.add(Calendar.YEAR, -21);

				inicio.add(Calendar.YEAR, -25);

				criteria.add(Restrictions.between("aluno.dtNascimento", inicio.getTime(), fim.getTime()));

				break;

			case 4:

				inicio = Calendar.getInstance();

				fim = Calendar.getInstance();

				fim.add(Calendar.YEAR, -26);

				inicio.add(Calendar.YEAR, -30);

				criteria.add(Restrictions.between("aluno.dtNascimento", inicio.getTime(), fim.getTime()));

				break;

			case 5:

				inicio = Calendar.getInstance();

				fim = Calendar.getInstance();

				fim.add(Calendar.YEAR, -31);

				inicio.add(Calendar.YEAR, -40);

				criteria.add(Restrictions.between("aluno.dtNascimento", inicio.getTime(), fim.getTime()));

				break;

			case 6:

				inicio = Calendar.getInstance();

				fim = Calendar.getInstance();

				fim.add(Calendar.YEAR, -41);

				inicio.add(Calendar.YEAR, -50);

				criteria.add(Restrictions.between("aluno.dtNascimento", inicio.getTime(), fim.getTime()));

				break;

			case 7:

				fim = Calendar.getInstance();

				fim.add(Calendar.YEAR, -50);

				criteria.add(Restrictions.between("aluno.dtNascimento", fim.getTime(), fim.getTime()));

				break;
		}
	}

}
