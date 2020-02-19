package org.brit;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.brit.conditions.AttributeValueContains.attribute_contains;

/**
 * @author sbrit
 */
public class CustomConditionTest {

    @BeforeClass
    public void beforeCustomConditionTestClass() {
        open("http://the-internet.herokuapp.com/dynamic_content");
    }

    @Test
    public void customConditionTest() {
        $("#content")
                .shouldHave(attribute_contains("class", "large-12"));
    }


}
