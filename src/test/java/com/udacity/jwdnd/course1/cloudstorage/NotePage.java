package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePage {

  private JavascriptExecutor js;
  private WebDriverWait wait;

  @FindBy(id="nav-notes-tab")
  private WebElement notesTabLink;

  @FindBy(id="add-new-note")
  private WebElement addNewNoteButton;

  @FindBy(id="note-title")
  private WebElement noteTitleField;

  @FindBy(id="note-description")
  private WebElement noteDescriptionField;

  @FindBy(id="noteSubmit")
  private WebElement noteSubmitButton;

  @FindBy(xpath="//*[@id=\"userTable\"]/tbody/tr/td[1]/button")
  private WebElement editButton;

  @FindBy(xpath="//*[@id=\"userTable\"]/tbody/tr/td[1]/a")
  private WebElement deleteButton;

  public NotePage(WebDriver driver) {
    js = (JavascriptExecutor) driver;
    wait = new WebDriverWait(driver, 30);
    PageFactory.initElements(driver, this);
  }

  public void openNotesTab() {
    wait.until(ExpectedConditions.visibilityOf(notesTabLink));
    js.executeScript("arguments[0].click();", notesTabLink);
  }

  public void addNewNote(String title, String description) {
    wait.until(ExpectedConditions.elementToBeClickable(addNewNoteButton)).click();
    wait.until(ExpectedConditions.visibilityOf(noteTitleField)).sendKeys(title);
    wait.until(ExpectedConditions.visibilityOf(noteDescriptionField)).sendKeys(description);
    js.executeScript("arguments[0].click();", noteSubmitButton);
  }

  public void editNote(String title, String description) {
    js.executeScript("arguments[0].click();", editButton);
    WebElement titleField = wait.until(ExpectedConditions.visibilityOf(noteTitleField));
    titleField.clear();
    titleField.sendKeys(title);
    WebElement descriptionField = wait.until(ExpectedConditions.visibilityOf(noteDescriptionField));
    descriptionField.clear();
    descriptionField.sendKeys(description);
    js.executeScript("arguments[0].click();", noteSubmitButton);
  }

  public void deleteNote() {
    js.executeScript("arguments[0].click();", deleteButton);
  }

}
