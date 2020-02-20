package org.brit;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.brit.webdriverprovider.MyCustomWebDriverProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * @author sbrit
 */
public class WebDriverProviderTestClass {
    @Test
    public void customWebDriverTest() {
     //   Configuration.remote = "http://localhost:4444/wd/hub";
        Configuration.browser = MyCustomWebDriverProvider.class.getName();
        open("http://google.com");
        $(byName("q")).shouldBe(Condition.visible);
        $("#hplogo").shouldBe(Condition.visible);
    }

}
