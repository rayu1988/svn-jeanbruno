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

import br.gov.go.sectec.portalemprego.comum.interfacedao.AreaInteresseDao;
import br.gov.go.sectec.portalemprego.comum.utilitario.ValidatorUtil;
import br.gov.go.sectec.portalemprego.core.entidade.AreaInteresse;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.AreaInteresseDTO;

@Repository("areaInteresseDao")
public class AreaInteresseDaoImpl extends PersistenciaGenericaJpa<AreaInteresse, Long> implements AreaInteresseDao {

	@Override
	public List<AreaInteresseDTO> listarAreaInteresseOrdenado() {

		final Criteria criteria = this.novoCriteria();

		criteria.setProjection(Projections.projectionList().add(Projections.property("idAreaInteresse").as("idDTO")).add(Projections.property("dsAreaInteresse").as("nomeDTO")));

		criteria.setResultTransformer(Transformers.aliasToBean(AreaInteresseDTO.class));

		criteria.addOrder(Order.asc("dsAreaInteresse"));

		return criteria.list();
	}

	
	@Override
	public List<AreaInteresseDTO> ListarAreaInteresse(Long idCidadeSelecao, Integer idTipoSexo, Integer idTipoFaixaEtaria, Long idAreaInteresSelecao) {

		final Criteria criteria = this.novoCriteria();
		

		criteria.createAlias("cargosInteresse", "cargosInteresse");

		criteria.createAlias("cargosInteresse.aluno", "aluno");

		criteria.createAlias("aluno.curriculos", "curriculos");

		criteria.createAlias("aluno.endereco", "endereco");

		criteria.createAlias("endereco.cidade", "cidade");
		
        addCriteria(criteria, "cidade.idCidade", idCidadeSelecao);
        
        addCriteria(criteria, "aluno.tpSexo", idTipoSexo);
        
        addCriteria(criteria, "idAreaInteresse", idAreaInteresSelecao);
        
        if(ValidatorUtil.isNotNull(idTipoFaixaEtaria)){

        	addRestrictionFaixaEtaria(idTipoFaixaEtaria, criteria);
        	
        }
		
        Projection projList = Projections.projectionList().add(Projections.property("idAreaInteresse").as("idDTO")).add(Projections.property("dsAreaInteresse").as("nomeDTO"));
        
        criteria.setProjection(projList);

        criteria.addOrder(Order.asc("dsAreaInteresse"));

		criteria.setProjection(Projections.distinct(projList));

		criteria.setResultTransformer(Transformers.aliasToBean(AreaInteresseDTO.class));

		
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
	private void addRestrictionFaixaEtaria(Integer idTipoFaixaEtaria, final Criteria criteria) {

		Calendar inicio = null;
        
        Calendar fim = null;

        switch(idTipoFaixaEtaria){
        	
        	case 1: 
        		
        	inicio = Calendar.getInstance();  
        		
        	inicio.add(Calendar.YEAR, -18);
        	
        	criteria.add(Restrictions.between("aluno.dtNascimento",inicio.getTime(), new Date()));
        	
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
