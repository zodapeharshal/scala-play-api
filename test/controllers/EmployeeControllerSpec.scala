package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class EmployeeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "EmployeeController GET" should {
        "return 200 and the expected JSON response" in {
            val request = FakeRequest(GET, "/employees")
            val response = route(app, request).get

            status(response) mustBe OK
            contentType(response) mustBe Some("application/json")
        }
  }
  "EmployeeController POST" should {
      "return 200 and expected Respose" in {
        val requestData =  Json.obj("name" -> "JohnDoe", "email"-> "john.doe@gmail.com", "phone"->"123456789", "department"->"unknown" )
        val request = FakeRequest(POST, "/employees/add").withJsonBody(requestData) 
        val response = route(app, request).get
        status(response) mustBe CREATED
        contentAsString(response) must include("Added Employee")
      }
  }

  // "EmployeeController DELETE" should {
  //     "return 200 and should return updated list" in {
        
  //       val request = FakeRequest(DELETE, "/employees/delete/69")
  //       val result = route(app, request).get

  //       // 303 response redirected to other url 
  //       status(result) mustEqual SEE_OTHER 
  //       // should redirect to this url 
  //       redirectLocation(result) mustBe Some("/employees")

  //     }
  // }
  
  
}
