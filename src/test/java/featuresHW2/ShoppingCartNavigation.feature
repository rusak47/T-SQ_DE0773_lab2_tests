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

  @tag1
  Scenario: sort products by price in descending order and verify the highest price
    Given user opens magento shop home page
    When user <firstname> <lastname> logs in with <email> <password>
    And user navigates to another category
    And user sorts products by price in descending order
    Then the first product in the list has the highest price in the category

    Examples:
      |email                   |password      |firstname|lastname|
      | "gqebhqjn@example.com" | "c40C4czRNn" | "dgblc" | "zgcqbq" |
      | "tbrjpkos@example.com" | "i#$E*I&_v[" | "msnxx" | "dgirpk" |
      | "kyjzeqbz@example.com" | ":]sRsaf3o~" | "pgucb" | "efrklv" |
      | "uxzxwogx@example.com" | "Xk3A}07YSy" | "mbuqj" | "hvepay" |

  @tag2
  Scenario Outline: sort products by price in ascending order and search for a product
    Given user opens magento shop home page
    When user <firstname> <lastname> logs in with <email> <password>
    And user navigates to a category
    And user sorts products by price in ascending order
    Then the first product in the list has the minimal price in the category
    When user searches for a product by its full or partial title "<searchText>"
    Then the product found contains the text "<searchText>"

    Examples:
      |email                   |password      |firstname|lastname|searchText       |
      | "gqebhqjn@example.com" | "c40C4czRNn" | "dgblc" | "zgcqbq" | "jacket"       |
      | "tbrjpkos@example.com" | "i#$E*I&_v[" | "msnxx" | "dgirpk" | "shirt"        |
      | "kyjzeqbz@example.com" | ":]sRsaf3o~" | "pgucb" | "efrklv" | "pants"        |
      | "uxzxwogx@example.com" | "Xk3A}07YSy" | "mbuqj" | "hvepay" | "sweater"      |

  @tag3
  Scenario: Add products to the cart and verify cart updates
    Given user opens magento shop home page
    When user <firstname> <lastname> logs in with <email> <password>
    And user navigates to the third category
    And user adds the product with the maximum price to the cart
    And user adds the product with the minimal price to the cart
    When user searches for a product by its full or partial title "<searchText>"
    And user adds the found product to the cart
    Then the cart is updated correctly with the added products by title, quantity, and price

    Examples:
      |email                   |password      |firstname|lastname|searchText       |
      | "gqebhqjn@example.com" | "c40C4czRNn" | "dgblc" | "zgcqbq" | "jacket"       |
      | "tbrjpkos@example.com" | "i#$E*I&_v[" | "msnxx" | "dgirpk" | "shirt"        |
      | "kyjzeqbz@example.com" | ":]sRsaf3o~" | "pgucb" | "efrklv" | "pants"        |
      | "uxzxwogx@example.com" | "Xk3A}07YSy" | "mbuqj" | "hvepay" | "sweater"      |