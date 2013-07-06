package br.gov.go.sectec.portalemprego.comum.utilitario;

/**
 * 
 * <p>
 * <b>Title:</b> CpfCnpjUtil.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Classe para validação de CPF e CNPJ
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
public class CpfCnpjUtil {

	// Contador de digitos do CNPJ
	public static final int CNPJ_DIGITS = 14;

	// Mascara do CNPJ
	public static final String CNPJ_MASK = "##.###.###/####-##";

	// Contador de digitos do CPF
	public static final int CPF_DIGITS = 11;

	// Mascara do CPF
	public static final String CPF_MASK = "###.###.###-##";

	public static boolean isValid(String cpfOrCnpj) {

		if (cpfOrCnpj == null)
			return false;
		String n = cpfOrCnpj.replaceAll("[^0-9]", "");
		boolean isCnpj = n.length() == CNPJ_DIGITS;
		boolean isCpf = n.length() == CPF_DIGITS;
		if (!isCpf && !isCnpj)
			return false;
		int i;
		int j; // just count
		int digit; // A number digit
		int coeficient; // A coeficient
		int sum; // The sum of (Digit * Coeficient)
		int[] foundDv = { 0, 0 }; // The found Dv1 and Dv2
		int dv1 = Integer.parseInt(String.valueOf(n.charAt(n.length() - 2)));
		int dv2 = Integer.parseInt(String.valueOf(n.charAt(n.length() - 1)));
		for (j = 0; j < 2; j++) {
			sum = 0;
			coeficient = 2;
			for (i = n.length() - 3 + j; i >= 0; i--) {
				digit = Integer.parseInt(String.valueOf(n.charAt(i)));
				sum += digit * coeficient;
				coeficient++;
				if (coeficient > 9 && isCnpj)
					coeficient = 2;
			}
			foundDv[j] = 11 - sum % 11;
			if (foundDv[j] >= 10)
				foundDv[j] = 0;
		}
		return dv1 == foundDv[0] && dv2 == foundDv[1];
	}

	public static char getModule11Dv(String number, boolean isCpf) {

		int sum; // Sum of Multiply (Digit * Peso)
		int digit; // A number digit
		int coeficient; // A coeficient
		int dv; // Calculated Chek Digit
		// Remove literal characters
		String n = number.replaceAll("[^0-9]", "");
		// Sum Calculation
		sum = 0;
		coeficient = 2;
		for (int i = n.length() - 1; i >= 0; i--) {
			digit = Integer.parseInt(String.valueOf(n.charAt(i)));
			sum += digit * coeficient;
			coeficient++;
			if (coeficient > 9 && !isCpf)
				coeficient = 2;
		}
		// Module 11
		dv = 11 - sum % 11;
		if (dv >= 10)
			dv = 0; // must be beetwen 0 and 9
		return Integer.toString(dv).charAt(0);
	}

	public static String completeDv(String number) {

		if (number != null) {
			String n = number.replaceAll("[^0-9]", "");
			boolean isCpf = n.length() == 9;
			n = n + getModule11Dv(n, isCpf);
			n = n + getModule11Dv(n, isCpf);
			return n;
		} else {
			return null;
		}
	}

	public static String extractDv(String completeNumber) {

		if (completeNumber != null) {
			String n = completeNumber.replaceAll("[^0-9]", "");
			boolean isCpf = n.length() == 9;
			return "" + getModule11Dv(completeNumber, isCpf);
		} else {
			return null;
		}

	}

	// Mascara de CPF ou CNPJ
	private String mask = null;

	private String number = null;

	// Determina se vai haver auto-correcao no setCpfCnpj()
	private boolean autoCorrection = false;

	public CpfCnpjUtil() {

		super();
	}

	public CpfCnpjUtil( String cpfCnpj ) {

		super();
		setCpfCnpj(cpfCnpj);
	}

	// Compara o objeto
	public boolean equals(Object obj) {

		return this.toString().equals(obj.toString());
	}

	// Retorna a mascara do CPF/CNPJ
	public String getMask() {

		return mask;
	}

	// Retorna o numero do CPF/CNPJ sem a mascara
	public String getNumber() {

		return number;
	}

	// Retorna o CPF/CNPJ formatado
	public String getCpfCnpj() {

		if (number != null) {
			if (this.isCpf())
				return number.replaceAll("([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})", "$1\\.$2\\.$3-$4");
			else
				return number.replaceAll("([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})", "$1\\.$2\\.$3/$4-$5");
		} else
			return null;
	}

	// Determina se o objeto é um CNPJ
	public boolean isCnpj() {

		return number != null && number.length() == CNPJ_DIGITS;
	}

	// Determina se o objeto é um CPF
	public boolean isCpf() {

		return number != null && number.length() == CPF_DIGITS;
	}

	// Determina se o formato é válido
	public boolean isFormatValid() {

		return ( isCpf() || isCnpj() );// Must be CPF or CNPJ
	}

	// Determina se o objeto é válido
	public boolean isValid() {

		return isValid(getNumber());
	}

	// Seta o CPF/CNPJ para String
	public void setCpfCnpj(String cpfCnpj) {

		if (cpfCnpj != null) {
			number = cpfCnpj.replaceAll("[^0-9]*", "");
			if (isCpf()) {
				mask = CPF_MASK;
			} else if (isCnpj()) {
				mask = CNPJ_MASK;
			}
		} else
			number = null;
	}

	// Retorna o objeto Long representando o numero
	public long toLong() {

		if (number != null && number.length() > 0)
			return Long.parseLong(number);
		else
			return 0;
	}

	public String toString() {

		return getCpfCnpj();
	}

	public boolean isAutoCorrection() {

		return autoCorrection;
	}

	public void setAutoCorrection(boolean autoCorrection) {

		this.autoCorrection = autoCorrection;
	}

}
