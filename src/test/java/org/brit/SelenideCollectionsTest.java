package org.brit;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;

/**
 * @author sbrit
 */
public class SelenideCollectionsTest {
    @BeforeTest
    public void beforeTest() {
        Configuration.baseUrl = "http://the-internet.herokuapp.com";
        Configuration.startMaximized = true;
        Configuration.downloadsFolder = "myDownloads";
        open("/challenging_dom");
    }

    @Test
    public void collectionTest() {
        ElementsCollection elementsCollection = $$(".large-10.columns tbody tr");

        //get texts
        List<String> text = elementsCollection.texts();
        System.out.println(text);

        // get last
        SelenideElement lastElement = elementsCollection.last();
        System.out.println(lastElement.text());

        // get first
        SelenideElement firstElement = elementsCollection.first();
        System.out.println(firstElement.text());

        //filter
        ElementsCollection texts = elementsCollection.filter(Condition.or("texts", Condition.text("0"), Condition.text("3")));
        texts.forEach(p -> {
            System.out.println(p.text());
        });

        //exact element
        SelenideElement exactElement = elementsCollection.find(Condition.exactText("Iuvaret2\tApeirian2\tAdipisci2\tDefiniebas2\tConsequuntur2\tPhaedrum2\tedit delete"));
        System.out.println(exactElement.text());

        //Mix elements
        String element = elementsCollection
                .find(Condition.text("0"))
                .$(linkText("edit"))
                .attr("href");

        System.out.println(element);
    }

}
