package com.ltri.excel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.ltri.excel.listener.GoodsListener;
import com.ltri.excel.entity.Goods;
import com.ltri.excel.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@RestController
@Api(tags = "Excel上传相关接口")
public class ExcelController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/downloadData")
    @ApiOperation("查询数据导出Excel")
    public void download(HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("demo", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        //sheet1
        WriteSheet writeSheet = EasyExcel.writerSheet(0, "sheet1").head(Goods.class).build();
        //sheet2
        WriteSheet writeSheet2 = EasyExcel.writerSheet(1, "sheet2").head(Goods.class).build();
        excelWriter.write(goodsService.list(), writeSheet);
        excelWriter.write(goodsService.list(), writeSheet2);
        excelWriter.finish();
    }

    @GetMapping("/downloadExcelTemplate")
    @ApiOperation("下载导出模版")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        //设置文件路径
        ClassPathResource classPathResource = new ClassPathResource("goods.xlsx");
        InputStream inputStream = classPathResource.getInputStream();
        response.setContentType("application/force-download");
        String fileName = new String("模版".getBytes(), "iso8859-1") + ".xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        IOUtils.copy(inputStream, response.getOutputStream());
    }

    @PostMapping("/uploadExcel")
    @ApiOperation("上传Excel导入数据库")
    public void upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), Goods.class, new GoodsListener(goodsService)).sheet().doRead();
    }
}
