import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime, closingTime;

    ByteArrayOutputStream outputStream;
    List<String> itemsInOrder;
    
    @BeforeEach
    public void initializations_BeforeEach() {
    	openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant = Mockito.spy(restaurant);
        
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        
        outputStream = new ByteArrayOutputStream();
    	System.setOut(new PrintStream(outputStream));
    	
    	itemsInOrder = new ArrayList<String>();
    	itemsInOrder.add("Sweet corn soup");
    	itemsInOrder.add("Vegetable lasagne");
    	
    }
    
    @AfterEach
    public void afterEach() {
    	System.setOut(System.out);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
    	
    	//Testing For Edge and Pass Test Cases
    	Mockito.when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("20:00:00"));
    	
    	assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
    	
    	Mockito.when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("09:30:00"),LocalTime.parse("23:30:00"));
        
    	assertFalse(restaurant.isRestaurantOpen());
        assertFalse(restaurant.isRestaurantOpen());

    }
    @Test
    public void is_restaurant_open_should_return_false_if_time_is_equal_to_opening_and_closing_time(){
        
        //LocalTime.parse("10:30:00"),LocalTime.parse("22:00:00"),
    	Mockito.when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("10:30:00"),LocalTime.parse("22:00:00"));
        
    	assertFalse(restaurant.isRestaurantOpen());
        assertFalse(restaurant.isRestaurantOpen());

    }
    	
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    //<<<<<<<<<<<<<<<<<<<<<<<RESTAURANT DETAILS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void get_name_method_should_return_name_of_current_restaurant() {
    	
    	String actualName = restaurant.getName().toLowerCase();
    	String expectedName = "amelie's cafe";
    	
    	assertEquals(expectedName,actualName);
    }
    
    @Test
    public void display_details_should_return_the_details_of_current_restaurant() {
    	
    	String expectedDetails =
    			"Restaurant:Amelie's cafe\n"
                +"Location:Chennai\n"
                +"Opening time:10:30\n"
                +"Closing time:22:00\n"
                +"Menu:"+"\n" + "[Sweet corn soup:119\n, Vegetable lasagne:269\n]\n";
    	
    	restaurant.displayDetails();
    	
    	assertEquals(expectedDetails,outputStream.toString());
    	
    }
   //<<<<<<<<<<<<<<<<<<<<<<<RESTAURANT DETAILS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
  //<<<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void get_order_cost_should_return_total_order_cost_equal_to_388() {
    	
    	int actualTotalOrderCost = restaurant.getOrderCost(itemsInOrder);
    	int expectedTotalOrderCost = 388;
    	
    	assertEquals(expectedTotalOrderCost,actualTotalOrderCost);
    }
    
    @Test
    public void display_order_cost_should_print_total_order_cost_equal_to_388_with_message() {
    	
    	String expectedMessage = "Your order will cost Rs.388\n";
    	restaurant.displayOrderCost(itemsInOrder);
    	
    	assertEquals(expectedMessage,outputStream.toString());
    }
    
  //<<<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}