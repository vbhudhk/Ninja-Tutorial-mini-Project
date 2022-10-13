package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {

	WebDriver driver;
	
	public LandingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//span[@class='caret']")
	private WebElement myAccountDropDown;
	
	@FindBy(xpath="//a[text()='Login']")
	private WebElement logInOption;
	
	public WebElement myAccountDropDown() {
		return myAccountDropDown;
	}
	
	public WebElement logInOption() {
		return logInOption;
	}
	
}
