package pl.moderntester.pages.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.moderntester.pages.basic.FormPage;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class FileHandler extends BasePage {
    private static Logger log = LoggerFactory.getLogger(FileHandler.class);
    public String defaultDirectoryPath;

    public FileHandler(WebDriver driver, String defaultDirectoryPath) {
        super(driver);
        this.defaultDirectoryPath = defaultDirectoryPath;
    }

    public ChromeOptions setDefaultDownloadDirectory() {
        File f = new File(defaultDirectoryPath);
        String absPath = f.getAbsolutePath();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", absPath);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(ChromeOptions.CAPABILITY, options);
        options.addArguments("start-maximized");
        log.info("Default download folder set");
        return options;
    }

    public int getDirectoryFileAmount(String... directoryPath) {
        if (directoryPath.length == 1) return Objects.requireNonNull(new File(directoryPath[0]).list()).length;
        else return Objects.requireNonNull(new File(defaultDirectoryPath).list()).length;
    }

    public boolean isFileDownloaded(String fileName, String... directoryPath) {
        String[] list;
        if (directoryPath.length == 1) list = new File(directoryPath[0]).list();
        else list = new File(defaultDirectoryPath).list();
        return Arrays.asList(Objects.requireNonNull(list)).contains(fileName);
    }

    public void downloadFile() {
        FormPage formPage = new FormPage(driver);
        int fileAmountBefore = getDirectoryFileAmount(defaultDirectoryPath);
        formPage.downloadButton.click();
        while (fileAmountBefore + 1 != getDirectoryFileAmount(defaultDirectoryPath)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("File downloaded");
    }
}