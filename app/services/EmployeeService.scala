package services 

import com.google.inject.Inject
import models.{Employee, EmployeeList}
import scala.concurrent.Future

class EmployeeService @Inject() (emps: EmployeeList) {
    
    def addEmployee(emp: Employee): Future[Employee]= {
        emps.add(emp)
    }

    def deleteEmployee(id: Int): Future[Int] = {
        emps.delete(id)
    }

    def updateEmployee(emp: Employee): Future[Int] = {
        emps.update(emp)
    }
 
    def getEmployee(id: Int): Future[Option[Employee]] = {
        emps.get(id)
    }
    
    def listAllEmployees: Future[Seq[Employee]] = {
        emps.listAll
    }
}