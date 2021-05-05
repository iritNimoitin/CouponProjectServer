# CouponProjectServer

## About

**Coupon management system** allows companies to generate coupons as part of advertising campaigns
And marketing that they sustain.
The system also has registered customers. Customers can purchase coupons. Coupons are limited in quantity
And in effect. Customer is limited to one coupon of each type.
The system records the coupons purchased by each customer.
The system's income is from purchases of coupons by customers and from creation and updating of new coupons by the companies.

Access to the system is divided into three types of Clients:
1. Administrator - Manage the list of companies and the list of customers.
2. Company - Managing a list of coupons associated with the company.
3. Customer - Purchasing coupons.

### * This project was built on top of a previous project called:
> [Spring-Coupon-Project](https://github.com/iritNimoitin/Spring-Coupon-Project)

In this project I externalize the business logic of the server to the world so that it can be used on the Internet, for example by a website.
I will do this by implementing RESTful Service on the server side, using Spring @RestController.


## Execution stages

 * Part 1 - Implementing Spring MVC with Embedded Tomcat. This is done by the extension of Starter-Web.
  
 * Part 2 - Exceptions defined for the system from the previous project, will be transmitted as a Message to the customer, so that they can be displayed if necessary.
  
 * Part 3 - Define @RestController for each Facade Service.
  
 * Part 4 - Manage accesses to the system using Tokens that will be issued to each customer upon login.
  
 * Part 5 - Blocking a client trying to access the system without a token and redirect it to login. Performed by Spring Filter.
  
 * Part 6 - Creating a class that will maintain a valid list of Tokens and be used by the Controllers.
