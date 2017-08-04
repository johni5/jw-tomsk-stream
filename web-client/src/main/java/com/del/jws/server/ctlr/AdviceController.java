package com.del.jws.server.ctlr;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Created by dodolinel
 * date: 06.04.2017
 */
@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle(Exception ex) {
        return "redirect:/not_found";
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public String handleValidation(Exception ex) {
//        return "redirect:/not_found";
//    }

}
