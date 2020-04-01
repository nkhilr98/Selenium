package testCases;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logAnalyser.LogManager;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resource.Resource;
import resource.Utils;

public class zero8_Windows {
	
	private static final long Searchtime = 50;
	String baseWindowHandle,url;
	//
	public  void Execute(int caseId) throws Exception
	{
		Resource rec=Resource.getInstance();
        AuthManager auth = new AuthManager();
		
		Utils.LoadLoginPage();
		WebDriver webdriver = rec.GetWebDriver();
		auth.PerformLogin(caseId,"admin","welcome1@","STATUS_OK",false);
		
		try{
			webdriver.switchTo().defaultContent();
			WebDriverWait wait = (WebDriverWait) new WebDriverWait(webdriver,Searchtime).ignoring(Exception.class);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("TopLevelFrame")));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("topframe")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("ADMIN-anchor"))).click();
			Utils.waitForJSandJQueryToLoad();
			webdriver.switchTo().parentFrame();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("SubMenu")));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("SubMenu")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("Security"))).click();
			Utils.waitForJSandJQueryToLoad();
			webdriver.switchTo().parentFrame();
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("contents")));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("fraList")));
			auth.SelectAuthFRListDropBox("SELUSERAUTH","1");
			auth.SelectAuthFRListDropBox("USERAUTHTYPE","WindowsDomain");	
			WebElement checkbox = webdriver.findElement(By.name("NTServer"));
			if(!checkbox.isSelected())
			    checkbox.click();
			Thread.sleep(10000);
			//for(int i=0;i<9;i++)
			//{
			Map<String, String> Arrmap = new HashMap<String, String>();
			Arrmap=rec.getArgumentList(caseId);
			String Field=Arrmap.get("FIELD");
			String Domain=Arrmap.get("DOMAIN");
			String Row=Arrmap.get("NUMBER");
			String PDC=Arrmap.get("PDC");
			
			SetDomainDetails(caseId,Domain, PDC);
			GetDomainDetails(caseId,Field, Row);
			//}
		}
			catch(Exception e)
			{
			Utils.waitForJSandJQueryToLoad();
			webdriver.quit();
            throw e;
			}
			Utils.waitForJSandJQueryToLoad();
			webdriver.quit();
       	}
	public void SetDomainDetails(int caseId,String Domain,String PDC) throws Exception
	{
		LogManager lm=new LogManager();
		//String Logfile=lm.ClearLogFile("authplugin");
		AuthManager auth = new AuthManager();
		Resource rec=Resource.getInstance();
		WebDriver webdriver=rec.GetWebDriver();
		WebDriver webdriver1=rec.GetWebDriver();
		JSONObject jsonobj =rec.GetJsonObj(caseId);
		String GET =jsonobj.getString("GET");
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(webdriver,Searchtime).ignoring(Exception.class);
		baseWindowHandle = webdriver.getWindowHandle();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnWinDomainAdd"))).click();
		Thread.sleep(500);
		
		if(!Utils.waitForJSandJQueryToLoad())
		{
			System.out.println("Wait fail after clicking (Create)");
			throw new Exception("Internal Error After clicking (Create)");
		}
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		for (String handles : webdriver.getWindowHandles()) {
			if (handles.equals(baseWindowHandle))
				continue;
			webdriver.switchTo().window(handles);
			System.out.println(handles);
			System.out.println(baseWindowHandle);
		}
		Utils.waitForJSandJQueryToLoad();
		webdriver.switchTo().defaultContent();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("STRDOMAIN"))).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("STRDOMAIN"))).sendKeys(Domain);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("STRPDC"))).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("STRPDC"))).sendKeys(PDC);
		Thread.sleep(3000);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("OK"))).click();
		//webdriver.switchTo().window(baseWindowHandle);
		//webdriver.switchTo().
		//wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("TopLevelFrame")));
		//wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("topframe")));
		//wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("contents")));
		//wait.until(ExpectedConditions.
		//wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("fraList")));
		//webdriver.switchTo().frame("30b1163365fa7fa97b2d41dcee85079a");
		//wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("contents")));
		//wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("fraList")));
		webdriver.switchTo().defaultContent();
		//Thread.sleep(3000);
		//Utils.waitForJSandJQueryToLoad();
		//webdriver.switchTo().parentFrame();
		wait.until(ExpectedConditions.elementToBeClickable(By.name("Save"))).click();
		
		
	}
	public void GetDomainDetails(int caseId,String Field,String row) throws Exception
	{
		LogManager lm=new LogManager();
		//String Logfile=lm.ClearLogFile("authplugin");
		AuthManager auth = new AuthManager();
		Resource rec=Resource.getInstance();
		WebDriver webdriver=rec.GetWebDriver();
		JSONObject jsonobj =rec.GetJsonObj(caseId);
		String GET =jsonobj.getString("GET");
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(webdriver,Searchtime).ignoring(Exception.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idWinDomainAuthTable")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("WinDomainAuthTable")));
		System.out.println("Field "+ Field);
		String col="";
		if (Field.equals("Domain Name"))	
			col="1";
		else if(Field.equals("PDC"))
			col="2";
		else if(Field.equals("BDC"))
			col="3";
		String str=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='WinDomainAuthTable']/tbody/tr["+row+"]/td["+col+"]/span"))).getText();
		//String str=webdriver.findElement(By.xpath("//table[@id='WinDomainAuthTable']/tbody/tr["+row+"]/td["+col+"]/span")).getText();
		System.out.println("STR GET "+ str);
		if(!str.isEmpty()&&str.equals(GET))
			jsonobj.put("STATUS", "STATUS_OK");
		else 
			jsonobj.put("STATUS", "STATUS_NOT_OK");
		
	}
	
}
