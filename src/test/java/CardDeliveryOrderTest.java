import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.time.LocalDate.*;

public class CardDeliveryOrderTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        Configuration.holdBrowserOpen = true;

    }

    @Test
    public void shouldTestSuccessNotification() {
        String newDate = now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").val("Москва");
        $("[data-test-id='date'] input").sendKeys(newDate);
        $("[data-test-id='name'] input").val("Крокодилов Геннадий");
        $("[data-test-id='phone'] input").val("+77777777777");
        $("[data-test-id='agreement']").click();
        $(".button").shouldHave(text("Забронировать")).click();
        $("[data-test-id='notification']").shouldHave(text("Встреча успешно забронирована на " + newDate), Duration.ofSeconds(15));

    }

    @Test
    public void shouldTestValidCity() {
        String newDate = now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").sendKeys(newDate);
        $("[data-test-id='name'] input").val("Крокодилов Геннадий");
        $("[data-test-id='phone'] input").val("+77777777777");
        $("[data-test-id='agreement']").click();
        $(".button").shouldHave(text("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldTestValidName() {
        String newDate = now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").val("Москва");
        $("[data-test-id='date'] input").sendKeys(newDate);
        $("[data-test-id='name'] input").val("Crocodile Dundee");
        $("[data-test-id='phone'] input").val("+77777777777");
        $("[data-test-id='agreement']").click();
        $(".button").shouldHave(text("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldTestValidDate() {
        $("[data-test-id='city'] input").val("Москва");
        $("[data-test-id='date'] input").sendKeys("40.13.3456");
        $("[data-test-id='name'] input").val("Крокодилов Геннадий");
        $("[data-test-id='phone'] input").val("+77777777777");
        $("[data-test-id='agreement']").click();
        $(".button").shouldHave(text("Забронировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(text("Неверно введена дата"));
    }

    @Test
    public void shouldTestAgreement() {
        String newDate = now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").val("Москва");
        $("[data-test-id='date'] input").sendKeys(newDate);
        $("[data-test-id='name'] input").val("Крокодилов Геннадий");
        $("[data-test-id='phone'] input").val("+77777777777");
        $(".button").shouldHave(text("Забронировать")).click();
        $("[data-test-id='agreement'] .checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }
}

