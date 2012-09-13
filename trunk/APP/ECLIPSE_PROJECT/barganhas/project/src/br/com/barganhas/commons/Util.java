package br.com.barganhas.commons;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

public class Util {

	public static boolean isStringOk(String str) {
		return str != null && !str.isEmpty();
	}
	
	public static boolean isCollectionOk(Collection<?> list){
		return ((list != null) && (!list.isEmpty()));
	}
	
	public static void validateParameterNull(Object... parameters) {
		for (Object param : parameters) {
			if (param == null) {
				throw new IllegalArgumentException("parameter " + parameters.getClass().getName() + " cann't be null");
			}
		}
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
		String bundleName = context.getApplication().getMessageBundle();
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
		if (context.getViewRoot() != null && context.getViewRoot().getLocale() != null) {
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
		
		if (height > maxHeight) {
			width = maxWidth / (height / width);
			height = maxHeight;
		}
		
		if (width > maxWidth) {
			height = maxHeight / (width / height);
			width = maxWidth;
		}
		
		Transform transform = ImagesServiceFactory.makeResize((int)width, (int)height);
		Image newImage = imagesService.applyTransform(transform, oldImage);
		return new Blob(newImage.getImageData());
	}
}
