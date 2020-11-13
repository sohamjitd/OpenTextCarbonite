package frameworkcomponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	public String path;


	public ExcelUtil(String path){
		this.path=System.getProperty("user.dir")+"//src//main//resources//"+ path;
	}

	//Function to return the excel sheet values as a 2D Array of Strings
	public Object[][] getAllMatchingTestCases(String sheetName,String tcName)throws IOException, IllegalArgumentException {
		
		if (sheetName == null)
		{
			throw new IllegalArgumentException(sheetName + " cannot be null.");
		}
		if (tcName == null)
		{
			throw new IllegalArgumentException(tcName + " cannot be null.");
		}
		XSSFSheet sheet=null;
		XSSFWorkbook wb = null;
		FileInputStream file = null;
		try
		{
			file=new FileInputStream(this.path);
			wb =new XSSFWorkbook(file);
			sheet = wb.getSheet(sheetName);
		}
		finally
		{
			if (wb != null)
			{
				wb.close();
			}
			if (file != null)
			{
				file.close();
			}
		}
		
		int lastRowNum=sheet.getLastRowNum();
		int lastCellNum=sheet.getRow(0).getLastCellNum();

		int totalrowwithTC=0;
		for(int k=0;k<=lastRowNum;k++){
			XSSFRow row = sheet.getRow(k);
			if (row != null)
			{
				XSSFCell cell = row.getCell(0);
				if (cell != null && tcName.equals(cell.toString()))
				{
					totalrowwithTC++;
				}
			}
			
		}
		if (totalrowwithTC < 1)
		{
			throw new IllegalArgumentException("Test Case: " + tcName + " on sheet " + sheetName + " has no associated test case rows.");
		}
		Object[][] obj=new Object[totalrowwithTC][1];
		int ag=0;

		for(int i=1;i<=lastRowNum;i++){
			XSSFRow row = sheet.getRow(i);
			if (row != null)
			{
				XSSFCell cell = row.getCell(0);
				if (cell != null && tcName.equals(cell.toString()))
				{
					Map<Object,Object> datamap=new HashMap<>();
					for(int j=0;j<lastCellNum;j++){
						datamap.put(sheet.getRow(0).getCell(j).toString(), row.getCell(j).toString());
					}
					obj[ag][0]=datamap;
					ag++;
				}
			}
			
		}

		return obj;

	}
}
