package geodisclientdatacsv;

import org.testng.annotations.Test;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import geodisclientdatacsv.Testlistener;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Listeners(Testlistener.class)
public class ClientData {
	//public ChromeDriver driver;
    WebDriver driver;
    static String RequiredString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder s = new StringBuilder(n);
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

    
    String randomVerification = formattedDate + " " + randomNumber + " " + RequiredString(6);
    String random =  randomNumber + " " + RequiredString(6);
    String randomRecherche = formattedDate + " " + randomNumber + " " + RequiredString(3);

    @BeforeClass
    public void setup() {
        // Configurez le chemin vers le driver de votre navigateur
        System.setProperty("webdriver.chrome.driver", "C:/Users/DEV01/eclipse-workspace/ClientFichierCSV/src/test/java/geodisclientdatacsv/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        //driver = initializeDriver();
    }
    
    
  /*private  ChromeDriver initializeDriver() {
  WebDriverManager.chromedriver().driverVersion("124").setup();
       driver = new ChromeDriver();
        return driver;
    }*/

    @DataProvider(name = "clientDataFromCSV")
    public Object[][] readClientDataFromCSV() throws IOException, CsvValidationException {
        List<String[]> clientDataList = new ArrayList<>();
        System.out.println("Reading data from the CSV file...");
        int lineCount = 0;
        String csvFilePath = "C:/Users/DEV01/eclipse-workspace/ClientCloudsysDataCSV/src/test/resources/clientsdata1.csv"; // Remplacez cela par votre chemin réel

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(csvFilePath)))) {
            String[] values;
            csvReader.readNext(); // Skip the header
            while ((values = csvReader.readNext()) != null) {
                // Vérifier si la ligne contient des données
                if (values.length > 0 && !values[0].isEmpty()) {
                    lineCount++;
                    clientDataList.add(values);
                    // Imprimer chaque ligne lue pour vérification avec le numéro de ligne
                    System.out.println("Line " + lineCount + " read from CSV: " + String.join(", ", values));
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            throw e;
        }
        System.out.println("Reading CSV data complete. Number of lines read: " + lineCount);
        return clientDataList.toArray(new Object[0][]);
    }

    @Test(dataProvider = "clientDataFromCSV")
    public void testClientData(String nom, String prenom, String numeroTel1, String numeroTel2) {
        System.out.println("Starting the test with the data:");
        //System.out.println("Name: " + nom + ", Forename: " + prenom + ", Tel 1 Number: " + numeroTel1 + ", Tel 2 Number: " + numeroTel2);

        // Naviguer vers l'URL et se connecter
        driver.get("http://92.205.22.177:8080/apex/f?p=112:LOGIN_DESKTOP::::::");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("P101_USERNAME")));

        WebElement usernameField = driver.findElement(By.id("P101_USERNAME"));
        usernameField.sendKeys("Molka12");

        WebElement passwordField = driver.findElement(By.id("P101_PASSWORD"));
        passwordField.sendKeys("0000");

        driver.findElement(By.id("P101_LOGIN")).click();
        System.out.println("Identifiants de connexion saisis avec succès.");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ouvrir le menu de navigation
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[@class='a-TreeView-toggle'])[2]"))).click();
        WebElement mesClientsLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Mes clients")));
        mesClientsLink.click();
        System.out.println("Navigation ! ");

        // Créer un nouveau client
        wait.until(ExpectedConditions.elementToBeClickable(By.id("B166764947412786522"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("P37_TITRE_CLIENT"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='P37_TITRE_CLIENT']/option[contains(text(),'Ste')]"))).click();

        // Remplir les champs du formulaire avec les données du CSV
        driver.findElement(By.id("P37_NOM_CLIENT")).sendKeys(nom +randomVerification);
        driver.findElement(By.id("P37_MATRICULE_FISCALE")).sendKeys("03214");
        driver.findElement(By.id("P37_ADRESSE_1")).sendKeys("TUNIS");
        driver.findElement(By.id("P37_ADRESSE_2")).sendKeys("Résidence zohra,cité khadhra");
        driver.findElement(By.id("P37_RIB_BANCAIRE")).sendKeys("123456789789");
        driver.findElement(By.id("P37_MOBILE1")).sendKeys(numeroTel1);
        driver.findElement(By.id("P37_MOBILE2")).sendKeys(numeroTel2);
        driver.findElement(By.id("P37_TELEPHONE")).sendKeys(numeroTel2);
        driver.findElement(By.id("P37_BANQUE")).sendKeys("BIAT");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Cliquer sur le bouton créer
        driver.findElement(By.id("B166757748140786496")).click();

        System.out.println("Create Client... ");
        System.out.println("Client Name: " + nom);
        System.out.println("Register: 03214");
        System.out.println("Address : TUNIS");
        System.out.println("Mobile: " + numeroTel1);
        System.out.println("Telephone: " + numeroTel2);
        System.out.println("Bank: BIAT");
        System.out.println("Banker Rib: 123456789789");
        
        System.out.println("Client Created !  ");    
        
        String searchFieldId = "R166763848610786519_search_field";
        String searchButtonId = "R166763848610786519_search_button";
        
        
        driver.findElement(By.id(searchFieldId)).sendKeys(randomVerification);
        
      
        driver.findElement(By.id(searchButtonId)).click(); 
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       

    }
 

    @AfterClass
    public void teardown() {
        if (driver != null) {
            //driver.quit();
        }
    }
}
