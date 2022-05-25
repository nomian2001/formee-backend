package dtapcs.springframework.Formee.dtos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties("hibernateLazyInitializer")
public class DataResponse implements Serializable {
    private Integer code;
    private Object result;
    private String message;
    private String description;
    private List<String> errors;

    private DataResponse(Builder builder) {
        this.code = builder.code;
        this.result = builder.result;
        this.message = builder.message;
        this.description = builder.description;
        this.errors = builder.errors;
    }

    public static DataResponse.Builder ok() {
        return withCode(HttpStatus.OK.value());
    }

    public static DataResponse.Builder badRequest() {
        return withCode(HttpStatus.BAD_REQUEST.value());
    }

    public static DataResponse.Builder withCode(Integer code) {
        return new DataResponse.Builder(code);
    }

    public static class Builder {

        private final Integer code; //This is important, so we'll pass it to the constructor.
        private Object result;
        private String message;
        private String description;
        private List<String> errors;

        public Builder(Integer code) {
            this.code = code;
        }

        public Builder withResult(Object result) {
            this.result = result;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withErrors(List<String> errors) {
            this.errors = errors;
            return this;
        }

        public DataResponse build() {
            DataResponse account = new DataResponse(this);
            return account;
        }
    }
}
