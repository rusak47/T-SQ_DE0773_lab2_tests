package allure;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Attachment;

public class Utils {

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] takeScreenshot(WebDriver driver) {
        System.out.println("Util: Taking screenshot");
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public static void screenShot (WebDriver driver) throws IOException, InterruptedException {
    	TakesScreenshot ts = (TakesScreenshot)driver;
    	File source=ts.getScreenshotAs(OutputType.FILE);
        File destination= new File("./screenshots/c_"+timestamp()+".png");
        FileUtils.copyFile(source, destination);
       
    }

    public static String timestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
    }

}
