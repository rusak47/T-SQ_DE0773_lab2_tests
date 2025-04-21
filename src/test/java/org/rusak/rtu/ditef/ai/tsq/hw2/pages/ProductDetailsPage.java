package org.rusak.rtu.ditef.ai.tsq.hw2.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.rusak.rtu.ditef.ai.tsq.hw2.models.Product;

//https://github.com/krmali/SeleniumTest/blob/0a00eb9b4f2f03f90dfce4af0429e48ab1d38e2b/src/main/java/com/AutoPractice_Selenium/ProductPage.java
public class ProductDetailsPage {
  WebDriver driver;

  WebElement addToCart;
  WebElement whiteColor;
  WebElement blackColor;
  WebElement quantityTextBox;
  WebElement productName;
  Select sizeTextBox;
  WebElement checkoutButton;
  WebElement afterAddingCartMessage;

  public ProductDetailsPage(WebDriver driver){
    this.driver = driver;
    this.addToCart = driver.findElement(By.xpath("//*[@id='add_to_cart']/button"));
    this.sizeTextBox = new Select(driver.findElement(By.id("group_1")));
    this.quantityTextBox = driver.findElement(By.id("quantity_wanted"));
    this.whiteColor = driver.findElement(By.xpath("//ul[@id='color_to_pick_list']//a[@name='White']"));
    this.blackColor = driver.findElement(By.xpath("//ul[@id='color_to_pick_list']//a[@name='Black']"));
    this.productName = driver.findElement(By.xpath("//*[@id='center_column']//h1"));

  }


  //Size is S, M or L
  //Color false for black and true for white
  public void addToCart(Product p){
    this.sizeTextBox.selectByVisibleText(p.size);
    this.quantityTextBox.clear();
    this.quantityTextBox.sendKeys(Integer.toString(p.quantity));
    if(p.color.equals("white"))
      this.whiteColor.click();
    else 
      this.blackColor.click();

    this.addToCart.click();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='layer_cart']//a")));
    checkoutButton = driver.findElement(By.xpath("//*[@id='layer_cart']//a"));
    p.name = this.productName.getText();
    this.afterAddingCartMessage = driver.findElement(By.xpath("//*[@id='layer_cart']/div[1]/div[1]/h2"));
  }

  public void proceedCheckout(){
    checkoutButton.click();
  }

  public String getProductName(){
    return productName.getText();
  }


  //after adding to cart message
  public String getMessage(){
    if(afterAddingCartMessage == null)
      return "";
    return this.afterAddingCartMessage.getText();
  }

}