package online.shixun.project.module.order.enumerate;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * 订单状态枚举操作类
 * @author am
 *
 */
public class OrderStatusHandler extends BaseTypeHandler<OrderStatusEnum> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, OrderStatusEnum parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getOrdinal());
	}

	@Override
	public OrderStatusEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int i = rs.getInt(columnName);
		if (rs.wasNull()) {
			return null;
		} else {
			return OrderStatusEnum.get(i);
		}
	}

	@Override
	public OrderStatusEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int i = rs.getInt(columnIndex);
		if (rs.wasNull()) {
			return null;
		} else {
			return OrderStatusEnum.get(i);
		}
	}

	@Override
	public OrderStatusEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int i = cs.getInt(columnIndex);
		if (cs.wasNull()) {
			return null;
		} else {
			return OrderStatusEnum.get(i);
		}
	}

}
