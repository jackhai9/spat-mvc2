package cn.hofan.spat.mvc2.support;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.AnnotationUtils;




import cn.hofan.spat.mvc2.annotation.SqlSafe;
import cn.hofan.spat.mvc2.route.ActionInfo;
import cn.hofan.spat.mvc2.route.RouteResult;
import cn.hofan.spat.mvc2.route.RouteResult.PreProcessor;

public class CheckSqlInjection implements PreProcessor {
	
	private Map<ActionInfo, Boolean> isSqlSafeCache = new HashMap<ActionInfo, Boolean>();

	/**
	 * 检查sql注入
	 */
	@Override
	public void PreProcess(RouteResult mtachUrlResult) {
		Boolean isSqlSafe = isSqlSafeCache.get(mtachUrlResult.action);
		if (isSqlSafe == null) isSqlSafe = false;
		
		if (isSqlSafe) return;
		
		// TODO: check sql injection, 需要张记豪
	}

	/**
	 * 决定是否检查Sql注入
	 */
	@Override
	public void Register(ActionInfo action) {
		
		Method method = action.getActionMethod();
		SqlSafe sqlSafe = AnnotationUtils.findAnnotation(action.getController().getClass(), SqlSafe.class);
		Boolean isSqlSafe = sqlSafe == null ? false :
			sqlSafe.value();

		sqlSafe = AnnotationUtils.findAnnotation(method, SqlSafe.class);
		isSqlSafe = sqlSafe == null ? isSqlSafe :
			sqlSafe.value();
		
		isSqlSafeCache.put(action, isSqlSafe);
	}

}
