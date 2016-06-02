package cn.hofan.spat.mvc2.invoke.converter;

/**
 * Convert String to Byte.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class ByteConverter implements Converter<Byte> {

    public Byte convert(String s) {
        return Byte.parseByte(s);
    }

}
