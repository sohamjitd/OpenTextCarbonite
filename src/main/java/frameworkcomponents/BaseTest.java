
package frameworkcomponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import frameworkcomponents.ExcelUtil;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public abstract class BaseTest {
	
	private ExtentReports extent = ExtReport.getReport(System.getProperty("user.dir")+"/Reports/"+this.getClass().getName()+System.currentTimeMillis()+"Reports.html");
	protected ExtentTest test;
	private String SheetName;
	protected String TestFileDirectory;
	private ExcelUtil excelutil;
	protected ThreadLocal<WebDriver> wbdriver = new ThreadLocal<WebDriver>();	


	protected void setDefaultParamsUI (String datatable, String sheetname)
	{
		String className = this.getClass().getName();
		System.out.println("Executing " + className + " UI Tests");
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//chromedriver");
		System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"//geckodriver.exe");
		SheetName=sheetname;
		TestFileDirectory=System.getProperty("user.dir")+"//src//test//resources";
		excelutil=new ExcelUtil(datatable+".xlsx");
	}

	/*
	This function will run before EveryTestCase
	 */
	@BeforeMethod(alwaysRun=true)
	protected void intializeTest(Method method){
		String testMethodName=method.getName();
		ExtentTest test = extent.startTest(testMethodName);
		System.out.println("Executing:" + testMethodName);
		set_Logger(test);
	}

	@AfterMethod(alwaysRun=true)
	protected void endTest(Method method){
		extent.flush();
	}

	/*
	  	This is the Data Fetching Function.
		############################	  User INPUT: Name of the Sheet which we are referring to in the Excel Sheet. Here its "RatingsAPI". Cannot have a space ##############################
	 */
	@DataProvider(name="getData")
	protected Object[][] getDataFromDataProvider(Method method) throws IOException{
		return this.excelutil.getAllMatchingTestCases(SheetName, method.getName());
	}



	/**
	 * @param expected - Expected value to check
	 * @param actual - actual value to check
	 * @param key - A text representation in the logs of the items that represent what expected and actual are
	 */
	protected <T> void compareAndLogResults(T actual, T expected, String key)
	{
		logResults(Objects.equals(expected, actual), key, true, expected);
		Assert.assertEquals(actual, expected,key+ " value is " + actual);
	}


	/**
	 * @param result - Represents whether the test succeeded of failed
	 * @param key - A text representation in the logs of the items that represent what expected and actual are
	 * @param logExpected - Whether to log the expected value
	 * @param expected - Expected value to log
	 */
	private <T> void logResults(Boolean result, String key, Boolean logExpected, T expected)
	{
		String success = "succeeded";
		int successFlag = 1;
		LogStatus status = LogStatus.PASS; 
		if (!result)
		{
			success = "failed";
			successFlag = 0;
			status = LogStatus.FAIL;
		}
		if (logExpected)
		{
			ExtTest.getTest().log(status, key+ " value is " + expected);
		}
		System.out.println("Validation of "+ key + " " + success);
	}

	/**
	 * @param actual - Result of the condition to check
	 * @param key - A text representation in the logs of the item to represent what actual is
	 */
	protected void assertTrueAndLogResults(Boolean actual, String key)
	{
		logResults(actual, key, false, "");
		Assert.assertTrue(actual);
	}


	/**
	 * @param filePath - File to open and output to console
	 * @return - The contents of the file at 'filePath' or null
	 */
	protected String outputFile(String filePath) 
	{
		String contents = null;
		try {
			contents = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(contents);
		return contents;	
	}

	//For UI
	public synchronized void set_Logger(ExtentTest tst) {
		ExtTest.setTest(tst);
	}

	//Webdriver based on mode of execution
	/**
	 * @param Browser name eg Chrome/Firefox
	 * config file set up- for Remote or local execution
	 * @return - Selenium WebDriver
	 */
	protected WebDriver myBrowserDriver(String browser) throws MalformedURLException{


			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//chromedriver.exe");			
			if(browser.equalsIgnoreCase("chrome")){
				System.out.println("Prepping Chrome Browser");
				return new ChromeDriver();
			}else if(browser.equalsIgnoreCase("firefox")){
				System.out.println("Prepping Firefox Browser");
				return new FirefoxDriver();
			}else{
				return new ChromeDriver();	
			}
		
		
	}
	
}

