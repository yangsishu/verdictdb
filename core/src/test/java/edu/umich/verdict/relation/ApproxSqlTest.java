package edu.umich.verdict.relation;

import java.sql.ResultSet;

import edu.umich.verdict.VerdictConf;
import edu.umich.verdict.VerdictContext;
import edu.umich.verdict.exceptions.VerdictException;
import edu.umich.verdict.util.ResultSetConversion;

public class ApproxSqlTest {

	public static void main(String[] args) throws VerdictException {
		VerdictConf conf = new VerdictConf();
		conf.setDbms("impala");
		conf.setPort("21050");
		conf.setDbmsSchema("instacart1g");
		conf.set("no_user_password", "true");
		VerdictContext vc = new VerdictContext(conf);
		
//		String sql = "select order_hour_of_day, count(distinct product_id), count(*)"
//				+ " from order_products, orders"
//				+ " where order_products.order_id = orders.order_id"
//				+ "   AND order_dow = 0 or order_dow = 1"
//				+ " group by order_hour_of_day"
//				+ " order by order_hour_of_day asc";
		
//		String sql = "select order_dow, count(*) "
//				+ "from orders "
//				+ "group by order_dow "
//				+ "order by order_dow asc";
		
//		String sql = "select count(*) "
//				+ "from orders";
		
		String sql = "select avg(days_since_prior) "
				+ "from orders";
		
//		String sql = "select count(*), count(distinct user_id), count(distinct order_id) "
//				+ "from orders";
//				+ "where order_dow = 0 OR order_dow = 1 ";
		
//		String sql = "select count(*), count(distinct user_id), count(distinct orders.order_id) "
//				+ "from order_products, orders "
//				+ "where order_products.order_id = orders.order_id "
//				+ " AND (order_dow = 0 OR order_dow = 1) ";
		
		ExactRelation r = ExactRelation.from(vc, sql);
		ApproxRelation a = r.approx();
		ResultSet rs = a.collectResultSet();
		ResultSetConversion.printResultSet(rs);
		
		vc.destroy();
	}

}