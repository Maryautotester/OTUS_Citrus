package otus.tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import otus.pojo.Data;
import otus.pojo.Support;
import otus.pojo.User;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class FirstTestDataProviderGetUser extends TestNGCitrusSpringSupport {
    private TestContext context;

    @DataProvider(name = "usersProvider")
    public Object[][] usersProvider() {
        return new Object[][] {
                new Object[]{"1", "George", "Bluth"},
                new Object[]{"2", "Janet", "Weaver"},
                new Object[]{"3","Emma", "Wong"},
                new Object[]{"4","Eve", "Holt"},
                new Object[]{"5","Charles", "Morris"},
                new Object[]{"6","Tracey", "Ramos"},
                new Object[]{"7","Michael", "Lawson"},
                new Object[]{"8","Lindsay", "Ferguson"},
                new Object[]{"9","Tobias", "Funke"},
                new Object[]{"10","Byron", "Fields"},
                new Object[]{"11","George", "Edwards"},
                new Object[]{"12","Rachel", "Howell"},
        };
    }
    @Test(description = "Получение информации о пользователе", dataProvider = "usersProvider")
    @CitrusTest
    public void getTestActions(String id, String name, String surname) {
        this.context = citrus.getCitrusContext().createTestContext();

        // Request
        run(http()
                .client("restClientReqres")
                .send()
                .get("users/" + id)
        );

        // Response
        run(http()
                .client("restClientReqres")
                .receive()
                .response(HttpStatus.OK)
                .message()

        // Validation by some fields
                .type("application/json")
                .validate(jsonPath()
                        .expression("$.data.id", id)
                        .expression("$.data.first_name", name)
                        .expression("$.data.last_name", surname))

        // Validation POJO
//                        .body(new ObjectMappingPayloadBuilder(getResponseData(name, surname), "objectMapper"))
        );

    }

//    public User getResponseData(String name, String surname) {
//        User user = new User();
//
//        Data data = new Data();
//        data.setId(Integer.valueOf(context.getVariable("userId")));
//        data.setEmail("janet.weaver@reqres.in");
//        data.setFirstName("Janet");
//        data.setLastName("Weaver");
//        data.setAvatar("https://reqres.in/img/faces/2-image.jpg");
//        user.setData(data);
//
//        Support support = new Support();
//        support.setUrl("https://reqres.in/#support-heading");
//        support.setText("To keep ReqRes free, contributions towards server costs are appreciated!");
//
//        user.setSupport(support);
//
//        return user;
//    }
}
