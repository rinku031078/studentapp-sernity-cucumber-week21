package com.studentapp.cucumber.steps;

import com.studentapp.studentinfo.StudentSteps;
import com.studentapp.utils.TestUtils;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;


public class StudentStepDefs {

    static String email = null;
    static int studentId;

    static ValidatableResponse response;

    @Steps
    StudentSteps studentSteps;

    @When("^User sends a GET request to list endpoints$")
    public void userSendsAGETRequestToListEndpoints() {
        response = studentSteps.getAllStudentsInfo();
    }

    @Then("^User must get response back with valid status code 200$")
    public void userMustGetResponseBackWithValidStatusCode() {
        response.statusCode(200);
    }


    @When("^I create a new student by providing the information firstName \"([^\"]*)\" lastName \"([^\"]*)\" email \"([^\"]*)\" programme \"([^\"]*)\" courses \"([^\"]*)\"$")
    public void iCreateANewStudentByProvidingTheInformationFirstNameLastNameEmailProgrammeCourses(String firstName, String lastName, String _email, String programme, String courses) {
        List<String> courseList = new ArrayList<>();
        courseList.add(courses);
        email = TestUtils.getRandomValue() + _email;
        studentSteps.createStudent(firstName, lastName, email, programme, courseList);
        response.log().all().statusCode(200);
    }

    @Then("^I verify that the student with \"([^\"]*)\" is created$")
    public void iVerifyThatTheStudentWithIsCreated(String _email) {
        response.statusCode(200);
        HashMap<String, Object> studentInfo = studentSteps.getStudentInfoByEmail(email);
        Assert.assertThat(studentInfo, hasValue(email));
        studentId = (int) studentInfo.get("id");
        System.out.println("student Id is: " + studentId);
    }

    @When("^I update student information firstName \"([^\"]*)\" lastName \"([^\"]*)\" email \"([^\"]*)\" programme \"([^\"]*)\" courses \"([^\"]*)\"$")
    public void iUpdateStudentInformationFirstNameLastNameEmailProgrammeCourses(String firstName, String lastName, String _email, String programme, String courses) {
        List<String> courseList = new ArrayList<>();
        courseList.add(courses);
        response = studentSteps.updateStudent(studentId,firstName,lastName,email,programme,courseList);
        response.statusCode(200);
    }

    @Then("^I verify that student information is updated with name \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iVerifyThatStudentInformationIsUpdatedWithNameAnd(String firstName, String courses) {
        response = studentSteps.getStudentById(studentId);
        response.statusCode(200);
        Assert.assertEquals(firstName, "Henry" );
        Assert.assertEquals(courses, "Selenium" );
    }

    @When("^I delete single student data$")
    public void iDeleteSingleStudentData() {
        studentSteps.deleteStudent(studentId).statusCode(204);
    }

    @Then("^I verify that same student data was deleted by getting data by studentId$")
    public void iVerifyThatSameStudentDataWasDeletedByGettingDataByStudentId() {
        studentSteps.getStudentById(studentId).statusCode(404);
    }
}
