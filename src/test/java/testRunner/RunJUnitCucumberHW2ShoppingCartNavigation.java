package testRunner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import io.cucumber.junit.platform.engine.Constants;
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("stepDefinitionsHW2")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME,value ="src/test/java/featuresHW2/ShoppingCartNavigation.feature")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME,value ="testRunner/stepDefinitionsHW2")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME,value= "not @tagtest")
@ConfigurationParameter(key =Constants.EXECUTION_DRY_RUN_PROPERTY_NAME,value = "false")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME,value = "pretty, html:target/cucumber-report/cucumber_hw2_shopping.html, io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
public class RunJUnitCucumberHW2ShoppingCartNavigation {

}