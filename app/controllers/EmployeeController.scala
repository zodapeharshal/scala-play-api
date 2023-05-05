package controllers ; 

import play.api.libs.json._
import play.api.libs.functional.syntax._
import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import scala.collection.mutable.ListBuffer
import models._
import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.{Employee, EmployeeForm}
import play.api.data.FormError
 
import services.EmployeeService
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


// @Singleton
// class EmployeeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

//      implicit val implicitEmployeeWrites = new Writes[Employee]{
//         def writes(emp: Employee): JsValue = {
//             Json.obj(
//                 "id" -> emp.id , 
//                 "name" -> emp.name,
//                 "email" -> emp.email,
//                 "phone" -> emp.phone,
//                 "department" -> emp.department,
//             )
//         }
//     }

    
//   def index() = Action { implicit request: Request[AnyContent] =>
//     Ok("All Books")
//   }

//     private val employeeList = new ListBuffer[Employee]() ; 
//     employeeList += Employee(1, "John Doe", "john.doe@example.com", "123-456-7890", "Sales")
//     employeeList += Employee(2, "Jane Smith", "jane.smith@example.com", "987-654-3210", "Marketing")


//   // sample data
// //   var employees = Seq(
// //     Employee(1, "John Doe", "john.doe@example.com", "123-456-7890", "Sales"),
// //     Employee(2, "Jane Smith", "jane.smith@example.com", "987-654-3210", "Marketing")
// //   )

//   // GET /employees
//   def getEmployees: Action[AnyContent] = Action {
//     Ok(Json.toJson(employeeList))
//   }

//     def getById(employeeId: Int) : Action[AnyContent] = Action {
//         val empid = employeeList.find(_.id == employeeId)
//         empid match {
//             case Some(value) => Ok(Json.toJson(value))
//             case None => NotFound 
//         }
//     }

// }

class EmployeeController @Inject()(
    cc: ControllerComponents,
    employeeService : EmployeeService
) extends AbstractController(cc) {
    implicit val employeeFormat  = Json.format[Employee]

    def getAll() = Action.async { implicit request: Request[AnyContent] => 
        employeeService.listAllEmployees map { emps => 
        Ok(Json.toJson(emps))

        }
    }

    def getById(id: Int) = Action.async { implicit request: Request[AnyContent] => 
        employeeService.getEmployee(id) map { emp => 
            Ok(Json.toJson(emp))
        }
    }

    def add() = Action.async {implicit request: Request[AnyContent] => 
        EmployeeForm.form.bindFromRequest().fold(
            errorForm => {
                errorForm.errors.foreach(println)
                Future.successful(BadRequest("Error!"))
            },
            data => {
                val newEmp = Employee(0, data.name, data.email, data.phone, data.department)
                employeeService.addEmployee(newEmp).map(emp=> Created(Json.toJson(emp)))
            }
        )
    }

    def update(id: Int) = Action.async {implicit request: Request[AnyContent] => 
        EmployeeForm.form.bindFromRequest().fold(
            errorForm => {
                errorForm.errors.foreach(println)
                Future.successful(BadRequest("Error! BAD REQUEST "))
            },
            data => {
                val newEmp = Employee(id, data.name, data.email, data.phone, data.department)
                employeeService.updateEmployee(newEmp).map( _ => Redirect(routes.EmployeeController.getAll))
          
            }
        )
    }

    def delete(id: Int) = Action.async { implicit  request: Request[AnyContent] => 
    // val deletedRows = employeeService.deleteEmployee(id) 
    // deletedRows match {
    //     case 0 => NotFound
    //     case scala.util.Failure(e) => InternalServerError("An error occured while deleting the employee")
    // }
    employeeService.deleteEmployee(id)  map { res =>
          Redirect(routes.EmployeeController.getAll)
        }
    }
}