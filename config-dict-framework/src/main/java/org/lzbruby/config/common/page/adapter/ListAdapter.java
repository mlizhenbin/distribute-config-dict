package org.lzbruby.config.common.page.adapter;


/**
 * 用于List查询参数设置的封装
 *
 * @param <T>
 * @author lizhenbin
 */
public interface ListAdapter<T> extends FindAdapter<T> {
    /**
     * 获取最大页数
     *
     * @return
     */
    public int getMaxPageNo();

    /**
     * 设置排序字段
     *
     * @param orderItem
     */
    public ListAdapter<T> setOrderItem(String orderItem);

    /**
     * 设置排序类型
     *
     * @param orderType
     */
    public ListAdapter<T> setOrderType(String orderType);

    /**
     * 开始页数
     *
     * @param pageNo
     */
    public ListAdapter<T> setPageNo(int pageNo);

    /**
     * 获取总条数
     *
     * @return
     */
    public int getItemTotal();

    /**
     * 设置总条数
     *
     * @param itemTotal
     */
    public ListAdapter<T> setItemTotal(int itemTotal);

    /**
     * 设置查询返回条数
     *
     * @param pageSize
     */
    public ListAdapter<T> setPageSize(Integer pageSize);
}
