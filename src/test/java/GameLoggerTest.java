import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import Logs.GameLogger;

public class GameLoggerTest {
    @Test
    public void testLogMaputaRound() throws IOException {
        String logFilePath = "test_log.json";
        GameLogger logger = new GameLogger(logFilePath);

        logger.logMaputaRound("TestPlayer");

        String logContent = Files.readString(Path.of(logFilePath));
        assertTrue(logContent.contains("TestPlayer объявляет Maputa!"));

        Files.deleteIfExists(Path.of(logFilePath));
    }
}
