package sn.gainde2000.backenmfpai.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage;


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

    public static MFPAIResponse error(MFPAIMessage mfpaiMessage, String s) {
        return MFPAIResponse.builder()
                .success(false)
                .status(mfpaiMessage.getCode())
                .message(String.format(mfpaiMessage.getMessage(), s))
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
}
