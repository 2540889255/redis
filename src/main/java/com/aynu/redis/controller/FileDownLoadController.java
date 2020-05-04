package com.aynu.redis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 */
@Controller
@RequestMapping("/file")
public class FileDownLoadController {

    @GetMapping("/upload")
    public String fileUpLoad(){
        return "upload";
    }

    @PostMapping("/upload/request")
    @ResponseBody
    public Map<String,Object> uploadPart(@RequestParam("file")Part part){
        System.out.println("upload的请求到达了这");
        //获取提交文件名称
        String filename=part.getSubmittedFileName();
        System.out.println(filename);
        String filepath="D:\\springboot\\"+filename;
        try {
            part.write(filepath);
        }catch (Exception e){
            e.printStackTrace();
            return dealResultMap(false,"上传失败");
        }
        return dealResultMap(true,"长传成功");
    }
    @PostMapping("/upload/mutirequest")
    @ResponseBody
    public String upload(MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();
        String filePath = "D:\\springboot\\";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            System.out.println("注入成功");
            return "success";
        } catch (IOException e) {
            System.out.println("出现了错误");
        }
        return "404";
    }


    private Map<String, Object> dealResultMap(boolean b, String string) {
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("success",b);
        result.put("msg",string);
        return result;
    }


}
