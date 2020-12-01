package com.weichen.log.aspect;

import com.weichen.entity.YxAdmin;
import com.weichen.log.annotation.YingxLog;
import com.weichen.log.entity.YxLog;
import com.weichen.log.service.YxLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author weichenLi
 * @create 2020-11-30
 * @changeTime 15:56
 */
@Aspect
@Configuration
public class YxLogAspect {
    @Resource
    private HttpSession session;
    @Resource
    private YxLogService service;

    @Around("@annotation(com.weichen.log.annotation.YingxLog)")
    public Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("=======执行日志记录操作=======");
        YxLog yxLog=new YxLog();
        String id= UUID.randomUUID().toString().replace("-", "");
        yxLog.setId(id);//+++++++++++
        //操作的用户名 *
        HashMap<String,Object> adminMap= (HashMap<String, Object>) session.getAttribute("InfoMap");
        YxAdmin admin = (YxAdmin) adminMap.get("thisAdmin");
        yxLog.setUsername(admin.getUsername());//+++++++++++
        //操作时间 *
        String operationTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        yxLog.setOperationTime(operationTime);//+++++++++++
        //操作的表明和操作的业务类型 通过对方法上的注解属性值解析得到
        MethodSignature methodSignature= (MethodSignature) joinPoint.getSignature();
        //获取方法对象
        Method method=methodSignature.getMethod();
        //获取完整的方法名 *
        String methodName=methodSignature.toString();
        yxLog.setMethodName(methodName);//+++++++++++
        //获取方法上的注解然后解析
        YingxLog annotation=method.getAnnotation(YingxLog.class);
        //操作表名 *
        String tableName=annotation.tableName();
        //操作的业务类型 *
        String operationMethod=annotation.value();
        String operation=annotation.operation();
        yxLog.setTableName(tableName);//+++++++++++
        yxLog.setOperationMethod(operationMethod);//+++++++++++
        System.out.println(yxLog);
        System.out.println("=======执行日志记录操作==========");

        service.addOperation(yxLog,operation);

        Object[] args=joinPoint.getArgs();
        Object returnValue=joinPoint.proceed(args);
        return returnValue;
    }
}
