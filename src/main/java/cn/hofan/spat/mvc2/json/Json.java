package cn.hofan.spat.mvc2.json;

public interface Json {
	  public abstract <T> T fromJson(String paramString, Class<T> paramClass);

	  public abstract String toJson(Object paramObject);

	  public abstract void setDateFormat(String paramString);

	  public abstract Object getMapper();
}
