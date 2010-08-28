<script type="text/javascript" src="../scripts/shCore.js"></script>
<script type="text/javascript" src="../scripts/shBrushBash.js"></script>
<script type="text/javascript" src="../scripts/shBrushCpp.js"></script>
<script type="text/javascript" src="../scripts/shBrushCSharp.js"></script>
<script type="text/javascript" src="../scripts/shBrushCss.js"></script>
<script type="text/javascript" src="../scripts/shBrushDelphi.js"></script>
<script type="text/javascript" src="../scripts/shBrushDiff.js"></script>
<script type="text/javascript" src="../scripts/shBrushGroovy.js"></script>
<script type="text/javascript" src="../scripts/shBrushJava.js"></script>
<script type="text/javascript" src="../scripts/shBrushJScript.js"></script>
<script type="text/javascript" src="../scripts/shBrushPhp.js"></script>
<script type="text/javascript" src="../scripts/shBrushPlain.js"></script>
<script type="text/javascript" src="../scripts/shBrushPython.js"></script>
<script type="text/javascript" src="../scripts/shBrushRuby.js"></script>
<script type="text/javascript" src="../scripts/shBrushScala.js"></script>
<script type="text/javascript" src="../scripts/shBrushSql.js"></script>
<script type="text/javascript" src="../scripts/shBrushVb.js"></script>
<script type="text/javascript" src="../scripts/shBrushXml.js"></script>
<link type="text/css" rel="stylesheet" href="../styles/shCore.css"/>
<link type="text/css" rel="stylesheet" href="../styles/shThemeMidnight.css"/>
<script type="text/javascript">
	SyntaxHighlighter.config.clipboardSwf = '../scripts/clipboard.swf';
	SyntaxHighlighter.all();
</script>

<link href="icpbravo.css" rel="stylesheet" type="text/css" />


<table width='100%'><tr>
<td align='left'><a href="/ICPBravoJSP/index.jsp"><img src='/ICPBravoJSP/img/LogoGrande.png' border='0'></a></td>
<td align='right'><h1><a href='http://www.oaks.com.br'>OAK Soluções Empresariais</a></h1></td>
</tr></table>

<%
if (session.getAttribute("logged") == null) {
	String usr=request.getParameter("usr");
	String pw=request.getParameter("pw");
	
	if (usr == null) {
		%><META http-equiv="refresh" content="0;URL=Login.jsp"><%
	}
}
%>
