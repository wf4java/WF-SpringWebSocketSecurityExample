package wf.spring.justmessenger.model.exception.basic;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SuccessfulHttpException extends HttpException {

    private static final HttpStatus STATUS = HttpStatus.OK;

    public SuccessfulHttpException() {
    }

    public SuccessfulHttpException(String message) {
        super(message);
    }

    public SuccessfulHttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public SuccessfulHttpException(Throwable cause) {
        super(cause);
    }

    public SuccessfulHttpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return STATUS;
    }

}
