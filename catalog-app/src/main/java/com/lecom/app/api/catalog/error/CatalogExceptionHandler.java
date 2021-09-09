package com.lecom.app.api.catalog.error;

import com.lecom.app.api.catalog.domain.service.exception.CatalogAlreadyExistsException;
import com.lecom.app.api.catalog.domain.service.exception.ProductAlreadyExistsException;
import com.lecom.app.api.catalog.domain.service.exception.ProductNotFoundException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CatalogExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        String messageUser = messageSource.getMessage("mensagem.invalida",null, LocaleContextHolder.getLocale());
        String messageDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<Erro> erros = List.of(new Erro(messageUser, messageDev));
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        List<Erro> erros = createErrorsList(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler( { DataIntegrityViolationException.class } )
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest webRequest) {
        String messageUser = messageSource.getMessage("recurso.operacao-nao-permitida",null, LocaleContextHolder.getLocale());
        String messageDev = ExceptionUtils.getRootCauseMessage(ex);
        List<Erro> erros = List.of(new Erro(messageUser, messageDev));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler( { EmptyResultDataAccessException.class } )
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
        String messageUser = messageSource.getMessage("recurso.nao-encotrado",null, LocaleContextHolder.getLocale());
        String messageDev = ex.toString();
        List<Erro> erros = List.of(new Erro(messageUser, messageDev));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler( { CatalogAlreadyExistsException.class } )
    public ResponseEntity<Object> handleCatalogAlreadyExistsException(CatalogAlreadyExistsException ex, WebRequest request) {
        String messageUser = messageSource.getMessage("catalog.already.exists",null, LocaleContextHolder.getLocale());
        String messageDev = ex.toString();
        List<Erro> erros = List.of(new Erro(messageUser, messageDev));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler( { ProductAlreadyExistsException.class } )
    public ResponseEntity<Object> handleProductAlreadyExistsException(ProductAlreadyExistsException ex, WebRequest request) {
        String messageUser = messageSource.getMessage("product.already.exists",null, LocaleContextHolder.getLocale());
        String messageDev = ex.toString();
        List<Erro> erros = List.of(new Erro(messageUser, messageDev));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler( { ProductNotFoundException.class } )
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
        String messageUser = messageSource.getMessage("product.not.found",null, LocaleContextHolder.getLocale());
        String messageDev = ex.toString();
        List<Erro> erros = List.of(new Erro(messageUser, messageDev));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private List<Erro> createErrorsList(BindingResult bindingResult) {
        List<Erro> erros = new ArrayList<>();
        for(FieldError f: bindingResult.getFieldErrors()) {
            String messageUser = messageSource.getMessage(f, LocaleContextHolder.getLocale());
            String messageDev = f.toString();
            erros.add(new Erro(messageUser, messageDev));
        }
        return erros;
    }

    public static class Erro {

        private String messageToUser;
        private String messageToDev;

        public Erro(String messageToUser, String messageToDev) {
            this.messageToUser = messageToUser;
            this.messageToDev = messageToDev;
        }

        public String getMessageToUser() {
            return messageToUser;
        }

        public String getMessageToDev() {
            return messageToDev;
        }
    }

}
