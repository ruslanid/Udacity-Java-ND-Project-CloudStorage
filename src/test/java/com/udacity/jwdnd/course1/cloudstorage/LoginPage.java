package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

  private WebDriverWait wait;

  @FindBy(xpath="/html/body/div/form")
  private WebElement loginForm;

  @FindBy(id="inputUsername")
  private WebElement usernameField;

  @FindBy(id="inputPassword")
  private WebElement passwordField;

  public LoginPage(WebDriver driver) {
    wait = new WebDriverWait(driver, 30);
    PageFactory.initElements(driver, this);
  }

  public void login(String username, String password) {
    wait.until(ExpectedConditions.visibilityOf(loginForm));
    usernameField.sendKeys(username);
    passwordField.sendKeys(password);
    passwordField.submit();
  }
}
