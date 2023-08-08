package otus.behaviors;

import com.consol.citrus.TestActionRunner;
import com.consol.citrus.TestBehavior;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestContext;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class CreateUserBehavior implements TestBehavior {
    private TestContext context;
    public String name;
    public String job;

    public CreateUserBehavior(TestContext context, String name, String job) {
        this.context = context;
        this.name = name;
        this.job = job;
    }
    @Override
    public void apply(TestActionRunner testActionRunner) {

        testActionRunner.run(http()
                    .client("restClientReqres")
                    .send()
                    .post("users")
                    .message()
                    .type("application/json")
                    .body("{\n" +
                            "    \"name\": \"" + name + "\",\n" +
                            "    \"job\": \"" + job + "\"\n" +
                            "}")
            );

        testActionRunner.run(http()
                            .client("restClientReqres")
                            .receive()
                            .response(HttpStatus.CREATED)
                            .message()
                            .type("application/json")
                            // Validation by node & value
                            .validate(jsonPath()
                                    .expression("$.name", name)
                                    .expression("$.job", job))
            );
        }

}
