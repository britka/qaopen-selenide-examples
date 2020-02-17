package org.brit;

import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * @author sbrit
 */
public class SimpleTest {
    @Test
    public void simpleTest(){
        open("http://google.com");
        $(byName("q")).shouldBe(Condition.visible);
        $("#hplogo").shouldBe(Condition.visible);
    }
}
