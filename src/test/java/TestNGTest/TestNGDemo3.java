package TestNGTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestNGDemo3 {
    WebDriver driver;

   
	@BeforeTest
    public void setupTest() {
      
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
       
        
    
    }

    @Test
    public void googleSearch() {
        driver.get("https://www.paruvendu.fr/depose-annonce-gratuite");
        JavascriptExecutor js =(JavascriptExecutor)driver;
        js.executeScript("cmp_pv.cookie.saveConsent(true);");
		 driver.navigate().refresh();
       // driver.findElement(By.name("q")).sendKeys("Automation step by step");
        
        driver.findElement(By.partialLinkText("Déposer une annonce gratuite")).click();
        
        //driver.findElement(By.name("btnK")).click(); 
        WebElement dropdown = driver.findElement(By.cssSelector("div.select"));
		 dropdown.click();

		  WebElement autoMotoBateau = driver.findElement(By.cssSelector("li[data-value='V']"));
		  autoMotoBateau.click();
		  
		  WebElement voitureOccasion = driver.findElement(By.cssSelector("li[data-value='VVO00000']"));
		  voitureOccasion.click();
		
		  Select categ = new Select(driver.findElement(By.id("choixFamille")));
		  categ.selectByVisibleText("Location");
		  
		  Select categ1 = new Select(driver.findElement(By.id("categorie")));
		  categ1.selectByVisibleText("Voiture");
		  driver.findElement(By.id("descBien")).sendKeys("Voiture a louer pas cher et on bonne état! ");

		  Select diffusion = new Select(driver.findElement(By.id("nbrSemainesPublication")));
		  diffusion .selectByVisibleText("Pendant 6 mois");
		  driver.findElement(By.id("prix")).sendKeys("25");
		  driver.findElement(By.id("codePostal")).sendKeys("75013");
		  driver.findElement(By.partialLinkText("Suivant")).click();
    }

    @AfterTest
    public void tearDownTest() {
    	
       //driver.close();
      // driver.quit();
        System.out.println("Test completed successfully");
    }
}
