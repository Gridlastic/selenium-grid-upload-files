package upload;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import junit.framework.TestCase;

public class selenium_grid_file_upload extends TestCase {
	 private RemoteWebDriver driver;

	 //NOTE: replace USERNAME:ACCESS_KEY@HUB_SUBDOMAIN and VIDEO_URL with your credentials found in the Gridlastic dashboard
	 String VIDEO_URL = "";
	 String HUB = "https://USERNAME:ACCESS_KEY@HUB_SUBDOMAIN.gridlastic.com/wd/hub";
	 // PREPARE TEST BY DOWNLOADING https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png 
	 
	 public void setUp() throws Exception {
	     DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
	     capabilities.setCapability("version", "11");
	     capabilities.setCapability("platform", Platform.WIN10);
	     capabilities.setCapability("platformName", "windows");
	     capabilities.setCapability("video", "True");
	     capabilities.setCapability("ie.fileUploadDialogTimeout", 10000); // First time open of the internet explorer file upload dialog box is slow.
	     
	     driver = new RemoteWebDriver(
	        new URL(HUB),
	        capabilities);
	     driver.setFileDetector(new LocalFileDetector()); // Files will be uploaded from local machine via the selenium grid.
	     driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	     System.out.println("GRIDLASTIC VIDEO URL: " + VIDEO_URL + ((RemoteWebDriver) driver).getSessionId()); 
	 }

	 public void test_google_image_search() throws Exception {
			driver.get("https://www.google.com/ncr");
			driver.findElement(By.linkText("Images")).click();
			driver.findElementByCssSelector("[aria-label='Search by image']").click();;
			driver.findElement(By.linkText("Upload an image")).click();
			Thread.sleep(3000); // slow down for demo purposes
			WebElement inputFilePath = driver.findElement(By.id("qbfile"));
			File uploadedFile = new File("C:/Users/homework/Desktop/googlelogo_color_272x92dp.png"); // local machine file location
			inputFilePath.sendKeys(uploadedFile.getAbsolutePath());	
			Thread.sleep(10000); // slow down for demo purposes
	}

	 public void tearDown() throws Exception {
	     driver.quit();
	 }
	}

