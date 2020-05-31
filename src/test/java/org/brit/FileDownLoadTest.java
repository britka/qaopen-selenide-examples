package org.brit;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.By.linkText;

/**
 * @author sbrit
 */
public class FileDownLoadTest {
    @BeforeClass
    public void beforeFileDownLoadTestClass() throws IOException {
        Configuration.downloadsFolder = "downloads";
       Configuration.proxyEnabled = true;
       Configuration.fileDownload = FileDownloadMode.PROXY;
//        open("http://the-internet.herokuapp.com/upload");
//        $("#file-upload").uploadFromClasspath("fileToDownload.txt");
//        $("#file-submit").click();
//        $("#uploaded-files").shouldBe(Condition.visible, Condition.exactText("fileToDownload.txt"));
//        if (new File(Configuration.downloadsFolder).exists()){
//            FileUtils.cleanDirectory(new File(Configuration.downloadsFolder));
//        }
    }

    @Test
    public void testTestWithoutProxy() throws IOException {
        open("http://the-internet.herokuapp.com/download");
        File file = $(linkText("fileToDownload.txt")).download();
        List<String> strings = FileUtils.readLines(file, "UTF-8");
        strings.forEach(System.out::println);
    }

    @Test
    public void testTestWithProxy2() throws IOException {
        open("http://www.jtricks.com/bits/content_disposition.html");
     //   File file = $(linkText("Text file with Content-Type of text/plain.")).download();
        File file = $(linkText("Text file with Content-Type of application/x-unknown.")).download();
        List<String> strings = FileUtils.readLines(file, "UTF-8");
        strings.forEach(System.out::println);
    }


}
