package cn.hofan.spat.mvc2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import cn.hofan.spat.mvc2.utils.Converter;

public class MVCHttpServletRequestWrapper extends HttpServletRequestWrapper{

	private final BeatContext beat;
	
	public MVCHttpServletRequestWrapper(HttpServletRequest request, BeatContext beat) {
		
		super(request);
		this.beat = beat;
	}
	
	public String getOriginalParameter(String name){
		
		return super.getParameter(name);
	}
    public String[] getOriginalParameterValues(String name){
	 
		 return  super.getParameterValues(name);
	 }
    @Override
    public String getParameter(String name) {
    	
    	String source = super.getParameter(name);
    	
    	String[] pwv = beat.getParamWithoutValidate();
    	if(source == null)
    		return null;
    	
    	if(pwv == null)
    		return Converter.convert(source);
    	
    	for(String s : pwv){
			if(name.equals(s))
				return source;
    	}
    	
    	return Converter.convert(source);
    }

    @Override
    public String[] getParameterValues(String name){
		
    	String [] ss = super.getParameterValues(name);
    	
    	if(ss == null)
    		return null;
    	
    	String[] pwvs = beat.getParamWithoutValidate();
    	
    	for(int i = 0 ; i < ss.length ; i++){
    		String s = ss[i];
	    	if( s == null){
	    		continue;
	    	}
	    	
	    	if(pwvs == null){
	    		ss[i] = Converter.convert(s);
	    		continue;
	    	}
	    	
	    	boolean needConvert = true;
	    	
	    	for(String pwv : pwvs){	    		
				if(name.equals(pwv)){
					needConvert = false;
					break;
				}
	    	}
	    	if(needConvert)
	    		ss[i] = Converter.convert(s);
    	}
    	return ss;
    	
    }
}
