package com.cs5500.NEUEat.controller;

import com.alibaba.fastjson.JSON;
import com.cs5500.NEUEat.NeuEatApplication;
import com.cs5500.NEUEat.model.Dish;
import com.cs5500.NEUEat.model.Driver;
import com.cs5500.NEUEat.model.Order;
import com.cs5500.NEUEat.model.User;
import com.cs5500.NEUEat.repository.DriverRepository;
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
public class DriverControllerTest extends AbstractTestNGSpringContextTests {
  Driver d;

  @Autowired
  DriverRepository driverRepository;

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  private WebApplicationContext webApplicationContext;
  private MockMvc mockMvc;

  @Test
  public void getDriverById() throws Exception {
    d = driverRepository.findAll().get(0);
    String id = d.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn().getResponse().getContentAsString();
    System.out.println(responseString);
  }

  @Test
  public void loginDriver() throws Exception{
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    User testUser = new User();
    testUser.setUserName("tma");
    testUser.setPassword("123456");
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/driver/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSON.toJSONString(testUser)))
        .andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void registerDriver() throws Exception{
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    User testUser = new User();
    testUser.setUserName("tma");
    testUser.setPassword("123456");
    testUser.setPhoneNumber("123456789");
    testUser.setAddress("987654321");
    testUser.setCity("Seattle");
    testUser.setState("WA");
    testUser.setZip("98121");
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/driver/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSON.toJSONString(testUser))
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void logoutDriver() throws Exception{
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/driver/logout")
        .contentType(MediaType.APPLICATION_JSON)
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void getPendingOrders() throws Exception{
    d = driverRepository.findAll().get(0);
    String id = d.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/pendingOrders/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn().getResponse().getContentAsString();
    System.out.println(responseString);
    String responseString1 = mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/myActiveOrder/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn().getResponse().getContentAsString();
    System.out.println(responseString1);
  }

  @Test
  public void getActiveOrder() throws Exception{
//    Driver driver = new Driver();
//    driver.setId("1234");
//    driverRepository.save(driver);
//    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/myActiveOrder/" + "1234")
//        .contentType(MediaType.APPLICATION_JSON)
//        .accept(MediaType.APPLICATION_JSON)
//    ).andExpect(MockMvcResultMatchers.status().isOk())
//        .andDo(MockMvcResultHandlers.print())
//        .andReturn().getResponse().getContentAsString();
//    System.out.println(responseString);
  }

  @Test
  public void getOrderHistory() throws Exception{
    d = driverRepository.findAll().get(0);
    String id = d.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/myOrderHistory/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn().getResponse().getContentAsString();
    System.out.println(responseString);
  }

  @Test
  public void acceptOrder() throws Exception{
    Order order = new Order();
    order.setCustomerId("1");
    Dish dish = new Dish();
    dish.setDishName("123");
    dish.setImageUrl("12345");
    dish.setPrice(1.99);
    List<Dish> menu = new ArrayList<>();
    menu.add(dish);
    order.setContent(menu);
    order.setId("1234567");
    orderRepository.save(order);
    d = driverRepository.findAll().get(0);
    String id = d.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/driver/accept")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"driverId\":" + id + ", \"orderId\":1234567}")
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void finishOrder() throws Exception{
    d = driverRepository.findAll().get(0);
    String id = d.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/driver/finish")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"driverId\":" + id + ", \"orderId\":1234567}")
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void resetPassword() throws Exception{
    d = driverRepository.findAll().get(0);
    String id = d.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/driver/resetpassword")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":" + id + ", \"oldPassword\":123456, \"newPassword\":256478}")
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void resetPhoneNumber() throws Exception{
    d = driverRepository.findAll().get(0);
    String id = d.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/driver/resetPhone")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":" + id + ", \"phoneNumber\":123456789}")
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void resetAddress() throws Exception{
    d = driverRepository.findAll().get(0);
    String id = d.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/driver/resetAddress")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":" + id + ", \"address\":testA, \"city\": testcity, \"state\": testState, \"zip\": 12345}")
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void deleterDriver() throws Exception{
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    d = driverRepository.findAll().get(0);
    String id = d.getId();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/driver/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":" + id +"}")
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }
}