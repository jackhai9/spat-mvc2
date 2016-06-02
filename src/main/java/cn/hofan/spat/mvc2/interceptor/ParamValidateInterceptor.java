package cn.hofan.spat.mvc2.interceptor;

import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotationUtils;

import cn.hofan.spat.mvc2.ActionResult;
import cn.hofan.spat.mvc2.BeatContext;
import cn.hofan.spat.mvc2.annotation.ParamWithoutValidate;

public class ParamValidateInterceptor implements ActionInterceptor {

	@Override
	public ActionResult preExecute(BeatContext beat) {
		
		Method method = beat.getAction().getActionMethod();
		
		ParamWithoutValidate nci = AnnotationUtils.findAnnotation(method, ParamWithoutValidate.class);
		
		if(nci !=null){
			String[] paramsWithoutValidate = nci.value();
			beat.setParamWithoutValidate(paramsWithoutValidate);
		}
		
		return null;
	}

}
