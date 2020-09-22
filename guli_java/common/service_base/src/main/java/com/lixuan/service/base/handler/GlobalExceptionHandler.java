package com.lixuan.service.base.handler;

import com.lixuan.common.base.result.Result;
import com.lixuan.common.base.result.ResultCodeEnum;
import com.lixuan.common.base.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;

/**
 * 统一异常处理
 * @author LiXxuan
 * @date 2020/9/14 16:28
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 统一异常处理器
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
//        log.error(e.getMessage());
//        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return Result.error();
    }

    /**
     * 处理特定异常：sql语法错误
     * @param e
     * @return
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public Result error(BadSqlGrammarException e){
        log.error(ExceptionUtil.getMessage(e));
        return Result.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }

    /**
     * json解析错误处理方法
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result error(HttpMessageNotReadableException e){
        log.error(ExceptionUtil.getMessage(e));
        return Result.setResult(ResultCodeEnum.JSON_PARSE_ERROR);
    }
}
