package com.example.bookstore;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;
import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebTest {

    @Test
    void MainPage() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\mir-u\\Downloads\\chromedriver-win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        ChromeDriver driver = new ChromeDriver(options);
        driver.get("http://localhost:8080/");
        assertEquals("Главная страница", driver.getTitle());
        driver.quit();
    }

    @Test
    void LogIn() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\mir-u\\Downloads\\chromedriver-win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        ChromeDriver driver = new ChromeDriver(options);
        driver.get("http://localhost:8080/login");
        assertEquals("Вход", driver.getTitle());

        driver.findElement(By.name("nickname")).sendKeys("Cipollino");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals("Главная страница", driver.getTitle());

        driver.quit();
    }

    @Test
    void SignUp() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\mir-u\\Downloads\\chromedriver-win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        ChromeDriver driver = new ChromeDriver(options);
        driver.get("http://localhost:8080/register");
        assertEquals("Вход", driver.getTitle());

        driver.findElement(By.name("nickname")).sendKeys("Cipollino");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals("Ошибка!", driver.getTitle());
        //driver.quit();
        driver.get("http://localhost:8080/register");

        driver.findElement(By.name("nickname")).sendKeys("NEW_USER");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals("Главная страница", driver.getTitle());

        //driver.quit();
        driver.get("http://localhost:8080/login");
        assertEquals("Вход", driver.getTitle());

        driver.findElement(By.name("nickname")).sendKeys("NEW_USER");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals("Главная страница", driver.getTitle());

        driver.quit();
    }

    @Test
    void userWork() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\mir-u\\Downloads\\chromedriver-win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        ChromeDriver driver = new ChromeDriver(options);
        driver.get("http://localhost:8080/login");
        assertEquals("Вход", driver.getTitle());

        driver.findElement(By.name("nickname")).sendKeys("Cipollino");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals("Главная страница", driver.getTitle());

        driver.get("http://localhost:8080/books");
        assertEquals("Книги", driver.getTitle());

        driver.findElement(By.id("searchText")).sendKeys("Мюллер");
        driver.findElement(By.id("searchSubmitButton")).click();
        WebElement objectsTable = driver.findElement(By.id("booksTable"));

        List<WebElement> objectsInTable = objectsTable.findElements(By.tagName("tr"));
        boolean searchTarget = false;
        int searchCount = 0;
        for (WebElement objectInTable : objectsInTable) {
            List<WebElement> cols = objectInTable.findElements(By.tagName("td"));
            if (cols.size() == 0) {
                continue;
            }
            searchCount = searchCount + 1;
            WebElement classCol = cols.get(0);
            if (classCol.findElement(By.tagName("span")).getText().equals("Гордость и пробуждение")) {
                searchTarget = true;
            }
        }
        assertEquals(2, searchCount);
        assertEquals(true, searchTarget);
        driver.findElement(By.id("resetSubmitButton")).click();

        driver.findElement(By.id("searchText")).sendKeys("Пламя");
        driver.findElement(By.id("searchSubmitButton")).click();
        objectsTable = driver.findElement(By.id("booksTable"));

        objectsInTable = objectsTable.findElements(By.tagName("tr"));
        searchCount = 0;
        for (WebElement objectInTable : objectsInTable) {
            List<WebElement> cols = objectInTable.findElements(By.tagName("td"));
            if (cols.size() == 0) {
                continue;
            }
            searchCount = searchCount + 1;
            WebElement classCol = cols.get(0);
        }
        assertEquals(1, searchCount);
        driver.findElement(By.id("resetSubmitButton")).click();
        driver.get("http://localhost:8080/book?bookId=6");
        assertEquals("Книга", driver.getTitle());
        driver.findElement(By.id("orderNumber")).sendKeys("1000");
        driver.findElement(By.id("delivery1")).click();
        driver.findElement(By.id("orderBookSubmitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Ошибка!", driver.getTitle());

        driver.get("http://localhost:8080/book?bookId=6");
        assertEquals("Книга", driver.getTitle());
        driver.findElement(By.id("orderNumber")).sendKeys("1");
        driver.findElement(By.id("delivery1")).click();
        driver.findElement(By.id("orderBookSubmitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Книги", driver.getTitle());

        driver.get("http://localhost:8080/orders");
        objectsTable = driver.findElement(By.id("ordersUserTable"));
        objectsInTable = objectsTable.findElements(By.tagName("tr"));
        searchTarget = false;
        for (WebElement objectInTable : objectsInTable) {
            List<WebElement> cols = objectInTable.findElements(By.tagName("td"));
            if (cols.size() == 0) {
                continue;
            }
            WebElement classCol = cols.get(1);
            if (classCol.findElement(By.tagName("span")).getText().equals("Законы страсти")) {
                searchTarget = true;
            }
        }
        assertEquals(true, searchTarget);

        driver.quit();
    }

    @Test
    void adminWork() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\mir-u\\Downloads\\chromedriver-win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        ChromeDriver driver = new ChromeDriver(options);
        driver.get("http://localhost:8080/login");
        assertEquals("Вход", driver.getTitle());

        driver.findElement(By.name("nickname")).sendKeys("gianni");
        driver.findElement(By.name("password")).sendKeys("r0darI");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals("Главная страница", driver.getTitle());

        driver.get("http://localhost:8080/books");
        assertEquals("Книги", driver.getTitle());

        driver.findElement(By.id("searchText")).sendKeys("Мюллер");
        driver.findElement(By.id("searchSubmitButton")).click();
        WebElement objectsTable = driver.findElement(By.id("booksTable"));

        List<WebElement> objectsInTable = objectsTable.findElements(By.tagName("tr"));
        boolean searchTarget = false;
        int searchCount = 0;
        for (WebElement objectInTable : objectsInTable) {
            List<WebElement> cols = objectInTable.findElements(By.tagName("td"));
            if (cols.size() == 0) {
                continue;
            }
            searchCount = searchCount + 1;
            WebElement classCol = cols.get(0);
            if (classCol.findElement(By.tagName("span")).getText().equals("Гордость и пробуждение")) {
                searchTarget = true;
                WebElement formCol = cols.get(4);
                formCol.findElement(By.name("numberToAdd")).sendKeys("1");
                formCol.findElement(By.id("addBookButton")).click();
                break;
            }
        }
        assertEquals(true, searchTarget);

        driver.findElement(By.id("resetSubmitButton")).click();

        driver.findElement(By.id("searchText")).sendKeys("Гордость и пробуждение");
        driver.findElement(By.id("searchSubmitButton")).click();
        objectsTable = driver.findElement(By.id("booksTable"));

        objectsInTable = objectsTable.findElements(By.tagName("tr"));
        searchCount = 0;
        for (WebElement objectInTable : objectsInTable) {
            List<WebElement> cols = objectInTable.findElements(By.tagName("td"));
            if (cols.size() == 0) {
                continue;
            }
            searchCount = searchCount + 1;
            WebElement classCol = cols.get(3);
            assert(classCol.findElement(By.tagName("p")).getText().equals("14"));
        }
        assertEquals(1, searchCount);

        driver.get("http://localhost:8080/books");
        assertEquals("Книги", driver.getTitle());

        driver.findElement(By.id("newName")).sendKeys("Новый хит");
        driver.findElement(By.id("newAuthor")).sendKeys("Открытие года");
        driver.findElement(By.id("newPrice")).sendKeys("600");
        driver.findElement(By.id("newNumber")).sendKeys("10");
        driver.findElement(By.id("newCoverage1")).click();
        driver.findElement(By.id("newGenre")).sendKeys("Триллер");
        driver.findElement(By.id("newPages")).sendKeys("1001");
        driver.findElement(By.id("newAnno")).sendKeys("Очень интересно!");
        driver.findElement(By.id("addBookSubmitButton")).click();

        driver.findElement(By.id("searchText")).sendKeys("Новый хит");
        driver.findElement(By.id("searchSubmitButton")).click();
        objectsTable = driver.findElement(By.id("booksTable"));

        objectsInTable = objectsTable.findElements(By.tagName("tr"));
        searchCount = 0;
        for (WebElement objectInTable : objectsInTable) {
            List<WebElement> cols = objectInTable.findElements(By.tagName("td"));
            if (cols.size() == 0) {
                continue;
            }
            searchCount = searchCount + 1;
            WebElement classCol = cols.get(3);
            assertEquals("10", classCol.findElement(By.tagName("p")).getText());
        }
        assertEquals(1, searchCount);

        driver.get("http://localhost:8080/orders");
        objectsTable = driver.findElement(By.id("ordersAdminTable"));
        objectsInTable = objectsTable.findElements(By.tagName("tr"));
        searchTarget = false;
        for (WebElement objectInTable : objectsInTable) {
            List<WebElement> cols = objectInTable.findElements(By.tagName("td"));
            if (cols.size() == 0) {
                continue;
            }
            WebElement classCol = cols.get(0);
            if (classCol.findElement(By.id("certainOrderId")).getText().equals("10")) {
                searchTarget = true;
                WebElement col = cols.get(7);
                col.findElement(By.id("status_delivered")).click();
                col.findElement(By.id("changeStatusSubmitButton")).click();
                break;
            }
        }
        assertEquals(true, searchTarget);

        driver.quit();
    }
}