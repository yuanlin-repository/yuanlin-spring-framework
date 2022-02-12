package github.yuanlin.service;


import github.yuanlin.model.Order;

/**
 * @author yuanlin
 * @date 2022/02/09/20:44
 */
public interface OrderService {

	Order createOrder(String username, String product);

	Order queryOrder(String username);
}
