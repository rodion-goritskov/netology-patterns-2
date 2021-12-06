package ru.netology.patterns.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserDataGenerator {
    private static final Faker FAKER = new Faker();
    private static final RequestSpecification REQUEST_SPEC = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private UserDataGenerator() {}

    private static void sendUser(UserData user) {
        given()
                .spec(REQUEST_SPEC)
                .body(user, ObjectMapperType.GSON)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    private static UserData.UserDataBuilder generateUser() {
        return UserData
                .builder()
                .status(UserStatus.ACTIVE)
                .login(FAKER.name().username())
                .password(FAKER.regexify("[A-Za-z!&%]{15}"));
    }

    public static UserData generateActiveUser() {
        UserData createdUserData =
                generateUser().status(UserStatus.ACTIVE).build();
        sendUser(createdUserData);

        return createdUserData;
    }

    public static UserData generateBlockedUser() {
        UserData createdUserData =
                generateUser().status(UserStatus.BLOCKED).build();
        sendUser(createdUserData);

        return createdUserData;
    }
}
