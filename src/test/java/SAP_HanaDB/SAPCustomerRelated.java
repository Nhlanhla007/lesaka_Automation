package SAP_HanaDB;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import JDGroupPageObjects.ic_Login;
import ic_MagentoPageObjects.MagentoAccountInformation;
import ic_MagentoPageObjects.MagentoRetrieveCustomerDetailsPage;
import utils.Action;
import utils.hana;


public class SAPCustomerRelated {

	WebDriver driver;
    Action action;
    LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2 =null;
    String bp = SAPorderRelated.BPnumber; //BP Number -->Customer BP number, if line 99 is null set this.
    hana hn;
    MagentoRetrieveCustomerDetailsPage magentoRetrieve;
    MagentoAccountInformation magentoVerification;
    static Map<String, String> dataStore;
    public SAPCustomerRelated(WebDriver driver, LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2) {
    	  this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	        dataStore = new LinkedHashMap<>();
	        this.dataMap2=dataMap2;
	        //magentoRetrieve = new MagentoRetrieveCustomerDetailsPage(driver, dataMap2);
	        //magentoVerification = new MagentoAccountInformation(driver, dataMap2);
    }
    
    public int getConnectionRow(String Instance){
    	HashMap<String, ArrayList<String>> connectiondetailSheet = dataMap2.get("DB_connection_master++");//Db connection h
    	int finalrow=-1;
    	int noofRows = connectiondetailSheet.get("DB_Instance").size();
    	for(int con =0;con<noofRows;con++){
    		if(Instance == connectiondetailSheet.get("DB_Instance").get(con)){
    			finalrow=con;
    			
    		}
    	}
    	return finalrow;
    }
  
    
    
    enum kna1Columns{
		KUNNR,NAME1,ADRNR,ANRED,//ERDAT,ERNAM
	}
	
	enum adrcColumns{
		CITY1,POST_CODE1,STREET,TEL_NUMBER,TIME_ZONE,COUNTRY,REGION,LOCATION,BEZEI,STR_SUPPL3
	}
	
	enum but000Columns{
		
		NAME_LAST,
		NAME_FIRST,
		INITIALS,
		BIRTHDT,
		XSEXM,
		XSEXF,

	}
	
	enum vatNumberColumns{
		TAXNUM
	}
	
	enum adrc6Columns{
		SMTP_ADDR,SMTP_SRCH,
	}
	
	
	enum adrc2Columns{
		TELNR_LONG
	}
	
	
	enum but0IDColumns{
		IDNUMBER,
	}
	//Data for database connectivity
	String Server;
	String Port;
	String Username;
	String Password;
	String TypeOfDB;
	
	
	//Data from account creation excel sheet
	String vatNumberFlag ;
	String email ;
	String website;
	String taxVatNumberFlag;
	public void sapDbTests(HashMap<String, ArrayList<String>> input,ArrayList<HashMap<String, ArrayList<String>>> mySheets,ExtentTest test,int testcaseID,int rowNumber) throws Exception {
		int sheetRow1= findRowToRun(mySheets.get(0), 0, testcaseID); //accountCreation
		int sheetRow2= findRowToRun(mySheets.get(1), 0, testcaseID); //deliverypopulation
		//int sheetRow3= findRowToRun(mySheets.get(2), 0, testcaseID); //typeOf sap validation //uses input.get("Value").get(rowNumber)
		int sheetRow4= findRowToRun(mySheets.get(2), 0, testcaseID); //updatesheet
		int createCustomerBackEndSheet = findRowToRun(mySheets.get(3), 0, testcaseID); //magentoCreate back end sheet
		int customerUpdateBackEndSheet = findRowToRun(mySheets.get(4), 0, testcaseID); //magentoCreate back end sheet
		
		  	String DBinstance = input.get("DB_Instance").get(rowNumber); 
		  	int irow= getConnectionRow(DBinstance);
		  
		  	//Get SAP Details
			 Server = dataMap2.get("DB_connection_master++").get("Host").get(irow);// "11.19.2.172";
			 Port = dataMap2.get("DB_connection_master++").get("port").get(irow);
			 Username = dataMap2.get("DB_connection_master++").get("Username").get(irow);
			 Password = dataMap2.get("DB_connection_master++").get("Password").get(irow);
			 TypeOfDB = dataMap2.get("DB_connection_master++").get("TypeOfDB").get(irow);
		  
		 
		
		String typeOfSAPValidation = input.get("typeOfSapValidation").get(rowNumber); 
		//System.out.println(mySheets.get(2).get("firstName_output").get(sheetRow4));
		email = mySheets.get(0).get("emailAddress").get(sheetRow1);
		website = mySheets.get(0).get("WebSite").get(sheetRow1);
		
		//If the update flag is checked it takes the latest updated email, 
		//if not checked it takes the original email
		String updateEmailFlag =null;
		String updateEmail= null;
		String customerMagentoEmail = null;
		
		String updatedMagentoBillingEmailFlag = null;
		String updatedMagentoBillingEmail = null;		
		String currentCustomerMagentoBillingEmail = null;
		if(typeOfSAPValidation.equalsIgnoreCase("Customer Update")) {
			updateEmailFlag = mySheets.get(2).get("email").get(sheetRow4);
			updateEmail = mySheets.get(2).get("email_output").get(sheetRow4);
			
			if(updateEmailFlag.equalsIgnoreCase("yes")) {
			navigateToCustomerBpNumber(updateEmail, website, test);
			}else {
				//email that logged into ic with
				navigateToCustomerBpNumber(ic_Login.Username, website, test);
			}
			magentoVerification.getPartnerNumber(test);
		}else if(typeOfSAPValidation.equalsIgnoreCase("Customer Creation Magento Admin")) {
			customerMagentoEmail =mySheets.get(3).get("Email").get(createCustomerBackEndSheet);
			navigateToCustomerBpNumber(customerMagentoEmail, website, test);
			magentoVerification.getPartnerNumber(test);
		}else if(typeOfSAPValidation.equalsIgnoreCase("Customer Creation")) {
			navigateToCustomerBpNumber(email, website, test);
			magentoVerification.getPartnerNumber(test);
		}else if(typeOfSAPValidation.equalsIgnoreCase("Customer Update Magento Admin")) {
			updatedMagentoBillingEmailFlag = mySheets.get(4).get("email").get(customerUpdateBackEndSheet);
			if(updatedMagentoBillingEmailFlag.equalsIgnoreCase("yes")) {
				updatedMagentoBillingEmail = mySheets.get(4).get("adminEmail_output").get(customerUpdateBackEndSheet);
				navigateToCustomerBpNumber(updatedMagentoBillingEmail, "Main Website", test);
			}else {
				currentCustomerMagentoBillingEmail = mySheets.get(4).get("emailAddress_input").get(customerUpdateBackEndSheet);
				navigateToCustomerBpNumber(currentCustomerMagentoBillingEmail, "Main Website", test);
			}
			magentoVerification.getPartnerNumber(test);
				
		}

		//Get Customer partner number
		String SAPorderNumber=MagentoAccountInformation.ActualBPnumber;
		
		//If not partner number is returned throw an error
		if(SAPorderNumber == null) {
			throw new Exception("Partner Number is not generated");
		}
		
		if(typeOfSAPValidation.equalsIgnoreCase("Customer Update")) {
		taxVatNumberFlag = mySheets.get(2).get("taxVat").get(sheetRow4);
		}else {
			taxVatNumberFlag = "No";
		}
		//SAPorderNumber=SAPorderNumber.replace("[RabbitMQ] Order SAP Number: ", ""); 
		vatNumberFlag = mySheets.get(0).get("vatNumberFlag").get(sheetRow1);
		Map<String, String> customerDetails = customerSAPDetails(SAPorderNumber);
		
		//GET DETAILS FROM SAP
		String SAPFirstName = customerDetails.get("NAME_FIRST");
		String SAPLastName = customerDetails.get("NAME_LAST");
		String SAPEmail = customerDetails.get("SMTP_ADDR");
		String sapVatnumber = customerDetails.get("TAXNUM");
		String SAID = customerDetails.get("IDNUMBER");
		String passport = customerDetails.get("IDNUMBER");
		
		String SAPcity = customerDetails.get("CITY1");
		String SAPpostCode = customerDetails.get("POST_CODE1");
		String SAPStreetAddress = customerDetails.get("STREET");
		String SAPsuburb = customerDetails.get("LOCATION");
		String SAPProvince = customerDetails.get("BEZEI");
		String SAPbuildingDetails = customerDetails.get("STR_SUPPL3");
		
		
		switch (typeOfSAPValidation) {
		//Customer details for relevant properties are taken and compared to details in SAP
		case "Customer Update":
			String updateFirstNameFlag = mySheets.get(2).get("firstName").get(sheetRow4);			
			if(updateFirstNameFlag.equalsIgnoreCase("yes")){
			String updatedName = mySheets.get(2).get("firstName_output").get(sheetRow4);
			action.CompareResult("SAP Updated First Name", updatedName, SAPFirstName, test);
			}
			String updateLastNameFlag = mySheets.get(2).get("lastName").get(sheetRow4);
			if(updateLastNameFlag.equalsIgnoreCase("yes")) {
				String updatedLastName = mySheets.get(2).get("lastName_output").get(sheetRow4);
				action.CompareResult("SAP Updated Last name", updatedLastName, SAPLastName, test);
			}			
			if (taxVatNumberFlag.equalsIgnoreCase("yes")) {
				String taxVatNumeber = mySheets.get(2).get("taxVat_output").get(sheetRow4);
				action.CompareResult("SAP UpdatedTaxNumber", taxVatNumeber, sapVatnumber, test);
			}
			if (updateEmailFlag.equalsIgnoreCase("yes")) {
				String Email = updateEmail;
				action.CompareResult("SAP Updated Email", Email, SAPEmail, test);
			}
			
			String updateBillingFlag =mySheets.get(2).get("billingAddress").get(sheetRow4);
			if(updateBillingFlag.equalsIgnoreCase("yes")) {
				String updateBillingStreetFlag = mySheets.get(2).get("billing_streetAddress").get(sheetRow4);
				if(updateBillingStreetFlag.equalsIgnoreCase("yes")) {
					String updatedBilling  = mySheets.get(2).get("billing_streetAddress_output").get(sheetRow4);
					action.CompareResult("SAP Updated Billing Address", updatedBilling, SAPStreetAddress, test);
				}
				String updatedBuildingDetails = mySheets.get(2).get("billing_buildingDetails_output").get(sheetRow4);
				if(updatedBuildingDetails != null & SAPbuildingDetails != null) {
					action.CompareResult("SAP Building Details", updatedBuildingDetails, SAPbuildingDetails, test);
				}
				String billingProvince = mySheets.get(2).get("billing_provinceName_output").get(sheetRow4);
				action.CompareResult("SAP Updated Province", billingProvince, SAPProvince, test);
				
				String billingCity = mySheets.get(2).get("billing_city_output").get(sheetRow4);
				action.CompareResult("SAP Updated City", billingCity, SAPcity, test);
				
				String billingSuburb = mySheets.get(2).get("billing_suburb_output").get(sheetRow4);
				action.CompareResult("SAP Updated Billing Suburb", billingSuburb, SAPsuburb, test);
				
				String billingPostalCode = mySheets.get(2).get("billing_postalCode_output").get(sheetRow4);
				action.CompareResult("SAP Updated postal code", billingPostalCode, SAPpostCode, test);
				
			}
			
			break;
		case "Customer Creation":
			String customerCreationname = mySheets.get(0).get("firstName").get(sheetRow1);
			String customerCreationlastName = mySheets.get(0).get("lastName").get(sheetRow1);
			String vatNumber = null;
			String passportOrIdFlag = mySheets.get(0).get("identityType").get(sheetRow1);
			String passportOrId = null; 					    
			action.CompareResult("SAP First name", customerCreationname, SAPFirstName, test);
			action.CompareResult("SAP Last name", customerCreationlastName, SAPLastName, test);
			action.CompareResult("SAP Email", email, SAPEmail, test);
							
			if (vatNumberFlag.equalsIgnoreCase("Yes")) {
				vatNumber = mySheets.get(0).get("vatNumber").get(sheetRow1);
				
				action.CompareResult("SAP Vat number", vatNumber, sapVatnumber, test);
			}

			if (passportOrIdFlag.equalsIgnoreCase("ID")) {
				passportOrId = mySheets.get(0).get("identityNumber/passport").get(sheetRow1);
				
				action.CompareResult("SAP SA ID", passportOrId, SAID, test);
			} else if (passportOrIdFlag.equalsIgnoreCase("Passport")) {
				passportOrId = mySheets.get(0).get("identityNumber/passport").get(sheetRow1);
				
				action.CompareResult("SAP Passport", passportOrId, passport, test);
			}
			break;
		case"Customer Creation Magento Admin":
			String customerMagentoFirstName = mySheets.get(3).get("Firstname").get(createCustomerBackEndSheet);
			String cusomerMagnetoLastName =mySheets.get(3).get("Lastname").get(createCustomerBackEndSheet);			
			String customerMagentoIDFlag =mySheets.get(3).get("Identitynumber/passport").get(createCustomerBackEndSheet);//wtf
			String customerMagentoID =mySheets.get(3).get("SAID").get(createCustomerBackEndSheet);
			String customerMagentoPassport=mySheets.get(3).get("Passport").get(createCustomerBackEndSheet);
			String cusomterMagentoWebsite=mySheets.get(3).get("Website").get(createCustomerBackEndSheet);
			
			action.CompareResult("SAP First Name", customerMagentoFirstName, SAPFirstName, test);
			action.CompareResult("SAP Last name", cusomerMagnetoLastName, SAPLastName, test);
			action.CompareResult("SAP Email", customerMagentoEmail, SAPEmail, test);
			if(customerMagentoIDFlag.equalsIgnoreCase("SAID")) {
			action.CompareResult("SAP ID", customerMagentoID, SAID, test);
			}else {
			action.CompareResult("SAP Passport", customerMagentoPassport, SAID, test);
			}
			break;
		case "Customer Update Magento Admin":
			String firstNameUpdateFlag = mySheets.get(4).get("firstName").get(customerUpdateBackEndSheet);
			if(firstNameUpdateFlag.equalsIgnoreCase("yes")) {
				String updatedFirstName  =mySheets.get(4).get("adminFirstName_output").get(customerUpdateBackEndSheet);
				action.CompareResult("SAP firstname", updatedFirstName, SAPFirstName, test);
			}
			String lastNameUpdateFlag = mySheets.get(4).get("lastName").get(customerUpdateBackEndSheet);
			if (lastNameUpdateFlag.equalsIgnoreCase("yes")) {
				String updatedLastname = mySheets.get(4).get("adminLastName_output").get(customerUpdateBackEndSheet);
				action.CompareResult("SAP last name", updatedLastname, SAPLastName, test);
			}
			String taxVatNumberUpdateFlag = mySheets.get(4).get("taxVat").get(customerUpdateBackEndSheet);
			if (taxVatNumberUpdateFlag.equalsIgnoreCase("yes")) {
				String updatedTaxNumber = mySheets.get(4).get("adminTaxVat_output").get(customerUpdateBackEndSheet);
				if(updatedTaxNumber!=null | sapVatnumber != null) {
				action.CompareResult("SAP tax/Vat number", updatedTaxNumber, sapVatnumber, test);
				}
			}
			String 	emailUpdateFlag = mySheets.get(4).get("email").get(customerUpdateBackEndSheet); 
			if (emailUpdateFlag.equalsIgnoreCase("yes")) {
				action.CompareResult("SAP Email", updatedMagentoBillingEmail, SAPEmail, test);
				action.CompareResult("SAP email", customerMagentoEmail, SAPEmail, test);
			}
			String billingAddressUpdateFlag = mySheets.get(4).get("billingAddress").get(customerUpdateBackEndSheet);
			if (billingAddressUpdateFlag.equalsIgnoreCase("yes")) {
				String billingStreeAddressUpdateFlag = mySheets.get(4).get("billing_streetAddress").get(customerUpdateBackEndSheet);
				if(billingStreeAddressUpdateFlag.equalsIgnoreCase("yes")) {
				String updatedBillingStreeAddress = mySheets.get(4).get("adminBilling_streetAddress_output").get(customerUpdateBackEndSheet);
				action.CompareResult("SAP billing street", updatedBillingStreeAddress, SAPStreetAddress, test);
				}
				String updatedBillingBuilding = mySheets.get(4).get("billing_buildingDetails_output").get(customerUpdateBackEndSheet);
				String updatedBillingProvince = mySheets.get(4).get("billing_provinceName_output").get(customerUpdateBackEndSheet);
				String updatedBillingCity = mySheets.get(4).get("billing_city_output").get(customerUpdateBackEndSheet);
				String updatedBillingSuburb = mySheets.get(4).get("billing_suburb_output").get(customerUpdateBackEndSheet);
				String updatedBillingPostalCode = mySheets.get(4).get("billing_postalCode_output").get(customerUpdateBackEndSheet);
				
				action.CompareResult("SAP Billing building", updatedBillingBuilding, SAPbuildingDetails, test);
				action.CompareResult("SAP Province", updatedBillingProvince, SAPProvince, test);
				action.CompareResult("SAP City", updatedBillingCity, SAPcity, test);
				action.CompareResult("SAP Suburb", updatedBillingSuburb, SAPsuburb, test);
				action.CompareResult("SAP Postal Code", updatedBillingPostalCode, SAPpostCode, test);
			}
			break;
		default:
			break;
		}

	}
	
	public void navigateToCustomerBpNumber(String emailToSearch,String website,ExtentTest test) throws IOException {
		magentoRetrieve.navigateToCustomer(test);
		magentoRetrieve.searchForCustomer(emailToSearch, test);
		magentoRetrieve.tableData(emailToSearch, website, test);		
	}

	//The map below stores all customer data from DB
	static Map<String, String> custData ;
	public Map<String, String> customerSAPDetails(String sapOrderNumber) throws Exception {
		/*
		 * String Server = "11.19.2.172"; String Port = "30215"; String Username =
		 * "225505"; String Password = "Welc0me@2021"; String name = "DBconnect"; String
		 * DBType ="ECC_QA";
		 */
		String Query ="Select * from SAPEQ1.KNA1 WHERE KUNNR = '"+sapOrderNumber+"' Limit 1";
		hn =new hana(TypeOfDB,Server,Port,Username,Password); 
		ResultSet rs = hn.ExecuteQuery(Query);
		
		int rowsCountReturned = hn.GetRowsCount(rs);
		List<String> alldatainrows = hn.GetRowdataByColumnName(rs, "KUNNR");
		
		custData = kna1ColumnData(rs);
		
		return custData;
	}
	
	public Map<String, String> kna1ColumnData(ResultSet data) throws Exception {		
		//loop through the enum to get the names of all the fields
		String valueOfADNR = null ;
		String bpNumber = null;
		for(kna1Columns column : kna1Columns.values()) {
			List<String> d =hn.GetRowdataByColumnName(data, column.toString());
			dataStore.put(column.toString(), d.get(0));
			if(column.toString().equalsIgnoreCase("ADRNR")) {
				 valueOfADNR =  d.get(0);
			}
			if(column.toString().equalsIgnoreCase("KUNNR")) {
				bpNumber = d.get(0);
			}
		}
		but0ID(bpNumber);
		if(vatNumberFlag.equalsIgnoreCase("yes") | taxVatNumberFlag.equalsIgnoreCase("yes")) {
		vatNumber(bpNumber);
		}
		adrcWithprovince(bpNumber);
		adr6Data(valueOfADNR);
		but000Data(bpNumber);
		return  dataStore;
	}
	
	//need method that accepts ARDC, and searches 
	/*
	 * public void adrcData(String ADRCNumber) throws Exception { String query =
	 * "SELECT * FROM SAPEQ1.ADRC WHERE ADDRNUMBER = '"+ADRCNumber+"'"; ResultSet
	 * set = hn.ExecuteQuery(query); int rowsCountReturned = hn.GetRowsCount(set);
	 * System.out.println("Row count returned :"+rowsCountReturned); for(adrcColumns
	 * adrc : adrcColumns.values()) { List<String> d =hn.GetRowdataByColumnName(set,
	 * adrc.toString()); dataStore.put(adrc.toString(), d.get(0)); } }
	 */
	
	//method for but000
	public void but000Data(String bpNumber) throws Exception {
		String Query ="Select * from SAPEQ1.BUT000 AS adr WHERE PARTNER = '"+bpNumber.trim()+"' Limit 1";
		ResultSet set = hn.ExecuteQuery(Query);
		int rowsCountReturned = hn.GetRowsCount(set);
		for(but000Columns but000 : but000Columns.values()) {
			List<String> d =hn.GetRowdataByColumnName(set, but000.toString().trim());
			if(!d.isEmpty()) {
			dataStore.put(but000.toString(), d.get(0));
			}else {
				dataStore.put(but000.toString(), null);
			}
		}
	}
	public void adr6Data(String ADRCNumber) throws SQLException {
		String query = "SELECT * FROM SAPEQ1.ADR6 WHERE ADDRNUMBER = '"+ADRCNumber+"' AND FLG_NOUSE = ''";
		ResultSet set = hn.ExecuteQuery(query);
		int rowsCountReturned = hn.GetRowsCount(set);
		for(adrc6Columns but000 : adrc6Columns.values()) {
			List<String> d =hn.GetRowdataByColumnName(set, but000.toString().trim());
			if(!d.isEmpty()) {
			dataStore.put(but000.toString(), d.get(0));
			}else {
				dataStore.put(but000.toString(), null);
			}
		}
	}
	
	public void vatNumber(String bpNumber) throws SQLException {
		String query = "SELECT * FROM SAPEQ1.DFKKBPTAXNUM  WHERE PARTNER = '"+bpNumber+"'";
		ResultSet set = hn.ExecuteQuery(query);
		int rowsCountReturned = hn.GetRowsCount(set);
		for(vatNumberColumns but000 : vatNumberColumns.values()) {
			List<String> d =hn.GetRowdataByColumnName(set, but000.toString().trim());
			if(!d.isEmpty()) {
			dataStore.put(but000.toString(), d.get(0));
			}else {
				dataStore.put(but000.toString(), null);
			}
		}
	}
	
	public void adr2(String ADRCNumber) throws Exception {
		String query = "SELECT * FROM SAPEQ1.ADRC2 WHERE ADDRNUMBER = '"+ADRCNumber+"' AND FLG_NOUSE = ''";
		ResultSet set = hn.ExecuteQuery(query);
		int rowsCountReturned = hn.GetRowsCount(set);
		for(adrc2Columns but000 : adrc2Columns.values()) {
			List<String> d =hn.GetRowdataByColumnName(set, but000.toString().trim());
			if(!d.isEmpty()) {
			dataStore.put(but000.toString(), d.get(0));
			}else {
				dataStore.put(but000.toString(), null);
			}
		}
	}
	
	public void but0ID(String bpNumber) throws Exception {
		String query = "SELECT * FROM SAPEQ1.BUT0ID WHERE PARTNER = '"+bpNumber+"'";
		ResultSet set = hn.ExecuteQuery(query);
		int rowsCountReturned = hn.GetRowsCount(set);
		for(but0IDColumns but000 : but0IDColumns.values()) {
			List<String> d =hn.GetRowdataByColumnName(set, but000.toString().trim());
			if(!d.isEmpty()) {
			dataStore.put(but000.toString(), d.get(0));
			}else {
				dataStore.put(but000.toString(), null);
			}
		}
	}
	
	public void adrcWithprovince(String BpNumber) throws Exception {
		String query = "select * from SAPEQ1.ADRC "
				+ "inner join SAPEQ1.T005U on ADRC.COUNTRY = T005U.LAND1 and ADRC.REGION = T005U.BLAND inner join SAPEQ1.BUT021_FS on ADRC.ADDRNUMBER = BUT021_FS.ADDRNUMBER "
				+ "where BUT021_FS.PARTNER = '"+BpNumber+"' and BUT021_FS.ADR_KIND = 'ZDELIVERY' and T005U.MANDT = 000 and T005U.SPRAS = 'E'";
		ResultSet set = hn.ExecuteQuery(query);
		int rowsCountReturned = hn.GetRowsCount(set);
		for (adrcColumns adrc : adrcColumns.values()) {
			List<String> d = hn.GetRowdataByColumnName(set, adrc.toString());
			if(!d.isEmpty()) {
			dataStore.put(adrc.toString(), d.get(0));
			}else {
				dataStore.put(adrc.toString(), null);
			}
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