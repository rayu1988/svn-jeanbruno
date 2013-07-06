package br.gov.go.sectec.portalemprego.comum.interfacedao;
import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.ControleEncaminhamento;
import br.gov.go.sectec.portalemprego.comum.interfacedao.PersistenciaGenerica;

public interface ControleEncaminhamentoDao extends PersistenciaGenerica<ControleEncaminhamento, Integer> {
	public ControleEncaminhamento consultar(Integer id);
	public List<ControleEncaminhamento> listar();
	public void incluir(ControleEncaminhamento controleEncaminhamento);
	public ControleEncaminhamento alterar(ControleEncaminhamento controleEncaminhamento);
	public void excluir(ControleEncaminhamento controleEncaminhamento);
}
