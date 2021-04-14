package JDGroupPageObjects;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class ic_RefreshLogoHomepage {
	
	 WebDriver driver;
	    Action action;
	    DataTable2 dataTable2;
                             
	    public ic_RefreshLogoHomepage(WebDriver driver, DataTable2 dataTable2) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	        this.dataTable2= dataTable2;

	    }

	    @FindBy(xpath="//header/div[2]/div[1]/div[1]/a[1]")
	    private WebElement ic_logo;
	    
	    @FindBy(xpath="//html/head/title")
	    private WebElement ic_titlepage;
	   
	    
    public void homepageLogo(ExtentTest test) throws IOException{
	    
	    action.click(ic_logo, "Click to go homepage", test); 
	    String title= driver.getTitle();
	    action.CompareResult("Return homepage", "Incredible Connection Home", title, test);
	    	
	    }
	    
	    
	    
}