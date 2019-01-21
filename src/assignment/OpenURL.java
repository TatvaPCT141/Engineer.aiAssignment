package assignment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
		List<WebElement>  tableEle= driver.get().findElements(By.xpath("//*[@id=\"content\"]/ul"));
		boolean searchFound= false;
		for (WebElement ulEle : tableEle)
		{
			List<WebElement> li =null;
			li = ulEle.findElements(By.tagName("li"));
			for (WebElement liElement : li)
			{
				if(liElement.getText().equalsIgnoreCase(searchValue))
				{
					List<WebElement>  a=liElement.findElements(By.tagName("a"));
					//liElement.click();
					for(WebElement aLink: a)
					{
						aLink.click();
						searchFound= true;
						break;
					}
				}
				else
				{
					System.out.println("Link's name not found: "+ searchValue+ ", Value found is:"+ liElement.getText());
				}
			}
		}
		if(searchFound==false)
		{
			System.out.println("Link's name not found with the Name: "+ searchValue);
			driver.get().quit();
		}
		else
		{
			System.out.println("Search Test Case Pass Successfully and Found the name with: "+ searchValue);
		}
		
	}
	public static void main(String[] args) 
	{
		try 
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter Application URL: ");
			String url = reader.readLine();
			//Open URL https://the-internet.herokuapp.com
			OpenURL(url);
			System.out.println("Enter Search Value");
			String searchvalue= reader.readLine();
			//Search from example: Frames
			SearchExample(url,searchvalue);
		} 
		catch (Exception e) 
		{
			System.out.println("Error Found: "+e.getMessage());
		}

	}

}
