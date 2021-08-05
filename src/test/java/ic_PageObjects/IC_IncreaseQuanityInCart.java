package ic_PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class IC_IncreaseQuanityInCart {

	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    IC_Cart cart;
    
    @FindBy(css = "a.go-back")
    private WebElement backButton;
    
    @FindBy(xpath = "//*[@id=\"shopping-cart-table\"]/tbody/tr[1]/td[3]//label/div/button[2]")
    private WebElement quantityAdd;
    
    @FindBy(xpath = "(//*[contains(@class, 'qty-action update update-cart-item')])")
    private WebElement updateQuantity;
    
	public IC_IncreaseQuanityInCart(WebDriver driver, DataTable2 dataTable2){
		
		this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
        cart = new IC_Cart(driver, dataTable2);
	}

	public void increaseQuantity(ExtentTest test) throws Exception {
		boolean buttonAvail = action.waitUntilElementIsDisplayed(backButton, 15000);
		if(buttonAvail) {
		backButton.click();
		}

		action.click(quantityAdd, "Increase Quantity", test);
		action.explicitWait(2000);
		action.click(updateQuantity, "Update Quantity", test);
		action.explicitWait(7000);
		String cartCount = cart.itemsInCartCounter(test);
		action.CompareResult("Cart Count After update", "2", cartCount, test);
	}
	
	
	
}
