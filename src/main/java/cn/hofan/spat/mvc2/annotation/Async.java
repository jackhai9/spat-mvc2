package cn.hofan.spat.mvc2.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>
 * 执行是否是异步执行
 * 如果没有表示，表示是同步执行
 * </p>
 * <p>
 * 线程池处理请求的方式
 * </p>
 * 在 Web 服务器上，维护一个用于服务 请求的线程池。当请求到达时，将调度池中的线程以处理该请求。
 * 如果对请求进行同步处理，则在处理请求时将阻塞处理请求的线程，并且该线程不能对另一个请求提供服务。
 * 这可能不是一个问题，因为线程池可以设置得足够大以容纳许多阻塞的线程。但是，线程池中的线程数目是有限制的。
 * 在同时处理多个长时间运行的请求的大型应用程序中，可能会阻塞所有可用的线程。这种情况称为“线程不足”。
 * <p/>
 * 当出现这种情况时，Web 服务器会将请求排队。
 * 如果请求队列已满，则 Web 服务器会拒绝请求并处于 HTTP 503 状态（服务器太忙）。
 * <p/>
 * 处理异步请求
 * 在可能出现线程不足的应用程序中，您可以配置通过异步方式处理操作。
 * 异步请求与同步请求所需的处理时间相同。
 * 例如，如果某个请求生成一个需要两秒钟来完成的网络调用，则该请求无论是同步执行还是异步执行都需要两秒钟。
 * 但是，在异步调用的过程中，服务器在等待第一个请求完成的过程中不会阻塞对其他请求的响应。
 * 因此，当有许多请求调用长时间运行的操作时，异步请求可以防止出现请求排队的情况。
 * 在调用异步操作时，将执行以下步骤：
 * 1. Web 服务器从线程池（辅助线程）获取一个线程并安排它处理传入请求。此辅助线程启动一个异步操作。
 * 2. 将此辅助线程返回到线程池以对另一个 Web 请求提供服务。
 * 3. 在异步操作完成时通知 ASP.NET。
 * 4. Web 服务器从线程池获取一个线程（可能是与启动异步操作的线程不同的线程）以处理请求的其余部分，包括呈现响应。
 * 
 * 选择同步操作方法或异步操作方法
 * 本节列出了有关何时使用同步操作方法或异步操作方法的准则。这只是一些准则；
 * 您必须逐个检查每个应用程序以确定异步操作方法是否能帮助提高性能。
 * 通常，在满足以下条件时使用同步管线：
 * 操作很简单或运行时间很短。
 * 简单性比效率更重要。
 * 此操作主要是 CPU 操作而不是包含大量的磁盘或网络开销的操作。对 CPU 绑定操作使用异步操作方法未提供任何好处并且还导致更多的开销。
 * 通常，在满足以下条件时使用异步管线：
 * 操作是网络绑定的或 I/O 绑定的而不是 CPU 绑定的。
 * 测试显示阻塞操作对于网站性能是一个瓶颈，并且通过对这些阻塞调用使用异步操作方法，。
 * 并行性比代码的简单性更重要。
 * 您希望提供一种可让用户取消长时间运行的请求的机制。
 * 
 * @author renjun
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Async {
	boolean value() default true;
}
