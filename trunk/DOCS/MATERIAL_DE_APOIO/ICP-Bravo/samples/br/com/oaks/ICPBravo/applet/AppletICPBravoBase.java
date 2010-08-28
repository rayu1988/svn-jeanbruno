package br.com.oaks.ICPBravo.applet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.swing.JApplet;

import br.com.oaks.ICPBravo.cms.content.BytesContent;
import br.com.oaks.ICPBravo.util.ICPBravoUtil;

public abstract class AppletICPBravoBase extends JApplet {
	protected CurrentConfiguration _currentConfiguration;
	private static final long serialVersionUID = 1L;

	public AppletICPBravoBase () {
	}
	
	public void init() {
		onInit();
		System.out.println("Applet Incializado "+System.currentTimeMillis());
	}
	
	protected void showPolicies() throws Exception {
		String jre = System.getProperty("java.home");
		String jrePolicy = (jre+"/lib/security/java.policy").replaceAll("\\\\", "/");
		BufferedReader in = new BufferedReader(new FileReader(jrePolicy));
		String tmp = ICPBravoUtil.getTmpRoot();
		ICPBravoUtil.mkDir(tmp, false);
		String newFile = (tmp+"/java.policy").replaceAll("\\\\", "/");
		BufferedWriter out = new BufferedWriter(new FileWriter(newFile));
		String readed;
		boolean foundGrant = false;
		boolean changePermission = false;
		boolean permissionHasChanged = false;
		String newPermission = "\tpermission java.net.SocketPermission \"*\", \"connect, resolve\";";
		// permission java.net.SocketPermission "www.certificadodigital.com.br:80", "connect, resolve";
		while ((readed = in.readLine()) != null) {
			String toOut = readed;
			if (readed.indexOf("grant {") >= 0) {
				foundGrant = true;
			} else if (readed.indexOf("}") >= 0) {
				foundGrant = false;
			}
			if (foundGrant && readed.indexOf("permission java.net.SocketPermission") >= 0) {
				if (readed.equals(newPermission)) {
					permissionHasChanged = true;
				}
				toOut = "//"+readed+"\n\t// necess"+ConfiguracaoApplet.aacute+"rio para o ICP-Bravo para o acesso "+ConfiguracaoApplet.agrave+"s CRLs"+"\n"+newPermission;
				changePermission = true;
			}
			out.write(toOut+"\n");
		}
		in.close();
		out.close();
		
		if ((!permissionHasChanged) && changePermission) {
//			JOptionPane.showMessageDialog(null, "A sua configura"+ConfiguracaoApplet.ccedil+""+ConfiguracaoApplet.atilde+"o n"+ConfiguracaoApplet.atilde+"o permite a valida"+ConfiguracaoApplet.ccedil+""+ConfiguracaoApplet.atilde+"o de CRL online.\nPara permitir esta funcionalidade, voc"+ConfiguracaoApplet.ecirc+" deve substituir o arquivo "+jrePolicy+" pelo arquivo "+newFile+" e reiniciar a aplica"+ConfiguracaoApplet.ccedil+""+ConfiguracaoApplet.atilde+"o.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
			
//			File original = new File(jrePolicy);
//			original.renameTo(new File(jrePolicy+".old"));
//			
//			File novo = new File(newFile);
//			novo.renameTo(new File(jrePolicy));
		}
	}

	public void start() {
		try {
			showPolicies();
		} catch (Exception e) {
			e.printStackTrace();
		}
		onStart();
		System.out.println("Applet startado "+System.currentTimeMillis());
	}
	
	public void stop() {
	}

	public void destroy() {
	}

	public abstract void onInit();
	public abstract void onStart();

	public String getParameter(String key) {
	    String toReturn = null;

	    try {
		     toReturn = super.getParameter(key);
		} catch(java.lang.NullPointerException ex) {
	    }

	    return toReturn;
	}
	
	protected static String javascriptDecode(String str) {
		try {
			return URLDecoder.decode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	}
	
	protected static String javascriptEncode(String str) {
		try {
			return URLEncoder.encode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	}
	
	protected static String url2Web(String url) {
		return url.replaceAll("\\%", "#");
	}

	protected void callJavaScriptB64(String javaScriptInit, String javaScriptAdd, String javaScriptFinalize, byte [] value) throws MalformedURLException {
		callJavaScript(javaScriptInit, javaScriptAdd, javaScriptFinalize, new BytesContent(value).getBase64Encoded());		
	}

	protected void callJavaScriptHex(String javaScriptInit, String javaScriptAdd, String javaScriptFinalize, byte [] value) throws MalformedURLException {
		callJavaScript(javaScriptInit, javaScriptAdd, javaScriptFinalize, new BytesContent(value).getHexEncoded());		
	}
	
	// Método que invoca o JS com valores muito grandes, que devem ser particionados em pedaços de 100 bytes,
	// Onde será invocada uma função de inicialização, outra adicionando os pedaços e outra para finalizar
	protected void callJavaScript(String javaScriptInit, String javaScriptAdd, String javaScriptFinalize, String value) throws MalformedURLException {
		int passo=100;
		int tam=value.length();
		callJavaScript(javaScriptInit);
		for (int a=0; a<value.length(); a+=passo) {
			String pedaco;
			if (a+passo < tam)
				pedaco = value.substring(a, a+passo);
			else
				pedaco = value.substring(a);
			// Antes de enviar, converte o parâmetro para url e depois troca os caracteres % por # 
			callJavaScript(javaScriptAdd+"(\"" + url2Web(javascriptEncode(pedaco)) + "\");");
		}
		callJavaScript(javaScriptFinalize);
	}
	
	protected void callJavaScript(String javaScript) throws MalformedURLException {
//		JSObject win = JSObject.getWindow(this);
//		  win.eval("appletLoaded()");

		String frameRetorno = _currentConfiguration.getConf().getFrameRetorno();
		
		if (frameRetorno == null)
			getAppletContext().showDocument(new URL("javascript:" + javaScript));
		else
			getAppletContext().showDocument(new URL("javascript:" + javaScript), frameRetorno);
	}
}
