package com.study.eg.aop;

import com.study.eg.aop.service.HelloWorldService;
import com.study.eg.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 *AOP核心概念
 * 1.通知(Advice)：在切面的某个特定的连接点上执行的动作，即当程序到达一个执行点后会执行相对应的一段代码。也称为增强处理。
 *通知共有如下5种类型：
 *A 前置通知(Before advice)：在某连接点(JoinPoint)之前执行的通知。xml中在<aop:aspect>里面使用<aop:before>
 *元素进行声明
 *
 *B 后置通知(After advice) ：当某连接点退出的时候执行的通知(不论是正常返回还是异常退出)。xml中在<aop:aspect>里
 *面使用<aop:after>元素进行声明
 *
 *C 返回通知(After return advice) ：在某连接点正常执行完成后，可以取到该连接点的返回值的通知。xml中在
 *<aop:aspect>里面使用<after-returning>元素进行声明
 *
 *D 环绕通知(Around advice)：包围一个连接点的通知，可以在调用方法前后完成自定义行为。xml中在<aop:aspect>里面使
 *用<aop:around>元素进行声明
 *
 *E 抛出异常后通知(After throwing advice)：在方法抛出异常退出时执行的通知。xml中在<aop:aspect>里面使用
 *<aop:after-throwing>元素进行声明
 *
 *通知执行顺序：前置通知→环绕通知连接点之前→连接点执行→环绕通知连接点之后→返回通知→后置通知
 *→(如果发生异常)异常通知→返回通知→后置通知
 *
 *2.连接点(JoinPoint)：程序执行的某个特定位置，例如类初始化前，类初始化后，方法执行前，方法执行后等，Spring只支持方法
 *的连接点，即方法执行前，方法执行后，方法抛出异常时。
 *
 *3.切入点(Pointcut)：切入点是一个筛选连接点的过程。因为在你的工程中可能有很多连接点，你只是想让其中几个，在调用这几个
 *方法之前、之后或者抛出异常时干点什么，那么就用切入点来定义这几个方法，让切点来筛选连接点，选中那几个你想要的方法。
 *
 *4.切面(Aspect)：切面通常是指一个类，是通知和切入点的结合。到这里会发现连接点就是为了让你好理解切点产生的。通俗来说，切
 *面的配置可以理解为:什么时候在什么地方做什么事。切入点说明了在哪里干(指定到方法)，通知说明了什么时候干什么(什么时候通过
 *before,after,around等指定)。
 *
 *5.引入(Introduction)：允许一个切面声明一个通知对象实现指定接口，并且提供了一个接口实现类来代表这些对象。引入的定义使
 *用<aop:aspect>中的<aop:declare-parents>元素。(例子:通过JMX输出统计信息)
 *
 *6.目标对象(Target Object)：包含连接点的对象，也被称作被通知或被代理对象。
 *
 *7.AOP代理(AOP Proxy)：AOP框架创建的对象，代理就是目标对象的加强。在Spring AOP中有两种代理方式，JDK动态代理和
 *CGLIB代理。默认情况下，TargetObject实现了接口时，则采用JDK动态代理，强制使用CGLIB代理需要将 <aop:config>的
 *proxy-target-class属性设为true。
 *
 *8.织入(Weaving)：把切面应用到目标对象来创建新的代理对象的过程，织入一般发生在如下几个时机:
 *(1)编译时：当一个类文件被编译时进行织入，这需要特殊的编译器才可以做的到，例如AspectJ的织入编译器
 *(2)类加载时：使用特殊的ClassLoader在目标类被加载到程序之前增强类的字节代码
 *(3)运行时：切面在运行的某个时刻被织入,SpringAOP就是以这种方式织入切面的，原理应该是使用了JDK的动态代理技术
 *
 * @author relax tongyu
 * @create 2018-02-09 14:46
 **/
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class AopSampleStarter extends BaseUtils implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AopSampleStarter.class, args);
    }

    @Autowired
    private HelloWorldService hellowWorldService;

    @Override
    public void run(String... args) throws Exception {
         hellowWorldService.getHelloMessage("Test param");
    }
}
