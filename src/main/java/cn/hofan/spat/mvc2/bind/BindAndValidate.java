package cn.hofan.spat.mvc2.bind;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;

import cn.hofan.spat.mvc2.BeatContext;


/**
 * 绑定和校验
 * 
 * @author renjun (jun.ren@gmail.com)
 *
 */
public class BindAndValidate {
	

	
	@Autowired
	private Validator validator;


	/**
	 * 根据beat信息绑定一个目标对象
	 */
	public ObjectBindResult bind(Object target, BeatContext beat){
		ServletRequestDataBinder binder = new ServletRequestDataBinder(target);
		binder.bind(beat.getRequest());
		BindingResult r = binder.getBindingResult();
		return new ObjectBindResult(r);	
	}
	
	/**
	 * 根据beat信息,和目标对象的类型，生成和绑定一个目标对象
	 * @param targetType
	 * @param beat
	 * @return
	 * @throws Exception
	 */
	public ObjectBindResult bind(Class<?> targetType, BeatContext beat) throws Exception{
		Object target = BeanUtils.instantiateClass(targetType);
		return bind(target, beat);
	}
	
	/**
	 * 校验一个目标对象
	 * @param <T>
	 * @param target
	 * @return
	 */
	public <T> ObjectBindResult validate(T target){
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(target);
		List<CheckedError> errors = new ArrayList<CheckedError>();
		for(ConstraintViolation<T> constraintViolation : constraintViolations) {
			CheckedError error = new CheckedError(CheckedError.ErrorType.VALIDATE,
					constraintViolation.getPropertyPath().toString(), 
					constraintViolation.getMessage());
			errors.add(error);
		}	
		return new ObjectBindResult(target, errors);
	}
	
	
	
	private static BindAndValidate singleton = null;
	private static ApplicationContext context = null;
	
	
	/**
	 * @param context the context to set
	 */
	public static void setApplicationContext(ApplicationContext context) {
		BindAndValidate.context = context;
	}
	
	public static BindAndValidate Singleton(){
		
		if (context == null) System.out.println("context is null.");
		if (singleton == null)
			singleton = (BindAndValidate)context.getBean("bindAndValidate");
			
		return singleton;
	}
}
