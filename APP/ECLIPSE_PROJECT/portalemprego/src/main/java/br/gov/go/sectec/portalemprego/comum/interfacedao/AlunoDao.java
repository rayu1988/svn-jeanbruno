package br.gov.go.sectec.portalemprego.comum.interfacedao;

import br.gov.go.sectec.portalemprego.core.entidade.Aluno;

public interface AlunoDao extends PersistenciaGenerica<Aluno, Long> {

	/**
	 * M�todo respons�vel por obter aluno por login e senha.
	 * 
	 * @author Silv�nio
	 * 
	 * @param login
	 * 
	 * @param senha
	 * 
	 * @return <code>Aluno</code>
	 */
	Aluno obterAlunoPorLoginSenha(String login, String senha);
}
