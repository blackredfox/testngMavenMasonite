
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.List;
import java.util.NoSuchElementException;

public class BaseActions {


    protected WebDriver driver;
    protected WebDriverWait wait;

    public BaseActions(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }


    // Different varieties of Ajax click
    public void ajaxClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void ajaxClick(By by) {
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        wait.until(ExpectedConditions.elementToBeClickable(by));
        ajaxClick(driver.findElement(by));
    }



public String getText(By locator){
   return driver.findElement(locator).getText();
}

public By setStateLocator(String state){
    return By.xpath("//ul[@role='listbox']//li[@data-value='" + state + "']");
}



public void addSides(String option){
   driver.findElement(By.xpath(" //span[text()='" + option + "']")).click();
}


}


