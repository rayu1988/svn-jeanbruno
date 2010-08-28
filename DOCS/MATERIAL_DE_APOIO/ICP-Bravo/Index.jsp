<html>
<header>
	<title>ICPBravo - Sample</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
	<link href="pages/icpbravo.css" rel="stylesheet" type="text/css" />
</header>
<body>
 
<table width='100%'><tr>
<td align='left'><a href="/ICPBravoJSP/index.jsp"><img src='/ICPBravoJSP/img/LogoGrande.png' border='0'></a></td>
<td align='right'><h1><a href='http://www.oaks.com.br'>OAK Solu��es Empresariais</a></h1></td>
</tr></table>

<%
Object logged = session.getAttribute("logged");
if (logged == null) {
	String usr=request.getParameter("usr");
	String pw=request.getParameter("pw");
	
	if (usr == null) {
		%><META http-equiv="refresh" content="0;URL=/ICPBravoJSP/pages/Login.jsp"><%
	} else {
		logged = IcpBravoJSP.Util.getUser(usr, pw);
		if (logged != null) {
			session.setAttribute("logged", logged);
		} else {
			%>
			Usu�rio Inv�lido
			<META http-equiv="refresh" content="5;URL=/ICPBravoJSP/pages/Login.jsp">
			<%
		}
	}
}
if (logged != null) {
System.out.println("Logando com "+logged.toString());
%>

<h1 style='width:100%;text-align:center;'>Exemplos para treinamento na biblioteca ICP-Bravo</h1>
<p>Os exemplos a seguir procuram explorar caracter�sticas comuns em aplica��es onde se faz necess�rio o uso de certifica��o digital.</p>
<p>� importante abstrair que alguma rotinas s�o executadas do lado do servidor (normalmente s�o as que fazem acesso ao c�digo do SDK diretamente em linguagem java), por�m nos casos onde � necess�rio interagir com dispositivos criptogr�ficos ou chaves privativas que s� est�o dispon�veis na esta��o do usu�rio, devemos utilizar o SDK no browser do lado do cliente (por meio do uso do applet).</p>
<p>Procurem observar esta caracter�stica nos exemplos.</p>
<h3>Entendendo certificados digitais e cadeias</h3>
<ul>
<li><a href="/ICPBravoJSP/pages/ServerGenerateRootCertificate.jsp">Gerando o certificado raiz</a></li>
<li><a href="/ICPBravoJSP/pages/ViewCA.jsp">Diret�rio da AC</a></li>
<li><a href="/ICPBravoJSP/pages/GenerateCSR.jsp">Gerando o certificado de usu�rio</a></li>
<li><a href="/ICPBravoJSP/pages/ShowCertificate.jsp">Vendo um certificado</a></li>
<li><a href="/ICPBravoJSP/pages/AddTrusted.jsp">Criando uma cadeia de certificados confi�veis</a></li>
<li><a href="/ICPBravoJSP/pages/RevogateCertificate.jsp">Revogando um certificado</a></li>
</ul>
<h3>Entendendo assinaturas digitais</h3>
<ul>
<li><a href="/ICPBravoJSP/pages/SignString.jsp">Assinatura de String</a></li>
<li><a href="/ICPBravoJSP/pages/SignWebForm.jsp">Assinatura de Formul�rio WEB</a></li>
<li><a href="/ICPBravoJSP/pages/SignUrl.jsp">Assinatura de URL</a></li>
<li><a href="/ICPBravoJSP/pages/SignRemote.jsp">Assinatura Remota</a></li>
<li><a href="/ICPBravoJSP/pages/CoSignString.jsp">Co-Assinatura de String</a></li>
<li><a href="/ICPBravoJSP/pages/SignPdfUrl.jsp">Assinatura de PDF</a></li>
<li><a href="/ICPBravoJSP/pages/SignPdfRemote.jsp">Assinatura de PDF Remota</a></li>
<li><a href="/ICPBravoJSP/pages/CryptString.jsp">Criptografia de String</a></li>
</ul>
<h3>O SDK</h3>
<ul>
<li><a href="/ICPBravoJSP/pages/ViewApplet.jsp">Applet com interface de usu�rio</a></li>
<li><a href="/ICPBravoJSP/doc/index.html">Documenta��o do SDK</a></li>
<li><a href="/ICPBravoJSP/pages/Login.jsp">Sair da aplica��o</a></li>
</ul>

<% } %>
</body></html>
