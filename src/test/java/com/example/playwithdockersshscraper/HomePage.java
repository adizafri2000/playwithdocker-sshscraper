package com.example.playwithdockersshscraper;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class HomePage {
    public SelenideElement loginButton = $x("//*[@id=\"btnGroupDrop1\"]");
    public SelenideElement dockerOption = $x("/html/body/div/div[2]/div/div/a");
    public SelenideElement startButton = $x("//*[@id=\"landingForm\"]/p/a");
}
