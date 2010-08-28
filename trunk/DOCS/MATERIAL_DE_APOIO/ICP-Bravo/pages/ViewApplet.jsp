<html>
<header>
	<title>ICPBravo - Visualizando Applet</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>

<applet id='ICPBravoApplet'
		width='100%'
		height='800px'
		code='br.com.oaks.ICPBravo.applet.AppletICPBravo'
		codebase='../lib'
		archive='/ICPBravoJSP/lib/ICPBravoAPI-1.20.jar,/ICPBravoJSP/lib/commons-codec.jar,/ICPBravoJSP/lib/commons-httpclient.jar,/ICPBravoJSP/lib/commons-logging.jar,/ICPBravoJSP/lib/mail.jar'
		mayscript>
		<param name='AlgoritmosConsumo' value='SHA1'>
</applet>

</body></html>