package sn.gainde2000.backenmfpai.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage;
import sn.gainde2000.backenmfpai.web.dtos.responses.ValidationRspError;

import javax.naming.AuthenticationException;
import javax.naming.SizeLimitExceededException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 23/01/2024-14:29
 * @project backend_mfpai
 */

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //exception par défaut
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MFPAIResponse> exceptionHandler(Exception exception) {
        exception.printStackTrace();
        LOGGER.error("{}", exception.getMessage());
        return ResponseEntity.internalServerError().body(MFPAIResponse.error(MFPAIMessage.INTERNAL_SERVER_ERROR).errors(exception.getMessage()));
    }

    //pour les pubc exception
    @ExceptionHandler(MFPAIException.class)
    public ResponseEntity<MFPAIResponse> handleMFPAIException(MFPAIException exception) {
        // exception.printStackTrace();
        LOGGER.error("{}", exception.getMessage());
        HttpStatus status = exception.getStatus() != null ? exception.getStatus() : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(MFPAIResponse.error(exception).errors(exception.getMessage()));
    }

    //pour les contraintes de validations au niveau des méthodes
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<MFPAIResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        List<ValidationRspError> errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> ValidationRspError.builder().field(fieldError.getField()).message(fieldError.getDefaultMessage()).build())
                .collect(Collectors.toList());

        // LOGGER.error("{}", errors);

        return ResponseEntity.badRequest().body(MFPAIResponse.error(MFPAIMessage.CONSTRAINT_VIOLATION).errors(errors));
    }

    //pour les contraintes de validations au niveau des objets
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<MFPAIResponse> handleViolationException(ConstraintViolationException exception) {
        List<ValidationRspError> errors = exception.getConstraintViolations()
                .stream()
                .map(fieldError -> ValidationRspError.builder().field(fieldError.getPropertyPath().toString()).message(fieldError.getMessage()).build())
                .collect(Collectors.toList());

        // LOGGER.error("constraints", errors);
        return ResponseEntity.badRequest().body(MFPAIResponse.error(MFPAIMessage.CONSTRAINT_VIOLATION).errors(errors));

    }

/*    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<SmartCareResponse> handleAmazonS3Exception(AmazonS3Exception ex) {
        return ResponseEntity.ok().body(new SmartCareResponse(SmartCareMessage.WS_AMAZON_ERROR, ex));
    }*/

    /**
     * SECURITY EXCEPTIONS
     **/

    //pour les accès à des ressources (points terminaux des controleurs) non autorisés
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<MFPAIResponse> handleAccessDeniedException(AccessDeniedException exception) {
        // exception.printStackTrace();
        // LOGGER.error("An exception occurred with message: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(MFPAIResponse.error(MFPAIMessage.UNAUTHORIZED).message("the resource you tried to reach is absolutely forbidden for some reason.").errors(exception.getMessage()));
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<MFPAIResponse> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        // exception.printStackTrace();
        // LOGGER.error("An exception occurred with message: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MFPAIResponse.error(MFPAIMessage.NOT_FOUND).message(exception.getMessage()).errors(exception.getMessage()));
    }

    //pour les accès non autorisés
    @ExceptionHandler({AuthenticationException.class, MissingCsrfTokenException.class, InvalidCsrfTokenException.class, SessionAuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<MFPAIResponse> handleAuthenticationException(RuntimeException ex, HttpServletRequest request) {
        // LOGGER.error("An exception occurred with message: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .body(
                        MFPAIResponse
                                .error(MFPAIMessage.UNAUTHORIZED)
                                .message("Vous n'avez pas la permission d'accéder à cette ressource !")
                                .errors(ex.getMessage())
                                .url(UrlUtils.buildFullRequestUrl(request))
                );
    }

    //pour les accès non autorisés
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<MFPAIResponse> handleBadCredentialsException(BadCredentialsException ex) {
        // LOGGER.error("An exception occurred with message: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(MFPAIResponse.error(MFPAIMessage.LOGIN_BAD_CREDENTIALS).message("Incorrect login or password!").errors(ex.getMessage()));
    }

    /* pour le traitement des fichiers */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseEntity<MFPAIResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        // LOGGER.error("An exception occurred with message: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(MFPAIResponse.error(MFPAIMessage.UNAUTHORIZED).message("Vous avez dépassé la taille maximale autorisée !").errors(ex.getMessage()));
    }

    @ExceptionHandler(value = SizeLimitExceededException.class)
    public ResponseEntity<MFPAIResponse> handleSizeLimitExceededException(SizeLimitExceededException ex) {
        // LOGGER.error("An exception occurred with message: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(MFPAIResponse.error(MFPAIMessage.UNAUTHORIZED).message("Vous avez dépassé la taille maximale autorisée par requête !").errors(ex.getMessage()));
    }

    /* fin pour le traitement des fichiers */

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<MFPAIResponse> handleParseExceptionHandler(ParseException exception) {
        // LOGGER.error("An exception occurred with message: {}", exception.getMessage());
        return ResponseEntity.badRequest().body(MFPAIResponse.error(MFPAIMessage.INTERNAL_SERVER_ERROR).message("Le format de date fournit n'est pas pris en compte !").errors(exception.getMessage()));
    }


    @ExceptionHandler(LockedException.class)
    public ResponseEntity<MFPAIResponse> handleLockedException(LockedException exception) {
        // LOGGER.error("An exception occurred with message: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(MFPAIResponse.error(MFPAIMessage.ACCOUNT_DISABLED).errors(exception.getMessage()));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<MFPAIResponse> handleDisabledException(DisabledException exception) {
        // LOGGER.error("An exception occurred with message: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(MFPAIResponse.error(MFPAIMessage.ACCOUNT_DISABLED).errors(exception.getMessage()));
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<MFPAIResponse> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException exception) {
        // LOGGER.error("An exception occurred with message: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(MFPAIResponse.error(MFPAIMessage.LOGIN_MAX_ATTEMPT).errors(exception.getMessage()));
    }

/*    @ExceptionHandler(JobParametersInvalidException.class)
    public ResponseEntity<SmartCareResponse> handleJobParametersInvalidException(JobParametersInvalidException exception) {
        // LOGGER.error("An exception occurred with message: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(SmartCareResponse.error(SmartCareMessage.INVALID_FILE, exception.getMessage()).errors(exception.getMessage()));
    }*/
}
