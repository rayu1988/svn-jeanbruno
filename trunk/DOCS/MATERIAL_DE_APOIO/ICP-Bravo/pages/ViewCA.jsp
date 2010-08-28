<html>
<header>
	<title>ICPBravo - Adiciona um certificado confiável - Processamento</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1>Diretório do servidor</h1>
<h3>Server-Side</h3>
<p>Esta página serve para apresentar o diretório da AC, onde poderemos verificar os arquivos criados no servidor.</p>
<hr />
	<ul>
<%
	String msg="";
	try {
		java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
		String certificados = resourceBundle.getString("home")+"/certs";
		java.io.File dir = new java.io.File(certificados);
		if (!dir.exists())
			dir.mkdir();
		java.io.File [] files = dir.listFiles();
		for (java.io.File file : files) {
			if (!file.isDirectory()) {
%><li>
<%= file.getName() %>: 
<a href='<%= resourceBundle.getString("dominio")+"/ICPBravoJSP/certs/"+file.getName() %>'>[Baixar]</a> 
</li><%
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
		msg = e.getMessage();
	}
%>
	</ul>
<%=msg%>
</body></html>
