import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class seleniumtest2 {

	public static void main(String[] args) {
		
		
		
		WebDriverManager.chromedriver().setup();
		 WebDriver driver = new ChromeDriver();
		 JavascriptExecutor js =(JavascriptExecutor)driver;
		 driver.get("https://www.paruvendu.fr/voiture-occasion/");
		 js.executeScript("cmp_pv.cookie.saveConsent(true);");
		 driver.navigate().refresh();
		 driver.findElement(By.partialLinkText("Déposer une annonce gratuite")).click();
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
		  
		  String cds = driver.getPageSource();
		  String valeurAttendue = "Vos coordonnées de contact";
		  if (cds.contains(valeurAttendue)) {
			  System.out.println("C'est Ok");
		  } else 
		  {
			  System.out.println("C'est Ko");
		  }

	}

}
