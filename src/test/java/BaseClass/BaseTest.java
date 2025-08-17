package BaseClass;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.*;

public class BaseTest {
    public WebDriver driver;

    @BeforeSuite
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C://Users//dell//IdeaProjects//SeleniumJavaCoding//src//test//resources//Driver/chromedriver.exe"); // Or use WebDriverManager
        ChromeOptions cop = new ChromeOptions();
        cop.setBinary("C://Users//dell//Downloads//chrome-win64//chrome-win64/chrome.exe");
        cop.addArguments("--remote-allow-origins=*");
        cop.addArguments("--incognito");
        driver = new ChromeDriver(cop);
        driver.get("https://demoqa.com/");
        driver.manage().window().maximize();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        if (driver != null) {
            driver.quit();
        }
    }
}
