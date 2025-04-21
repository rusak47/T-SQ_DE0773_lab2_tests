
# Web Application Testing Framework

This project demonstrates automated testing of web applications using the following tools and methodologies:

- **JUnit** tests integrated with **Selenium WebDriver** for browser automation.
- Implementation of the **Page Object Model (POM)** pattern, compared with direct `WebElement` access for maintainable and reusable test code.
- **Cucumber** for behavior-driven development (BDD), enabling the creation of human-readable test scenarios.
- **Allure** for generating comprehensive test reports, including screenshots, logs, and test data.

The framework is built using **Maven**, ensuring dependency management and enabling the generation of detailed HTML reports post-test execution. It is designed to streamline the testing process and improve the reliability of web application testing.

# Conclusions

- The Cucumber runner has a known issue where the **WebDriver** instance is initialized twice. 
    - **Workaround:** A static WebDriver instance was used to mitigate this problem. Further investigation is needed to identify a permanent fix.
- Hosting the web server for tests locally, where the package is built, significantly reduces timeouts and improves the reliability of `WebElement` interactions.
- The **Page Object Model (POM)** is a more structured and maintainable approach for web testing compared to direct `WebElement` access. However, it can become cumbersome and fragile as the application grows in complexity.
- Debugging tests that involve multiple steps can be challenging. Enhanced logging and modular test design can help improve traceability and ease of debugging.

This framework aims to provide a robust foundation for automated web application testing while addressing common challenges in test execution and maintenance.