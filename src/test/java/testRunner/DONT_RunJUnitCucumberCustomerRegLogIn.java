package testRunner;

import org.junit.jupiter.api.Disabled;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import io.cucumber.junit.platform.engine.Constants;
@Disabled //doesnt work
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("stepDefinitions")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME,value ="src/test/java/features/NewCustomerRegistration.feature")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME,value ="testRunner/stepDefinitions")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME,value= "@tag2")
@ConfigurationParameter(key =Constants.EXECUTION_DRY_RUN_PROPERTY_NAME,value = "false")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME,value ="pretty, html:target/cucumber-report/cucumber.html")
public class DONT_RunJUnitCucumberCustomerRegLogIn {

}