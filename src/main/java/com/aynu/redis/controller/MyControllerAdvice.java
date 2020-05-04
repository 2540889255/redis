package com.aynu.redis.controller;

import com.aynu.redis.exception.NotFoundException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice(
        //制定拦包的控制器
        basePackages = {"com.aynu.redis.controller.*"},
        //限定被标注为@Controller 和 @RestController得类进行拦截处理
        annotations = {Controller.class, RestController.class}
)
public class MyControllerAdvice {

    //绑定格式化 参数转换规则和增加验证器等
    @InitBinder
    public void initDataBinder(WebDataBinder binder){
        //自定义日期编辑器,限定格式为yyyy-MM-dd 且参数不允许为空
        CustomDateEditor dateEditor=new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),false);
        //注册自定义日期编辑器
        binder.registerCustomEditor(Date.class,dateEditor);
    }

    //在执行控制器之前执行 可以初始化数据
    @ModelAttribute
    public void projectModel(Model model){
        model.addAttribute("project_name","chapter");
    }


    //@ExceptionHandler(value = Exception.class)
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(Model model,NotFoundException exception){
        //给数据模型增加异常信息
        model.addAttribute("exception_message",exception.getMessage());
        //返回异常信息
        return "error/Exception";

    }


}
