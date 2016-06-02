package cn.hofan.spat.mvc2.bind;

import java.util.ArrayList;

import java.util.List;



import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * 绑定结果
 * @author renjun
 *
 */
public class ObjectBindResult {

	private Object target;
	private List<CheckedError> errors = new ArrayList<CheckedError>();
	
	ObjectBindResult(BindingResult result){
		List<ObjectError> springErrors = result.getAllErrors();
		for(ObjectError springError : springErrors){
			// TODO: 错误值是多少？
			CheckedError error = new CheckedError(CheckedError.ErrorType.BIND,
					springError.getObjectName(),
					springError.getDefaultMessage()); 
			errors.add(error);
		}
		
		this.target = result.getTarget();
	}
	
	
	public ObjectBindResult(Object target, List<CheckedError> errors) {
		super();
		this.target = target;
		this.errors = errors;
	}

	/**
	 * 绑定目标
	 * @return
	 */
	public Object getTarget(){
		return this.target;
	}
	
	/**
	 * 绑定错误
	 * @return
	 */
	public List<CheckedError> getErrors(){
		return errors;
	}
	
	/**
	 * 得到错误的数量
	 * @return
	 */
	public int getErrorCount(){
		return errors.size();
	}
	
	/**
	 * 合并
	 * @param result
	 */
	public void merge(ObjectBindResult result){
		this.errors.addAll(result.getErrors());
	}
}
