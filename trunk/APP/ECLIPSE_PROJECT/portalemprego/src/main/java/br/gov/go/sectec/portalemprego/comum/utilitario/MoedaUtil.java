package br.gov.go.sectec.portalemprego.comum.utilitario;

import java.math.BigDecimal;


public class MoedaUtil {
	
	public static BigDecimal obterBigDecimal(String dinheiro){
		
		return new BigDecimal((dinheiro).replace("R$", "").replaceAll("\\.", "").replaceAll(",", ".").trim());
	}

	public static String formataMoeda(BigDecimal vlr){
		java.text.DecimalFormat df = new java.text.DecimalFormat("R$ ###,###,##0.00");
		return df.format( vlr );
		}
	
}
