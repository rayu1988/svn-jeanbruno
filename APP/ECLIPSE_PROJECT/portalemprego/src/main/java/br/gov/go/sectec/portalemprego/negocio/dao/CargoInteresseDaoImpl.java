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

import br.gov.go.sectec.portalemprego.comum.interfacedao.CargoInteresseDao;
import br.gov.go.sectec.portalemprego.comum.utilitario.ValidatorUtil;
import br.gov.go.sectec.portalemprego.core.entidade.CargoInteresse;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CargoInteresseDTO;

@Repository("cargoInteresseDao")
public class CargoInteresseDaoImpl extends PersistenciaGenericaJpa<CargoInteresse, Long> implements CargoInteresseDao {

	@Override
	public List<CargoInteresseDTO> listarCargoInteresse() {

		final Criteria criteria = this.novoCriteria();

		criteria.setProjection(Projections.projectionList().add(Projections.property("idCargoInteresse").as("idDTO")).add(Projections.property("dsCargoInteresse").as("nomeDTO")));

		criteria.setResultTransformer(Transformers.aliasToBean(CargoInteresseDTO.class));

		criteria.addOrder(Order.asc("dsCargoInteresse"));

		return criteria.list();
	}

	@Override
	public CargoInteresse obterCargoInteressePorIdAluno(final Long idAluno) {

		final Criteria criteria = this.novoCriteria();

		criteria.createAlias("aluno", "aluno");

		this.addCriteria(criteria, "aluno.idAluno", idAluno);

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		return (CargoInteresse) criteria.uniqueResult();
	}

	@Override
	public List<CargoInteresseDTO> listarCurriculoCargo(final Long idCidadeSelecao, final Integer idTipoSexo, final Integer idTipoFaixaEtaria, final Long idArea) {

		final Criteria criteria = this.novoCriteria();

		criteria.createAlias("aluno", "aluno");

		criteria.createAlias("areaInteresse", "areaInteresse");

		criteria.createAlias("aluno.curriculos", "curriculos");

		criteria.createAlias("aluno.endereco", "endereco");

		criteria.createAlias("endereco.cidade", "cidade");

		this.addCriteria(criteria, "cidade.idCidade", idCidadeSelecao);

		this.addCriteria(criteria, "aluno.tpSexo", idTipoSexo);

		this.addCriteria(criteria, "areaInteresse.idAreaInteresse", idArea);

		if (ValidatorUtil.isNotNull(idTipoFaixaEtaria)) {

			this.addRestrictionFaixaEtaria(idTipoFaixaEtaria, criteria);

		}

		final Projection projList = Projections.projectionList().add(Projections.property("idCargoInteresse").as("idDTO"))
				.add(Projections.property("dsCargoInteresse").as("nomeDTO"));

		criteria.setProjection(projList);

		criteria.addOrder(Order.asc("dsCargoInteresse"));

		criteria.setProjection(Projections.distinct(projList));

		criteria.setResultTransformer(Transformers.aliasToBean(CargoInteresseDTO.class));

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
