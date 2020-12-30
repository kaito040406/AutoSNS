package mainPG;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import common.osCheck;

public class instaAuto {
//	propatityファイルのパス
	private final String propatityPath = "src/resourse/common.properties";
//	アクセス情報
	private String logId;
	private String logPass;
	private String searchWord;
	private int retryNo;
	private WebDriver driver;

	public instaAuto(String logId, String logPass, String searchWord, int retryNo) {
		setPram(logId, logPass, searchWord, retryNo);
	}

	private void setPram(String logId, String logPass, String searchWord, int retryNo) {
		this.logId = logId;
		this.logPass = logPass;
		this.searchWord = searchWord;
		this.retryNo = retryNo;
	}
	private String getPropatityPath() {
		return this.propatityPath;
	}
	private String getLogId() {
		return this.logId;
	}
	private String getLogPass() {
		return this.logPass;
	}
	private String getSearchWord() {
		return this.searchWord;
	}
	private int getRetryNo() {
		return this.retryNo;
	}

	public boolean autoIine() throws IOException, InterruptedException {

		try {
		String url = "";
		String driverPath = "";
//		Properties properties = new Properties();
//		try {
//			System.out.println(getPropatityPath());
//			FileInputStream prop = new FileInputStream(getPropatityPath());
//			properties.load(prop);
//			url = properties.getProperty("instagram");
//			driverPath = properties.getProperty("driverPath");
//			System.out.println(driverPath);
//		} catch (FileNotFoundException e) {
//			return false;
//		}
		osCheck thisOs = new osCheck();
		url = "https://www.instagram.com/";
		if(thisOs.getOsName().equals("windows")) {
			driverPath = "./exe/win/chromedriver";
		}else if(thisOs.getOsName().equals("mac")) {
			driverPath = "./exe/mac/chromedriver";
		}else {
			return false;
		}

		System.setProperty("webdriver.chrome.driver", driverPath);
		this.driver = new ChromeDriver();
//ログイン画面
		  this.driver.get(url);
	      Thread.sleep(5000);
//	      要素情報取得
//	      ID入力のための要素取得
	      WebElement inputId = this.driver.findElement(By.name("username"));
//	      パスワード入力のための要素取得
	      WebElement inputPass = this.driver.findElement(By.name("password"));
//	      ID入力
	      inputId.sendKeys(logId);
	      Thread.sleep(2000);
//	      パスワード入力
	      inputPass.sendKeys(logPass);
	      Thread.sleep(2000);
//	      フォーム送信
	      inputPass.submit();

//	      お知らせ等が出てくる場合があるので数回リロード
	      Thread.sleep(5000);
	      this.driver.get(url);
	      Thread.sleep(5000);
	      this.driver.get(url);
	      Thread.sleep(2000);

//	      お知らせ削除用ボタン取得
	      WebElement delinf = this.driver.findElement(By.className("HoLwm"));
	      Thread.sleep(2000);
	      delinf.click();
	      Thread.sleep(2000);

//一覧画面
//	      ハッシュタグ検索要素取得
	      WebElement searchBox = this.driver.findElement(By.className("XTCLo"));
	      Thread.sleep(2000);
//	      検索ワード入力
	      searchBox.sendKeys(searchWord);
	      Thread.sleep(2000);

//	      検索ボックスクリック要素取得
	      List<WebElement> searchBox2 =  this.driver.findElements(By.cssSelector(".z556c"));
	      Thread.sleep(2000);
//	      検索ボックスクリック
	      try {
	    	  getPurposeElement(searchBox2, 0).click();
	      }catch(NullPointerException e){
	    	  return false;
	      }
	      Thread.sleep(10000);

//検索結果一覧
//	      詳細ページクリック
	      List<WebElement> detailBoxs =  this.driver.findElements(By.cssSelector(".v1Nh3"));
	      Thread.sleep(2000);
	      System.out.println(detailBoxs);
	      try {
	    	  getPurposeElement(detailBoxs, 1).click();
	      }catch(NullPointerException e){
	    	  return false;
	      }
	      Thread.sleep(2000);

	      int i = 0;
	      Random random = new Random();
	      while(i < retryNo) {
	    	 Thread.sleep(2000);
	    	 WebElement nextPage = this.driver.findElement(By.className("_65Bje"));
	    	 int checkpram = random.nextInt(100);
	    	 if(checkpram < 70) {
	    		 if(checkiine(this.driver)) {
	    			 List<WebElement> Iinebutons = this.driver.findElements(By.cssSelector(".QBdPU"));
	    			 try {
	    				 randomWait(6,12);
	    				 getPurposeElement(Iinebutons,1).click();
	    			 }catch(NullPointerException e){
	    				 return false;
	    		     }
	    			 i++;
	    			 randomWait(12,24);
	    		 }else {
	    			 Thread.sleep(2000);
	    		 }
	    	 }else {
	    		Thread.sleep(2000);
	    	 }
	    	 nextPage.click();
	      }
		}catch(Exception e){
			return false;
		}
		return true;
	}

	private WebElement getPurposeElement(List<WebElement> Elements, int ElNo) {
		if(Elements != null) {
			int i = 0;
			for(WebElement Element: Elements) {
				if(i == ElNo) {
					return Element;
				}else {
					i++;
				}
			}
		}
		return null;
	}


	private boolean checkiine(WebDriver driver) {
		String html = driver.getPageSource();
		Document doc = Jsoup.parseBodyFragment(html);
		Elements datas = doc.select("._8-yf5");
		for(Element data: datas) {
			String ariaLabelText = "";
			try{
				ariaLabelText = data.attr("aria-label");
			}catch(Exception e) {
				continue;
			}
			if(ariaLabelText.contains("「いいね！」を取り消す")) {
				return false;
			}else {
				continue;
			}
		}
		return true;
	}

	private void randomWait(int rowNo, int highNo) throws InterruptedException {
		Random random = new Random();
		int difference = highNo - rowNo;
		int num =10;
		if(difference > 0) {
			num = random.nextInt(difference) + rowNo;
		}
		Thread.sleep(num * 1000);
	}

	public void quitIine() {
		this.driver.quit();
	}
}
