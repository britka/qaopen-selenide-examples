package org.brit.traffic;

import com.browserup.bup.BrowserUpProxy;
import com.browserup.harreader.model.Har;
import com.browserup.harreader.model.HarEntry;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.proxy.CaptureType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;

/**
 * @author sbrit
 */
public class TrafficWithInternalProxy {
    BrowserMobProxy bmp;

    @BeforeClass
    public void beforeClass() throws IOException {
        Files.createDirectories(new File("hars").toPath());
        FileUtils.cleanDirectory(new File("hars"));
        Configuration.proxyEnabled = true;
        Configuration.startMaximized = true;
        Configuration.downloadsFolder = "myDownloads";
    }

    @Test(dataProvider = "statusCodesProvider")
    public void statusCodes(int statusCodes) {
        open("http://the-internet.herokuapp.com/status_codes");
        BrowserUpProxy proxy = WebDriverRunner.getSelenideProxy().getProxy();
        proxy.newHar();
        $$("ul li a").find(Condition.exactText("" + statusCodes)).click();
        sleep(1500);
        Har har = proxy.endHar();
        List<HarEntry> entries = har.getLog().getEntries();
        for (HarEntry entry : entries) {
            if (entry.getRequest().getUrl().equals("http://the-internet.herokuapp.com/status_codes/" + statusCodes)) {
                Assert.assertEquals(entry.getResponse().getStatus(), statusCodes);
                System.out.println(entry.getRequest().getUrl());
                System.out.println(entry.getResponse().getStatus());
                break;
            }
        }
    }

    @DataProvider
    public Object[] statusCodesProvider() {
        return new Object[]{
                200,
                301,
                404,
                500
        };
    }
}
