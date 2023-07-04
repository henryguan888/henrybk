package com.henrybk.aspect;

import com.alibaba.fastjson.JSON;
import com.henrybk.annotation.Log;
import com.henrybk.constants.TypeCode;
import com.henrybk.enums.OperStatusEnum;
import com.henrybk.model.sys.SysOperLog;
import com.henrybk.service.AsyncOperLogService;
import com.henrybk.utils.AddressUtil;
import com.henrybk.utils.IpUtil;
import com.henrybk.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @description 操作日志AOP类
 * @author Henry
 * @since 2023-05-22
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Resource
    private AsyncOperLogService asyncOperLogService;

    /**
     * 方法执行之前执行
     * @param joinPoint
     * @param controllerLog
     */
    @Before("@annotation(controllerLog)")
    public void doBefore(JoinPoint joinPoint, Log controllerLog) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        request.setAttribute(TypeCode.SYS_OPER_BEGIN_TIME,new Date().getTime());
    }

    /**
     * 处理完请求后执行
     * @param joinPoint
     * @param controllerLog
     * @param jsonResult
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    private void handleLog(JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult) {
        try {
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();

            // *========数据库日志=========*//
            SysOperLog sysOperLog = new SysOperLog();
            sysOperLog.setStatus(OperStatusEnum.NORMAL);
            // 请求的地址
            sysOperLog.setOperIp(IpUtil.getIpAddr(request));
            sysOperLog.setOperUrl(request.getRequestURI());

            sysOperLog.setOperLocation(AddressUtil.getRealAddressByIP(IpUtil.getIpAddr(request)));

            String token = request.getHeader("token");
            sysOperLog.setOperName(JwtUtil.getUsername(token));

            if (null != e) {
                sysOperLog.setStatus(OperStatusEnum.ABNORMAL);
                sysOperLog.setErrorMsg(e.getMessage());
            }
            
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            sysOperLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            sysOperLog.setRequestMethod(request.getMethod());
            
            //设置执行时间
            Long oper_start_time = (Long) request.getAttribute(TypeCode.SYS_OPER_BEGIN_TIME);
            sysOperLog.setCostTime(new Date().getTime() - oper_start_time);

            //设置操作时间
            sysOperLog.setOperTime(new Date());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, sysOperLog, jsonResult);
            // 保存数据库
            asyncOperLogService.saveSysOperLog(sysOperLog);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==操作日志写入后置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
        }
    }
    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * @param log     日志
     * @param sysOperLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog sysOperLog, Object jsonResult) throws Exception {
        // 设置action动作
        sysOperLog.setBusinessType(log.businessType());
        // 设置标题
        sysOperLog.setTitle(log.title());
        // 设置操作人类别
        sysOperLog.setOperatorType(log.operatorType());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, sysOperLog);
        }
        // 是否需要保存response，参数和值
        if (log.isSaveResponseData() && !StringUtils.isEmpty(jsonResult)) {
            sysOperLog.setJsonResult(JSON.toJSONString(jsonResult));
        }
    }
    /**
     * 获取请求的参数，放到log中
     * @param sysOperLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLog sysOperLog) throws Exception {
        String requestMethod = sysOperLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            sysOperLog.setOperParam(params);
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (!StringUtils.isEmpty(o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params += jsonObj.toString() + " ";
                    } catch (Exception e) {
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
