package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect //애노테이션 기반 프록시 적용시 필요
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    // @Around 값에 포인트컷 표현식을 넣는다(포인트컷), @Around 의 메서드는 어드바이스가 됨
    @Around("execution(* hello.proxy.app..*(..))") //포인트컷
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        //어드바이스 부분

        // 부가기능적용
        TraceStatus status = null;
        try{
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            // 로직 호출
            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
