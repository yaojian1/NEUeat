package com.cs5500.NEUEat.controller;

import static org.junit.Assert.assertEquals;

import com.alibaba.fastjson.JSON;
import com.cs5500.NEUEat.NeuEatApplication;
import com.cs5500.NEUEat.model.Dish;
import com.cs5500.NEUEat.model.Order;
import com.cs5500.NEUEat.repository.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NeuEatApplication.class)
@WebAppConfiguration
public class OrderControllerTest extends AbstractTestNGSpringContextTests {

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  private WebApplicationContext webApplicationContext;
  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  // The sequence is important, it must be addOrderToCart -> Delete
  // because the order can only be delayed before checking out
  @Test
  public void addOrderToCart() throws Exception {
    String url = "/api/order/addToCart";

    JSONArray shopcart = new JSONArray();
    Object dish = new JSONObject().put("dishName", "rice").put("price", 10.0)
        .put("imageUrl", "abcdefg");
    shopcart.put(dish);
    String jsonOrder = new JSONObject().put("customerId", "1").put("restaurantId", "2")
        .put("shopcart", shopcart).toString();

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonOrder)
    ).andReturn();
    int status = result.getResponse().getStatus();
    assertEquals(200, status);
    String content = result.getResponse().getContentAsString();
    assertEquals("1", content);
  }

  @Test
  public void deleteOrder() throws Exception {
    List<Order> db = orderRepository.findAll();
    String id = db.get(db.size() - 1).getId();
    String url = "/api/order/" + id;
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(id)
    ).andReturn();
    int status = result.getResponse().getStatus();
    assertEquals(200, status);
    String content = result.getResponse().getContentAsString();
    assertEquals("1", content);
  }

  @Test
  public void checkoutUsers() throws Exception {
    Dish dish = new Dish();
    dish.setDishName("Big Mac");
    dish.setImageUrl(
        "https://www.mcdonalds.com/is/image/content/dam/usa/nfl/nutrition/items/regular/desktop/t-mcdonalds-Big-Mac.jpg");
    dish.setPrice(5);
    List<Dish> menu = new ArrayList<>();
    menu.add(dish);
    Order order = new Order();
    order.setId("111");
    order.setCustomerId("1");
    order.setRestaurantId("2");
    order.setContent(menu);
    orderRepository.save(order);
    String jsonOrder = JSON.toJSONString(order);
    JSONArray array = new JSONArray();
    array.put(jsonOrder);
    String jsonString = new JSONObject().put("orders", array).toString();

    String url = "/api/order/checkoutAll";
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonString)
    ).andReturn();
    int status = result.getResponse().getStatus();
    assertEquals(200, status);
    String content = result.getResponse().getContentAsString();
    assertEquals("1", content);
  }

  // add comment to the order I just check out(id=111)
  @Test
  public void addComment() throws Exception {
    String url = "/api/order/addComment";
    String jsonOrder = new JSONObject().put("orderId", "111").put("rating", 5)
        .put("content", "good").toString();

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonOrder)
    ).andReturn();
    int status = result.getResponse().getStatus();
    assertEquals(200, status);
    String content = result.getResponse().getContentAsString();
    assertEquals("1", content);
  }

  @Test
  public void deleteComment() throws Exception {
    List<Order> db = orderRepository.findAll();
    String id = db.get(db.size() - 1).getId();
    String url = "/api/order/deleteComment/" + id;
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(id)
    ).andReturn();
    int status = result.getResponse().getStatus();
    assertEquals(200, status);
    String content = result.getResponse().getContentAsString();
    assertEquals("1", content);
  }
}
