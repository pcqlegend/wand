package com.pcq;

import com.alibaba.fastjson.JSONObject;
import com.pcq.annotation.WandMethod;
import com.pcq.utils.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WandStandAloneServlet
        extends HttpServlet
        implements ApplicationContextAware {
    private String secretKey = "";

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    Map<String, Object> beanMap = new HashMap();
    Map<String, WMethod> wandMethodMap = new HashMap();
    private static ApplicationContext applicationContext = null;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = resp.getWriter();
        if (StringUtils.isEmpty(secretKey)) {
            printWriter.println("未配置访问秘钥");
            return;
        }

        HttpSession session = req.getSession();

        String secretKeyStr = (String) req.getParameter("secretKey");
        //先从参数中获取
        //有的话继续访问，并放到session中
        //否则 查询session 如果都没有则没有权限
        if (StringUtils.isEmpty(secretKeyStr)) {
            secretKeyStr = (String) session.getAttribute("secretKey");
        }else{
            session.setAttribute("secretKey", secretKey);
            session.setMaxInactiveInterval(20 * 60);
        }
        if (!secretKey.equals(secretKeyStr)) {
            printWriter.println("无权限");
            return;
        }
        printWriter.println("<html>");
        printWriter.println("<head> ");
        printWriter.println("<h1>wand </h1><body>");
        String methodName;
        if (requestURI.startsWith("/wand/methodList")) {
            printWriter.println("<table><tr><th>服务</th><th>方法</th><th>方法描述</th></tr><p>");
            for (String wMethodName : this.wandMethodMap.keySet()) {
                String[] key = wMethodName.split("#");
                String className = key[0];
                methodName = key[1];
                printWriter.println("<tr><td>" + className + "</td>" + "<td>" + methodName + "</td>" + "<td>" +

                        ((WMethod) this.wandMethodMap.get(wMethodName)).getMethodDesc() + "</td>" + "<td><a href=\"/wand/methodInfo?className=" + className + "&methodName=" + methodName + "\">调用</a></td></tr>");
            }
            printWriter.println("</table>");
        } else if (requestURI.startsWith("/wand/methodInfo")) {
            String className = req.getParameter("className");
            methodName = req.getParameter("methodName");
            WMethod wMethod = (WMethod) this.wandMethodMap.get(className + "#" + methodName);
            printWriter.println("服务名：" + className + "<p>");
            printWriter.println("方法名：" + methodName + "<p>");
            printWriter.println("<form action=\"/wand/invoke\" method=\"POST\"><table>");
            printWriter.println("<tr><th>类型</th><th>参数名</th><th>参数值</th></tr>");
            printWriter.println("<input name=\"className\" hidden value=\"" + className + "\"></input>");
            printWriter.println("<input name=\"methodName\" hidden value=\"" + methodName + "\"></input>");
            int i = 0;
            for (WParameter parameter : wMethod.getParameters()) {
                String name = parameter.getName() == null ? "" : parameter.getName();
                printWriter.println("<tr><td>" + parameter
                        .getType() + "</td>" + "<td>" + name + "</td>" + "<td><input name=\"field-" + i + "\"></input></td>" + "</tr>");

                i++;
            }
            printWriter.println("</table>");
            printWriter.println("<input type=submit></input><input type=reset></input></form>");
        }
        printWriter.println("</body></head>");
        printWriter.println("</html>");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=utf-8");
        String requestURI = req.getRequestURI();
        HttpSession session = req.getSession();
        String secretKeyStr = (String) session.getAttribute("secretKey");
        PrintWriter printWriter = resp.getWriter();

        if (!secretKey.equals(secretKeyStr)) {
            printWriter.println("无权限");
            return;
        }
        if (requestURI.startsWith("/wand/invoke")) {

            String className = req.getParameter("className");
            String methodName = req.getParameter("methodName");
            Map<String, String[]> parameterMap = req.getParameterMap();
            Object bean = this.beanMap.get(className);
            WMethod wMethod = (WMethod) this.wandMethodMap.get(className + "#" + methodName);
            List<WParameter> wParameterList = wMethod.getParameters();

            int count = wParameterList.size();
            String[] paramArr = new String[count];
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                String key = (String) entry.getKey();
                String[] split = key.split("-");
                if (split.length > 1) {
                    int seq = Integer.parseInt(split[1]);
                    paramArr[seq] = ((String[]) entry.getValue())[0];
                }
            }
            Object[] parameters = new Object[count];
            for (int i = 0; i < count; i++) {
                WParameter wParameter = (WParameter) wParameterList.get(i);
                BuildParameterRes buildParameterRes = buildParameter(paramArr[i], wParameter.getType());
                if (!buildParameterRes.isSuccess()) {
                    printWriter.println("第" + (i + 1) + "参数非法");
                    return;
                }
                parameters[i] = buildParameterRes.getData();
            }
            Method method = wMethod.getMethod();
            try {
                Object res = method.invoke(bean, parameters);
                printWriter.println(JSONObject.toJSONString(res));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private BuildParameterRes buildParameter(String o, String type) {
        try {
            if (type.equals("java.lang.String")) {
                return new BuildParameterRes(true, o);
            }
            if (type.equals("int")) {
                if (StringUtils.isEmpty(o)) {
                    return new BuildParameterRes(true, Integer.valueOf(0));
                }
                int a = Integer.valueOf(o).intValue();
                return new BuildParameterRes(true, Integer.valueOf(a));
            }
            if (type.equals("long")) {
                if (StringUtils.isEmpty(o)) {
                    return new BuildParameterRes(true, Long.valueOf(0L));
                }
                long a = Long.valueOf(o).longValue();
                return new BuildParameterRes(true, Long.valueOf(a));
            }
            if (type.equals("float")) {
                if (StringUtils.isEmpty(o)) {
                    return new BuildParameterRes(true, Float.valueOf(0.0F));
                }
                float a = Float.valueOf(o).floatValue();
                return new BuildParameterRes(true, Float.valueOf(a));
            }
            if (type.equals("double")) {
                if (StringUtils.isEmpty(o)) {
                    return new BuildParameterRes(true, Float.valueOf(0.0F));
                }
                double a = Double.valueOf(o).doubleValue();
                return new BuildParameterRes(true, Double.valueOf(a));
            }
            if (type.equals("boolean")) {
                if (StringUtils.isEmpty(o)) {
                    return new BuildParameterRes(true, Boolean.valueOf(false));
                }
                if (o.equalsIgnoreCase("true")) {
                    return new BuildParameterRes(true, Boolean.valueOf(true));
                }
                return new BuildParameterRes(true, Boolean.valueOf(false));
            }
            if (StringUtils.isEmpty(o)) {
                return new BuildParameterRes(true, null);
            }
            if (type.equals("java.lang.Integer")) {
                return new BuildParameterRes(true, Integer.valueOf(o));
            }
            if (type.equals("java.lang.Long")) {
                return new BuildParameterRes(true, Long.valueOf(o));
            }
            if (type.equals("java.lang.Boolean")) {
                if (o.equalsIgnoreCase("true")) {
                    return new BuildParameterRes(true, Boolean.TRUE);
                }
                return new BuildParameterRes(true, Boolean.FALSE);
            }
            if (type.equals("java.lang.Float")) {
                return new BuildParameterRes(true, Float.valueOf(o));
            }
            if (type.equals("java.lang.Double")) {
                return new BuildParameterRes(true, Double.valueOf(o));
            }
        } catch (NumberFormatException e) {
            return BuildParameterRes.buildFailedRes(false, "参数类型错误");
        }
        return BuildParameterRes.buildFailedRes(false, "参数类型错误");
    }

    public void init() {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            boolean isPrototype = applicationContext.isPrototype(beanName);
            if (!isPrototype) {
                Object bean = applicationContext.getBean(beanName);
                if (bean == null) {
                    System.out.println("wandServlet init: " + beanName + "is null");
                } else {
                    Class clazz = bean.getClass();
                    if (AopUtils.isAopProxy(bean)) {
                        clazz = AopUtils.getTargetClass(bean);
                    }
                    Method[] methods = clazz.getMethods();
                    for (Method method : methods) {
                        WandMethod annotation = (WandMethod) method.getAnnotation(WandMethod.class);
                        if (annotation != null) {
                            String clazzName = clazz.getName();
                            if (null == this.beanMap.get(clazzName)) {
                                this.beanMap.put(clazzName, bean);
                            }
                            this.wandMethodMap.put(clazzName + "#" + method.getName(), builldWandMethod(method, clazzName, annotation));
                        }
                    }
                }
            }
        }
        this.secretKey = this.getInitParameter("secretKey");
    }

    private WMethod builldWandMethod(Method method, String className, WandMethod anotation) {
        WMethod wMethod = new WMethod();
        wMethod.setClassName(className);
        wMethod.setMethodName(method.getName());
        wMethod.setMethod(method);
        wMethod.setMethodDesc(anotation.desc());
        String params = anotation.params();

        List<WParameter> parameterList = new LinkedList();
        Class[] parameterTypes = method.getParameterTypes();
        String[] paramNames = params.split("&");
        int parameterSeq = 0;
        for (Class parameterClass : parameterTypes) {
            WParameter wParameter = new WParameter();
            wParameter.setType(parameterClass.getName());
            if (paramNames.length > parameterSeq) {
                wParameter.setName(paramNames[parameterSeq]);
            }
            parameterSeq++;
            parameterList.add(wParameter);
        }
        wMethod.setParameters(parameterList);
        return wMethod;

    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        applicationContext = applicationContext;
    }
}
