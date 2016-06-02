package cn.hofan.spat.mvc2.invoke;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.hofan.spat.mvc2.ActionResult;
import cn.hofan.spat.mvc2.BeatContext;
import cn.hofan.spat.mvc2.bind.BindAndValidate;
import cn.hofan.spat.mvc2.bind.BindUtils;
import cn.hofan.spat.mvc2.bind.ObjectBindResult;
import cn.hofan.spat.mvc2.invoke.converter.ConverterFactory;
import cn.hofan.spat.mvc2.route.ActionInfo;
import cn.hofan.spat.mvc2.route.RouteResult;

public class ActionInvoker {
	
	private static ConverterFactory converter = new ConverterFactory();
	
	final static Log log = LogFactory.getLog(ActionInvoker.class);
	
	/**
	 * 执行匹配结果
	 * @return
	 * @throws Exception
	 */
	public Object invoke(RouteResult match) throws Exception {
        try {
        	
        	ActionInfo action = match.action;
        	Map<String, String> urlParams = match.urlParams;
        	BeatContext beat = match.beat;
        	
        	// 处理拦截器 2010-12-07
        	ActionResult preResult = action.getInterceptorHandler().preExecute(beat);
        	if(preResult != null){
        		return preResult;
        	}
        	
        	// 处理参数
        	Class<?>[] paramTypes = action.getParamTypes();
    		Object[] param = new Object[paramTypes.length];
    		for(int i = 0; i < action.getParamNames().length; i++){
    			String paramName = action.getParamNames()[i];
    			Class<?> clazz = paramTypes[i];
    			
    			String v = urlParams.get(paramName);
    			
    			if (v != null) {
    				if(converter.canConvert(clazz)){
        				param[i] = converter.convert(clazz, v);
        				continue;
        			}
    			}
    			
    			if(converter.canConvert(clazz)) continue;
    			
    			// 绑定数据
    			ObjectBindResult br = BindUtils.bind(clazz, beat);
    			beat.getBindResults().add(br);
    			param[i] = br.getTarget();
    			
    			// 校验
    			beat.getBindResults().add(BindAndValidate.Singleton().validate(param[i]));    			
    		}
    		
    		// 计时
    		Long t1= System.currentTimeMillis();
    		
    		Object result = null;
    		
    		//用于捕获执行具体Action时出现的错误并交给ExceptionInterceptor
    		
			try {
				result = action.getActionMethod().invoke(action.getController(), param);
			} catch (Exception e) {
				log.error("Action Invoke", e);
				ActionResult exceptionResult = action.getInterceptorHandler().exceptionExecute(beat);
	    		if(exceptionResult != null){
	        		return exceptionResult;
	        	}
			}
    		Long t2=System.currentTimeMillis();
    		Long t = t2 - t1;
    		
    		// 时间大于，记日志
    		if (t >= 100){
    			log.info("time:"  + t + "ms, url:" + beat.getClient().getRelativeUrl());
    		}
    		
    		//处理后置拦截器
    		ActionResult afterResult = action.getInterceptorHandler().afterExecute(beat);
        	
    		if(afterResult != null){
        		return afterResult;
        	}
    		return result ;
        }
        catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            if (t!=null && t instanceof Exception)
                throw (Exception) t;
            throw e;
        }
	}
}
