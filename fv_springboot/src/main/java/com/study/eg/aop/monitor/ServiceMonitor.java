/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.study.eg.aop.monitor;

import com.study.eg.utils.BaseUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect //声明本类为切面
@Component // 声明本类为spring组件
public class ServiceMonitor extends BaseUtils {

    /**
     *
     *  Grammer for Point-cut expression:   "execution(* com..*Service.*(..))"
     *  从做左到右：
     *      *   匹配任何数量字符；
     *      ..  匹配任何数量字符的重复，如在类型模式中匹配任何数量子包；而在方法参数模式中匹配任何数量参数。
     *      +   匹配指定类型的子类型；仅能作为后缀放在类型模式后边。
     *
     *
     *
     *  切入点指示符用来指示切入点表达式目的，，在Spring AOP中目前只有执行方法这一个连接点，
     *  Spring AOP支持的AspectJ切入点指示符如下：
     *
     *  execution：用于匹配方法执行的连接点；
     *  within：用于匹配指定类型内的方法执行；
     *  this：用于匹配当前AOP代理对象类型的执行方法；注意是AOP代理对象的类型匹配，这样就可能包括引入接口也类型匹配；
     *  target：用于匹配当前目标对象类型的执行方法；注意是目标对象的类型匹配，这样就不包括引入接口也类型匹配；
     *  args：用于匹配当前执行的方法传入的参数为指定类型的执行方法；
     *
     *  @within：用于匹配所以持有指定注解类型内的方法；
     *
     *  @target：用于匹配当前目标对象类型的执行方法，其中目标对象持有指定的注解；
     *
     *  @args：用于匹配当前执行的方法传入的参数持有指定注解的执行；
     *
     *  @annotation：用于匹配当前执行方法持有指定注解的方法；
     *
     *  bean：Spring AOP扩展的，AspectJ没有对于指示符，用于匹配特定名称的Bean对象的执行方法；
     *
     *  reference pointcut：表示引用其他命名切入点，只有@ApectJ风格支持，Schema风格不支持。
     *
     *  备注： AspectJ切入点支持的切入点指示符还有：（Spring不一定有--未测）
     *          call、get、set、preinitialization、staticinitialization、
     *          initialization、handler、adviceexecution、withincode、
     *          cflow、cflowbelow、if、@this、@withincode；
     *
     *   参见： http://jinnianshilongnian.iteye.com/blog/1415606
     * @param joinPoint
     */



    /**
     *
     * @Author: tongyu
     * @Title: logServiceAccessed
     * @Time:2016年12月1日
     * @Description:  在方法正常结束后执行代码，放回通知是可以访问到方法的返回值
     *
     * @param joinPoint
     */
	@AfterReturning(value="execution(* com..*Service.*(..))", returning="result")
	public void logServiceAccessed(JoinPoint joinPoint, Object result) {
        log.info(AFTER_MSG_FORMAT, getFullMethodName(joinPoint), ArrayUtils.toString(result));
    }

	@Before("execution(* com..*Service.*(..))")
	public void logServiceAccessing(JoinPoint joinPoint){
		log.info(BEFORE_MSG_FORMAT, getFullMethodName(joinPoint), ArrayUtils.toString(joinPoint.getArgs()));
    }

    //@Around("execution(* com..*Service.*(..))")
    public Object logServiceAccessFailed(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        String fullMethodName = getFullMethodName(proceedingJoinPoint);
        try {
            //前置通知
            log.info(BEFORE_MSG_FORMAT, fullMethodName, Arrays.asList(proceedingJoinPoint.getArgs()));
            //执行目标方法
            result = proceedingJoinPoint.proceed();
            //后置通知
            log.info(AFTER_MSG_FORMAT, fullMethodName, result);

        } catch (Throwable e) {
            //异常通知
            log.error(THROWING_MSG_FORMAT, fullMethodName, e);
        }
        //后置通知
        log.info(AFTER_MSG_FORMAT, fullMethodName, result);

        return result;
	}

	private String getFullMethodName(JoinPoint joinPoint){
	    return joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature();
    }

	private static final String BEFORE_MSG_FORMAT = "BEFORE-ADVICE -> The method:{} begin with PARAM:{}";
	private static final String AFTER_MSG_FORMAT = "AFTER-ADVICE -> The method:{} end with RESULT:{}";
	private static final String THROWING_MSG_FORMAT = "THROWING-ADVICE -> The method:{} occurs Exception:";

}
