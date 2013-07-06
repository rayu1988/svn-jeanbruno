package br.gov.go.sectec.portalemprego.comum.enumerator;

/**
 * <p>
 * <b>Title:</b> FaixaEtariaEnum.java
 * </p>
 * 
 * <p>
 * <b>Description:</b>
 * </p>
 * 
 * <p>
 * <b>Company: </b> Premium Consultoria e Sistemas
 * </p>
 * 
 * @author Joffre
 * 
 * @version 1.0.0
 */
public enum FaixaEtariaEnum {

	Menor18("Menor de 18 anos", 1), 
	Maior18Menor20("De 18 anos a 20 anos", 2), 
	Maior21Menor25("De 21 anos a 25 anos", 3), 
	Maior26Menor30("De 26 anos a 30 anos", 4), 
	Maior31Menor40("De 31 anos a 40 anos", 5), 
	Maior41Menor50("De 41 anos a 50 anos", 6), 
	Maior50("Acima de 50 anos", 7);

	private FaixaEtariaEnum( final String descricao, final Integer tipo ) {

		this.descricao = descricao;
		
		this.tipo = tipo;
	}

	private String descricao;

	private Integer tipo;

	/**
	 * 
	 * Método responsável por retornar a Faixa Etaria com base no item selecionado
	 *
	 * @author Joffre
	 *
	 * @param faixa
	 * @return
	 */
	public static FaixaEtariaEnum get(final Integer faixa) {

		if (faixa.equals(1)) {
			return FaixaEtariaEnum.Menor18;
		} else if (faixa.equals(2)) {
			return FaixaEtariaEnum.Maior18Menor20;
		} else if (faixa.equals(3)) {
			return FaixaEtariaEnum.Maior21Menor25;
		} else if (faixa.equals(4)) {
			return FaixaEtariaEnum.Maior26Menor30;
		} else if (faixa.equals(5)) {
			return FaixaEtariaEnum.Maior31Menor40;
		} else if (faixa.equals(6)) {
			return FaixaEtariaEnum.Maior41Menor50;
		} else {
			return FaixaEtariaEnum.Maior50;
		}
	}

	public String getDescricao() {

		return this.descricao;
	}

	public void setDescricao(final String descricao) {

		this.descricao = descricao;
	}

	public Integer getTipo() {

		return this.tipo;
	}

	public void setTipo(final Integer tipo) {

		this.tipo = tipo;
	}
}
