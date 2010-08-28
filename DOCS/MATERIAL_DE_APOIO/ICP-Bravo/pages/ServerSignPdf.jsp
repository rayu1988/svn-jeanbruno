<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="org.json.simple.JSONObject"%>
<%
	JSONObject obj=new JSONObject();
	try {
		java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
		br.com.oaks.ICPBravo.manager.ICPBravoManager manager = br.com.oaks.ICPBravo.manager.ICPBravoManager.getDefaultManager();
		br.com.oaks.ICPBravo.util.PDFSignedData signPDF = (br.com.oaks.ICPBravo.util.PDFSignedData) session.getAttribute("currentSign");
		// Neste exemplo, os objetos necessários para a assinatura serão armazenados em sessão, para que possamos quebrar em duas etapas. 
		if (signPDF == null) { // Criar uma nova assinatura
System.out.println("Nova Assinatura");
			String certsPEM=request.getParameter("cert");
			certsPEM = br.com.oaks.ICPBravo.util.ICPBravoUtil.readFromUrlAndBase64(certsPEM);
System.out.println("cert: "+certsPEM);
			if (certsPEM != null) {
				br.com.oaks.ICPBravo.certs.ICPBravoCertificate signer = br.com.oaks.ICPBravo.certs.ICPBravoCertificate.CreateFromPEMAndChain(certsPEM, manager, "signatario");
				signPDF = new br.com.oaks.ICPBravo.util.PDFSignedData(manager, resourceBundle.getString("home")+"/pages/Sample.pdf");
				session.setAttribute("currentSign", signPDF);
				br.com.oaks.ICPBravo.cms.CMSSignerInformation si = signPDF.addSigner(signer);
				session.setAttribute("currentSigner", si);
				byte [] toSign = si.generateSignedAttributes().getDEREncoded();
				String toSignB64 = new sun.misc.BASE64Encoder().encode(toSign);
System.out.println("toSign: "+toSignB64);
				obj.put("toSign",toSignB64);
			} else {
				obj.put("err","cert is null");
			}
		} else { // Concluir a assinatura
System.out.println("Continuação da Assinatura");
			br.com.oaks.ICPBravo.cms.CMSSignerInformation si = (br.com.oaks.ICPBravo.cms.CMSSignerInformation) session.getAttribute("currentSigner"); 
			String signPEM=request.getParameter("signature");
System.out.println("sign: "+signPEM);
			signPEM = br.com.oaks.ICPBravo.util.ICPBravoUtil.readFromUrlAndBase64(signPEM);
			si.setSign(new sun.misc.BASE64Decoder().decodeBuffer(signPEM));
			byte[] pdfEncoded = signPDF.getPdfEncoded();
			String pdfEncodedB64 = new sun.misc.BASE64Encoder().encode(pdfEncoded); 
System.out.println("signPDF: "+pdfEncodedB64);
			obj.put("signPDF", pdfEncodedB64);
			
			// Limpa a sessão para uma nova assinatura
			session.removeAttribute("currentSigner");
			session.removeAttribute("currentSign");
		}
	} catch (Exception e) {
		e.printStackTrace();
		obj.put("err",e.getMessage());
	}
	out.print(obj);
	out.flush();
%>
