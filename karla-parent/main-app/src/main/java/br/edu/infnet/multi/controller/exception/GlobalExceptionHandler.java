package br.edu.infnet.multi.controller.exception;


import br.edu.infnet.multi.model.exceptions.CepNotFoundException;
import br.edu.infnet.multi.model.exceptions.ExternalApiException;
import br.edu.infnet.multi.model.exceptions.InvalidCepException;
import br.edu.infnet.multi.model.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Validação de campos obrigatórios com @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // JSON malformado ou campo tipo errado
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidJson(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();
        String mensagem;

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException invalidFormatException) {
            String fieldName = invalidFormatException.getPath().get(0).getFieldName();
            String targetType = invalidFormatException.getTargetType().getSimpleName();
            mensagem = "O campo '" + fieldName + "' tem valor inválido. Esperado tipo: " + targetType;
        } else {
            mensagem = "O corpo da requisição está em formato JSON inválido. Verifique a sintaxe.";
        }

        errors.put("Data/hora", LocalDateTime.now().toString());
        errors.put("Status", HttpStatus.METHOD_NOT_ALLOWED.toString());
        errors.put("Mensagem", mensagem);

        return new ResponseEntity<>(errors, HttpStatus.METHOD_NOT_ALLOWED);
    }

    // Para @RequestParam ausente
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingRequestParam(MissingServletRequestParameterException ex) {
        Map<String, String> errors = new HashMap<>();

        String mensagem = "O parâmetro obrigatório '" + ex.getParameterName() +
                "' do tipo '" + ex.getParameterType() + "' não foi informado.";

        errors.put("Data/hora", LocalDateTime.now().toString());
        errors.put("Status", HttpStatus.BAD_REQUEST.toString());
        errors.put("Mensagem", mensagem);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Para @PathVariable ausente
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleMissingPathVariable(HttpRequestMethodNotSupportedException ex) {
        Map<String, String> errors = new HashMap<>();

        String mensagem = "O parâmetro de caminho não foi informado.";

        errors.put("Data/hora", LocalDateTime.now().toString());
        errors.put("Status", HttpStatus.BAD_REQUEST.toString());
        errors.put("Mensagem", mensagem);

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(IllegalArgumentException ex) {
        Map<String, String> errors = new HashMap<>();

        errors.put("Data/hora", LocalDateTime.now().toString());
        errors.put("Status", HttpStatus.BAD_REQUEST.toString());
        errors.put("Mensagem", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(IllegalStateException ex) {
        Map<String, String> errors = new HashMap<>();

        errors.put("Data/hora", LocalDateTime.now().toString());
        errors.put("Status", HttpStatus.BAD_REQUEST.toString());
        errors.put("Mensagem", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(ResourceNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();

        errors.put("Data/hora", LocalDateTime.now().toString());
        errors.put("Status", HttpStatus.NOT_FOUND.toString());
        errors.put("Mensagem", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCepException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCepException(InvalidCepException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Data/hora", LocalDateTime.now().toString());
        errors.put("Status", HttpStatus.BAD_REQUEST.toString());
        errors.put("Mensagem", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CepNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCepNotFoundException(CepNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Data/hora", LocalDateTime.now().toString());
        errors.put("Status", HttpStatus.NOT_FOUND.toString());
        errors.put("Mensagem", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<Map<String, String>> handleExternalApiException(ExternalApiException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Data/hora", LocalDateTime.now().toString());
        errors.put("Status", HttpStatus.SERVICE_UNAVAILABLE.toString());
        errors.put("Mensagem", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.SERVICE_UNAVAILABLE);
    }










}
