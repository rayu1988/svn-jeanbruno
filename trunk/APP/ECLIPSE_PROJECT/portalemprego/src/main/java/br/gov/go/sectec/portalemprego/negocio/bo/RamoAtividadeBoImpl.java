package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.RamoAtividadeBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.PersistenciaGenerica;
import br.gov.go.sectec.portalemprego.comum.interfacedao.RamoAtividadeDao;
import br.gov.go.sectec.portalemprego.core.entidade.RamoAtividade;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.RamoAtividadeDTO;

/**
 * <p>
 * <b>Title:</b> RamoAtividadeBoImpl.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> classe de negocio para ramo atividade
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
@Service("ramoAtividadeBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RamoAtividadeBoImpl extends PremiumBOImpl<RamoAtividade, Long> implements RamoAtividadeBo {

	private static final long serialVersionUID = 1L;

	@Autowired
	private RamoAtividadeDao ramoAtividadeDao;


	/**
	 * Método responsável por listar os dados do ramo ordenado.
	 *
	 * @author Silvânio
	 *
	 * @return lista de ramo atividade.
	 */
	@Override
	public List<RamoAtividadeDTO> listarOrdenado() {

		return ramoAtividadeDao.listarOrdenado();
	}
	
	@Override
	PersistenciaGenerica<RamoAtividade, Long> getDAO() {

		return this.ramoAtividadeDao;
	}

}
