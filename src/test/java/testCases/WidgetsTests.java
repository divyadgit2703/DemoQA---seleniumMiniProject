package testCases;

import BaseClass.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class WidgetsTests extends BaseTest {
    @Test(description = "Date picker Handling")
    public void calendarHandling() {
        try {
            driver.findElement(By.xpath("//h5[text()='Book Store Application']/../..")).click();
            driver.findElement(By.xpath("//div[text()='Widgets']/..")).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            driver.findElement(By.xpath("//span[text()='Date Picker']/..")).click();
            driver.findElement(By.id("datePickerMonthYearInput")).click();
            WebElement month=driver.findElement(By.className("react-datepicker__month-select"));
            WebElement Year = driver.findElement(By.className("react-datepicker__year-select"));
            Select scMonth =new Select(month);
            scMonth.selectByVisibleText("July");
            Select scYear =new Select(Year);
            scYear.selectByVisibleText("1998");
            String date = "21";
            driver.findElement(By.xpath("//div[text()='"+date+"']")).click();

        } catch (ElementNotInteractableException e) {
            System.out.println("Retrying to search the element again" + e.getMessage());
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Text Box']/..")));
        } catch (TimeoutException T) {
            System.err.println("Timeout waiting for the element to be visible");
        }

    }
}
