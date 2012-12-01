package br.com.barganhas.commons;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;

import org.com.tatu.helper.GeneralsHelper;
import org.com.tatu.helper.HtmlHelper;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

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
	
	public static Blob transformBlobImage(Blob baseImage, int maxHeight, int maxWidth) {
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		Image oldImage = ImagesServiceFactory.makeImage(baseImage.getBytes());
		
		double height = oldImage.getHeight();
		double width = oldImage.getWidth();
		
		if (width > maxWidth) {
			height = (maxWidth * 100 / width) * height / 100;
			width = maxWidth;
		}
		
		if (height > maxHeight) {
			width = (maxHeight * 100 / height) * width / 100;
			height = maxHeight;
		}
		
		Transform transform = ImagesServiceFactory.makeResize((int)width, (int)height);
		Image newImage = imagesService.applyTransform(transform, oldImage);
		return new Blob(newImage.getImageData());
	}
	
	public static String getStringFromKey(Key keyBase) {
		return keyBase != null ? KeyFactory.keyToString(keyBase) : "";
	}
	
	public static Key getKeyFromString(String str) {
		return KeyFactory.stringToKey(str);
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
}
