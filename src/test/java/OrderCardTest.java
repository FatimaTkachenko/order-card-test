import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class OrderCardTest {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void shouldSendOrderSuccessfully() {
        driver.get("http://localhost:9999");

        WebElement nameField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='name']"))
        );
        nameField.sendKeys("Иванов Иван");

        WebElement phoneField = driver.findElement(By.cssSelector("input[name='phone']"));
        phoneField.sendKeys("+79998887766");

        WebElement agreementCheckbox = driver.findElement(By.cssSelector(".checkbox__box"));
        agreementCheckbox.click();

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='button']"));
        submitButton.click();

        WebElement successMessage = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id='order-success']"))
        );

        assertTrue(successMessage.isDisplayed());
        assertTrue(successMessage.getText().contains("Ваша заявка успешно отправлена"));
    }

    @Test
    public void shouldShowErrorWhenNameEmpty() {
        driver.get("http://localhost:9999");

        WebElement phoneField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='phone']"))
        );
        phoneField.sendKeys("+79998887766");

        WebElement agreementCheckbox = driver.findElement(By.cssSelector(".checkbox__box"));
        agreementCheckbox.click();

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='button']"));
        submitButton.click();

        WebElement nameError = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".input__sub"))
        );

        assertTrue(nameError.isDisplayed());
        assertNotEquals("", nameError.getText());
    }

    @Test
    public void shouldShowErrorWhenPhoneEmpty() {
        driver.get("http://localhost:9999");

        WebElement nameField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='name']"))
        );
        nameField.sendKeys("Иванов Иван");

        WebElement agreementCheckbox = driver.findElement(By.cssSelector(".checkbox__box"));
        agreementCheckbox.click();

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='button']"));
        submitButton.click();

        WebElement phoneError = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".input__sub"))
        );

        assertTrue(phoneError.isDisplayed());
        assertNotEquals("", phoneError.getText());
    }
}