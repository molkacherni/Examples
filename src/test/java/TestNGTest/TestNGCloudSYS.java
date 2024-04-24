package TestNGTest;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;


public class TestNGCloudSYS{
    WebDriver driver;
    static String randomEmployeeInfo = generateRandomEmployeeInfo();
    static {
        randomEmployeeInfo = generateRandomEmployeeInfo();
    }
    
    @BeforeTest
    public void setupTest() {
    	System.out.println(" the random string is : " + randomEmployeeInfo);
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test(priority = 1)
    public void testConnexion() {
        String timestamp = generateTimestamp();
        System.out.println("Test Connexion: " + timestamp);
        ConnexionTest connexionTest = new ConnexionTest(driver);
        connexionTest.execute();
    }

    @Test(priority = 2)
    public void testNavigationMenu() {
        String timestamp = generateTimestamp();
        System.out.println("Test Navigation Menu: " + timestamp);
        NavigationMenuTest navigationMenuTest = new NavigationMenuTest(driver);
        navigationMenuTest.execute();
    }

    @Test(priority = 3)
    public void testCreerClient() {
        String timestamp = generateTimestamp();
        System.out.println("Test Creer Client: " + timestamp);
        CreerClientTest creerClientTest = new CreerClientTest(driver, randomEmployeeInfo);
        creerClientTest.execute();
    }

    @Test(priority = 4)
    public void testRechercheClient() {
        String timestamp = generateTimestamp();
        System.out.println("Test Recherche Client: " + timestamp);
        String randomVerification = randomEmployeeInfo; // Replace with your random verification string
        RechercheClientTest rechercheClientTest = new RechercheClientTest(driver, randomEmployeeInfo);
        rechercheClientTest.execute("CHERNI MOLKA", randomVerification);

        boolean isClientFound = driver.getPageSource().contains(randomEmployeeInfo);
        Assert.assertTrue(isClientFound, "Le client recherché n'est pas affiché sur la page.");
    }

    @Test(priority = 5)
    public void testUsernameFieldVisibility() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("t_Body_content")));
        Assert.assertTrue(usernameField.isDisplayed(), "Un nouveau client déjà créé.");
    }

    @AfterTest
    public void tearDownTest() {
        // driver.quit();
        System.out.println("Test completed successfully");
    }

    private String generateTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
        return dateFormat.format(new Date());
    }

    private static String generateRandomEmployeeInfo() {
        String[] employeeNames = {"CHERNI MOLKA"};
        Random rand = new Random();
        String randomEmployeeName = employeeNames[rand.nextInt(employeeNames.length)];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        return randomEmployeeName + "_" + timestamp;
    }
}

class ConnexionTest{
    private WebDriver driver;

    public ConnexionTest(WebDriver driver) {
        this.driver = driver;
    }

    public void execute() {
        System.out.println("Executing Connexion Test...");
        driver.get("http://92.205.22.177:8080/apex/f?p=112:LOGIN_DESKTOP::::::");
        WebElement usernameInput = waitForElement(By.id("P101_USERNAME"));
        usernameInput.sendKeys("Molka12");
      
        WebElement passwordInput = waitForElement(By.id("P101_PASSWORD"));
        passwordInput.sendKeys("0000");
        WebElement loginButton = waitForElement(By.id("P101_LOGIN"));
        loginButton.click();
    }

    private WebElement waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}

class NavigationMenuTest {
    private WebDriver driver;

    public NavigationMenuTest(WebDriver driver) {
        this.driver = driver;
    }

    public void execute() {
        System.out.println("Executing Navigation Menu Test...");
        WebElement gestionDesTiersToggle = waitForElement(By.xpath("(//span[@class='a-TreeView-toggle'])[2]"));
        gestionDesTiersToggle.click();
        driver.findElement(By.partialLinkText("Mes clients")).click();
    }

    private WebElement waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}

class CreerClientTest {
    private WebDriver driver;
    private String employeeInfo;

    public CreerClientTest(WebDriver driver, String employeeInfo) {
        this.driver = driver;
        this.employeeInfo = employeeInfo;
    }

    public void execute() {
        System.out.println("Executing Creer Client Test...");
        WebElement creerButton = waitForElement(By.id("B166764947412786522"));
        creerButton.click();
        Select selectList = new Select(driver.findElement(By.id("P37_TITRE_CLIENT")));
        selectList.selectByVisibleText("Madame");
        driver.findElement(By.id("P37_NOM_CLIENT")).sendKeys(employeeInfo);
        driver.findElement(By.id("P37_MATRICULE_FISCALE")).sendKeys("03214");
        driver.findElement(By.id("P37_ADRESSE_1")).sendKeys("TUNIS ");
        driver.findElement(By.id("P37_ADRESSE_2")).sendKeys("Résidence zohra,cité khadhra");
        driver.findElement(By.id("P37_RIB_BANCAIRE")).sendKeys("123456789789");
        driver.findElement(By.id("P37_MOBILE1")).sendKeys("56305396");
        driver.findElement(By.id("P37_TELEPHONE")).sendKeys("70123456");
        driver.findElement(By.id("P37_BANQUE")).sendKeys("BIAT");
        WebElement creerButton1 = driver.findElement(By.id("B166757748140786496"));
        creerButton1.click();
    }

    private WebElement waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}

class RechercheClientTest {
    private WebDriver driver;
    private String rechercheNomClient;

    public RechercheClientTest(WebDriver driver, String rechercheNomClient) {
        this.driver = driver;
        this.rechercheNomClient = rechercheNomClient;
    }

    public void execute(String nomClient, String randomVerification) {
        System.out.println("Executing Recherche Client Test...");
        WebElement searchBar = waitForElement(By.id("R166763848610786519_search_field"));
        searchBar.sendKeys(rechercheNomClient);
        searchBar.sendKeys(Keys.RETURN);
        
      
        int maxRetries = 3;
        int retryCount = 0;
        boolean isElementDisplayed = false;

    
        while (retryCount < maxRetries && !isElementDisplayed) {
            WebDriverWait wait = new WebDriverWait(driver, 10); 
            WebElement tdElement = wait.ignoring(StaleElementReferenceException.class)
                    .until(driver -> {
                        WebElement element = driver.findElement(By.xpath("//table[@id='166764050341786521']//td[contains(text(), '" + rechercheNomClient + "')]"));
                        if (element.isDisplayed()) {
                            return element;
                        } else {
                            return null;
                        }
                    });



          
            if (tdElement != null) {
                isElementDisplayed = true;
                System.out.println("Client créé.");
            } else {
                System.out.println("Aucun résultat trouvé");
                retryCount++;
            }
        }

     
        Assert.assertTrue(isElementDisplayed, "Le nom du client recherché est pas présent dans le tableau.");

    }


    private WebElement waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
} 
