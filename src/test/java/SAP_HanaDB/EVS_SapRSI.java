package SAP_HanaDB;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;
import utils.hana;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class EVS_SapRSI {
    WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2 =null;
    public EVS_SapRSI(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
    }
    
    

    @FindBy(xpath = "//*[@class=\"admin__menu\"]/ul[@id='nav']/li[@id=\"menu-magento-catalog-catalog\"]/a/span[contains(text(),\"Catalog\")]")
    WebElement catalogTab;

    @FindBy(xpath = "//li[@role=\"menu-item\"]/a/span[contains(text(),'Products')]")
    WebElement productsTab;

    @FindBy(xpath = "//button[contains(text(),'Filters')]")
    WebElement magentoFilterTab;

    @FindBy(name = "sku")
    public WebElement sku;

    @FindBy(xpath = "//span[contains(text(),'Apply Filters')]")
    public WebElement magentoApplyFilterTab;

    @FindBy(xpath = "//span[contains(text(),'SAP Data')]")
    public WebElement sapDataTab;

    @FindBy(xpath = "//a[contains(text(),'Edit')]")
    public WebElement clickEdit;

    @FindBy(xpath = "//button[contains(text(),'Clear all')]")
    public WebElement Clearbutton;

    @FindBy(name = "product[rough_stock_indicator]")
    public WebElement roughStockIndicatorAct;

    @FindBy(xpath = "//*[@id=\"container\"]/div/div[2]/div[2]/div[2]/fieldset/div[2]/div/div[2]/table/tbody/tr")
    public WebElement productStoreCount;

    public void getDataFromSAPDB(ExtentTest test) throws Exception {
        hana hn =connectToSap(test) ;
        String channelID=dataTable2.getValueOnCurrentModule("channelID");
        String rough_stock_value=dataTable2.getValueOnCurrentModule("rough_stock_value");
        String Query= "select * from SAPABAP1.\"/OAA/RSI_SNP\" " +
                "where channel_id = '"+channelID+"' " +
                "and ROUGH_STOCK_DATE >=to_date(now()) " +
                "and AGGR_AVAIL_QTY>0 and " +
                "rough_stock_value = '"+rough_stock_value+"' " +
                "order by rand() limit 1";

        //System.out.println("Query:"+Query);
        ResultSet rs = hn.ExecuteQuery(Query);
        int rowsCountReturned = hn.GetRowsCount(rs);
        //System.out.println("rowsCountReturned: "+rowsCountReturned);
        if(rowsCountReturned >= 1) {
        String SKUCode=getColumnValue(hn,rs ,"ARTICLE_ID");
        //System.out.println("SKUCode: "+SKUCode);

        String AGGR_AVAIL_QTY_1=getColumnValue(hn,rs ,"AGGR_AVAIL_QTY");;
        String AGGR_AVAIL_QTY=AGGR_AVAIL_QTY_1.split("\\.")[0];
        //System.out.println("AGGR_AVAIL_QTY: "+AGGR_AVAIL_QTY);

        dataTable2.setValueOnCurrentModule("SKUCode",SKUCode);        
        dataTable2.setValueOnCurrentModule("AGGR_AVAIL_QTY",AGGR_AVAIL_QTY);
        hn.closeDB();
        }else {
        	hn.closeDB();
        	throw new Exception("No Data Is Returned From SAP");
        }        
    }

        public void getDataFromSAPDBAfterCheckout(ExtentTest test) throws Exception {
        hana hn =connectToSap(test);
        String channelID=dataTable2.getValueOnOtherModule("EVS_SapRSIGetDataFromSAPDB","channelID",0);
        String ARTICLE_ID=dataTable2.getValueOnOtherModule("EVS_SapRSIGetDataFromSAPDB","SKUCode",0);
        String AGGR_AVAIL_QTY=dataTable2.getValueOnOtherModule ("EVS_SapRSIGetDataFromSAPDB","AGGR_AVAIL_QTY",0);
        
        action.click(catalogTab,"catalogTab",test);
        action.explicitWait(2000);
        action.click(productsTab,"productsTab",test);
        action.explicitWait(9000);
        if(action.waitUntilElementIsDisplayed(Clearbutton, 10000)) {
        action.click(Clearbutton,"Clearbutton",test);
        }
        action.explicitWait(5000);
        action.click(magentoFilterTab,"magentoFilterTab",test);
        action.writeText(sku,ARTICLE_ID,"skuInputTest",test);
        action.click(magentoApplyFilterTab,"magentoApplyFilterTab",test);
        action.explicitWait(5000);
        if(action.waitUntilElementIsDisplayed(clickEdit, 6000)) {
        	action.javaScriptClick(clickEdit, "clickEdit", test);
        }else {
        	//action.CompareResult("Records Returned", "True", "False", test);
        	throw new Exception("No Records Have Been Found");        	
        }
//        action.explicitWait(5000);
        List<WebElement> storeCount = driver.findElements(By.xpath("//*[@id=\"container\"]/div/div[2]/div[2]/div[2]/fieldset/div[2]/div/div[2]/table/tbody/tr"));
        for ( WebElement i : storeCount ) {
        	action.scrollElemetnToCenterOfView(i, "Data Table", test);
            WebElement z1= i.findElement(By.xpath("./child::td[1]"));
            WebElement z4= i.findElement(By.xpath("./child::td[4]/div/div[2]/input"));
            String store=dataTable2.getValueOnOtherModule("EVS_SapRSIGetDataFromSAPDB","Store",0);
            if(z1.getText().equals(store)) {
                action.CompareResult("Magento Item Quantity After Sales Order ", dataTable2.getValueOnOtherModule("EVS_SapRSIGetDataFromSAPDB","AGGR_AVAIL_QTY",0),z4.getAttribute("value"), test);
            }
        
			String Query = "select AGGR_AVAIL_QTY from SAPABAP1.\"/OAA/RSI_SNP\" " + "where ARTICLE_ID = '" + ARTICLE_ID
					+ "' " + "and CHANNEL_ID = '" + channelID + "'";

        //System.out.println("Query:"+Query);
        ResultSet rs = hn.ExecuteQuery(Query);
        int rowsCountReturned = hn.GetRowsCount(rs);
        //System.out.println("rowsCountReturned: "+rowsCountReturned);
        if(rowsCountReturned >= 1) {
        String SKUCode=getColumnValue(hn,rs ,"ARTICLE_ID");
        //System.out.println("SKUCode: "+SKUCode);
        String AGGR_AVAIL_QTY_1=getColumnValue(hn,rs ,"AGGR_AVAIL_QTY");;
        String AGGR_AVAIL_QTYAfterOneCheckout=AGGR_AVAIL_QTY_1.split("\\.")[0];
        //System.out.println("AGGR_AVAIL_QTYAfterOneCheckout: "+AGGR_AVAIL_QTYAfterOneCheckout);
        action.CompareResult("AGGR_AVAIL_QTY In SAPDB After Sales Order",AGGR_AVAIL_QTY,AGGR_AVAIL_QTYAfterOneCheckout,test);
        hn.closeDB();
	} else {
		hn.closeDB();
		throw new Exception("No Data Is Returned From SAP");
	}
    }
    }


    public void getSellableArticle(ExtentTest test) throws Exception {
    	  hana hn =connectToSap(test) ;
          String channelID=dataTable2.getValueOnOtherModule("EVS_SapRSIGetDataFromSAPDB","channelID",0);
          String ARTICLE_ID=dataTable2.getValueOnOtherModule("EVS_SapRSIGetDataFromSAPDB","SKUCode",0);
          String AGGR_AVAIL_QTY=dataTable2.getValueOnOtherModule ("EVS_SapRSIGetDataFromSAPDB","AGGR_AVAIL_QTY",0);
          String rough_stock_value=dataTable2.getValueOnOtherModule ("EVS_SapRSIGetDataFromSAPDB","rough_stock_value",0);
			
			String Query = "select * from SAPABAP1.\"/OAA/RSI_SNP\" where " + "channel_id = '" + channelID + "' and "
					+ "ROUGH_STOCK_DATE >=to_date(now())and" + " AGGR_AVAIL_QTY between 1 and 50000 "
					+ "and rough_stock_value = 'G' order by rand() limit 1";
 
          //Hard coded Article ID below as could not find data, proper query is above
//          String Query = "select * from SAPABAP1.\"/OAA/RSI_SNP\" where channel_id = 'SO61' and ROUGH_STOCK_DATE >=to_date(now())"
//          		+ "and AGGR_AVAIL_QTY between 1 and 50000 and rough_stock_value = 'G' and article_id = '000000000010115998' order by rand() limit 1";
          
			//System.out.println("Query:"+Query);
          ResultSet rs = hn.ExecuteQuery(Query);
          int rowsCountReturned = hn.GetRowsCount(rs);
          //System.out.println("rowsCountReturned: "+rowsCountReturned);
          String SKUCode=getColumnValue(hn,rs ,"ARTICLE_ID");
          //System.out.println("SKUCode: "+SKUCode);
          String AGGR_AVAIL_QTY_1=getColumnValue(hn,rs ,"AGGR_AVAIL_QTY");
          String AGGR_AVAIL_QTYFinal=AGGR_AVAIL_QTY_1.split("\\.")[0];
          //System.out.println("Original AGGR_AVAIL_QTY: "+AGGR_AVAIL_QTYFinal);
          String AGGR_AVAIL_QTY_AFTER_CHECKOUT = String.valueOf((Integer.parseInt(AGGR_AVAIL_QTYFinal)-1));
          dataTable2.setValueOnOtherModule ("EVS_SapRSIGetDataFromSAPDB","SKUCode",SKUCode,0);
          dataTable2.setValueOnOtherModule("evs_ProductSearch", "specificProduct", SKUCode, 0);
          dataTable2.setValueOnOtherModule("EVS_SapRSIGetDataFromSAPDB","AGGR_AVAIL_QTY",AGGR_AVAIL_QTY_AFTER_CHECKOUT,0);
          hn.closeDB();
    }



    public String getColumnValue(hana hn,ResultSet rs ,String tableColumn) throws SQLException {
        List<String> alldatatableColumnValue = hn.GetRowdataByColumnName(rs, tableColumn);
        String tableColumnValue=alldatatableColumnValue.get(0);
        return tableColumnValue;
    }
    public hana connectToSap(ExtentTest test) throws IOException, SQLException {
        String DBinstance = dataTable2.getValueOnOtherModule ("EVS_SapRSIGetDataFromSAPDB","DB_Instance",0);
        //ECCQA
        String primaryKey="DB_Instance";
        String conSheet="DB_connection_master";
        String Server =dataTable2.getRowUsingReferenceAndKey(conSheet,primaryKey,DBinstance,"Host");
        String Port =  dataTable2.getRowUsingReferenceAndKey(conSheet,primaryKey,DBinstance,"port");
        String Username =  dataTable2.getRowUsingReferenceAndKey(conSheet,primaryKey,DBinstance,"Username");
        String Password =  dataTable2.getRowUsingReferenceAndKey(conSheet,primaryKey,DBinstance,"Password");
        String TypeOfDB = dataTable2.getRowUsingReferenceAndKey(conSheet,primaryKey,DBinstance,"TypeOfDB");
        hana hn =new hana(TypeOfDB,Server,Port,Username,Password,test);
        return hn;
    }
    
    
    public void getRSIItemInMagento(ExtentTest test) throws IOException, Exception {
        action.click(catalogTab,"catalogTab",test);
        action.explicitWait(2000);
        action.click(productsTab,"productsTab",test);
        action.explicitWait(9000);
        if(action.waitUntilElementIsDisplayed(Clearbutton, 10000)) {
        action.click(Clearbutton,"Clearbutton",test);
        }
        action.explicitWait(5000);
        action.click(magentoFilterTab,"magentoFilterTab",test);
        String sky = dataTable2.getValueOnOtherModule ("EVS_SapRSIGetDataFromSAPDB","SKUCode",0);
        action.writeText(sku,sky,"skuInputTest",test);
        action.click(magentoApplyFilterTab,"magentoApplyFilterTab",test);
        action.explicitWait(5000);
        if(action.waitUntilElementIsDisplayed(clickEdit, 6000)) {
        	action.javaScriptClick(clickEdit, "clickEdit", test);
        }else {
        	//action.CompareResult("Records Returned", "True", "False", test);
        	throw new Exception("No Records Have Been Found");        	
        }
//        action.explicitWait(5000);
        List<WebElement> storeCount = driver.findElements(By.xpath("//*[@id=\"container\"]/div/div[2]/div[2]/div[2]/fieldset/div[2]/div/div[2]/table/tbody/tr"));
        for ( WebElement i : storeCount ) {
            WebElement z1= i.findElement(By.xpath("./child::td[1]"));
            WebElement z4= i.findElement(By.xpath("./child::td[4]/div/div[2]/input"));
            String store=dataTable2.getValueOnOtherModule("EVS_SapRSIGetDataFromSAPDB","Store",0);
            if(z1.getText().equals(store)) {
                action.CompareResult(" Item Qty SapDB ", dataTable2.getValueOnOtherModule("EVS_SapRSIGetDataFromSAPDB","AGGR_AVAIL_QTY",0),z4.getAttribute("value"), test);
            }
        }
		
		action.explicitWait(12000);
		action.waitUntilElementIsDisplayed(sapDataTab, 10000);
		action.click(sapDataTab, "sapDataTab", test);
		action.scrollElemetnToCenterOfView(roughStockIndicatorAct, "Scroll to Rough Stock Indicator", test);
		System.out.println(action.getText(roughStockIndicatorAct, "roughStockIndicator", test));
		action.CompareResult(" rough Stock Indicator SAP DB ",
				dataTable2.getValueOnOtherModule("EVS_SapRSIGetDataFromSAPDB", "rough_stock_value", 0),
				action.getText(roughStockIndicatorAct, "roughStockIndicator", test), test);		 

    }
}