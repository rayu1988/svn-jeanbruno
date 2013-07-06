package br.gov.go.sectec.portalemprego.negocio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.ExperienciaProfissionalDao;
import br.gov.go.sectec.portalemprego.comum.utilitario.ValidatorUtil;
import br.gov.go.sectec.portalemprego.core.entidade.ExperienciaProfissional;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.ExperienciaProfissionalDTO;

@Repository("experienciaProfissionalDao")
public class ExperienciaProfissionalDaoImpl extends PersistenciaGenericaJpa<ExperienciaProfissional, Long> implements ExperienciaProfissionalDao {

	@Override
	public List<ExperienciaProfissionalDTO> listarExperienciaProfissional() {

		final Criteria criteria = this.novoCriteria();
		
		criteria.setProjection(Projections.projectionList().
				add(Projections.property("idExperienciaProfissional").as("idDTO")).
				add(Projections.property("dsCargo").as("nomeDTO")).
				add(Projections.property("dsEmpresa").as("dsEmpresaDTO")).
				add(Projections.property("dsFuncao").as("dsFuncaoDTO")).
				add(Projections.property("dtDataEntrada").as("dtDataEntradaDTO")).
				add(Projections.property("dtDataSaida").as("dtDataSaidaDTO")));

		criteria.setResultTransformer(Transformers.aliasToBean(ExperienciaProfissionalDTO.class));

		criteria.addOrder(Order.asc("dsExperienciaProfissional"));

		return criteria.list();
	}

	@Override
	public List<ExperienciaProfissionalDTO> listarExperienciaPorIdAluno(Long idAluno) {

		final Criteria criteria = this.novoCriteria();
		
		   criteria.createAlias("curriculo", "curriculo");
			
		   criteria.createAlias("curriculo.aluno", "aluno");
			
		   addCriteria(criteria, "aluno.idAluno", idAluno);

		criteria.setProjection(Projections.projectionList().
				add(Projections.property("idExperienciaProfissional").as("idDTO")).
				add(Projections.property("dsCargo").as("dsCargo")).
				add(Projections.property("dsEmpresa").as("dsEmpresaDTO")).
				add(Projections.property("dsFuncao").as("dsFuncaoDTO")).
				add(Projections.property("dtDataEntrada").as("dtDataEntradaDTO")).
				add(Projections.property("dtDataSaida").as("dtDataSaidaDTO")));

		criteria.setResultTransformer(Transformers.aliasToBean(ExperienciaProfissionalDTO.class));

		return criteria.list();
	}

	@Override
	public void removerTodosPorIdAluno(Long idAluno) {

		final Criteria criteria = this.novoCriteria();
		
		   criteria.createAlias("curriculo", "curriculo");
			
		   criteria.createAlias("curriculo.aluno", "aluno");
			
		   addCriteria(criteria, "aluno.idAluno", idAluno);
		   
		   List<ExperienciaProfissional> lista = criteria.list();

		   if(ValidatorUtil.isNotEmpty(lista)){
			   
			   removeAll(lista);
			   
		   }
		   
	}

}
