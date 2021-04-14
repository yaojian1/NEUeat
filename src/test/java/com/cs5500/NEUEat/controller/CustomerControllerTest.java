package com.cs5500.NEUEat.controller;

import com.alibaba.fastjson.JSON;
import com.cs5500.NEUEat.NeuEatApplication;
import com.cs5500.NEUEat.model.Customer;
import com.cs5500.NEUEat.model.Dish;
import com.cs5500.NEUEat.model.Order;
import com.cs5500.NEUEat.model.User;
import com.cs5500.NEUEat.repository.CustomerRepository;
import com.cs5500.NEUEat.repository.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NeuEatApplication.class)
@WebAppConfiguration
public class CustomerControllerTest {
  Customer c;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  private WebApplicationContext webApplicationContext;
  private MockMvc mockMvc;

  @Test
  public void getCustomerById() throws Exception{
    c = customerRepository.findAll().get(0);
    String id = c.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn().getResponse().getContentAsString();
    System.out.println(responseString);
  }

  @Test
  public void loginCustomer() throws Exception{
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    User testUser = new User();
    testUser.setUserName("tma");
    testUser.setPassword("123456");
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/customer/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSON.toJSONString(testUser))
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void registerCustomer() throws Exception{
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    User testUser = new User();
    testUser.setUserName("tma");
    testUser.setPassword("123456");
    testUser.setPhoneNumber("123456777");
    testUser.setAddress("12355555");
    testUser.setCity("Seattle");
    testUser.setState("WA");
    testUser.setZip("98125");
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/customer/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSON.toJSONString(testUser))
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void logoutCustomer() throws Exception{
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/customer/logout")
        .contentType(MediaType.APPLICATION_JSON)
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void getShoppingCart() throws Exception{
//    Order order = new Order();
//    order.setId("1");
//    Dish dish = new Dish();
//    dish.setDishName("123");
//    dish.setImageUrl("12345");
//    dish.setPrice(1.99);
//    List<Dish> menu = new ArrayList<>();
//    menu.add(dish);
//    order.setContent(menu);
//    order.setId("1234567");
//    orderRepository.save(order);
    Order order = new Order();
    order.setCustomerId("tma");
    Dish dish = new Dish();
    dish.setDishName("123");
    dish.setImageUrl("12345");
    dish.setPrice(1.99);
    List<Dish> menu = new ArrayList<>();
    menu.add(dish);
    order.setContent(menu);
    order.setId("1234567");
    orderRepository.save(order);
    c = customerRepository.findAll().get(0);
    String id = c.getId();
    System.out.println(c);
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/myCart/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn().getResponse().getContentAsString();
    System.out.println(responseString);
  }

  @Test
  public void getActiveOrders() throws Exception{
    c = customerRepository.findAll().get(0);
    String id = c.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/myActiveOrders/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn().getResponse().getContentAsString();
    System.out.println(responseString);
  }

  @Test
  public void getOrderHistory() throws Exception{
    c = customerRepository.findAll().get(0);
    String id = c.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/myOrderHistory/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn().getResponse().getContentAsString();
    System.out.println(responseString);
  }

  @Test
  public void resetPassword() throws Exception{
    Customer customer = new Customer();
    customer.setUserName("9090");
    customer.setPassword("123456");
    customer.setAddress("6767");
    customer.setCity("Seattle");
    customer.setPhoneNumber("789102344");
    customer.setState("WA");
    customer.setZip("12345");
    customerRepository.save(customer);
    c = customerRepository.findAll().get(0);
    System.out.println(c);
    String id = c.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/customer/resetPassword")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":" + id + ", \"password\":123456, \"newPassword\":256478}")
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void resetPhoneNumber() throws Exception{
    Customer customer = new Customer();
    customer.setUserName("9090");
    customer.setPassword("123456");
    customer.setAddress("6767");
    customer.setCity("Seattle");
    customer.setPhoneNumber("789102344");
    customer.setState("WA");
    customer.setZip("12345");
    customerRepository.save(customer);
    c = customerRepository.findAll().get(0);
    System.out.println(c);
    String id = c.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/customer/resetPhone")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":" + id + ", \"phoneNumber\":123456789}")
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void resetAddress() throws Exception{
    c = customerRepository.findAll().get(0);
    String id = c.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/customer/resetAddress")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":" + id + ", \"address\":testA, \"city\": testcity, \"state\": testState, \"zip\": 12345}")
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void deleterCustomer() throws Exception{
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    c = customerRepository.findAll().get(0);
    String id = c.getId();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/customer/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":" + id +"}")
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }
}