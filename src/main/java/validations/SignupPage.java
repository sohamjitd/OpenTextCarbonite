package validations;

import static org.testng.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import frameworkcomponents.DriverUtilBase;
import frameworkcomponents.ExtTest;

public class SignupPage {

	private DriverUtilBase driverUtilBase;
	private String liHomeBackUp="//a[contains(text(),'Home backup')]/parent::li";
	private String inputEmailId="//input[@name='Email']";
	private String inputConfirmEmailId="//input[@name='confirmEmail']";
	private String inputPassword="//input[@name='Password']";
	private String inputConfirmPassword="//input[@name='ConfirmPassword']";
	private String selectCounrty="//select[@name='Country']";
	private String btnSubmit="//input[@value='Submit']";
	private String btnCookieClose="//*[@id='notice']//button";
	private String labelWelcome="//h2[contains(text(),'Welcome to Carbonite Safe')]";
	
	//Constructor	--Reusable
	public SignupPage(DriverUtilBase driver) {
		this.driverUtilBase=driver;
	}

	public void gotoURL(String url){
		this.driverUtilBase.gotoURL(url);
	}
	
	public String getPageTitle(){
		return this.driverUtilBase.getTitle();
	}

	public boolean isHomeBackUpTabSelected() {
		this.driverUtilBase.waitForElementToBePresent(By.xpath(liHomeBackUp));
		if (this.driverUtilBase.getValueFromProperty(By.xpath(liHomeBackUp), "active")!=null) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean signupFormElementsDisplayed() {
		if(this.driverUtilBase.isEnabled(By.xpath(inputEmailId))&&this.driverUtilBase.isEnabled(By.xpath(inputConfirmEmailId))
				&&this.driverUtilBase.isEnabled(By.xpath(inputPassword))&&this.driverUtilBase.isEnabled(By.xpath(inputConfirmPassword))
				&&this.driverUtilBase.isEnabled(By.xpath(selectCounrty))&&this.driverUtilBase.isEnabled(By.xpath(btnSubmit))) {
			return true;
		}else return false;
	}
	
	public String fillSignupInformation(String pwd) throws InterruptedException {
		String emailId=getRandomId()+"@gmail.com";
		this.driverUtilBase.sendText(By.xpath(inputEmailId), emailId);
		this.driverUtilBase.sendText(By.xpath(inputConfirmEmailId), emailId);
		this.driverUtilBase.sendText(By.xpath(inputPassword), pwd);
		this.driverUtilBase.sendText(By.xpath(inputConfirmPassword), pwd);
		waitSeconds(1);
		this.driverUtilBase.pressEnter(By.xpath(inputConfirmPassword));
		waitSeconds(2);
		this.driverUtilBase.waitForElementToBePresent(By.xpath(labelWelcome));
		return emailId;
	}
	
	
	
	public void waitSeconds(int sec) throws InterruptedException{
		Thread.sleep(sec*1000);
	}


	public void quit(){
		this.driverUtilBase.closeBrowser();
	}


	public String capture() {
		return this.driverUtilBase.capture();
	}

	public String getRandomId() {
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "-");
		for (int i = 0; i < 5; i++)
		    sb.append(chars[rnd.nextInt(chars.length)]);

		return sb.toString();
	}
	
	public <T> void compareAndLogUI(T actual, T expected, String key, ExtentTest test)
	{

		try
		{
			if (actual.equals(expected)){
				ExtTest.getTest().log(LogStatus.PASS, ExtTest.getTest().addScreenCapture(capture()) + key);
				System.out.println("Validation: " + key);
				Assert.assertEquals(actual, expected, key + " value is " + actual);
			}else
			{
				ExtTest.getTest().log(LogStatus.FAIL, ExtTest.getTest().addScreenCapture(capture()) + key);
				System.out.println("Validation: " + key + " Failed");
				quit();
				Assert.assertEquals(actual, expected, key + " value is incorrect" + actual);
			}
		}
		catch (Exception e)
		{
			System.out.println("Validation: "+ key+" Failed");
			ExtTest.getTest().log(LogStatus.FAIL, ExtTest.getTest().addScreenCapture(this.driverUtilBase.capture()) +" unexpected error " + e.getStackTrace().toString());
			quit();
			assertTrue(false, "Validation of " + key + " failed");
		}

	}

}
