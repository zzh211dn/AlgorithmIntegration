/**
 * 
 */
package com.shanghaiUni.Excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Administrator
 *
 */
public class ExcelOperation {
	public static Workbook getWorkbook(String file)
			throws FileNotFoundException, IOException {
		if (file.endsWith(".xls")) {
			return new HSSFWorkbook(new FileInputStream(file));
		} else if (file.endsWith(".xlsx")) {
			return new XSSFWorkbook(new FileInputStream(file));
		} else {
			throw new RuntimeException("读取excel文件失败");
		}
	}
	public static boolean isEmpty(Cell dataCell) {
		// if (dataCell == null ||
		// "".equals(dataCell.getStringCellValue().trim())) {
		if (dataCell == null || dataCell.getCellType() == Cell.CELL_TYPE_BLANK) {

			return true;
		} else {
			if (dataCell.getCellType() == Cell.CELL_TYPE_STRING
					&& "".equals(dataCell.getStringCellValue().trim())) {

				return true;
			}
			return false;
		}
	}

}
