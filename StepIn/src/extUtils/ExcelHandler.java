package extUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHandler {

	FileInputStream fs;
	XSSFWorkbook wb, wr;
	XSSFSheet ws, wrs;

	XSSFRow row;
	XSSFCell cell;
	String tcId;

	static final String dir = System.getProperty("user.dir");
	static final String dataFile = dir + "/src/testdata/TestData.xlsx";

	static final String dataOut = "C:\\performancemeasurement\\Test_Output.xlsx";
	static final String perDataOut = dir + "/performance_output/performance_output.xlsx";
	static final String perDataIn = dir + "/performance_output/performance_input.xlsx";

	public static void main(String[] args) throws IOException {

		ExcelHandler e = new ExcelHandler();
		e.setFileToRead("Tree", "TC001");
		System.out.println(e.getData("ts002", "item"));

	}

	public int getTotalRowCount() {
		return ws.getLastRowNum();
	}

	public void upDateOutputInNewCell(int rownum, String data, int col) {
		try {
			setDataToCell(rownum, col, data);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public int getLastColInRow(int rowNo) {
		row = wrs.getRow(rowNo);
		int col = 0;
		Iterator cellIter = row.cellIterator();
		while (cellIter.hasNext()) {

			cell = (XSSFCell) cellIter.next();
			col = cell.getColumnIndex();
		}
		return col;
	}

	public String getTestData(String tsId, String data) throws IOException {
		int row = getTestDataRow(tsId);
		int col = getColNumForMatchingString(data, 1);
		return (getdatafromCell(row, col));
	}

	public String getData(String tsId, String data) throws IOException {
		int row = getRowNoforTestCase(tsId);
		int col = getColNumForMatchingString(data);
		return (getdatafromCell(row, col));
	}

	public void setFileToRead(String ScriptID, String tId) throws IOException {
		FileInputStream fs = new FileInputStream(dataFile);
		wb = new XSSFWorkbook(fs);
		ws = wb.getSheet(ScriptID);
		tcId = tId;
	}

	public void setFileToRead(String sheetName) throws IOException {
		FileInputStream fs = new FileInputStream(dataFile);
		wb = new XSSFWorkbook(fs);
		ws = wb.getSheet(sheetName);
	}

	public void setPerFileToRead(String sheetName) throws IOException {
		FileInputStream fs = new FileInputStream(perDataIn);
		wb = new XSSFWorkbook(fs);
		ws = wb.getSheet(sheetName);
	}

	public void setFileToWrite() {
		FileInputStream fs;
		try {
			fs = new FileInputStream(dataOut);
			wr = new XSSFWorkbook(fs);
			wrs = wr.getSheetAt(0);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void setPerformanceFileToWrite() {
		FileInputStream fs;
		try {
			fs = new FileInputStream(perDataOut);
			wr = new XSSFWorkbook(fs);
			wrs = wr.getSheetAt(0);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void getfile(String file, String sheetname) {
		FileInputStream fs;
		try {
			fs = new FileInputStream(file);
			wr = new XSSFWorkbook(fs);
			wrs = wr.getSheet(sheetname);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public int getRowNoforTestCase(String tsId) throws IOException {

		int rowno = 0;
		Iterator rows = ws.rowIterator();
		while (rows.hasNext()) {
			row = (XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			cell = (XSSFCell) cells.next();
			if (tcId.contentEquals(cell.getStringCellValue())) {
				cell = (XSSFCell) cells.next();
				if (tsId.contentEquals(cell.getStringCellValue()))
					break;
			}

			rowno = rowno + 1;

		}

		return rowno;
	}

	public int getTestDataRow(String tsId) throws IOException {

		int rowno = 0;
		Iterator rows = ws.rowIterator();
		while (rows.hasNext()) {
			row = (XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			cell = (XSSFCell) cells.next();

			if (tsId.contentEquals(cell.getStringCellValue()))
				break;

			rowno = rowno + 1;

		}

		return rowno;
	}

	public int getColNumForMatchingString(String data, int rowNo) {

		int colno = 0;

		row = ws.getRow(rowNo);
		Iterator cells = row.iterator();

		while (cells.hasNext()) {
			cell = (XSSFCell) cells.next();
			if (data.contentEquals(cell.getStringCellValue()))
				break;

			colno = colno + 1;
		}
		return colno;
	}

	public int getColNumForMatchingString(String data) {

		int colno = 0;

		row = ws.getRow(2);
		Iterator cells = row.iterator();

		while (cells.hasNext()) {
			cell = (XSSFCell) cells.next();
			if (data.contentEquals(cell.getStringCellValue()))
				break;

			colno = colno + 1;
		}
		return colno;
	}

	public String getdatafromCell(int r, int col) {

		row = ws.getRow(r);
		cell = row.getCell(col);

		try {
			return cell.getStringCellValue();
		} catch (Exception e) {
			double val = cell.getNumericCellValue();
			int intval = (int) val;
			return Integer.toString(intval);
		}

	}

	public void addCell(int r, int col) {
		row = wrs.getRow(r);
		row.createCell(col);
	}

	public void setDataToCell(int r, int col, String data) {

		try {
			addCell(r, col);
			cell = row.getCell(col);
			cell.setCellValue(data);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setDataToCell(int r, int col, double data) {

		try {
			addCell(r, col);
			cell = row.getCell(col);
			cell.setCellValue(data);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void saveToExcel() {
		try {
			FileOutputStream out = new FileOutputStream(dataOut);
			wr.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveToPerformanceExcel() {
		try {
			FileOutputStream out = new FileOutputStream(perDataOut);
			wr.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
