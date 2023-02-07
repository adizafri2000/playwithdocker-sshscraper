package com.example.playwithdockersshscraper;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DockerLoginPage {
    public SelenideElement usernameEntry = $x("//*[@id=\"username\"]");
    public SelenideElement continueButton = $x("/html/body/div[2]/main/section/div/div/div/form/div[2]/button");
    public SelenideElement passwordEntry = $x("//*[@id=\"password\"]");
}
