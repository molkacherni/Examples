package geodisTenderCreate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.plugins.di.Keys;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import geodisTenderCreate.Testlistener;
@Listeners(Testlistener.class)
public class TestcaseGeodisCreateTender {
	//public ChromeDriver driver;
		ChromeDriver driver = null;
		
	    WebDriverWait wait;
	    JavascriptExecutor js;
	  

	    // Variables de classe pour stocker les valeurs des variables
	    private String dbUrl;
	    private String dbUsername;
	    private String dbPassword;
	    private String dbId;
	
	    
	    static String RequiredString(int n) {
	        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
	                + "abcdefghijklmnopqrstuvxyz";
	        StringBuilder s = new StringBuilder(n)
;
	        int y;
	        for (y = 0; y < n; y++) {
	            int index = (int) (AlphaNumericString.length() * Math.random());
	            s.append(AlphaNumericString.charAt(index));
	        }
	        return s.toString();
	    }

	    int upper = 1000;
	    int lower = 100;
	    int randomNumber = (int) (Math.random() * (upper - lower)) + lower;

	    LocalDateTime myDateObj = LocalDateTime.now();
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM HH:mm:ss");
	    String formattedDate = myDateObj.format(myFormatObj);

	    String randomTest = formattedDate + " " + randomNumber + " " + RequiredString(3);
	    @BeforeTest
	    public void Configure() throws SQLException {
	    	
	        System.out.println("Test started!");
	     
	        System.setProperty("webdriver.chrome.driver", "C:/Users/DEV01/eclipse-workspace/GEODIS-TENDERLCLCREATIONSTEP1/src/test/java/geodisTenderCreate/chromedriver.exe");
	        driver = new ChromeDriver();
	        // driver = initializeDriver(); 

	        js = (JavascriptExecutor) driver;
	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	        wait = new WebDriverWait(driver, 20);

	        // Récupérer toutes les variables de la base de données
	        getAllVariablesFromDatabase();
	       
	    }
	   /* private ChromeDriver initializeDriver() {
		      
	   	    WebDriverManager.chromedriver().driverVersion("125").setup();
	           driver = new ChromeDriver();
	           return driver;
	    }  */   
	    @Test(priority = 1)
	    public void testDatabaseConnectionAndLogin() throws SQLException {
	    	
	    
	    	
	        System.out.println("Attempting to connect to the Oracle database...");

	        if (dbUrl != null && dbUsername != null && dbPassword != null) {
	            System.out.println("Successfully Retrieved Login Information !");

	            try {
	                testConnexion(dbUrl, dbUsername, dbPassword);
	            } finally {
	               
	            }
	        } else {
	            System.out.println("Failed to retrieve necessary values from the database.");
	        }
	    }

	    private void getAllVariablesFromDatabase() throws SQLException {
	        String DB_URL = "jdbc:oracle:thin:@92.205.22.177:1521:XE";
	        String USER = "ERP";
	        String PASS = "ERP";
	        String error = "";

	        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
	            String[] queries = {
	            		 "SELECT CODE_VARIABLE, LIB_VARIABLE,VALEUR_VAR FROM DTM_V_DATA WHERE CODE_VARIABLE = 'VAR_1' and ID_TEST_STEP= '63'",               
	            		 "SELECT  CODE_VARIABLE, LIB_VARIABLE,VALEUR_VAR FROM DTM_V_DATA WHERE CODE_VARIABLE = 'VAR_2' and ID_TEST_STEP= '63'",
	                     "SELECT  CODE_VARIABLE, LIB_VARIABLE,VALEUR_VAR FROM DTM_V_DATA WHERE CODE_VARIABLE = 'VAR_3' and ID_TEST_STEP= '63'",
	                     "SELECT  CODE_VARIABLE, LIB_VARIABLE,VALEUR_VAR FROM DTM_V_DATA WHERE CODE_VARIABLE = 'VAR_1'and ID_TEST_STEP= '64' "
	             
	            };

	            try (PreparedStatement statement = conn.prepareStatement(queries[0]);
	           	     ResultSet resultSet = statement.executeQuery()) {
	           	    if (resultSet.next()) {
	           	        dbUrl = resultSet.getString("VALEUR_VAR");
	           	      
	           	        String codeVariable = resultSet.getString("CODE_VARIABLE");
	           	        String libVariable = resultSet.getString("LIB_VARIABLE");
	           	        String valeurVar = resultSet.getString("VALEUR_VAR");
	           	   
	           	    // Ajoutez l'affichage ici
	           	    System.out.println( codeVariable + ":" + libVariable+  ":"  + valeurVar );
	           	} }catch (SQLException e) {
	           	    error += "Erreur SQL : " + e.getMessage() + "\n";
	           	}
	            try (PreparedStatement statement = conn.prepareStatement(queries[1]);
	              	     ResultSet resultSet = statement.executeQuery()) {
	              	    if (resultSet.next()) {
	              	        dbUsername = resultSet.getString("VALEUR_VAR");
	              	      
	              	        String codeVariable = resultSet.getString("CODE_VARIABLE");
	              	        String libVariable = resultSet.getString("LIB_VARIABLE");
	              	        String valeurVar = resultSet.getString("VALEUR_VAR");
	              	   
	              	    // Ajoutez l'affichage ici
	              	    System.out.println( codeVariable + ":" + libVariable+  ":"  + valeurVar );
	              	} }catch (SQLException e) {
	              	    error += "Erreur SQL : " + e.getMessage() + "\n";
	              	}
	            
	            try (PreparedStatement statement = conn.prepareStatement(queries[2]);
	              	     ResultSet resultSet = statement.executeQuery()) {
	              	    if (resultSet.next()) {
	              	        dbPassword = resultSet.getString("VALEUR_VAR");
	              	      
	              	        String codeVariable = resultSet.getString("CODE_VARIABLE");
	              	        String libVariable = resultSet.getString("LIB_VARIABLE");
	              	        String valeurVar = resultSet.getString("VALEUR_VAR");
	              	   
	              	    // Ajoutez l'affichage ici
	              	    System.out.println( codeVariable + ":" + libVariable+  ":"  + valeurVar );
	              	} }catch (SQLException e) {
	              	    error += "Erreur SQL : " + e.getMessage() + "\n";
	              	}
	           
	            try (PreparedStatement statement = conn.prepareStatement(queries[3]);
	             	     ResultSet resultSet = statement.executeQuery()) {
	             	    if (resultSet.next()) {
	             	    	dbId = resultSet.getString("VALEUR_VAR");
	             	      
	             	        String codeVariable = resultSet.getString("CODE_VARIABLE");
	             	        String libVariable = resultSet.getString("LIB_VARIABLE");
	             	        String valeurVar = resultSet.getString("VALEUR_VAR");
	             	   
	             	    // Ajoutez l'affichage ici
	             	    System.out.println( codeVariable + ":" + libVariable+  ":"  + valeurVar );
	             	} }catch (SQLException e) {
	             	    error += "Erreur SQL : " + e.getMessage() + "\n";
	             	}
	           
	           
	      }
	      
	        
	        if (!error.isEmpty()) {
	            System.out.println("Erreurs lors de la récupération des informations depuis la base de données :");
	            System.out.println(error);
	        }
	    }

	    private void testConnexion(String Url, String username, String password) {
	        driver.manage().window().maximize();
	        if (Url != null && username != null && password != null) {
	            driver.get(Url);

	            WebDriverWait wait = new WebDriverWait(driver, 30);
	            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(":r1:")));

	            WebElement usernameField = driver.findElement(By.id(":r1:"));
	            usernameField.sendKeys(username);

	            WebElement passwordField = driver.findElement(By.id(":r2:"));
	            passwordField.sendKeys(password);

	            driver.findElement(By.cssSelector(".k-button:nth-child(5) > .k-button-text")).click();
	           

	            System.out.println("Login credentials successfully entered.");

	            try {
	                Thread.sleep(2000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("Some login information is null. Unable to load the page.");
	        }
	    }
	   
	    @Test(priority = 2)
        public void TestSearchTender() {
	    	 System.out.println("Start search ...");
	    	 WebDriverWait wait = new WebDriverWait(driver, 10);
	    	
	    	 WebElement svgElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".k-filterable:nth-child(1) svg")));
	         svgElement.click();
	         Actions builder = new Actions(driver);
	         builder.moveToElement(svgElement).perform();
	         WebElement bodyElement = driver.findElement(By.tagName("body"));
	         builder.moveToElement(bodyElement, 0, 0).perform();

	         WebElement columnMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".k-columnmenu-item-wrapper:nth-child(2) > .k-columnmenu-item")));
	         columnMenuItem.click();
	         System.out.println("filter on the Reference column with id :" +dbId);

	         WebElement raElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(":ra:")));
	         raElement.click();
	         raElement.sendKeys(dbId);

	         WebElement primaryButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".k-button-solid-primary:nth-child(1) > .k-button-text:nth-child(1)")));
	         primaryButton.click();

	         WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("GBD-G-LCL-20240530-275")));
	         link.click();
	         System.out.println("Id found :" +dbId);
	    	 // driver.findElement(By.cssSelector(".tender-process-step-card:nth-child(5) .tender-process-step-card__actions")).click();
	    
	    }
	    
	   /* @Test(priority = 3)
	    public void TestSetupTender() throws InterruptedException {
	    	 System.out.println("Setup Tender Started ...");
	        WebDriverWait wait = new WebDriverWait(driver, 30); 
	        Actions act = new Actions(driver);
	        
	      
	        WebElement buttonByCss = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.k-button.k-button-sm.k-button-solid.k-button-solid-primary.k-rounded-md")));
	        buttonByCss.click();

	        WebElement uploadButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.k-button.k-button-md.k-button-solid.k-button-solid-base.k-rounded-md.k-upload-button")));
	        act.moveToElement(uploadButton).perform();

	        WebElement fileInput = driver.findElement(By.cssSelector("input[type='file']"));
	        fileInput.sendKeys("C:/Users/DEV01/Downloads/Template.xlsx");

	        wait.until(ExpectedConditions.attributeToBeNotEmpty(fileInput, "value"));
	        
	        System.out.println("The file has been uploaded successfully!");
	        
	        WebElement checkbox = driver.findElement(By.id(":rg:"));
            checkbox.click();
            System.out.println("Checkbox check file is successful!"); 
            Thread.sleep(2000);
            
            WebElement buttonNext = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div[2]/div[4]/div/div[1]/div[1]/div/button[2]")));
            buttonNext.click();
            WebElement checkbox2 = driver.findElement(By.id(":rj:"));
            checkbox2.click();
            Thread.sleep(2000);
            WebElement buttonNext2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='root']/div[2]/div[4]/div/div/div/div/button[3]/span[2]")));
            buttonNext2.click();
            Thread.sleep(2000);
            WebElement buttonNext3 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div[2]/div[4]/div/div[1]/div[1]/div/button[3]/span[2]")));
            buttonNext3.click();
            
            System.out.println("Setup Tender Created !");
	    }*/
 	  
	    @Test(priority = 3)
	    public void TestUpdateMapping() throws InterruptedException {
	    	 System.out.println("Update Mapping Started ...");
	    	 System.out.println("INFO ...");
	    	 WebElement buttonupdatemapping = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div[2]/div[2]/div[3]/div/div[2]/div/div[2]/div/div[2]/div/span[2]/a \n"
	    	 		+ "")));
	    	 buttonupdatemapping .click();
	    	 
	    	 WebElement sourceElement = driver.findElement(By.xpath("//td[contains(@class, 'kendo-cells left') and text()='Cust. Lane Id']"));
	         WebElement targetElement = driver.findElement(By.cssSelector("span.preview-values.success"));

	         Actions actions = new Actions(driver);
	         actions.dragAndDrop(sourceElement, targetElement).build().perform();
	         
	         System.out.println("Drag and drop of the Customer Lane ID  was successfully completed !");
	         
	         Thread.sleep(2000);
	         System.out.println("ORIGINE...");
	         WebElement sourceElement1 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//td[@class='kendo-cells' and @aria-colindex='3' and @data-grid-col-index='2' and .//span[@class='crs-preview-cell' and .//span[text()='RO'] and .//span[text()='US'] and .//span[text()='CN']]]")
	         ));

	         WebElement targetElement1 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//td[@aria-colindex='4' and @data-grid-col-index='3' and .//span[@class='preview-values success' and .//span[text()='No Val']]]")
	         ));
	         
	         Actions actions1 = new Actions(driver);

	         actions1.dragAndDrop(sourceElement1, targetElement1).build().perform();
	         System.out.println("Drag and drop of the Pick-up Country Code was successfully completed !");
	         
	         Thread.sleep(2000);
	         
	         WebElement sourceElement2 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//td[@class='kendo-cells' and @role='gridcell' and @aria-colindex='3' and @data-grid-col-index='2']//span[@class='value-preview isValue' and text()='4K SERVICES SRLHOREA']")
	         ));

	         WebElement targetElement2 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":re:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[7]/td[4]")
	         ));

	    	 Actions actions2 = new Actions(driver);
	         actions2.dragAndDrop(sourceElement2, targetElement2).build().perform();
	        
	         System.out.println("Drag and drop of the Pick-up Additional Information Code was successfully completed !");
	         
	         Thread.sleep(2000);
	         
	         
	         WebElement sourceElement3 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":rf:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[5]/td[3]")
	         ));
	         WebElement targetElement3 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":re:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[5]/td[4]")
	         ));

	         Actions actions3 = new Actions(driver);

	         actions3.dragAndDrop(sourceElement3, targetElement3).build().perform();
             System.out.println("Drag and drop of the Pick-up City Code was successfully completed !");
	         
	         Thread.sleep(2000); 
	         
	         
	         
	         WebElement sourceElement4 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":rf:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[4]/td[3]")
	         ));
	         WebElement targetElement4 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":re:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[6]/td[4]")
	         ));

	         Actions actions4 = new Actions(driver);

	         actions4.dragAndDrop(sourceElement4, targetElement4).build().perform();
             System.out.println("Drag and drop of the Pick-up Zip Code was successfully completed!");
	         
	         Thread.sleep(2000); 
	         
	         
	         WebElement sourceElement5 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":rf:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[9]/td[3]")
	         ));
	         WebElement targetElement5 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":re:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[8]/td[4]")
	         ));

	         Actions actions5 = new Actions(driver);

	         actions5.dragAndDrop(sourceElement5, targetElement5).build().perform();
             System.out.println("Drag and drop of the Customer POL Code was successfully completed!");
	         
	         Thread.sleep(2000); 
	         
	         System.out.println("DESTINATION ...");
	         
	         WebElement sourceElement6 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":rf:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[11]/td[3]")
	         ));
	         WebElement targetElement6 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":re:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[10]/td[4]")
	         ));

	         Actions actions6 = new Actions(driver);

	         actions6.dragAndDrop(sourceElement6, targetElement6).build().perform();
             System.out.println("Drag and drop of the Delivery Country  was successfully completed!");
	         
	         Thread.sleep(2000); 
	         

	         WebElement sourceElement7 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":rf:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[16]/td[3]")
	         ));
	         WebElement targetElement7 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":re:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[11]/td[4]")
	         ));

	         Actions actions7 = new Actions(driver);

	         actions7.dragAndDrop(sourceElement7, targetElement7).build().perform();
             System.out.println("Drag and drop of the Delivery City  was successfully completed!");
	         
	         Thread.sleep(2000); 
	         
	         WebElement sourceElement8 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":rf:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[15]/td[3]")
	         ));
	         WebElement targetElement8 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":re:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[12]/td[4]")
	         ));

	         Actions actions8 = new Actions(driver);

	         actions8.dragAndDrop(sourceElement8, targetElement8).build().perform();
             System.out.println("Drag and drop of the Delivery Zip Code  was successfully completed!");
	         
	         Thread.sleep(2000); 
	         WebElement sourceElement9 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":rf:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[17]/td[3]")
	         ));
	         WebElement targetElement9 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":re:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[13]/td[4]")
	         ));

	         Actions actions9 = new Actions(driver);

	         actions9.dragAndDrop(sourceElement9, targetElement9).build().perform();
             System.out.println("Drag and drop of the Delivery Additional Information  was successfully completed!");
	         
	         Thread.sleep(2000); 
	         
	         WebElement sourceElement10 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":rf:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[13]/td[3]")
	         ));
	         WebElement targetElement10 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":re:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[14]/td[4]")
	         ));

	         Actions actions10 = new Actions(driver);

	         actions10.dragAndDrop(sourceElement10, targetElement10).build().perform();
             System.out.println("Drag and drop of the Customer POD  was successfully completed!");
	         
	         Thread.sleep(2000); 
	         
	         System.out.println("ShipmentInfo ...");
	         
	         WebElement sourceElement11 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":rf:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[3]/td[3]")
	         ));
	         WebElement targetElement11 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":re:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[16]/td[4]")
	         ));

	         Actions actions11 = new Actions(driver);

	         actions11.dragAndDrop(sourceElement11, targetElement11).build().perform();
             System.out.println("Drag and drop of the Terms  was successfully completed!");
	         
	         Thread.sleep(2000); 
	         
	         
	         WebElement sourceElement12 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":rf:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[18]/td[3]")
	         ));
	         WebElement targetElement12 = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 By.xpath("//*[@id=\":re:-role-element-id\"]/div[2]/div/div[1]/table/tbody/tr[17]/td[4]")
	         ));

	         Actions actions12 = new Actions(driver);

	         actions12.dragAndDrop(sourceElement12, targetElement12).build().perform();
            System.out.println("Drag and drop of the Hazardous Cargo Y/N  was successfully completed!");
	         
	         Thread.sleep(2000);
	         
	       WebElement buttonsave = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div[2]/div[1]/div/div[2]/button[1]")));
		    buttonsave .click();
		   WebElement ButtonGenerateIris = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div[2]/div[1]/div/div[2]/button[2]")));
		   ButtonGenerateIris.click();
	         
		   System.out.println("Generate Iris !");
	         
	    }
	    @AfterTest
	    public void tearDown() {
	      
	        // driver.quit();
	    }
}
