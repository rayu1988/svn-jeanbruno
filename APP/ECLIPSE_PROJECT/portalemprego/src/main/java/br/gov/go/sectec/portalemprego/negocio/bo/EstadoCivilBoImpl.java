package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.EstadoCivilBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.EstadoCivilDao;
import br.gov.go.sectec.portalemprego.core.entidade.EstadoCivil;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.EstadoCivilDTO;

@Service("estadoCivilBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EstadoCivilBoImpl extends PremiumBOImpl<EstadoCivil, Long> implements EstadoCivilBo {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EstadoCivilDao estadoCivilDao;

	/**
	 * Método responsável por listar os estados civil.
	 * 
	 * @author Silvânio
	 * 
	 * @return <code>List></code>
	 */
	@Override
	public List<EstadoCivilDTO> listarEstadoCivil() {

		return this.estadoCivilDao.listarEstadoCivil();
	}

	@Override
	EstadoCivilDao getDAO() {

		return this.estadoCivilDao;
	}

}
