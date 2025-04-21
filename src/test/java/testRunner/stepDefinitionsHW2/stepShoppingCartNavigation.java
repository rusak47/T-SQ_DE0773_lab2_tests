package testRunner.stepDefinitionsHW2;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.rusak.rtu.ditef.ai.tsq.Utils;
import org.rusak.rtu.ditef.ai.tsq.hw2.models.Product;
import org.rusak.rtu.ditef.ai.tsq.hw2.pages.CustomerHomePage;
import org.rusak.rtu.ditef.ai.tsq.hw2.pages.HomePage;
import org.rusak.rtu.ditef.ai.tsq.hw2.pages.ProductsPage;
import org.rusak.rtu.ditef.ai.tsq.hw2.pages.SignInPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class stepShoppingCartNavigation {
    WebDriver driver;
    WebDriverWait wait;

    SignInPage signInPage;
    CustomerHomePage customerHomePage;
    ProductsPage productsPage;

    @Before public void testSetup() throws InterruptedException {
		driver = Utils.setupDriverInstance(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3)); 
	}
	@After public void tearDown() { Utils.quitSharedDriver(driver); }

    @Given("user opens magento shop home page and navigates to login page")
    public void userOpensMagentoShopHomePage() {
        signInPage = HomePage.create(driver).gotoSignIn();
        signInPage.checkIsOnSignInPage();
    }

    @When("existing user {string} {string} logs in with {string} {string}")
    public void userLogsIn(String firstname, String lastname, String email, String password) {
        customerHomePage = signInPage.signIn(email, password);
        customerHomePage.checkIsOnCustomerHomePage();
    }

    @And("empty cart")
    public void emptyCart() {
        customerHomePage.clearCart();
    }

    @And("user navigates to another {string} {string} {string} category")
    public void userNavigatesToAnotherCategory(String root, String product, String subproduct) {
        productsPage = (ProductsPage) customerHomePage.accessCategory(root, product, subproduct);
        productsPage.checkIsCorrectCategoryPage(root, product, subproduct);
    }

    @And("user sorts products by price in descending order")
    public void userSortsProductsByPriceInDescendingOrder() {
        productsPage.toggleSorting(ProductsPage.SortDirection.Descending);
        //productsPage.waittoappear(1000);        
        productsPage.setSortBy(ProductsPage.SortBy.Price);
    }

    @Then("the first product in the list has the highest price in the category")
    public void theFirstProductInTheListHasTheHighestPriceInTheCategory() {
        productsPage.checkSortedDir(ProductsPage.SortDirection.Descending);
    }

    @And("user sorts products by price in ascending order")
    public void userSortsProductsByPriceInAscendingOrder() {
        productsPage.toggleSorting(ProductsPage.SortDirection.Ascending);
        //productsPage.waittoappear(1000);  
        productsPage.setSortBy(ProductsPage.SortBy.Price);
    }

    @Then("the first product in the list has the minimal price in the category")
    public void theFirstProductInTheListHasTheMinimalPriceInTheCategory() {
        productsPage.checkSortedDir(ProductsPage.SortDirection.Ascending);
    }

    @When("user searches for a product by its full or partial title {string}")
    public void userSearchesForAProductByItsFullOrPartialTitle(String searchText) {
        productsPage.search(searchText);
    }

    @Then("the {int} product found contains the text {string}")
    public void theProductFoundContainsTheText(int top, String searchText) {
        //NB it's not a perfect match search, so irrelevant products may be found
        // thus limit check to first TOPn
        productsPage.checkSearchTextFound(searchText, top);
    }

    @And("user adds the found product {string} to the cart")
    public void userAddsTheFoundProductToTheCart(String searchedText) {
        theProductFoundContainsTheText(1, searchedText);
        
        productsPage.addToCart(Product.builder()
            .index(0)
            .name(searchedText)
            .quantity(1)
            .size("M")
            .color("Black")
            .build()
        );
    }

    @And("user adds the product with the maximum price to the cart")
    public void userAddsTheProductWithTheMaximumPriceToTheCart() {
        userSortsProductsByPriceInDescendingOrder();
        theFirstProductInTheListHasTheHighestPriceInTheCategory();
        productsPage.addToCart(Product.builder()
            .index(0)
            .quantity(1)
            .size("M")
            .color("Black")
            .build()
        );
    }

    @And("user adds the product with the minimal price to the cart")
    public void userAddsTheProductWithTheMinimalPriceToTheCart() {
        userSortsProductsByPriceInAscendingOrder();
        theFirstProductInTheListHasTheMinimalPriceInTheCategory();
        productsPage.addToCart(Product.builder()
            .index(0)
            .quantity(1)
            .size("XL")
            .color("Blue")
            .build()
        );
    }

    @Then("the cart is updated correctly with the added products by title, quantity, and price")
    public void theCartIsUpdatedCorrectlyWithTheAddedProductsByTitleQuantityAndPrice() {
        productsPage.showMiniCart();
        productsPage.checkMiniCartTotalQty(3);
        productsPage.checkCartHasItem("Beaumont Summit Kit", 1, 42);
        productsPage.checkCartHasItem("Lando Gym Jacket", 1, 99);
        productsPage.checkCartHasItem("Wayfarer Messenger Bag", 1, 45);
    }

}