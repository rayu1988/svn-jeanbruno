package br.gov.go.sectec.portalemprego.negocio.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.EnderecoBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.EnderecoDao;
import br.gov.go.sectec.portalemprego.comum.interfacedao.PersistenciaGenerica;
import br.gov.go.sectec.portalemprego.core.entidade.Endereco;

@Service("enderecoBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
public class EnderecoBoImpl extends PremiumBOImpl<Endereco, Integer>  implements EnderecoBo {

	private static final long serialVersionUID = 1L;
	@Autowired
	private EnderecoDao enderecoDao;

	@Override
	PersistenciaGenerica<Endereco, Integer> getDAO() {

		return enderecoDao;
	}
	

}
