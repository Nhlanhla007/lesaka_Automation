package KeywordManager;

import EVSPageOblects.EVS_Login;
import JDGroupPageObjects.*;
import SAP_HanaDB.SAPCustomerRelated;
import SAP_HanaDB.SAPorderRelated;
import SAP_HanaDB.SapRSI;
import com.aventstack.extentreports.ExtentTest;
import emailverification.ICGiftCardVerification;
import emailverification.ICWishlistverification;
import emailverification.ic_PasswordForgotEmailVerification;
import emailverification.ic_ResetPasswordEmailLink;
import ic_MagentoPageObjects.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Action;
import utils.DataTable2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class JDGKeyManager {
    WebDriver driver;
    DataTable2 dataTable2;
    Action action;
    LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2 = new LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>();

    public JDGKeyManager(WebDriver driver, DataTable2 dataTable2, LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2){
        this.driver=driver;
        this.dataTable2=dataTable2;
        this.dataMap2=dataMap2;
        action = new Action(driver);
    }
    public void setDriver(WebDriver driver){
        this.driver=driver;
    }

    public void runKeyWord (String actionToRun,int testcaseID,HashMap<String, Integer> occCount, ExtentTest test) throws Exception {
        String moduleToRun = actionToRun;
        //IC classes below
        IConnection ic = new IConnection(driver, dataTable2);
        Magento_UserInfoVerification Magentoverify = new Magento_UserInfoVerification(driver, dataTable2);
        ic_PaymentOption Payopt = new ic_PaymentOption(driver, dataTable2);
        ic_PayUPayment PayU = new ic_PayUPayment(driver, dataTable2);
        Ic_Products products = new Ic_Products(driver, dataTable2);
        IC_Cart icCart = new IC_Cart(driver, dataTable2);
        ic_AccountConfirmation icAccountConfirmation = new ic_AccountConfirmation(driver, dataTable2);
        ICDelivery icDelivery = new ICDelivery(driver, dataTable2);
        ic_Magento_Login icMagento = new ic_Magento_Login(driver, dataTable2);
        MagentoOrderStatusPage orderStatus = new MagentoOrderStatusPage(driver, dataTable2);
        ic_MagentoOrderSAPnumber icOrderSAPnumber = new ic_MagentoOrderSAPnumber(driver, dataTable2);
        ic_AccountInformation verifyAcc = new ic_AccountInformation(driver, dataMap2);
        ic_NewAccountCreation newAcc = new ic_NewAccountCreation(driver, dataTable2);
        MagentoRetrieveCustomerDetailsPage custDetails = new MagentoRetrieveCustomerDetailsPage(driver, dataTable2);
        MagentoAccountInformation MagentoCustDetail = new MagentoAccountInformation(driver, dataTable2);
        MagentoRegisterNewUser MagentonewUser = new MagentoRegisterNewUser(driver, dataTable2);
        ICUpdateCustomer icUpdateUser = new ICUpdateCustomer(driver, dataTable2);
        ic_GiftCardPurchase icGiftCardPurchase = new ic_GiftCardPurchase(driver, dataTable2);
        admin_UserUpdate adminUserUpdate = new admin_UserUpdate(driver, dataTable2);
        customerValidationUpdates customerVerifyEdits = new customerValidationUpdates(driver, dataTable2);
        ic_Login ic_login = new ic_Login(driver, dataTable2);
        ic_verifyDeliveryOptions icDeliveryOptionDisplay = new ic_verifyDeliveryOptions(driver, dataTable2);
        ic_invalidLoginCreds ic_invalidCredslogin = new ic_invalidLoginCreds(driver, dataTable2);
        ic_LoginPasswordIsSecured icPasswordSecured = new ic_LoginPasswordIsSecured(driver, dataTable2);
        ic_forgotPasswordLink icforgottenPassLink = new ic_forgotPasswordLink(driver, dataTable2);
        verifyForgotPassword icVerifyForgotPass = new verifyForgotPassword(driver, dataTable2);
        ic_PasswordForgotEmailVerification icForgotEmailSent = new ic_PasswordForgotEmailVerification(driver, dataTable2);
        ic_ResetPasswordEmailLink ResetPasswordLink = new ic_ResetPasswordEmailLink(driver, dataTable2);
        ic_CashDepositPayment ic_cashDepositPayment = new ic_CashDepositPayment(driver, dataTable2);
        SAPorderRelated SaporderRelated = new SAPorderRelated(driver, dataMap2, dataTable2);
        ICGiftCardVerification icGiftCardVerification = new ICGiftCardVerification(driver, dataTable2);
        ic_GiftCardUsability GiftCardUsability = new ic_GiftCardUsability(driver, dataTable2);
        ic_existingAddress icExistingAddress = new ic_existingAddress(driver, dataTable2);
        ic_RedeemGiftCard icRedeemGiftCard = new ic_RedeemGiftCard(driver, dataTable2);
        ic_SearchMinimumCharacter icMinimumCharacter = new ic_SearchMinimumCharacter(driver, dataTable2);
        SAPCustomerRelated customerDB = new SAPCustomerRelated(driver, dataMap2, dataTable2);
        IC_RetriveOrderID ic_RetriveOrderID = new IC_RetriveOrderID(driver, dataTable2);
        IC_RetriveGiftCardOrderId ic_RetriveGiftCardOrderID = new IC_RetriveGiftCardOrderId(driver, dataTable2);
        admin_GiftCardReport giftCardReport = new admin_GiftCardReport(driver, dataTable2);
        Magento_CancelSalerOrderCreditMemo CancelSalerOrderCreditMemo = new Magento_CancelSalerOrderCreditMemo(driver, dataTable2);
        Magento_CancelSalesorderVerification CancelSalesorderVerification = new Magento_CancelSalesorderVerification(driver, dataTable2);
        Magento_CreditApp_NavigateFilter CreditApp_NavigateFilter = new Magento_CreditApp_NavigateFilter(driver, dataTable2);
        Magento_CreditStatusVerification CreditStatusVerification = new Magento_CreditStatusVerification(driver, dataTable2);
        ic_RefreshLogoHomepage icLogo = new ic_RefreshLogoHomepage(driver, dataTable2);
        ic_EnterBasicDetails icEnterBasicDetails = new ic_EnterBasicDetails(driver, dataTable2);
        ic_SpouseDetails icEnterSpouseInfo = new ic_SpouseDetails(driver, dataTable2);
        ic_ContactDetailsLoan icContactInfo = new ic_ContactDetailsLoan(driver, dataTable2);
        ic_PopularSearch PopularSearch = new ic_PopularSearch(driver, dataTable2);
        ic_SearchTextReturningNoResult icReturnNoResults = new ic_SearchTextReturningNoResult(driver, dataTable2);
        IC_CreditAppEmploymentDetails creditAppEmployDetails = new IC_CreditAppEmploymentDetails(driver, dataTable2);
        IC_CreditAppAddressDetails creditAppAddressDetails = new IC_CreditAppAddressDetails(driver, dataTable2);
        ic_SubscriberNewsletter_DuplicateEmailaddress ic_SubscribeNews_DupliEmailID = new ic_SubscriberNewsletter_DuplicateEmailaddress(
                driver, dataTable2);
        ic_newLetterInvalidEmail icNewsletterEmail = new ic_newLetterInvalidEmail(driver, dataTable2);
        ic_Subscriber_Newsletter_ValidEmailaddress ic_SubscribeNewsletter = new ic_Subscriber_Newsletter_ValidEmailaddress(driver, dataTable2);
        IC_ProductsSortBy productsSortBy = new IC_ProductsSortBy(driver, dataTable2);
        ic_WishlistToCart IC_WishlistToCart = new ic_WishlistToCart(driver, dataTable2);
        ic_verifyWishlistItem verifyWishlistItem = new ic_verifyWishlistItem(driver, dataTable2);
        ic_RemoveFromcart RemoveFromcart = new ic_RemoveFromcart(driver, dataTable2);
        ic_WishList WishList = new ic_WishList(driver, dataTable2);
        ic_CompareProducts productsCompared = new ic_CompareProducts(driver, dataTable2);
        ic_NavigetoWishlist NavigetoWishlist = new ic_NavigetoWishlist(driver, dataTable2);
        IC_verifyLogin ic_verifyLogin = new IC_verifyLogin(driver, dataTable2);
        IC_IncreaseQuanityInCart increQuantity = new IC_IncreaseQuanityInCart(driver, dataTable2);
        IC_RemoveItemsFromCart removeItemsFromCart = new IC_RemoveItemsFromCart(driver, dataTable2);
        ic_SendWishlistToEmail SendWishlistToEmail = new ic_SendWishlistToEmail(driver, dataTable2);
        ICWishlistverification icEmailWishlistverification = new ICWishlistverification(driver, dataTable2);
        RedirectToProdDetailPageFromCart redirectAndVerify = new RedirectToProdDetailPageFromCart(driver, dataTable2);
        IC_Pagination pagination = new IC_Pagination(driver, dataTable2);
        IC_Parallel_login parrallel_ic_Login = new IC_Parallel_login(driver, dataTable2);
        SapRSI sapRSI = new SapRSI(driver, dataTable2);
        ic_validateProductSKU SKUproduct = new ic_validateProductSKU(driver, dataTable2);
        //evs classes below
        EVS_Login evs_Login = new EVS_Login(driver, dataTable2);
        ExtentTest test1 = test.createNode(moduleToRun);
        int rowNumber = -1;
        if (dataMap2.containsKey(moduleToRun + "++")) {
            rowNumber = findRowToRun(dataMap2.get(moduleToRun + "++"), occCount.get(moduleToRun), testcaseID);
        }
        int i = 0;
        WebElement el = null;
        switch (moduleToRun) {
            case "Login":
                ic.login(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "ic_login":
                ic_login.Login_ic(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "ic_invalidCredslogin":
                ic_invalidCredslogin.invalidLogin_ic(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "icPasswordSecured":
                icPasswordSecured.loginPasswordSafe(dataMap2.get("ic_login++"), test1, rowNumber);
                break;
            case "icForgotPasswordLink":
                icforgottenPassLink.forgotPasswordLink(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "icVerifyForgotPass":
                icVerifyForgotPass.forgotPasswordPage(dataMap2.get("accountCreation++"), test1, rowNumber);
                break;
            case "icEmailSentVerification":
                icForgotEmailSent.icVerifyNewPasswordEmailSent(dataMap2.get("accountCreation++"), test1, rowNumber);
                break;
            case "icResetForgottenPassword":
                ResetPasswordLink.clickLinkOnGmail(dataMap2.get(moduleToRun+ "++"), test1, rowNumber);
                ResetPasswordLink.resetNewPassword(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                ResetPasswordLink.clickUsedResetLink(dataMap2.get(moduleToRun+ "++"), test1, rowNumber);
                break;
            case "Logout":
                ic.logout(test1);
                break;
            case "CheckoutpaymentOption":
                Payopt.CheckoutpaymentOption(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "CheckoutpaymentOptionGiftCard":
                Payopt.CheckoutpaymentOptionGiftCard(dataMap2.get("deliveryPopulation++"),test1,rowNumber);
                break;
            case "PayUPagePayment":
                PayU.PayUPagePayment(dataMap2.get(moduleToRun+ "++"), test1, rowNumber);
                break;
            case "ProductSearch":
                products.ic_SelectProductAndAddToCart(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "ClearCart":
                icCart.removeAllItemsInCart(test1);
                break;
            case "CompareProducts":
                productsCompared.validateAddedProductsCompare(test1, el);
                productsCompared.clearAllProduct(test1, el);
                break;
            /*
             * case "iCcartVerification": icCart.iCcartVerification(test1); break;
             */
            case "deliveryPopulation":
                icDelivery.deliveryPopulation(dataMap2.get("deliveryPopulation" + "++"), test1, rowNumber);
                break;
            case "deliveryPopulationGiftCard":
                rowNumber = findRowToRun(dataMap2.get("deliveryPopulation++"), 0, testcaseID);
                icDelivery.deliveryPopulationGiftCard(dataMap2.get("deliveryPopulation++"), test1, rowNumber);
                break;
            case "Login_magento":
                icMagento.Login_magento(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "OrderStatusSearch":
                orderStatus.navigateToOrderPage(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "GenerateOrderSAPnumber":
                icOrderSAPnumber.GenerateOrderSAPnumber(dataMap2.get(moduleToRun+ "++"), test1, rowNumber);
                break;
            case "EnterNewUserDetails":
                //newAcc.EnterNewUserDetails(dataMap2.get(currentKeyWord+"++"),test1,rowNumber);
                break;
//			case "Verify_Acount_Information":
//				verifyAcc.Verify_Acount_Information(dataMap2.get(currentKeyWord+"++"),test1,rowNumber);
//				break;
            case "accountCreation":
                newAcc.accountCreation(dataMap2.get(moduleToRun + "++"), test1, rowNumber);

                break;
            case "icAccountConfirmation":
                icAccountConfirmation.AccountCreationConfirmation(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "RetrieveCustomerDetails":
                custDetails.retrieveCustomerDetails(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "VadidateCustomerInfo_backend":
                rowNumber = findRowToRun(dataMap2.get("accountCreation++"), 0, testcaseID);
                MagentoCustDetail.VadidateCustomerInfo_backend(dataMap2.get("accountCreation++"), test1, rowNumber);
                break;
            case "Magento_UserInfoVerification":
                rowNumber = findRowToRun(dataMap2.get("accountCreation++"), 0, testcaseID);
                Magentoverify.Validate_UserInfobackend(dataMap2.get("accountCreation" + "++"), test1, rowNumber);
                break;
            case "CreateaccountBackend":
                MagentonewUser.CreateAccount_validateInfo_Backend(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "ICUpdateUser":
                ArrayList<HashMap<String, ArrayList<String>>> mySheets = new ArrayList<HashMap<String, ArrayList<String>>>();
                mySheets.add(dataMap2.get("ICUpdateUser"+ "++"));
                mySheets.add(dataMap2.get("ic_login++"));
                icUpdateUser.updateAccount(mySheets, test1, testcaseID);
                break;
            case "customerValidationUpdates":
                ArrayList<HashMap<String, ArrayList<String>>> adminsheets = new ArrayList<HashMap<String, ArrayList<String>>>();
                adminsheets.add(dataMap2.get("customerValidationUpdates" + "++"));
                //rowNumber = findRowToRun(dataMap2.get("accountCreation++"), 0, testcaseID);
                customerVerifyEdits.VerifyCustomerinfoUpadtes(adminsheets, test1, testcaseID);
                break;
            case "adminUserUpdate":
                ArrayList<HashMap<String, ArrayList<String>>> adminSheets = new ArrayList<HashMap<String, ArrayList<String>>>();
                adminSheets.add(dataMap2.get(moduleToRun + "++"));
                adminUserUpdate.editCustomerDetails(adminSheets, test1, testcaseID);
                break;
            case "ic_CashDepositPayment":
                ArrayList<HashMap<String, ArrayList<String>>> RequiredSheets = new ArrayList<HashMap<String, ArrayList<String>>>();
                RequiredSheets.add(dataMap2.get(moduleToRun + "++"));
                RequiredSheets.add(dataMap2.get("Login_magento++"));
                ic_cashDepositPayment.InvoiceCashDeposit(RequiredSheets, test1, testcaseID);
                break;
            case "icGiftCardPurchase":
                icGiftCardPurchase.purchaseGiftCard(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "SAP_OrderRelated":
                SaporderRelated.SAP_OrderDetailVadidation(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "icGiftCardVerificationSender":
                icGiftCardVerification.icGiftCardVerificationSender(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "getnumberOfEmails":
                rowNumber = findRowToRun(dataMap2.get("icGiftCardVerificationSender++"), 0, testcaseID);
                icGiftCardVerification.clearEmail(dataMap2.get("icGiftCardVerificationSender++"), test1, rowNumber);
                break;
            case "giftCardReport":
                ArrayList<HashMap<String, ArrayList<String>>> mySheet = new ArrayList<HashMap<String, ArrayList<String>>>();
                mySheet.add(dataMap2.get(moduleToRun + "++"));
                giftCardReport.giftCardReports(mySheet, test1, testcaseID);
                break;
            case "VeriyGiftcardUsableity":
                GiftCardUsability.VeriyGiftcardUsableity(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "icRedeemGiftCard":
                icRedeemGiftCard.redeemGiftCard(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "EnterBasicDetails":
                icEnterBasicDetails.enterBasicInfor(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "EnterSpouseInfor":
                icEnterSpouseInfo.enterSpouseDetails(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "EnterContact":
                icContactInfo.enterContactDetailsForLoan(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "icInvalidEmailNewsLetter":
                icNewsletterEmail.ic_NewsLetterInvalidEmail(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "icExistingAddress":
                icExistingAddress.AddressThere(test1);
                break;
            case "icSearchMinimumCharacter":
                icMinimumCharacter.icValidMinimumSearch(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "ic_RetriveOrderID":
                ic_RetriveOrderID.RetriveOrderID(test1);
                break;
            case "ic_RetriveGiftCardOrderID":
                ic_RetriveGiftCardOrderID.RetriveOrderID(test1);
                break;
            case "SapCustomer":
                ArrayList<HashMap<String, ArrayList<String>>> sheets = new ArrayList<HashMap<String, ArrayList<String>>>();
                sheets.add(dataMap2.get("accountCreation++"));
                sheets.add(dataMap2.get("deliveryPopulation++"));
                //sheets.add(dataMap2.get("SapCustomer++"));//Falls away
                sheets.add(dataMap2.get("ICUpdateUser++"));
                sheets.add(dataMap2.get("CreateaccountBackend++"));
                sheets.add(dataMap2.get("adminUserUpdate++"));
                customerDB.sapDbTests(dataMap2.get(moduleToRun + "++"), sheets, test1, testcaseID, rowNumber);
                break;
            case "CancelSalerOrderCreditMemo":
                CancelSalerOrderCreditMemo.magento_CancelSalesOrder(test1, rowNumber);
                break;
            case "CancelSalesorderVerification":
                CancelSalesorderVerification.verifyCancelOrderdetails_commentHistory(test1, rowNumber);
                break;
            case "CreditApp_NavigateFilter":
                CreditApp_NavigateFilter.VerifyCreditAppSelection(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "CreditStatusVerification":
                CreditStatusVerification.VerifyCreditAppStatus(dataMap2.get("CreditStatusVerification++"), test1, rowNumber);
                CreditStatusVerification.VerifyCreditAppStatus(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "icLogoHomepage":
                icLogo.homepageLogo(test1);
                break;
            case "icPopularSearch":
                PopularSearch.VerifyPopularSearch(test1, rowNumber);
                break;
            case "icSearchNoResultsReturned":
                icReturnNoResults.ic_DoesNotExtistSearch(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "CreditEnterEmploymentDetails":
                creditAppEmployDetails.dataInput(dataTable2, test1);
                break;
            case "CreditEnterAddressDetails":
                creditAppAddressDetails.dataInput(dataTable2, test1);
                break;
            case "ic_SubscribeNews_DupliEmailID":
                ic_SubscribeNews_DupliEmailID.SubscribeNewsletter_DuplicateEmail(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "IC_ProductsSortBy":
                productsSortBy.sortBy(test1);
                break;
            case "ic_NavigetoWishlist":
                NavigetoWishlist.NavigateToWishlist_verifymsg(test1);
                break;
            case "IC_ClearWishList":
                verifyWishlistItem.handleWishlistItem(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "ic_RemoveFromcart":
                RemoveFromcart.Clear_miniCart(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "ic_verifyLogin":
                ic_verifyLogin.IC_verifyLogin_addingProductTowishlist(test1, rowNumber);
                break;
            case "IC_WishlistToCart":
                IC_WishlistToCart.verifyProducts_wishlistTocart(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "IncreaseQuanityInCart":
                increQuantity.increaseQuantity(test1);
                break;
            case "RemoveArticleFromCart":
                removeItemsFromCart.removeItemFromCart(test1);
                break;
            case "SendWishlistToEmail":
                SendWishlistToEmail.ShareYourwishlist(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "icEmailWishlistverification":
                icEmailWishlistverification.icWishlistVerificationSender(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "RedirectToProdDetailPageFromCart":
                redirectAndVerify.verifyNavigationToProductDetailPageFromCart(test1);
                break;
            case "giftCardWithInvalidCouponCode":
                icRedeemGiftCard.giftCardWithInvalidCouponCode(test1);
                break;
            case "ic_logout":
                ic_login.logout(test1, dataMap2.get("ic_login++"), rowNumber);
                break;
            case "verifyCart":
                icCart.verifyCart(test1);
                break;
            case "Pagination":
                pagination.paginate(test1);
                break;
            case "verifyDeliveryOption":
                icDeliveryOptionDisplay.validateDeliveryOptionsDisplays(test1, rowNumber);
                break;
            case "parrallel_ic_Login":
                parrallel_ic_Login.checkParallelExecution(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "SapRSIGetDataFromSAPDB":
                sapRSI.getDataFromSAPDB(test1);
                break;
            case "getRSIItemInMagento":
                sapRSI.getRSIItemInMagento(test1);
                break;
            case "ic_SKUproduct":
                SKUproduct.displayProductSKU(test1, el);
                break;
            case "ic_SubscribeNewsletter":
                ic_SubscribeNewsletter.SubscribeNewsletter(dataMap2.get(moduleToRun + "++"), test1, rowNumber);
                break;
            case "NavigateToWishlist_VerifyLoginPageAppear":
                NavigetoWishlist.NavigateToWishlist_verifyLoginPageAppears(test1);
                break;
            case "evs_Login":
                evs_Login.Login(test1);
                break;

        }
    }
    public int findRowToRun (HashMap < String, ArrayList < String >> input,int occCount, int testcaseID){
        int numberRows = input.get("TCID").size();
        int rowNumber = -1;
        occCount = occCount + 1;
        for (int i = 0; i < numberRows; i++) {
            if (input.get("TCID").get(i).equals(Integer.toString(testcaseID))
                    && input.get("occurence").get(i).equals(Integer.toString(occCount))) {
                rowNumber = i;
            }
        }
        return rowNumber;
    }
}