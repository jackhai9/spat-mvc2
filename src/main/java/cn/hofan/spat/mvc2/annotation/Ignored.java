package cn.hofan.spat.mvc2.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 将{@link Ignored}标注在控制器或者它方法、拦截器( {@link ControllerInterceptor}
 * )、控制器异常处理类( {@link ControllerErrorHandler}、上，表示这个类不要被MVC识别。
 * <p>
 * 对拦截器而言就是不加入对控制器的拦截；对控制器而言就是不暴露该控制器或某个具体的方法；对异常处理器而言就是"就像没有它一样"。
 * <p>
 * 但是如果有其他类继承被标注为Ignored的类时，这个被Ignored的类的annotation仍然可能影响子类，具体请参考：
 * {@link Class#getAnnotation(Class)}的实现
 * 
 */
@Target( { ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ignored {

}
