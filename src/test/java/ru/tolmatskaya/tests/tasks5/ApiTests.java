package ru.tolmatskaya.tests.tasks5;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import org.junit.Test;
import ru.tolmatskaya.task5.data.*;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

public class ApiTests{

    @Test
    @DisplayName("Получить список пользователей со 2 страницы")
    public void getListUsers(){
        List<UserData> users = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UserListSchema.json"))
                .body("page", equalTo(2))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
                .body("data.id", not(hasItem(nullValue())))
                .body("data.first_name", hasItem("Tobias"))
                .body("data.last_name", hasItem("Funke"))
                //.log().all()
                .extract().jsonPath().getList("data", UserData.class);
        assertThat(users).extracting(UserData::getId).isNotNull();
        assertThat(users).extracting(UserData::getFirst_name).contains("Tobias");
        assertThat(users).extracting(UserData::getLast_name).contains("Funke");
    }

    @Test
    @DisplayName("Получить пользователя с id=2")
    public void getSingleUser(){
        UserData user = given().
                when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UserSingleSchema.json"))
                //.log().all()
                .extract().jsonPath().getObject("data", UserData.class);
        assertThat(user.getId()).isEqualTo(2);
        assertThat(user.getEmail()).isEqualTo("janet.weaver@reqres.in");
        assertThat(user.getFirst_name()).isEqualTo("Janet");
        assertThat(user.getLast_name()).isEqualTo("Weaver");
        assertThat(user.getAvatar()).isEqualTo("https://reqres.in/img/faces/2-image.jpg");
    }

    @Test
    @DisplayName("Получить пользователя с id=23")
    public void getSingleUserNotFound(){
        given().
                when()
                .get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404)
                .body(equalTo("{}"));
    }

    @Test
    @DisplayName("Получить список ресурсов")
    public void getListResource(){
        List<ResourseData> resourses = given().
                when()
                .get("https://reqres.in/api/unknown")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ResourseListSchema.json"))
                .body("page", equalTo(1))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
                //.log().all()
                .extract().jsonPath().getList("data", ResourseData.class);
        assertThat(resourses).extracting(ResourseData::getId).isNotNull();
        assertThat(resourses).extracting(ResourseData::getName).contains("fuchsia rose");
        assertThat(resourses).extracting(ResourseData::getYear).contains(2001);
        assertThat(resourses).extracting(ResourseData::getColor).contains("#C74375");
    }

    @Test
    @DisplayName("Получить ресурс с id = 2")
    public void getSingleResource(){
        ResourseData resourse = given().
                when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ResourseSingleSchema.json"))
                //.log().all()
                .extract().jsonPath().getObject("data", ResourseData.class);
        assertThat(resourse).extracting(ResourseData::getId).isEqualTo(2);
        assertThat(resourse).extracting(ResourseData::getName).isEqualTo("fuchsia rose");
        assertThat(resourse).extracting(ResourseData::getYear).isEqualTo(2001);
        assertThat(resourse).extracting(ResourseData::getColor).isEqualTo("#C74375");
        assertThat(resourse).extracting(ResourseData::getPantone_value).isEqualTo("17-2031");

    }

    @Test
    @DisplayName("Получить ресурс с id = 23")
    public void getSingleResourceNotFound(){
        given().
                when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .statusCode(404)
                .body(equalTo("{}"));
    }

    @Test
    @DisplayName("Создать пользователя")
    public void postCreate(){
        UserRequest newUser = UserRequest.builder()
                .name("morpheus")
                .job("leader")
                .build();

        UserResponse createdUser = given()
                .contentType(ContentType.JSON)
                .body(newUser)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CreateUserResponseSchema.json"))
                //.log().all()
                .extract().as(UserResponse.class);

        assertThat(createdUser.getName()).isEqualTo("morpheus");
        assertThat(createdUser.getJob()).isEqualTo("leader");
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Обновить пользователя PUT")
    public void putUpdate(){
        UserRequest updateUser = UserRequest.builder()
                .name("morpheus")
                .job("zion resident")
                .build();

        UserResponse updatedUser = given()
                .contentType(ContentType.JSON)
                .body(updateUser)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UpdateUserResponseSchema.json"))
                //.log().all()
                .extract().as(UserResponse.class);

        assertThat(updatedUser.getName()).isEqualTo("morpheus");
        assertThat(updatedUser.getJob()).isEqualTo("zion resident");
        assertThat(updatedUser.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Обноваить пользователя PATCH")
    public void patchUpdate(){
        UserRequest updateUser = UserRequest.builder()
                .name("morpheus")
                .job("zion resident")
                .build();

        UserResponse updatedUser = given()
                .contentType(ContentType.JSON)
                .body(updateUser)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UpdateUserResponseSchema.json"))
                //.log().all()
                .extract().as(UserResponse.class);

        assertThat(updatedUser.getName()).isEqualTo("morpheus");
        assertThat(updatedUser.getJob()).isEqualTo("zion resident");
        assertThat(updatedUser.getUpdatedAt()).isNotNull();

    }

    @Test
    @DisplayName("Удалить пользователя")
    public void deleteDelete(){
        given().
                when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204)
                .body(equalTo(""));
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void postRegisterSuccessful(){
        LoginRegisterRequest loginRegisterRequest = LoginRegisterRequest.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        LoginRegisterResponse loginRegisterResponse = given()
                .contentType(ContentType.JSON)
                .body(loginRegisterRequest)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("RegisterSuccessfulResponseSchema.json"))
                //.log().all()
                .extract().as(LoginRegisterResponse.class);

        assertThat(loginRegisterResponse).isNotNull();
        assertThat(loginRegisterResponse.getId()).isEqualTo(4);
        assertThat(loginRegisterResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("Неуспешная регистрация")
    public void postRegisterUnsuccessful(){
        LoginRegisterRequest loginRegisterRequest = LoginRegisterRequest.builder()
                .email("sydney@fife")
                .build();

        LoginRegisterResponse loginRegisterResponse = given()
                .contentType(ContentType.JSON)
                .body(loginRegisterRequest)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("RegisterLoginUnsuccessfulResponseSchema.json"))
                //.log().all()
                .extract().as(LoginRegisterResponse.class);

        assertThat(loginRegisterResponse).isNotNull();
        assertThat(loginRegisterResponse.getError()).isEqualTo("Missing password");
    }

    @Test
    @DisplayName("Успешная авторизация")
    public void postLoginSuccessful(){
        LoginRegisterRequest loginRequest = LoginRegisterRequest.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();

        LoginRegisterResponse loginResponse = given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("LoginSuccessfulResponseSchema.json"))
                //.log().all()
                .extract().as(LoginRegisterResponse.class);

        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("Неуспешная авторизация")
    public void postLoginUnsuccessful(){
        LoginRegisterRequest loginRequest = LoginRegisterRequest.builder()
                .email("peter@klaven")
                .build();

        LoginRegisterResponse loginRegisterResponse = given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("RegisterLoginUnsuccessfulResponseSchema.json"))
                //.log().all()
                .extract().as(LoginRegisterResponse.class);

        assertThat(loginRegisterResponse).isNotNull();
        assertThat(loginRegisterResponse.getError()).isEqualTo("Missing password");
    }

    @Test
    @DisplayName("Получить список пользователей с задержкой в 3 секунды")
    public void getDelayedResponse(){
        List<UserData> users = given().
                when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .statusCode(200)
                .time(greaterThan(3000L)).and().time(lessThan(6000L))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UserListSchema.json"))
                .body("page", equalTo(1))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
                //.log().all()
                .extract().jsonPath().getList("data", UserData.class);
        assertThat(users).extracting(UserData::getId).isNotNull();
        assertThat(users).extracting(UserData::getFirst_name).contains("George");
        assertThat(users).extracting(UserData::getLast_name).contains("Bluth");
    }



}
