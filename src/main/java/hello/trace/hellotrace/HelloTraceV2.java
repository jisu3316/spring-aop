package hello.trace.hellotrace;

import hello.trace.TraceId;
import hello.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloTraceV2 {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    public TraceStatus begin(String message) {
        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.id(), addSpace(START_PREFIX, traceId.level()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    //V2에서 추가가
    public TraceStatus beginSync(TraceId beforeTraceId, String message) {
//        TraceId traceId = new TraceId();
        TraceId nextId = beforeTraceId.createNextId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", nextId.id(), addSpace(START_PREFIX, nextId.level()), message);
        return new TraceStatus(nextId, startTimeMs, message);
    }


    public void end(TraceStatus status) {
        complete(status, null);
    }

    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.startTime();
        TraceId traceId = status.traceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.id(), addSpace(COMPLETE_PREFIX, traceId.level()), status.message(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.id(), addSpace(EX_PREFIX, traceId.level()), status.message(), resultTimeMs, e.toString());
        }
    }

    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "| ");
        }
        return sb.toString();
    }
}
