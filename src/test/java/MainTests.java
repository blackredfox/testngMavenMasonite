import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class MainTests extends BaseUI {

    public static final boolean TC1 = true;
    public static final boolean TC2A = true;
    public static final boolean TC3A = true;
    public static final boolean TC4 = true;
    public static final boolean TC5 = true;
    public static final boolean TC6 = true;
    public static final boolean TC7 = true;
    public static final boolean TC8 = true;
    public static final boolean TC9 = true;
    public static final boolean TC10 = true;
    public static final boolean TC11 = true;

    String actualTitle;

    @Test(enabled = TC1)
    public void verifySearchFlavor() {
        mainPage.searchItem(Data.flavorBanana);
        actualTitle = mainPage.getText(Locators.RESULT_FLAVOR_TEXT);
        Assert.assertEquals(actualTitle, Data.flavorBanana);
    }


    @Test(enabled = TC2A)
    public void verifyToppingsAlphabet() {
        mainPage.addSides(Data.addTopping);
        List<WebElement> flavorsElements = driver.findElements(Locators.TITLE_OF_ITEMS);
        for (int i = 0; i < flavorsElements.size()-1; i++) {
            if (flavorsElements.get(i).getText().compareToIgnoreCase(flavorsElements.get(i + 1).getText()) > 0) {
                Assert.fail("List of toppings from UI is` not listed alphabetically");
            }
        }
    }

    @Test(enabled = TC3A)
    public void verifySaucesAlphabet() {
        mainPage.addSides(Data.addSauce);
        List<WebElement> flavorsElements = driver.findElements(Locators.TITLE_OF_ITEMS);
        for (int i = 0; i < flavorsElements.size()-1; i++) {
            if (flavorsElements.get(i).getText().compareToIgnoreCase(flavorsElements.get(i + 1).getText()) > 0) {
                Assert.fail("List of sauces from UI is` not listed alphabetically");
            }
        }
    }


    @Test(enabled = TC4)
    public void verifyDefaultWaffleCone() {
      Assert.assertTrue(shoppingCartPage.findDefaultWaffleCone().isDisplayed());
    }


    @Test(enabled = TC5)
    public void verifyChangesItemsInShoppingCart() {
        mainPage.addQuantity(1, Locators.ADD_BANANA_SCOOP_QUANTITY);
        Assert.assertTrue(shoppingCartPage.findFirstScoopInCart().isDisplayed());
        if(!shoppingCartPage.findFirstScoopInCart().getText().contains(Data.flavorBanana)){
          Assert.fail(Data.flavorBanana + " is not found in cart!");
        }

    }
    @Test(enabled = TC6)
    public void verifyChangesPriceInShoppingCart() {
        mainPage.addQuantity(2, Locators.ADD_BANANA_SCOOP_QUANTITY);
        String price = shoppingCartPage.findPriceItemInCart().getText();
        if(!price.contains(Data.priceForTwoScoops)){
            Assert.fail(Data.priceForTwoScoops + " is not correct in cart!");
        }
    }


    @Test(enabled = TC7)
    public void maxThreeScoops() {
        mainPage.addQuantity(3, Locators.ADD_BANANA_SCOOP_QUANTITY);
        if(driver.findElement(Locators.ADD_BANANA_SCOOP_QUANTITY).isEnabled()){
           Assert.fail("Add button is still more than 3 scoops");
        }
        if(shoppingCartPage.findFirstScoopInCart().getText().contains("4")){
            Assert.fail("Cart has 4 scoops");
        }
    }


    @Test(enabled = TC8)
    public void lessThanZeroScoops() {
        if(driver.findElement(Locators.SUBSCTRACT_BANANA_SCOOP_QUANTITY).isEnabled()){
           Assert.fail("Substract button is enabled");
        }
        if(shoppingCartPage.countNoFlavorsLabels() == 0){
            Assert.fail("Cart has less 0 scoops in cart");
        }
    }

    @Test(enabled = TC9)
    public void verifyThatOneScoopIsRequired() {
        if(!shoppingCartPage.findCheckoutButton().isEnabled()){
           Assert.fail("Checkout button is enabled without scoop in cart");
        }
        mainPage.addQuantity(1, Locators.ADD_BANANA_SCOOP_QUANTITY);
        wait.until(ExpectedConditions.elementToBeClickable(Locators.BUY_ICE_CREAM_CHECKOUT_BUTTON));
        mainPage.clickCheckoutButton();
        wait.until(ExpectedConditions.elementToBeClickable(Locators.CARD_NUMBER_TEXT_FIELD));
    }


    @Test(enabled = TC10)
    public void verifyCheckoutReturnToHomePage() {
        mainPage.addQuantity(1, Locators.ADD_BANANA_SCOOP_QUANTITY);
        driver.findElement(Locators.BUY_ICE_CREAM_CHECKOUT_BUTTON).click();
        wait.until(ExpectedConditions.elementToBeClickable(Locators.HOME_PAGE_RETURN_BUTTON));
        driver.findElement(Locators.HOME_PAGE_RETURN_BUTTON).click();
        wait.until(ExpectedConditions.elementToBeClickable(Locators.BUY_ICE_CREAM_CHECKOUT_BUTTON));
        Assert.assertTrue(driver.findElement(Locators.ORDER_TITLE).isDisplayed());

    }




    @Test(enabled = TC11)
    public void getVerifyFlavorResults(){
        mainPage.searchItem(Data.flavorBanana);
        actualTitle = mainPage.getText(Locators.RESULT_FLAVOR_TEXT);
        Assert.assertEquals(actualTitle, Data.flavorBanana);
        mainPage.addQuantity(1, Locators.ADD_BANANA_SCOOP_QUANTITY);
        mainPage.clickCheckoutButton();
        checkoutPage.fillInOrder(Data.cardNumber, Data.expirationDate, Data.cvcNumber, Data.cardName,
                Data.billingAddress, Data.cityName, Data.zipCode, Data.stateName);
        checkoutPage.clickCompleteOrder();
        checkoutPage.ajaxClick(Locators.TITLE_ORDER_COMPLETED);
        Assert.assertTrue(driver.findElement(Locators.TITLE_ORDER_COMPLETED).isDisplayed(), "Order is completed");
        finalReceiptPage.clickCreateNewOrder();
        Assert.assertTrue(driver.findElement(Locators.ORDER_TITLE).isDisplayed());
    }







}
