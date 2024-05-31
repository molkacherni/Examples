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
	    private String dbRFQType;
	    private String dbTransportmode;
	    private String dbTenderManager;
	    private String dbCRMtenderID;
	    private String dbRFQDescription;
	    private String dbCustomer;
	    private String dbVerticalMarket;
	    
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
	            		 "SELECT CODE_VARIABLE, LIB_VARIABLE,VALEUR_VAR FROM DTM_V_DATA WHERE CODE_VARIABLE = 'VAR_1' and ID_TEST_STEP= '61'",               
	            		 "SELECT  CODE_VARIABLE, LIB_VARIABLE,VALEUR_VAR FROM DTM_V_DATA WHERE CODE_VARIABLE = 'VAR_2' and ID_TEST_STEP= '61'",
	                     "SELECT  CODE_VARIABLE, LIB_VARIABLE,VALEUR_VAR FROM DTM_V_DATA WHERE CODE_VARIABLE = 'VAR_3' and ID_TEST_STEP= '61'",
	                     "SELECT  CODE_VARIABLE, LIB_VARIABLE,VALEUR_VAR FROM DTM_V_DATA WHERE CODE_VARIABLE = 'VAR_1'and ID_TEST_STEP= '62' ",
	                     "SELECT  CODE_VARIABLE, LIB_VARIABLE,VALEUR_VAR FROM DTM_V_DATA WHERE CODE_VARIABLE = 'VAR_2' and ID_TEST_STEP= '62' ",
	                     "SELECT  CODE_VARIABLE, LIB_VARIABLE,VALEUR_VAR FROM DTM_V_DATA WHERE CODE_VARIABLE = 'VAR_3' and ID_TEST_STEP= '62' ",
	                     "SELECT  CODE_VARIABLE, LIB_VARIABLE,VALEUR_VAR FROM DTM_V_DATA WHERE CODE_VARIABLE = 'VAR_4' and ID_TEST_STEP= '62' ",
	                     "SELECT  CODE_VARIABLE, LIB_VARIABLE,VALEUR_VAR FROM DTM_V_DATA WHERE CODE_VARIABLE = 'VAR_5'and ID_TEST_STEP= '62' ",
	                     "SELECT  CODE_VARIABLE, LIB_VARIABLE,VALEUR_VAR FROM DTM_V_DATA WHERE CODE_VARIABLE = 'VAR_6' and ID_TEST_STEP= '62'",
	                     "SELECT  CODE_VARIABLE, LIB_VARIABLE, VALEUR_VAR FROM DTM_V_DATA WHERE CODE_VARIABLE = 'VAR_7' and ID_TEST_STEP= '62' "
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
	             	    	dbRFQType = resultSet.getString("VALEUR_VAR");
	             	      
	             	        String codeVariable = resultSet.getString("CODE_VARIABLE");
	             	        String libVariable = resultSet.getString("LIB_VARIABLE");
	             	        String valeurVar = resultSet.getString("VALEUR_VAR");
	             	   
	             	    // Ajoutez l'affichage ici
	             	    System.out.println( codeVariable + ":" + libVariable+  ":"  + valeurVar );
	             	} }catch (SQLException e) {
	             	    error += "Erreur SQL : " + e.getMessage() + "\n";
	             	}
	           try (PreparedStatement statement = conn.prepareStatement(queries[4]);
	             	     ResultSet resultSet = statement.executeQuery()) {
	             	    if (resultSet.next()) {
	             	    	dbTransportmode = resultSet.getString("VALEUR_VAR");
	             	      
	             	        String codeVariable = resultSet.getString("CODE_VARIABLE");
	             	        String libVariable = resultSet.getString("LIB_VARIABLE");
	             	        String valeurVar = resultSet.getString("VALEUR_VAR");
	             	   
	             	    // Ajoutez l'affichage ici
	             	    System.out.println( codeVariable + ":" + libVariable+  ":"  + valeurVar );
	             	} }catch (SQLException e) {
	             	    error += "Erreur SQL : " + e.getMessage() + "\n";
	             	}
	           try (PreparedStatement statement = conn.prepareStatement(queries[5]);
	            	     ResultSet resultSet = statement.executeQuery()) {
	            	    if (resultSet.next()) {
	            	    	dbTenderManager = resultSet.getString("VALEUR_VAR");
	            	      
	            	        String codeVariable = resultSet.getString("CODE_VARIABLE");
	            	        String libVariable = resultSet.getString("LIB_VARIABLE");
	            	        String valeurVar = resultSet.getString("VALEUR_VAR");
	            	   
	            	    // Ajoutez l'affichage ici
	            	    System.out.println( codeVariable + ":" + libVariable+  ":"  + valeurVar );
	            	} }catch (SQLException e) {
	            	    error += "Erreur SQL : " + e.getMessage() + "\n";
	            	}
	           try (PreparedStatement statement = conn.prepareStatement(queries[6]);
	            	     ResultSet resultSet = statement.executeQuery()) {
	            	    if (resultSet.next()) {
	            	    	dbCRMtenderID = resultSet.getString("VALEUR_VAR");
	            	      
	            	        String codeVariable = resultSet.getString("CODE_VARIABLE");
	            	        String libVariable = resultSet.getString("LIB_VARIABLE");
	            	        String valeurVar = resultSet.getString("VALEUR_VAR");
	            	   
	            	    // Ajoutez l'affichage ici
	            	    System.out.println( codeVariable + ":" + libVariable+  ":"  + valeurVar );
	            	} }catch (SQLException e) {
	            	    error += "Erreur SQL : " + e.getMessage() + "\n";
	            	}
	           try (PreparedStatement statement = conn.prepareStatement(queries[7]);
	            	     ResultSet resultSet = statement.executeQuery()) {
	            	    if (resultSet.next()) {
	            	    	dbRFQDescription = resultSet.getString("VALEUR_VAR");
	            	      
	            	        String codeVariable = resultSet.getString("CODE_VARIABLE");
	            	        String libVariable = resultSet.getString("LIB_VARIABLE");
	            	        String valeurVar = resultSet.getString("VALEUR_VAR");
	            	   
	            	    // Ajoutez l'affichage ici
	            	    System.out.println( codeVariable + ":" + libVariable+  ":"  + valeurVar  );
	            	} }catch (SQLException e) {
	            	    error += "Erreur SQL : " + e.getMessage() + "\n";
	            	}
	           try (PreparedStatement statement = conn.prepareStatement(queries[8]);
	            	     ResultSet resultSet = statement.executeQuery()) {
	            	    if (resultSet.next()) {
	            	    	dbCustomer = resultSet.getString("VALEUR_VAR");
	            	      
	            	        String codeVariable = resultSet.getString("CODE_VARIABLE");
	            	        String libVariable = resultSet.getString("LIB_VARIABLE");
	            	        String valeurVar = resultSet.getString("VALEUR_VAR");
	            	   
	            	    // Ajoutez l'affichage ici
	            	    System.out.println( codeVariable + ":" + libVariable+  ":"  + valeurVar );
	            	} }catch (SQLException e) {
	            	    error += "Erreur SQL : " + e.getMessage() + "\n";
	            	}
	          try (PreparedStatement statement = conn.prepareStatement(queries[9]);
	           	     ResultSet resultSet = statement.executeQuery()) {
	           	    if (resultSet.next()) {
	           	    	dbVerticalMarket = resultSet.getString("VALEUR_VAR");
	           	      
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
	    public void TestCreateTender() {
	    	
	    	System.out.println("Create Tender ...");
	    	WebDriverWait wait = new WebDriverWait(driver, 30);

	        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".k-button-solid-primary:nth-child(1) > .k-button-text"))).click();
	        
	        wait.until(ExpectedConditions.elementToBeClickable(By.id(":ra:-accessibility-id"))).click();
	        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".k-list-item-text"))).click();

	        System.out.println("Selected RFQ Type option :" +dbRFQType);

	        wait.until(ExpectedConditions.elementToBeClickable(By.id(":rb:-accessibility-id"))).click();
	        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#option-\\3Arb\\3A-guid-2 > span"))).click();
	        System.out.println("Selected Transport mode option :" +dbTransportmode);
	        
	        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#\\3Arc\\3A-accessibility-id > .k-input-value-text"))).click();
	        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#option-\\3Arc\\3A-guid-0 > span"))).click();
	        System.out.println("Selected Tender Manager option :"+dbTenderManager);
	        
	        WebElement rdField = wait.until(ExpectedConditions.elementToBeClickable(By.id(":rd:")));
	        rdField.click();
	        rdField.sendKeys(dbCRMtenderID);
	        
	        System.out.println("Entered CRM tender ID :"+dbCRMtenderID);
	        
	        WebElement rfqDescriptionField = wait.until(ExpectedConditions.elementToBeClickable(By.id("rfqDescription")));
	        rfqDescriptionField.click();
	        rfqDescriptionField.sendKeys(dbRFQDescription+randomTest);
	        
	        System.out.println("Entered RFQ Description :"+dbRFQDescription );

	        WebElement rfField = wait.until(ExpectedConditions.elementToBeClickable(By.id(":rf:")));
	        rfField.click();
	        rfField.sendKeys(dbCustomer);
	        
	        System.out.println("Entered Customer :"+dbCustomer);
	        
	        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".geo-input:nth-child(8) .k-icon"))).click();

	          js.executeScript("window.scrollTo(0,0)");

	          wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li:nth-child(8) > span"))).click();

	         wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#\\3Art\\3A-cell-uid1720422000000 > .k-link"))).click();
	        System.out.println("Entered Customer Deadline: 06/06/2024");

	        
	        wait.until(ExpectedConditions.elementToBeClickable(By.id(":rh:-accessibility-id"))).click();
	        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#option-\\3Arh\\3A-guid-4 > .k-list-item-text"))).click();
	        System.out.println("Selected Vertical Market option :" +dbVerticalMarket);
	        
	        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".geo-input:nth-child(11) .k-icon"))).click();
            js.executeScript("window.scrollTo(0,0)");
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#\\3Aru\\3A-cell-uid1716966000000 > .k-link"))).click();
	        System.out.println("Entered Valid date from : 05/29/2024");
	        
	        
	       wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".geo-input:nth-child(12) .k-icon"))).click();
           js.executeScript("window.scrollTo(0,0)");
           wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li:nth-child(7) > span"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#\\3Arv\\3A-cell-uid1719730800000 > .k-link"))).click();
	       System.out.println("Entered Valid date  to: 06/30/2024");
	       
	       
	       wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".geo-input:nth-child(14) .k-icon"))).click();
	       js.executeScript("window.scrollTo(0,0)");
	       wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li:nth-child(7) > span"))).click();
           wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#\\3Ar10\\3A-cell-uid1719298800000 > .k-link"))).click();
	        System.out.println("Entered Internal deadline: 06/25/2024");
	        
	        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("save")));
	        saveButton.click();

	        System.out.println("Tender Created!");
	        try {
	            Thread.sleep(3000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        WebElement BackButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@role='button' and contains(@class, 'k-button-outline-primary') and contains(@class, 'k-rounded-md')]")));
	        BackButton.click();
	        
	        try {
	            Thread.sleep(10000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        String generatedId = (String) ((JavascriptExecutor) driver).executeScript("return document.querySelector('h1').innerText.trim();");

	        // Vérifier si l'ID généré est vide
	        if (!generatedId.isEmpty()) {
	            // Afficher le contenu de l'ID dans la console
	            System.out.println("Generated ID: " + generatedId);
	        } else {
	            // Afficher un message si l'ID généré est vide
	            System.out.println("Le contenu de l'ID généré est vide ou non trouvé.");
	        }
	        
	    }  
	    @AfterTest
	    public void tearDown() {
	      
	        // driver.quit();
	    }
}
