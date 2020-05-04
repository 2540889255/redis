package com.aynu.redis.view;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface PdfExportService {

    public void make(Map<String,Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse httpServletResponse) throws DocumentException;
}
