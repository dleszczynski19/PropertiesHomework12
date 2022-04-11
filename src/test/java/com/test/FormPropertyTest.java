package com.test;

import com.configuration.TestBase;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.moderntester.pages.basic.FormPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FormPropertyTest extends TestBase {
    private static Logger log = LoggerFactory.getLogger(FormPropertyTest.class);

    @Test
    public void shouldFillFormWithSuccess() {
        FormPage formPage = new FormPage(driver);

        formPage.fillFirstName(System.getProperty("firstName"))
                .fillLastName(System.getProperty("lastName"))
                .fillEmail(System.getProperty("email"))
                .chooseRandomSex()
                .fillAge(Integer.parseInt(System.getProperty("age")))
                .chooseRandomExperience()
                .chooseProfession(FormPage.Profession.valueOf(System.getProperty("profession")))
                .chooseRandomContinent()
                .selectCommands(System.getProperty("commandsArray").split("#"))
                .sendFile(System.getProperty("filePath"))
                .signIn();
        assertThat("Wrong validator message", formPage.getValidatorMsg(), equalTo(System.getProperty("message")));
        log.info(passed, passedMessage);
    }
}