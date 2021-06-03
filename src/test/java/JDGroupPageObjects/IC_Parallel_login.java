package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import base.TestCaseBase;
import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;

public class IC_Parallel_login {

	WebDriver driver;
	Action action;
	DataTable2 dataSheets;
	DataTable2 dataTable2;
	String email;
	String Password;
	String FirstName;

	public IC_Parallel_login(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;

	}

	@FindBy(xpath = "//div[@class='my-account']")
	WebElement ic_myAccountButton;

	@FindBy(xpath = "//a[contains(text(),'Log In')]")
	WebElement LoginBtn;

	@FindBy(xpath = "//input[@id='email']")
	WebElement ic_email;

	@FindBy(xpath = "//input[@id='pass']")
	WebElement ic_Password;

	@FindBy(xpath = "//button[@id='send2']")
	WebElement ic_SigninBtn;

	@FindBy(xpath = "//span[contains(text(),'Hello')]")
	WebElement ic_HelloMsg;

	public void checkParallelExecution(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber)
			throws IOException, InterruptedException {

		email = dataTable2.getValueOnCurrentModule("Email_Chrome_User");
		Password = dataTable2.getValueOnCurrentModule("Password_Chrome_User");
		FirstName = dataTable2.getValueOnCurrentModule("FirstName_Chrome_User");
		login(action, test);

		WebDriver driver2 = TestCaseBase.initializeTestBaseSetup("FIREFOX");
		PageFactory.initElements(driver2, this);
		Action action2 = new Action(driver2);

		email = dataTable2.getValueOnCurrentModule("Email_Firefox_User");
		Password = dataTable2.getValueOnCurrentModule("Password_Firefox_User");
		FirstName = dataTable2.getValueOnCurrentModule("FirstName_Firefox_User");
		login(action2, test);

		driver2.close();

	}

	public void login(Action action, ExtentTest test) throws IOException {
		String url =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"),"url");
		action.navigateToURL(url);
		action.waitForElementClickable(ic_myAccountButton,"ic_myAccountButton", 5);
		action.click(ic_myAccountButton, "My Account", test);
		action.waitForElementClickable(LoginBtn,"ic_myAccountButton", 5);
		action.click(LoginBtn, "Login In", test);
		action.waitForElementVisibility(ic_email,"ic_email", 5);
		action.writeText(ic_email, email, "email field", test);
		action.waitForElementVisibility(ic_Password,"ic_Password", 5);
		action.writeText(ic_Password, Password, "Password field", test);
		action.clickEle(ic_SigninBtn, "click ic_SigninBtn", test);
		action.explicitWait(10000);
		action.waitForElementClickable(ic_myAccountButton,"ic_myAccountButton", 10);
		action.click(ic_myAccountButton, "Click My Account Button", test);
		String expMsg = "Hello, " + FirstName + "!";
		action.waitForElementVisibility(ic_HelloMsg,"ic_HelloMsg", 10);
		String wlc_msg = action.getText(ic_HelloMsg, "Welcome Messsage for the User",test);
		action.CompareResult("Signup Validation", expMsg, wlc_msg, test);

	}

}
