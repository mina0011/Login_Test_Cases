import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class SearchTestCases {
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

    }

    @AfterMethod
    public void quitDriver() throws InterruptedException
    {
        Thread.sleep(3000);
        driver.quit();
    }
    @Test
    public void google() throws InterruptedException {
        //Navigate to Google
        driver.navigate().to("https://www.google.com/");

        //Assertion
        SoftAssert soft = new SoftAssert();

        boolean logo = driver.findElement(By.className("lnXdpd")).isDisplayed();
        soft.assertTrue(logo);

        //Write selenium at Search input
        driver.findElement(By.cssSelector("input[class=\"gLFyf gsfi\"]")).sendKeys("selenium");

        //Search options is shown after writing selenium

        Thread.sleep(3000);
        boolean actualSearch = driver.findElement(By.className("erkvQe")).isDisplayed();
        soft.assertTrue(actualSearch,"assertion 1");

        //Click on any empty area at screen
        driver.findElement(By.cssSelector("div[class=\"o3j99 LLD4me yr19Zb LS8OJ\"]")).click();

        //U can not see the Search options
        Thread.sleep(3000);
        boolean SearchBar = driver.findElement(By.xpath("//div[@class=\"mkHrUc\"]")).isDisplayed();
        soft.assertFalse(SearchBar,"assertion 2");

        soft.assertAll();
    }

}
