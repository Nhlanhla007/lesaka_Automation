package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;

import Logger.Log;
import utils.Action;

public class IC_Cart {
		
	  WebDriver driver;
	    Action action;

	    public IC_Cart(WebDriver driver) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);

	    }
	    static Logger logger = Log.getLogData(Action.class.getSimpleName());
	    
	    @FindBy(xpath="/html/body/div[1]/header/div[2]/div/div[3]/div[3]/a")
	    private WebElement iCCartButton;
	    
	    //loop throUGH THIS HERE TO DETERMINE IF THE PRODUCT IS FOUND, IF FOUND VALIDATE QUANTITY AND PRICE
	    @FindBy(xpath="//*[@id=\"mini-cart\"]/li")
	    private List<WebElement> icAllCartProducts;
	    
	    @FindBy(xpath="//*[@id=\"minicart-content-wrapper\"]/div[2]/div[2]/div[1]/div/span/span")
	    private WebElement icSubtotal;
	    
	    @FindBy(xpath="//*[@id=\"top-cart-btn-checkout\"]/span")
	    private WebElement icCCheckout;
	    
		
	    public void navigateToCart(ExtentTest test) {
	    	try {
				action.clickEle(iCCartButton, "IC cart button", test);
			} catch (Exception e) {
				// TODO: handle exception
			}
	    }
	    
	    int sum = 0;
		  public void iCcartVerification2(Map<String, List<String>> products,ExtentTest test) {
			  //Find all elements from the list
			  try {
				for(WebElement productsInCart : icAllCartProducts) {
					  String nameOfProduct = productsInCart.findElement(By.xpath(".//strong/a")).getText();
					  String price = productsInCart.findElement(By.xpath(".//span/span/span/span")).getText();					  
					  WebElement quantityTag = productsInCart.findElement(By.xpath(".//div[2]/input"));
					  String quantity = action.getAttribute(quantityTag, "data-item-qty");
					  sum += (Integer.parseInt(quantity)*Integer.parseInt(price.replace("R", "").replace(",", "")));
					  for(Map.Entry selectedProducts : products.entrySet()) {
						  //@SuppressWarnings("unchecked")
						List<String> data = (List<String>)selectedProducts.getValue();
						if(selectedProducts.getKey().equals(nameOfProduct)) {
						  action.CompareResult("Name : " + nameOfProduct , (String)selectedProducts.getKey(), nameOfProduct, test);
						  action.CompareResult("Price : " +price +" for " +nameOfProduct, data.get(0), price, test);
						  action.CompareResult("Quantity : " + quantity +" for " + nameOfProduct, data.get(1), quantity, test);						  
						}
					  }
				  }
				action.CompareResult("Total", String.valueOf(sum), icSubtotal.getText().replace("R", "").replace(",", "").replace(".", "") , test);
				action.clickEle(icCCheckout, "Secure Checkout", test);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
			}
			  //Compare with data from the map
			  
		  }
		  

	    public void cartButtonValidation(WebElement addToCartButton,ExtentTest test) {
			 try {
					  WebElement addToCartField = addToCartButton.findElement(By.xpath("./button/span"));
					  if(addToCartField.getText().equalsIgnoreCase("Adding...")) {
						  action.CompareResult("Adding product " , "Adding...", addToCartField.getText(), test);
					  }
					  String status2 = addToCartField.getText();
					  if(status2.equalsIgnoreCase("Added")) {
						  action.CompareResult("Added Confirmation", "Added", status2, test);
					  }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
			}
			  
		 }
	    
	    public Map<String, List<String>> productQuantityAndPrice(Map<String, String> products,List<String> quantity,ExtentTest test) {
	    	Map<String, List<String>> productData = new HashMap<String, List<String>>();
	    	
	    	for(Iterator<String> iterator = quantity.iterator(); iterator.hasNext(); ) {
	    		
	    		//find quantity --Add it to the list
	    		for(Map.Entry map : products.entrySet()) {
	    		//find name of product add it as the key
	    			List<String> priceAndQuantity = new ArrayList<>();
	    			String key = (String)map.getKey();
	    			String value = (String)map.getValue();
	    			priceAndQuantity.add(value);
	    			String quantityNum = iterator.next();
	    			
	    			
	    			priceAndQuantity.add(quantityNum);	    			
	    			iterator.remove();	
	    			
	    		productData.put(key, priceAndQuantity);
	    		}
	    	}	    	
	    	return productData;
	    }
}