import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Logs.GameLogger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class GameLoggerLiarTest {
    //Тест для GameLogger: Проверка логирования вызова Liar
    @Test
    public void testLogLiarCall() throws IOException {
        GameLogger logger = new GameLogger("test_log.json");

        logger.logLiarCall("TestPlayer1", "TestPlayer2");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode logFileContent = mapper.readTree(new File("test_log.json"));

        JsonNode firstEvent = logFileContent.get(0);
        assertEquals("TestPlayer1 заявляет Liar против TestPlayer2", firstEvent.get("description").asText());
    }
}
