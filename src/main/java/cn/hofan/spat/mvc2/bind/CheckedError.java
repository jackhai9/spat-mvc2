package cn.hofan.spat.mvc2.bind;


/**
 * 用于处理校验和数据绑定错误
 * 
 * @author renjun (jun.ren@gmail.com)
 *
 */
public class CheckedError {
	
	/**
	 * 错误来源：绑定，校验
	 */
	private ErrorType errorType;
	
	/**
	 * 绑定对象的名称
	 */
	private String targetName;
	
	/**
	 * 错误消息
	 */
	private String message;
	
	/**
	 * 错误值
	 */
	//private Object invalidValue;
	
	public CheckedError(ErrorType errorType, String targetName, String message) {
		super();
		this.errorType = errorType;
		this.targetName = targetName;
		this.message = message;
	}
	
	/**
	 * @return the targetName
	 */
	public String getTargetName() {
		return targetName;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return the errorType
	 */
	public ErrorType getErrorType() {
		return errorType;
	}
	/**
	 * @return the invalidValue
	 */
//	public Object getInvalidValue() {
//		return invalidValue;
//	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ValidateError [targetName=" + targetName + ", message="
				+ message + "]";
	}
	
	public enum ErrorType{
		BIND,
		VALIDATE
	}
}
