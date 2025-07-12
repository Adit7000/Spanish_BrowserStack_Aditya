package SpanishBrowserStackTask;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

public class SpanishSimpleBrowserStackCrossPlatform {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private WebDriverWait wait;

    @Before
    public void setUpChromeDriver() throws IOException {

        MutableCapabilities capabilities = new MutableCapabilities();
        HashMap<String, Object> bstackOptions = new HashMap<String, Object>();
        capabilities.setCapability("browserName", "Chrome");
        bstackOptions.put("os", "Windows");
        bstackOptions.put("osVersion", "11");
        bstackOptions.put("browserVersion", "latest");
        bstackOptions.put("userName", "adityaraghuwansh_VgX2xd");
        bstackOptions.put("accessKey", "qPpDDTyazfWABX8Yn7u5");
        bstackOptions.put("consoleLogs", "info");
        bstackOptions.put("projectName", "Spanish_BrowserStack");
        bstackOptions.put("seleniumVersion", "4.33.0");
        bstackOptions.put("buildName", "SpanishSimple");
        HashMap<String, Object> chromeOptions = new HashMap<String, Object>();
        chromeOptions.put("driver", "138.0.7204.49");
        bstackOptions.put("chrome", chromeOptions);
        capabilities.setCapability("bstack:options", bstackOptions);



        driver = new RemoteWebDriver(
                new URL("https://hub-cloud.browserstack.com/wd/hub"), capabilities
        );

        wait = new WebDriverWait(driver, Duration.ofSeconds(500));
        js = (JavascriptExecutor) driver;
        vars = new HashMap<>();
    }

    @After
    public void quitBrowser() {
        driver.quit();
    }

    @Test
    public void runTestSpanishSimple() throws InterruptedException, URISyntaxException, IOException {


        //1st Step:- Visit the News Website
        System.out.println("1st Step:- Visit the News Website - El pais");

        driver.get("https://elpais.com/");
        driver.manage().window().maximize();

        //Wait here for 3 seconds until the page loads fully
        Thread.sleep(3000);

        //Click on Accept Button
        click(By.xpath("//*[@id=\"didomi-notice-agree-button\"]"));

        //2nd Step:- Scrape Articles from the Opinion Section
        System.out.println("2nd Step:- Scrape Articles from the Opinion Section");


        //Click on Opinion
        click(By.xpath("//a[@cmp-ltrk='portada_menu'][normalize-space()='Opinión']"));

        //Wait here for 25 seconds until the page loads fully
        Thread.sleep(25000);

        //Now we have to fetch first 5 articles
        //Firstly Scroll down
        scrollByPressing11Keys();
        //Wait to scroll for 6 seconds
        Thread.sleep(6000);

        //1st Article's Title and Content
        System.out.println("1st Article");
        fetchTitle(By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[1]/article[1]/header/h2"));

        fetchContent(By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[1]/article[1]/p"));

        //2nd Article's Title and Content
        System.out.println("2nd Article");
        fetchTitle(By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[1]/article[2]/header/h2"));

        fetchContent(By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[1]/article[2]/p"));

        //3rd Article's Title and Content
        System.out.println("3rd Article");
        fetchTitle(By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[1]/article[3]/header/h2"));

        fetchContent(By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[1]/article[3]/p"));

        //4th Article's Title and Content
        System.out.println("4th Article");
        fetchTitle(By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[2]/article/header/h2"));

        fetchContent(By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[2]/article/p"));

        //Downloading Cover Image of 4th Article in our pc
        downloadImage((By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[2]/article/figure/a/img")), "Spanish_4th_Image.jpg");
        Thread.sleep(2000);


        //5th Article's Title and Content
        System.out.println("5th Article");
        fetchTitle(By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[3]/article/header/h2"));

        fetchContent(By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[3]/article/p"));

        //Downloading Cover Image of 5th Article in our pc
        downloadImage((By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[3]/article/figure/a/img")), "Spanish_5th_Image.jpg");
        Thread.sleep(2000);

        //3rd Step:- Translate Article Headers from Spanish to English
        System.out.println("3rd Step:- Translate Article Headers from Spanish to English and Print the Translated headers");
        //1st Article's Title and Content
        fetchTitleAndTranslate(By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[1]/article[1]/header/h2"));
        Thread.sleep(2000);
        //2nd Article's Title and Content
        fetchTitleAndTranslate(By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[1]/article[2]/header/h2"));

        //3rd Article's Title and Content
        fetchTitleAndTranslate(By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[1]/article[3]/header/h2"));

        //4th Article's Title and Content
        fetchTitleAndTranslate(By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[2]/article/header/h2"));

        //5th Article's Title and Content
        fetchTitleAndTranslate(By.xpath("//*[@id=\"main-content\"]/div[1]/section[1]/div[3]/article/header/h2"));


        //4th Step:- Analyze Translated Headers
        System.out.println("4th Step:- Analyzing Translated Headers and printing the words coming more thand twice with count ");

        //Creating list and analysing
        List<String> translatedTitleHeaders = new ArrayList<>();
        analyzeMoreThanTwiceWords(translatedTitleHeaders);
    }

    private void click(By selector) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(selector)).click();
        } catch (Exception e) {
            System.out.println("Error on clicking button: " + e.getMessage());
        }
    }

    private void fetchTitle(By selector) {
        try {
            WebElement title = wait.until(ExpectedConditions.elementToBeClickable(selector));
            String titleText = title.getText();
            System.out.println("Title: " + titleText);
        } catch (Exception e) {
            System.out.println("Error on fetching this Title: " + e.getMessage());
        }
    }


    private String fetchTitleAndTranslate(By selector) {
        try {
            WebElement titleAndTranslate = wait.until(ExpectedConditions.elementToBeClickable(selector));
            String titleAndTranslateText = titleAndTranslate.getText();
            System.out.println("Title in Spanish: " + titleAndTranslateText);

            String apiUrl = "https://free-google-translator.p.rapidapi.com/external-api/free-google-translator";
            String encodedQuery = URLEncoder.encode(titleAndTranslateText, StandardCharsets.UTF_8);
            URI uri = URI.create(apiUrl + "?from=es&to=en&query=" + encodedQuery);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("x-rapidapi-key", "3bf3f1c06fmsh7b9ecf28aab1db5p144b2cjsn89d99ca0bb4a")
                    .header("x-rapidapi-host", "free-google-translator.p.rapidapi.com")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"translate\":\"rapidapi\"}"))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                String translatedText = jsonResponse.getString("translation");
                System.out.println("Translated Title in English: " + translatedText);
                return translatedText;
            } else {
                System.out.println("Translation API failed. Response code: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Error fetching or translating title: " + e.getMessage());
        }

        return "";
    }

    private void analyzeMoreThanTwiceWords(List<String> translatedTitles) {
        Map<String, Integer> wordCount = new HashMap<>();

        for (String title : translatedTitles) {
            String[] words = title.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
            for (String word : words) {
                if (!word.isBlank()) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }
        }

        System.out.println("Searching Words repeated more than twice:");
        boolean found = false;
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > 2) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
                found = true;
            }
        }

        if (!found) {
            System.out.println("Hence, No word was repeated more than twice found.");
        }
    }


    private void fetchContent(By selector) {
        try {
            WebElement content = wait.until(ExpectedConditions.elementToBeClickable(selector));
            String contentText = content.getText();
            System.out.println("Content: " + contentText);
        } catch (Exception e) {
            System.out.println("Error on fetching this Content: " + e.getMessage());
        }
    }

    private void scrollByPressing11Keys() throws InterruptedException {
        Actions actions = new Actions(driver);
        for (int i = 0; i < 11; i++) {
            actions.sendKeys(Keys.ARROW_DOWN).perform();
            Thread.sleep(500);
        }

    }

    private void downloadImage(By selector, String fileName) throws IOException {
        WebElement imageLocater = wait.until(ExpectedConditions.elementToBeClickable(selector));
        String imageUrl = imageLocater.getDomAttribute("src");
        System.out.println(imageUrl);

        URL url = new URL(imageUrl);
        URLConnection connection = url.openConnection();

        try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream());

             FileOutputStream out = new FileOutputStream(fileName)) {
            byte[] buffer = new byte[1024];
            int count;
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }

            System.out.println("Image Downloaded, Check inside your Project folder with file name as:" + fileName);

        } catch (IOException e) {
            System.out.println("Image downloading Failed: " + e.getMessage());
        }
    }
}