package com.cs5500.NEUEat.controller;

import com.alibaba.fastjson.JSON;
import com.cs5500.NEUEat.NeuEatApplication;
import com.cs5500.NEUEat.model.Restaurant;
import com.cs5500.NEUEat.model.User;
import com.cs5500.NEUEat.repository.RestaurantRepository;
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
public class RestaurantControllerTest extends AbstractTestNGSpringContextTests {
  Restaurant r;

  @Autowired
  RestaurantRepository restaurantRepository;

  @Autowired
  private WebApplicationContext webApplicationContext;
  private MockMvc mockMvc;


  @Test
  public void getAllRestaurants() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurant/all")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn().getResponse().getContentAsString();

    System.out.println(responseString);
  }

  @Test
  public void searchRestaurants() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurant/search/PizzaHut")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn().getResponse().getContentAsString();
    System.out.println(responseString);
  }

  @Test
  public void getRestaurantById() throws Exception {
    r = restaurantRepository.findAll().get(0);
    String id = r.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurant/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn().getResponse().getContentAsString();
    System.out.println(responseString);
  }

  @Test
  public void loginRestaurant() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    User testUser = new User();
    testUser.setUserName("tma");
    testUser.setPassword("123456");
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/restaurant/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSON.toJSONString(testUser))
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void registerRestaurant() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    User testUser = new User();
    testUser.setUserName("tma");
    testUser.setPassword("123456");
    testUser.setPhoneNumber("123456777");
    testUser.setAddress("12355555");
    testUser.setCity("Seattle");
    testUser.setState("WA");
    testUser.setZip("98125");
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/restaurant/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSON.toJSONString(testUser))
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void logoutRestaurant() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/restaurant/logout")
        .contentType(MediaType.APPLICATION_JSON)
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void getActiveOrders() throws Exception {
    r = restaurantRepository.findAll().get(0);
    String id = r.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurant/myActiveOrders/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn().getResponse().getContentAsString();
    System.out.println(responseString);
  }

  @Test
  public void getOrderHistory() throws Exception {
    r = restaurantRepository.findAll().get(0);
    String id = r.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurant/myOrderHistory/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn().getResponse().getContentAsString();
    System.out.println(responseString);
  }

  @Test
  public void getMenu() throws Exception {
    r = restaurantRepository.findAll().get(0);
    String id = r.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurant/menu/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn().getResponse().getContentAsString();
    System.out.println(responseString);
  }

  @Test
  public void addDishToMenu() throws Exception {
    r = restaurantRepository.findAll().get(0);
    String id = r.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/restaurant/addToMenu")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"restaurantId\": " + id + ", \"dishName\":testdish, \"imageUrl\":testUrl, \"price\":3.2}")
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void removeDishToMenu() throws Exception {
    r = restaurantRepository.findAll().get(0);
    String id = r.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurant/removeDish")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"restaurantId\":" + id + ", \"dish\":{\"dishName\":\"testName\", \"price\":\"3.2\", \"imageUrl\":\"testUrl\"}}")
    ).andExpect(MockMvcResultMatchers.status().is4xxClientError())
        .andDo(MockMvcResultHandlers.print())
        .andReturn().getResponse().getContentAsString();
    System.out.println(responseString);
  }

  @Test
  public void getRestaurantInformation() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    r = restaurantRepository.findAll().get(0);
    String id = r.getId();
    String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurant/information/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn().getResponse().getContentAsString();
    System.out.println(responseString);
  }

  @Test
  public void updateRestaurantInformation() throws Exception {
    r = restaurantRepository.findAll().get(0);
    String id = r.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/restaurant/information")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"restaurantId\":" + id + ","
            + " \"status\":true, \"name\":testName, \"description\":testD,"
            + " \"imageUrl\":testI, \"tag1\":tag1, \"tag2\":tag2, \"tag3\":tag3}")
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }


  @Test
  public void resetPassword() throws Exception {
    r = restaurantRepository.findAll().get(0);
    String id = r.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/restaurant/resetpassword")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":" + id + ", \"oldPassword\":123456, \"newPassword\":256478}")
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void resetPhoneNumber() throws Exception {
    r = restaurantRepository.findAll().get(0);
    String id = r.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/restaurant/resetPhone")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":" + id + ", \"phoneNumber\":123456789}")
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void resetAddress() throws Exception {
    r = restaurantRepository.findAll().get(0);
    String id = r.getId();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/restaurant/resetAddress")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":" + id + ", \"address\":testA, \"city\": testcity, \"state\": testState, \"zip\": 12345}")
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void deleterRestaurant() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    r = restaurantRepository.findAll().get(0);
    String id = r.getId();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/restaurant/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":" + id +"}")
    ).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

}