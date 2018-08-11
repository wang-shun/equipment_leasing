package com.yankuang.equipment.web.log;

import com.yankuang.equipment.log.model.SysLog;
import com.yankuang.equipment.log.service.SysLogService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author xhh
 * @date 2018/8/9 13:41
 */

@Aspect
@Component
public class WebLogAspect {

    private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @RpcConsumer
    SysLogService sysLogService;

    @Pointcut("execution(public * com.yankuang.*..*.*(..))")
    public void webLog(){}

    @Pointcut("execution(public * com.yankuang.*..*.*(..)) && !execution(* com.yankuang.*..*.add*(..)) && !execution(* com.yankuang.*..*.update*(..)) " +
            "&& !execution(* com.yankuang.*..*.delete*(..)) && !execution(* com.yankuang.*..*.find*(..))")
    public void sysLog(){}

    /**
     * 添加业务逻辑方法切入点
     */
    @Pointcut("execution(* com.yankuang.*..*.add*(..)) ")
    public void insertCell() {
    }

    /**
     * 修改业务逻辑方法切入点
     */
    @Pointcut("execution(* com.yankuang.*..*.update*(..)) ")
    public void updateCell() {
    }

    /**
     * 删除业务逻辑方法切入点
     */
    @Pointcut("execution(* com.yankuang.*..*.delete*(..)) ")
    public void deleteCell() {
    }

    /**
     * 查询业务逻辑方法切入点
     */
    @Pointcut("execution(* com.yankuang.*..*.find*(..)) ")
    public void findCell() {
    }

    /**
     * 拦截异常处理
     */
    @Pointcut("execution(* com.yankuang.*..*.*(..))") //切点
    public void webExceptionLog(){}


    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("访问地址 : " + request.getRequestURL().toString());// 访问地址
        logger.info("请求方式 : " + request.getMethod());// 请求方式
        logger.info("访问IP : " + InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().lastIndexOf("/")+1));
        logger.info("执行了"+joinPoint.getTarget().getClass().getName()+"类的"+joinPoint.getSignature().getName()+"方法");// 获取哪个类哪个方法
        logger.info("传入参数 : " + Arrays.toString(joinPoint.getArgs()));//获取传入目标方法的参数对象

    }

    /**
     * 除增删改查（add,delete,update,find）之外的操作日志(后置通知)
     *
     * @param joinPoint
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "sysLog()")
    public void sysLog(JoinPoint joinPoint, Object object) throws Throwable {
        // 判断参数
        if (joinPoint.getArgs() == null) {// 没有参数
            return;
        }
        SysLog syslog=new SysLog();
        syslog.setContent(opContent(joinPoint));
        syslog.setCreate_date(DateUtils());
        syslog.setIp_address(InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().lastIndexOf("/")+1));
        syslog.setReturned_content("返回内容为：" + object);
        sysLogService.add(syslog);
    }

    /**
     * 添加操作日志(后置通知)
     *
     * @param joinPoint
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "insertCell()")
    public void insertLog(JoinPoint joinPoint, Object object) throws Throwable {
        // 判断参数
        if (joinPoint.getArgs() == null) {// 没有参数
            return;
        }
        SysLog syslog=new SysLog();
        syslog.setContent(opContent(joinPoint));
        syslog.setCreate_date(DateUtils());
        syslog.setOperation("添加");
        syslog.setIp_address(InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().lastIndexOf("/")+1));
        syslog.setReturned_content("返回内容为：" + object);
        sysLogService.add(syslog);
    }

    /**
     * 管理员删除操作日志(后置通知)
     *
     * @param joinPoint
     * @param object
     * @throws Throwable
     */
    @AfterReturning(value = "deleteCell()",  returning = "object")
    public void deleteLog(JoinPoint joinPoint, Object object) throws Throwable {
        // 判断参数
        if (joinPoint.getArgs() == null) {// 没有参数
            return;
        }
        // 创建日志对象
        SysLog syslog=new SysLog();
        syslog.setContent(opContent(joinPoint));
        syslog.setCreate_date(DateUtils());
        syslog.setOperation("删除");
        syslog.setIp_address(InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().lastIndexOf("/")+1));
        syslog.setReturned_content("返回内容为：" + object);
        sysLogService.add(syslog);
    }

    /**
     * 管理员修改操作日志(后置通知)
     *
     * @param joinPoint
     * @param object
     * @throws Throwable
     */
    @AfterReturning(value = "updateCell()",  returning = "object")
    public void updateLog(JoinPoint joinPoint, Object object) throws Throwable {
        // 判断参数
        if (joinPoint.getArgs() == null) {// 没有参数
            return;
        }
        // 创建日志对象
        SysLog syslog=new SysLog();
        syslog.setContent(opContent(joinPoint));
        syslog.setCreate_date(DateUtils());
        syslog.setOperation("修改");
        syslog.setIp_address(InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().lastIndexOf("/")+1));
        syslog.setReturned_content("返回内容为：" + object);
        sysLogService.add(syslog);
    }


    /**
     * 管理员查找操作日志(后置通知)
     *
     * @param joinPoint
     * @param object
     * @throws Throwable
     */
    @AfterReturning(value = "findCell()",  returning = "object")
    public void findLog(JoinPoint joinPoint, Object object) throws Throwable {
        // 判断参数
        if (joinPoint.getArgs() == null) {// 没有参数
            return;
        }
        // 创建日志对象
        SysLog syslog=new SysLog();
        syslog.setContent(opContent(joinPoint));
        syslog.setCreate_date(DateUtils());
        syslog.setOperation("查询");
        syslog.setIp_address(InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().lastIndexOf("/")+1));
        syslog.setReturned_content("返回内容为：" + object);
        sysLogService.add(syslog);
    }

    /**
     * 异常通知 用于拦截异常日志
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "webExceptionLog()", throwing = "e")
    public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //获取请求ip
        String ip = request.getRemoteAddr();
        //获取用户请求方法的参数并序列化为JSON格式字符串
        try {

            // 创建日志对象
            SysLog syslog=new SysLog();
            syslog.setContent(opContent(joinPoint));
            syslog.setCreate_date(DateUtils());
            syslog.setIp_address(InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().lastIndexOf("/")+1));
            syslog.setException_name(e.getClass().getName());
            syslog.setException_msg(e.getMessage());
            //保存数据库
            sysLogService.add(syslog);
        }  catch (Exception ex) {
            //记录本地异常日志
            e.printStackTrace();
        }
    }

    /**
     * 获取执行的操作的信息
     * @param joinPoint
     * @return
     */
    public String opContent(JoinPoint joinPoint){
        //获取类名
        String className=joinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        String str="执行了"+className+"类的"+methodName+"方法";
        return str;
    }

    public String DateUtils(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }
}
