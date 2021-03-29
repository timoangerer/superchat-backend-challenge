package dev.timoangerer.messaging.templating;

public class MissingTemplateVariableException extends RuntimeException {

    public MissingTemplateVariableException() {}

    public MissingTemplateVariableException(String message) {
      super(message);
    }
  
    public MissingTemplateVariableException(String message, Throwable cause) {
      super(message, cause);
    }
  
    public MissingTemplateVariableException(Throwable cause) {
      super(cause);
    }
  
    public MissingTemplateVariableException(
        String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
    }
}
