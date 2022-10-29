package hello.trace;

public class TraceStatus {

    private TraceId traceId;
    private Long startTime;
    private String message;

    public TraceStatus(TraceId traceId, Long startTime, String message) {
        this.traceId = traceId;
        this.startTime = startTime;
        this.message = message;
    }

    public TraceId traceId() {
        return traceId;
    }

    public Long startTime() {
        return startTime;
    }

    public String message() {
        return message;
    }
}
