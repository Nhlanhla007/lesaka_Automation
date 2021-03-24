package JDGroupPageObjects;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import io.qameta.allure.Step;
import utils.Action;
import utils.DataTable2;

public class ICUpdateCustomer {
	
	 WebDriver driver;
	    Action action;

	    public ICUpdateCustomer(WebDriver driver, DataTable2 dataTable2) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);

	    }
	    
	    
	    @FindBy(className = "my-account")
		WebElement ic_myAccountButton;
	  
	    @FindBy(xpath="//*[@id=\"header-slideout--0\"]/li[3]/a")
	    private WebElement MyAccountButton2;
	  
	    @FindBy(xpath="//*[@id=\"account-nav\"]/ul/li[5]/a")
	    private WebElement AddressBookEdit;
	    
	    @FindBy(xpath="//*[@id=\"account-nav\"]/ul/li[6]/a")
	    private WebElement AccountInfoEdit;
	    
	    //Edit
	    @FindBy(xpath="//*[@id=\"firstname\"]")
	    private WebElement ic_firstname;
	  //*[@id="firstname"]
	    @FindBy(xpath="//*[@id=\"firstname\"]")
	    private WebElement firstUpdated;
	    
	    @FindBy(xpath="//*[@id=\"lastname\"]")
	    private WebElement ic_lastname;
	    
	    @FindBy(xpath="//*[@id=\"lastname\"]")
	    private WebElement lastNameUpdated;
	    
	    @FindBy(xpath="//*[@id=\"taxvat\"]")
	    private WebElement ic_taxVat;
	  
	    @FindBy(xpath="//*[@id=\"form-validate\"]/fieldset[1]/div[4]/label ")
	    private WebElement emailCheckBox;
	    
	    @FindBy(xpath="//*[@id=\"email\"]")
	    private WebElement ic_email;
	    
	    @FindBy(xpath="//*[@id=\"maincontent\"]/div/div[2]/div[2]/div[2]/div[1]/div[1]/p")
	    private WebElement emailSaved;
	    
	    @FindBy(xpath="//*[@id=\"form-validate\"]/fieldset[1]/div[5]/label")
	    private WebElement passCheckBox;
	    
	    @FindBy(xpath="//*[@id=\"current-password\"]")
	    private WebElement passCurrent;
	    
	    @FindBy(xpath="//*[@id=\"password\"]")
	    private WebElement passNew;
	    				//*[@id="password-confirmation"]
	    @FindBy(xpath="//*[@id=\"password-confirmation\"]")
	    private WebElement passConfirmation;
	    
	    @FindBy(xpath="/html/body/div[1]/header/div[3]/div[2]/div/div/div")
	    private WebElement successSaved;
	    
	    //still need to add the xpath
	  //*[@id="account-nav"]/ul/li[5]/a
	    @FindBy(xpath="//*[@id=\"account-nav\"]/ul/li[5]/a")
	    private WebElement ic_addressInformation;
	    				
	    @FindBy(xpath="//*[@id=\"street_1\"]")
	    private WebElement ic_streetAddress;
	    
	    @FindBy(xpath="//*[@id=\"building_details\"]")
	    private WebElement ic_buildingDetails;
	    
	    @FindBy(xpath="//*[@id=\"region_id\"]/option")
	    private List<WebElement> ic_province;
	    
	    @FindBy(xpath="//*[@id=\"city\"]")
	    private WebElement ic_city;
	    
	    @FindBy(xpath="//*[@id=\"suburb\"]")
	    private WebElement ic_suburb;
	    
	    @FindBy(xpath="//*[@id=\"zip\"]")
	    private WebElement ic_postalCode;
	    
	    @FindBy(xpath="//*[@id=\"form-validate\"]/div/div[1]/button/span")
	    private WebElement SaveButton ;
	    
	    @FindBy(xpath="//*[@id=\"maincontent\"]/div/div[2]/div[2]/div[2]/div[1]/div[2]/a/span")
	    private WebElement ic_BillingAddress;
	    
	    @FindBy(xpath="//*[@id=\"maincontent\"]/div/div[2]/div[2]/div[2]/div[2]/div[2]/a/span")
	    private WebElement ic_ShippingAddress;
	 
	    /**
	     * 
	     * @param test
	     */
	    @Step("after loging")
		public void ic_NavigateToMyAccount(ExtentTest test) {
			try {
				action.click(ic_myAccountButton, "Navigate to accountTab",test);
				action.click(MyAccountButton2, "My Account", test);
				
				//action.click(AddressBook, "Address", test);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    
	    
	    @Step("Account Information")
	    public void updateAccount(ArrayList<HashMap<String, ArrayList<String>>> mySheets,ExtentTest test,int testcaseID){
	    	int sheetRow1= findRowToRun(mySheets.get(0), 0, testcaseID);
			int sheetRow2= findRowToRun(mySheets.get(1), 0, testcaseID);
	    	
	    try {
	    	
	    	String firstName = mySheets.get(0).get("firstName").get(sheetRow1);
	    	String lastName = mySheets.get(0).get("lastName").get(sheetRow1);
	    	String taxVat = mySheets.get(0).get("taxVat").get(sheetRow1);
	    	String email = mySheets.get(0).get("email").get(sheetRow1);
	    	String passWord = mySheets.get(0).get("passWord").get(sheetRow1);
	    	
	    	Random rand = new Random();
			String id = String.format("%04d", rand.nextInt(10000));
	    	

	    	//Default Billing address
	    	String billingAddress = mySheets.get(0).get("billingAddress").get(sheetRow1);
	    	String billing_streetAddress = mySheets.get(0).get("billing_streetAddress").get(sheetRow1);
	    	//String billing_provinceName = mySheets.get(0).get("billing_provinceName").get(sheetRow1);
	    	
	    	//Default shipping address
	    	String shippingAddress = mySheets.get(0).get("shippingAddress").get(sheetRow1);
	    	String shipping_streetAddress = mySheets.get(0).get("shipping_streetAddress").get(sheetRow1);
	    	//String shipping_provinceName = mySheets.get(0).get("shipping_provinceName").get(sheetRow1);
	    	
	    	
	    	action.click(ic_myAccountButton, "Navigate to accountTab",test);
	    	action.click(MyAccountButton2, "My Account", test);
	    	
	    	action.click(AccountInfoEdit, "Account Infor", test);
	    	String firstNameText = action.getAttribute(ic_firstname, "value");
	    	if(firstName.equalsIgnoreCase("Yes")){
	    		
	    		String firstNameTextUpdated = "Updated" + id;
	    		action.clear(ic_firstname, "Remmoving first name");
	    		action.writeText(ic_firstname, firstNameTextUpdated,"first Name", test);
	    		mySheets.get(0).get("firstName_output").set(sheetRow1, firstNameTextUpdated);
	    		
	    		action.click(SaveButton, "Save", test);
	    		action.CompareResult("User Saved", "You saved the account information.", action.getText(successSaved, ""), test);
	    		action.click(AccountInfoEdit, "Account Infor", test);
	    		action.explicitWait(5000);
	    		String firstNameTextSaved = action.getAttribute(firstUpdated, "value");
	    		action.CompareResult("compare updated first name", firstNameTextUpdated, firstNameTextSaved, test);
	    	}else {
	    		mySheets.get(0).get("firstName_output").set(sheetRow1, firstNameText);
	    	}
	    	
	    	String lastNameText = action.getAttribute(ic_lastname, "value");
	    	if(lastName.equalsIgnoreCase("yes")) {
	    		
	    		String lastNameTextUpdated = "Updated" + id;
	    		action.clear(ic_lastname, "Remmoving last name");
	    		action.writeText(ic_lastname, lastNameTextUpdated,"last Name", test);
	    		mySheets.get(0).get("lastName_output").set(sheetRow1, lastNameTextUpdated);
	    		
	    		action.click(SaveButton, "Save", test);
	    		action.CompareResult("User Saved", "You saved the account information.", action.getText(successSaved, ""), test);
	    		action.click(AccountInfoEdit, "Account Infor", test);
	    		action.explicitWait(5000);
	    		String lastNameTextSaved = action.getAttribute(lastNameUpdated, "value");
	    		action.CompareResult("compare updated last name", lastNameTextUpdated, lastNameTextSaved, test);
	    	}else {
	    		mySheets.get(0).get("lastName_output").set(sheetRow1, lastNameText);
	    	}
	    	
	    	
	    	String taxVatText = action.getAttribute(ic_taxVat, "value");
	    	if(taxVat.equalsIgnoreCase("yes")){
	    		String taxVatTextUpdated = "Updated" + id;
	    		action.clear(ic_taxVat, "Remmoving Vat");
	    		action.writeText(ic_taxVat, taxVatTextUpdated," VAT/TAX", test);
	    		mySheets.get(0).get("taxVat_output").set(sheetRow1, taxVatTextUpdated);
	    		
	    		action.click(SaveButton, "Save", test);
	    		action.click(AccountInfoEdit, "Account Infor", test);
	    	}else {
	    		mySheets.get(0).get("taxVat_output").set(sheetRow1, taxVatText);
	    	}
	    	
	    	action.click(emailCheckBox, "EmailCheckBox", test);
	    	String emailText = action.getAttribute(ic_email, "value");
	    	if(email.equalsIgnoreCase("yes")){
	    		String emailTextUpdated = id+"Updated"+ emailText;
	    		
	    		ic_email.clear();
	    		action.writeText(ic_email, emailTextUpdated ,"email", test);
	    		mySheets.get(0).get("email_output").set(sheetRow1, emailTextUpdated);
	    		
	    		String currentPassWordText = mySheets.get(1).get("Password").get(sheetRow2);
	    		action.writeText(passCurrent, currentPassWordText, "Current password", test);
	    		
	    		action.click(SaveButton, "Save", test);
	    		action.CompareResult("User Saved", "You saved the account information.", action.getText(successSaved, ""), test);
	    		action.click(AccountInfoEdit, "Account Infor", test);
	    	}else {
	    		mySheets.get(0).get("email_output").set(sheetRow1, emailText);
	    	}
	    	
	    	if(passWord.equalsIgnoreCase("yes")){
	    		action.click(passCheckBox, "PassWordCheckBox", test);
	    		String currentPassWordText = mySheets.get(1).get("Password").get(sheetRow2);
	    		
	    		action.writeText(passCurrent, currentPassWordText, "current Password", test);
	    		
	    		String passWTextUpdated = "updated" +currentPassWordText+ id;
	    		
	    		action.writeText(passNew, passWTextUpdated , "new Password", test);
	    		mySheets.get(0).get("passWord_output").set(sheetRow1, passWTextUpdated);
	    		action.writeText(passConfirmation, passWTextUpdated, "Confirm new Password", test);
	    		action.click(SaveButton, "Save", test);	
	    		action.CompareResult("User Saved", "You saved the account information.", action.getText(successSaved, ""), test);
	    		action.click(AccountInfoEdit, "Account Infor", test);
	    	}
	    	
	    	//Address Book edit
	    	
	    	action.click(ic_addressInformation, "Information Address", test);
	    	
	    	//Billing Address
	    	if(billingAddress.equalsIgnoreCase("Yes")){
	    		//action.click(ic_BillingAddress, "Change Billing address", test);
	    		//ic_BillingAddress.click();
	    		action.javaScriptClick(ic_BillingAddress, "Change Billing address", test);
	    		String streetAdressText = action.getAttribute(ic_streetAddress, "value");	    		
	    		if(billing_streetAddress.equalsIgnoreCase("yes")){
	    			//ADD random data from list here for addresses
		    		String streetAdressTextUpdated = streetAdressText + "Updated" + id;
		    		action.clear(ic_streetAddress, "Removing Streeta address");
	    	
		    		action.writeText(ic_streetAddress, streetAdressTextUpdated, "Street address", test);
		    		mySheets.get(0).get("billing_streetAddress_output").set(sheetRow1, streetAdressTextUpdated);
		    		
		    		/*action.click(SaveButton, "Save", test);
		    		action.CompareResult("User address Saved", "You saved the address.", action.getText(successSaved, ""), test);*/
		    		
		    	
		    		String buildDetailsText = action.getAttribute(ic_buildingDetails, "value");
		    		mySheets.get(0).get("billing_buildingDetails_output").set(sheetRow1, buildDetailsText);
		    		
		    		//Fix this province get the proper province
		    		String province = action.getSelectedOptionFromDropDown(ic_province);
		    		
		    		
		    		String cityText = action.getAttribute(ic_city, "value");
		    		mySheets.get(0).get("billing_city_output").set(sheetRow1, cityText);
		    		
		    		String suburbText = action.getAttribute(ic_suburb, "value");
		    		mySheets.get(0).get("billing_suburb_output").set(sheetRow1, suburbText);
		    		
		    		String postalCodeText = action.getAttribute(ic_postalCode, "value");
		    		mySheets.get(0).get("billing_postalCode_output").set(sheetRow1, postalCodeText);
		    		
		    		action.click(SaveButton, "Save", test);
		    		action.CompareResult("User address Saved", " You saved the address.", action.getText(successSaved, "Billing address updated"), test);
		    	
		    	}else {
		    		mySheets.get(0).get("billing_streetAddress_output").set(sheetRow1, streetAdressText);
		    	}
	    		
	    	}
	    	
	    	//address shipping address
	    	if(shippingAddress.equalsIgnoreCase("Yes")){
	    		action.click(ic_ShippingAddress, "Change Shipping address", test);
	    	
	    	if(shipping_streetAddress.equals("yes")){
	    		String streetAddressText = action.getAttribute(ic_streetAddress, "value");
	    		String streetAddressTextUpdated = streetAddressText + "Updated" + id;
	    		action.clear(ic_streetAddress, "Removing Streeta address");
	    		
	    		action.writeText(ic_streetAddress,streetAddressText, "Street address", test);
	    		mySheets.get(0).get("shipping_streetAddress_Output").set(sheetRow1, streetAddressTextUpdated);
	    		
	    		/*action.click(SaveButton, "Save", test);
	    		action.CompareResult("User address Saved", "You saved the address.", action.getText(successSaved, ""), test);*/
	    		
	    		String buildDetailsText = action.getAttribute(ic_buildingDetails, "value");
	    		mySheets.get(0).get("shipping_buildingDetails_output").set(sheetRow1, buildDetailsText);
	    		
	    		//action.selectExactValueFromListUsingTex(ic_province, billing_provinceName);
	    		
	    		String cityText = action.getAttribute(ic_city, "value");
	    		mySheets.get(0).get("shipping_city_output").set(sheetRow1, cityText);
	    		
	    		String suburbText = action.getAttribute(ic_suburb, "value");
	    		mySheets.get(0).get("shipping_suburb_output").set(sheetRow1, suburbText);
	    		
	    		String postalCodeText = action.getAttribute(ic_postalCode, "value");
	    		mySheets.get(0).get("shipping_postalCode_output").set(sheetRow1, postalCodeText);
	    		
	    		action.click(SaveButton, "Save", test);
	    		action.CompareResult("User address Saved", " You saved the address.", action.getText(successSaved, "Shipping address updated"), test);
	    		
	    	}
	    	}
	    
	    } catch(Exception e){
	    	e.printStackTrace();
	    	}
	    }
	    
	    
	public int findRowToRun(HashMap<String, ArrayList<String>> input,int occCount,int testcaseID){
		int numberRows=input.get("TCID").size();
		int rowNumber=-1;
		occCount=occCount+1;
		for(int i=0;i<numberRows;i++){
			if(input.get("TCID").get(i).equals(Integer.toString(testcaseID))&&input.get("occurence").get(i).equals(Integer.toString(occCount))){
				rowNumber=i;
			}
		}
		return rowNumber;
	}
	
}
