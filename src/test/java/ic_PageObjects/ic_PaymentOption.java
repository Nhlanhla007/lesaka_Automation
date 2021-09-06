package ic_PageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ic_PaymentOption {
    WebDriver driver;
    Action action;
    DataTable2 dataTable2;

    public ic_PaymentOption(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        this.dataTable2 = dataTable2;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
    }
	
    @FindBy(xpath = "//*[@id='opc-sidebar']/div[1]/div[1]/button")
    WebElement Btn_PlaceOrder;
    //payement options

    @FindBy(xpath = "//span[contains(text(),'Credit Card (Processed By PayU)')]")
    WebElement payUcreditcard;
    @FindBy(xpath = "//span[contains(text(),'Visa Checkout')]")
    WebElement VisaCheckout;
    @FindBy(xpath = "//span[contains(text(),'EFT Pro (Processed By PayU)')]")
    WebElement EFT_Pro;
    @FindBy(xpath = "//span[contains(text(),'Masterpass')]")
    WebElement Masterpass;
    @FindBy(xpath = "//span[contains(text(),'Pay with PayGate')]")
    WebElement PayGate;
    @FindBy(xpath = "//span[contains(text(),'uCount (Processed By PayU)')]")
    WebElement uCount;
    @FindBy(xpath = "//span[contains(text(),'Discovery Miles (Processed By PayU)')]")
    WebElement Discovery_Miles;
    @FindBy(xpath = "//span[contains(text(),'RCS Credit (Processed By PayU)')]")
    WebElement RCS_Credit;
    @FindBy(xpath = "//span[contains(text(),'Cash Deposits')]")
    WebElement Cash_Deposits;
    @FindBy(xpath = "//body/div[2]/main[1]/div[2]/div[1]/div[1]/div[4]/ol[1]/li[3]/div[1]/form[1]/fieldset[1]/div[1]/div[1]/div[1]/div[12]/div[1]/label[1]/span[1]")
    WebElement card;

    @FindBy(xpath = "//*[@id=\"customer-email\"]")
    WebElement emaiL;

    @FindBy(xpath = "//*[@name=\"firstname\"]")
    WebElement firstnamE;
	
    @FindBy(xpath = "//*[@name=\"lastname\"]")
    WebElement lastname;
	
    @FindBy(xpath = "//*[@name=\"telephone\"]")
    WebElement telephone;
	
    @FindBy(xpath = "//*[@name=\"custom_attributes[suburb]\"]")
    WebElement Suburb;
	
    @FindBy(xpath = "//*[@name=\"street[0]\"]")
    WebElement streetnamE;
	
    @FindBy(xpath = "//select[@name=\"region_id\"]")
    WebElement province;
	
    @FindBy(xpath = "//*[@name=\"city\"]")
    WebElement city;
	
    @FindBy(xpath = "//*[@name=\"postcode\"]")
    WebElement postalCode;
	
    @FindBy(xpath = "//*[@name=\"vat_id\"]")
    WebElement vatNumber;

    @FindBy(name = "custom_attributes[identity_number]")
    WebElement idNumber;
	
    @FindBy(xpath = "//*[@id='checkout-payment-method-load']/div/div/div[3]/div[2]/div[2]/div/div/label/span")
    WebElement Billingshipping;

    @FindBy(xpath = "//span[contains(text(),'I agree to all the terms & conditions')]")
    WebElement TermsCondition;

    @FindBy(xpath = "//input[@id='id-book-upload']")
    WebElement selectIDButton;

    @FindBy(xpath = "//span[contains(text(),'No file selected')]")
    WebElement NoFileSelection;

    @FindBy(xpath = "//span[contains(text(),\"Ready to upload\")]")
    WebElement uploadMsg;

    @FindBy(xpath = "//span[contains(text(),'Submit')]")
    WebElement IDSubmitBtn;

    @FindBy(xpath = "//span[contains(text(),'Uploading proof of ID, please wait...')]")
    WebElement uploadingMsg;


	public void uploadValidID(ExtentTest test) throws Exception {
		action.explicitWait(15000);
		action.ajaxWait(20, test);
		String uploadDocument = dataTable2.getValueOnOtherModule("ic_tvLicenseValidation", "Upload Document", 0);
	
        try {
            if (uploadDocument.equalsIgnoreCase("yes")) {
                boolean uploadButton = action.isElementPresent(selectIDButton);
                action.CompareResult("Upload Button is displayed", "true", String.valueOf(uploadButton), test);
				 
                if (uploadButton) {
                    boolean NoFileSelectionCheck = action.isElementPresent(NoFileSelection);
                    action.CompareResult("No Document is selected for Upload", "true", String.valueOf(NoFileSelectionCheck), test);
                    if (NoFileSelectionCheck) {
			
                        String filePath = dataTable2.getValueOnOtherModule("ic_tvLicenseValidation", "Document Upload Location", 0);

                        selectIDButton.sendKeys(filePath);
                        action.ajaxWait(10, test);
                        boolean uploadMessage = action.waitUntilElementIsDisplayed(uploadMsg, 5);
                        action.CompareResult("Ready to upload message", "true", String.valueOf(uploadMessage), test);
                        if (uploadMessage) {
                            action.explicitWait(10000);
                            action.javaScriptClick(IDSubmitBtn, "Submit Button", test);
                            boolean uploadingMessage = action.waitUntilElementIsDisplayed(uploadingMsg, 5);
                            action.CompareResult("File uploading message", "true", String.valueOf(uploadingMessage), test);
                            action.explicitWait(10000);
                            boolean uploadCompleteFlag = driver.findElements(By.xpath("//span[contains(text(),'Uploading proof of ID, please wait...')]")).size() > 0;
                            action.CompareResult("Uploading is Completed", "false", String.valueOf(uploadCompleteFlag), test);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Unable to upload the Document: " + e.getMessage());
        }
    }


    public WebElement ic_SelectPaymentMethod(String Paytype) {
        Map<String, WebElement> PaymentMap = new HashMap<String, WebElement>();
        PaymentMap.put("payUcreditcard", payUcreditcard);
        PaymentMap.put("VisaCheckout", VisaCheckout);
        PaymentMap.put("EFT_Pro", EFT_Pro);
        PaymentMap.put("Masterpass", Masterpass);
        PaymentMap.put("PayGate", PayGate);
        PaymentMap.put("uCount", uCount);
        PaymentMap.put("Discovery_Miles", Discovery_Miles);
        PaymentMap.put("RCS_Credit", RCS_Credit);
        PaymentMap.put("Cash_Deposits", Cash_Deposits);
        PaymentMap.put("card", card);
        WebElement actionele = null;
        Flag:
        for (Map.Entry m : PaymentMap.entrySet()) {
            if (m.getKey().toString().trim().toUpperCase().equalsIgnoreCase(Paytype)) {
                actionele = (WebElement) m.getValue();
                break Flag;
            }

        }

        return actionele;
    }

		public void CheckoutpaymentOption(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber)
				throws Exception {
			try {
				action.waitUntilElementIsDisplayed(Btn_PlaceOrder, 20);
				action.explicitWait(30000);
				String Paytype = input.get("Paytype_Option").get(rowNumber);
				WebElement paymenttype = ic_SelectPaymentMethod(Paytype);
				action.scrollElemetnToCenterOfView(paymenttype, "paymenttype", test);
				action.explicitWait(2000);
				action.javaScriptClick(paymenttype, "Select Payment option " + Paytype, test);
				action.ajaxWait(10, test);
 				action.explicitWait(1000);
 				action.ajaxWait(10, test);
 				action.explicitWait(1000);
 				action.ajaxWait(10, test);
 				action.explicitWait(1000);
				action.scrollElemetnToCenterOfView(Btn_PlaceOrder, "Place Order Button", test);
				action.explicitWait(2000);
				action.javaScriptClick(Btn_PlaceOrder, "Place order Button ", test);
				action.waitForJStoLoad(120);
				action.ajaxWait(30, test);
			} catch (Exception e) {
				throw new Exception("Unable to navigate Payment Details page. "+e.getMessage());
			}
		}
	
	
	public void CheckoutpaymentOptionGiftCard(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws Exception{
		try{
		action.waitUntilElementIsDisplayed(Btn_PlaceOrder, 20);
		action.explicitWait(30000);
		String firstNameGift = dataTable2.getValueOnOtherModule("deliveryPopulation", "firstName", 0);
        String lastnameGift = dataTable2.getValueOnOtherModule("deliveryPopulation", "lastname", 0);
        String emailGift = dataTable2.getValueOnOtherModule("deliveryPopulation", "email", 0);
        String streetNameG = dataTable2.getValueOnOtherModule("deliveryPopulation", "streetName", 0);
        String provinceGift = dataTable2.getValueOnOtherModule("deliveryPopulation", "province", 0);
        String cityGift = dataTable2.getValueOnOtherModule("deliveryPopulation", "city", 0);
        String postalcodeGift= dataTable2.getValueOnOtherModule("deliveryPopulation", "postalCode", 0);
        String phonenumberGift = dataTable2.getValueOnOtherModule("deliveryPopulation", "telephone", 0);
        String suburdGift= dataTable2.getValueOnOtherModule("deliveryPopulation", "Suburb", 0);
        String vatnumberGift = dataTable2.getValueOnOtherModule("deliveryPopulation", "vatNumber", 0);
		String Paytype = dataTable2.getValueOnOtherModule("CheckoutpaymentOption", "Paytype_Option", 0);
//		action.CheckEnabilityofButton(Btn_PlaceOrder, "Place Order", false, test);
            WebElement paymenttype = ic_SelectPaymentMethod(Paytype);
            action.javaScriptClick(paymenttype, "Select Payment option " + Paytype, test);
            action.explicitWait(5000);
            action.writeText(emaiL, emailGift, "Email", test);
            action.writeText(firstnamE, firstNameGift, "First name", test);
            action.writeText(lastname, lastnameGift, "Last name", test);
            action.writeText(streetnamE, streetNameG, "Street name", test);
            action.writeText(province, provinceGift, "Province", test);
            action.clear(city, "");
            action.writeText(city, cityGift, "City", test);
            action.clear(postalCode, "");
            action.writeText(postalCode, postalcodeGift, "Postal code", test);
            action.writeText(telephone, phonenumberGift, "Phone number", test);
            action.clear(Suburb, "");
            action.writeText(Suburb, suburdGift, "Suburb", test);
            action.writeText(vatNumber, vatnumberGift, "Vat number", test);
            action.explicitWait(15000);
	    
            ICDelivery.Streetname = dataTable2.getValueOnOtherModule("deliveryPopulation", "streetName", 0);
            ICDelivery.Cityname = dataTable2.getValueOnOtherModule("deliveryPopulation", "city", 0);
            ICDelivery.Postalcode = dataTable2.getValueOnOtherModule("deliveryPopulation", "postalCode", 0);
	    
            action.clickEle(Btn_PlaceOrder, "Place order Button ", test);
            action.waitForJStoLoad(60);
            action.ajaxWait(10, test);

        } catch (Exception e) {
            throw new Exception("Couldn't continue to PayU page, Error in delivery population " + e.getMessage());
        }
    }

}
	
