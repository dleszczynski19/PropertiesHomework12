package pl.moderntester.pages.basic;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.moderntester.pages.configuration.BasePage;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class FormPage extends BasePage {
    private static Logger log = LoggerFactory.getLogger(FormPage.class);

    @FindBy(css = "#inputFirstName3")
    private WebElement firstNameInput;

    @FindBy(css = "#inputLastName3")
    private WebElement lastNameInput;

    @FindBy(css = "#inputEmail3")
    private WebElement emailInput;

    @FindBy(css = "#inputAge3")
    private WebElement ageInput;

    @FindBy(css = "#additionalInformations")
    private WebElement additionalInput;

    @FindBy(css = "[name=\"gridRadiosSex\"]")
    private List<WebElement> sexListRadio;

    @FindBy(css = "#gridRadiosMale")
    private WebElement maleRadio;

    @FindBy(css = "#gridRadiosFemale")
    private WebElement femaleRadio;

    @FindBy(css = "#gridRadiosOther")
    private WebElement otherRadio;

    @FindBy(css = "[name=\"gridRadiosExperience\"]")
    private List<WebElement> experienceListRadio;

    @FindBy(css = "[name=\"gridRadiosExperience\"]")
    private List<WebElement> professionListCheckbox;

    @FindBy(css = "#gridCheckManulTester")
    private WebElement manualCheckbox;

    @FindBy(css = "#gridCheckAutomationTester")
    private WebElement automationCheckbox;

    @FindBy(css = "#gridCheckOther")
    private WebElement otherCheckbox;

    @FindBy(css = "#selectContinents")
    private WebElement continentsSelect;

    @FindBy(css = "#selectSeleniumCommands")
    private WebElement commandsSelect;

    @FindBy(css = "#chooseFile")
    private WebElement chooseFileInput;

    @FindBy(css = "#validator-message")
    private WebElement validatorMessage;

    @FindBy(css = ".btn.btn-secondary.btn-lg")
    public WebElement downloadButton;

    @FindBy(css = "[type=\"submit\"]")
    private WebElement signInButton;

    public enum Sex {
        MALE, FEMALE, OTHER
    }

    public enum Profession {
        MANUAL, AUTOMATION, OTHER
    }

    public enum Commands {
        BROWSER, NAVIGATION, SWITCH, WAIT, WEB_ELEMENT
    }

    public FormPage(WebDriver driver) {
        super(driver);
    }

    public FormPage fillFirstName(String name) {
        firstNameInput.sendKeys(name);
        log.info("First name input filled");
        return this;
    }

    public FormPage fillLastName(String lastName) {
        lastNameInput.sendKeys(lastName);
        log.info("Last name input filled");
        return this;
    }

    public FormPage fillEmail(String email) {
        emailInput.sendKeys(email);
        log.info("Email input filled");
        return this;
    }

    public FormPage chooseSex(Sex sex) {
        switch (sex) {
            case MALE:
                maleRadio.click();
                break;
            case FEMALE:
                femaleRadio.click();
                break;
            case OTHER:
                otherRadio.click();
                break;
        }
        log.info("Sex chosen");
        return this;
    }

    public FormPage chooseRandomSex() {
        clickRandomElement(sexListRadio).click();
        log.info("Random sex chosen");
        return this;
    }

    public FormPage fillAge(int age) {
        ageInput.clear();
        ageInput.sendKeys(String.valueOf(age));
        log.info("Age input filled");
        return this;
    }

    public FormPage chooseRandomExperience() {
        clickRandomElement(experienceListRadio).click();
        log.info("Random experience chosen");
        return this;
    }

    public FormPage chooseRandomContinent() {
        Select continents = new Select(continentsSelect);
        selectRandomElement(continents);
        log.info("Random continent chosen");
        return this;
    }

    public FormPage chooseContinent(BasePage.Continents continent) {
        String text = continent.name().toLowerCase(Locale.ROOT).replaceAll("_", "-");
        Select continents = new Select(continentsSelect);
        continents.selectByValue(text);
        log.info("Continent chosen");
        return this;
    }

    public FormPage chooseProfession(Profession profession) {
        switch (profession) {
            case MANUAL:
                manualCheckbox.click();
                break;
            case AUTOMATION:
                automationCheckbox.click();
                break;
            case OTHER:
                otherCheckbox.click();
                break;
        }
        log.info("Profession selected");
        return this;
    }

    public FormPage sendFile(String filePath) {
        File file = new File(filePath);
        chooseFileInput.sendKeys(file.getAbsolutePath());
        log.info("File send");
        return this;
    }

    public FormPage signIn() {
        signInButton.click();
        return this;
    }

    public FormPage selectCommands(String[] commands) {
        Actions actions = new Actions(driver);
        Select seleniumCommands = new Select(commandsSelect);
        for (int i = 0; i < commands.length; i++) {
            seleniumCommands.selectByValue(Commands.valueOf(commands[i]).name().toLowerCase(Locale.ROOT)
                    .replaceAll("_", "") + "-commands");
            actions.keyUp(Keys.LEFT_CONTROL)
                    .build()
                    .perform();
        }
        log.info("Commands selected");
        return this;
    }

    public String getValidatorMsg() {
        return validatorMessage.getText();
    }
}