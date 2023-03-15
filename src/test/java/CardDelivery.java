
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDelivery {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void firstTest() {
        System.setProperty("chromeoptions.args", "--remote-allow-origins=*");

        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String currentDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys("dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Иванов-Петров Иван");
        $("[data-test-id='phone'] input").setValue("+79231335015");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldBe(exactText("Встреча успешно забронирована на " + currentDate));
    }
}