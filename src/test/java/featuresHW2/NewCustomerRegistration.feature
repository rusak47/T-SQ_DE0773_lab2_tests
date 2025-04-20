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
Feature: New customer registration
  description about scenarious and user stories

  @tag1
  Scenario: new customer successful registration
    Given user opens magento shop home page and goes to registration page
    When user register with valid email
    Then user is registered succesfully
    And user logs out

  @tag2
  Scenario Outline: customer login
    Given user opens magento shop home page
    When user <firstname> <lastname> logs in with <email> <password>
    And user opens catalog of mens jackets
    Then user is on catalogue of men jackets page

    Examples:
      |email                   |password      |firstname|lastname|
      | "gqebhqjn@example.com" | "c40C4czRNn" | "dgblc" | "zgcqbq" |
      | "tbrjpkos@example.com" | "i#$E*I&_v[" | "msnxx" | "dgirpk" |
      
    # | "kyjzeqbz@example.com" | ":]sRsaf3o~" | "pgucb" | "efrklv" |
    # | "uxzxwogx@example.com" | "Xk3A}07YSy" | "mbuqj" | "hvepay" |