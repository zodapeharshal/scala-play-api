package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import scala.collection.mutable.ListBuffer
import play.api.libs.json._
import models.TodoListItem

@Singleton
class TodoListController @Inject()(val controllerComponents: ControllerComponents) extends BaseController{
    private val todoList = new ListBuffer[TodoListItem]()
    todoList += TodoListItem(1, "test", true)
    todoList += TodoListItem(2, "some other value", false)
    implicit val todoListJson = Json.format[TodoListItem]

    def getAll(): Action[AnyContent] = Action {
        if (todoList.isEmpty) {
            NoContent
        } else {
            Ok(Json.toJson(todoList))
        }
    }
}