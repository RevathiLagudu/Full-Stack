package example.micronaut.gorm.service

import example.micronaut.gorm.domain.BooksDomain
import example.micronaut.gorm.domain.LineItems
import example.micronaut.gorm.domain.OrderDomain
import example.micronaut.gorm.domain.UserDomain
import example.micronaut.gorm.exceptions.UserNotFound
import example.micronaut.gorm.model.OrderModel
import grails.gorm.transactions.Transactional
import javax.inject.Singleton

@Singleton
class OrderService {

    @Transactional
    def saveOrders(OrderModel orderModel){
        OrderDomain orderDomain=new OrderDomain()
        orderDomain.orderDate=orderModel.orderDate
        orderDomain.user= UserDomain.get(orderModel.userId)
        orderDomain.save()
        orderModel.bookIds.each {bookId->
            LineItems lineItems=new LineItems()
            lineItems.order=orderDomain
            lineItems.book=BooksDomain.get(bookId)
            lineItems.save()
        }
        return "your order id is ${orderDomain}"
    }

    @Transactional
    def getOrdersById(long orderId){
        OrderDomain orderDomain=OrderDomain.findById(orderId)
        if(!orderDomain) {
            return "this order not placed"
        }
        return toOrderModal(orderDomain)
    }

    @Transactional
    def getOrderDetailsById(long orderId){
        OrderDomain orderDomain=OrderDomain.findById(orderId)
        return toOrderModal(orderDomain)
    }

    @Transactional
    def getUserId(long userId){
        def user=UserDomain.findById(userId)
        def orders=OrderDomain.findByUser(user)
        def orderModal=[]
        orders.each {order->
            return orderModal<<toOrderModal(order)
        }
    }

    @Transactional
    def getAll(){
        def orders=OrderDomain.findAll()
        def emptyList=[]
        orders.each{order->
            emptyList<<toOrderModal(order)
        }
        return emptyList
    }

    @Transactional
    def updateOrder(Long id, OrderModel updatedOrderModel) {
        def order = OrderDomain.findById(id)
        if (order) {
            // Explicitly delete existing line items
            LineItems.findAllByOrder(order).each { it.delete(flush: true) }

            // Add new line items based on the updated book IDs
            updatedOrderModel.bookIds.each { bookId ->
                BooksDomain booksDomain = BooksDomain.findById(bookId)
                if (booksDomain) {
                    LineItems lineItems = new LineItems(order: order, book: booksDomain)
                    order.addToLineItems(lineItems)
                } else {
                    throw new IllegalArgumentException("Book with ID ${bookId} not found")
                }
            }
            order=order.save(flush: true)
            return toOrderModal(order)
        } else {
            throw new UserNotFound("Order Not Found")
        }
    }

    @Transactional
    def deleteOrder(long orderId){
        OrderDomain orderDomain=OrderDomain.findById(orderId)
        if(orderDomain) {
            orderDomain.delete()
        }
        else{
            return false
        }
    }


    static def toOrderModal(OrderDomain orderDomain) {
        OrderModel orderModel=new OrderModel()
        orderModel.orderId=orderDomain.id
        orderModel.orderDate=orderDomain.orderDate
        orderModel.userId=orderDomain.userId
        orderModel.bookIds=[]
        orderDomain.lineItems.each {
            items->
                orderModel.bookIds<<(items.book.id as Integer)
        }
        return orderModel

    }
}
