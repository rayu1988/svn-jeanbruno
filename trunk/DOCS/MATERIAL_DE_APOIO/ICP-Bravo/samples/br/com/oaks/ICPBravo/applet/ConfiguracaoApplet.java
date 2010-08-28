package br.com.oaks.ICPBravo.applet;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.com.oaks.ICPBravo.algorithm.asymmetric.AsymmetricAlgorithm;
import br.com.oaks.ICPBravo.algorithm.digest.DigestAlgorithm;
import br.com.oaks.ICPBravo.algorithm.symmetric.SymmetricAlgorithm;
import br.com.oaks.ICPBravo.asn1.oiw.OIWObjectIdentifiers;
import br.com.oaks.ICPBravo.certs.ICPBravoCertificate;
import br.com.oaks.ICPBravo.cms.policies.AdAPolicy;
import br.com.oaks.ICPBravo.cms.policies.AdCPolicy;
import br.com.oaks.ICPBravo.cms.policies.AdCpPolicy;
import br.com.oaks.ICPBravo.cms.policies.AdRPolicy;
import br.com.oaks.ICPBravo.cms.policies.AdTPolicy;
import br.com.oaks.ICPBravo.cms.policies.CMSPolicy;
import br.com.oaks.ICPBravo.cms.policies.Policy;
import br.com.oaks.ICPBravo.tsp.TimestampRequester;
import br.com.oaks.ICPBravo.util.HttpLoad.ProxyInfo;
import br.com.oaks.ICPBravo.exceptions.CryptographicDeviceNotFoundException;
import br.com.oaks.ICPBravo.manager.ICPBravoManager;
import br.com.oaks.ICPBravo.manager.PKCS11Manager;
import br.com.oaks.ICPBravo.util.HttpLoad;
import br.com.oaks.ICPBravo.util.ICPBravoUtil;

public class ConfiguracaoApplet implements Serializable {
	public static String ccedil;
	public static String Atilde;
	public static String atilde;
	public static String otilde;
	public static String agrave;
	public static String aacute;
	public static String eacute;
	public static String ecirc;
	public static String Ocirc;
	public static String Aacute;
	public static String Eacute;
	public static String iacute;
	public static String oacute;
	public static String uacute;

	static {
		if (ICPBravoUtil.isWindows()) {
			ccedil = "ç";
			Atilde = "Ã";
			atilde = "ã";
			otilde = "õ";
			agrave = "à";
			aacute = "á";
			eacute = "é";
			ecirc = "ê";
			Ocirc = "Ô";
			Aacute = "Á";
			Eacute = "É"; 
			iacute = "í";
			oacute = "ó";
			uacute = "ú";
		} else {
			ccedil = "ç";
			Atilde = "Ã";
			atilde = "ã";
			otilde = "õ";
			agrave = "à";
			aacute = "á";
			eacute = "é";
			ecirc = "ê";
			Ocirc = "Ô";
			Aacute = "Á";
			Eacute = "É"; 
			iacute = "í";
			oacute = "ó";
			uacute = "ú";
		}
	}
	private static final long serialVersionUID = 8L;

	public static final int REPOSITORIO_PKCS11	= 0;
	public static final int REPOSITORIO_PKCS12	= 1;
	public static final int REPOSITORIO_WindowsMy	= 2;
	public static final int REPOSITORIO_WindowsROOT	= 3;
	public static final int REPOSITORIO_CAPI	= 4;
	public static final int REPOSITORIO_JKS	= 5;
	public static final int REPOSITORIO_Default	= 6;
	
	private static final String [] tiposRepositorio = {
    	"PKCS11", 
    	"PKCS12", 
    	"Windows-MY",
    	"Windows-ROOT", 
    	"CAPI",
    	"JKS",
    	"Default",
    	};
	
	public static final int MOSTRAR_EM_ARVORE	= 0;
	public static final int MOSTRAR_EM_COMBO	= 1;
    private static String [] formasMostrarCertificados = {
    	ConfiguracaoApplet.Aacute+"rvore", 
    	"Combobox"};
	public static final int MOSTRAR_NA_MESMA_TELA		= 0;
	public static final int MOSTRAR_EM_TELA_SEPARADA	= 1;
    private static String [] formasMostrarAssinaturas = {
    	"Na mesma tela", 
    	"Em tela separada"};
    public static final int TIPO_WINDOWS 		= 0;
    public static final int TIPO_MOTIF   		= 1;
    public static final int TIPO_METAL   		= 2;
    public static final int TIPO_WINDOWSClassic = 3;
    
    private static String [] tabelaLookAndFeel = {
    	"com.sun.java.swing.plaf.windows.WindowsLookAndFeel", 
    	"com.sun.java.swing.plaf.motif.MotifLookAndFeel", 
    	"javax.swing.plaf.metal.MetalLookAndFeel", 
		"com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel"};
	
	private static String[] algDigest = new String[] {OIWObjectIdentifiers.TAG_SHA1, OIWObjectIdentifiers.TAG_SHA256, OIWObjectIdentifiers.TAG_MD5};
	private static String[] algSimetrico = new String[] {OIWObjectIdentifiers.TAG_AES128CBC, OIWObjectIdentifiers.TAG_AES128ECB, OIWObjectIdentifiers.TAG_AES128OFB, OIWObjectIdentifiers.TAG_AES128Wrap, OIWObjectIdentifiers.TAG_TripleDES112ECB, OIWObjectIdentifiers.TAG_TripleDES168ECB};

	private String _nomeArquivoConfiguracao;
	private int _tipoRepositorio = REPOSITORIO_PKCS11;
	private int _formaMostrarCertificados = MOSTRAR_EM_COMBO;
	private int _formaMostrarArquivos = MOSTRAR_EM_TELA_SEPARADA;
	private int _formaMostrarAssinaturas = MOSTRAR_EM_TELA_SEPARADA;
	private int _tipoLookAndFeel = TIPO_METAL;
	private String _urlTST = "";
	private String _policy = "";
	private String _algoritmosDigest = "SHA1";
	private String _algoritmosAssinatura = "RSA1024";
	private String _algoritmosCriptografia = "AES128CBC";
	private String _ultimoDiretorio = "";
	private boolean _permiteAssinaturaComCertificadoExpiradoOuInvalido=false;
	private boolean _permiteAssinaturaComCertificadoSemValidarCRL=true;
	private boolean _permiteAssinaturaComCertificadoNaoDigitalSignature=false;
	private boolean _permiteAssinaturaComCertificadoNaoNonRepudiation=true;
	private boolean _permiteAssinaturaComCertificadoNaoKeyEncipherment=true;
	private boolean _permiteAssinaturaComCertificadoNaoDataEncipherment=true;
	private boolean _permiteAssinaturaComCertificadoNaoKeyAgreement=true;
	private boolean _permiteAssinaturaComCertificadoNaoKeyCertisign=true;
	private boolean _permiteAssinaturaComCertificadoNaoCrlSign=true;
	private boolean _permiteAssinaturaComCertificadoNaoEncipherOnly=true;
	private boolean _permiteAssinaturaComCertificadoNaoDecipherOnly=true;

	private boolean _permiteAssinaturaComCertificadoNaoServerAuth=true;
	private boolean _permiteAssinaturaComCertificadoNaoClientAuth=true;
	private boolean _permiteAssinaturaComCertificadoNaoCodeSigning=true;
	private boolean _permiteAssinaturaComCertificadoNaoEmailProtection=true;
	private boolean _permiteAssinaturaComCertificadoNaoIpsecEndSystem=true;
	private boolean _permiteAssinaturaComCertificadoNaoIpsecTunnel=true;
	private boolean _permiteAssinaturaComCertificadoNaoIpsecUser=true;
	private boolean _permiteAssinaturaComCertificadoNaoTimeStamping=true;
	private boolean _permiteAssinaturaComCertificadoNaoOCSPSigning=true;
	private boolean _permiteAssinaturaComCertificadoNaoDvcs=true;
	private boolean _permiteAssinaturaComCertificadoNaoSbgpCertAAServerAuth=true;
	private boolean _permiteAssinaturaComCertificadoNaoScvp_responder=true;
	private boolean _permiteAssinaturaComCertificadoNaoEapOverPPP=true;
	private boolean _permiteAssinaturaComCertificadoNaoEapOverLAN=true;
	private boolean _permiteAssinaturaComCertificadoNaoScvpServer=true;
	private boolean _permiteAssinaturaComCertificadoNaoScvpClient=true;
	private boolean _permiteAssinaturaComCertificadoNaoIpsecIKE=true;
	private boolean _permiteAssinaturaComCertificadoNaoCapwapAC=true;
	private boolean _permiteAssinaturaComCertificadoNaoCapwapWTP=true;
	private boolean _permiteAssinaturaComCertificadoNaoSmartcardlogon=true;
	
	private boolean _permiteAssinaturaComCertificadoDeSigilo=false;
	private boolean _permiteAssinaturaComCertificadoNaoConfiavel=false;
	private boolean _permiteAssinaturaComCertificadoNaoIssuerIsCA=false;
	private boolean _permiteAssinaturaComCertificadoNaoICPBrasil=false;
	private boolean _permiteAssinatura=true;
	private boolean _permiteCoassinatura=true;
	private boolean _permiteMesclarAssinatura=true;
	private boolean _permiteApagarAssinatura=true;
	private boolean _permiteCriptografia=true;
	private boolean _atachaDocumentoNaAssinatura=true;
	private boolean _mostraApenasOsCertificadosQuePodemAssinar=true;
	private boolean _mostraSubjectDN=true;
	private boolean _mostraIssuerDN=true;
	private boolean _mostraSubjectAlternative=true;
	private boolean _mostraKeyUsage=true;
	private boolean _mostraExtendedKeyUsage=true;
	private boolean _mostraPolicies=true;
	private boolean _mostraDadosCRL=true;
	private boolean _mostraToString=true;
	private boolean _mostraConteudo=true;
	private String _paraAssinar = null;
	private String _assinaturaRemota = null;
	private boolean _somenteVerificar = false;
	private boolean _podeEditarDadoAAssinar = false;
	private String _gatewayVerificacaoCRL = null;
	private transient ProxyInfo _proxyInfo = null;
	private String _proxyInfoHost;
	private int _proxyInfoPort;
	private String _proxyInfoUser;
	private String _proxyInfoPassword;
	private String _definicoesPKCS11 = null;
	private String _frameRetorno = null;
	private String _tempFilesLocation = null;
	private boolean _permiteDownloadCAPI = false;
	private boolean _apresentaDialogsErro = true;

	private transient boolean _forcaUrlTST=false;
//	private transient boolean _forcaPolicy=false;
	private transient boolean _forcaTipoRepositorio=false;
	private transient boolean _forcaFormaMostrarCertificados=false;
	private transient boolean _forcaFormaMostrarArquivos=false;
	private transient boolean _forcaFormaMostrarAssinaturas=false;
	private transient boolean _forcaTipoLookAndFeel=false;
	private transient boolean _forcaalgoritmosDigest=false;
	private transient boolean _forcaalgoritmosCriptografia=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoExpiradoOuInvalido=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoSemValidarCRL=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoDigitalSignature=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoNonRepudiation=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoKeyEncipherment=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoDataEncipherment=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoKeyAgreement=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoKeyCertisign=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoCrlSign=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoEncipherOnly=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoDecipherOnly=false;

	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoServerAuth=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoClientAuth=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoCodeSigning=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoEmailProtection=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoIpsecEndSystem=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoIpsecTunnel=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoIpsecUser=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoTimeStamping=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoOCSPSigning=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoDvcs=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoSbgpCertAAServerAuth=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoScvp_responder=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoEapOverPPP=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoEapOverLAN=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoScvpServer=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoScvpClient=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoIpsecIKE=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoCapwapAC=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoCapwapWTP=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoSmartcardlogon=false;
	
	private transient boolean _forcaPermiteAssinaturaComCertificadoDeSigilo=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoConfiavel=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoIssuerIsCA=false;
	private transient boolean _forcaPermiteAssinaturaComCertificadoNaoICPBrasil=false;
	private transient boolean _forcaPermiteAssinatura=false;
	private transient boolean _forcaPermiteCoassinatura=false;
	private transient boolean _forcaPermiteMesclarAssinatura=false;
	private transient boolean _forcaPermiteApagarAssinatura=false;
	private transient boolean _forcaPermiteCriptografia=false;
	private transient boolean _forcaAtachaDocumentoNaAssinatura=false;
	private transient boolean _forcaMostraApenasOsCertificadosQuePodemAssinar=false;
	private transient boolean _forcaMostraSubjectDN=false;
	private transient boolean _forcaMostraIssuerDN=false;
	private transient boolean _forcaMostraSubjectAlternative=false;
	private transient boolean _forcaMostraKeyUsage=false;
	private transient boolean _forcaMostraExtendedKeyUsage=false;
	private transient boolean _forcaMostraPolicies=false;
	private transient boolean _forcaMostraDadosCRL=false;
	private transient boolean _forcaMostraToString=false;
	private transient boolean _forcaMostraConteudo=false;
	private transient boolean _forcaGatewayVerificacaoCRL=false;
//	private transient boolean _forcaFrameRetorno=false;
	private transient boolean _forcaTempFilesLocation=false;

	public ConfiguracaoApplet() {
	}

	public interface ConfiguracaoAlteradaListener {
		void onAlterarConfiguracao();
	}
	
	private boolean parametro(String parm) {
		return Boolean.parseBoolean(parm);
	}
	
	private void carregaParametrosForcados(String string0, String string1) {
		if (string1 == null) {
		} else if (string0.toLowerCase().indexOf("urltst") >= 0) {
			_forcaUrlTST=true;
			this._urlTST = string1;
		} else if (string0.toLowerCase().indexOf("policy") >= 0) {
//			_forcaPolicy=true;
			this._policy = string1;
		} else if (string0.toLowerCase().indexOf("definicoespkcs11") >= 0) {
			this._definicoesPKCS11 = string1;
		} else if (string0.toLowerCase().indexOf("gatewayverificacaocrl") >= 0) {
			_forcaGatewayVerificacaoCRL=true;
			this._gatewayVerificacaoCRL = string1;
		} else if (string0.toLowerCase().indexOf("proxyinfohost") >= 0) {
			this._proxyInfoHost = string1;
		} else if (string0.toLowerCase().indexOf("proxyinfoport") >= 0) {
			this._proxyInfoPort = Integer.parseInt(string1);
		} else if (string0.toLowerCase().indexOf("proxyinfouser") >= 0) {
			this._proxyInfoUser = string1;
		} else if (string0.toLowerCase().indexOf("proxyinfopassword") >= 0) {
			this._proxyInfoPassword = string1;
		} else if (string0.toLowerCase().indexOf("frameretorno") >= 0) {
//			_forcaFrameRetorno=true;
			this._frameRetorno = string1;
		} else if (string0.toLowerCase().indexOf("tempfileslocation") >= 0) {
			_forcaTempFilesLocation=true;
			setTempFilesLocation(string1);
		} else if (string0.toLowerCase().indexOf("tiporepositorio") >= 0) {
			_forcaTipoRepositorio=true;
			this._tipoRepositorio = Integer.parseInt(string1);
		} else if (string0.toLowerCase().indexOf("formamostrarcertificados") >= 0) {
			_forcaFormaMostrarCertificados=true;
			this._formaMostrarCertificados = Integer.parseInt(string1);
		} else if (string0.toLowerCase().indexOf("formamostrarassinaturas") >= 0) {
			_forcaFormaMostrarAssinaturas=true;
			this._formaMostrarAssinaturas = Integer.parseInt(string1);
		} else if (string0.toLowerCase().indexOf("formamostrararquivos") >= 0) {
			_forcaFormaMostrarArquivos=true;
			this._formaMostrarArquivos = Integer.parseInt(string1);
		} else if (string0.toLowerCase().indexOf("tipolookandfeel") >= 0) {
			_forcaTipoLookAndFeel=true;
			this._tipoLookAndFeel = Integer.parseInt(string1);
		} else if (string0.toLowerCase().indexOf("algoritmosconsumo") >= 0) {
			_forcaalgoritmosDigest=true;
			this._algoritmosDigest = string1;
		} else if (string0.toLowerCase().indexOf("algoritmoscriptografia") >= 0) {
			_forcaalgoritmosCriptografia=true;
			this._algoritmosCriptografia = string1;
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadoexpiradoouinvalido") >= 0) {
			_forcaPermiteAssinaturaComCertificadoExpiradoOuInvalido=true;
			_permiteAssinaturaComCertificadoExpiradoOuInvalido=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadosemvalidarcrl") >= 0) {
			_forcaPermiteAssinaturaComCertificadoSemValidarCRL=true;
			_permiteAssinaturaComCertificadoSemValidarCRL=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaodigitalsignature") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoDigitalSignature=true;
			_permiteAssinaturaComCertificadoNaoDigitalSignature=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaononrepudiation") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoNonRepudiation=true;
			_permiteAssinaturaComCertificadoNaoNonRepudiation=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaokeyencipherment") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoKeyEncipherment=true;
			_permiteAssinaturaComCertificadoNaoKeyEncipherment=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaodataencipherment") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoDataEncipherment=true;
			_permiteAssinaturaComCertificadoNaoDataEncipherment=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaokeyagreement") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoKeyAgreement=true;
			_permiteAssinaturaComCertificadoNaoKeyAgreement=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaokeycertisign") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoKeyCertisign=true;
			_permiteAssinaturaComCertificadoNaoKeyCertisign=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaocrlsign") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoCrlSign=true;
			_permiteAssinaturaComCertificadoNaoCrlSign=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoencipheronly") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoEncipherOnly=true;
			_permiteAssinaturaComCertificadoNaoEncipherOnly=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaodecipheronly") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoDecipherOnly=true;
			_permiteAssinaturaComCertificadoNaoDecipherOnly=parametro(string1);

		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoserverauth") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoServerAuth=true;
			_permiteAssinaturaComCertificadoNaoServerAuth=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoclientauth") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoClientAuth=true;
			_permiteAssinaturaComCertificadoNaoClientAuth=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaocodesigning") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoCodeSigning=true;
			_permiteAssinaturaComCertificadoNaoCodeSigning=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoemailprotection") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoEmailProtection=true;
			_permiteAssinaturaComCertificadoNaoEmailProtection=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoipsecendsystem") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoIpsecEndSystem=true;
			_permiteAssinaturaComCertificadoNaoIpsecEndSystem=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoipsectunnel") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoIpsecTunnel=true;
			_permiteAssinaturaComCertificadoNaoIpsecTunnel=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoipsecuser") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoIpsecUser=true;
			_permiteAssinaturaComCertificadoNaoIpsecUser=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaotimestamping") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoTimeStamping=true;
			_permiteAssinaturaComCertificadoNaoTimeStamping=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoocspsigning") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoOCSPSigning=true;
			_permiteAssinaturaComCertificadoNaoOCSPSigning=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaodvcs") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoDvcs=true;
			_permiteAssinaturaComCertificadoNaoDvcs=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaosbgpcertaaserverauth") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoSbgpCertAAServerAuth=true;
			_permiteAssinaturaComCertificadoNaoSbgpCertAAServerAuth=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoscvpresponder") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoScvp_responder=true;
			_permiteAssinaturaComCertificadoNaoScvp_responder=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoeapoverppp") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoEapOverPPP=true;
			_permiteAssinaturaComCertificadoNaoEapOverPPP=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoeapoverlan") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoEapOverLAN=true;
			_permiteAssinaturaComCertificadoNaoEapOverLAN=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoscvpserver") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoScvpServer=true;
			_permiteAssinaturaComCertificadoNaoScvpServer=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoscvpclient") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoScvpClient=true;
			_permiteAssinaturaComCertificadoNaoScvpClient=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoipsecike") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoIpsecIKE=true;
			_permiteAssinaturaComCertificadoNaoIpsecIKE=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaocapwapac") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoCapwapAC=true;
			_permiteAssinaturaComCertificadoNaoCapwapAC=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaocapwapwtp") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoCapwapWTP=true;
			_permiteAssinaturaComCertificadoNaoCapwapWTP=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaosmartcardlogon") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoSmartcardlogon=true;
			_permiteAssinaturaComCertificadoNaoSmartcardlogon=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadodesigilo") >= 0) {
			_forcaPermiteAssinaturaComCertificadoDeSigilo=true;
			_permiteAssinaturaComCertificadoDeSigilo=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoconfiavel") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoConfiavel=true;
			_permiteAssinaturaComCertificadoNaoConfiavel=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoissuerisca") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoIssuerIsCA=true;
			_permiteAssinaturaComCertificadoNaoIssuerIsCA=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinaturacomcertificadonaoicpbrasil") >= 0) {
			_forcaPermiteAssinaturaComCertificadoNaoICPBrasil=true;
			_permiteAssinaturaComCertificadoNaoICPBrasil=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permitecoassinatura") >= 0) {
			_forcaPermiteCoassinatura=true;
			_permiteCoassinatura=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteassinatura") >= 0) {
			_forcaPermiteAssinatura=true;
			_permiteAssinatura=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permitemesclarassinatura") >= 0) {
			_forcaPermiteMesclarAssinatura=true;
			_permiteMesclarAssinatura=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permiteapagarassinatura") >= 0) {
			_forcaPermiteApagarAssinatura=true;
			_permiteApagarAssinatura=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permitecriptografia") >= 0) {
			_forcaPermiteCriptografia=true;
			_permiteCriptografia=parametro(string1);
		} else if (string0.toLowerCase().indexOf("atachadocumentonaassinatura") >= 0) {
			_forcaAtachaDocumentoNaAssinatura=true;
			_atachaDocumentoNaAssinatura=parametro(string1);
		} else if (string0.toLowerCase().indexOf("mostraapenasoscertificadosquepodemassinar") >= 0) {
			_forcaMostraApenasOsCertificadosQuePodemAssinar=true;
			_mostraApenasOsCertificadosQuePodemAssinar=parametro(string1);
		} else if (string0.toLowerCase().indexOf("mostrasubjectdn") >= 0) {
			_forcaMostraSubjectDN=true;
			_mostraSubjectDN=parametro(string1);
		} else if (string0.toLowerCase().indexOf("mostraissuerdn") >= 0) {
			_forcaMostraIssuerDN=true;
			_mostraIssuerDN=parametro(string1);
		} else if (string0.toLowerCase().indexOf("mostrasubjectalternative") >= 0) {
			_forcaMostraSubjectAlternative=true;
			_mostraSubjectAlternative=parametro(string1);
		} else if (string0.toLowerCase().indexOf("mostrakeyusage") >= 0) {
			_forcaMostraKeyUsage=true;
			_mostraKeyUsage=parametro(string1);
		} else if (string0.toLowerCase().indexOf("mostraextendedkeyusage") >= 0) {
			_forcaMostraExtendedKeyUsage=true;
			_mostraExtendedKeyUsage=parametro(string1);
		} else if (string0.toLowerCase().indexOf("mostrapolicies") >= 0) {
			_forcaMostraPolicies=true;
			_mostraPolicies=parametro(string1);
		} else if (string0.toLowerCase().indexOf("mostradadoscrl") >= 0) {
			_forcaMostraDadosCRL=true;
			_mostraDadosCRL=parametro(string1);
		} else if (string0.toLowerCase().indexOf("mostratostring") >= 0) {
			_forcaMostraToString=true;
			_mostraToString=parametro(string1);
		} else if (string0.toLowerCase().indexOf("mostraconteudo") >= 0) {
			_forcaMostraConteudo=true;
			_mostraConteudo=parametro(string1);
		} else if (string0.toLowerCase().indexOf("conteudoparaassinar") >= 0) {
			_paraAssinar=string1;
		} else if (string0.toLowerCase().indexOf("assinaturaremota") >= 0) {
			_assinaturaRemota=string1;
		} else if (string0.toLowerCase().indexOf("somenteverificar") >= 0) {
			_somenteVerificar=parametro(string1);
		} else if (string0.toLowerCase().indexOf("podeeditardadoaassinar") >= 0) {
			_podeEditarDadoAAssinar=parametro(string1);
		} else if (string0.toLowerCase().indexOf("permitedownloadcapi") >= 0) {
			_permiteDownloadCAPI=parametro(string1);
		} else if (string0.toLowerCase().indexOf("apresentadialogserro") >= 0) {
			_apresentaDialogsErro=parametro(string1);
		}
	}
    
	private void carregaParametrosApplet(Applet applet, String tag) {
		try {
			carregaParametrosForcados(tag, applet.getParameter(tag));
		} catch(NullPointerException e) {
		}
	}
    
	private void carregaParametrosForcados(Applet applet) {
		carregaParametrosApplet(applet, "UrlTST");
		carregaParametrosApplet(applet, "Policy");
		carregaParametrosApplet(applet, "DefinicoesPKCS11");
		carregaParametrosApplet(applet, "GatewayVerificacaoCRL");
		carregaParametrosApplet(applet, "frameRetorno");
		carregaParametrosApplet(applet, "TempFilesLocation");
		carregaParametrosApplet(applet, "TipoRepositorio");
		carregaParametrosApplet(applet, "FormaMostrarCertificados");
		carregaParametrosApplet(applet, "FormaMostrarArquivos");
		carregaParametrosApplet(applet, "FormaMostrarAssinaturas");
		carregaParametrosApplet(applet, "TipoLookAndFeel");
		carregaParametrosApplet(applet, "AlgoritmosConsumo");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoExpiradoOuInvalido");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoSemValidarCrl");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoDigitalSignature");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoNonRepudiation");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoKeyEncipherment");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoDataEncipherment");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoKeyAgreement");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoKeyCertisign");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoCrlSign");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoEncipherOnly");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoDecipherOnly");

		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoServerAuth");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoClientAuth");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoCodeSigning");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoEmailProtection");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoIPSecEndSystem");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoIPSecTunnel");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoIPSecUser");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoIPSecIKE");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoTimeStamping");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoOCSPSigning");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoDvcs");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaosbgpcertaaserverauth");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoEAPOverPPP");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoEAPOverLan");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoSCVPServer");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoSCVPClient");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoSCVPResponder");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoCapWapac");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoCapWapwtp");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoSmartcardLogon");
		
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoDeSigilo");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoConfiavel");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoIssuerIsCA");
		carregaParametrosApplet(applet, "PermiteAssinaturaComCertificadoNaoICPBrasil");
		carregaParametrosApplet(applet, "PermiteAssinatura");
		carregaParametrosApplet(applet, "PermiteCoassinatura");
		carregaParametrosApplet(applet, "PermiteMesclarAssinatura");
		carregaParametrosApplet(applet, "PermiteApagarAssinatura");
		carregaParametrosApplet(applet, "PermiteCriptografia");
		carregaParametrosApplet(applet, "AtachaDocumentoNaAssinatura");
		carregaParametrosApplet(applet, "MostraApenasOsCertificadosQuePodemAssinar");
		carregaParametrosApplet(applet, "MostraSubjectDN");
		carregaParametrosApplet(applet, "MostraIssuerDN");
		carregaParametrosApplet(applet, "MostraSubjectAlternative");
		carregaParametrosApplet(applet, "MostraKeyUsage");
		carregaParametrosApplet(applet, "MostraExtendedKeyUsage");
		carregaParametrosApplet(applet, "MostraPolicies");
		carregaParametrosApplet(applet, "MostraDadosCRL");
		carregaParametrosApplet(applet, "MostraToString");
		carregaParametrosApplet(applet, "MostraConteudo");
		carregaParametrosApplet(applet, "ConteudoParaAssinar");
		carregaParametrosApplet(applet, "AssinaturaRemota");
		carregaParametrosApplet(applet, "SomenteVerificar");
		carregaParametrosApplet(applet, "PodeEditarDadoAAssinar");
		carregaParametrosApplet(applet, "PermiteDownloadCAPI");
	}
	
	private void carregaParametrosForcados(String [] args) {
		for (int a=0; a<args.length; a++) {
			String [] string = args[a].split("=");
			carregaParametrosForcados(string[0], string[1]);
		}
	}
	
	private static String nomeArquivoConfiguracao(String nome) {
		return ICPBravoUtil.getRaizICPBravo()+"/"+nome+".ser";
	}
	
	public static CurrentConfiguration carregaConfiguracaoApplet(String nome, Applet applet) {
		ConfiguracaoApplet cnf = carregaConfiguracaoApplet(nome);
		cnf.carregaParametrosForcados(applet);

		if (cnf._permiteDownloadCAPI) {
			try {
				ICPBravoUtil.setTempFilesLocation(cnf._tempFilesLocation);
				cnf.install(applet.getCodeBase().toString());
			} catch (Exception e) {
				System.out.println("Problemas na instalação: "+e.getMessage());
				e.printStackTrace();
			}
		}
		
		return new CurrentConfiguration(cnf);
	}
	
	public static CurrentConfiguration carregaConfiguracaoApplet(String nome, String [] args) {
		ConfiguracaoApplet cnf = carregaConfiguracaoApplet(nome);
		cnf.carregaParametrosForcados(args);
		
		return new CurrentConfiguration(cnf);
	}
	
	private static ConfiguracaoApplet carregaConfiguracaoApplet(String nome) {
		ConfiguracaoApplet toReturn = null;
		try {
			InputStream file = new FileInputStream(nomeArquivoConfiguracao(nome));
			InputStream buffer = new BufferedInputStream( file );
			ObjectInput input = new ObjectInputStream ( buffer );
			toReturn = (ConfiguracaoApplet) input.readObject();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		if (toReturn == null)
			toReturn = new ConfiguracaoApplet();
		toReturn.setNomeArquivoConfiguracao(nome);
		toReturn.inicializa();
		return toReturn;
	}
	
	private void inicializa() {
		try {
			UIManager.setLookAndFeel(tabelaLookAndFeel[_tipoLookAndFeel]);
			return;
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
	}
	
	private void salvar() {
		try {
			OutputStream file = new FileOutputStream(nomeArquivoConfiguracao(getNomeArquivoConfiguracao()));
			OutputStream buffer = new BufferedOutputStream( file );
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(this);
			} finally {
				output.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private JPanel componenteComLabel(String label, Component componente, int largura, int altura) {
		JPanel painel = new JPanel();
        painel.setLayout(new javax.swing.BoxLayout(painel, javax.swing.BoxLayout.X_AXIS));
		painel.setMinimumSize(new Dimension(largura, altura));
		painel.setMaximumSize(new Dimension(largura, altura));
		painel.setPreferredSize(new Dimension(largura, altura));
		
		if (componente instanceof JCheckBox) {
			painel.add(componente);
			painel.add(new JLabel(label));
		} else {
			painel.add(new JLabel(label+": "));
			painel.add(componente);
		}
		return painel;
	}
	
	public void configura(final ConfiguracaoAlteradaListener listener) {
		final JFrame frame = new JFrame("Configura"+ConfiguracaoApplet.ccedil+""+ConfiguracaoApplet.atilde+"o ICP-Bravo");
		int largura = 900;
		
		Dimension tamanho = new Dimension(largura+50, 700);
		
		frame.setPreferredSize(tamanho);
		frame.setMinimumSize(tamanho);
		frame.setMaximumSize(tamanho);
        Container painel = frame.getContentPane();
//        painel.setPreferredSize(tamanho);
        painel.setLayout(new javax.swing.BoxLayout(painel, javax.swing.BoxLayout.Y_AXIS));

    	JTabbedPane abas = new JTabbedPane();
		abas.setTabPlacement(JTabbedPane.TOP);

//		Dimension tamanho1 = new Dimension(largura-50, 200);

/***********************************************************************************************/
		JPanel painelAssinatura = new JPanel();
//		painelAssinatura.setPreferredSize(tamanho1);
		painelAssinatura.setLayout(new javax.swing.BoxLayout(painelAssinatura, javax.swing.BoxLayout.Y_AXIS));
        
        final JComboBox selalgoritmoDigest = new JComboBox(algDigest);
        selalgoritmoDigest.setEnabled(!_forcaalgoritmosDigest);
        for (int a=0; a<algDigest.length; a++) {
        	if (algDigest[a].equals(_algoritmosDigest)) {
                selalgoritmoDigest.setSelectedIndex(a);
                break;
        	}
        }
		selalgoritmoDigest.setPreferredSize(new Dimension(100, 20));
		painelAssinatura.add(componenteComLabel("algoritmo de Digest", selalgoritmoDigest, largura, 25));
        
        final JComboBox selalgoritmoSimetrico = new JComboBox(algSimetrico);
        selalgoritmoSimetrico.setEnabled(!_forcaalgoritmosCriptografia);
        for (int a=0; a<algSimetrico.length; a++) {
        	if (algSimetrico[a].equals(_algoritmosCriptografia)) {
        		selalgoritmoSimetrico.setSelectedIndex(a);
                break;
        	}
        }
        selalgoritmoSimetrico.setPreferredSize(new Dimension(100, 20));
		painelAssinatura.add(componenteComLabel("algoritmo de Criptografia", selalgoritmoSimetrico, largura, 25));
		
		final JTextField urlTST = new JTextField();
		urlTST.setEnabled(!_forcaUrlTST);
		urlTST.setText(_urlTST);
        painelAssinatura.add(componenteComLabel("Servidor de TST", urlTST, largura, 20));
		
		final JTextField gatewayVerificacaoCRL = new JTextField();
		gatewayVerificacaoCRL.setEnabled(!_forcaGatewayVerificacaoCRL);
		gatewayVerificacaoCRL.setText(_gatewayVerificacaoCRL);
        painelAssinatura.add(componenteComLabel("Gateway para verificação de CRL", gatewayVerificacaoCRL, largura, 20));
		
		final JTextField tempFilesLocation = new JTextField();
		tempFilesLocation.setEnabled(!_forcaTempFilesLocation);
		tempFilesLocation.setText(_tempFilesLocation);
        painelAssinatura.add(componenteComLabel("Localização dos arquivos temporários", tempFilesLocation, largura, 20));
		
        final JCheckBox permiteAssinaturaComCertificadoExpiradoOuInvalido = new JCheckBox();
        permiteAssinaturaComCertificadoExpiradoOuInvalido.setEnabled(!_forcaPermiteAssinaturaComCertificadoExpiradoOuInvalido);
        permiteAssinaturaComCertificadoExpiradoOuInvalido.setSelected(_permiteAssinaturaComCertificadoExpiradoOuInvalido);
        painelAssinatura.add(componenteComLabel("Permite assinatura com certificado inv"+ConfiguracaoApplet.aacute+"lido ou expirado", permiteAssinaturaComCertificadoExpiradoOuInvalido, largura, 20));
        
        final JCheckBox permiteAssinaturaComCertificadoSemValidarCRL = new JCheckBox();
        permiteAssinaturaComCertificadoSemValidarCRL.setEnabled(!_forcaPermiteAssinaturaComCertificadoSemValidarCRL);
        permiteAssinaturaComCertificadoSemValidarCRL.setSelected(_permiteAssinaturaComCertificadoSemValidarCRL);
        painelAssinatura.add(componenteComLabel("Permite assinatura sem validar a CRL do certificado", permiteAssinaturaComCertificadoSemValidarCRL, largura, 20));
        
        final JCheckBox permiteAssinaturaComCertificadoDeSigilo = new JCheckBox();
        permiteAssinaturaComCertificadoDeSigilo.setEnabled(!_forcaPermiteAssinaturaComCertificadoDeSigilo);
        permiteAssinaturaComCertificadoDeSigilo.setSelected(_permiteAssinaturaComCertificadoDeSigilo);
        painelAssinatura.add(componenteComLabel("Permite assinatura com certificado de sigilo", permiteAssinaturaComCertificadoDeSigilo, largura, 20));
        
        final JCheckBox permiteAssinaturaComCertificadoNaoIssuerIsCA = new JCheckBox();
        permiteAssinaturaComCertificadoNaoIssuerIsCA.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoIssuerIsCA);
        permiteAssinaturaComCertificadoNaoIssuerIsCA.setSelected(_permiteAssinaturaComCertificadoNaoIssuerIsCA);
        painelAssinatura.add(componenteComLabel("Permite assinatura com certificado cuja os fornecedores n"+ConfiguracaoApplet.atilde+"o tenham a permiss"+ConfiguracaoApplet.atilde+"o", permiteAssinaturaComCertificadoNaoIssuerIsCA, largura, 20));
        
        final JCheckBox permiteAssinaturaComCertificadoNaoConfiavel = new JCheckBox();
        permiteAssinaturaComCertificadoNaoConfiavel.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoConfiavel);
        permiteAssinaturaComCertificadoNaoConfiavel.setSelected(_permiteAssinaturaComCertificadoNaoConfiavel);
        painelAssinatura.add(componenteComLabel("Permite assinatura com certificado n"+ConfiguracaoApplet.atilde+"o confi"+ConfiguracaoApplet.aacute+"vel", permiteAssinaturaComCertificadoNaoConfiavel, largura, 20));
        
        final JCheckBox permiteAssinaturaComCertificadoNaoICPBrasil = new JCheckBox();
        permiteAssinaturaComCertificadoNaoICPBrasil.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoICPBrasil);
        permiteAssinaturaComCertificadoNaoICPBrasil.setSelected(_permiteAssinaturaComCertificadoNaoICPBrasil);
        painelAssinatura.add(componenteComLabel("Permite assinatura com certificado n"+ConfiguracaoApplet.atilde+"o ICP-Brasil", permiteAssinaturaComCertificadoNaoICPBrasil, largura, 20));
        
        final JCheckBox permiteAssinatura = new JCheckBox();
        permiteAssinatura.setEnabled(!_forcaPermiteAssinatura);
        permiteAssinatura.setSelected(_permiteAssinatura);
        painelAssinatura.add(componenteComLabel("Permite assinatura", permiteAssinatura, largura, 20));
        
        final JCheckBox permiteCoassinatura = new JCheckBox();
        permiteCoassinatura.setEnabled(!_forcaPermiteCoassinatura);
        permiteCoassinatura.setSelected(_permiteCoassinatura);
        painelAssinatura.add(componenteComLabel("Permite co-assinatura", permiteCoassinatura, largura, 20));
        
        final JCheckBox permiteMesclarAssinatura = new JCheckBox();
        permiteMesclarAssinatura.setEnabled(!_forcaPermiteMesclarAssinatura);
        permiteMesclarAssinatura.setSelected(_permiteMesclarAssinatura);
        painelAssinatura.add(componenteComLabel("Permite mesclar assinaturas", permiteMesclarAssinatura, largura, 20));
        
        final JCheckBox permiteApagarAssinatura = new JCheckBox();
        permiteApagarAssinatura.setEnabled(!_forcaPermiteApagarAssinatura);
        permiteApagarAssinatura.setSelected(_permiteApagarAssinatura);
        painelAssinatura.add(componenteComLabel("Permite apagar assinatura do cache", permiteApagarAssinatura, largura, 20));
        
        final JCheckBox permiteCriptografia = new JCheckBox();
        permiteCriptografia.setEnabled(!_forcaPermiteCriptografia);
        permiteCriptografia.setSelected(_permiteCriptografia);
        painelAssinatura.add(componenteComLabel("Permite criptografia", permiteCriptografia, largura, 20));

        final JCheckBox atachaDocumentoNaAssinatura = new JCheckBox();
        atachaDocumentoNaAssinatura.setEnabled(!_forcaAtachaDocumentoNaAssinatura);
        atachaDocumentoNaAssinatura.setSelected(_atachaDocumentoNaAssinatura);
        painelAssinatura.add(componenteComLabel("Adiciona o documento "+ConfiguracaoApplet.agrave+" assinatura", atachaDocumentoNaAssinatura, largura, 20));
        
/***********************************************************************************************/
		JPanel painelAssinaturaKeyUsageExtension = new JPanel();
//		painelAssinaturaKeyUsageExtension.setPreferredSize(tamanho1);
		painelAssinaturaKeyUsageExtension.setLayout(new javax.swing.BoxLayout(painelAssinaturaKeyUsageExtension, javax.swing.BoxLayout.Y_AXIS));
        
        final JCheckBox permiteAssinaturaComCertificadoNaoDigitalSignature = new JCheckBox();
        permiteAssinaturaComCertificadoNaoDigitalSignature.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoDigitalSignature);
        permiteAssinaturaComCertificadoNaoDigitalSignature.setSelected(_permiteAssinaturaComCertificadoNaoDigitalSignature);
        painelAssinaturaKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" assinador digital", permiteAssinaturaComCertificadoNaoDigitalSignature, largura, 20));
        
        final JCheckBox permiteAssinaturaComCertificadoNaoNonRepudiation = new JCheckBox();
        permiteAssinaturaComCertificadoNaoNonRepudiation.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoNonRepudiation);
        permiteAssinaturaComCertificadoNaoNonRepudiation.setSelected(_permiteAssinaturaComCertificadoNaoNonRepudiation);
        painelAssinaturaKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" Non-Repudiation", permiteAssinaturaComCertificadoNaoNonRepudiation, largura, 20));
        
        final JCheckBox permiteAssinaturaComCertificadoNaoKeyEncipherment = new JCheckBox();
        permiteAssinaturaComCertificadoNaoKeyEncipherment.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoKeyEncipherment);
        permiteAssinaturaComCertificadoNaoKeyEncipherment.setSelected(_permiteAssinaturaComCertificadoNaoKeyEncipherment);
        painelAssinaturaKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" Key-Encipherment", permiteAssinaturaComCertificadoNaoKeyEncipherment, largura, 20));
        
        final JCheckBox permiteAssinaturaComCertificadoNaoDataEncipherment = new JCheckBox();
        permiteAssinaturaComCertificadoNaoDataEncipherment.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoDataEncipherment);
        permiteAssinaturaComCertificadoNaoDataEncipherment.setSelected(_permiteAssinaturaComCertificadoNaoDataEncipherment);
        painelAssinaturaKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" Data-Encipherment", permiteAssinaturaComCertificadoNaoDataEncipherment, largura, 20));
        
        final JCheckBox permiteAssinaturaComCertificadoNaoKeyAgreement = new JCheckBox();
        permiteAssinaturaComCertificadoNaoKeyAgreement.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoKeyAgreement);
        permiteAssinaturaComCertificadoNaoKeyAgreement.setSelected(_permiteAssinaturaComCertificadoNaoKeyAgreement);
        painelAssinaturaKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" Key-Agreement", permiteAssinaturaComCertificadoNaoKeyAgreement, largura, 20));
        
        final JCheckBox permiteAssinaturaComCertificadoNaoKeyCertisign = new JCheckBox();
        permiteAssinaturaComCertificadoNaoKeyCertisign.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoKeyCertisign);
        permiteAssinaturaComCertificadoNaoKeyCertisign.setSelected(_permiteAssinaturaComCertificadoNaoKeyCertisign);
        painelAssinaturaKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" Key-Certisign", permiteAssinaturaComCertificadoNaoKeyCertisign, largura, 20));
        
        final JCheckBox permiteAssinaturaComCertificadoNaoCrlSign = new JCheckBox();
        permiteAssinaturaComCertificadoNaoCrlSign.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoCrlSign);
        permiteAssinaturaComCertificadoNaoCrlSign.setSelected(_permiteAssinaturaComCertificadoNaoCrlSign);
        painelAssinaturaKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" Crl-Sign", permiteAssinaturaComCertificadoNaoCrlSign, largura, 20));
        
        final JCheckBox permiteAssinaturaComCertificadoNaoEncipherOnly = new JCheckBox();
        permiteAssinaturaComCertificadoNaoEncipherOnly.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoEncipherOnly);
        permiteAssinaturaComCertificadoNaoEncipherOnly.setSelected(_permiteAssinaturaComCertificadoNaoEncipherOnly);
        painelAssinaturaKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" Encipher-Only", permiteAssinaturaComCertificadoNaoEncipherOnly, largura, 20));
        
        final JCheckBox permiteAssinaturaComCertificadoNaoDecipherOnly = new JCheckBox();
        permiteAssinaturaComCertificadoNaoDecipherOnly.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoDecipherOnly);
        permiteAssinaturaComCertificadoNaoDecipherOnly.setSelected(_permiteAssinaturaComCertificadoNaoDecipherOnly);
        painelAssinaturaKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" Decipher-Only", permiteAssinaturaComCertificadoNaoDecipherOnly, largura, 20));
		
/***********************************************************************************************/
		JPanel painelAssinaturaExtendedKeyUsageExtension = new JPanel();
//		painelAssinaturaExtendedKeyUsageExtension.setPreferredSize(tamanho1);
		painelAssinaturaExtendedKeyUsageExtension.setLayout(new javax.swing.BoxLayout(painelAssinaturaExtendedKeyUsageExtension, javax.swing.BoxLayout.Y_AXIS));
        
//    	ServerAuth
        final JCheckBox permiteAssinaturaComCertificadoNaoServerAuth = new JCheckBox();
        permiteAssinaturaComCertificadoNaoServerAuth.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoServerAuth);
        permiteAssinaturaComCertificadoNaoServerAuth.setSelected(_permiteAssinaturaComCertificadoNaoServerAuth);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" ServerAuth", permiteAssinaturaComCertificadoNaoServerAuth, largura, 20));
        
//    	ClientAuth
        final JCheckBox permiteAssinaturaComCertificadoNaoClientAuth = new JCheckBox();
        permiteAssinaturaComCertificadoNaoClientAuth.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoClientAuth);
        permiteAssinaturaComCertificadoNaoClientAuth.setSelected(_permiteAssinaturaComCertificadoNaoClientAuth);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" ClientAuth", permiteAssinaturaComCertificadoNaoClientAuth, largura, 20));
        
//    	CodeSigning
        final JCheckBox permiteAssinaturaComCertificadoNaoCodeSigning = new JCheckBox();
        permiteAssinaturaComCertificadoNaoCodeSigning.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoCodeSigning);
        permiteAssinaturaComCertificadoNaoCodeSigning.setSelected(_permiteAssinaturaComCertificadoNaoCodeSigning);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" CodeSigning", permiteAssinaturaComCertificadoNaoCodeSigning, largura, 20));
        
//    	EmailProtection
        final JCheckBox permiteAssinaturaComCertificadoNaoEmailProtection = new JCheckBox();
        permiteAssinaturaComCertificadoNaoEmailProtection.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoEmailProtection);
        permiteAssinaturaComCertificadoNaoEmailProtection.setSelected(_permiteAssinaturaComCertificadoNaoEmailProtection);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" EmailProtection", permiteAssinaturaComCertificadoNaoEmailProtection, largura, 20));
        
//    	IpsecEndSystem
        final JCheckBox permiteAssinaturaComCertificadoNaoIpsecEndSystem = new JCheckBox();
        permiteAssinaturaComCertificadoNaoIpsecEndSystem.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoIpsecEndSystem);
        permiteAssinaturaComCertificadoNaoIpsecEndSystem.setSelected(_permiteAssinaturaComCertificadoNaoIpsecEndSystem);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" IpsecEndSystem", permiteAssinaturaComCertificadoNaoIpsecEndSystem, largura, 20));
        
//    	IpsecTunnel
        final JCheckBox permiteAssinaturaComCertificadoNaoIpsecTunnel = new JCheckBox();
        permiteAssinaturaComCertificadoNaoIpsecTunnel.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoIpsecTunnel);
        permiteAssinaturaComCertificadoNaoIpsecTunnel.setSelected(_permiteAssinaturaComCertificadoNaoIpsecTunnel);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" IpsecTunnel", permiteAssinaturaComCertificadoNaoIpsecTunnel, largura, 20));
        
//    	IpsecUser
        final JCheckBox permiteAssinaturaComCertificadoNaoIpsecUser = new JCheckBox();
        permiteAssinaturaComCertificadoNaoIpsecUser.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoIpsecUser);
        permiteAssinaturaComCertificadoNaoIpsecUser.setSelected(_permiteAssinaturaComCertificadoNaoIpsecUser);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" IpsecUser", permiteAssinaturaComCertificadoNaoIpsecUser, largura, 20));
        
//    	TimeStamping
        final JCheckBox permiteAssinaturaComCertificadoNaoTimeStamping = new JCheckBox();
        permiteAssinaturaComCertificadoNaoTimeStamping.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoTimeStamping);
        permiteAssinaturaComCertificadoNaoTimeStamping.setSelected(_permiteAssinaturaComCertificadoNaoTimeStamping);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" TimeStamping", permiteAssinaturaComCertificadoNaoTimeStamping, largura, 20));
        
//    	OCSPSigning
        final JCheckBox permiteAssinaturaComCertificadoNaoOCSPSigning = new JCheckBox();
        permiteAssinaturaComCertificadoNaoOCSPSigning.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoOCSPSigning);
        permiteAssinaturaComCertificadoNaoOCSPSigning.setSelected(_permiteAssinaturaComCertificadoNaoOCSPSigning);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" OCSPSigning", permiteAssinaturaComCertificadoNaoOCSPSigning, largura, 20));
        
//    	Dvcs
        final JCheckBox permiteAssinaturaComCertificadoNaoDvcs = new JCheckBox();
        permiteAssinaturaComCertificadoNaoDvcs.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoDvcs);
        permiteAssinaturaComCertificadoNaoDvcs.setSelected(_permiteAssinaturaComCertificadoNaoDvcs);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" Dvcs", permiteAssinaturaComCertificadoNaoDvcs, largura, 20));
        
//    	SbgpCertAAServerAuth
        final JCheckBox permiteAssinaturaComCertificadoNaoSbgpCertAAServerAuth = new JCheckBox();
        permiteAssinaturaComCertificadoNaoSbgpCertAAServerAuth.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoSbgpCertAAServerAuth);
        permiteAssinaturaComCertificadoNaoSbgpCertAAServerAuth.setSelected(_permiteAssinaturaComCertificadoNaoSbgpCertAAServerAuth);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" SbgpCertAAServerAuth", permiteAssinaturaComCertificadoNaoSbgpCertAAServerAuth, largura, 20));
        
//    	Scvp_responder
        final JCheckBox permiteAssinaturaComCertificadoNaoScvp_responder = new JCheckBox();
        permiteAssinaturaComCertificadoNaoScvp_responder.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoScvp_responder);
        permiteAssinaturaComCertificadoNaoScvp_responder.setSelected(_permiteAssinaturaComCertificadoNaoScvp_responder);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" Scvp_responder", permiteAssinaturaComCertificadoNaoScvp_responder, largura, 20));
        
//    	EapOverPPP
        final JCheckBox permiteAssinaturaComCertificadoNaoEapOverPPP = new JCheckBox();
        permiteAssinaturaComCertificadoNaoEapOverPPP.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoEapOverPPP);
        permiteAssinaturaComCertificadoNaoEapOverPPP.setSelected(_permiteAssinaturaComCertificadoNaoEapOverPPP);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" EapOverPPP", permiteAssinaturaComCertificadoNaoEapOverPPP, largura, 20));
        
//    	EapOverLAN
        final JCheckBox permiteAssinaturaComCertificadoNaoEapOverLAN = new JCheckBox();
        permiteAssinaturaComCertificadoNaoEapOverLAN.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoEapOverLAN);
        permiteAssinaturaComCertificadoNaoEapOverLAN.setSelected(_permiteAssinaturaComCertificadoNaoEapOverLAN);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" EapOverLAN", permiteAssinaturaComCertificadoNaoEapOverLAN, largura, 20));
        
//    	ScvpServer
        final JCheckBox permiteAssinaturaComCertificadoNaoScvpServer = new JCheckBox();
        permiteAssinaturaComCertificadoNaoScvpServer.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoScvpServer);
        permiteAssinaturaComCertificadoNaoScvpServer.setSelected(_permiteAssinaturaComCertificadoNaoScvpServer);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" ScvpServer", permiteAssinaturaComCertificadoNaoScvpServer, largura, 20));
        
//    	ScvpClient
        final JCheckBox permiteAssinaturaComCertificadoNaoScvpClient = new JCheckBox();
        permiteAssinaturaComCertificadoNaoScvpClient.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoScvpClient);
        permiteAssinaturaComCertificadoNaoScvpClient.setSelected(_permiteAssinaturaComCertificadoNaoScvpClient);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" ScvpClient", permiteAssinaturaComCertificadoNaoScvpClient, largura, 20));
        
//    	IpsecIKE
        final JCheckBox permiteAssinaturaComCertificadoNaoIpsecIKE = new JCheckBox();
        permiteAssinaturaComCertificadoNaoIpsecIKE.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoIpsecIKE);
        permiteAssinaturaComCertificadoNaoIpsecIKE.setSelected(_permiteAssinaturaComCertificadoNaoIpsecIKE);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" IpsecIKE", permiteAssinaturaComCertificadoNaoIpsecIKE, largura, 20));
        
//    	CapwapAC
        final JCheckBox permiteAssinaturaComCertificadoNaoCapwapAC = new JCheckBox();
        permiteAssinaturaComCertificadoNaoCapwapAC.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoCapwapAC);
        permiteAssinaturaComCertificadoNaoCapwapAC.setSelected(_permiteAssinaturaComCertificadoNaoCapwapAC);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" CapwapAC", permiteAssinaturaComCertificadoNaoCapwapAC, largura, 20));
        
//    	CapwapWTP
        final JCheckBox permiteAssinaturaComCertificadoNaoCapwapWTP = new JCheckBox();
        permiteAssinaturaComCertificadoNaoCapwapWTP.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoCapwapWTP);
        permiteAssinaturaComCertificadoNaoCapwapWTP.setSelected(_permiteAssinaturaComCertificadoNaoCapwapWTP);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" CapwapWTP", permiteAssinaturaComCertificadoNaoCapwapWTP, largura, 20));
        
//    	Smartcardlogon
        final JCheckBox permiteAssinaturaComCertificadoNaoSmartcardlogon = new JCheckBox();
        permiteAssinaturaComCertificadoNaoSmartcardlogon.setEnabled(!_forcaPermiteAssinaturaComCertificadoNaoSmartcardlogon);
        permiteAssinaturaComCertificadoNaoSmartcardlogon.setSelected(_permiteAssinaturaComCertificadoNaoSmartcardlogon);
        painelAssinaturaExtendedKeyUsageExtension.add(componenteComLabel("Permite assinatura com certificado que n"+ConfiguracaoApplet.atilde+"o "+ConfiguracaoApplet.eacute+" Smartcardlogon", permiteAssinaturaComCertificadoNaoSmartcardlogon, largura, 20));
        
        
/***********************************************************************************************/
    	JTabbedPane abasAssinatura = new JTabbedPane();
		abasAssinatura.setTabPlacement(JTabbedPane.TOP);
        painelAssinatura.add(abasAssinatura);
        abasAssinatura.addTab("KeyUsageExtension", new JScrollPane(painelAssinaturaKeyUsageExtension));
        abasAssinatura.addTab("ExtendedKeyUsageExtension", new JScrollPane(painelAssinaturaExtendedKeyUsageExtension));

		abas.addTab("Assinatura", new JScrollPane(painelAssinatura));
		
/***********************************************************************************************/
		JPanel painelMostra = new JPanel();
//		painelMostra.setPreferredSize(tamanho1);
		painelMostra.setLayout(new javax.swing.BoxLayout(painelMostra, javax.swing.BoxLayout.Y_AXIS));
        
        final JComboBox selTipoRepositorio = new JComboBox(tiposRepositorio);
        selTipoRepositorio.setEnabled(!_forcaTipoRepositorio);
        selTipoRepositorio.setSelectedIndex(_tipoRepositorio);
        selTipoRepositorio.setPreferredSize(new Dimension(100, 20));
        painelMostra.add(componenteComLabel("Origem dos certificados", selTipoRepositorio, largura, 25));
        
        final JComboBox selFormaMostrarCertificados = new JComboBox(formasMostrarCertificados);
        selFormaMostrarCertificados.setEnabled(!_forcaFormaMostrarCertificados);
        selFormaMostrarCertificados.setSelectedIndex(_formaMostrarCertificados);
        selFormaMostrarCertificados.setPreferredSize(new Dimension(100, 20));
        painelMostra.add(componenteComLabel("Forma de apresentar os certificados", selFormaMostrarCertificados, largura, 25));
        
        final JComboBox selFormaMostrarArquivos = new JComboBox(formasMostrarAssinaturas);
        selFormaMostrarArquivos.setEnabled(!_forcaFormaMostrarArquivos);
        selFormaMostrarArquivos.setSelectedIndex(_formaMostrarArquivos);
        selFormaMostrarArquivos.setPreferredSize(new Dimension(100, 20));
        painelMostra.add(componenteComLabel("Forma de apresentar os arquivos", selFormaMostrarArquivos, largura, 25));
        
        final JComboBox selFormaMostrarAssinaturas = new JComboBox(formasMostrarAssinaturas);
        selFormaMostrarAssinaturas.setEnabled(!_forcaFormaMostrarAssinaturas);
        selFormaMostrarAssinaturas.setSelectedIndex(_formaMostrarAssinaturas);
        selFormaMostrarAssinaturas.setPreferredSize(new Dimension(100, 20));
        painelMostra.add(componenteComLabel("Forma de apresentar as assinaturas", selFormaMostrarAssinaturas, largura, 25));
        
		final JCheckBox mostraApenasOsCertificadosQuePodemAssinar = new JCheckBox();
        mostraApenasOsCertificadosQuePodemAssinar.setEnabled(!_forcaMostraApenasOsCertificadosQuePodemAssinar);
        mostraApenasOsCertificadosQuePodemAssinar.setSelected(_mostraApenasOsCertificadosQuePodemAssinar);
        painelMostra.add(componenteComLabel("Mostra apenas os certificados que podem assinar", mostraApenasOsCertificadosQuePodemAssinar, largura, 20));
        
		final JCheckBox mostraSubjectDN = new JCheckBox();
        mostraSubjectDN.setEnabled(!_forcaMostraSubjectDN);
        mostraSubjectDN.setSelected(_mostraSubjectDN);
        painelMostra.add(componenteComLabel("Mostra a aba SubjectDN", mostraSubjectDN, largura, 20));
        
        final JCheckBox mostraIssuerDN = new JCheckBox();
        mostraIssuerDN.setEnabled(!_forcaMostraIssuerDN);
        mostraIssuerDN.setSelected(_mostraIssuerDN);
        painelMostra.add(componenteComLabel("Mostra a aba IssuerDN", mostraIssuerDN, largura, 20));
        
        final JCheckBox mostraSubjectAlternative = new JCheckBox();
        mostraSubjectAlternative.setEnabled(!_forcaMostraSubjectAlternative);
        mostraSubjectAlternative.setSelected(_mostraSubjectAlternative);
        painelMostra.add(componenteComLabel("Mostra a aba Subject Alternative", mostraSubjectAlternative, largura, 20));
        
        final JCheckBox mostraKeyUsage = new JCheckBox();
        mostraKeyUsage.setEnabled(!_forcaMostraKeyUsage);
        mostraKeyUsage.setSelected(_mostraKeyUsage);
        painelMostra.add(componenteComLabel("Mostra a aba Key Usage", mostraKeyUsage, largura, 20));
        
        final JCheckBox mostraExtendedKeyUsage = new JCheckBox();
        mostraExtendedKeyUsage.setEnabled(!_forcaMostraExtendedKeyUsage);
        mostraExtendedKeyUsage.setSelected(_mostraExtendedKeyUsage);
        painelMostra.add(componenteComLabel("Mostra a aba Extended Key Usage", mostraExtendedKeyUsage, largura, 20));
        
        final JCheckBox mostraPolicies = new JCheckBox();
        mostraPolicies.setEnabled(!_forcaMostraPolicies);
        mostraPolicies.setSelected(_mostraPolicies);
        painelMostra.add(componenteComLabel("Mostra a aba Policies", mostraPolicies, largura, 20));
        
        final JCheckBox mostraDadosCRL = new JCheckBox();
        mostraDadosCRL.setEnabled(!_forcaMostraDadosCRL);
        mostraDadosCRL.setSelected(_mostraDadosCRL);
        painelMostra.add(componenteComLabel("Mostra a aba CRL", mostraDadosCRL, largura, 20));
        
        final JCheckBox mostraToString = new JCheckBox();
        mostraToString.setEnabled(!_forcaMostraToString);
        mostraToString.setSelected(_mostraToString);
        painelMostra.add(componenteComLabel("Mostra a aba ToString", mostraToString, largura, 20));
        
        final JCheckBox mostraConteudo = new JCheckBox();
        mostraConteudo.setEnabled(!_forcaMostraConteudo);
        mostraConteudo.setSelected(_mostraConteudo);
        painelMostra.add(componenteComLabel("Mostra a aba Conte"+ConfiguracaoApplet.uacute+"do", mostraConteudo, largura, 20));

		abas.addTab("Apresenta"+ConfiguracaoApplet.ccedil+""+ConfiguracaoApplet.atilde+"o do certificado", new JScrollPane(painelMostra));
		
/***********************************************************************************************/
		JPanel painelGeral = new JPanel();
        painelGeral.setLayout(new javax.swing.BoxLayout(painelGeral, javax.swing.BoxLayout.Y_AXIS));
		
        final JComboBox sellookAndFeel = new JComboBox(tabelaLookAndFeel);
        sellookAndFeel.setEnabled(!_forcaTipoLookAndFeel);
		sellookAndFeel.setSelectedIndex(_tipoLookAndFeel);
        sellookAndFeel.setPreferredSize(new Dimension(100, 20));
        painelGeral.add(componenteComLabel("Look&Feel", sellookAndFeel, largura, 25));

		final JTextField proxyInfoHost = new JTextField();
		proxyInfoHost.setText(_proxyInfoHost);
		painelGeral.add(componenteComLabel("Proxy - Host", proxyInfoHost, largura, 20));

		final JTextField proxyInfoPort = new JTextField();
		proxyInfoPort.setText(""+_proxyInfoPort);
		painelGeral.add(componenteComLabel("Proxy - Port", proxyInfoPort, largura, 10));

		final JTextField proxyInfoUser = new JTextField();
		proxyInfoUser.setText(_proxyInfoUser);
		painelGeral.add(componenteComLabel("Proxy - User", proxyInfoUser, largura, 20));

		final JPasswordField proxyInfoPassword = new JPasswordField();
		proxyInfoPassword.setText(_proxyInfoPassword);
		painelGeral.add(componenteComLabel("Proxy - Password", proxyInfoPassword, largura, 20));
        
		abas.addTab("Outros", new JScrollPane(painelGeral));

/***********************************************************************************************/
//      JComboBox selalgoritmoHash = new JComboBox(new String[] {"RSA", "DSA"});
//		selalgoritmoHash.setPreferredSize(new Dimension(100, 20));
//		painelMostra.add(selalgoritmoHash);
        
        JButton configura = new JButton("Salvar");
        configura.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_urlTST = urlTST.getText();
				_gatewayVerificacaoCRL = gatewayVerificacaoCRL.getText();
				setTempFilesLocation(tempFilesLocation.getText());
				_tipoRepositorio = selTipoRepositorio.getSelectedIndex();
				_formaMostrarCertificados = selFormaMostrarCertificados.getSelectedIndex();
				_formaMostrarArquivos = selFormaMostrarArquivos.getSelectedIndex();
				_formaMostrarAssinaturas = selFormaMostrarAssinaturas.getSelectedIndex();
				_tipoLookAndFeel = sellookAndFeel.getSelectedIndex();

				_proxyInfoHost = proxyInfoHost.getText();
				_proxyInfoPort = Integer.parseInt(proxyInfoPort.getText());
				_proxyInfoUser = proxyInfoUser.getText();
				_proxyInfoPassword = proxyInfoPassword.getPassword().toString();

				_algoritmosDigest = selalgoritmoDigest.getSelectedItem().toString(); 
				_algoritmosCriptografia = selalgoritmoSimetrico.getSelectedItem().toString(); 
				_permiteAssinaturaComCertificadoExpiradoOuInvalido = permiteAssinaturaComCertificadoExpiradoOuInvalido.isSelected();
				_permiteAssinaturaComCertificadoSemValidarCRL = permiteAssinaturaComCertificadoSemValidarCRL.isSelected();
				_permiteAssinaturaComCertificadoNaoDigitalSignature = permiteAssinaturaComCertificadoNaoDigitalSignature.isSelected();
				_permiteAssinaturaComCertificadoNaoNonRepudiation=permiteAssinaturaComCertificadoNaoNonRepudiation.isSelected();
				_permiteAssinaturaComCertificadoNaoKeyEncipherment=permiteAssinaturaComCertificadoNaoKeyEncipherment.isSelected();
				_permiteAssinaturaComCertificadoNaoDataEncipherment=permiteAssinaturaComCertificadoNaoDataEncipherment.isSelected();
				_permiteAssinaturaComCertificadoNaoKeyAgreement=permiteAssinaturaComCertificadoNaoKeyAgreement.isSelected();
				_permiteAssinaturaComCertificadoNaoKeyCertisign=permiteAssinaturaComCertificadoNaoKeyCertisign.isSelected();
				_permiteAssinaturaComCertificadoNaoCrlSign=permiteAssinaturaComCertificadoNaoCrlSign.isSelected();
				_permiteAssinaturaComCertificadoNaoEncipherOnly=permiteAssinaturaComCertificadoNaoEncipherOnly.isSelected();
				_permiteAssinaturaComCertificadoNaoDecipherOnly=permiteAssinaturaComCertificadoNaoDecipherOnly.isSelected();

				_permiteAssinaturaComCertificadoNaoServerAuth=permiteAssinaturaComCertificadoNaoServerAuth.isSelected();
				_permiteAssinaturaComCertificadoNaoClientAuth=permiteAssinaturaComCertificadoNaoClientAuth.isSelected();
				_permiteAssinaturaComCertificadoNaoCodeSigning=permiteAssinaturaComCertificadoNaoCodeSigning.isSelected();
				_permiteAssinaturaComCertificadoNaoEmailProtection=permiteAssinaturaComCertificadoNaoEmailProtection.isSelected();
				_permiteAssinaturaComCertificadoNaoIpsecEndSystem=permiteAssinaturaComCertificadoNaoIpsecEndSystem.isSelected();
				_permiteAssinaturaComCertificadoNaoIpsecTunnel=permiteAssinaturaComCertificadoNaoIpsecTunnel.isSelected();
				_permiteAssinaturaComCertificadoNaoIpsecUser=permiteAssinaturaComCertificadoNaoIpsecUser.isSelected();
				_permiteAssinaturaComCertificadoNaoTimeStamping=permiteAssinaturaComCertificadoNaoTimeStamping.isSelected();
				_permiteAssinaturaComCertificadoNaoOCSPSigning=permiteAssinaturaComCertificadoNaoOCSPSigning.isSelected();
				_permiteAssinaturaComCertificadoNaoDvcs=permiteAssinaturaComCertificadoNaoDvcs.isSelected();
				_permiteAssinaturaComCertificadoNaoSbgpCertAAServerAuth=permiteAssinaturaComCertificadoNaoSbgpCertAAServerAuth.isSelected();
				_permiteAssinaturaComCertificadoNaoScvp_responder=permiteAssinaturaComCertificadoNaoScvp_responder.isSelected();
				_permiteAssinaturaComCertificadoNaoEapOverPPP=permiteAssinaturaComCertificadoNaoEapOverPPP.isSelected();
				_permiteAssinaturaComCertificadoNaoEapOverLAN=permiteAssinaturaComCertificadoNaoEapOverLAN.isSelected();
				_permiteAssinaturaComCertificadoNaoScvpServer=permiteAssinaturaComCertificadoNaoScvpServer.isSelected();
				_permiteAssinaturaComCertificadoNaoScvpClient=permiteAssinaturaComCertificadoNaoScvpClient.isSelected();
				_permiteAssinaturaComCertificadoNaoIpsecIKE=permiteAssinaturaComCertificadoNaoIpsecIKE.isSelected();
				_permiteAssinaturaComCertificadoNaoCapwapAC=permiteAssinaturaComCertificadoNaoCapwapAC.isSelected();
				_permiteAssinaturaComCertificadoNaoCapwapWTP=permiteAssinaturaComCertificadoNaoCapwapWTP.isSelected();
				_permiteAssinaturaComCertificadoNaoSmartcardlogon=permiteAssinaturaComCertificadoNaoSmartcardlogon.isSelected();
				
				_permiteAssinaturaComCertificadoDeSigilo = permiteAssinaturaComCertificadoDeSigilo.isSelected();
				_permiteAssinaturaComCertificadoNaoConfiavel = permiteAssinaturaComCertificadoNaoConfiavel.isSelected();
				_permiteAssinaturaComCertificadoNaoIssuerIsCA = permiteAssinaturaComCertificadoNaoIssuerIsCA.isSelected();
				_permiteAssinaturaComCertificadoNaoICPBrasil = permiteAssinaturaComCertificadoNaoICPBrasil.isSelected();
				_permiteAssinatura = permiteAssinatura.isSelected();
				_permiteCoassinatura = permiteCoassinatura.isSelected();
				_permiteMesclarAssinatura = permiteMesclarAssinatura.isSelected();
				_permiteApagarAssinatura = permiteApagarAssinatura.isSelected();
				_permiteCriptografia = permiteCriptografia.isSelected();
				_atachaDocumentoNaAssinatura = atachaDocumentoNaAssinatura.isSelected();
				_mostraApenasOsCertificadosQuePodemAssinar = mostraApenasOsCertificadosQuePodemAssinar.isSelected();
				_mostraSubjectDN = mostraSubjectDN.isSelected();
				_mostraIssuerDN = mostraIssuerDN.isSelected();
				_mostraSubjectAlternative = mostraSubjectAlternative.isSelected();
				_mostraKeyUsage = mostraKeyUsage.isSelected();
				_mostraExtendedKeyUsage = mostraExtendedKeyUsage.isSelected();
				_mostraPolicies = mostraPolicies.isSelected();
				_mostraDadosCRL = mostraDadosCRL.isSelected();
				_mostraToString = mostraToString.isSelected();
				_mostraConteudo = mostraConteudo.isSelected();
				salvar();
				frame.setVisible(false);
				ICPBravoManager.reset();
				if (listener != null)
					listener.onAlterarConfiguracao();
			}
        });
        painel.add(abas);
        painel.add(configura);
        
        frame.setVisible(true);
	}

	public String getAlgoritmoDigest() {
		return _algoritmosDigest;
	}

	public DigestAlgorithm getAlgoritmoDigest(Provider provider) { 
		try {
			return DigestAlgorithm.getInstance(_algoritmosDigest, provider);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public AsymmetricAlgorithm getAlgoritmoAssinatura(Provider provider) { 
		try {
			return AsymmetricAlgorithm.getInstance(_algoritmosAssinatura, ICPBravoManager.getICPBravoProvider());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public SymmetricAlgorithm getAlgoritmoCriptografia(Provider provider) {
		try {
			return SymmetricAlgorithm.getInstance(_algoritmosCriptografia, provider);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setUltimoDiretorio(String ultimoDiretorio) { 
		this._ultimoDiretorio = ultimoDiretorio; 
		salvar(); 
	}
	
	public String getUltimoDiretorio() { return _ultimoDiretorio; }
	
	public int getTipoLookAndFeel() {
		return _tipoLookAndFeel;
	}
	
	public int getFormaMostrarCertificados() {
		return _formaMostrarCertificados;
	}
	
	public int getFormaMostrarArquivos() {
		return _formaMostrarArquivos;
	}
	
	public int getFormaMostrarAssinaturas() {
		return _formaMostrarAssinaturas;
	}
	
	public final static int SOMENTE_CRIPTOGRAFIA=0; 
	public final static int CRIPTOGRAFADA_E_ASSINADO=1; 
	public final static int ASSINADO_E_CRIPTOGRAFADA=2; 

	public int tipoCripografia() {
		return SOMENTE_CRIPTOGRAFIA;
	}
	
	public boolean podeCriptografar(ICPBravoCertificate certificado) {
		return certificado != null;
	}
	
	public boolean podeAssinar(ICPBravoCertificate certificado) {
		return certificado != null && certificado.canSign() && verificaSignatario(certificado);
	}

	public boolean podeDecriptografar(ICPBravoCertificate certificado) {
		return certificado != null;
	}
	
	public String mensagemSignatario(ICPBravoCertificate certificado) throws CertificateExpiredException, CertificateNotYetValidException {
		String toReturn = "";

		if (certificado == null)
			toReturn += "Certificado null\n";
		else {
			if (!certificado.canSign())
				toReturn += "Certificado não tem chave privativa\n";
			if (!(_permiteAssinaturaComCertificadoExpiradoOuInvalido || (!certificado.isExpiresOrInvalid())))
				toReturn += "Certificado expirado\n";
			if (!(_permiteAssinaturaComCertificadoSemValidarCRL || certificado.isCRLOk(this._gatewayVerificacaoCRL, this._proxyInfo)))
				toReturn += "Certificado revogado ou impossível de verificar CRL\n";
			if (!(_permiteAssinaturaComCertificadoNaoDigitalSignature || certificado.isKeyUsageExtensionDigitalSignature()))
				toReturn += "Certificado não é assinado digital\n";
			if (!(_permiteAssinaturaComCertificadoNaoNonRepudiation || certificado.isKeyUsageExtensionNonRepudiation()))
				toReturn += "Certificado não é não repudiável\n";
			if (!(_permiteAssinaturaComCertificadoNaoKeyEncipherment || certificado.isKeyUsageExtensionKeyEncipherment()))
				toReturn += "Certificado não é um cifrador de chaves\n";
			if (!(_permiteAssinaturaComCertificadoNaoDataEncipherment || certificado.isKeyUsageExtensionDataEncipherment()))
				toReturn += "Certificado não é um cifrador de dados\n";
			if (!(_permiteAssinaturaComCertificadoNaoKeyAgreement || certificado.isKeyUsageExtensionKeyAgreement()))
				toReturn += "Certificado não é chave de contrato\n";
			if (!(_permiteAssinaturaComCertificadoNaoKeyCertisign || certificado.isKeyUsageExtensionKeyCertisign()))
				toReturn += "Certificado não é um assinador de certificados\n";
			if (!(_permiteAssinaturaComCertificadoNaoCrlSign || certificado.isKeyUsageExtensionCrlSign()))
				toReturn += "Certificado não é um assinador de CRL\n";
			if (!(_permiteAssinaturaComCertificadoNaoEncipherOnly || certificado.isKeyUsageExtensionEncipherOnly()))
				toReturn += "Certificado não é apenas um cifrador\n";
			if (!(_permiteAssinaturaComCertificadoNaoDecipherOnly || certificado.isKeyUsageExtensionDecipherOnly()))
				toReturn += "Certificado não é apenas um decifrador\n";
			if (!(_permiteAssinaturaComCertificadoNaoServerAuth || certificado.isEnhancedKeyUsageExtensionServerAuth()))
				toReturn += "Certificado não é de autenticação de servidor\n";
			if (!(_permiteAssinaturaComCertificadoNaoClientAuth || certificado.isEnhancedKeyUsageExtensionClientAuth()))
				toReturn += "Certificado não é de autenticação de cliente\n";
			if (!(_permiteAssinaturaComCertificadoNaoCodeSigning || certificado.isEnhancedKeyUsageExtensionCodeSigning()))
				toReturn += "Certificado não é um assinador de código\n";
			if (!(_permiteAssinaturaComCertificadoNaoEmailProtection || certificado.isEnhancedKeyUsageExtensionEmailProtection()))
				toReturn += "Certificado não é para proteção de email\n";
			if (!(_permiteAssinaturaComCertificadoNaoIpsecEndSystem || certificado.isEnhancedKeyUsageExtensionIpsecEndSystem()))
				toReturn += "Certificado não é um Ipsec End System\n";
			if (!(_permiteAssinaturaComCertificadoNaoIpsecTunnel || certificado.isEnhancedKeyUsageExtensionIpsecTunnel()))
				toReturn += "Certificado não é um Ipsec Tunnel\n";
			if (!(_permiteAssinaturaComCertificadoNaoIpsecUser || certificado.isEnhancedKeyUsageExtensionIpsecUser()))
				toReturn += "Certificado não é um Ipsec User\n";
			if (!(_permiteAssinaturaComCertificadoNaoTimeStamping || certificado.isEnhancedKeyUsageExtensionTimeStamping()))
				toReturn += "Certificado não é para assinar timestamp\n";
			if (!(_permiteAssinaturaComCertificadoNaoOCSPSigning || certificado.isEnhancedKeyUsageExtensionOCSPSigning()))
				toReturn += "Certificado não é para assinar OCSP\n";
			if (!(_permiteAssinaturaComCertificadoNaoDvcs || certificado.isEnhancedKeyUsageExtensionDvcs()))
				toReturn += "Certificado não é um Dvcs\n";
			if (!(_permiteAssinaturaComCertificadoNaoSbgpCertAAServerAuth || certificado.isEnhancedKeyUsageExtensionSbgpCertAAServerAuth()))
				toReturn += "Certificado não é um SbgpCertAAServAuth\n";
			if (!(_permiteAssinaturaComCertificadoNaoScvp_responder || certificado.isEnhancedKeyUsageExtensionScvp_responder()))
				toReturn += "Certificado não é um Scvp responder\n";
			if (!(_permiteAssinaturaComCertificadoNaoEapOverPPP || certificado.isEnhancedKeyUsageExtensionEapOverPPP()))
				toReturn += "Certificado não é um Eap over PPP\n";
			if (!(_permiteAssinaturaComCertificadoNaoEapOverLAN || certificado.isEnhancedKeyUsageExtensionEapOverLAN()))
				toReturn += "Certificado não é um Eap over Lan\n";
			if (!(_permiteAssinaturaComCertificadoNaoScvpServer || certificado.isEnhancedKeyUsageExtensionScvpServer()))
				toReturn += "Certificado não é um Scvp server\n";
			if (!(_permiteAssinaturaComCertificadoNaoScvpClient || certificado.isEnhancedKeyUsageExtensionScvpClient()))
				toReturn += "Certificado não é um Scvp client\n";
			if (!(_permiteAssinaturaComCertificadoNaoIpsecIKE || certificado.isEnhancedKeyUsageExtensionIpsecIKE()))
				toReturn += "Certificado não é um Ipsec IKE\n";
			if (!(_permiteAssinaturaComCertificadoNaoCapwapAC || certificado.isEnhancedKeyUsageExtensionCapwapAC()))
				toReturn += "Certificado não é um Capwap AC\n";
			if (!(_permiteAssinaturaComCertificadoNaoCapwapWTP || certificado.isEnhancedKeyUsageExtensionCapwapWTP()))
				toReturn += "Certificado não é um Capwap WTP\n";
			if (!(_permiteAssinaturaComCertificadoNaoSmartcardlogon || certificado.isEnhancedKeyUsageExtensionSmartcardlogon()))
				toReturn += "Certificado não é um Smartcard logon\n";
			if (!(_permiteAssinaturaComCertificadoDeSigilo || (!certificado.isSecrecy())))
				toReturn += "Certificado é de sigilo\n";
			if (!(_permiteAssinaturaComCertificadoNaoIssuerIsCA || certificado.isAllIssuersIsCA()))
				toReturn += "Certificado não é emitido por um certificado de AC\n";
			if (!(_permiteAssinaturaComCertificadoNaoConfiavel || certificado.isTrusted()))
				toReturn += "Certificado não está na cadeia de confiáveis\n";
			if (!(_permiteAssinaturaComCertificadoNaoICPBrasil || certificado.isICPBrasil()))
				toReturn += "Certificado não é ICP-Brasil\n";
		}
		
		return toReturn;
	}
	
	public boolean verificaSignatario(ICPBravoCertificate certificado) {
		return certificado != null && 
//			certificado.inicializadoCorretamente() &&
			(_permiteAssinaturaComCertificadoExpiradoOuInvalido || (!certificado.isExpiresOrInvalid())) &&
			(_permiteAssinaturaComCertificadoSemValidarCRL || certificado.isCRLOk(this._gatewayVerificacaoCRL, this._proxyInfo)) &&
			(_permiteAssinaturaComCertificadoNaoDigitalSignature || certificado.isKeyUsageExtensionDigitalSignature()) &&
			(_permiteAssinaturaComCertificadoNaoNonRepudiation || certificado.isKeyUsageExtensionNonRepudiation()) &&
			(_permiteAssinaturaComCertificadoNaoKeyEncipherment || certificado.isKeyUsageExtensionKeyEncipherment()) &&
			(_permiteAssinaturaComCertificadoNaoDataEncipherment || certificado.isKeyUsageExtensionDataEncipherment()) &&
			(_permiteAssinaturaComCertificadoNaoKeyAgreement || certificado.isKeyUsageExtensionKeyAgreement()) &&
			(_permiteAssinaturaComCertificadoNaoKeyCertisign || certificado.isKeyUsageExtensionKeyCertisign()) &&
			(_permiteAssinaturaComCertificadoNaoCrlSign || certificado.isKeyUsageExtensionCrlSign()) &&
			(_permiteAssinaturaComCertificadoNaoEncipherOnly || certificado.isKeyUsageExtensionEncipherOnly()) &&
			(_permiteAssinaturaComCertificadoNaoDecipherOnly || certificado.isKeyUsageExtensionDecipherOnly()) &&
			(_permiteAssinaturaComCertificadoNaoServerAuth || certificado.isEnhancedKeyUsageExtensionServerAuth()) &&
			(_permiteAssinaturaComCertificadoNaoClientAuth || certificado.isEnhancedKeyUsageExtensionClientAuth()) &&
			(_permiteAssinaturaComCertificadoNaoCodeSigning || certificado.isEnhancedKeyUsageExtensionCodeSigning()) &&
			(_permiteAssinaturaComCertificadoNaoEmailProtection || certificado.isEnhancedKeyUsageExtensionEmailProtection()) &&
			(_permiteAssinaturaComCertificadoNaoIpsecEndSystem || certificado.isEnhancedKeyUsageExtensionIpsecEndSystem()) &&
			(_permiteAssinaturaComCertificadoNaoIpsecTunnel || certificado.isEnhancedKeyUsageExtensionIpsecTunnel()) &&
			(_permiteAssinaturaComCertificadoNaoIpsecUser || certificado.isEnhancedKeyUsageExtensionIpsecUser()) &&
			(_permiteAssinaturaComCertificadoNaoTimeStamping || certificado.isEnhancedKeyUsageExtensionTimeStamping()) &&
			(_permiteAssinaturaComCertificadoNaoOCSPSigning || certificado.isEnhancedKeyUsageExtensionOCSPSigning()) &&
			(_permiteAssinaturaComCertificadoNaoDvcs || certificado.isEnhancedKeyUsageExtensionDvcs()) &&
			(_permiteAssinaturaComCertificadoNaoSbgpCertAAServerAuth || certificado.isEnhancedKeyUsageExtensionSbgpCertAAServerAuth()) &&
			(_permiteAssinaturaComCertificadoNaoScvp_responder || certificado.isEnhancedKeyUsageExtensionScvp_responder()) &&
			(_permiteAssinaturaComCertificadoNaoEapOverPPP || certificado.isEnhancedKeyUsageExtensionEapOverPPP()) &&
			(_permiteAssinaturaComCertificadoNaoEapOverLAN || certificado.isEnhancedKeyUsageExtensionEapOverLAN()) &&
			(_permiteAssinaturaComCertificadoNaoScvpServer || certificado.isEnhancedKeyUsageExtensionScvpServer()) &&
			(_permiteAssinaturaComCertificadoNaoScvpClient || certificado.isEnhancedKeyUsageExtensionScvpClient()) &&
			(_permiteAssinaturaComCertificadoNaoIpsecIKE || certificado.isEnhancedKeyUsageExtensionIpsecIKE()) &&
			(_permiteAssinaturaComCertificadoNaoCapwapAC || certificado.isEnhancedKeyUsageExtensionCapwapAC()) &&
			(_permiteAssinaturaComCertificadoNaoCapwapWTP || certificado.isEnhancedKeyUsageExtensionCapwapWTP()) &&
			(_permiteAssinaturaComCertificadoNaoSmartcardlogon || certificado.isEnhancedKeyUsageExtensionSmartcardlogon()) &&
			(_permiteAssinaturaComCertificadoDeSigilo || (!certificado.isSecrecy())) &&
			(_permiteAssinaturaComCertificadoNaoIssuerIsCA || certificado.isAllIssuersIsCA()) &&
			(_permiteAssinaturaComCertificadoNaoConfiavel || certificado.isTrusted()) &&
			(_permiteAssinaturaComCertificadoNaoICPBrasil || certificado.isICPBrasil());
	}

	public boolean permiteAssinatura() { return _permiteAssinatura; }
	public boolean permiteCoassinatura() { return _permiteCoassinatura; }
	public boolean permiteMesclarAssinatura() { return _permiteMesclarAssinatura; }
	public boolean permiteApagarAssinatura() { return _permiteApagarAssinatura; }
	public boolean atachaDocumentoNaAssinatura() { return _atachaDocumentoNaAssinatura; }
	public boolean permiteCriptografia() { return _permiteCriptografia; }

	public boolean mostraApenasOsCertificadosQuePodemAssinar() { return _mostraApenasOsCertificadosQuePodemAssinar; }
	public boolean mostraSubjectDN() { return _mostraSubjectDN; }
	public boolean mostraIssuerDN() { return _mostraIssuerDN; }
	public boolean mostraSubjectAlternative() { return _mostraSubjectAlternative; }
	public boolean mostraKeyUsage() { return _mostraKeyUsage; }
	public boolean mostraExtendedKeyUsage() { return _mostraExtendedKeyUsage; }
	public boolean mostraPolicies() { return _mostraPolicies; }
	public boolean mostraDadosCRL() { return _mostraDadosCRL; }
	public boolean mostraToString() { return _mostraToString; }
	public boolean mostraConteudo() { return _mostraConteudo; }

	public void setNomeArquivoConfiguracao(String nomeArquivoConfiguracao) {
		this._nomeArquivoConfiguracao = nomeArquivoConfiguracao;
	}

	public String getNomeArquivoConfiguracao() {
		return _nomeArquivoConfiguracao;
	}

	public String getParaAssinar() {
		return _paraAssinar;
	}

	public String getAssinaturaRemota() {
		return _assinaturaRemota;
	}

	public boolean getSomenteVerificar() {
		return _somenteVerificar;
	}

	public boolean getPodeEditarDadoAAssinar() {
		return _podeEditarDadoAAssinar;
	}
	
	private int getTipoRepositorio() {
		return _tipoRepositorio;
	}

	public String getManagerName() {
		return tiposRepositorio[getTipoRepositorio()];
	}
	
	public ICPBravoManager getManager() throws NoSuchAlgorithmException, CryptographicDeviceNotFoundException, KeyStoreException, NoSuchProviderException, FileNotFoundException  {
		String key = getManagerName();
		if (key.equals(PKCS11Manager.ManagerName) && this._definicoesPKCS11 != null) {
			return new PKCS11Manager(this._definicoesPKCS11);
		}
		return ICPBravoManager.getManagerInstance(key);
	}
	
	public void setURLTst(String urlTst) {
		_urlTST = urlTst;
	}
	
	public String getURLTst() {
		return _urlTST;
	}
	
	public Policy getPolicy() {
		return getPolicy(_policy, _urlTST);
	}
	
	public static Policy getPolicy(String policy, String urlTST) {
		if (policy == null)
			policy = "";
		if (policy.equals("AD-CP"))
			return new AdCpPolicy();
		else if (policy.equals("AD-A"))
			return new AdAPolicy(new TimestampRequester(urlTST));
		else if (policy.equals("AD-D"))
			return new AdCPolicy(new TimestampRequester(urlTST));
		else if (policy.equals("AD-R"))
			return new AdRPolicy(new TimestampRequester(urlTST));
		else if (policy.equals("AD-T"))
			return new AdTPolicy(new TimestampRequester(urlTST));
		else
			return new CMSPolicy();
	}
	
	public String getGatewayVerificacaoCRL() {
		return _gatewayVerificacaoCRL;
	}
	
	public String getFrameRetorno() {
		return _frameRetorno;
	}
	
	public String getTempFilesLocation() {
		return ICPBravoUtil.getTempFilesLocation();
	}
	
	public void setTempFilesLocation(String tempFilesLocation) {
		_tempFilesLocation = tempFilesLocation;
		ICPBravoUtil.setTempFilesLocation(tempFilesLocation);
	}
	
	public String getDefinicoesPKCS11() {
		return _definicoesPKCS11;
	}
	
	public void setApresentaDialogsErro(boolean apresentaDialogsErro) {
		_apresentaDialogsErro = apresentaDialogsErro;
	}
	
	public boolean getApresentaDialogsErro() {
		return _apresentaDialogsErro;
	}

	public ProxyInfo getProxyInfo() {
		if (_proxyInfo == null && _proxyInfoHost != null && _proxyInfoHost.length() > 0) {
			_proxyInfo = new ProxyInfo(_proxyInfoHost, _proxyInfoPort, _proxyInfoUser, _proxyInfoPassword);
		}
		return _proxyInfo;
	}
	
	private void install(String urlServer) throws Exception {
		String [] archives = {"MsCAPI.dll"};
//		URL urlCodeBase = applet.getCodeBase();
//		String urlServer = urlCodeBase.toString();
		
		for (String archive : archives) {
			String local = ICPBravoUtil.getTempFilesLocation()+"/"+archive;
			if (! new File(local).exists()) {
				String remote = urlServer+archive;
				
				System.out.println("Arquivo "+local+" não encontrado.");
				try {
					HttpLoad load = new HttpLoad(remote, null, null);
					byte [] loaded = load.loadFromLocation();
					ICPBravoUtil.writeFile(local, loaded);
					System.out.println("Arquivo "+local+" carregado do servidor.");
				} catch (Exception e) {
					System.out.println("Arquivo "+local+" não carregado do servidor.");
					e.printStackTrace();
				}
			} else
				System.out.println("Arquivo "+local+" presente.");
		}
	}
}
