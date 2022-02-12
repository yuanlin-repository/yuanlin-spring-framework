package github.yuanlin.service;

import github.yuanlin.model.Order;

/**
 * @author yuanlin
 * @date 2022/02/09/20:45
 */
public class OrderServiceImpl implements OrderService {

	@Override
	public Order createOrder(String username, String product) {
		Order order = new Order();
		order.setUserName(username);
		order.setProduct(product);
		return order;
	}

	@Override
	public Order queryOrder(String username) {
		Order order = new Order();
		order.setUserName("test");
		order.setProduct("test");
		return order;
	}
}
