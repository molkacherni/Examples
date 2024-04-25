package TestNGTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestNGCloudSYS {
    WebDriver driver;
    Random random = new Random();

    private String generateTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        int randomNumber = random.nextInt(1000);
        return timestamp + "_" + randomNumber;
    }

    @BeforeTest
    public void setupTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test(priority=1)
    public void testConnexion() throws InterruptedException {
        String timestamp = generateTimestamp();
        System.out.println(" testConnexion: " + timestamp);
        ConnexionTest connexionTest = new ConnexionTest(driver);
        connexionTest.execute();
    }

    @Test(priority=2)
    public void testNavigationMenu() {
        String timestamp = generateTimestamp();
        System.out.println(" testNavigationMenu: " + timestamp);
        NavigationMenuTest navigationMenuTest = new NavigationMenuTest(driver);
        navigationMenuTest.execute();
    }

    @Test(priority=3)
    public void testCreerClient() {
        String timestamp = generateTimestamp();
        System.out.println(" testCreerClient: " + timestamp);
        CreerClientTest creerClientTest = new CreerClientTest(driver);
        creerClientTest.execute();
    }

    @Test(priority=4)
    public void testRechercheClient() {
        String timestamp = generateTimestamp();
        System.out.println(" testRechercheClient: " + timestamp);
        RechercheClientTest rechercheClientTest = new RechercheClientTest(driver);
        rechercheClientTest.execute("CHERNI MOLKA");

        boolean isClientFound = driver.getPageSource().contains("CHERNI MOLKA");
        Assert.assertTrue(isClientFound, "Le client recherché n'est pas affiché sur la page.");
    }

    @Test(priority=5)
    public void testUsernameFieldVisibility() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("t_Body_content")));

        Assert.assertTrue(usernameField.isDisplayed(), " Un nouveau client déja crée.");
    }

    @AfterTest
    public void tearDownTest() {
        //driver.quit();
        System.out.println("Test completed successfully");
    }
}

class ConnexionTest {
    private WebDriver driver;

    public ConnexionTest(WebDriver driver) {
        this.driver = driver;
    }

    public void execute() {
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
    private Random random = new Random();

    public CreerClientTest(WebDriver driver) {
        this.driver = driver;
    }

    public void execute() {
        WebElement creerButton = waitForElement(By.id("B166764947412786522"));
        creerButton.click();
        Select selectList = new Select(driver.findElement(By.id("P37_TITRE_CLIENT")));
        selectList.selectByVisibleText("Madame"); 

       
        String randomString = generateRandomString();

        
        String nomClient = "CHERNI MOLKA_" + randomString;
        driver.findElement(By.id("P37_NOM_CLIENT")).sendKeys(nomClient);

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

    private String generateRandomString() {
        int length = 5;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString();
    }

    private WebElement waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}

class RechercheClientTest {
    private WebDriver driver;
    private Random random = new Random();

    public RechercheClientTest(WebDriver driver) {
        this.driver = driver;
    }

    public void execute(String nomClient) {
        String recherche = nomClient  + " " + generateTimestamp();
        WebElement searchBar = waitForElement(By.id("R166763848610786519_search_field"));
        searchBar.sendKeys(recherche);
        searchBar.sendKeys(Keys.RETURN);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='166764050341786521']")));

        List<WebElement> searchResults = driver.findElements(By.xpath("//table[@id='166764050341786521']/tbody/tr"));
        if (!searchResults.isEmpty()) {
            WebElement dernierClient = searchResults.get(searchResults.size() - 1);
            String ligneComplete = dernierClient.getText();
            String timestamp = generateTimestamp();

            ligneComplete += " Date de création : " + timestamp;

            System.out.println("Nom du dernier client créé : "  + ligneComplete);
        } else {
            System.out.println("Aucun résultat trouvé.");
        }
    }
    private String generateTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        int randomNumber = random.nextInt(1000);
        return timestamp + "_" + randomNumber;
    }

    private WebElement waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
