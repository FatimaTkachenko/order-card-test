import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class OrderCardTest {
    
    @Test
    public void testOrderCardPositive() {
        // Инициализация драйвера (замените путь на актуальный)
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            // Открываем страницу (замените URL на актуальный)
            driver.get("https://example.com/order-card");
            
            // Заполняем поле "Имя"
            WebElement nameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("name"))
            );
            nameField.sendKeys("Иван Иванов");
            
            // Заполняем поле "Телефон"
            WebElement phoneField = driver.findElement(By.id("phone"));
            phoneField.sendKeys("+7 999 888-77-66");
            
            // Нажимаем кнопку "Отправить"
            WebElement submitButton = driver.findElement(By.id("submit"));
            submitButton.click();
            
            // Проверяем, что появилось сообщение об успехе
            WebElement successMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("success-message"))
            );
            
            assertTrue(successMessage.isDisplayed(), "Сообщение об успехе не отображается");
            assertEquals("Заказ оформлен!", successMessage.getText(), "Текст сообщения не совпадает");
            
        } finally {
            // Закрываем браузер
            driver.quit();
        }
    }
    
    @Test
    public void testOrderCardWithEmptyFields() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            driver.get("https://example.com/order-card");
            
            // Нажимаем кнопку "Отправить" без заполнения полей
            WebElement submitButton = driver.findElement(By.id("submit"));
            submitButton.click();
            
            // Проверяем, что появилось сообщение об ошибке
            WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("error"))
            );
            
            assertTrue(errorMessage.isDisplayed(), "Сообщение об ошибке не отображается");
            
        } finally {
            driver.quit();
        }
    }
}