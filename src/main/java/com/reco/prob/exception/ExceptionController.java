package com.reco.prob.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> noSuchElemenHandle() {
        return ResponseEntity.badRequest().body("잘못된 조회입니다.");
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> numberFormatHandle() {
        return ResponseEntity.badRequest().body("숫자를 확인후 입력해 주세요.");
    }

    @ExceptionHandler(NoSuchHistoryException.class)
    public ResponseEntity<String> noSuchHistoryHandle(NoSuchHistoryException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> noSuchHistoryHandle(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("입력하신 데이터를 확인하십시오. "+ e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
