package com.aynu.redis.view;



import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PdfView extends AbstractPdfView {

    private PdfExportService pdfExportService=null;


    //创建对象的时候导出服务的接口
    public  PdfView(PdfExportService pdfExportService){
        this.pdfExportService=pdfExportService;
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfWriter pdfWriter, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        pdfExportService.make(map,document,pdfWriter,httpServletRequest,httpServletResponse);
    }


}
