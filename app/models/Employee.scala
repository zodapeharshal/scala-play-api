package models
import play.api.data.Form
import com.google.inject.Inject
import play.api.data.Forms.mapping
import play.api.data.Forms._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.MySQLProfile.api._


case class Employee(id: Int, name: String, email: String, phone: String, department: String)

case class EmployeeFormData(name:String, email: String, phone: String, department: String)

object EmployeeForm {
    val form = Form(
        mapping(
            "name" -> nonEmptyText,
            "email" -> email, 
            "phone" -> nonEmptyText,
            "department" -> nonEmptyText,
        )(EmployeeFormData.apply)(EmployeeFormData.unapply)
    )
}

class EmployeeTableDef(tag: Tag) extends Table[Employee](tag, "Employee") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def email = column[String]("email")
    def phone = column[String]("phone")
    def department = column[String]("department")

    override def * = (id, name , email, phone, department) <> ((Employee.apply _).tupled, Employee.unapply)
}

class EmployeeList @Inject()(
    protected val dbConfigProvider : DatabaseConfigProvider
)(implicit executionContext: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {
        var employeeList = TableQuery[EmployeeTableDef]

        def add(emp: Employee)= {
            dbConfig.db
            .run(employeeList += emp)
            .map(res => "Added Employee")
            .recover{
                case ex : Exception => {
                    printf(ex.getMessage())
                    ex.getMessage
                }
            }
        }
        
        def delete(id: Int): Future[Int] = {
            dbConfig.db.run(employeeList.filter(_.id === id).delete)
        }
        
        def update(emp: Employee): Future[Int] = {
            dbConfig.db
            .run(employeeList.filter(_.id === emp.id)
                    .map(x => (x.name, x.email, x.phone, x.department))
                    .update(emp.name, emp.email, emp.phone, emp.department)
            )
        }
        
        def get(id: Int): Future[Option[Employee]] = {
            dbConfig.db.run(employeeList.filter(_.id === id).result.headOption)
        }
        
        def listAll: Future[Seq[Employee]] = {
            dbConfig.db.run(employeeList.result)
        }
    }
