package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private UserService userService;

	@Autowired
	private CredentialService credentialService;

	@Autowired
	private EncryptionService encryptionService;

	private WebDriver driver;

	private  WebDriverWait wait;

	private String baseURL;

	private JavascriptExecutor js;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		this.wait = new WebDriverWait(driver, 2);
		this.baseURL = "http://localhost:" + port;
		this.js = (JavascriptExecutor) driver;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	/**
	 * Verify that an unauthorized user can only access the login and signup pages.
	 */
	@Test
	public void pageAccessRestriction_unauthorizedUser_onlyCanAccessLoginAndSignupPage() {
		// test login access
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/home/file");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/logout");
		Assertions.assertEquals("Login", driver.getTitle());

		// test signup access
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}


	/**
	 * Verify that the home page is accessible after login and is no longer accessible after logout.
	 */
	@Test
	public void pageAccessRestriction_signupLoginAndLogoutUser_homePageNotAccessibleAfterLogout() {
		// test access home page after login
		// Create a test account
		doMockSignUp("Home Page Restriction","Test","HPRT","123");
		doLogIn("HPRT", "123");

		Assertions.assertEquals("Home", driver.getTitle());

		// test access home page after logout
		HomePage homePage = new HomePage(driver);
		homePage.logout();
		driver.get(baseURL + "/home");

		Assertions.assertFalse(driver.getTitle().matches("Home"));
	}

	/**
	 * Create a note and verify it is displayed.
	 */
	@Test
	public void noteOperation_createNote_displayNewNote() {
		// Create a test account
		doMockSignUp("Create Note","Test","CNT","123");
		doLogIn("CNT", "123");

		// switch to note tab
		HomePage homePage = new HomePage(driver);
		js.executeScript("arguments[0].click();", homePage.getNoteTab());

		// open note modal
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddNoteBtn()));
		js.executeScript("arguments[0].click();", homePage.getAddNoteBtn());

		// add and save note
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getSaveNoteBtn()));
		homePage.addNote("Hello","World");

		// return to home page
		ResultPage resultPage = new ResultPage(driver);
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());

		// get displayed note
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddNoteBtn()));
		Note displayedNote = homePage.getNotes().get(0);

		Assertions.assertEquals("Hello", displayedNote.getNoteTitle());
		Assertions.assertEquals("World", displayedNote.getNoteDescription());
	}

	/**
	 * Edit an existing note and verify that the changes are displayed.
	 */
	@Test
	public void noteOperation_editNote_displayNoteChanges() {
		// Create a test account
		doMockSignUp("Edit Note","Test","ENT","123");
		doLogIn("ENT", "123");

		// switch to note tab
		HomePage homePage = new HomePage(driver);
		js.executeScript("arguments[0].click();", homePage.getNoteTab());

		// open note modal
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddNoteBtn()));
		js.executeScript("arguments[0].click();", homePage.getAddNoteBtn());

		// add and save note
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getSaveNoteBtn()));
		homePage.addNote("Hello","World");

		// return to home page
		ResultPage resultPage = new ResultPage(driver);
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());

		// open note modal
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddNoteBtn()));
		js.executeScript("arguments[0].click();", homePage.getEditNoteBtn().get(0));

		// edit and save note
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getSaveNoteBtn()));
		homePage.editNote("Hello!", "World!");

		// return to home page
		resultPage = new ResultPage(driver);
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());

		// get displayed note
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddNoteBtn()));
		Note displayedNote = homePage.getNotes().get(0);

		Assertions.assertEquals("Hello!", displayedNote.getNoteTitle());
		Assertions.assertEquals("World!", displayedNote.getNoteDescription());
	}

	/**
	 * Delete a note and verify that the note is no longer displayed.
	 */
	@Test
	public void noteOperation_deleteNote_noteNotDisplay() {
		// Create a test account
		doMockSignUp("Delete Note","Test","DNT","123");
		doLogIn("DNT", "123");

		// switch to note tab
		HomePage homePage = new HomePage(driver);
		js.executeScript("arguments[0].click();", homePage.getNoteTab());

		// open note modal
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddNoteBtn()));
		js.executeScript("arguments[0].click();", homePage.getAddNoteBtn());

		// add and save note
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getSaveNoteBtn()));
		homePage.addNote("Hello","World");

		// return to home page
		ResultPage resultPage = new ResultPage(driver);
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());

		// delete note
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddNoteBtn()));
		homePage.deleteNote(0);

		// return to home page
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());

		// get displayed note
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddNoteBtn()));
		List<Note> displayedNote = homePage.getNotes();

		Assertions.assertEquals(0, displayedNote.size());
	}

	/**
	 * Create a set of credentials, verifies that they are displayed,
	 * and verifies that the displayed password is encrypted.
	 */
	@Test
	public void credentialOperation_createCredentials_displayNewCredentialAndEncryptedPassword() {
		// Create a test account
		doMockSignUp("Create Credentials","Test","CCT","123");
		doLogIn("CCT", "123");

		// switch to credential tab
		HomePage homePage = new HomePage(driver);
		js.executeScript("arguments[0].click();", homePage.getCredentialTab());

		// open credential modal
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialBtn()));
		js.executeScript("arguments[0].click();", homePage.getAddCredentialBtn());

		// add and save credential1
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getSaveCredentialBtn()));
		homePage.addCredential("facebook.com", "admin1", "pass1");

		// return to home page
		ResultPage resultPage = new ResultPage(driver);
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());

		// open credential modal
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialBtn()));
		js.executeScript("arguments[0].click();", homePage.getAddCredentialBtn());

		// add and save credential2
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getSaveCredentialBtn()));
		homePage.addCredential("google.com", "admin2", "pass2");

		// return to home page
		resultPage = new ResultPage(driver);
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());

		// get displayed credential
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialBtn()));
		List<Credential> displayedCredentials = homePage.getEncryptedCredentials();
		Integer userId = userService.getUser("CCT").getUserId();

		// check credential1
		Credential credentialData = credentialService.getCredentialList(userId).get(0);
		Credential credentialDisplay = displayedCredentials.get(0);
		Assertions.assertEquals("facebook.com", credentialDisplay.getUrl());
		Assertions.assertEquals("admin1", credentialDisplay.getUsername());
		Assertions.assertEquals(encryptionService.encryptValue("pass1",credentialData.getKey()), credentialDisplay.getPassword());

		// check credential2
		credentialData = credentialService.getCredentialList(userId).get(1);
		credentialDisplay = displayedCredentials.get(1);
		Assertions.assertEquals("google.com", credentialDisplay.getUrl());
		Assertions.assertEquals("admin2", credentialDisplay.getUsername());
		Assertions.assertEquals(encryptionService.encryptValue("pass2",credentialData.getKey()), credentialDisplay.getPassword());
	}

	/**
	 * View an existing set of credentials, verifies that the viewable password is unencrypted
	 */
	@Test
	public void credentialOperation_viewCredentials_showUnencryptedPassword() {
		// Create a test account
		doMockSignUp("View Credentials","Test","VCT","123");
		doLogIn("VCT", "123");

		// switch to credential tab
		HomePage homePage = new HomePage(driver);
		js.executeScript("arguments[0].click();", homePage.getCredentialTab());

		// open credential modal
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialBtn()));
		js.executeScript("arguments[0].click();", homePage.getAddCredentialBtn());

		// add and save credential1
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getSaveCredentialBtn()));
		homePage.addCredential("facebook.com", "admin1", "pass1");

		// return to home page
		ResultPage resultPage = new ResultPage(driver);
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());

		// open credential modal
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialBtn()));
		js.executeScript("arguments[0].click();", homePage.getAddCredentialBtn());

		// add and save credential2
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getSaveCredentialBtn()));
		homePage.addCredential("google.com", "admin2", "pass2");

		// return to home page
		resultPage = new ResultPage(driver);
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());

		// open credential modal
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialBtn()));
		js.executeScript("arguments[0].click();", homePage.getEditCredentialBtn().get(0));

		// check password1
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getSaveCredentialBtn()));
		Assertions.assertEquals("pass1", homePage.getCredentialPasswordField().getAttribute("value"));
		homePage.closeCredentialModal();

		// open credential modal
		js.executeScript("arguments[0].click();", homePage.getEditCredentialBtn().get(1));

		// check password2
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getSaveCredentialBtn()));
		Assertions.assertEquals("pass2", homePage.getCredentialPasswordField().getAttribute("value"));
	}

	/**
	 * Edit the credentials and verify that the changes are displayed.
	 */
	@Test
	public void credentialOperation_editCredentials_displayCredentialsChanges() {
		// Create a test account
		doMockSignUp("Edit Credentials","Test","ECT","123");
		doLogIn("ECT", "123");

		// switch to credential tab
		HomePage homePage = new HomePage(driver);
		js.executeScript("arguments[0].click();", homePage.getCredentialTab());

		// open credential modal
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialBtn()));
		js.executeScript("arguments[0].click();", homePage.getAddCredentialBtn());

		// add and save credential1
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getSaveCredentialBtn()));
		homePage.addCredential("facebook.com", "admin1", "pass1");

		// return to home page
		ResultPage resultPage = new ResultPage(driver);
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());

		// open credential modal
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialBtn()));
		js.executeScript("arguments[0].click();", homePage.getAddCredentialBtn());

		// add and save credential2
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getSaveCredentialBtn()));
		homePage.addCredential("google.com", "admin2", "pass2");

		// return to home page
		resultPage = new ResultPage(driver);
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());

		// open credential modal
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialBtn()));
		js.executeScript("arguments[0].click();", homePage.getEditCredentialBtn().get(0));

		// edit and save credential
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getSaveCredentialBtn()));
		homePage.editCredential("youtube.com", "admin3", "pass3");

		// return to home page
		resultPage = new ResultPage(driver);
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialBtn()));
		Integer userId = userService.getUser("ECT").getUserId();

		// check edited credential1
		Credential credentialData = credentialService.getCredentialList(userId).get(0);
		Credential credentialDisplay = homePage.getEncryptedCredentials().get(0);
		Assertions.assertEquals("youtube.com", credentialDisplay.getUrl());
		Assertions.assertEquals("admin3", credentialDisplay.getUsername());
		Assertions.assertEquals(encryptionService.encryptValue("pass3",credentialData.getKey()), credentialDisplay.getPassword());

		// open credential modal
		js.executeScript("arguments[0].click();", homePage.getEditCredentialBtn().get(1));

		// edit and save credential
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getSaveCredentialBtn()));
		homePage.editCredential("instagram.com", "admin4", "pass4");

		// return to home page
		resultPage = new ResultPage(driver);
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialBtn()));

		// check edited credential2
		credentialData = credentialService.getCredentialList(userId).get(1);
		credentialDisplay = homePage.getEncryptedCredentials().get(1);
		Assertions.assertEquals("instagram.com", credentialDisplay.getUrl());
		Assertions.assertEquals("admin4", credentialDisplay.getUsername());
		Assertions.assertEquals(encryptionService.encryptValue("pass4",credentialData.getKey()), credentialDisplay.getPassword());
	}

	/**
	 * Delete an existing set of credentials and verify that the credentials are no longer displayed.
	 */
	@Test
	public void credentialOperation_deleteCredentials_credentialsNotDisplay() {
		// Create a test account
		doMockSignUp("Delete Credentials","Test","DCT","123");
		doLogIn("DCT", "123");

		// switch to credential tab
		HomePage homePage = new HomePage(driver);
		js.executeScript("arguments[0].click();", homePage.getCredentialTab());

		// open credential modal
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialBtn()));
		js.executeScript("arguments[0].click();", homePage.getAddCredentialBtn());

		// add and save credential1
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getSaveCredentialBtn()));
		homePage.addCredential("facebook.com", "admin1", "pass1");

		// return to home page
		ResultPage resultPage = new ResultPage(driver);
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());

		// open credential modal
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialBtn()));
		js.executeScript("arguments[0].click();", homePage.getAddCredentialBtn());

		// add and save credential2
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getSaveCredentialBtn()));
		homePage.addCredential("google.com", "admin2", "pass2");

		// return to home page
		resultPage = new ResultPage(driver);
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());

		// delete credential1
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialBtn()));
		homePage.deleteCredential(0);

		// return to home page
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());

		// delete credential2
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialBtn()));
		homePage.deleteCredential(0);

		// return to home page
		js.executeScript("arguments[0].click();", resultPage.getContinueLink());

		// get displayed credential
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getAddCredentialBtn()));
		List<Credential> displayedCredential = homePage.getEncryptedCredentials();

		Assertions.assertEquals(0, displayedCredential.size());
	}
}
