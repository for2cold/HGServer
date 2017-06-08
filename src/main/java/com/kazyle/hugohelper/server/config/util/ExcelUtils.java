package com.kazyle.hugohelper.server.config.util;

import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * <b>ExcelUtils</b> is
 * </p>
 *
 * @author Kazyle
 * @version 1.0.0
 * @since 2017/6/8
 */
public class ExcelUtils {

    /**
     * 读取Excel数据
     * @param inputStream
     * @param startRowNum
     * @return
     */
    public static List<List<String>> readExcel(InputStream inputStream, int startRowNum) {
        List<List<String>> lists = null;
        HSSFWorkbook hssfWorkbook = null;
        try {
            hssfWorkbook = new HSSFWorkbook(inputStream);

            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            if (null != hssfSheet) {
                lists = Lists.newArrayList();
                for (int rowNum = startRowNum; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        List<String> list = Lists.newArrayList();
                        for (int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++) {
                            HSSFCell hssfCell = hssfRow.getCell(cellNum);
                            if (null != hssfCell) {
                                hssfCell.setCellType(Cell.CELL_TYPE_STRING);
                                list.add(hssfCell.getStringCellValue());
                            } else {
                                list.add("-");
                            }
                        }
                        lists.add(list);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lists;
    }
}
