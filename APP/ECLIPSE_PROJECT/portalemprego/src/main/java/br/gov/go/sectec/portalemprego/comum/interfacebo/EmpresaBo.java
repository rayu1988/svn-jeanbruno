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
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @return lista de pais.
	 */
	public List<PaisDTO> listarPais();

	/**
	 * M�todo respons�vel por obter pais por id.
	 * 
	 * @author Silv�nio
	 * 
	 * @param idPais
	 * 
	 * @return
	 */
	public Pais obterPaisPorId(Long idPais);

	/**
	 * M�todo respons�vel por retornar lista de ufs.
	 * 
	 * @author Silv�nio
	 * 
	 * @param idPais
	 *            identificador do pais.
	 * 
	 * @return lista de uf.
	 */
	public List<UfDTO> obetUfPorIdPais(Long idPais);

	/**
	 * M�todo respons�vel por obter a cidade pelo id de uf.
	 * 
	 * @author Silv�nio
	 * 
	 * @param idUF
	 *            identificador uf.
	 * 
	 * @return lida de uf.
	 */
	public List<CidadeDTO> obterCidadePorIdUf(Long idUF);

	/**
	 * M�todo respons�vel por obter a cidade pelo identificador.
	 *
	 * @author Silv�nio
	 *
	 * @param idCidade
	 * 
	 * @return
	 */
	public Cidade obterCidadePorId(Long idCidade);

	/**
	 * M�todo respons�vel por listar Tipos de telefone.
	 *
	 * @author Silv�nio
	 *
	 * @return lista de telefone.
	 */
	public List<TipoTelefoneDTO> listarTipoTelefone();

	/**
	 * M�todo respons�vel por
	 *
	 * @author Silv�nio
	 *
	 * @param idTipoTelefone
	 * 
	 * @return
	 */
	public TipoTelefone obterTipoTelefonePorId(Long idTipoTelefone);

	/**
	 * M�todo respons�vel por listar os ramos de atividades.
	 *
	 * @author Silv�nio
	 *
	 * @return lista de ramo de atividade.
	 */
	public List<RamoAtividadeDTO> listarRamoAtividade();

	/**
	 * M�todo respons�vel por obter os ramos de atividades.
	 *
	 * @author Silv�nio
	 *
	 * @param idRamoAtividade
	 * 
	 * @return
	 */
	public RamoAtividade obterRamoAtividadePorId(Long idRamoAtividade);

	/**
	 * M�todo respons�vel por sigla do estado
	 *
	 * @author Silv�nio
	 *
	 * @param string
	 * 
	 * @return List<CidadeDTO>
	 */
	public List<CidadeDTO> obterCidadePorSiglaUf(String string);

	/**
	 * M�todo respons�vel por listar os dados da area de interesse.
	 *
	 * @author Silv�nio
	 *
	 * @return lista de area
	 */
	public List<AreaInteresseDTO> listarAreaInteresse();

	/**
	 * M�todo respons�vel por obter a area de interesse por id.
	 *
	 * @author Silv�nio
	 *
	 * @param idArea
	 * 
	 * @return
	 */
	public AreaInteresse obterAreaInteressePorId(Long idArea);
}
