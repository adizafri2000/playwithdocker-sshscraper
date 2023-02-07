package com.example.playwithdockersshscraper;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class EnvironmentPage {

    public SelenideElement addNewInstanceButton =  $x("//*[@id=\"popupContainer\"]/md-sidenav/md-content/button/span");
    public SelenideElement sshCommandOutput = $x("//*[@id=\"input_3\"]");
}
