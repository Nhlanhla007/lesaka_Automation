package evs_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class EVS_CompareProducts {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	EVS_ProductSearch evs_products;

	public EVS_CompareProducts(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
	}

	@FindBy(xpath = "//div[@data-ui-id='message-success']/div")
	public WebElement addToCompareMsg;

	@FindBy(xpath = "//span[contains(text(),'Compare items')]")
	public WebElement compareItemTab;

	@FindBy(xpath = "//span[contains(text(),'Remove all')]")
	public WebElement removeAll;

	@FindBy(xpath = "//button[contains(@class,'action-primary action-accept')]")
	public WebElement alertAccept;

	@FindBy(xpath = "//div[contains(text(),'You cleared the comparison list.')]")
	public WebElement comparisionListClearMsg;

	@FindBy(xpath = "//div[contains(text(),'You cleared the comparison list.')]")
	public WebElement clearMsg;

	@FindBy(xpath = "//span[contains(text(),'Compare Items')]")
	public WebElement compareItemHeader;

	@FindBy(xpath = "//span[contains(text(),\"Back to Search results for\")]")
	public WebElement backButton;

	@FindBy(xpath = "//strong[@class='product-item-name']/a")
	public List<WebElement> compareItemList;

	public void validateCompare(ExtentTest test) throws IOException {
//		compareItem(test);
		navigateToSearchResult(test);
		removeAllItem(test);

	}

	public void comparePageValidation(String product, ExtentTest test) throws IOException {

		String expMsg = "You added product " + product + " to the comparison list.";
		String actualMsg = action.getText(addToCompareMsg, "Success message", test);
		boolean successMsg = addToCompareMsg.isDisplayed();
		if (successMsg) {
			action.CompareResult("Validate message", expMsg, actualMsg, test); 
			
//			JavascriptExecutor executor = (JavascriptExecutor)driver;		
//			Boolean disabled = (Boolean) executor.executeScript("return arguments[0].hasAttribute(\"disabled\");", compareItemTab);			
//			if(!disabled){
//				compareItem(test);				
//			}
			
			

			
			
			
		} else {
			action.CompareResult("Comparision failure", "true", String.valueOf(successMsg), test);
		}
		

	}
	

	public void compareItem(ExtentTest test) throws IOException {

		List<String> actualItemToCompareProducts = new ArrayList<String>();
		action.clickEle(compareItemTab, "Compare Item", test);
		if (action.isElementPresent(compareItemHeader)) {
			List<WebElement> compareItem = compareItemList;

			for (WebElement ele : compareItem) {
				String prodName = ele.getAttribute("title");
				actualItemToCompareProducts.add(prodName);
			}

			Map<String, List<String>> AllProductsWishlist = evs_products.productData;
			Set<String> expectedItemsToCompare = AllProductsWishlist.keySet();

			boolean checkCompateItem = actualItemToCompareProducts.containsAll(expectedItemsToCompare);

			action.CompareResult("Check Items in Compate List", "true", String.valueOf(checkCompateItem), test);
		}

	}

	public void navigateToSearchResult(ExtentTest test) throws IOException {
		if (action.isElementPresent(backButton)) {
			action.clickEle(backButton, "Back button", test);
			action.explicitWait(6000);
		}
	}

	public void removeAllItem(ExtentTest test) throws IOException {
		if (action.isElementPresent(removeAll)) {
			action.scrollToElement(removeAll, "Remove All");
			action.explicitWait(2000);
			action.clickEle(removeAll, "Remove All", test);
			action.explicitWait(2000);
			action.click(alertAccept, "Alert accept", test);
			String expectedMsg = "You cleared the comparison list.";
			String actualdMsg = action.getText(clearMsg, "Clear Message", test);
			action.CompareResult("Clearance Message", expectedMsg, actualdMsg, test);

		}
	}

}
