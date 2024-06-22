package ru.tolmatskaya.tests.tasks5;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import ru.tolmatskaya.task5.data.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class ApiTests{

    @Test
    @DisplayName("Получить список пользователей со 2 страницы")
    public void getListUsers(){
        List<UserData> users = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
                .body("data.id", not(hasItem(nullValue())))
                .body("data.first_name", hasItem("Tobias"))
                .body("data.last_name", hasItem("Funke"))
                .log().all()
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
                .body("page", equalTo(1))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
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
                .extract().as(UserResponse.class);

        assertThat(createdUser.getName()).isEqualTo("morpheus");
        assertThat(createdUser.getJob()).isEqualTo("leader");
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Обновить пользователя PUT")
    public void putUpdate(){}

    @Test
    @DisplayName("Обноваить пользователя PATCH")
    public void patchUpdate(){}

    @Test
    @DisplayName("Удалить пользователя")
    public void deleteDelete(){}

    @Test
    @DisplayName("Успешная регистрация")
    public void postRegisterSuccessful(){}

    @Test
    @DisplayName("Неуспешная регистрация")
    public void postRegisterUnsuccessful(){}

    @Test
    @DisplayName("Успешная авторизация")
    public void postLoginSuccessful(){}

    @Test
    @DisplayName("Неуспешная авторизация")
    public void postLoginUnsuccessful(){}

    @Test
    @DisplayName("Получить список пользователей с задержкой в 3 секунды")
    public void getDelayedResponse(){}

    @Test
    public void createUser(){
        Map<String, String> requestData = new HashMap<>();
        requestData.put("name", "morpheus");
        requestData.put("job", "leader");
        Response response = given()
                .contentType("application/json")
                .body(requestData)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .response();
        JsonPath jsonResponse = response.jsonPath();
        Assert.assertEquals("name is not valid", jsonResponse.get("name").toString(), requestData.get("name"));
        Assert.assertEquals("name is not valid", jsonResponse.get("job").toString(), requestData.get("job"));
    }

    @Test
    public void createUserWithDTO(){
        People people = new People("morpheus", "leader");
        PeopleCreated peopleCreated = given()
                .contentType("application/json")
                .body(people)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(PeopleCreated.class);
        System.out.println(peopleCreated.getName());

    }

}
