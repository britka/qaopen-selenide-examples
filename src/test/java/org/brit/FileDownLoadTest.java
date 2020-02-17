package org.brit;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import org.apache.commons.io.FileUtils;
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
    @Test
    public void testTestWithProxy() throws IOException {
        Configuration.downloadsFolder = "downloads";
        Configuration.proxyEnabled = true;
        Configuration.fileDownload = FileDownloadMode.PROXY;
        open("https://codepen.io/gladchinda/full/GemGNG");
        switchTo().frame("result");
        FileUtils.cleanDirectory(new File(Configuration.downloadsFolder));
        $(linkText("Export Photos")).click();
        sleep(1500);
        List<String> strings = FileUtils.readLines(new File(Configuration.downloadsFolder + "/photos.csv"), "UTF-8");
        strings.forEach(System.out::println);
    }

    @Test
    public void testTestWithoutProxy() throws IOException {
        Configuration.downloadsFolder = "downloads";
        open("http://the-internet.herokuapp.com/download");
        FileUtils.cleanDirectory(new File(Configuration.downloadsFolder));
        File file = $(linkText("UploadNotepad.txt")).download();
        List<String> strings = FileUtils.readLines(file, "UTF-8");
        strings.forEach(System.out::println);
    }

    @Test
    public void testTestWithProxy2() throws IOException {
        Configuration.downloadsFolder = "downloads";
        Configuration.proxyEnabled = true;
        Configuration.fileDownload = FileDownloadMode.PROXY;
        open("http://www.jtricks.com/bits/content_disposition.html");
        FileUtils.cleanDirectory(new File(Configuration.downloadsFolder));
        File file = $(linkText("Text file with Content-Type of text/plain.")).download();
        List<String> strings = FileUtils.readLines(file, "UTF-8");
        strings.forEach(System.out::println);
    }


}
