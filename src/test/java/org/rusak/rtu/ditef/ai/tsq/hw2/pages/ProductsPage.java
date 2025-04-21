package org.rusak.rtu.ditef.ai.tsq.hw2.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.rusak.rtu.ditef.ai.tsq.hw2.models.DelayIntf;
import org.rusak.rtu.ditef.ai.tsq.hw2.models.Product;
import org.rusak.rtu.ditef.ai.tsq.hw2.models.TopNavigationIntf;

import allure.ScreenshooterIntf;
import lombok.Getter;

public class ProductsPage implements TopNavigationIntf, DelayIntf, ScreenshooterIntf {
    public @Getter WebDriver driver;

    WebDriverWait wait;
    WebElement sortDirection;
    Select sortDropdown;
    List<WebElement> prices;
    List<WebElement> productTitles;
    List<WebElement> productColors;
    List<WebElement> productSizes;
    List<WebElement> productToCartBtns;   
    
    By productDetailsLocator = By.cssSelector("div.product.details.product-item-details");     
    By productItemInfoLocator = By.cssSelector(".products.product-items div.product-item-info");

    public enum SortBy{
        Price("Price"),
        Name("Name"),
        CreationDate("Creation Date")
        ;
        @Getter String value;

        SortBy(String value){this.value = value;  }
    }

    public enum SortDirection{
        Ascending("asc"),
        Descending("desc")
        ;
        @Getter String value;

        SortDirection(String value){this.value = value;  }
    }

    public ProductsPage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(3)); 
        
        refreshElements();
        takeScreenshot();
    }

    public final void refreshElements(){
        this.sortDirection = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".action.sorter-action")));
        this.sortDropdown = new Select(wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("select#sorter"))));
        this.prices = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".product.details.product-item-details div.price-box span.price")));
        this.productTitles = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".product.details.product-item-details .product-item-name a")));
        
        //attributes may be missing
        try {
            this.productColors = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".product.details.product-item-details .swatch-attribute.color")));
        } catch (Exception e) {
            this.productColors = null;
        }
        try {
            this.productSizes = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".product.details.product-item-details .swatch-attribute.size")));
        } catch (Exception e) {
            this.productSizes = null;
        }
        
        this.productToCartBtns = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".product.details.product-item-details .action.tocart")));
    }

    @Override public void search(String searchText){
        TopNavigationIntf.super.search(searchText);
        waittoappear(450);
        refreshElements();
        takeScreenshot();
    }

    public void addToCart(Product product){
        // be sure that products page is loaded and selected product is visible
        List<WebElement> productInfoContainers = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productItemInfoLocator));
        WebElement productInfoContainer = productInfoContainers.get(product.getIndex());
        
        ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", productInfoContainer);
        new Actions(getDriver())
        .moveToElement(productInfoContainer)
        .perform();

        System.out.println("scrolled to product details: "+productInfoContainer.getText());
        //waittoappear(10000);

        WebElement productToCartBtn = productToCartBtns.get(product.getIndex());
        wait.until(ExpectedConditions.visibilityOf(productToCartBtn));
        new Actions(getDriver())
        .moveToElement(productToCartBtn)
        .build()
        .perform();
        
        System.out.println("Focused on btn: "+productToCartBtn.getText());
        //waittoappear(5000);
        selectProductAttribute("size", product);
        selectProductAttribute("color", product);
        
        //waittoappear(5000);
        wait.until(ExpectedConditions.elementToBeClickable(productToCartBtn));
        productToCartBtn.click();
        
        refreshElements();
        takeScreenshot();

        //TODO check that product was added to cart - presence of .message-error.error.message
    }

    /**
     * Selects a product attribute option (like size or color) by its value
     * @param attributeType The attribute type (e.g., "size" or "color")
     * @param optionValue The value to select
     */
    public void selectProductAttribute(String attributeType, Product product) {
        if (product == null || product.getIndex() < 0 || product.getIndex() >= productTitles.size() || product.getAttributeValueByType(attributeType) == null) {
            return;
        }
        
        WebElement productElement = driver.findElements(productDetailsLocator).get(product.getIndex());
        //check that product web element have such attribute
        if (!hasProductAttribute(productElement, attributeType)) {
            System.out.println("Product does not have attribute: " + attributeType);
            return;
        }

        //TODO if product have no any attribute, then lists are wrong
        List<WebElement> options = switch (attributeType) {
            case "color" -> productColors.get(product.getIndex()).findElements(By.cssSelector(".swatch-attribute-options div"));
            case "size" -> productSizes.get(product.getIndex()).findElements(By.cssSelector(".swatch-attribute-options div"));
            default -> null;
        };

        if (options == null || options.isEmpty()) {
            System.out.println("No options found for attribute type: " + attributeType);
            return; 
        }
        
        // Wait for attribute options to be visible
        wait.until(ExpectedConditions.visibilityOfAllElements(options));
        boolean found = false;
        for (WebElement option : options) {
            if (found = option.getText().equalsIgnoreCase(product.getAttributeValueByType(attributeType))) {
                option.click();
                break;
            }
        }
        if (!found) { //select the first one
            options.get(0).click();
        }
        //refreshElements();

        
        // For color, use option-label attribute; for size and others, use text content
        /*String xpathSelector;
        if (attributeType.equals("color")) {
            xpathSelector = "//div[contains(@class, 'swatch-attribute-options')]//div[@option-label='" + optionValue + "']";
        } else {
            xpathSelector = "//div[contains(@class, 'swatch-attribute-options')]//div[text()='" + optionValue + "']";
        }
        
        // Find and click the option
        WebElement option = driver.findElement(By.xpath(xpathSelector));
        option.click();
        */
    }

    /*@Override public void clearCart(){
        TopNavigationIntf.super.clearCart();
        refreshElements();
        takeScreenshot();
    }*/

    /*public void addToCart(Product p){
        for (int i = 0; i < productTitles.size(); i++) {
            if (productTitles.get(i).getText().equals(p.getName())) {
                productToCartBtns.get(i).click();
                break;
            }
        }
    }*/

    private boolean hasProductAttribute(WebElement productElement, String attributeType) {
        try {
            return !productElement.findElements(By.cssSelector(".swatch-attribute." + attributeType)).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public void toggleSorting(SortDirection sort){
        if (this.sortDirection.getDomAttribute("class").contains("sort-"+sort.getValue())) {
            System.out.println("Already sorted in "+sort.name()+" order");
            return;
        }
        //this.sortDirection.click();
        ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", sortDirection);
        wait.until(ExpectedConditions.visibilityOf(sortDirection));
        new Actions(driver)
        .moveToElement(sortDirection)
        .perform()
        ;

        waittoappear(1000); //otherwise it may fail to click
        wait.until(ExpectedConditions.elementToBeClickable(sortDirection));
        sortDirection.click();

        refreshElements();
        System.out.println("Sorting direction: "+this.sortDirection.getText());
        takeScreenshot();
    }

    public void setSortBy(SortBy sortBy){
        if (this.sortDropdown.getFirstSelectedOption().getText().equals(sortBy.getValue())) {
            System.out.println("Already sorted by "+sortBy.name());
            return;
        }
        this.sortDropdown.selectByVisibleText(sortBy.getValue());
        refreshElements();
        wait.until(ExpectedConditions.attributeToBe(this.sortDropdown.getWrappedElement(), "value", sortBy.getValue().toLowerCase()));
        
        System.out.println("Sorting by: "+sortBy.getValue());
        takeScreenshot();
    }

    public void checkSortedDir(SortDirection sort){
        wait.until(ExpectedConditions.visibilityOf(sortDirection));

        assert this.sortDirection.getDomAttribute("class").contains("sort-"+sort.getValue()) : "Sorting ("+this.sortDirection.getDomAttribute("class")+") direction is not "+sort.name();
        refreshElements();
        
        double price = Double.parseDouble(prices.get(0).getText().replace("$", ""));
        
        for (int i = 1; i < prices.size(); i++) {
            double currentPrice = Double.parseDouble(prices.get(i).getText().replace("$", ""));
            if (sort == SortDirection.Descending) {
                assert price >= currentPrice : "Prices are not sorted in descending order. " + price + " >= " + currentPrice;
            }else{
                assert price <= currentPrice : "Prices are not sorted in ascending order. " + price + " <= " + currentPrice;
            }
        }
    }

    public void checkSearchTextFound(String searchText, int limit){
        wait.until(ExpectedConditions.visibilityOfAllElements(productTitles));
        System.out.println("Product titles: "+productTitles.size() + " limit check to TOP" + limit);

        for (int i = 0; i < limit; i++) {
            assert productTitles.get(i).getText().toLowerCase().contains(searchText.toLowerCase()) : "Product title ("+productTitles.get(i).getText()+") does not contain the search text.";
        }
    }


}
