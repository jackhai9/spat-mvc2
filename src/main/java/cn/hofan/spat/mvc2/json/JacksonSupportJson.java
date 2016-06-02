package cn.hofan.spat.mvc2.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.hofan.spat.mvc2.utils.StringUtil;


public class JacksonSupportJson implements Json {
	private static Log log = LogFactory.getLog(JacksonSupportJson.class);
	  private ObjectMapper objectMapper;

	  public JacksonSupportJson(JsonSerialize.Inclusion inclusion)
	  {
	    this.objectMapper = new ObjectMapper();

	    this.objectMapper.getSerializationConfig().setSerializationInclusion(inclusion);

	    this.objectMapper.getDeserializationConfig().set(
	      DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	  }

	  public static Json buildNormalBinder()
	  {
	    return new JacksonSupportJson(JsonSerialize.Inclusion.ALWAYS);
	  }

	  public static Json buildNonNullBinder()
	  {
	    return new JacksonSupportJson(JsonSerialize.Inclusion.NON_NULL);
	  }

	  public static Json buildNonDefaultBinder()
	  {
	    return new JacksonSupportJson(JsonSerialize.Inclusion.NON_DEFAULT);
	  }

	  public <T> T fromJson(String jsonString, Class<T> clazz)
	  {
	    if (!(StringUtil.hasLengthAfterTrimWhiteSpace(jsonString)))
	      return null;

	    try
	    {
	      return this.objectMapper.readValue(jsonString, clazz);
	    } catch (IOException e) {
	      log.warn("parse json string error:" + jsonString, e); }
	    return null;
	  }

	  public String toJson(Object object)
	  {
	    try
	    {
	      return this.objectMapper.writeValueAsString(object);
	    } catch (IOException e) {
	      log.warn("write to json string error:" + object, e); }
	    return null;
	  }

	  public void setDateFormat(String pattern)
	  {
	    if (StringUtil.hasLengthAfterTrimWhiteSpace(pattern)) {
	      DateFormat df = new SimpleDateFormat(pattern);
	      this.objectMapper.getSerializationConfig().setDateFormat(df);
	      this.objectMapper.getDeserializationConfig().setDateFormat(df);
	    }
	  }

	  public ObjectMapper getMapper()
	  {
	    return this.objectMapper;
	  }
}
