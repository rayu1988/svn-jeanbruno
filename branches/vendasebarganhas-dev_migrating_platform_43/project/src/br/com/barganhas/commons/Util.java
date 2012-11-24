package br.com.barganhas.commons;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.com.tatu.helper.GeneralsHelper;
import org.com.tatu.helper.HtmlHelper;
import org.imgscalr.Scalr;

import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.entities.management.TransferObject;
import br.com.barganhas.business.exceptions.AppException;

public final class Util {

	public static boolean isStringOk(String str) {
		return GeneralsHelper.isStringOk(str);
	}
	
	public static boolean isCollectionOk(Collection<?> collection) {
		return GeneralsHelper.isCollectionOk(collection);
	}
	
	public static int sizeCollection(Collection<?> collection) {
		return collection != null ? collection.size() : 0;
	}
	
	public static boolean validateNickname(String nickname) {
		if (!GeneralsHelper.isStringOk(nickname)) {
			return false;
		}
		
		Pattern pattern = Pattern.compile("[a-z][a-z0-9]+");
		Matcher m = pattern.matcher(nickname);
		return m.matches();
	}
	
	public static String getNameAsJavaBean(Class<?> classe){
		String name = classe.getSimpleName();
		String firstLatter = name.substring(0, 1);
		String restOfTheName = name.substring(1, name.length());
		return firstLatter.toLowerCase() + restOfTheName;
	}
	
	public static ClassLoader getCurrentClassLoader(Object defaultObject) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = defaultObject.getClass().getClassLoader();
		}
		return loader;
	}
	
	public static String getMessageResourceString(String key, Object... params) {
		String text = null;
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = context != null ? context.getApplication().getMessageBundle() : "messages";
		Locale locale = getLocale(context);
		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale, getCurrentClassLoader(params));
		try {
			text = bundle.getString(key);
		} catch (Exception e) {
			text = key;
		}
		if (params != null) {
			MessageFormat mf = new MessageFormat(text, locale);
			text = mf.format(params, new StringBuffer(), null).toString();
		}
		return text;
	}
	
	public static Locale getLocale(FacesContext context) {
		if (context !=null && (context.getViewRoot() != null && context.getViewRoot().getLocale() != null)) {
			return context.getViewRoot().getLocale();
		} else {
			return new Locale("pt", "pt_BR");
		}
	}
	
	public static byte[] getImageByteArray(byte[] imageData, String formatName, int maxWidth, int maxHeight) throws IOException {
		if (formatName.contains("jpeg") || formatName.contains("jpg")) {
			formatName = "jpg";
		} else if (formatName.contains("png")) {
			formatName = "png";
		} else {
			throw new IllegalStateException("Unknow file type");
		}
		
		InputStream in = new ByteArrayInputStream(imageData);
		BufferedImage bufferedOldImage = ImageIO.read(in);
		BufferedImage bufferedNewImage = Scalr.resize(bufferedOldImage, maxWidth, maxHeight);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedNewImage, formatName, baos);
		baos.flush();
		
		byte[] data = baos.toByteArray();
		baos.close();
		
		return data;
	}
	
	public static TransferObject getProperlyTransferObject(TransferObject toBase){
		Class<?> clazz = null;
		try{
			clazz = toBase.getClass();
			while(clazz.getSuperclass() != TransferObject.class){
				  if(clazz == Object.class) {
					  throw new Exception("Invalid Object " + clazz.getName());
				  }
				  clazz = clazz.getSuperclass();
			}
			TransferObject properlyTransferObject = (TransferObject) clazz.newInstance();
			properlyTransferObject.setKey(toBase.getKey());
			return properlyTransferObject;
		}catch(Exception e) {
			throw new AppException(e);
		}
	}
	
	public static String truncateString(String strBase, int maxSize) {
		String suspensionPoints = "...";
		if (strBase.length() > (maxSize + suspensionPoints.length()) ) {
			return strBase.substring(0, maxSize).concat(suspensionPoints);
		}
		return strBase;
	}
	
	public static String textToHtml(String text) {
		return new HtmlHelper().toHtml(text);
	}
	
	public static String getFirstName(UserAccountTO user) {
		return user.getFullname().split(" ")[0];
	}
	
	public static <T extends TransferObject> List<T> getListAsSet(Set<T> set) {
		return new ArrayList<T>(set);
	}
}
