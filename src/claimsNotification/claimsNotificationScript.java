package claimsNotification;

	import java.util.concurrent.TimeUnit;
	import org.openqa.selenium.By;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.firefox.FirefoxDriver;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.Select;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import java.util.concurrent.TimeUnit;
	import org.apache.commons.logging.Log;
	import org.apache.log4j.Logger;
	import org.apache.log4j.xml.DOMConfigurator;
	import utility.Constant;
	import utility.ExcelUtils;

	public class claimsNotificationScript {
	    /**
			* @param args
	    */
		private static WebDriver driver;
		static String url = "https://piptesta2a.crif.com/";
		static String usrnmeField = ".//*[@id='ContentPlaceHolder1_UsernameTextBox']";
		static String pswdField = ".//*[@id='ContentPlaceHolder1_PasswordTextBox']";
		static String lgnBtn = ".//*[@id='ContentPlaceHolder1_Button1']";
		static String srchBtn = "html/body/div[1]/div[1]/ul/li[4]/a";
		static String appIdField = "html/body/div[1]/div[2]/form/div[3]/div[1]/fieldset/div/table/tbody/tr[1]/td[1]/table/tbody/tr[1]/td[2]/input";
		static String newCaseBtn = "html/body/div[1]/div[2]/form/div[1]/div[1]/div/div[2]/div/div[2]/input";
		static String searchBtn = "html/body/div[1]/div[2]/form/div[3]/div[1]/fieldset/div/table/tbody/tr[4]/td[3]/input[1]";
		static String claimValue = "html/body/div[1]/div[2]/form/div[2]/div/div[1]/div[1]/fieldset[1]/table/tbody/tr[2]/td[2]/select";
		static String houseNumField = "html/body/div[1]/div[2]/form/div[2]/div/div[1]/div[1]/fieldset[2]/fieldset/table[2]/tbody/tr[2]/td[2]/input";
		static Logger log = Logger.getLogger("devpinoyLogger"); 

		public static void main(String[] args) throws Exception {	
			mojLogin();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			createNewCase();
			addCompanyDetails("1112247717");
			adddefendantDetails("Mr","Gareth", "Griffiths", "CF10 3AX", "ABC123", "P60000954");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			addClaimantDetails();
			driver.quit();
		}
		
	/*################################################################################*/
	/*############################## Sub Methods #####################################*/
	/*################################################################################*/
		
		public static void mojLogin() throws Exception{
			ExcelUtils.setExcelFile(Constant.Path_TestData + Constant.File_TestData,"Sheet1");
			String username = ExcelUtils.getCellData(1, 1);
			String pswrd = ExcelUtils.getCellData(1, 2);		
			
			driver = new FirefoxDriver();
			log.info("New instance of Firefox started");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			log.info("Implicit wait was executed 30secs");
			driver.get(url);
			log.info("This webpage was opened: "+url);
			driver.findElement(By.xpath(usrnmeField)).sendKeys(username);
			log.info(username+" was entered into the username field");
			driver.findElement(By.xpath(usrnmeField)).sendKeys(Keys.TAB);
			log.info("The TAB key was pressed within the username field");
			driver.findElement(By.xpath(pswdField)).sendKeys(pswrd);
			log.info(pswrd+" was enetered in the password field ");
			driver.findElement(By.xpath(lgnBtn)).click();
			log.info("A click event was executed on the login button");
		}
		
		public static void createNewCase(){
			driver.findElement(By.xpath(newCaseBtn)).click();
			//Creates a new 'Select' object to work with Combo boxes 
			Select dropDown = new Select (driver.findElement(By.xpath(claimValue)));
			//use the 'Select' object to click the option in the dropdown 
			dropDown.selectByVisibleText("Up to £10,000");
			driver.findElement(By.xpath(houseNumField)).sendKeys("1");			
		}
		
		public static void addCompanyDetails(String refNum){
			String prefillBtn = "html/body/div[1]/div[2]/form/div[2]/div/div[1]/div[1]/fieldset[2]/table[1]/tbody/tr/td[3]/input";
			String contactNameField = ".//*[@id='ContactName_10']";
			String contactSurnameField = ".//*[@id='ContactSurname_12']";
			String telephoneNumField = ".//*[@id='TelephoneNumber_13']";
			String refNumField = ".//*[@id='ReferenceNumber_0']";
			String emailField = ".//*[@id='EmailAddress_14']";
			
			driver.findElement(By.xpath(prefillBtn)).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.findElement(By.xpath(contactNameField)).sendKeys("Chief");
			driver.findElement(By.xpath(contactSurnameField)).sendKeys("Petrelli");
			driver.findElement(By.xpath(telephoneNumField)).sendKeys("0330 333 5544");
			driver.findElement(By.xpath(refNumField)).sendKeys(refNum);
			driver.findElement(By.xpath(emailField)).sendKeys("gavin.petrelli@admiralgroup.co.uk");
		}
		
		public static void adddefendantDetails(String title, String name, String surname, String postcode, String vehReg, String polNum){
			Select defStatusCombo = new Select(driver.findElement(By.xpath(".//*[@id='DefendantStatus_0']")));
			Select titleCombo =new Select(driver.findElement(By.xpath(".//*[@id='TitleType_1']")));
			String nameField = ".//*[@id='Name_100']";
			String surnameField = ".//*[@id='Surname_120']";
			String postcodeField = ".//*[@id='PostCode_4000']";
			String srchBtn = ".//*[@id='PAFLookUp']";
			String vehRegField =".//*[@id='VRN_30']";
			String polNumField = ".//*[@id='PolicyNumberReference_40']";
			String selectInsurerBtn = ".//*[@id='SelectInsurer']";
			String insNameField = ".//*[@id='SearchName_2']";
			String insSrchBtn = ".//*[@id='Search']";
			String selectInsBtn = ".//*[@id='btnSelect_1']";
			
			defStatusCombo.selectByVisibleText("Personal");
			titleCombo.selectByVisibleText(title);
			driver.findElement(By.xpath(nameField)).sendKeys(name);
			driver.findElement(By.xpath(surnameField)).sendKeys(surname);
			driver.findElement(By.xpath(postcodeField)).sendKeys(postcode);
			driver.findElement(By.xpath(srchBtn)).click();
			driver.findElement(By.xpath(vehRegField)).sendKeys(vehReg);
			driver.findElement(By.xpath(polNumField)).sendKeys(polNum);
			driver.findElement(By.xpath(selectInsurerBtn)).click();
			driver.findElement(By.xpath(insNameField)).sendKeys("Admiral");
			driver.findElement(By.xpath(insSrchBtn)).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			new WebDriverWait(driver,60).until(ExpectedConditions.
					visibilityOfElementLocated(By.xpath(selectInsBtn)));
			driver.findElement(By.xpath(selectInsBtn)).click();			
		}
		
		public static void addClaimantDetails(){
			String clmntTitle = ".//*[@id='TitleType_15']";
			String clmntName = ".//*[@id='MainApplicantName']";
			String clmntSurname = ".//*[@id='MainApplicantSurname']";
			String clmntOccupation = ".//*[@id='Occupation_0']";
			String clmntReg = ".//*[@id='VRN_5']";
			String clmntDOB = "html/body/div[1]/div[2]/form/div[2]/div/div[1]/div[1]/fieldset[4]/table[2]/tbody/tr[3]/td[4]/input[2]";
			String isChildClaim = ".//*[@id='ChildClaim_2']";
			String cuePIRef = ".//*[@id='AskCUEPIReference_0']";
			String niNumb = ".//*[@id='NationalInsuranceNumber_4']";
			String noNiNumbReason = ".//*[@id='NINComment_3']";
			String houseNumb = ".//*[@id='HouseNumber_20']";
			String clmntPostcode = ".//*[@id='PostCode_40']";
			String clmntAddressSrch = ".//*[@id='PAFLookUp']";
			String streetOne = ".//*[@id='Street1_10']";
			String clmntTown = ".//*[@id='City_60']";
			
			//If no address found it will need to be added manually 
			
			//Create new instance of select and find the combo box by xpath.
			Select titleCombo =new Select(driver.findElement(By.xpath(clmntTitle)));
			titleCombo.selectByVisibleText("Mrs.");
			driver.findElement(By.xpath(clmntName)).sendKeys("Tim");
			driver.findElement(By.xpath(clmntSurname)).sendKeys("Jones");
			driver.findElement(By.xpath(clmntOccupation)).sendKeys("IT Analyst");
			driver.findElement(By.xpath(clmntReg)).sendKeys("AK64AAS");
			driver.findElement(By.xpath(clmntDOB)).sendKeys("27/11/1984");
			Select childClaimCmb =new Select(driver.findElement(By.xpath(isChildClaim)));
			childClaimCmb.selectByIndex(0);
		
		}

}

