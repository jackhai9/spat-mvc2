package cn.hofan.spat.mvc2.invoke.converter;

/**
 * Convert String to Integer.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class StringConverter implements Converter<String> {

    public String convert(String s) {
        return s;
    }

}
