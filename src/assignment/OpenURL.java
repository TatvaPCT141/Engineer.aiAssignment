package assignment;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class OpenURL {
	
	static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	static DesiredCapabilities capabilities = null;
	
	public static void OpenURL(String url)
	{
		System.setProperty("webdriver.chrome.driver", "D:\\Automation_Projects\\drivers\\chromedriver.exe");
		capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		try 
		{
			driver.set(new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities));
		} catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		driver.get().get(url);
	}

	public static void SearchExample(String url, String searchValue)
	{
		OpenURL(url);
		List<WebElement>  tableEle= driver.get().findElements(By.xpath("//*[@id=\"content\"]/ul"));
		for (WebElement ulEle : tableEle)
		{
			List<WebElement> li =null;
			li = ulEle.findElements(By.tagName("li"));
			for (WebElement liElement : li)
			{
				if(liElement.getText().equalsIgnoreCase(searchValue))
				{
					liElement.click();
				}
				else
				{
					System.out.println("Link's name not found");
					driver.get().quit();
				}
			}
		}
		
	}
	public static void main(String[] args) 
	{
		try 
		{
			//Open URL
			OpenURL(args[0]);
			
			//Search from example
			SearchExample(args[0],args[1]);
		} 
		catch (Exception e) 
		{
			System.out.println("Error Found: "+e.getMessage());
			
		}

	}

}
