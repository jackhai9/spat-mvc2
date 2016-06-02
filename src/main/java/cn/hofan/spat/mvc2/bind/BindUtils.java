package cn.hofan.spat.mvc2.bind;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;

import cn.hofan.spat.mvc2.BeatContext;

public class BindUtils {
//	
//	//TODO: 将来需要扩展，包括处理错误
//	public static <T> T doBind(Class<T> paramType, BeatContext beat) throws Exception{
//		T target = BeanUtils.instantiateClass(paramType);
//		ServletRequestDataBinder binder = new ServletRequestDataBinder(target);
//		binder.bind(beat.getRequest());
//		return target;
//	}
	
	/**
	 * 根据beat信息绑定一个目标对象
	 */
	public static ObjectBindResult bind(Object target, BeatContext beat){
		ServletRequestDataBinder binder = new ServletRequestDataBinder(target);
		binder.bind(beat.getRequest());
		BindingResult r = binder.getBindingResult();
		return new ObjectBindResult(r);	
	}
	
	/**
	 * 根据beat信息,和目标对象的类型，绑定一个目标对象
	 * @param targetType
	 * @param beat
	 * @return
	 * @throws Exception
	 */
	public static ObjectBindResult bind(Class<?> targetType, BeatContext beat) throws Exception{
		Object target = BeanUtils.instantiateClass(targetType);
		return bind(target, beat);
	}
	
	/**
	 * 校验一个目标对象
	 * @param <T>
	 * @param target
	 * @return
	 */
	public static <T> ObjectBindResult validate(T target){
		//javax.validation.ValidatorFactory.
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
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
}
