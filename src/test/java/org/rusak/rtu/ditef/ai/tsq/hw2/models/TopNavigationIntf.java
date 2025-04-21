package org.rusak.rtu.ditef.ai.tsq.hw2.models;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.rusak.rtu.ditef.ai.tsq.hw2.pages.ProductsPage;

public interface TopNavigationIntf{
    
    public WebDriver getDriver();

    By searchInputLocator = By.cssSelector("input#search");
    By searchButtonLocator = By.cssSelector("form#search_mini_form button.action.search");

    By cartItemsCountLocator = By.cssSelector(".minicart-wrapper .counter-number");
    By cartLocator = By.cssSelector(".minicart-wrapper a.action.showcart");
    By clearCartLocator = By.cssSelector(".minicart-wrapper a.action.delete");
    By cartDetailedViewLocator = By.cssSelector(".minicart-wrapper.active a.viewcart");
    
    By cartItemLocator = By.cssSelector(".minicart-wrapper.active div.minicart-items-wrapper .item.product");
    By cartItemQttyLocator = By.cssSelector("input.cart-item-qty");
    By cartItemTitleLocator = By.cssSelector(".minicart-wrapper.active div.minicart-items-wrapper .item.product .product-item-name a");
    By cartItemPriceLocator = By.cssSelector(".minicart-wrapper.active div.minicart-items-wrapper .item.product .price");

    By popUpAcceptButtonLocator = By.cssSelector(".modal-popup.confirm button.action-accept");
    public default Object accessCategory(String root, String category, String subcategory) {
        By mainCategoryLocator = null;
        if(root.equals("Men")){
            mainCategoryLocator = By.id("ui-id-5");
        }
        By subCategoryLocator;
        switch (category) {
            case "Tops" -> subCategoryLocator = By.id("ui-id-17");
            case "Bottoms" -> subCategoryLocator = By.id("ui-id-18");
            default -> subCategoryLocator = null;
        }
        By productCategoryLocator;
        productCategoryLocator = switch (subcategory) {
            case "Jackets" -> By.id("ui-id-19");
            case "Shorts" -> By.id("ui-id-24");
            default -> null;
        };
    
        if(mainCategoryLocator == null || subCategoryLocator == null || productCategoryLocator == null){
            throw new RuntimeException("Invalid category provided");
        }

        WebElement mainCategory = getDriver().findElement(mainCategoryLocator);
        WebElement subCategory = getDriver().findElement(subCategoryLocator);
        WebElement productCategory = getDriver().findElement(productCategoryLocator);
        
        Actions actionbuilder = new Actions(getDriver());
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        
        wait.until(ExpectedConditions.visibilityOf(mainCategory));
        wait.until(ExpectedConditions.elementToBeClickable(mainCategory));
        actionbuilder.moveToElement(mainCategory).perform();
        
        wait.until(ExpectedConditions.visibilityOf(subCategory));
        wait.until(ExpectedConditions.elementToBeClickable(subCategory));
        actionbuilder.moveToElement(subCategory).perform();
        
        wait.until(ExpectedConditions.visibilityOf(productCategory));
        wait.until(ExpectedConditions.elementToBeClickable(productCategory));
        actionbuilder.moveToElement(productCategory).click(productCategory).perform();
        
        return new ProductsPage(getDriver());
	}

    public default void checkIsCorrectCategoryPage(String root, String category, String subCategory){
        String title = getDriver().getTitle();
        System.out.println("Page title is: " + title);
        System.out.println("Page URL is: " + getDriver().getCurrentUrl());

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        wait.until(ExpectedConditions.and(
            ExpectedConditions.titleContains(root),
            ExpectedConditions.titleContains(category),
            ExpectedConditions.titleContains(subCategory)
        ));
    }

    public default void search(String searchText) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        
        WebElement searchInput = getDriver().findElement(searchInputLocator);
        WebElement searchButton = getDriver().findElement(searchButtonLocator);

        ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", searchInput);
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        
        //searchInput.sendKeys(searchText);
        //searchButton.click();

        Actions actionbuilder = new Actions(getDriver());
        
        actionbuilder.sendKeys(searchInput, searchText).perform();
        wait.until(ExpectedConditions.visibilityOf(searchButton));
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        actionbuilder.click(searchButton).perform();
        
        wait.until(ExpectedConditions.titleContains("Search results for:"));
        System.out.println("Page title is: " + getDriver().getTitle());
    }

    @SuppressWarnings("UseSpecificCatch")
    public default void clearCart() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        WebElement cart = wait.until(ExpectedConditions.visibilityOfElementLocated(cartLocator));
        WebElement cartItemsCount = null;
        int cartItemsCountInt = 0;
        try {
            cartItemsCount = wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemsCountLocator));
            System.out.println("Cart items count: " + cartItemsCount.getText());
            
            cartItemsCountInt = Integer.parseInt(cartItemsCount.getText());
        } catch (Exception ignore) { }

        if (cartItemsCountInt == 0) {
            System.out.println("Cart is already empty");
            return;
        }
        
        cart.click();
        while (cartItemsCountInt > 0) {
            WebElement clearFirstItem = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(clearCartLocator)).get(0);
            clearFirstItem.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(popUpAcceptButtonLocator)).click();
            
            try { Thread.sleep(2000); } catch (InterruptedException ignore) {}

            try {
                cartItemsCount = wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemsCountLocator));
                cartItemsCountInt = Integer.parseInt(cartItemsCount.getText());
            } catch (Exception ignore) { return;  }
        }
    }

    public default void showMiniCart() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        WebElement cart = wait.until(ExpectedConditions.visibilityOfElementLocated(cartLocator));
        cart.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(cartDetailedViewLocator));
    }

    public default void checkMiniCartTotalQty(int expectedQty) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        WebElement cartItemsCount = wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemsCountLocator));
        int cartItemsCountInt = Integer.parseInt(cartItemsCount.getText());
        assert cartItemsCountInt == expectedQty : "Cart items count is not correct. Expected: " + expectedQty + ", actual: " + cartItemsCountInt;
    }

    public default void checkCartHasItem(String title, int quantity, double price) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        List<WebElement> cartItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartItemLocator));

        WebElement matchingItem = cartItems.stream().filter(item -> {
            String itemTitle = item.findElement(cartItemTitleLocator).getText();
            double itemPrice = Double.parseDouble(item.findElement(cartItemPriceLocator).getText().replace("$", "").replace(",", ""));
            
            System.out.println("Item title: " + itemTitle + ", price: " + itemPrice);
            return itemTitle.equals(title) && itemPrice == price;
        }).findFirst().orElse(null);
        
        if (matchingItem == null) {
            assert matchingItem != null : "No matching item found in the cart with title: " + title + ", quantity: " + quantity + ", price: " + price;
        }
        
        int itemQuantity = Integer.parseInt(matchingItem.findElement(cartItemQttyLocator).getDomAttribute("data-item-qty"));
        assert itemQuantity == quantity : "Item quantity is not correct. Expected: " + quantity + ", actual: " + itemQuantity;
    }

}
