package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.AreaInteresse;
import br.gov.go.sectec.portalemprego.core.entidade.Cidade;
import br.gov.go.sectec.portalemprego.core.entidade.Empresa;
import br.gov.go.sectec.portalemprego.core.entidade.Pais;
import br.gov.go.sectec.portalemprego.core.entidade.RamoAtividade;
import br.gov.go.sectec.portalemprego.core.entidade.TipoTelefone;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.AreaInteresseDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CidadeDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.PaisDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.RamoAtividadeDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoTelefoneDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.UfDTO;

public interface EmpresaBo extends NegocioGenerico<Empresa, Integer> {

	public Empresa login(String dsLogin, String dsSenha);
	
	public Empresa obterEmpresaPeloId(Long id);
	
	public Empresa salvar(Empresa empresa);
	
	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @return lista de pais.
	 */
	public List<PaisDTO> listarPais();

	/**
	 * Método responsável por obter pais por id.
	 * 
	 * @author Silvânio
	 * 
	 * @param idPais
	 * 
	 * @return
	 */
	public Pais obterPaisPorId(Long idPais);

	/**
	 * Método responsável por retornar lista de ufs.
	 * 
	 * @author Silvânio
	 * 
	 * @param idPais
	 *            identificador do pais.
	 * 
	 * @return lista de uf.
	 */
	public List<UfDTO> obetUfPorIdPais(Long idPais);

	/**
	 * Método responsável por obter a cidade pelo id de uf.
	 * 
	 * @author Silvânio
	 * 
	 * @param idUF
	 *            identificador uf.
	 * 
	 * @return lida de uf.
	 */
	public List<CidadeDTO> obterCidadePorIdUf(Long idUF);

	/**
	 * Método responsável por obter a cidade pelo identificador.
	 *
	 * @author Silvânio
	 *
	 * @param idCidade
	 * 
	 * @return
	 */
	public Cidade obterCidadePorId(Long idCidade);

	/**
	 * Método responsável por listar Tipos de telefone.
	 *
	 * @author Silvânio
	 *
	 * @return lista de telefone.
	 */
	public List<TipoTelefoneDTO> listarTipoTelefone();

	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @param idTipoTelefone
	 * 
	 * @return
	 */
	public TipoTelefone obterTipoTelefonePorId(Long idTipoTelefone);

	/**
	 * Método responsável por listar os ramos de atividades.
	 *
	 * @author Silvânio
	 *
	 * @return lista de ramo de atividade.
	 */
	public List<RamoAtividadeDTO> listarRamoAtividade();

	/**
	 * Método responsável por obter os ramos de atividades.
	 *
	 * @author Silvânio
	 *
	 * @param idRamoAtividade
	 * 
	 * @return
	 */
	public RamoAtividade obterRamoAtividadePorId(Long idRamoAtividade);

	/**
	 * Método responsável por sigla do estado
	 *
	 * @author Silvânio
	 *
	 * @param string
	 * 
	 * @return List<CidadeDTO>
	 */
	public List<CidadeDTO> obterCidadePorSiglaUf(String string);

	/**
	 * Método responsável por listar os dados da area de interesse.
	 *
	 * @author Silvânio
	 *
	 * @return lista de area
	 */
	public List<AreaInteresseDTO> listarAreaInteresse();

	/**
	 * Método responsável por obter a area de interesse por id.
	 *
	 * @author Silvânio
	 *
	 * @param idArea
	 * 
	 * @return
	 */
	public AreaInteresse obterAreaInteressePorId(Long idArea);
}
