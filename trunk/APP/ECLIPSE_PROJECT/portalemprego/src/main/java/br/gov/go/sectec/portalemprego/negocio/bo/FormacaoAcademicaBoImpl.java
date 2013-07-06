package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.FormacaoAcademicaBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.FormacaoAcademicaDao;
import br.gov.go.sectec.portalemprego.comum.interfacedao.PersistenciaGenerica;
import br.gov.go.sectec.portalemprego.core.entidade.FormacaoAcademica;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.FormacaoAcademicaDTO;

@Service("formacaoAcademicaBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
public class FormacaoAcademicaBoImpl  extends PremiumBOImpl<FormacaoAcademica, Long> implements FormacaoAcademicaBo {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private FormacaoAcademicaDao formacaoAcademicaDao;

	@Override
	public List<FormacaoAcademicaDTO> listarFormacaoPorIdAluno(Long idAluno) {

		return formacaoAcademicaDao.listarFormacaoPorIdAluno(idAluno);
	}

	@Override
	FormacaoAcademicaDao getDAO() {

		return formacaoAcademicaDao;
	}

	@Override
	public void removerTodosPorIdAluno(Long idAluno) {

		formacaoAcademicaDao.removerTodosPorIdAluno(idAluno);
		
	}
	

}
