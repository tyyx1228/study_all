package com.tongy.study.flume.log4j;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * Created by maoxiangyi on 2016/6/20.
 */
public class LogInfoBuilder {
    private final static Logger logger = Logger.getLogger("msg");

    public static void main(String[] args) {
        Random random = new Random();
        List<String> list = logInfoList();
        while (true)
            logger.info(list.get(random.nextInt(list.size())));
    }

    private static List<String> logInfoList() {
        List list = new ArrayList<String>();
        list.add("aid:1||msg:error: Caused by: java.lang.NoClassDefFoundError: com/starit/gejie/dao/SysNameDao");
        list.add("java.sql.SQLException: You have an error in your SQL syntax;");
        list.add("error Unable to connect to any of the specified MySQL hosts.");
        list.add("error:Servlet.service() for servlet action threw exception java.lang.NullPointerException");
        list.add("error:Exception in thread main java.lang.ArrayIndexOutOfBoundsException: 2");
        list.add("error:NoSuchMethodError: com/starit/.");
        list.add("error:java.lang.NoClassDefFoundError: org/coffeesweet/test01/Test01");
        list.add("error:java.lang.NoClassDefFoundError: org/coffeesweet/test01/Test01");
        list.add("error:Java.lang.IllegalStateException");
        list.add("error:Java.lang.IllegalMonitorStateException");
        list.add("error:Java.lang.NegativeArraySizeException");
        list.add("error:java.sql.SQLException: You have an error in your SQL syntax;");
        list.add("error:Java.lang.TypeNotPresentException ");
        list.add("error:Java.lang.UnsupprotedOperationException ");
        list.add("error Java.lang.IndexOutOfBoundsException");
        list.add("error Java.lang.ClassNotFoundException");
        list.add("error java.lang.ExceptionInInitializerError ");
        list.add("error:java.lang.IncompatibleClassChangeError ");
        list.add("error:java.lang.LinkageError ");
        list.add("error:java.lang.OutOfMemoryError ");
        list.add("error java.lang.StackOverflowError");
        list.add("error: java.lang.UnsupportedClassVersionError");
        list.add("error java.lang.ClassCastException");
        list.add("error: java.lang.CloneNotSupportedException");
        list.add("error: java.lang.EnumConstantNotPresentException ");
        list.add("error java.lang.IllegalMonitorStateException ");
        list.add("error java.lang.IllegalStateException ");
        list.add("error java.lang.IndexOutOfBoundsException ");
        list.add("error java.lang.NumberFormatException ");
        list.add("error java.lang.RuntimeException ");
        list.add("error java.lang.TypeNotPresentException ");
        list.add("error MetaSpout.java:9: variable i might not have been initialized");
        list.add("error MyEvaluator.java:1: class Test1 is public, should be declared in a file named Test1.java ");
        list.add("error Main.java:5: cannot find symbol ");
        list.add("error NoClassDefFoundError: asa wrong name: ASA ");
        list.add("error Test1.java:54: 'void' type not allowed here");
        list.add("error Test5.java:8: missing return statement");
        list.add("error:Next.java:66: cannot find symbol ");
        list.add("error symbol  : method createTempFile(java.lang.String,java.lang.String,java.lang.String) ");
        list.add("error invalid method declaration; return type required");
        list.add("error array required, but java.lang.String found");
        list.add("error Exception in thread main java.lang.NumberFormatException: null 20. .");
        list.add("error non-static method cannot be referenced from a static context");
        list.add("error Main.java:5: non-static method fun1() cannot be referenced from a static context");
        list.add("error continue outside of  loop");
        list.add("error MyAbstract.java:6: missing method body, or declare abstract");
        list.add("error Main.java:6: Myabstract is abstract; cannot be instantiated");
        list.add("error MyInterface.java:2: interface methods cannot have body ");
        list.add("error Myabstract is abstract; cannot be instantiated");
        list.add("error asa.java:3: modifier static not allowed here");
        list.add("error possible loss of precision  found: long required:byte  var=varlong");
        list.add("error  java.lang.NegativeArraySizeException ");
        list.add("error java.lang.ArithmeticException:  by zero");
        list.add("error java.lang.ArithmeticException");
        list.add("error java.lang.ArrayIndexOutOfBoundsException");
        list.add("error java.lang.ClassNotFoundException");
        list.add("error java.lang.IllegalArgumentException");
        list.add("error fatal error C1010: unexpected end of file while looking for precompiled header directive");
        list.add("error fatal error C1083: Cannot open include file: R…….h: No such file or directory");
        list.add("error C2011:C……clas type redefinition");
        list.add("error C2018: unknown character 0xa3");
        list.add("error C2057: expected constant expression");
        list.add("error C2065: IDD_MYDIALOG : undeclared identifier IDD_MYDIALOG");
        list.add("error C2082: redefinition of formal parameter bReset");
        list.add("error C2143: syntax error: missing : before  ");
        list.add("error C2146: syntax error : missing ';' before identifier dc");
        list.add("error C2196: case value '69' already used");
        list.add("error C2509: 'OnTimer' : member function not declared in 'CHelloView'");
        list.add("error C2555: 'B::f1': overriding virtual function differs from 'A::f1' only by return type or calling convention");
        list.add("error C2511: 'reset': overloaded member function 'void (int)' not found in 'B'");
        list.add("error C2660: 'SetTimer' : function does not take 2 parameters");
        list.add("error warning C4035: 'f……': no return value");
        list.add("error warning C4553: '= =' : operator has no effect; did you intend '='");
        list.add("error C4716: 'CMyApp::InitInstance' : must return a value");
        list.add("error LINK : fatal error LNK1168: cannot open Debug/P1.exe for writing");
        list.add("error LNK2001: unresolved external symbol public: virtual _ _thiscall C (void)");
        list.add("error java.lang.IllegalArgumentException: Path index.jsp does not start with");
        list.add("error org.apache.struts.action.ActionServlet.process(ActionServlet.java:148");
        list.add("error org.apache.jasper.JasperException: Exception in JSP");
        list.add("error The server encountered an internal error () that prevented it from fulfilling this request");
        list.add("error org.apache.jasper.servlet.JspServletWrapper.handleJspException(JspServletWrapper.java:467");
        list.add("error javax.servlet.http.HttpServlet.service(HttpServlet.java:803)");
        list.add("error javax.servlet.jsp.JspException: Cannot find message resources under key org.apache.struts.action.MESSAGE");
        list.add("error Stacktrace:  org.apache.jasper.servlet.JspServletWrapper.handleJspException(JspServletWrapper.java:467)");
        list.add("error javax.servlet.ServletException: Cannot find bean org.apache.struts.taglib.html.BEAN in any scope");
        list.add("error no data found");
        list.add("error exception in thread main org.hibernate.MappingException: Unknown entity:.");
        list.add("error using namespace std;");
        list.add("error C2065: 'cout' : undeclared identifier");
        list.add("error main already defined in aaa.obj");
        list.add("error syntax error : missing ';' before '}'");
        list.add("error cout : undeclared identifier");
        list.add("error weblogic.servlet.internal.WebAppServletContext$ServletInvocationAction.run(WebAp ");
        list.add("error Caused by: java.lang.reflect.InvocationTargetException");
        list.add("error Caused by: java.lang.NoClassDefFoundError: com/starit/gejie/dao/SysNameDao");
        list.add("error at com.starit.gejie.Util.Trans.BL_getSysNamesByType(Trans.java:220)");
        return list;
    }
}
