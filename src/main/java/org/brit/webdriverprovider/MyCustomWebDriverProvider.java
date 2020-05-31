package org.brit.webdriverprovider;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;

/**
 * @author sbrit
 */
public class  MyCustomWebDriverProvider implements com.codeborne.selenide.WebDriverProvider {
    @Override
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions firefoxOptions = new ChromeOptions();
        firefoxOptions.merge(desiredCapabilities);
        RemoteWebDriver webDriver = null;

        try {
            webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), firefoxOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return webDriver;
    }
}
