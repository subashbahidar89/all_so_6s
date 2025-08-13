import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test {
	
	public static void main(String[] args) {
		System.out.println("Hello.....");
		HashMap<String, Map<String,Object>> orders = new HashMap<>();
		List<Map<String,Object>> orderList = new ArrayList();
		Map<String, Object> orderItem1 = new HashMap<>();
		orderItem1.put("productName", "Oil");
		orderItem1.put("productPrice", 150);
		Map<String, Object> orderItem2 = new HashMap<>();
		orderItem2.put("productName", "Oil");
		orderItem2.put("productPrice", 120);
		Map<String, Object> orderItem3 = new HashMap<>();
		orderItem3.put("productName", "cookingOil");
		orderItem3.put("productPrice", 140);
		
		orders.put("order1" , orderItem1);
		orders.put("order2" , orderItem2);
		orders.put("order3" , orderItem3);
		
		
		
		
		
		List<Map.Entry<String, Map<String, Object>>> sortedOrders = orders.entrySet().stream().sorted(Comparator.comparing(e -> (Integer) e.getValue().get("productPrice"))).collect(Collectors.toList());
		
		sortedOrders.forEach(order -> 
		System.out.println(order.getKey() + " - " +
                order.getValue().get("productName") + " - " +
                order.getValue().get("productPrice"))
);
		
	}

}
