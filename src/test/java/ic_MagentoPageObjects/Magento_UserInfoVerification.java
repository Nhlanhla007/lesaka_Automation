package ic_MagentoPageObjects;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import ic_PageObjects.ICDelivery;
import utils.Action;
import utils.DataTable2;

public class Magento_UserInfoVerification {
	
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	MagentoRetrieveCustomerDetailsPage MagentoRetrieveCustomer;
	MagentoAccountInformation magentoAccountInformation = new MagentoAccountInformation(driver,dataTable2);
	ICDelivery registeredCustomerDetails;
	
	 public Magento_UserInfoVerification (WebDriver driver, DataTable2 dataTable2) {
			this.driver = driver;
			this.dataTable2 = dataTable2;
			MagentoRetrieveCustomer = new MagentoRetrieveCustomerDetailsPage(driver,dataTable2);
			PageFactory.initElements(driver, this);
			action = new Action(driver);
			registeredCustomerDetails = new ICDelivery(driver, dataTable2);
		}
	
	public Magento_UserInfoVerification() {
		System.out.println("Hello");
	}
	@FindBy(xpath = "//input[@name='customer[partner_number]']")
	WebElement customerBPnnumber;

	@FindBy(xpath = "//span[contains(text(),'Account Information')]")
	WebElement Account_Information;

	@FindBy(xpath = "//input[@name='customer[firstname]']")
	WebElement Cust_Firstname;
	@FindBy(xpath = "//input[@name='customer[lastname]']")
	WebElement Cust_Lastname;
	@FindBy(xpath = "//input[@name='customer[email]']")
	WebElement Cust_Email;
	@FindBy(xpath = "//input[@name='customer[identity_number]']")
	WebElement Cust_SAID;
	@FindBy(xpath = "//input[@name='customer[passport_number]']")
	WebElement Cust_Passport;
	////input[@name="customer[taxvat]"]
	@FindBy(xpath = "//input[@name='customer[taxvat]']")
	WebElement Cust_VAT;
	
	@FindBy(xpath = "//a[@id='tab_newsletter_content']")
	WebElement Tab_NewsLetter;
	//Ic Update usr first/lastname taxvat email
	@FindBy(xpath = "//input[@id='_newslettersubscription']")
	WebElement Cust_NewsLetter;
	
	@FindBy(xpath = "//*[@id=\"sales_order_view_tabs_order_info_content\"]/section[2]/div[2]/div[1]//a")
	WebElement guestEditBtn;
	
	@FindBy(xpath = "//*[@id=\"sales_order_view_tabs_order_info_content\"]/section[1]//table/tbody/tr[2]/td/a")
	WebElement guestEmail;
	
	@FindBy(id = "firstname")
	WebElement guestFirstName;
	
	@FindBy(id = "lastname")
	WebElement guestLastName;
	
	@FindBy(id = "street0")
	WebElement guestStreetAddress;
	
	@FindBy(id = "city")
	WebElement guestCity;
	
	@FindBy(id = "region_id")
	WebElement guestProvince;
	
	@FindBy(id = "postcode")
	WebElement guestPostalCode;
	
	@FindBy(id = "telephone")
	WebElement guestTelephone;
	
	@FindBy(id = "vat_id")
	WebElement guestVatNumber;
	
	@FindBy(id = "suburb")
	WebElement guestSuburb;
	
	@FindBy(id = "identity_number")
	WebElement guestID;
	
	
	//  MAGENTO ADDRESS INFORMATION
	  
	@FindBy(xpath = "//*[@id=\"tab_address\"]")
	private WebElement admin_AddressBtn;

	@FindBy(xpath = "//*[@class=\"edit-default-billing-address-button action-additional\"]/span")//button[@title='Add New Customer'] /html[1]/body[1]/div[6]/aside[1]/div[2]/header[1]/div[1]/div[1]/button[1]/span[1]/span[1]
	private WebElement admin_billingEdit;	
	   
	  @FindBy(xpath="//input[@name='street[0]']") 
	  WebElement admin_Billing_streetAddress;
	 
	
	public void Validate_UserInfobackend(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws Exception {
		int TimetoLoadpage=11;	
		String ExpFirstname = null;
		String ExpLastname = null;
		String ExpEmail = null;
		String ExpidentityType = null;
		String ExpPassportnumber = null;
		String ExpSAIDnumber = null;
		String vatNumberFlag = null;
		String ExpVATnumber = null;
		//DOES NOT WORK
		//String ExpWebsite =dataTable2.getValueOnOtherModule("accountCreation", "WebSite",0);
		
		String typeOfVerificationFlag = dataTable2.getValueOnCurrentModule("Data Source");
		
		driver.navigate().refresh();
		action.explicitWait(7000);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		//driver.manage().window().s
		
		//IF CONSTUCT FOR WHAT TYPE OF VALIDATION IS TAKING PLACE
		//For Account creation(Set this way by default)
		//***************************************
		if(typeOfVerificationFlag.equalsIgnoreCase("Create Account")) {	
			//GETS DATA FROM ACCOUNT CREATION
		js.executeScript("window.scrollBy(0,0)");
		action.scrollElemetnToCenterOfView(Account_Information,"Account_Information",test);
		action.click(Account_Information, "Account Information", test);
		ExpFirstname=input.get("firstName").get(rowNumber);//"Brian";
		ExpLastname=input.get("lastName").get(rowNumber);//"Jones";
		ExpEmail=input.get("emailAddress").get(rowNumber);//"BrenoFernandesMalingaPatrick8@armyspy.com";
	
		//new variables flag  identityType on ID and passport
		 ExpidentityType =input.get("identityType").get(rowNumber);//"ID";
		 ExpPassportnumber=input.get("identityNumber/passport").get(rowNumber);//"5311266534086";
		 ExpSAIDnumber=input.get("identityNumber/passport").get(rowNumber);//"5311266534086";
		
		//new variable flag on newsletter
		String ExpNewsletterFalg = input.get("newsletter").get(rowNumber);//"Yes";
		String NewletterArgs = "";
		
		//VAT validation
		 vatNumberFlag=input.get("vatNumberFlag").get(rowNumber);//"Yes";
		 ExpVATnumber=input.get("vatNumber").get(rowNumber);//"2222224";
		
		 switch (ExpidentityType) {
			case "ID":
				
				//String ActSAID = FetchDataFromCustInfo_MagentoBackend(Cust_SAID, "Customer_SAID", 11, 2, test);
				String actualID = action.getAttribute(Cust_SAID, "value");
				action.CompareResult("Verify the SAID number in Magento backend : ", ExpSAIDnumber, actualID, test);
				break;
			case "Passport":
				//String ActPassport = FetchDataFromCustInfo_MagentoBackend(Cust_Passport, "Customer_Passport", 11, 2, test);
				String acutalPassport = action.getAttribute(Cust_Passport, "value");
				action.CompareResult("Verify the Passport number in Magento backend : ", ExpPassportnumber, acutalPassport, test);
				break;
		
		}
		//------Basic verification ends here------------------------------------
		//validate news letter depending on ExpNewsletterFalg...
		if(ExpNewsletterFalg.equalsIgnoreCase("Yes")){
			NewletterArgs ="Newsletter";
		}else{
			NewletterArgs ="No newsletter";
		}
		
		//validate the VAT/Tax number
		if(vatNumberFlag.equalsIgnoreCase("Yes")){
			//String ActVAT = FetchDataFromCustInfo_MagentoBackend(Cust_VAT, "Customer_VAT", 11, 2, test);
			String actualVat = action.getAttribute(Cust_VAT, "value");
			action.CompareResult("Verify the VAT number in Magento backend : ", ExpVATnumber, actualVat, test);

		}
		
		switch (NewletterArgs) {
			case "Newsletter":
				String ActNewsletteres="";
				action.click(Tab_NewsLetter, "Click Tab_NewsLetter", test);
				boolean checknewsletter =action.elementExists(Cust_NewsLetter, TimetoLoadpage);
				if(checknewsletter==true){
					 ActNewsletteres =action.getAttribute(Cust_NewsLetter, "value");
					action.CompareResult("Verify the Newsletter subscription is Checked  : ", "true",String.valueOf(ActNewsletteres), test);
				}else{
					action.CompareResult("Verify the Newsletter subscription is Checked: ", "true", String.valueOf(ActNewsletteres), test);
				}
				
				break;
			case "No newsletter":
				String ActNonewsletter="";
				action.click(Tab_NewsLetter, "Click Tab_NewsLetter", test);
				boolean checknewsletter1=action.elementExists(Cust_NewsLetter, TimetoLoadpage);
				if(checknewsletter1==true){
					ActNonewsletter =action.getAttribute(Cust_NewsLetter, "value");
					action.CompareResult("Verify the No Newsletter subscription : ", "false",String.valueOf(ActNonewsletter), test);
				}else{
					action.CompareResult("Verify the No Newsletter subscription : ", "false", String.valueOf(ActNonewsletter), test);
				}
				break;
		
		}
		
		}else if(typeOfVerificationFlag.equalsIgnoreCase("Create Account Magento Admin")) {
			//GETS DATA FROM THE CREATE ACCOUNT BACKEND SHEET
					js.executeScript("window.scrollBy(0,0)");
					action.scrollElemetnToCenterOfView(Account_Information,"Account_Information",test);
					action.click(Account_Information, "Account Information", test);
					ExpFirstname= dataTable2.getValueOnOtherModule("CreateaccountBackend", "Firstname", 0);
					ExpLastname = dataTable2.getValueOnOtherModule("CreateaccountBackend", "Lastname", 0);
					ExpEmail =dataTable2.getValueOnOtherModule("CreateaccountBackend", "Email", 0);
					ExpSAIDnumber =dataTable2.getValueOnOtherModule("CreateaccountBackend", "SAID", 0);
					ExpidentityType = dataTable2.getValueOnOtherModule("CreateaccountBackend", "Identitynumber/passport", 0);
					ExpPassportnumber= dataTable2.getValueOnOtherModule("CreateaccountBackend", "Passport", 0);
					switch (ExpidentityType) {
					case "ID":
						//String ActSAID = FetchDataFromCustInfo_MagentoBackend(Cust_SAID, "Customer_SAID", 11, 2, test);
						String actualID = action.getAttribute(Cust_SAID, "value");
						action.CompareResult("Verify the SAID number in Magento backend : ", ExpSAIDnumber, actualID, test);
						break;
					case "Passport":
						//String ActPassport = FetchDataFromCustInfo_MagentoBackend(Cust_Passport, "Customer_Passport", 11, 2, test);
						String acutalPassport = action.getAttribute(Cust_Passport, "value");
						action.CompareResult("Verify the Passport number in Magento backend : ", ExpPassportnumber, acutalPassport, test);
						break;
				}
					
					
			
		}else if(typeOfVerificationFlag.equalsIgnoreCase("Update Account")) {
			js.executeScript("window.scrollBy(0,0)");
			//GETS DATA FROM THE icUpdateUser sheet
			//action.scrollElementIntoView(Account_Information);
			action.scrollElemetnToCenterOfView(Account_Information,"Account_Information",test);
			action.click(Account_Information, "Account Information", test);
			action.explicitWait(5000);
			ExpFirstname = dataTable2.getValueOnOtherModule("ICUpdateUser", "firstName_output", 0);
			ExpLastname = dataTable2.getValueOnOtherModule("ICUpdateUser", "lastName_output", 0);
			ExpEmail = dataTable2.getValueOnOtherModule("ICUpdateUser", "email_output", 0);
			ExpVATnumber = dataTable2.getValueOnOtherModule("ICUpdateUser", "taxVat_output", 0);
			
		}else if(typeOfVerificationFlag.equalsIgnoreCase("Update Account Magento Admin")) {	
			js.executeScript("window.scrollBy(0,0)");
			//GETS DATA FROM adminUserUpdates
			//action.scrollElementIntoView(Account_Information);
			action.scrollElemetnToCenterOfView(Account_Information,"Account_Information",test);
			action.explicitWait(3000);
			action.click(Account_Information, "Account Information", test);
			action.explicitWait(5000);
			ExpFirstname=dataTable2.getValueOnOtherModule("adminUserUpdate", "adminFirstName_output", 0).trim();
			ExpLastname =dataTable2.getValueOnOtherModule("adminUserUpdate", "adminLastName_output", 0).trim();
			ExpEmail =dataTable2.getValueOnOtherModule("adminUserUpdate", "adminEmail_output", 0).trim();
			//ExpSAIDnumber =dataTable2.getValueOnOtherModule("adminUserUpdate++", "", 0);
			ExpVATnumber = dataTable2.getValueOnOtherModule("adminUserUpdate", "adminTaxVat_output", 0);
			
			//String ActVAT = FetchDataFromCustInfo_MagentoBackend(Cust_VAT, "Customer_VAT", 11, 2, test);
			String actualVat = action.getAttribute(Cust_VAT, "value");
			action.CompareResult("VAT number in Magento backend : ", ExpVATnumber, actualVat, test);
			String verifyBillingAddChange = dataTable2.getValueOnOtherModule("adminUserUpdate", "billingAddress", 0);
			if(verifyBillingAddChange.equalsIgnoreCase("yes")) {
				action.click(admin_AddressBtn, "Address Tab", test);
				action.explicitWait(5000);
				action.click(admin_billingEdit, "Billing Address Edit", test);
				String updatedBillingAddress = dataTable2.getValueOnOtherModule("adminUserUpdate", "adminBilling_streetAddress_output", 0);
				action.waitUntilElementIsDisplayed(admin_Billing_streetAddress, 20000);
				String actualBillingAdd = action.getAttribute(admin_Billing_streetAddress, "value");
				action.CompareResult("Billing Address In Magento Backend", updatedBillingAddress, actualBillingAdd, test);
			}

			
		}else if(typeOfVerificationFlag.equalsIgnoreCase("Guest Customer Creation")) {
			//GETS DATA FROM deliveryPopulation
			//Compares to data in the order page
			ExpFirstname=dataTable2.getValueOnOtherModule("deliveryPopulation", "firstName",0).trim();
			ExpLastname = dataTable2.getValueOnOtherModule("deliveryPopulation", "lastname",0).trim();
			ExpEmail = dataTable2.getValueOnOtherModule("deliveryPopulation", "email",0).trim();
			//VAT number has not been implemented in delivery population
			ExpSAIDnumber = dataTable2.getValueOnOtherModule("deliveryPopulation", "idNumber",0).trim();
			ExpVATnumber = dataTable2.getValueOnOtherModule("deliveryPopulation", "vatNumber",0);
			String expStreetAddress = dataTable2.getValueOnOtherModule("deliveryPopulation", "streetName",0).trim();
			String expCity = dataTable2.getValueOnOtherModule("deliveryPopulation", "city",0).trim();
			String expSuburb = dataTable2.getValueOnOtherModule("deliveryPopulation", "Suburb",0).trim();
			String expProvince = dataTable2.getValueOnOtherModule("deliveryPopulation", "province",0).trim();
			String expPostalCode = dataTable2.getValueOnOtherModule("deliveryPopulation", "postalCode",0).trim();
			String expTelephone =dataTable2.getValueOnOtherModule("deliveryPopulation", "telephone",0).trim();
			String magentoGuestEmail = guestEmail.getText();
			

			//action.scrollElementIntoView(guestEditBtn);
			action.scrollElemetnToCenterOfView(guestEditBtn,"Account_Information",test);
			//action.explicitWait(000);
			action.click(guestEditBtn, "Guest Edit Button", test);
			//guestEditBtn.click();
			String magentoGuestID = action.getAttribute(guestID, "value");
			String magentoGuestFirstName = action.getAttribute(guestFirstName, "value");
			String magentoGuestLastName = action.getAttribute(guestLastName, "value");
			String magentoGuestVatNumber = action.getAttribute(guestVatNumber, "value");
			String magentoGuestStreetAddress = action.getAttribute(guestStreetAddress, "value");
			String magentoGuestCity = action.getAttribute(guestCity, "value");
			String magentoGuestSuburb = action.getAttribute(guestSuburb, "value");
			//String magentoGuestProvince = action.getSelectedOptionFromDropDown(guestProvince);
			String magentoGuestPostalCode = action.getAttribute(guestPostalCode, "value");
			String magentoGuestTelephone = action.getAttribute(guestTelephone, "value");
			
			action.mouseover(guestFirstName, "Guest first name");
			action.CompareResult("Verify the First name in Magento backend : ", ExpFirstname, magentoGuestFirstName, test);
			action.mouseover(guestLastName, "Guest Last Name");
			action.CompareResult("Verify the Last name in Magento backend : ", ExpLastname, magentoGuestLastName, test);
			action.mouseover(guestEmail, "Guest email");
			action.CompareResult("Verify the Email in Magento backend : ", ExpEmail, magentoGuestEmail, test);
			action.mouseover(guestID, "Guest ID");
			action.CompareResult("Verify the SAID number in Magento backend : ", ExpSAIDnumber, magentoGuestID, test);
			action.mouseover(guestVatNumber, "Guest VAT number");
			action.CompareResult("Verify the VAT number in Magento backend : ", ExpVATnumber, magentoGuestVatNumber, test);
			action.CompareResult("Verify Street address in Magento backend :", expStreetAddress,magentoGuestStreetAddress , test);
			action.CompareResult("Verify City in Magento backend : ", expCity, magentoGuestCity, test);
		//	action.CompareResult("Verify Province in Magento Backend", expProvince, magentoGuestProvince, test);
			action.CompareResult("Verify Suburb in Magento backend : ", expSuburb, magentoGuestSuburb, test);
			action.CompareResult("Verify Postal code in Magento backend : ", expPostalCode, magentoGuestPostalCode, test);
			action.CompareResult("Verify Telephone in Magento backend : ", expTelephone, magentoGuestTelephone, test);
			//GET ADDRESS INFORMATION 
			/*
			 * String expStreetAddress
			 * =dataTable2.getValueOnOtherModule("deliveryPopulation", "streetName",0);
			 * String expCity =dataTable2.getValueOnOtherModule("deliveryPopulation",
			 * "city",0); String expProvince
			 * =dataTable2.getValueOnOtherModule("deliveryPopulation", "province",0); String
			 * expSuburb = dataTable2.getValueOnOtherModule("deliveryPopulation",
			 * "Suburb",0); String postalCode
			 * =dataTable2.getValueOnOtherModule("deliveryPopulation", "postalCode",0);
			 */
		}else if(typeOfVerificationFlag.equalsIgnoreCase("Registered customer from sales order")) {
			action.scrollElemetnToCenterOfView(Account_Information,"Account_Information",test);
			action.waitUntilElementIsDisplayed(Account_Information, 10);
			action.click(Account_Information, "Account Information", test);
			//NOTE DELIVERY POPULATION WITH REGISTERED new USER HAS TO RUN FOR THIS TO POPULATE ALSO EXISTING WITH EXISTING ADDRESS
			//Map<String,String> customerDetails =ICDelivery.registeredUserDetails;
			//CAN GET FROM SHEET AS WELL
			ExpFirstname = dataTable2.getValueOnOtherModule("deliveryPopulation", "firstName", 0).trim();
			ExpLastname = dataTable2.getValueOnOtherModule("deliveryPopulation", "lastname", 0).trim();
			ExpEmail = dataTable2.getValueOnOtherModule("deliveryPopulation", "email", 0).trim();
			ExpSAIDnumber = dataTable2.getValueOnOtherModule("deliveryPopulation", "idNumber", 0).trim();
			ExpVATnumber = dataTable2.getValueOnOtherModule("deliveryPopulation", "vatNumber", 0).trim();
			//String ActSAID = FetchDataFromCustInfo_MagentoBackend(Cust_SAID, "Customer_SAID", 11, 2, test);
			action.waitUntilElementIsDisplayed(Cust_SAID, 20);
			String actualID = action.getAttribute(Cust_SAID, "value");
			action.CompareResult("Verify the SAID number in Magento backend : ", ExpSAIDnumber, actualID, test);
			
			//String ActVAT = FetchDataFromCustInfo_MagentoBackend(Cust_VAT, "Customer_VAT", 11, 2, test);
			String actualVat = action.getAttribute(Cust_VAT, "value");
			action.CompareResult("Verify the VAT number in Magento backend : ", ExpVATnumber, actualVat, test);
			
		}
		
		//code block---------------------------------------
		if(!(typeOfVerificationFlag.equalsIgnoreCase("Guest Customer Creation"))) {
		//MagentoRetrieveCustomer.navigateToCustomer(test);
		//MagentoRetrieveCustomer.searchForCustomer(ExpEmail, test);
		//Website(Incredible Connection) should come from excel
		//MagentoRetrieveCustomer.tableData(ExpEmail, "Incredible Connection", test);
		//action.clickEle(Account_Information, "Account_Information", test);
		//basic verification--------------------------------------------------------------------------------------
		//String ActFirstname = FetchDataFromCustInfo_MagentoBackend(Cust_Firstname, "Customer_Firstname", 11, 2, test);
		action.explicitWait(3000);
		String actualFirstName = action.getAttribute(Cust_Firstname, "value");
		action.CompareResult("Verify the First name in Magento backend : ", ExpFirstname, actualFirstName, test);
		//String ActLastname = FetchDataFromCustInfo_MagentoBackend(Cust_Lastname, "Custome_Lastname", 11, 2, test);
		String actualLastName = action.getAttribute(Cust_Lastname, "value");
		action.CompareResult("Verify the Last name in Magento backend : ", ExpLastname, actualLastName, test);
		
		//String ActEmailname = FetchDataFromCustInfo_MagentoBackend(Cust_Email, "Customer_Email", 11, 2, test);
		String actualEmail = action.getAttribute(Cust_Email, "value");
		action.CompareResult("Verify the Email in Magento backend : ", ExpEmail, actualEmail, test);

		
		String ActualBPnumber =FetchDataFromCustInfo_MagentoBackend(customerBPnnumber,"customerBPnnumber",TimetoLoadpage,40,test);
		System.out.println("ActualBPnumber:"+ActualBPnumber);
		//hana han = new hana();
//		han.hanaconnector(Integer.parseInt(ActualBPnumber));
		//int rowCount=0;
		//int rowCount=han.hanaconnector(0104022744);
		/*
		 * if( rowCount==0){ action.CompareResult("zero rec : ", ExpEmail, actualEmail,
		 * test); }else{ action.CompareResult("Verify the Email in Magento backend : ",
		 * ExpEmail, actualEmail, test); }
		 */
		}
		//validate ID or passport is entered basis of identity type flag..
		
		/*
		//conditional verification on basis of verficationFlag
		switch (verficationFlag) {
			case "Vat Number":
				
			case "ID":
				String ActSAID = FetchDataFromCustInfo_MagentoBackend(Cust_SAID, "Customer_SAID", 11, 2, test);
				action.CompareResult("Verify the SAID number in Magento backend : ", ExpSAIDnumber, ActSAID, test);
			case "Passport":
				String ActPassport = FetchDataFromCustInfo_MagentoBackend(Cust_VAT, "Customer_VAT", 11, 2, test);
				action.CompareResult("Verify the Passport number in Magento backend : ", ExpPassportnumber, ActPassport, test);
			case "Newsletter":
				String ActNewsletteres="";
				action.click(Tab_NewsLetter, "Click Tab_NewsLetter", test);
				boolean checknewsletter =action.elementExists(Cust_NewsLetter, TimetoLoadpage);
				if(checknewsletter==true){
					 ActNewsletteres =action.getAttribute(Cust_NewsLetter, "value");
					action.CompareResult("Verify the Newsletter subscription : ", "true",String.valueOf(ActNewsletteres), test);
				}else{
					action.CompareResult("Verify the Newsletter subscription : ", "true", String.valueOf(ActNewsletteres), test);
				}
				
				
			case "No newsletter":
				String ActNonewsletter="";
				action.click(Tab_NewsLetter, "Click Tab_NewsLetter", test);
				boolean checknewsletter1=action.elementExists(Cust_NewsLetter, TimetoLoadpage);
				if(checknewsletter1==true){
					ActNonewsletter =action.getAttribute(Cust_NewsLetter, "value");
					action.CompareResult("Verify the No Newsletter subscription : ", "false",String.valueOf(ActNonewsletter), test);
				}else{
					action.CompareResult("Verify the No Newsletter subscription : ", "false", String.valueOf(ActNonewsletter), test);
				}
				
		}*/
		
	}
	public String FetchDataFromCustInfo_MagentoBackend(WebElement element,String elename,int TimetoLoadpage,int TimeOutinSecond,ExtentTest test) throws Exception {
		int trycount=1;
		String resData="";
		long startTime = System.currentTimeMillis(); // ... long finish = System.currentTimeMillis(); long timeElapsed = finish - start
		Instant start = Instant.now();
		int elapsedTime = 0;
		System.out.println(".....................................");
		System.out.println("elementName: "+elename);
		System.out.println(".....................................");
		while(elapsedTime<=TimeOutinSecond & resData.length()<1){
			action.refresh();
			action.waitForPageLoaded(TimetoLoadpage);
			action.click(Account_Information, "Account_Information", test);
			if(action.elementExists(element, TimetoLoadpage)==true){
				resData = action.getAttribute(element, "value");
			}
			
//			trycount++;
			Thread.sleep(TimetoLoadpage * 1000);
			long endTime = System.currentTimeMillis();
			long elapsedTimeInMils = endTime-startTime;
			elapsedTime = ((int) elapsedTimeInMils)/1000;
			System.out.println("elapsedTime: "+elapsedTime);
//			Instant finish = Instant.now();
//			long timeElapsed = Duration.between(start,finish).toSeconds();
		}
		if(resData.isEmpty() | resData==null | resData == ""){

			action.CompareResult(elename+" is fetched sucessfully :"+resData,"True", "False", test);
			throw new Exception("Partner Number Is Not Generated");
			//return resData;
		}else{
			action.CompareResult(elename+" is fetched sucessfully :"+resData,"True", "True", test);
			return resData;
		}
	}
	
	
	
}
