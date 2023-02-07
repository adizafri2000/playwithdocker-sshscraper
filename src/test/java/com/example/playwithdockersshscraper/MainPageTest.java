package com.example.playwithdockersshscraper;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.webdriver;
import static org.junit.jupiter.api.Assertions.*;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPageTest {
    private static Logger LOGGER = Logger.getLogger(MainPageTest.class.getName());

    HomePage homePage = new HomePage();
    DockerLoginPage dockerLoginPage = new DockerLoginPage();
    EnvironmentPage environmentPage = new EnvironmentPage();
    static PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

    private static String USERNAME = propertiesFileReader.getUsername();
    private static String PASSWORD = propertiesFileReader.getPassword();
    public String PWDLINK = propertiesFileReader.getApplicationUrl();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());

        if(USERNAME.equals("ENTER_DOCKERHUB_USERNAME") || PASSWORD.equals("ENTER_DOCKERHUB_PASSWORD"))
            fail("PLEASE SET YOUR DOCKER HUB USERNAME AND PASSWORD IN THE .properties FILE");
    }
    //https://login.docker.com/u/login/identifier?state=hKFo2SBFSFRoWVBWdTNIN2JsUU9MRk96ZTN2VEFGM2ZaZ1V5cqFur3VuaXZlcnNhbC1sb2dpbqN0aWTZIDZSZDE1ZmRabk4yUFFPYUNCeXNQdkhGczFKN3BUQzBoo2NpZNkgR3hkRjhpSG4zZ3NaOG9NWWdsZ2NnTDV6eUVNWkgzREM
    //https://login.docker.com/u/login/identifier?state=hKFo2SBabmdaY3dXNklqMHZjREVuNGRFMlQtZ3BoaVl6Wmt1N6Fur3VuaXZlcnNhbC1sb2dpbqN0aWTZIGhFT0g1d1pjZnZCLUpSd1JRSlpTWDJULUxQZG9VWEpTo2NpZNkgR3hkRjhpSG4zZ3NaOG9NWWdsZ2NnTDV6eUVNWkgzREM

    @BeforeEach
    public void setUp() {
        open(PWDLINK);
    }

    // To be cleaned up
    // Entire automation process is defined here
    @Test
    public void getToCredentialsPage(){

        // Obtain the current window
        String homePageWindow= webdriver().driver().getWebDriver().getWindowHandle();
        String otherWindow = "";
        LOGGER.info("homePageWindow = " + homePageWindow);

        homePage.loginButton.click();
        homePage.dockerOption.click();

        // A new window will popup for Docker Hub authorization
        Set s = webdriver().object().getWindowHandles();
        Iterator ite=s.iterator();

        while(ite.hasNext())
        {
            String popupHandle=ite.next().toString();
            otherWindow = popupHandle;
            LOGGER.info("popupHandle = " + otherWindow);
            if(!popupHandle.contains(homePageWindow))
            {

                // Change the focus to the Docker Hub authorization window
                webdriver().driver().switchTo().window(popupHandle);

            }
        }

        // Enter Docker Hub credentials to login
        dockerLoginPage.usernameEntry.sendKeys(USERNAME);
        dockerLoginPage.continueButton.click();
        dockerLoginPage.passwordEntry.sendKeys(PASSWORD);
        dockerLoginPage.continueButton.click();

        // Change focus to main window
        webdriver().driver().switchTo().window(homePageWindow);
        homePage.startButton.click();

        // Now in the Play with Docker's sandbox environment console
        environmentPage.addNewInstanceButton.click();

        // Wait for 4 seconds until VM instance is created and data appears
        sleep(4000);

        // SSH command is located in an input tag with attribute = "value"
        String sshCommand = environmentPage.sshCommandOutput.getAttribute("value");
        LOGGER.info("SSH COMMAND: " + sshCommand);

        // Obtain only the username and hostname from ssh credentials
        List<String> sshcred = Arrays.asList(sshCommand.split(" "));
        sshcred.forEach(LOGGER::info);
        //sshcred.remove(0);
        sshcred = Arrays.asList(sshcred.get(1).split("@"));

        // Write ssh credentials - username and hostname to sshcred.txt
        PrintWriter out = null;
        try {
            out = new PrintWriter("sshcred.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        sshcred.forEach(out::println);

        out.close();
    }
}
