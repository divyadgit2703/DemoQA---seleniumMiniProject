package testCases;

import BaseClass.BaseTest;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class WindowsFrameAlertTest extends BaseTest {

    @Test(description = "Switch to New Tab and verify the message")
    public void OpenNewTab() {
        try {
            driver.findElement(By.xpath("//h5[text()='Book Store Application']/../..")).click();
            driver.findElement(By.xpath("//div[text()='Alerts, Frame & Windows']")).click();
            WebElement BrowserWindowOption = driver.findElement(By.xpath("//span[text()='Browser Windows']"));
            BrowserWindowOption.click();
            driver.findElement(By.id("windowButton")).click();
            String parentWindowId = driver.getWindowHandle();
            Set<String> handles = driver.getWindowHandles();
            for (String handle : handles) {
                if (!parentWindowId.equals(handle)) {
                    driver.switchTo().window(handle);
                    String windowTitle = driver.findElement(By.xpath("//body[contains(text(),'Knowledge')]")).getText();
                    System.out.println(windowTitle);
                    Assert.assertEquals(windowTitle, "This is a sample page", "Incorrect");
                }
            }
        } catch (ElementNotInteractableException e) {
            System.out.println("Retrying to search the element again" + e.getMessage());
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Text Box']/..")));
        } catch (TimeoutException T) {
            System.err.println("Timeout waiting for the element to be visible");
        }
    }


    @Test(description = "Switch to New Tab and verify the message")
    public void OpenAlert() {
        try {
            driver.findElement(By.xpath("//h5[text()='Book Store Application']/../..")).click();
            driver.findElement(By.xpath("//div[text()='Alerts, Frame & Windows']")).click();
            WebElement BrowserWindowOption = driver.findElement(By.xpath("//span[text()='Alerts']"));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            BrowserWindowOption.click();
            driver.findElement(By.id("alertButton")).click();
            Alert alert = driver.switchTo().alert();
            alert.accept();
            driver.findElement(By.id("timerAlertButton")).click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
            wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
            driver.findElement(By.id("confirmButton")).click();
            alert.dismiss();
            List<WebElement> confirmMsg = driver.findElements(By.xpath("//span[contains(text(),'You selected ')]"));
            boolean present = !confirmMsg.isEmpty();
            System.out.println(present);
            Assert.assertFalse(present);
            driver.findElement(By.id("confirmButton")).click();
            alert.accept();
            confirmMsg = driver.findElements(By.xpath("//span[contains(text(),'You selected ')]"));
            present = !confirmMsg.isEmpty();
            Assert.assertTrue(present);
            List<WebElement> promptMsg = driver.findElements(By.xpath("//span[contains(.,'test')]"));
            boolean isDisplayed = !promptMsg.isEmpty();
            System.out.println(isDisplayed);
            Assert.assertFalse(isDisplayed);
            driver.findElement(By.id("promtButton")).click();
            alert.sendKeys("test");
            alert.accept();
            List<WebElement> AfterpromptMsg = driver.findElements(By.xpath("//span[contains(.,'test')]"));
            boolean PromptMsgisDisplayed = !AfterpromptMsg.isEmpty();
            Assert.assertTrue(PromptMsgisDisplayed);
        } catch (ElementNotInteractableException e) {
            System.out.println("Retrying to search the element again" + e.getMessage());
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Text Box']/..")));
        } catch (TimeoutException T) {
            System.err.println("Timeout waiting for the element to be visible");
        }
    }

    @Test(description = "Switch to New Tab and verify the message")
    public void FrameHandling() {
        try {
            driver.findElement(By.xpath("//h5[text()='Book Store Application']/../..")).click();
            driver.findElement(By.xpath("//div[text()='Alerts, Frame & Windows']")).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            driver.findElement(By.xpath("//span[text()='Frames']")).click();
            //Switch frame by Index --- this can be used when we do not reliable id or name
            driver.switchTo().frame("2");
            boolean frameMsg = driver.findElement(By.xpath("//h1[text()='This is a sample page']")).isDisplayed();
            Assert.assertTrue(frameMsg);
            System.out.println(frameMsg);
            //Switch frame by using Id or name
            driver.switchTo().frame("frame1");
             frameMsg = driver.findElement(By.xpath("//h1[text()='This is a sample page']")).isDisplayed();
            Assert.assertTrue(frameMsg);
            System.out.println(frameMsg);

        } catch (ElementNotInteractableException e) {
            System.out.println("Retrying to search the element again" + e.getMessage());
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Text Box']/..")));
        } catch (TimeoutException T) {
            System.err.println("Timeout waiting for the element to be visible");
        }
    }

    @Test(description = "Switch to New Tab and verify the message")
    public void nestedFrameHandling() {
        try {
            driver.findElement(By.xpath("//h5[text()='Book Store Application']/../..")).click();
            driver.findElement(By.xpath("//div[text()='Alerts, Frame & Windows']")).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            driver.findElement(By.xpath("//span[text()='Nested Frames']")).click();
            JavascriptExecutor js = (JavascriptExecutor)driver;
            js.executeScript("window.scrollBy(0,document.documentElement.scrollHeight)");

            //Handling nested frame
            //switching to parent frame
            driver.switchTo().frame("frame1");
            WebElement parentFrame =driver.findElement(By.xpath("//body[contains(.,'Parent frame')]"));
            String parentFrameMsg = parentFrame.getText();
            System.out.println(parentFrameMsg);
            //Switch to child frame
            driver.switchTo().frame(0);
            WebElement childFrame =driver.findElement(By.xpath("//body[contains(.,'Child Iframe')]"));
            String childFrameMsg = childFrame.getText();
            System.out.println(childFrameMsg);
            driver.switchTo().defaultContent();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            boolean parentFrameMsgDisplayed=driver.findElement(By.xpath("//body[contains(.,'Parent frame')]")).isDisplayed();
            System.out.println(parentFrameMsgDisplayed);
            Assert.assertTrue(parentFrameMsgDisplayed);


        }catch (ElementNotInteractableException e) {
            System.out.println("Retrying to search the element again" + e.getMessage());
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Text Box']/..")));
        } catch (TimeoutException T) {
            System.err.println("Timeout waiting for the element to be visible");
        }
    }


    @Test(description = "Switch to New Tab and verify the message")
    public void excelfileReading() throws IOException {
        try {

              System.out.print("testing");
              String filePath = ".\\sample.xlsx";
            FileInputStream file=new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet("example1");
            int rows = sheet.getLastRowNum();
            int cols= sheet.getRow(0).getLastCellNum();
            for(int r=0;r<=rows;r++)
            {
                XSSFRow row = sheet.getRow(r);
                for(int c=0;c<cols;c++)
                {
                    XSSFCell cell = row.getCell(c);
                    switch (cell.getCellType())
                    {
                        case STRING:System.out.print(cell.getStringCellValue());
                                     break;
                        case NUMERIC:System.out.print(cell.getNumericCellValue());
                            break;
                    }
                    System.out.print("|");

                }
                System.out.println("");
            }
        }catch (ElementNotInteractableException e) {
            System.out.println("Retrying to search the element again" + e.getMessage());
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Text Box']/..")));
        } catch (TimeoutException T) {
            System.err.println("Timeout waiting for the element to be visible");
        }
    }

    @Test(description = "Switch to New Tab and verify the message")
    public void excelfileWriting() throws IOException {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("sheet 1");
            Object[][] arr = {{"Name","Place","Salary"},
                    {"John","USA","376743"},
                    {"Michal","UK","84378323"},
                    {"Bob","Canada","0984535"},
                    {"Cathe","London","3648222"},
                    {"Steve","India","93847953"}
            };
            int rows=arr.length;
            int cols=arr[0].length;
            for(int r=0;r<rows;r++)
            {
                XSSFRow row=sheet.createRow(r);
                for(int c=0;c<cols;c++)
                {
                    XSSFCell cell=row.createCell(c);
                    Object value = arr[r][c];
                    if(value instanceof String)
                        cell.setCellValue((String)value);
                       else
                    {
                        if(value instanceof Integer)
                            cell.setCellValue((Integer)value);
                    }
                }
            }
            String filepath = ".\\test.xlsx";
            FileOutputStream fis = new FileOutputStream(filepath);
            workbook.write(fis);
            System.out.println("file write done...");
        }catch (ElementNotInteractableException e) {
            System.out.println("Retrying to search the element again" + e.getMessage());
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Text Box']/..")));
        } catch (TimeoutException T) {
            System.err.println("Timeout waiting for the element to be visible");
        }
    }


}