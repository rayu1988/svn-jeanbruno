package org.com.tatu.helper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import org.apache.commons.beanutils.PropertyUtils;

public class ReflectionsHelper {

	public static void setNestedDefaultValue(Object object, String propertyPath, Object value, String divisor) throws Exception{
		String[] props = propertyPath.split(divisor);
		for (int j = 0; j < props.length; j++) {
			if (j < (props.length - 1)) {
				if (PropertyUtils.getProperty(object, props[j]) == null) {
					Class<?> clazz = PropertyUtils.getPropertyType(object, props[j]);
					PropertyUtils.setProperty(object, props[j], clazz.newInstance());
				} 
				object = PropertyUtils.getProperty(object, props[j]);
			} else {
				PropertyUtils.setProperty(object, props[j], value);
			}
		}
	}
	
	public static Class<?> getGenericClassType(Field f){
		return (Class) ((ParameterizedType)f.getGenericType()).getActualTypeArguments()[0];
	}
	
	public static void setAttValue(Object bean, String caminhoPropriedade, Object fieldValue){
		try {
			if (caminhoPropriedade.indexOf('.') == -1) {
				PropertyUtils.setNestedProperty(bean, caminhoPropriedade, fieldValue);
			} else {
				String first = caminhoPropriedade.substring(0, caminhoPropriedade.indexOf("."));
				Object valor = getAttValue(bean, first);
				if (valor == null) {
					Class type = getAttType(bean.getClass(), first);
					Object property = instanciarEPopularAtributo(type.newInstance(), caminhoPropriedade.substring(caminhoPropriedade.indexOf(".")+1, caminhoPropriedade.length()), fieldValue);
					PropertyUtils.setNestedProperty(bean, first, property);
				} else {
					Object property = instanciarEPopularAtributo(valor, caminhoPropriedade.substring(caminhoPropriedade.indexOf(".")+1, caminhoPropriedade.length()), fieldValue);
					PropertyUtils.setNestedProperty(bean, first, property);					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object getAttValue(Object bean, String fieldName){
		try{
			return PropertyUtils.getNestedProperty(bean, fieldName);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Class<?> getAttType(Class<?> classe, String propertyPath){
		try{
			String[] paths = propertyPath.split("\\.");
			Class<?> classeAtual = classe;
			for(String path : paths){
				Field f;
				try {
					f = classeAtual.getDeclaredField(path);
				} catch (NoSuchFieldException e) {
					f = classeAtual.getSuperclass().getDeclaredField(path);
				}
				classeAtual = f.getType();
			}
			return classeAtual;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object instanciarEPopularAtributo(Object obj, String caminhoPropriedade, Object fieldValue){
		try{
			if(caminhoPropriedade.indexOf('.') == -1){
				PropertyUtils.setNestedProperty(obj, caminhoPropriedade, fieldValue);
			}else{
				String first = caminhoPropriedade.substring(0, caminhoPropriedade.indexOf("."));
				Object valor = getAttValue(obj, first);
				if(valor == null){
					Class type = getAttType(obj.getClass(), first);
					Object property = instanciarEPopularAtributo(type.newInstance(), caminhoPropriedade.substring(caminhoPropriedade.indexOf(".")+1, caminhoPropriedade.length()), fieldValue);
					PropertyUtils.setNestedProperty(obj, first, property);
				}else{
					Object property = instanciarEPopularAtributo(valor, caminhoPropriedade.substring(caminhoPropriedade.indexOf(".")+1, caminhoPropriedade.length()), fieldValue);
					PropertyUtils.setNestedProperty(obj, first, property);					
				}
			}
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
