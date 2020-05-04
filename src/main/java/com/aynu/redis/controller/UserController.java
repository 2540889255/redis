package com.aynu.redis.controller;

import com.aynu.redis.pojo.User;
import com.aynu.redis.service.UserService;
import com.aynu.redis.service.UserServiceImpl;
import com.aynu.redis.view.PdfExportService;
import com.aynu.redis.view.PdfView;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.awt.*;
import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    public UserService userService;

    @RequestMapping("/getUser")
    @ResponseBody
    public User getUser(){
        User user=new User();
        user.setUser_id(1);
        user.setUser_name("小路");
        userService.printPerson(user);
        return user;
    }

    @RequestMapping("/getUsers")
    public ModelAndView getUsers(){
        List<User> users = userService.getUsers();

        View view=new PdfView(exportSerivce());
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setView(view);
        modelAndView.addObject("userList",users);
        return modelAndView;

    }

    @SuppressWarnings("unchecked")
    private PdfExportService exportSerivce() {
        //使用mambda表达式定义自定义导出
        return (map,document,writer,request,response) -> {
            try {
                //设置A4纸张
                document.setPageSize(PageSize.A4);
                //标题
                document.addTitle("用户信息");
                //换行
                document.add(new Chunk("\n"));
                //表格三列
                PdfPTable pdfPTable = new PdfPTable(3);
                //单元格
                PdfPCell cell=null;
                //字体
                //第一种：使用iTextAsian.jar包中的字体
                //BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                //Font font = new Font(baseFont);

                //第二种：使用Windows系统字体
                BaseFont baseFont_zh = BaseFont.createFont("C:\\Windows\\Fonts\\simfang.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                Font font = new Font(baseFont_zh);
                //BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                //Font font=new Font();//baseFont);
                font.setColor(Color.BLUE);
                font.setStyle(Font.BOLD);
                //标题
                cell =new PdfPCell(new Paragraph("id",font));
                //居中对齐
                cell.setHorizontalAlignment(1);
                //将单元格加入表格
                pdfPTable.addCell(cell);
                cell=new PdfPCell(new Paragraph("user_name",font));
                //居中对齐
                cell.setHorizontalAlignment(1);
                pdfPTable.addCell(cell);
                cell=new PdfPCell(new Paragraph("user_infocel",font));
                cell.setHorizontalAlignment(1);
                pdfPTable.addCell(cell);
                //获取数据模型中的用户列表
                List<User> userList=(List<User>)map.get("userList");
                System.out.println(userList.size());
                for (User user:userList
                     ) {
                    document.add(new Chunk("\n"));
                    cell=new PdfPCell(new Paragraph(user.getUser_id()+""));
                    pdfPTable.addCell(cell);
                    cell=new PdfPCell(new Paragraph(user.getUser_name(),font));
                    pdfPTable.addCell(cell);
                    cell=new PdfPCell(new Paragraph(user.getUser_infocol()+""));
                    pdfPTable.addCell(cell);

                }
                //在档案红加入表格
                document.add(pdfPTable);
            }catch (Exception e){
                e.printStackTrace();
            }
        };
    }
}
