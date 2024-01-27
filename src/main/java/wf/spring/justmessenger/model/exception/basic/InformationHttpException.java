package wf.spring.justmessenger.model.exception.basic;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InformationHttpException extends HttpException {

    private static final HttpStatus STATUS = HttpStatus.CONTINUE;

    public InformationHttpException() {

    }

    public InformationHttpException(String message) {
        super(message);
    }

    public InformationHttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public InformationHttpException(Throwable cause) {
        super(cause);
    }

    public InformationHttpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return STATUS;
    }
}
