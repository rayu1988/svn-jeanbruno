package br.gov.go.sectec.portalemprego.negocio.dao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.AlunoDao;
import br.gov.go.sectec.portalemprego.core.entidade.Aluno;

@Repository("alunoDao")
public class AlunoDaoImpl extends PersistenciaGenericaJpa<Aluno, Long> implements AlunoDao {

	@Override
	public Aluno obterAlunoPorLoginSenha(String login, String senha) {

		Criteria criteria = this.novoCriteria();
		
		addCriteria(criteria, "dsLogin", login);
		
		addCriteria(criteria, "dsSenha", senha);
		
		return (Aluno) criteria.uniqueResult();
	}


}
