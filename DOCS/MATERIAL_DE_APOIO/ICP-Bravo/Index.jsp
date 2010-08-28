<html>
<header>
	<title>ICPBravo - Sample</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
	<link href="pages/icpbravo.css" rel="stylesheet" type="text/css" />
</header>
<body>
 
<table width='100%'><tr>
<td align='left'><a href="/ICPBravoJSP/index.jsp"><img src='/ICPBravoJSP/img/LogoGrande.png' border='0'></a></td>
<td align='right'><h1><a href='http://www.oaks.com.br'>OAK Soluções Empresariais</a></h1></td>
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
			Usuário Inválido
			<META http-equiv="refresh" content="5;URL=/ICPBravoJSP/pages/Login.jsp">
			<%
		}
	}
}
if (logged != null) {
System.out.println("Logando com "+logged.toString());
%>

<h1 style='width:100%;text-align:center;'>Exemplos para treinamento na biblioteca ICP-Bravo</h1>
<p>Os exemplos a seguir procuram explorar características comuns em aplicações onde se faz necessário o uso de certificação digital.</p>
<p>É importante abstrair que alguma rotinas são executadas do lado do servidor (normalmente são as que fazem acesso ao código do SDK diretamente em linguagem java), porém nos casos onde é necessário interagir com dispositivos criptográficos ou chaves privativas que só estão disponíveis na estação do usuário, devemos utilizar o SDK no browser do lado do cliente (por meio do uso do applet).</p>
<p>Procurem observar esta característica nos exemplos.</p>
<h3>Entendendo certificados digitais e cadeias</h3>
<ul>
<li><a href="/ICPBravoJSP/pages/ServerGenerateRootCertificate.jsp">Gerando o certificado raiz</a></li>
<li><a href="/ICPBravoJSP/pages/ViewCA.jsp">Diretório da AC</a></li>
<li><a href="/ICPBravoJSP/pages/GenerateCSR.jsp">Gerando o certificado de usuário</a></li>
<li><a href="/ICPBravoJSP/pages/ShowCertificate.jsp">Vendo um certificado</a></li>
<li><a href="/ICPBravoJSP/pages/AddTrusted.jsp">Criando uma cadeia de certificados confiáveis</a></li>
<li><a href="/ICPBravoJSP/pages/RevogateCertificate.jsp">Revogando um certificado</a></li>
</ul>
<h3>Entendendo assinaturas digitais</h3>
<ul>
<li><a href="/ICPBravoJSP/pages/SignString.jsp">Assinatura de String</a></li>
<li><a href="/ICPBravoJSP/pages/SignWebForm.jsp">Assinatura de Formulário WEB</a></li>
<li><a href="/ICPBravoJSP/pages/SignUrl.jsp">Assinatura de URL</a></li>
<li><a href="/ICPBravoJSP/pages/SignRemote.jsp">Assinatura Remota</a></li>
<li><a href="/ICPBravoJSP/pages/CoSignString.jsp">Co-Assinatura de String</a></li>
<li><a href="/ICPBravoJSP/pages/SignPdfUrl.jsp">Assinatura de PDF</a></li>
<li><a href="/ICPBravoJSP/pages/SignPdfRemote.jsp">Assinatura de PDF Remota</a></li>
<li><a href="/ICPBravoJSP/pages/CryptString.jsp">Criptografia de String</a></li>
</ul>
<h3>O SDK</h3>
<ul>
<li><a href="/ICPBravoJSP/pages/ViewApplet.jsp">Applet com interface de usuário</a></li>
<li><a href="/ICPBravoJSP/doc/index.html">Documentação do SDK</a></li>
<li><a href="/ICPBravoJSP/pages/Login.jsp">Sair da aplicação</a></li>
</ul>

<% } %>
</body></html>
