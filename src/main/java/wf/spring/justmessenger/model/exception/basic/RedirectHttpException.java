package wf.spring.justmessenger.model.exception.basic;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RedirectHttpException extends HttpException {

    private static final HttpStatus STATUS = HttpStatus.MULTIPLE_CHOICES;

    public RedirectHttpException() {
    }

    public RedirectHttpException(String message) {
        super(message);
    }

    public RedirectHttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedirectHttpException(Throwable cause) {
        super(cause);
    }

    public RedirectHttpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return STATUS;
    }
}
