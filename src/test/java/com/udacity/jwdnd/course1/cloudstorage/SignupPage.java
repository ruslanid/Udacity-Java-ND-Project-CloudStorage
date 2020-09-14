package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {

  private WebDriverWait wait;

  @FindBy(xpath="/html/body/div/form")
  private WebElement signupForm;

  @FindBy(id="signup-success")
  private WebElement successfulSignupMessage;

  @FindBy(id="inputFirstName")
  private WebElement firstNameField;

  @FindBy(id="inputLastName")
  private WebElement lastNameField;

  @FindBy(id="inputUsername")
  private WebElement usernameField;

  @FindBy(id="inputPassword")
  private WebElement passwordField;

  public SignupPage(WebDriver driver) {
    wait = new WebDriverWait(driver, 30);
    PageFactory.initElements(driver, this);
  }

  public void signup(String firstName, String lastName, String username, String password) {
    wait.until(ExpectedConditions.visibilityOf(signupForm));
    firstNameField.sendKeys(firstName);
    lastNameField.sendKeys(lastName);
    usernameField.sendKeys(username);
    passwordField.sendKeys(password);
    passwordField.submit();
    wait.until(ExpectedConditions.visibilityOf(successfulSignupMessage));
  }
}
