package com.news.demo.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public String AuthorizedErrorHandler(HttpServletRequest request,Exception e)
    {
        logger.info("##############################认证失败##############################");
        return "403  没有权限";
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public String NullPointerExceptionHandler(HttpServletRequest request,Exception e)
    {
        logger.info("#############################空指针################################");
        e.printStackTrace();
        return "空指针异常，请联系后台";
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public String HttpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request,Exception e)
    {
        logger.info("#############################请求错误################################");
        return "请求方法错误，请先检查是否登录";
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public String SQLExceptionHandler(HttpServletRequest request,Exception e)
    {
        logger.info("#############################数据库异常################################");
        e.printStackTrace();
        return "数据库出错，请检查参数格式并联系后台";
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public String HttpMessageNotReadableExceptionHandler(HttpServletRequest request,Exception e)
    {
        logger.info("#############################收不到参数，可能是无登陆################################");
        return "缺少参数，或参数格式不正确";
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public String MultipartExceptionHandler(HttpServletRequest request,Exception e)
    {
        logger.info("#############################文件传输异常，可能是文件超过2MB################################");
        return "文件可能超过2MB";
    }
}
