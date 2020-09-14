package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {

  private JavascriptExecutor js;
  private WebDriverWait wait;

  @FindBy(id="nav-credentials-tab")
  private WebElement credentialsTabLink;

  @FindBy(id="add-new-credential")
  private WebElement addNewCredentialButton;

  @FindBy(id="credential-url")
  private WebElement credentialUrlField;

  @FindBy(id="credential-username")
  private WebElement credentialUsernameField;

  @FindBy(id="credential-password")
  private WebElement credentialPasswordField;

  @FindBy(id="credentialSubmit")
  private WebElement credentialSubmitButton;

  @FindBy(xpath="//*[@id=\"credentialTable\"]/tbody/tr/td[1]/button")
  private WebElement editButton;

  @FindBy(xpath="//*[@id=\"credentialTable\"]/tbody/tr/td[1]/a")
  private WebElement deleteButton;

  public CredentialPage(WebDriver driver) {
    js = (JavascriptExecutor) driver;
    wait = new WebDriverWait(driver, 30);
    PageFactory.initElements(driver, this);
  }

  public void openCredentialsTab() {
    wait.until(ExpectedConditions.visibilityOf(credentialsTabLink));
    js.executeScript("arguments[0].click();", credentialsTabLink);
  }

  public void addNewCredential(String url, String username, String password) {
    wait.until(ExpectedConditions.elementToBeClickable(addNewCredentialButton)).click();
    wait.until(ExpectedConditions.visibilityOf(credentialUrlField)).sendKeys(url);
    wait.until(ExpectedConditions.visibilityOf(credentialUsernameField)).sendKeys(username);
    wait.until(ExpectedConditions.visibilityOf(credentialPasswordField)).sendKeys(password);
    js.executeScript("arguments[0].click();", credentialSubmitButton);
  }

  public void editCredential(String url, String username) {
    js.executeScript("arguments[0].click();", editButton);
    WebElement urlField = wait.until(ExpectedConditions.visibilityOf(credentialUrlField));
    urlField.clear();
    urlField.sendKeys(url);
    WebElement usernameField = wait.until(ExpectedConditions.visibilityOf(credentialUsernameField));
    usernameField.clear();
    usernameField.sendKeys(username);
    js.executeScript("arguments[0].click();", credentialSubmitButton);
  }

  public void deleteCredential() {
    js.executeScript("arguments[0].click();", deleteButton);
  }

}
