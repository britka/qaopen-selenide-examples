package org.brit.traffic;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
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

/**
 * @author sbrit
 */
public class TrafficWithExternalProxy {

    BrowserMobProxy bmp;

    @BeforeClass
    public void beforeClass() throws IOException {
        Files.createDirectories(new File("hars").toPath());
        FileUtils.cleanDirectory(new File("hars"));
        bmp = new BrowserMobProxyServer();
        bmp.setTrustAllServers(true);
        bmp.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());
        bmp.setHarCaptureTypes(CaptureType.getHeaderCaptureTypes());
        bmp.start(0);
        Configuration.startMaximized = true;
    }

    @AfterClass
    public void afterClass() {
        bmp.stop();
    }

    @Test(dataProvider = "statusCodesProvider")
    public void statusCodesWithBMP(int statusCodes) throws IOException {
        Proxy seleniumProxy = net.lightbody.bmp.client.ClientUtil.createSeleniumProxy(bmp);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        Configuration.browserCapabilities = capabilities;
        open("http://the-internet.herokuapp.com/status_codes");
        bmp.newHar();
        $$("ul li a").find(Condition.exactText("" + statusCodes)).click();
        sleep(1500);
        net.lightbody.bmp.core.har.Har har = bmp.endHar();
        List<net.lightbody.bmp.core.har.HarEntry> entries = har.getLog().getEntries();
        for (net.lightbody.bmp.core.har.HarEntry entry : entries) {
            if (entry.getRequest().getUrl().equals("http://the-internet.herokuapp.com/status_codes/" + statusCodes)) {
                Assert.assertEquals(entry.getResponse().getStatus(), statusCodes);
                System.out.println(entry.getRequest().getUrl());
                System.out.println(entry.getResponse().getStatus());
                break;
            }
        }
        har.writeTo(new File("hars/" + statusCodes + ".har"));
        sleep(1500);
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
