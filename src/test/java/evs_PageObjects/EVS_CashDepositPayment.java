package evs_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import ic_MagentoPageObjects.MagentoOrderStatusPage;
import ic_MagentoPageObjects.ic_Magento_Login;
import utils.Action;
import utils.DataTable2;

public class EVS_CashDepositPayment {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
//	ic_Magento_Login Magentologin;
//	MagentoOrderStatusPage magentoOrderStatusPage;
	public EVS_CashDepositPayment(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
//        Magentologin =new ic_Magento_Login(driver, dataTable2);
//        magentoOrderStatusPage = new MagentoOrderStatusPage(driver, dataTable2);
	}
	
	
	@FindBy(xpath = "//p[contains(text(),'Your order # is')]")
	WebElement OderID;
	
	@FindBy(xpath = "//*[@id='order_invoice']/span[text()='Invoice']")
	WebElement Invoice_Tab;
	
	@FindBy(xpath = "//*[@title='Submit Invoice']/span[text()='Submit Invoice']")
	WebElement SubmitInvoice;
	@FindBy(xpath = "//*[@id='messages']//div[text()='The invoice has been created.']")
	WebElement CreateInvoiceSuccessMsg;
	
	@FindBy(xpath = "//table[@class='admin__table-secondary order-information-table']//tr//td//span[@id='order_status']")
	WebElement OrderStatus;
//	public String RetriveOrderID(ExtentTest test) throws IOException {
//		String Oderid = null;
//		action.isElementOnNextPage(OderID, (long) 11,test);
//	    Oderid = action.getText(OderID, "Order ID");
//	    Oderid = Oderid.replace("Your order # is: ","").replace(".","");
//		return Oderid;
//	}
	public void InvoiceCashDeposit(ExtentTest test) throws Exception{		
		String ExpOrderStatus = "Received Paid";		
			MagentoAdminDoInvoiceing(test);
			MagentoOrderInfoVerification(test, ExpOrderStatus);
	}
	
	public void MagentoOrderInfoVerification(ExtentTest test,String ExpOrderstatus) throws IOException{		
			action.scrollToElement(OrderStatus, "OrderStatus");
			String ActorderStatus = action.getText(OrderStatus,"OrderStatus",test);
			if(ActorderStatus.equalsIgnoreCase(ExpOrderstatus)){
				action.CompareResult("Order ID status ", ExpOrderstatus, ActorderStatus, test);
			}else{
				action.CompareResult("Order ID status ", ExpOrderstatus, ActorderStatus, test);
			}
		
	}
	public void MagentoAdminDoInvoiceing(ExtentTest test) throws IOException{
			action.click(Invoice_Tab, "Invoice_Tab", test);
			action.waitForElementPresent(SubmitInvoice, 21);
			action.scrollToElement(SubmitInvoice,"SubmitInvoice");
			action.click(SubmitInvoice, "SubmitInvoice", test);
			action.waitForElementPresent(CreateInvoiceSuccessMsg, 21);
			String createInvoicemsg = action.getText(CreateInvoiceSuccessMsg, "Create Invoice Success Message",test);
			if(createInvoicemsg.contains("invoice has been created")){
				action.CompareResult("Invoicing Done successfully : ", "true", "true", test);
			}else{
				action.CompareResult("Invoicing Done successfully : ", "true", "false", test);
			}
	}	
}