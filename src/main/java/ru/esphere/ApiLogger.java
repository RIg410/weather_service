package ru.esphere;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.System.currentTimeMillis;
import static ru.esphere.utils.Utils.currentUser;

@Aspect
@Component
public class ApiLogger implements ApplicationListener<AuthenticationSuccessEvent> {
    private static Logger log = LoggerFactory.getLogger(ApiLogger.class);

    @Around(value = "@annotation(requestMapping)", argNames = "joinPoint,requestMapping")
    public Object invoke(ProceedingJoinPoint joinPoint, RequestMapping requestMapping) throws Throwable {
        Object resp = null;
        Throwable err = null;
        long startTime = currentTimeMillis();
        try {
            resp = joinPoint.proceed();
            return resp;
        } catch (Throwable e) {
            err = e;
            throw e;
        } finally {
            final String req = Arrays.stream(joinPoint.getArgs())
                    .map(arg -> arg == null ? "null" : arg)
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            final Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

            log.info("[{}({}) -> {}]; User: [{}]; time: [{} ms]", method.getName(), req,
                    resp != null ? resp : err, currentUser().orElse(null), currentTimeMillis() - startTime);
        }
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        log.info("[{} login!]", event.getAuthentication().getName());
    }
}
