/**
 * 
 */
package br.com.softplan.web.ocr.struts;

import java.lang.reflect.Method;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import br.com.softplan.ocr.common.OCRUtil;
import br.com.softplan.ocr.enumerator.OCRTypeExtension;
import br.com.softplan.ocr.loader.OCRConfigWebContext;
import br.com.softplan.ocr.loader.OCRConfigsProp;
import br.com.softplan.ocr.run.OCRSoftplan;
import br.com.softplan.ocr.run.tesseract.OCRTesseractEngine;

/**
 * @author jean.villete
 *
 */
public class UploadAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.resolveMethod(mapping, form, request, response);
	}
	
	protected ActionForward resolveMethod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String method = mapping.getPath();
		method = method.replace("/", "");
		
		Method targetMethod = getClass().getMethod(method,
				new Class[]{ ActionMapping.class, 
							UploadForm.class, 
							HttpServletRequest.class, 
							HttpServletResponse.class
				});
		
		return (ActionForward) targetMethod.invoke(this, mapping, (UploadForm) form, request, response);
	}
	
	public ActionForward runOCR(ActionMapping mapping, UploadForm uploadForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		FormFile formFile = uploadForm.getFileUpload();
		String fileName = formFile.getFileName();
		byte[] fileData = formFile.getFileData();
		
		OCRConfigsProp ocrConfigs = new OCRConfigWebContext();
		OCRSoftplan ocrSoftplan = new OCRSoftplan(ocrConfigs);
		ocrSoftplan.setOCREngine(OCRTesseractEngine.class);
		ocrSoftplan.setImageFile(fileName, fileData);
		ocrSoftplan.setDebugText(uploadForm.isDebugText());
		
		OCRTypeExtension targetExtension = OCRTypeExtension.PDF;
		
		fileData = ocrSoftplan.getBytes(targetExtension);
		
		response.setContentType(targetExtension.getMimeType());
		response.setContentLength(fileData.length);
		response.setHeader( "Content-Disposition", "attachment; filename=\"" + OCRUtil.removeExtension(fileName) + ".pdf " + "\"" );
		ServletOutputStream out = response.getOutputStream();
		out.write(fileData);
		
		return null;
	}
}