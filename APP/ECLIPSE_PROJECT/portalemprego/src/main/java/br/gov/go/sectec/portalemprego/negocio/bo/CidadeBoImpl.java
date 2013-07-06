package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.CidadeBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.CidadeDao;
import br.gov.go.sectec.portalemprego.core.entidade.Cidade;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CidadeDTO;

/**
 * <p>
 * <b>Title:</b> CidadeBoImpl.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Classe de negocio Cidade.
 * </p>
 * 
 * <p>
 * <b>Company: </b> Premium Consultoria e Sistemas
 * </p>
 * 
 * @author Silvânio
 * 
 * @version 1.0.0
 */
@Service("cidadeBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CidadeBoImpl extends PremiumBOImpl<Cidade, Long> implements CidadeBo {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CidadeDao cidadeDao;
	/**
	 * Método responsável por obter a lsita de cidade por id uf.
	 *
	 * @author Silvânio
	 *
	 * @param idUF
	 * 
	 * @return List<CidadeDTO>
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<CidadeDTO> obterCidadePorIdUf(final Long idUF) {

		return this.getDAO().obterCidadePorIdUf(idUF);
	}
	/**
	 * Método responsável por sigla do estado
	 *
	 * @author Silvânio
	 *
	 * @param string
	 * 
	 * @return List<CidadeDTO>
	 */
	@Override
	public List<CidadeDTO> obterCidadePorSiglaUf(String siglaUf) {

		return getDAO().obterCidadePorSiglaUf(siglaUf);
	}

	@Override
	CidadeDao getDAO() {

		return this.cidadeDao;
	}
}
