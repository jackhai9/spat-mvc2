package cn.hofan.spat.mvc2;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import cn.hofan.spat.mvc2.thread.BeatContextBean;
import cn.hofan.spat.mvc2.trace.CustomTraceInfo;
import cn.hofan.spat.mvc2.trace.RequestTraceInfo;
import cn.hofan.spat.mvc2.trace.ResponseTraceInfo;
import cn.hofan.spat.mvc2.trace.TraceInfo;

public class Trace {

	
	private static final String customTraceTitle = "<table width='100%'><tr colspan='2'  bgcolor='#333366'>CUSTOM TRACE INFO : </tr><tr><td>CONTENT</td><td>WASTETIME</td></tr>";
	private static final String sysTraceTitle = "<table width='100%'><tr colspan='2'  bgcolor='#333366'>SYS TRACE INFO : </tr><tr><td>TITLE</td><td>CONTENT</td></tr>";
	private static final String tableEnd = "</table>";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");   
	
	public static void trace(String content, BeatContext beat){
		
		TraceInfo traceInfo = beat.getTraceInfo();
		
		if(traceInfo == null)
			return;
		
		CustomTraceInfo cti = new CustomTraceInfo();
		cti.setContent(content);
		long curTime = System.currentTimeMillis();
		cti.setWasteTime(curTime - traceInfo.getLastTraceTime());
		traceInfo.setLastTraceTime(curTime);
		traceInfo.getCustomTraceInfos().add(cti);
	}
	
	public static void init(BeatContextBean beat){
		String trace = beat.getRequest().getParameter("trace"+sdf.format(new Date()));

		if(trace == null || !trace.equals("true"))
			return;
		
		TraceInfo traceInfo = new TraceInfo();
    	traceInfo.setArriveTime(System.currentTimeMillis());
    	traceInfo.setLastTraceTime(System.currentTimeMillis());
    	traceInfo.setCustomTraceInfos(new ArrayList<CustomTraceInfo>());
    	traceInfo.setReqInfo(new RequestTraceInfo());
    	traceInfo.setResInfo(new ResponseTraceInfo());
    	beat.setTraceInfo(traceInfo);
    	
	}
	public static void wrapper(BeatContext beat) throws IOException{

		if(beat.getTraceInfo() == null)
			return ;
		
		StringBuilder sb = new StringBuilder();
		getCustomInfo(sb, beat);		
		getSysInfo(sb, beat);
		beat.getResponse().getWriter().write(sb.toString());
	}
	
	private static void getCustomInfo(StringBuilder sb, BeatContext beat){
		
		sb.append(customTraceTitle);
		
		for(CustomTraceInfo cti : beat.getTraceInfo().getCustomTraceInfos()){
			
			sb.append("<tr>");
			sb.append("<td>" + cti.getContent() + "</td>");
			sb.append("<td>" + cti.getWasteTime() + "</td>");
			sb.append("</tr>");
			
		}
		sb.append(tableEnd);
		
	}
	
	private static void getSysInfo(StringBuilder sb, BeatContext beat){
		sb.append(sysTraceTitle);
		sb.append(tableEnd);
	}
}
