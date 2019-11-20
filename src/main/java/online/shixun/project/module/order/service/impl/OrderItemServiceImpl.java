package online.shixun.project.module.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import online.shixun.project.module.commodity.dto.CommodityDto;
import online.shixun.project.module.commodity.service.CommodityService;
import online.shixun.project.module.order.dto.OrderItemDto;
import online.shixun.project.module.order.enumerate.OrderStatusEnum;
import online.shixun.project.module.order.mapper.OrderItemMapper;
import online.shixun.project.module.order.service.OrderItemService;
import online.shixun.project.module.spike.dto.SpikeCommodityDto;
import online.shixun.project.module.spike.service.SpikeCommodityService;

/**
 * 订单服务接口实现类
 * @author am
 *
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private SpikeCommodityService spikeCommodityService;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 通过参数获取订单商品信息(与通过ID获取的商品详细信息区别在于商品的可选参数不是全部,而是选中部分)
	 */
	@Override
	public List<OrderItemDto> getOrderCommodityByParameter(String parameter, CommodityService commodityService, String username) {
		List<OrderItemDto> orders = new ArrayList<OrderItemDto>();
		// 解析参数
		for (String str : parameter.split("-")) {
			String[] items = str.split("_");
			CommodityDto commodity = commodityService.getDetailsCommodityById(Long.parseLong(items[0]));
			// 修改参数
			JSONArray parameterJson = commodity.getParameters().getParameterJson();
			JSONArray reorganization = new JSONArray();
			// 参数溢价
			Double premium = 0.0;
			
			JSONObject redisOrder = null;
			if (items.length >= 4 && "1".equals(items[3])) {
				// 查询Redis订单
				redisOrder = (JSONObject) redisTemplate.opsForValue().get("state_order_" + items[4] + "_" + username);
			}
			// 遍历可选参数
			for (int i=0; i<parameterJson.size(); i++) {
				JSONObject json = parameterJson.getJSONObject(i);
				String reorganizationParameter = "";
				// 遍历需要去除的参数
				for (int j=0; j<json.getJSONArray("parameters").size(); j++) {
					// 当商品选中商品参数存在时
					if (items[1].split(",").length > 1 &&
						json.getJSONArray("parameters").getJSONObject(j).getString("name").equals(items[1].split(",")[i])) {
						
						reorganizationParameter = json.getString("name") + "：" + 
											      json.getJSONArray("parameters").getJSONObject(j).getString("name");
						// 计算参数溢价
						premium += Double.parseDouble(json.getJSONArray("parameters").getJSONObject(j).getString("price"));
					}
				}
				reorganization.add(reorganizationParameter);
			}
			
			OrderItemDto order = new OrderItemDto();
			order.setCommodity(commodity);
			order.setCount(Integer.parseInt(items[2]));
			order.setUnitPrice(commodity.getPrice() + premium);
			if (redisOrder != null) {
				// 查询秒杀商品
				SpikeCommodityDto spikeCommodity = spikeCommodityService.getSpikeCommodityById(redisOrder.getLongValue("spikeCommodityId"));
				order.setActualAmount(spikeCommodity.getPrice());
				// 优惠方式为秒杀抢购
				order.setPreferentialWay(1);
			} else {
				order.setActualAmount(order.getUnitPrice() * order.getCount());
				// 无优惠方式
				order.setPreferentialWay(0);
			}
			order.setParameter(reorganization.toString());
			orders.add(order);
		}
		return orders;
	}

	/**
	 * 插入订单项
	 */
	@Override
	public boolean saveOrderItem(OrderItemDto orderItem) {
		return orderItemMapper.insertOrderItem(orderItem) == 1;
	}

	/**
	 *  根据订单号查询订单项
	 */
	@Override
	public List<OrderItemDto> getOrderItemByOrderNumber(Long orderNumber, String username) {
		return orderItemMapper.selectOrderItemByOrderNumber(orderNumber, username);
	}

	/**
	 * 更新订单项状态
	 */
	@Override
	public boolean updateOrderItemStatus(Long commodityId, Long orderId, OrderStatusEnum orderItemStatus) {
		return orderItemMapper.updateOrderItemStatus(commodityId, orderId, orderItemStatus) == 1;
	}
	
}
