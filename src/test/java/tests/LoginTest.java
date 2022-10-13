package tests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObject.AccountPage;
import pageObject.LandingPage;
import pageObject.LoginPage;
import resources.BaseClass;

public class LoginTest extends BaseClass {
	public WebDriver driver;
	Logger log;

	@BeforeMethod
	public void openApplication() throws IOException {
		log = LogManager.getLogger(LoginTest.class.getName());
		driver = initializeBrowser();
		log.debug("Browser got launched");
		driver.get(prop.getProperty("url"));
		log.debug("Navigated to application URL");
	}

	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = { { "vaibhavdharmik27@gmail.com", "V@ibh@v1994", "Successfull" },
				{ "abc@rest.com", "123", "Failure" } };
		return data;
	}

	@Test(dataProvider = "getLoginData")
	public void verifyLogIn(String email, String password, String expResult) throws IOException {
		// Object of the LandingPage
		LandingPage landingPage = new LandingPage(driver);
		landingPage.myAccountDropDown().click();
		log.debug("Clicked on My Account dropdown");
		landingPage.logInOption().click();
		log.debug("Clicked on login option");

		// Object of the LoginPage
		LoginPage loginPage = new LoginPage(driver);
		//// loginPage.emailAddressField().sendKeys(prop.getProperty("email"));
		loginPage.emailAddressField().sendKeys(email);
		log.debug("Email addressed got entered");
		//// loginPage.passwordField().sendKeys(prop.getProperty("password"));
		loginPage.passwordField().sendKeys(password);
		log.debug("Password got entered");
		loginPage.loginButton().click();
		log.debug("Clicked on Login Button");
		// Object of the AccountPage
		AccountPage accountPage = new AccountPage(driver);
		String actResult = null;
		try {
			String actText = accountPage.myAccount().getText();
			String expText = "My Account";
			boolean result = actText.equals(expText);

			Assert.assertTrue(result);
			if (result) {
				log.debug("User got logged in");
				actResult = "Successfull";
			}
		} catch (Exception e) {
			log.debug("User didn't log in");
			actResult = "Failure";
		}
		Assert.assertNotEquals(actResult, expResult);
		// Assert.assertEquals(actResult, expResult);
		log.info("Login Test got passed");
	}

	@AfterMethod
	public void Closure() {
		driver.close();
		log.debug("Browser got closed");
	}

}
