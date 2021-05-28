package evs_MagentoPageObjects;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import groovy.time.TimeDuration;
import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;

public class EVS_MagentoOrderSAPnumber {
	WebDriver driver;
    Action action;
    
    public EVS_MagentoOrderSAPnumber(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);

    }
    
    @FindBy(xpath="//div[contains(text(),'[RabbitMQ] Order SAP Number: ')][1]")
    private WebElement OrderDetailSAPNumber;
    
    Timer t = new Timer();
    public static String OrderSAPnumber;
    
    public void GenerateOrderSAPnumber(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws Exception {
    	boolean flagres = false;
    	int totalConunter=0;
    	String OrderSAPnumber = "";
    	long startTime = System.currentTimeMillis();
    	int TimeOutinSecond =Integer.parseInt(input.get("TimeOutinSecond").get(rowNumber));
    	int trycount =Integer.parseInt(input.get("totalCounter").get(rowNumber));
    	int elapsedTime = 0;
    	while(elapsedTime<=TimeOutinSecond && flagres==false)
    	{
			action.refresh();
			action.waitForPageLoaded(TimeOutinSecond);
			
			try {
				if(action.elementExists(OrderDetailSAPNumber, 10)){
						action.scrollElemetnToCenterOfView(OrderDetailSAPNumber);
						OrderSAPnumber = OrderDetailSAPNumber.getText();//action.getText(OrderDetailSAPNumber, "SAP Number",test);
						//action.scrollToElement(OrderDetailSAPNumber,"OrderDetailSAPNumber");
						System.out.println(OrderSAPnumber);
					if(OrderSAPnumber.isEmpty()){
			    		action.explicitWait(TimeOutinSecond);
			    		action.refresh();
						System.out.println("not found on count:" + totalConunter);
			    	}else{
			    		flagres = true;
						System.out.println("OrderSAPnumber :" + OrderSAPnumber);
						input.get("OrderSAPnumber").set(rowNumber,OrderSAPnumber.replace("[RabbitMQ] Order SAP Number: ",""));
			    	}
				}else{
					System.out.println("OrderDetailSAPNumber not exist");
				}
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				if(trycount==totalConunter){
					e.printStackTrace();
				}
			}

			//Thread.sleep(TimeOutinSecond * 1000);
			long endTime = System.currentTimeMillis();
			long elapsedTimeInMils = endTime-startTime;
			elapsedTime = ((int) elapsedTimeInMils)/1000;
			System.out.println("elapsedTime: "+elapsedTime);
			totalConunter++;
		}
    	if(flagres){
    		action.CompareResult("SAP order Number generated :"+OrderSAPnumber, String.valueOf(true), String.valueOf(flagres), test);
    	}else{
    		action.CompareResult("SAP order Number generated :"+OrderSAPnumber, String.valueOf(true), String.valueOf(flagres), test);
    		throw new Exception("SAP Order Number Is Not Generated");
    	}
    	System.out.println();
    }
}
