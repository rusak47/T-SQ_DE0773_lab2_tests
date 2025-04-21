#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
#1) Launch WebDriver Chrome browser
#2) Navigate to "https://magento.softwaretestingboard.com/"
#3) Check that the page title is “Home Page" and page URL contains "https://magento.softwaretestingboard.com/"
#4) Go to the registration page using web element "Create an Account".
#5) Fill in the required fields in the form and register a new customer account.
#6) Check that registration is successfull by page URL: https://magento.softwaretestingboard.com/customer/account/ and contact information that includes customer's name, surname and e-mail address.
#7) Log out
#8) Close the browser by a driver.

@tag
Feature: Log in a customer and verify products in the cart
  description about scenarious and user stories
  
Background:
    Given user opens magento shop home page and navigates to login page
    When existing user "dgblc" "zgcqbq" logs in with "gqebhqjn@example.com" "c40C4czRNn"
    And empty cart

  @tagtest
  Scenario Outline: Testing implemented steps
    And user navigates to another <root> <product> <subproduct> category
  #  And user sorts products by price in descending order
  #  Then the first product in the list has the highest price in the category
    And user adds the product with the minimal price to the cart
    And user adds the product with the maximum price to the cart
  #ok  And user sorts products by price in ascending order
  #ok  Then the first product in the list has the minimal price in the category
  #ok  When user searches for a product by its full or partial title <searchText>
  #ok  Then the <top> product found contains the text <searchText>
    When user searches for a product by its full or partial title <searchText>
    And user adds the found product <searchText> to the cart
    Then the cart is updated correctly with the added products by title, quantity, and price

    Examples:
      |root|product       |subproduct|top|searchText|
      | "Men" | "Tops"    | "Jackets" | 3 | "Wayfarer" |
    #  | "Men" | "Bottoms" | "Shorts" | 4 | "gym" |

  @tag1
  Scenario Outline: sort products by price in descending order and verify the highest price
    And user navigates to another <root> <product> <subproduct> category
    And user sorts products by price in descending order
    Then the first product in the list has the highest price in the category

    Examples:
      |root|product       |subproduct|
      | "Men" | "Tops"    | "Jackets" |
  #    | "Men" | "Bottoms" | "Shorts" |

  @tag2
  Scenario Outline: sort products by price in ascending order and search for a product
    And user navigates to another <root> <product> <subproduct> category
    And user sorts products by price in ascending order
    Then the first product in the list has the minimal price in the category
    When user searches for a product by its full or partial title <searchText>
    Then the <top> product found contains the text <searchText>

    Examples:
      |root|product       |subproduct|top|searchText|
      | "Men" | "Tops"    | "Jackets" | 3 | "heat" |
    #  | "Men" | "Bottoms" | "Shorts" | 4 | "gym" |


  @tag3
  Scenario Outline: Add products to the cart and verify cart updates
    And user navigates to another <root> <product> <subproduct> category
    And user adds the product with the maximum price to the cart
    And user adds the product with the minimal price to the cart
    When user searches for a product by its full or partial title <searchText>
    And user adds the found product <searchText> to the cart
    Then the cart is updated correctly with the added products by title, quantity, and price

    Examples:
      |root|product       |subproduct|top|searchText|
      | "Men" | "Tops"    | "Jackets" | 3 | "Wayfarer" |
    #  | "Men" | "Bottoms" | "Shorts" | 4 | "Wayfarer" |
