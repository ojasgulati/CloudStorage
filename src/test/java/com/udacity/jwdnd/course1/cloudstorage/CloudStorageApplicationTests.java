package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.page.CredentialsPage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.NotePage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}



	@Test
	public void testUnAuthorized(){
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login",driver.getTitle());
	}

	@Test
	public void signupLoginAndAccessHome(){
		String username = "pzastoup";
		String password = "whatabadpassword";

		driver.get("http://localhost:" + this.port + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Peter", "Zastoupil", username, password);

		driver.get("http://localhost:" + this.port + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

		driver.get("http://localhost:" + this.port + "/logout");

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login",driver.getTitle());
	}

	private void login(){
		String username = "pzastoup";
		String password = "whatabadpassword";
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}

	@Test
	public void createEditDeleteNote(){
		login();
		String title = "ojas";
		String description = "ok";

		NotePage notePage = new NotePage(driver);
		notePage.createNote(title, description);

		driver.get("http://localhost:" + this.port + "/home");

		Note note = notePage.getFirstNote();
		Assertions.assertEquals(title, note.getNoteTitle());
		Assertions.assertEquals(description, note.getNoteDescription());

		driver.get("http://localhost:" + this.port + "/home");

		String editTitle = "ojasedit";
		String editDescription = "okedit";
		notePage.editNote(editTitle, editDescription);

		driver.get("http://localhost:" + this.port + "/home");

		Note editNote = notePage.getFirstNote();

		Assertions.assertEquals(editTitle, editNote.getNoteTitle());
		Assertions.assertEquals(editDescription, editNote.getNoteDescription());

		driver.get("http://localhost:" + this.port + "/home");
		notePage.deleteNote();

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertTrue(notePage.isNoteTitlePresent());

	}

	@Test
	public void createEditDeleteCredentials(){
		login();
		String url = "https://localhost:8080/login";
		String username = "pzastoup";
		String password = "whatabadpassword";


		CredentialsPage credentialsPage = new CredentialsPage(driver);
		credentialsPage.submitCredential(url, username, password);

		driver.get("http://localhost:" + this.port + "/home");

		Credential credential = credentialsPage.getFirstCredential();
		Assertions.assertEquals(url, credential.getUrl());
		Assertions.assertEquals(username, credential.getUsername());
		Assertions.assertNotEquals(credential.getPassword(), password);

		driver.get("http://localhost:" + this.port + "/home");

		String editUrl = "https://localhost:8080/signup";
		String editUsername = "ojas";
		String decryptPassword = credentialsPage.getDecryptPasswordAndEditCredential(editUrl, editUsername, password);

		Assertions.assertEquals(decryptPassword , password);

		driver.get("http://localhost:" + this.port + "/home");

		Credential editCredential = credentialsPage.getFirstCredential();

		Assertions.assertEquals(editUrl, editCredential.getUrl());
		Assertions.assertEquals(editUsername, editCredential.getUsername());

		driver.get("http://localhost:" + this.port + "/home");
		credentialsPage.deleteCredential();

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertTrue(credentialsPage.isCredentialTitlePresent());

	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

}
