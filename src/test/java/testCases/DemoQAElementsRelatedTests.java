package testCases;

import BaseClass.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class DemoQAElementsRelatedTests extends BaseTest {

    @Test(description = "Verify Text Box Visibility & Enabled State")
    public void TextBoxVisibility() {
        try {
            driver.findElement(By.xpath("//h5[text()='Book Store Application']/../..")).click();
            driver.findElement(By.xpath("//div[text()='Elements']/..")).click();
            driver.findElement(By.xpath("//span[text()='Text Box']/..")).click();
            String textBoxTitle = driver.findElement(By.xpath("//h1[text()='Text Box']")).getText();
            Assert.assertEquals(textBoxTitle, "Text Box", "Incorrect page title");
            driver.findElement(By.id("userName-label")).isDisplayed();
            driver.findElement(By.id("userEmail-label")).isDisplayed();
            driver.findElement(By.id("currentAddress-label")).isDisplayed();
            driver.findElement(By.id("permanentAddress-label")).isDisplayed();
            driver.findElement(By.id("userName")).isEnabled();
            driver.findElement(By.id("userEmail")).isEnabled();
            driver.findElement(By.id("currentAddress")).isEnabled();
            driver.findElement(By.id("permanentAddress")).isEnabled();
        } catch (ElementNotInteractableException e) {
            System.out.println("Retrying to search the element again" + e.getMessage());
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Text Box']/..")));
        } catch (TimeoutException T) {
            System.err.println("Timeout waiting for the element to be visible");
        }
    }


    @Test(description = "Submit form data successfully")
    public void submitFormData() {
        try {
            driver.findElement(By.xpath("//h5[text()='Book Store Application']/../..")).click();
            driver.findElement(By.xpath("//div[text()='Elements']/..")).click();
            driver.findElement(By.xpath("//span[text()='Text Box']/..")).click();
            String textBoxTitle = driver.findElement(By.xpath("//h1[text()='Text Box']")).getText();
            Assert.assertEquals(textBoxTitle, "Text Box", "Incorrect page title");
            driver.findElement(By.id("userName")).isEnabled();
            driver.findElement(By.id("userName")).sendKeys("divya");
            driver.findElement(By.id("userEmail")).sendKeys("drrg@gmail.com");
            driver.findElement(By.id("currentAddress")).sendKeys("test");
            driver.findElement(By.id("permanentAddress")).isEnabled();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.getElementById('permanentAddress').value='test permanent address through javascript executor';");
            WebElement submitBtn = driver.findElement(By.xpath("//button[text()='Submit']"));
            js.executeScript("arguments[0].scrollIntoView(true);", submitBtn);
            submitBtn.click();
            //compare with the output
            String nameOutput = driver.findElement(By.xpath("//p[contains(.,'divya')]")).getText();
            System.out.println(nameOutput);
            Assert.assertTrue(nameOutput.contains("divya"), "Name not matching");
            String emailOutput = driver.findElement(By.xpath("//p[contains(.,'drrg@gmail.com')]")).getText();
            Assert.assertTrue(emailOutput.contains("drrg@gmail.com"), "email not matching");
            String addressOutput = driver.findElement(By.xpath("//p[contains(.,'test') and @id=\"currentAddress\"]")).getText();
            Assert.assertTrue(addressOutput.contains("test"), "address not matching");
            String permanentAdressOutput = driver.findElement(By.xpath("//p[contains(.,'test permanent address through javascript executor')]")).getText();
            Assert.assertTrue(permanentAdressOutput.contains("test permanent address through javascript executor"), "permanent address not matching");
        } catch (ElementNotInteractableException e) {
            System.out.println("Retrying to search the element again" + e.getMessage());
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit"))).click();
        } catch (TimeoutException T) {
            System.err.println("Timeout waiting for the element to be visible");
        }
    }


    @Test(description = "Verify checkbox page accessibility and side arrow expand/collapse")
    public void checkBoxAccessibility() {
        WebElement collapseButton = null;
        try {
            driver.findElement(By.xpath("//h5[text()='Book Store Application']/../..")).click();
            driver.findElement(By.xpath("//div[text()='Elements']/..")).click();
            driver.findElement(By.xpath("//span[text()='Check Box']")).click();
            String textBoxTitle = driver.findElement(By.xpath("//h1[text()='Check Box']")).getText();
            Assert.assertEquals(textBoxTitle, "Check Box", "Incorrect page title");
            driver.findElement(By.xpath("//span[text()='Home']/../preceding-sibling::button")).click();
            driver.findElement(By.xpath("//span[text()='Desktop']")).isDisplayed();
            collapseButton = driver.findElement(By.xpath("//span[text()='Home']/../preceding-sibling::button"));
            collapseButton.click();
            boolean isCollapsed = driver.findElement(By.xpath("//span[text()='Desktop']")).isDisplayed();
            System.out.println(isCollapsed);
            Assert.assertTrue(isCollapsed, "Not collapsed");
        } catch (NoSuchElementException e) {
            System.out.println("Waiting for the element");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Home']/../preceding-sibling::button"))).click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Waiting for the element");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", collapseButton);
        } catch (TimeoutException T) {
            System.err.println("Timeout waiting for the element to be visible");
        }
    }

    @Test(description = "Verify user able to select and deselect single check box")
    public void selectDeselectSingleCheckbox() {
        WebElement checkBoxClick = null;
        try {
            driver.findElement(By.xpath("//h5[text()='Book Store Application']/../..")).click();
            driver.findElement(By.xpath("//div[text()='Elements']/..")).click();
            checkBoxClick = driver.findElement(By.xpath("//span[text()='Check Box']"));
            checkBoxClick.click();
            driver.findElement(By.xpath("//span[text()='Home']/../preceding-sibling::button")).click();
            WebElement checkBox = driver.findElement(By.xpath("(//span[text()='Documents']/preceding-sibling::span)[1]"));
            boolean selected = checkBox.isSelected();
            if (!selected) {
                checkBox.click();
            }
            //uncheck checkbox
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            checkBox.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            boolean deSelected = checkBox.isSelected();
            if (deSelected) {
                System.out.println("deselected the checkbox");
            }
        } catch (ElementClickInterceptedException e) {
            System.out.println("Waiting for the element");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkBoxClick);
        }

    }


    @Test(description = "Verify user able to select and deselect single check box")
    public void selectDeselectMultipleCheckbox() {
        driver.findElement(By.xpath("//h5[text()='Book Store Application']/../..")).click();
        driver.findElement(By.xpath("//div[text()='Elements']/..")).click();
        driver.findElement(By.xpath("//span[text()='Check Box']")).click();
        driver.findElement(By.xpath("//span[text()='Home']/../preceding-sibling::button")).click();
        List<WebElement> checkBoxList = driver.findElements(By.xpath("//span[@class='rct-checkbox']"));
        for (WebElement checBox : checkBoxList) {
            if (!checBox.isSelected()) {
                WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(2));
                wait.until(ExpectedConditions.elementToBeClickable(checBox)).click();
            }
        }
    }
}

