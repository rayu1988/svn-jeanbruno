<%
response.setContentType ("application/pdf");
response.setHeader ("Content-Disposition", "inline; filename=\"PdfSign.pdf\"");
String pdf=request.getParameter("pdf");
byte [] pdfEncoded = new br.com.oaks.ICPBravo.cms.content.Base64Content(pdf).getContent();
for (int a=0; a<pdfEncoded.length; a++)
	out.write(pdfEncoded[a]);
out.flush();
out.close();
%>