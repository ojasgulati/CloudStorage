package com.udacity.jwdnd.course1.cloudstorage.page;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class CredentialsPage {

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmit;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "btn-add-new-credential")
    private WebElement btnAddNewCredential;

    @FindBy(className = "credentialUrlText")
    private WebElement credentialUrlText;

    @FindBy(className = "credentialUsernameText")
    private WebElement credentialUsernameText;

    @FindBy(className = "credentialPasswordText")
    private WebElement credentialPasswordText;

    @FindBy(id = "editCredentialButton")
    private WebElement editCredentialButton;

    @FindBy(id = "deleteCredentialButton")
    private WebElement deleteCredentialButton;


    private final WebDriver driver;

    public CredentialsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void submitCredential(String url, String username, String password){
        navCredentialsTab.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        btnAddNewCredential.click();
        credentialUrl.sendKeys(url);
        credentialPassword.sendKeys(password);
        credentialUsername.sendKeys(username);
        credentialUsername.submit();
    }

    public Credential getFirstCredential(){
        navCredentialsTab.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Credential credential = new Credential();
        credential.setUrl(credentialUrlText.getAttribute("innerText"));
        credential.setUsername(credentialUsernameText.getAttribute("innerText"));
        credential.setPassword(credentialPasswordText.getAttribute("innerText"));
        return credential;
    }

    public String getDecryptPasswordAndEditCredential(String url, String username, String password){
        navCredentialsTab.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        editCredentialButton.click();
        String decryptPassword = credentialPassword.getAttribute("value");

        credentialUrl.clear();
        credentialPassword.clear();
        credentialUsername.clear();

        credentialUrl.sendKeys(url);
        credentialPassword.sendKeys(password);
        credentialUsername.sendKeys(username);
        credentialUsername.submit();

        return decryptPassword;
    }

    public void deleteCredential(){
        navCredentialsTab.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        deleteCredentialButton.click();
    }

    public boolean isCredentialTitlePresent(){
        navCredentialsTab.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver.findElements(By.id("credentialUrlText")).isEmpty();
    }


}
