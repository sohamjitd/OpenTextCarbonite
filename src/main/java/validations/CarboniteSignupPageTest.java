package validations;

import java.net.MalformedURLException;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import frameworkcomponents.BaseTest;
import frameworkcomponents.DriverUtilBase;

public class CarboniteSignupPageTest extends BaseTest{


	@BeforeClass(alwaysRun=true)
	public void setDefaultParamsUI() {
		super.setDefaultParamsUI("Carbonite", "SignupPage");
	}

	@Test(testName="TC1_ValidateLandingPage",dataProvider="getData",enabled=true)
	public void TC1_ValidateLandingPage(Map<Object,Object> map) throws MalformedURLException {
		DriverUtilBase driver=new DriverUtilBase(myBrowserDriver(map.get("Browser").toString()));
		SignupPage signuppage=new SignupPage(driver);

		try {
			signuppage.gotoURL(map.get("URL").toString());
			signuppage.compareAndLogUI(signuppage.isHomeBackUpTabSelected(), true, map.get("Test_Description").toString()+" :: Navigated to home page and Home Back Up Tab is selected.", test);
			signuppage.quit();
		}catch(Exception e) {
			signuppage.compareAndLogUI("Error from Selenium Exception: ", "", e.toString(), test);
		}
	}

	@Test(testName="TC2_ValidateSignupForm",dataProvider="getData",enabled=true)
	public void TC2_ValidateSignupForm(Map<Object,Object> map) throws MalformedURLException {
		DriverUtilBase driver=new DriverUtilBase(myBrowserDriver(map.get("Browser").toString()));
		SignupPage signuppage=new SignupPage(driver);

		try {
			signuppage.gotoURL(map.get("URL").toString());
			signuppage.compareAndLogUI(signuppage.signupFormElementsDisplayed(), true, map.get("Test_Description").toString()+" :: Sign up Form elements are displayed.", test);
			signuppage.quit();
		}catch(Exception e) {
			signuppage.compareAndLogUI("Error from Selenium Exception: ", "", e.toString(), test);
		}
	}

	@Test(testName="TC3_ValidateSuccessfulSignup",dataProvider="getData",enabled=true)
	public void TC3_ValidateSuccessfulSignup(Map<Object,Object> map) throws MalformedURLException, InterruptedException {
		DriverUtilBase driver=new DriverUtilBase(myBrowserDriver(map.get("Browser").toString()));
		SignupPage signuppage=new SignupPage(driver);

		try {
			signuppage.gotoURL(map.get("URL").toString());
			signuppage.compareAndLogUI(signuppage.isHomeBackUpTabSelected(), true, map.get("Test_Description").toString()+" :: Navigated to home page and Home Back Up Tab is selected.", test);
			String email=signuppage.fillSignupInformation(map.get("Password").toString());
			signuppage.compareAndLogUI(signuppage.getPageTitle(), "Download", map.get("Test_Description").toString()+" :: Sign up for emailId: " +email+" completed", test);
		
		  signuppage.quit();
		  
		  }catch(Exception e) {
		  signuppage.compareAndLogUI("Error from Selenium Exception: ", "",
		  e.toString(), test); }
		 

	}


}
