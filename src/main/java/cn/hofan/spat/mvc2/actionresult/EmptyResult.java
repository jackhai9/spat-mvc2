package cn.hofan.spat.mvc2.actionresult;

import cn.hofan.spat.mvc2.ActionResult;
import cn.hofan.spat.mvc2.BeatContext;

public class EmptyResult extends ActionResult {

	/**
	 * 表示一个不执行任何操作的结果，如不返回任何内容的控制器操作方法。
	 */
	@Override
	public void render(BeatContext beat) throws Exception {
	}

}
