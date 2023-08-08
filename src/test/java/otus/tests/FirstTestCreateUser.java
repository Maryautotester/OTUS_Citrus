package otus.tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;
import otus.pojo.CreateUserResponse;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.dsl.JsonSupport.json;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class FirstTestCreateUser extends TestNGCitrusSpringSupport {
    private TestContext context;

    String name = "Nick";
    String job = "Teacher";

    @Test(description = "Создание пользователе")
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.getCitrusContext().createTestContext();

        $(http()
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

        $(http()
                .client("restClientReqres")
                .receive()
                .response(HttpStatus.CREATED)
                .message()
                .type("application/json")



                // Validation Pojo
//                 .body(new ObjectMappingPayloadBuilder(getResponseData(name, job), "objectMapper"))

                // Ignore node createdAt from json
//                .validate(json()
//                        .ignore("$.createdAt"))

                // Validation by node & value
//                .validate(jsonPath()
//                        .expression("$.name", name)
//                        .expression("$.job", job))




                // Validation from Body, save it to variable and then validate $(echo("currentId = ${currentId} and createdAt = ${createdAt}"));
                .extract(fromBody()
                        .expression("$.id", "currentId")
                        .expression("$.createdAt", "createdAt"))

        );
        $(echo("currentId = ${currentId} and createdAt = ${createdAt}"));
    }

    // Validation using Pojo
//    public CreateUserResponse getResponseData(String name, String job) {
//
//        CreateUserResponse createUserResponse = new CreateUserResponse();
//        createUserResponse.setName(name);
//        createUserResponse.setJob(job);
//        createUserResponse.setId("@isNumber()@");
//        createUserResponse.setCreatedAt("unknown");
////       createUserResponse.setCreatedAt("@ignore()@");
//
//        return createUserResponse;
//    }



}
