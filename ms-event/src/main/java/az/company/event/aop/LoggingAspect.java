package az.company.event.aop;

import az.company.event.util.LogUtil;
import az.company.event.util.WebUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.v;

/**
 * @author QasimovEY on 1/13/2021
 */

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {

    private final WebUtils webUtils;

    @Around("execution(* az.company.event.controller..*(..)))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String uri = webUtils.getRequestUri();

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        Map<String, Object> params =
                LogUtil.getParamsAsMap(methodSignature.getParameterNames(), proceedingJoinPoint.getArgs());

        log.info("[Request]  | Uri: {} [{}.{}] | Params: {}",
                v("uri", uri), className, methodName, v("params", params));

        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;

        log.info("[Response] | Uri: {} [{}.{}] | Elapsed time: {} ms | Result: {}",
                uri, className, methodName, v("elapsed_time", elapsedTime), result);

        return result;
    }

}
