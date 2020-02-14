package org.brit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

/**
 * @author sbrit
 */
public class WebdriverManagerTests {
    @Test
    public void wdm1() {
        WebDriverManager.chromedriver().version("80").setup();
        open("http://google.com");
        sleep(2000);
    }

    @Test
    public void wdm2() {
        WebDriverManager.chromedriver().version("77").setup();
        open("http://google.com");
        sleep(2000);
    }

    @Test
    public void wdm3() {
        WebDriverManager.chromedriver().clearCache();
        WebDriverManager.chromedriver().version("79").setup();
        open("http://google.com");
        sleep(2000);
    }
}
