package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.ExperienciaProfissionalBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.ExperienciaProfissionalDao;
import br.gov.go.sectec.portalemprego.comum.interfacedao.PersistenciaGenerica;
import br.gov.go.sectec.portalemprego.core.entidade.ExperienciaProfissional;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.ExperienciaProfissionalDTO;

@Service("experienciaProfissionalBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ExperienciaProfissionalBoImpl extends PremiumBOImpl<ExperienciaProfissional, Long> implements ExperienciaProfissionalBo {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ExperienciaProfissionalDao experienciaProfissionalDao;

	@Override
	ExperienciaProfissionalDao getDAO() {

		return this.experienciaProfissionalDao;
	}

	@Override
	public List<ExperienciaProfissionalDTO> listarExperienciaPorIdAluno(Long idAluno) {

		return getDAO().listarExperienciaPorIdAluno(idAluno);
	}

	@Override
	public void removerTodosPorIdAluno(Long idAluno) {

		 getDAO().removerTodosPorIdAluno(idAluno);
		
	}

}
