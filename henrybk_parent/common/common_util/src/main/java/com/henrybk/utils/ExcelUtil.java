package com.henrybk.utils;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.henrybk.handler.CustomCellWriteHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description EasyExcel工具类
 * @author Henry
 * @since 2023-05-26
 */
@Slf4j
public class ExcelUtil {

    /**
     * 导出excel
     * @param response  响应类
     * @param fileName  文件名
     * @param sheetName sheet名
     * @param dataList  数据列表
     * @param clazz     class类
     * @param <T>       泛型
     */
    @SneakyThrows
    public static <T> void export(HttpServletResponse response, String fileName, String sheetName, List<T> dataList, Class<T> clazz) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(Charsets.UTF_8.name());
        // 这里URLEncoder.encode可以防止中文乱码
        fileName = fileName + "-" + DateFormatUtils.format(new Date(),"yyyyMMdd");
        fileName = URLEncoder.encode(fileName, Charsets.UTF_8.name()).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), clazz).registerWriteHandler(new CustomCellWriteHandler())
                .sheet(sheetName != null ? sheetName : "sheet1").doWrite(dataList);
    }

    public static<T> List<T> readExcel(InputStream file, Class<T> model) {
        List<T> list = new ArrayList<>();
        EasyExcel
                //读取的文件
                .read(file)
                //反射获取类型
                .head(model)
                //excel类型 (可以指定 xlx,xlsx,csx)
                //.excelType(ExcelTypeEnum.XLSX)
                //读取的excel左下角的名字
                .sheet(0)
                //注册监听器
                .registerReadListener(new AnalysisEventListener<T>() {
                    @Override
                    public void invoke(T t, AnalysisContext analysisContext) {
                        list.add(t);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        System.out.println("读取完毕" + model);
                    }
                }).doRead();
        return list;
    }
}


