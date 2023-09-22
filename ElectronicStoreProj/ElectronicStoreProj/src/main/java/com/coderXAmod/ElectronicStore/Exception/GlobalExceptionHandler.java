package com.coderXAmod.ElectronicStore.Exception;

import com.coderXAmod.ElectronicStore.dto.ApiResponceMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalExceptionHandler {
    @ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<ApiResponceMessage> resoureNotFoundExceptionHandler(ResourseNotFoundException ex)
    {
        ApiResponceMessage responce = ApiResponceMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
    return new ResponseEntity<>(responce,HttpStatus.NOT_FOUND);
    }
    //MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleMethodNotFoundValidException(MethodArgumentNotValidException ex)
    {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String ,Object> responce =new HashMap<>();
        allErrors.stream().forEach(objectError ->
        {
            String message = objectError.getDefaultMessage();
            String field =((FieldError) objectError).getField();
            responce.put(field,message);
        });
return new ResponseEntity<>(responce,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponceMessage> handleBadApiRequest(BadApiRequest ex)
    {
        ApiResponceMessage responce = ApiResponceMessage.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();
        return new ResponseEntity<>(responce,HttpStatus.NOT_FOUND);
    }
}
