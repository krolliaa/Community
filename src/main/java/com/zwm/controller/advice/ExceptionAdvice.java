package com.zwm.controller.advice;

import com.zwm.util.CommunityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice(annotations = {Controller.class})
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({Exception.class})
    public void exceptionHandler(Exception exception, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        //报错日志
        logger.error("服务器发送异常：" + exception.getMessage());
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            logger.error(stackTraceElement.toString());
        }
        //根据请求判断是不是异步请求
        String xRequestedWith = httpServletRequest.getHeader("x-requested-with");
        if ("XMLHttpRequest".equals(xRequestedWith)) {
            httpServletResponse.setContentType("application/plain;charset-utf-8");
            PrintWriter printWriter = httpServletResponse.getWriter();
            printWriter.write(CommunityUtils.getJsonString(1, "服务器异常"));
        } else {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error");
        }
    }
}
