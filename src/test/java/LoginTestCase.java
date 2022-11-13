import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class LoginTestCase {
    //Declare WebDriver on the level of the whole class
    WebDriver driver;

    @BeforeMethod
    public void openBrowser()
    {
        //1-Define Bridge Using WebDriver Manager OR Using System.setProperty
        WebDriverManager.chromedriver().setup();

        //2-Create new object from ChromeDriver
        driver=  new ChromeDriver();
        //3-Configuration
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //4-Navigate to
        driver.navigate().to("https://the-internet.herokuapp.com/");

    }

    @AfterMethod
    public void quitDriver() throws InterruptedException
    {
        Thread.sleep(3000);
        driver.quit();
    }

    @Test(priority = 1)
    public void validLogin()
    {
        //1- Click on Form Authentication
        driver.findElement(By.linkText("Form Authentication")).click();

        //2- enter username
        driver.findElement(By.id("username")).sendKeys("tomsmith");

        //3- enter password
        driver.findElement(By.name("password")).sendKeys("SuperSecretPassword!");

        //4- click login button
        driver.findElement(By.className("fa-sign-in")).click();

        //5-Assertion
        SoftAssert soft = new SoftAssert();

        //5.1- url should equal  https://the-internet.herokuapp.com/secure
        String actualURL = driver.getCurrentUrl();
        soft.assertEquals(actualURL,"https://the-internet.herokuapp.com/secure");

        //5.2 success message should contain "You logged into a secure area!"
        String actualText = driver.findElement(By.cssSelector("div[class=\"flash success\"]")).getText();
        //soft.assertEquals(actualText.contains("You logged into a secure area!"), true);
        //Or
        soft.assertTrue(actualText.contains("You logged into a secure area!"));

        //5.3 background color is green
        String actual_background_Color = driver.findElement(By.xpath("//div[@class=\"flash success\"]")).getCssValue("background-color");
        //Convert from RGBA to HEX
        String background_Color_HEX = Color.fromString(actual_background_Color).asHex();
        soft.assertEquals(background_Color_HEX,"#5da423");

        //5.4 logout button is displayed on UI
        boolean login = driver.findElement(By.className("icon-signout")).isDisplayed();
        soft.assertTrue(login);

        soft.assertAll();
    }
    @Test(priority = 2)
    public void Invalid()
    {
        //1- Click on Form Authentication
        driver.findElement(By.linkText("Form Authentication")).click();

        //2- enter invalid username
        driver.findElement(By.id("username")).sendKeys("WrongName");

        //3- enter invalid password
        driver.findElement(By.name("password")).sendKeys("WrongPassword!");

        //4- click login button
        driver.findElement(By.className("fa-sign-in")).click();

        //5- Assertions
        SoftAssert soft = new SoftAssert();

        //5.1- url should equal  https://the-internet.herokuapp.com/login
        String actualURL = driver.getCurrentUrl();
        soft.assertEquals(actualURL,"https://the-internet.herokuapp.com/login");

        //5.3 background color is green
        String actual_background_color = driver.findElement(By.id("flash")).getCssValue("background-color");
        //Convert from RGBA to HEX
        String Background_color_HEX = Color.fromString(actual_background_color).asHex();
        soft.assertEquals(Background_color_HEX,"#c60f13");

        //5.2 success message should contain "Your username is invalid!"
        String actualText = driver.findElement(By.cssSelector("div[class=\"flash error\"]")).getText();
        soft.assertEquals(actualText.contains("Your username is invalid!"),true);

        //5.4 logout button is Empty on UI
        boolean login = driver.findElements(By.className("icon-signout")).isEmpty();
        soft.assertTrue(login);

        soft.assertAll();
    }
    @Test(priority = 3)
    public void Elements() {
        //1- Click on Add/Remove Elements
        driver.findElement(By.linkText("Add/Remove Elements")).click();

        //2- Click on addElement Button
        driver.findElement(By.xpath("//button[@onclick=\"addElement()\"]")).click();

        //Assertion
        SoftAssert soft = new SoftAssert();

        //Delete Button is Displayed after click on addElement Button
        boolean delete_button = driver.findElement(By.className("added-manually")).isDisplayed();
        soft.assertTrue(delete_button);

        //Click on Delete Button
        driver.findElement(By.cssSelector("button[class=\"added-manually\"]")).click();

        //Delete Button is not Exist After CLicking on it
        boolean deleteButton = driver.findElements(By.className("added-manually")).isEmpty();
        soft.assertTrue(deleteButton);

        soft.assertAll();
    }

}

