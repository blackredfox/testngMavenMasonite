import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.lang.reflect.Method;

public class BaseUI {
    WebDriver driver;
    WebDriverWait wait;
    MainPage mainPage;
    CheckoutPage checkoutPage;
    FinalReceiptPage finalReceiptPage;
    ShoppingCartPage shoppingCartPage;

    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod(groups = {"user","admin","ie"}, alwaysRun = true)
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser, Method method) {

        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            Reports.start(method.getDeclaringClass().getName() + " : " + method.getName());
        }
        // Check if parameter passed as 'chrome'
        else if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.get("chrome://settings/clearBrowserData");
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            driver.manage().deleteAllCookies();

        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        wait = new WebDriverWait(driver, 20);
        mainPage = new MainPage(driver, wait);
        shoppingCartPage = new ShoppingCartPage(driver, wait);
        checkoutPage = new CheckoutPage(driver, wait);
        finalReceiptPage = new FinalReceiptPage(driver, wait);
        driver.manage().window().maximize();
        driver.get(Data.mainURL);
    }

    @AfterMethod
    public void afterActions(ITestResult testResult) {

        if (testResult.getStatus() == ITestResult.FAILURE) {
            Reports.fail(driver, testResult.getName());
        }
        Reports.stop();
      driver.quit();
    }
}
