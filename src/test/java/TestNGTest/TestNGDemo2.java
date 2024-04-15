package TestNGTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestNGDemo2 {
    WebDriver driver;

    @BeforeTest
    public void setupTest() {
      
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    
    }

    @Test
    public void googleSearch4() {
        driver.get("https://google.com");
        driver.findElement(By.name("q")).sendKeys("Automation step by step");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("btnK")));
        searchButton.click();
      
    }

    @AfterTest
    public void tearDownTest() {
    	
       //driver.close();
      // driver.quit();
        System.out.println("Test completed successfully");
    }
}
