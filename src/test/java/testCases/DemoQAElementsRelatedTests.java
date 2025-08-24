package testCases;

import BaseClass.BaseTest;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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

import javax.imageio.stream.FileImageInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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


    @Test(description = "Verify user able to select and deselect multiple check box")
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

    @Test(description = "Verify user able to select and deselect single radio button")
    public void selectAndDeselectRadioButton() {
        driver.findElement(By.xpath("//h5[text()='Book Store Application']/../..")).click();
        driver.findElement(By.xpath("//div[text()='Elements']/..")).click();
        driver.findElement(By.xpath("//span[text()='Radio Button']")).click();
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
        By yesRadioBtn = By.xpath("//label[text()='Yes']");
        WebElement yesRadioBtnLocator = wait.until(ExpectedConditions.visibilityOfElementLocated(yesRadioBtn));
        yesRadioBtnLocator.isDisplayed();
        driver.findElement(By.id("impressiveRadio")).isEnabled();
        driver.findElement(By.id("yesRadio")).isEnabled();
        driver.findElement(By.id("noRadio")).isEnabled();
        boolean yesRadioBtnLocatorEnabled = yesRadioBtnLocator.isEnabled();
        if(yesRadioBtnLocatorEnabled) {
        yesRadioBtnLocator.click();
        }
        boolean impressiveRadioBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Impressive']"))).isDisplayed();
        boolean noRadioBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='No']"))).isDisplayed();

    }


    @Test(description = "Add all radio button options into a excel file")
    public void writeRadioOptionInExcel() throws IOException {
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
        driver.findElement(By.xpath("//h5[text()='Book Store Application']/../..")).click();
        driver.findElement(By.xpath("//div[text()='Elements']/..")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Radio Button']"))).click();
        List<WebElement> radioList = driver.findElements(By.xpath("//input[@type='radio']/following-sibling::label"));
        for (WebElement list:radioList)
        {
            System.out.println(list.getText());
        }
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("example1");
        Row row = sheet.createRow(0);
        for (int i=0;i<radioList.size();i++)
        {
         Cell cell = row.createCell(i);
         cell.setCellValue(radioList.get(i).getText());
        }
        try (FileOutputStream fos = new FileOutputStream("sample.xlsx")) {
            workbook.write(fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        workbook.close();
    }

    @Test(description = "Add/Edit/Delete record in webtable")
    public void addAllTableDataIntoExcel() throws IOException{
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
        driver.findElement(By.xpath("//h5[text()='Book Store Application']/../..")).click();
        driver.findElement(By.xpath("//div[text()='Elements']/..")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Web Tables']"))).click();
        String title = driver.findElement(By.xpath("//h1[text()='Web Tables']")).getText();
        Assert.assertEquals(title, "Web Tables", "Incorrect page title");
        List<WebElement> allRows =driver.findElements(By.xpath("//div[@role='rowgroup']"));
        int rows = allRows.size();
        int cols = allRows.get(0).findElements(By.xpath(".//div[@role='gridcell']")).size();
        System.out.println(rows);
        System.out.println(cols);
        Object[][] arr = new Object[rows][cols];
        for(int i=0;i<rows;i++)
        {
            List<WebElement> cells = allRows.get(i).findElements(By.xpath(".//div[@role='gridcell']"));
            for (int j=0;j< cells.size();j++)
            {
                arr[i][j]=cells.get(j).getText().trim();
            }
        }
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("webtable");
        for(int r=0;r<rows;r++)
        {
            XSSFRow row = sheet.createRow(r);
            for(int c=0;c<cols;c++)
            {
                XSSFCell cell=row.createCell(c);
                Object value = arr[r][c];
                if(value instanceof String)
                  cell.setCellValue((String)value);
                else
                {
                    if(value instanceof Integer)
                        cell.setCellValue((String)value);
                }
            }
        }
        String filepath = ".\\webtable.xlsx";
        FileOutputStream out=new FileOutputStream(filepath);
        workbook.write(out);


    }
    }



