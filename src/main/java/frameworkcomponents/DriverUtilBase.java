package frameworkcomponents;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverUtilBase {
	protected WebDriver driver;
	protected WebDriverWait wait;

	//Default Constructor
	public DriverUtilBase(){

	}

	//Overloading default Constructor
	public DriverUtilBase(WebDriver driver){
		this.driver=driver;
		this.wait=new WebDriverWait(this.driver,30);
	}

	//Function to navigate to a URL
	public void gotoURL(String url) {
		this.driver.manage().window().maximize();
		this.driver.get(url);		
	}
	
	public void navigateURL(String url) {
		this.driver.navigate().to(url);
	}

	//Function to close the browser
	public void closeBrowser(){
		this.driver.quit();
	}

	//Function to get the title of a page opened in Browser
	public String getTitle() {
		// TODO Auto-generated method stub
		return this.driver.getTitle();
	}

	//Function to enter text into a webelement
	public void sendText(By locator, String value) {
		this.driver.findElement(locator).sendKeys(value);
	}

	//Function to click a WebElement
	public void click(By locator) {
		this.driver.findElement(locator).click();
	}
	
	public boolean isEnabled(By locator){
		return this.driver.findElement(locator).isEnabled();
	}

	//Function to wait for a Webelement
	public void waitForElementToBePresent(By locator){
		this.wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	//Function to press Enter in a webelement
	public void pressEnter(By locator){
		this.driver.findElement(locator).sendKeys(Keys.ENTER);
	}

	//Function to get a specific attribute value from the element
	public String getValueFromProperty(By locator,String property){
		return this.driver.findElement(locator).getAttribute(property);
	}	


	//Screenshot capture Function
	public String capture(){
		try{
			File srcFile=((TakesScreenshot)this.driver).getScreenshotAs(OutputType.FILE);
			//File Dest=new File("src/.../ErrImages/"+System.currentTimeMillis()+".png");
			//File Dest=new File(System.getProperty("user.dir")+"//test-output//screenshots//"+System.currentTimeMillis()+".png");
			//String filepath=Dest.getAbsolutePath();
			String filepath="./Reports/screenshots/"+System.currentTimeMillis()+".png";
			File Dest=new File(filepath);
			
			FileUtils.copyFile(srcFile, Dest);
			return "."+filepath;
		}catch(Exception e){
			return null;
		}


	}

	public void close() {
		this.driver.close();
	}

}

