package org.lzbruby.config.common.page.adapter;

import java.util.HashMap;
import java.util.Map;


/**
 * 默认的列表查询适配器
 *
 * @param <T>
 */
public class DefaultListAdapter<T> extends DefaultFindAdapter<T> implements ListAdapter<T> {

    @Override
    public Map<String, Object> convert(T clazz) {
        Map<String, Object> rtnMap = super.convert(clazz);
        //list参数设置到map中去
        rtnMap = covertListParater(rtnMap);
        return rtnMap;
    }

    /**
     * 获取最大页数
     *
     * @return
     */
    public int getMaxPageNo() {
        if (itemTotal == 0) {
            return 1;
        } else {
            return itemTotal / pageSize + (itemTotal / pageSize == 0 ? 0 : 1);
        }
    }

    //getter and setter methods
    public Integer getBegin() {
        if (pageNo <= 1) {
            begin = 0;
        } else {
            begin = (pageNo - 1) * pageSize;
        }
        return begin;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public ListAdapter<T> setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getPageNo() {
        return pageNo;
    }

    public ListAdapter<T> setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public int getItemTotal() {
        return itemTotal;
    }

    public ListAdapter<T> setItemTotal(int itemTotal) {
        this.itemTotal = itemTotal;
        return this;
    }


    public String getOrderItem() {
        return orderItem;
    }

    public ListAdapter<T> setOrderItem(String orderItem) {
        orderItem = orderItem.trim();
        if (orderItem.indexOf(" ") > -1) {
            throw new RuntimeException("setOrderItem error , orderItem has space! but is not allowed!");
        }
        this.orderItem = orderItem;
        return this;
    }

    public String getOrderType() {
        return orderType;
    }

    public ListAdapter<T> setOrderType(String orderType) {
        if (!OrderType.validate(orderType)) {
            this.orderType = OrderType.DESC.getName();
        }
        this.orderType = orderType;
        return this;
    }

    /**
     * 把list查询参数传入到Paramter map中
     *
     * @param dynamicFileds
     * @return
     */
    protected Map<String, Object> covertListParater(Map<String, Object> dynamicFileds) {
        if (dynamicFileds == null) {
            dynamicFileds = new HashMap<String, Object>();
        }
        dynamicFileds.put("begin", getBegin());
        dynamicFileds.put("end", getBegin() + pageSize);
        dynamicFileds.put("pageSize", pageSize);
        dynamicFileds.put("orderItem", orderItem);
        dynamicFileds.put("orderType", orderType);
        return dynamicFileds;
    }

    protected static final int PAGE_SIZE = 20;
    //分页开始页数,和pageNo相关
    protected Integer begin = 0;
    //每页条数
    protected Integer pageSize = PAGE_SIZE;
    //第几页
    protected int pageNo = 1;

    protected int itemTotal = 0;
    //排序的字段
    protected String orderItem;
    //排序类型
    protected String orderType = OrderType.DESC.getName();

    /**
     * 排序的类型
     */
    protected enum OrderType {
        ASC("ASC"),
        DESC("DESC");

        private OrderType(String name) {
            this.name = name;
        }

        public static Map<String, OrderType> orderTypeMaps = new HashMap<String, OrderType>();

        static {
            for (OrderType orderType : OrderType.values()) {
                orderTypeMaps.put(orderType.getName(), orderType);
            }
        }

        public static boolean validate(String orderType) {
            if (orderTypeMaps.get(orderType) == null) {
                return false;
            }
            return true;
        }

        private String name;

        public String getName() {
            return name;
        }
    }

}
