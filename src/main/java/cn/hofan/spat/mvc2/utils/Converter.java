package cn.hofan.spat.mvc2.utils;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class Converter {
	private static Map<String, String> sqlMatches = new HashMap();
	  private static Map<Character, String> htmlEncodeMatches = new HashMap();
	  private static Map<String, Character> htmlDecodeMatches = new HashMap();
	  private static final Properties sqlInjectProperties = new Properties();
	  private static final Properties htmlEncodeProperties = new Properties();

	  public static String convert(String source)
	  {
	    for (Iterator localIterator = sqlMatches.keySet().iterator(); localIterator.hasNext(); ) { String key = (String)localIterator.next();
	      source = source.replaceAll("[" + ((String)sqlMatches.get(key)) + "]", key);
	    }

	    source = encodeHtml(source);

	    return source;
	  }

	  public static void initSqlInject(String configFile)
	  {
	    File file = new File(configFile);
	    Reader reader = null;
	    try
	    {
	      reader = new FileReader(file);
	      sqlInjectProperties.load(reader);

	      for (Enumeration e = sqlInjectProperties.propertyNames(); e.hasMoreElements(); )
	      {
	        String key = (String)e.nextElement();
	        String content = (String)sqlMatches.get(sqlInjectProperties.getProperty(key, ""));
	        if (content == null)
	          content = key;
	        else
	          content = content + "|" + key;

	        sqlMatches.put(sqlInjectProperties.getProperty(key, ""), content);
	      }
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();

	      if (reader == null) return;
	      try {
	        reader.close();
	      } catch (Exception e2) {
	        e2.printStackTrace();
	      }
	    }
	    finally
	    {
	      if (reader != null)
	        try {
	          reader.close();
	        } catch (Exception e2) {
	          e2.printStackTrace();
	        }
	    }
	  }

	  public static void initHtmlEncode(String configFile)
	  {
	    File file = new File(configFile);
	    Reader reader = null;
	    try
	    {
	      reader = new FileReader(file);
	      htmlEncodeProperties.load(reader);

	      for (Enumeration e = htmlEncodeProperties.propertyNames(); e.hasMoreElements(); )
	      {
	        String key = ((String)e.nextElement()).trim();
	        char c = key.charAt(0);

	        htmlEncodeMatches.put(Character.valueOf(c), htmlEncodeProperties.getProperty(key, ""));
	        htmlDecodeMatches.put(htmlEncodeProperties.getProperty(key, ""), Character.valueOf(c));
	      }
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();

	      if (reader == null) return;
	      try {
	        reader.close();
	      } catch (Exception e2) {
	        e2.printStackTrace();
	      }
	    }
	    finally
	    {
	      if (reader != null)
	        try {
	          reader.close();
	        } catch (Exception e2) {
	          e2.printStackTrace();
	        }
	    }
	  }

	  public static String encodeHtml(String str)
	  {
	    if (str == null)
	      return null;

	    StringWriter writer = new StringWriter((int)(str.length() * 1.5D));

	    for (int i = 0; i < str.length(); ++i) {
	      char c = str.charAt(i);
	      if (htmlEncodeMatches.get(Character.valueOf(c)) == null)
	        writer.write(c);
	      else
	        writer.write((String)htmlEncodeMatches.get(Character.valueOf(c)));
	    }
	    return writer.toString();
	  }

	  public static String decodeHtml(String str)
	    throws Exception
	  {
	    throw new Exception("no impl");
	  }
}
