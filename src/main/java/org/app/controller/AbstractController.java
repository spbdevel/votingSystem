package org.app.controller;

import org.app.controller.exeption.ExceptionJSONInfo;
import org.app.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractController {
    @Autowired
    protected RestaurantRepository restauRepository;


    @ExceptionHandler(Throwable.class)
    public @ResponseBody
    ExceptionJSONInfo handleEmployeeNotFoundException(HttpServletRequest request, Exception ex){
        ExceptionJSONInfo response = new ExceptionJSONInfo();
        response.setUrl(request.getRequestURL().toString());
        response.setMessage(ex.getMessage() == null ? "error on making request" : ex.getMessage());
        return response;
    }

}
