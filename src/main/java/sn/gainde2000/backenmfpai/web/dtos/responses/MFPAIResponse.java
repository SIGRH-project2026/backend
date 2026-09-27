package sn.gainde2000.backenmfpai.web.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import sn.gainde2000.backenmfpai.exceptions.MFPAIException;


import java.io.Serializable;
import java.util.Objects;

/**
 * @author Abdou Karim CISSOKHO
 * @created 04/11/2023-22:41
 * @project gestion-courriers
 */

@Data
@SuperBuilder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MFPAIResponse {

    private boolean success;
    private String message;
    private Object data;
    private String status;
    private Object errors;
    private String url;

    public static MFPAIResponse success(Object data) {
        return MFPAIResponse.builder().success(true).data(data).build();
    }

    public static MFPAIResponse error(MFPAIMessage message) {
        return MFPAIResponse.builder()
                .success(false)
                .status(message.getCode())
                .message(message.getMessage())
                .build();
    }

    public static MFPAIResponse error(MFPAIException mfpaiException) {
        return MFPAIResponse.builder()
                .success(false)
                .status(mfpaiException.getCode())
                .message(mfpaiException.getMessage())
                .build();
    }

    public static MFPAIResponse error(MFPAIMessage message, String s) {
        return MFPAIResponse.builder()
                .success(false)
                .status(message.getCode())
                .message(String.format(message.getMessage(), s))
                .build();
    }

    public MFPAIResponse message(String message) {
        this.message = message;
        return this;
    }

    public MFPAIResponse data(Object data) {
        if (Objects.isNull(this.data)) this.data = data;
        return this;
    }

    public MFPAIResponse errors(Object errors) {
        this.errors = errors;
        return this;
    }

    public MFPAIResponse url(String url) {
        this.url = url;
        return this;
    }

    @Getter
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Builder
    public static class PageMetadata implements Serializable {
        private static final long serialVersionUID = 7156526077883281623L;
        private final int size;
        private final long totalElements;
        private final int totalPages;
        private final int number;

        public PageMetadata(int size, long totalElements, int totalPages, int number) {
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.number = number;
        }
    }

}
