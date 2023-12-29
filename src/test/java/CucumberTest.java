
import com.tests.lab2.Lab2Application;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@RunWith(Cucumber.class)
@CucumberOptions
(
        features = "src\\test\\java\\features",
        glue = "com\\tests\\lab3",
        tags = "@all",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
@SpringBootTest
@ContextConfiguration(classes = Lab2Application.class)
public class CucumberTest {
}
