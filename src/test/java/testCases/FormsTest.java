package testCases;

import BaseClass.BaseTest;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class FormsTest extends BaseTest {


    @Test(description = "Add dropdown values in an excel")
    public void addDropDownValuesInexcelfile() throws IOException {
        try {
            driver.findElement(By.xpath("//h5[text()='Book Store Application']/../..")).click();
            driver.findElement(By.xpath("//div[text()='Forms']")).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            driver.findElement(By.xpath("//span[text()='Practice Form']")).click();
            JavascriptExecutor js =(JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,document.documentElement.scrollHeight)");
            driver.findElement(By.id("state")).click();
            List<WebElement> allStates = driver.findElements(By.xpath("//div[@tabindex='-1']"));
            for (int i=0;i<allStates.size();i++)
            {
                System.out.println(allStates.get(i).getText());
            }
            XSSFWorkbook workbook=new XSSFWorkbook();
            XSSFSheet sheet=workbook.createSheet("dropdowns");
            int rows = allStates.size();
            int cols = 1;
            for(int c=0;c<rows;c++)
            {
                XSSFRow row = sheet.createRow(c);
                XSSFCell cell=row.createCell(0);
                String value=allStates.get(c).getText();
                cell.setCellValue(value);
            }
            String filepath = ".\\exa.xlsx";
            FileOutputStream out = new FileOutputStream(filepath);
            workbook.write(out);
            System.out.println("added to select");

        }catch (ElementNotInteractableException e) {
            System.out.println("Retrying to search the element again" + e.getMessage());
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Text Box']/..")));
        } catch (TimeoutException T) {
            System.err.println("Timeout waiting for the element to be visible");
        }
    }

}
