package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CloudStorageApplicationTests {

  @LocalServerPort
  public int port;

  public WebDriver driver;

  @BeforeAll
  public static void beforeAll() {
    WebDriverManager.chromedriver().setup();
  }

  @BeforeEach
  public void beforeEach() {
    driver = new ChromeDriver();
  }

  @AfterEach
  public void afterEach() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Test
  @Order(1)
  @DisplayName("User can access login page")
  public void getLoginPage() {
    driver.get("http://localhost:" + this.port + "/login");
    Assertions.assertEquals("Login", driver.getTitle());
  }

  @Test
  @Order(2)
  @DisplayName("Unauthenticated user CANNOT access home page")
  public void unauthenticatedUserCannotAccessHomePage() {
    driver.get("http://localhost:" + this.port + "/home");
    Assertions.assertEquals("Login", driver.getTitle());
  }

  @Test
  @Order(3)
  @DisplayName("Authenticated user CAN access home page")
  public void authenticatedUserCanAccessHomePage() {
    driver.get("http://localhost:" + this.port + "/signup");
    SignupPage signupPage = new SignupPage(driver);
    signupPage.signup("ABC", "ZXC", "abc-zxc", "password123");

    driver.get("http://localhost:" + this.port + "/login");
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login("abc-zxc", "password123");

    WebDriverWait wait = new WebDriverWait(driver, 30);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("logoutButton")));

    Assertions.assertEquals("Home", driver.getTitle());
  }

  @Test
  @Order(4)
  @DisplayName("User CANNOT access home page AFTER logging out")
  public void userCannotAccessHomePageAfterLoggingOut() {
    driver.get("http://localhost:" + this.port + "/login");
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login("abc-zxc", "password123");

    WebDriverWait wait = new WebDriverWait(driver, 30);
    WebElement logoutButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("logoutButton")));
    logoutButton.submit();

    driver.get("http://localhost:" + this.port + "/home");

    Assertions.assertEquals("Login", driver.getTitle());
  }

  @Test
  @Order(5)
  @DisplayName("User can create a new note")
  public void userCanCreateANewNote() throws InterruptedException {
    driver.get("http://localhost:" + this.port + "/login");
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login("abc-zxc", "password123");

    NotePage notePage = new NotePage(driver);
    notePage.openNotesTab();
    notePage.addNewNote("Title of a note", "Description of a note");

    WebDriverWait wait = new WebDriverWait(driver, 30);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("success-result")));
    driver.get("http://localhost:" + this.port + "/home");

    notePage.openNotesTab();

    Thread.sleep(1000);

    WebElement editButton = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr/td[1]/button"));
    WebElement deleteButton = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr/td[1]/a"));
    WebElement noteTitle = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr/td[2]"));
    WebElement noteDescription = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr/td[3]"));

    Assertions.assertEquals(editButton.getText(), "Edit");
    Assertions.assertEquals(deleteButton.getText(), "Delete");
    Assertions.assertEquals(noteTitle.getText(), "Title of a note");
    Assertions.assertEquals(noteDescription.getText(), "Description of a note");
  }

  @Test
  @Order(6)
  @DisplayName("User can edit a note")
  public void userCanEditANote() throws InterruptedException {
    driver.get("http://localhost:" + this.port + "/login");
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login("abc-zxc", "password123");

    NotePage notePage = new NotePage(driver);
    notePage.openNotesTab();
    notePage.editNote("New title", "New description");

    WebDriverWait wait = new WebDriverWait(driver, 30);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("success-result")));
    driver.get("http://localhost:" + this.port + "/home");

    notePage.openNotesTab();

    Thread.sleep(1000);

    WebElement noteTitle = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr/td[2]"));
    WebElement noteDescription = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr/td[3]"));

    Assertions.assertEquals(noteTitle.getText(), "New title");
    Assertions.assertEquals(noteDescription.getText(), "New description");
  }

  @Test
  @Order(7)
  @DisplayName("User can delete a note")
  public void userCanDeleteANote() throws InterruptedException {
    driver.get("http://localhost:" + this.port + "/login");
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login("abc-zxc", "password123");

    NotePage notePage = new NotePage(driver);
    notePage.openNotesTab();
    notePage.deleteNote();

    WebDriverWait wait = new WebDriverWait(driver, 30);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("success-result")));
    driver.get("http://localhost:" + this.port + "/home");

    notePage.openNotesTab();

    Thread.sleep(1000);

    List<WebElement> noteRows = driver.findElements(By.xpath("//*[@id=\"userTable\"]/tbody/tr"));
    Assertions.assertTrue(noteRows.isEmpty());
  }

  @Test
  @Order(8)
  @DisplayName("User can create a new credential")
  public void userCanCreateANewCredential() throws InterruptedException {
    driver.get("http://localhost:" + this.port + "/login");
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login("abc-zxc", "password123");

    CredentialPage credentialPage = new CredentialPage(driver);
    credentialPage.openCredentialsTab();
    credentialPage.addNewCredential("http://udacity.com", "username", "password");

    WebDriverWait wait = new WebDriverWait(driver, 30);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("success-result")));
    driver.get("http://localhost:" + this.port + "/home");

    credentialPage.openCredentialsTab();

    Thread.sleep(1000);

    WebElement editButton = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[1]/button"));
    WebElement deleteButton = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[1]/a"));
    WebElement credentialUrl = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[2]"));
    WebElement credentialUsername = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[3]"));

    Assertions.assertEquals(editButton.getText(), "Edit");
    Assertions.assertEquals(deleteButton.getText(), "Delete");
    Assertions.assertEquals(credentialUrl.getText(), "http://udacity.com");
    Assertions.assertEquals(credentialUsername.getText(), "username");
  }

  @Test
  @Order(9)
  @DisplayName("User can edit a credential")
  public void userCanEditACredential() throws InterruptedException {
    driver.get("http://localhost:" + this.port + "/login");
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login("abc-zxc", "password123");

    CredentialPage credentialPage = new CredentialPage(driver);
    credentialPage.openCredentialsTab();
    credentialPage.editCredential("http://ice-mountain.com", "new-username");

    WebDriverWait wait = new WebDriverWait(driver, 30);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("success-result")));
    driver.get("http://localhost:" + this.port + "/home");

    credentialPage.openCredentialsTab();

    Thread.sleep(1000);

    WebElement credentialUrl = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[2]"));
    WebElement credentialUsername = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[3]"));

    Assertions.assertEquals(credentialUrl.getText(), "http://ice-mountain.com");
    Assertions.assertEquals(credentialUsername.getText(), "new-username");
  }

  @Test
  @Order(10)
  @DisplayName("User can delete a credential")
  public void userCanDeleteACredential() throws InterruptedException {
    driver.get("http://localhost:" + this.port + "/login");
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login("abc-zxc", "password123");

    CredentialPage credentialPage = new CredentialPage(driver);
    credentialPage.openCredentialsTab();
    credentialPage.deleteCredential();

    WebDriverWait wait = new WebDriverWait(driver, 30);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("success-result")));
    driver.get("http://localhost:" + this.port + "/home");

    credentialPage.openCredentialsTab();

    Thread.sleep(1000);

    List<WebElement> credentialRows = driver.findElements(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr"));
    Assertions.assertTrue(credentialRows.isEmpty());
  }
}
