package org.brit.conditions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Driver;
import org.openqa.selenium.WebElement;

/**
 * @author sbrit
 */
public class AttributeValueContains extends Condition {
    private String attributeName;
    private String containsValue;


    public AttributeValueContains(String attributeName, String containsValue) {
        super("attribute_contains");
        this.attributeName = attributeName;
        this.containsValue = containsValue;
    }

    @Override
    public String actualValue(Driver driver, WebElement element) {
        return String.format("%s=\"%s\"", attributeName, getAttributeValue(element));
    }

    @Override
    public boolean apply(Driver driver, WebElement element) {
        return getAttributeValue(element).contains(containsValue);
    }

    @Override
    public String toString() {
        return String.format("%s %s=\"%s\"", getName(), attributeName, containsValue);
    }

    private String getAttributeValue(WebElement element) {
        String attr = element.getAttribute(attributeName);
        return attr == null ? "" : attr;
    }

    public static Condition attribute_contains(String attributeName, String expectedAttributeValue) {
        return new AttributeValueContains(attributeName, expectedAttributeValue);
    }
}
