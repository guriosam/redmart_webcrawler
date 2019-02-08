package redmart_crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Visitor {
	private WebDriver driver;
	private String baseUrl;

	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:/Users/gurio/workspace/redmart_crawler/chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "https://redmart.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void getInfo() throws Exception {
		driver.get(baseUrl);
		
		String onSale = "";
		if (isElementPresent(By.xpath("//*[@id=\"categoryNav\"]/section[1]/div[2]/a"))) {
			onSale = driver.findElement(By.xpath("//*[@id=\"categoryNav\"]/section[1]/div[2]/a")).getText();
			onSale = onSale.replaceAll("//d", "");
		}

		String sideBarNew = "";
		if (isElementPresent(By.xpath("//*[@id=\"categoryNav\"]/section[1]/div[3]/a"))) {
			sideBarNew = driver.findElement(By.xpath("//*[@id=\"categoryNav\"]/section[1]/div[3]/a")).getText();
		}

		String shopByStore = "";
		if (isElementPresent(By.xpath("//*[@id=\"categoryNav\"]/section[1]/div[4]/a"))) {
			shopByStore = driver.findElement(By.xpath("//*[@id=\"categoryNav\"]/section[1]/div[4]/a")).getText();
		}
		
		HashMap<String, List<String>> subsections = new HashMap<>();
		
		subsections.put(onSale, new ArrayList<>());
		subsections.put(sideBarNew, new ArrayList<>());
		subsections.put(shopByStore, new ArrayList<>());

		List<WebElement> classes = driver.findElements(By.className("subcategory"));

		for (WebElement clazz : classes) {

			WebElement li = clazz.findElement(By.xpath(".."));
			WebElement ul = li.findElement(By.xpath(".."));
			WebElement div = ul.findElement(By.xpath(".."));
			WebElement parent = div.findElement(By.xpath(".."));

			if (!subsections.containsKey(parent.getText())) {
				subsections.put(parent.getText(), new ArrayList<>());
			}

			subsections.get(parent.getText()).add(clazz.getAttribute("innerText"));
		}
		
		for(String sections : subsections.keySet()){
			System.out.println("Section: " + sections);
			System.out.println("Subsections: " + subsections.get(sections));
		}

		tearDown();
	}

	public void tearDown() throws Exception {
		driver.quit();
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

}
