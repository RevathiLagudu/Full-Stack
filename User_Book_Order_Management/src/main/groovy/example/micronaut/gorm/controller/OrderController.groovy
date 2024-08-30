package example.micronaut.gorm.controller

import example.micronaut.gorm.model.OrderModel
import example.micronaut.gorm.service.OrderService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put


import javax.inject.Inject

@Controller("/orders")
class OrderController {

    @Inject
    OrderService orderService

    @Post
    def saveOrders(@Body OrderModel orderModel){
        return orderService.saveOrders(orderModel)
    }

    @Get("/{id}")
    def getById(@PathVariable long id){
        return orderService.getOrdersById(id)
    }

    @Get("/orderId/{id}")
    def getOrderDetailsById(@PathVariable long id){
        return orderService.getOrderDetailsById(id)
    }
    @Get("/userId/{id}")
    def getUserId(@PathVariable long id){
        return orderService.getUserId(id)
    }
    @Get
    def getAll(){
        return orderService.getAll()
    }
    @Delete("/{orderId}")
    def deleteById(@PathVariable long orderId){
        orderService.deleteOrder(orderId)
        return "Deleted successfully"
    }

    @Put("/update/{id}")
    def updateOrders(@PathVariable long id,@Body OrderModel orderModel){
        orderService.updateOrder(id,orderModel)
        return "updated successfully"
    }
}
