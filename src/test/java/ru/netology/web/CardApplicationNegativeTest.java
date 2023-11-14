package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CardApplicationNegativeTest {
    private WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    public void invalidInputNameTest() {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Max");
        driver.findElement(By.cssSelector(".button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText().trim();

        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                actualText);
    }

    @Test
    public void emptyInputNameTest() {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector(".button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText().trim();

        Assertions.assertEquals("Поле обязательно для заполнения", actualText);
    }

    @Test
    public void invalidInputPhoneTest() {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Александр");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("a");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText().trim();

        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                actualText);
    }

    @Test
    public void emptyInputPhoneTest() {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Александр");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText().trim();

        Assertions.assertEquals("Поле обязательно для заполнения", actualText);
    }

    @Test
    public void notShouldSendApplicationWithoutCheckboxTest() {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Александр");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector(".button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__text")).getText().trim();

        Assertions.assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй",
                actualText);
    }
}
