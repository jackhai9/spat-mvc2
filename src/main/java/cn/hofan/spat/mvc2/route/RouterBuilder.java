package cn.hofan.spat.mvc2.route;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;

import cn.hofan.spat.mvc2.MvcController;

public class RouterBuilder {
	
	// private BeatRouter router = new BeatRouter();
	// private ApplicationContext context = null;

//	/**
//	 * 根据当前请求获得匹配结果
//	 * @param beat
//	 * @return
//	 */
//	public RouteResult match(BeatContext beat){
//		return router.get(beat);
//	}
//	
//	/**
//	 * @param context the context to set
//	 */
//	public void setApplicationContext(ApplicationContext context) {
//		this.context = context;
//		detectActionExecutions();
//	}
	
	public static BeatRouter build(ApplicationContext context){
		return detectActionExecutions(context);
		
	}

	
	private static BeatRouter detectActionExecutions(ApplicationContext context) {

		Set<ActionInfo> actions = new LinkedHashSet<ActionInfo>();
		
		String[] beanNames = context.getBeanNamesForType(MvcController.class);
		// Take any bean name that we can determine URLs for.
		
		
		for (String beanName : beanNames) {
			
			System.out.println("beanName : " + beanName);

			ControllerInfo ce = ControllerInfo.Factory(beanName, context);
			if (ce == null) continue;
			
			addActionExecution(actions, ce);
		}		
		return BuildMappingGroup(actions);
	}

	private static void addActionExecution(Set<ActionInfo> actions, ControllerInfo ce){
		actions.addAll(ce.actions);		
	}
	
	private static BeatRouter BuildMappingGroup(Set<ActionInfo> actions){
		BeatRouter router = new BeatRouter();
		
		for(ActionInfo action : actions)
			router.addMapping(action);

		return router;	
	}


}
