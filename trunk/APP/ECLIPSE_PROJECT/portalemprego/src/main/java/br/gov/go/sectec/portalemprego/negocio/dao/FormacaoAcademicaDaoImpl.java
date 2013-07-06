package br.gov.go.sectec.portalemprego.negocio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.FormacaoAcademicaDao;
import br.gov.go.sectec.portalemprego.comum.utilitario.ValidatorUtil;
import br.gov.go.sectec.portalemprego.core.entidade.FormacaoAcademica;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.FormacaoAcademicaDTO;

/**
 * 
 * <p>
 * <b>Title:</b> FormacaoAcademicaDaoImpl.java
 * </p>
 * 
 * <p>
 * <b>Description:</b>
 * </p>
 * 
 * <p>
 * <b>Company: </b> Premium Consultoria e Sistemas
 * </p>
 * 
 * @author Joffre
 * 
 * @version 1.0.0
 */
@Repository("formacaoAcademicaDao")
public class FormacaoAcademicaDaoImpl extends PersistenciaGenericaJpa<FormacaoAcademica, Long> implements FormacaoAcademicaDao {

	@Override
	public List<FormacaoAcademicaDTO> listarFormacaoAcademica() {

		final Criteria criteria = this.novoCriteria();

		criteria.createAlias("", "");

		criteria.setProjection(Projections.projectionList().add(Projections.property("idFormacaoAcademica").as("idDTO")).add(Projections.property("dsCurso").as("nomeDTO"))
				.add(Projections.property("dsInstituicao").as("dsInstituicaoDTO")).add(Projections.property("dtFimCurso").as("dtFimCursoDTO"))
				.add(Projections.property("dtInicioCurso").as("dtInicioCursoDTO")));
		// add(Projections.property("tipoGrau.idTipoGrau").as("idTipoGrauDTO")).
		// add(Projections.property("").as("nomeDTO")));

		criteria.setResultTransformer(Transformers.aliasToBean(FormacaoAcademicaDTO.class));

		criteria.addOrder(Order.asc(""));

		return criteria.list();
	}

	@Override
	public List<FormacaoAcademicaDTO> listarFormacaoPorIdAluno(final Long idAluno) {

		final Criteria criteria = this.novoCriteria();

		criteria.createAlias("tipoGrau", "tipoGrau");

		criteria.createAlias("curriculo", "curriculo");

		criteria.createAlias("curriculo.aluno", "aluno");

		this.addCriteria(criteria, "aluno.idAluno", idAluno);

		criteria.setProjection(Projections.projectionList().add(Projections.property("idFormacaoAcademica").as("idDTO")).add(Projections.property("dsCurso").as("curso"))
				.add(Projections.property("dsInstituicao").as("instituicao")).add(Projections.property("dtFimCurso").as("dtFinal"))
				.add(Projections.property("dtInicioCurso").as("dtInicial")).add(Projections.property("tipoGrau.idTipoGrau").as("idGrau"))
				.add(Projections.property("tipoGrau.dsTipoGrau").as("grau")));

		criteria.setResultTransformer(Transformers.aliasToBean(FormacaoAcademicaDTO.class));

		criteria.addOrder(Order.asc("dsCurso"));

		return criteria.list();
	}

	@Override
	public void removerTodosPorIdAluno(final Long idAluno) {

		final Criteria criteria = this.novoCriteria();

		criteria.createAlias("tipoGrau", "tipoGrau");

		criteria.createAlias("curriculo", "curriculo");

		criteria.createAlias("curriculo.aluno", "aluno");

		this.addCriteria(criteria, "aluno.idAluno", idAluno);

		List<FormacaoAcademica> lista =  criteria.list();

		 if(ValidatorUtil.isNotEmpty(lista)){
			   
			   removeAll(lista);
			   
		   }
		
	}

}
