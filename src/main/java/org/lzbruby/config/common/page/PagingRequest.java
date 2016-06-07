package org.lzbruby.config.common.page;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：分页请求
 * <p>保存条数、第几页、排序规则、查询的起始时间和结束时间等分页请求信息</p>
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 15/9/9 Time: 10:01
 */
public class PagingRequest implements Serializable {


    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6537871581972777911L;

    /**
     * 每页条数
     */
    private Integer pageSize = 20;

    /**
     * 第几页
     */
    private int pageNo = 1;

    /**
     * 排序的字段
     */
    private String orderItem;

    /**
     * 排序类型
     */
    private String orderType;

    /**
     * 起始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    private Map<String, Object> dynamicQueryFields = new HashMap<String, Object>();

    public void addFields(String fieldName, Object fieldValue) {
        dynamicQueryFields.put(fieldName, fieldValue);
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (null != pageSize) {
            this.pageSize = pageSize;
        }
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Map<String, Object> getDynamicQueryFields() {
        return dynamicQueryFields;
    }

    /**
     * 降序
     *
     * @param sort 排序字段
     * @return
     */
    public PagingRequest desc(String sort) {
        this.descSet(sort);
        return this;
    }

    protected void descSet(String sort) {
        if (StringUtils.isNotBlank(sort)) {
            this.orderItem = sort;
            this.orderType = "DESC";
        }
    }

    /**
     * 升序
     *
     * @param sort 排序字段
     * @return
     */
    public PagingRequest asc(String sort) {
        this.ascSet(sort);
        return this;
    }

    protected void ascSet(String sort) {
        if (StringUtils.isNotBlank(sort)) {
            this.orderItem = sort;
            this.orderType = "ASC";
        }
    }

    /**
     * 设置第几页
     *
     * @param sPageNo 第几页
     * @return
     */
    public PagingRequest pageNo(String sPageNo) {
        this.pageNoSet(sPageNo);
        return this;
    }

    protected void pageNoSet(String sPageNo) {
        try {
            this.pageNo = Integer.parseInt(sPageNo);
        } catch (Exception e) {
            // do nothing
        }
    }

    /**
     * 设置第几页和按字段{sort}进行降序
     *
     * @param sort    排序字段
     * @param sPageNo 第几页
     * @return
     */
    public PagingRequest pageNoAndDesc(String sort, String sPageNo) {
        this.descSet(sort);
        this.pageNoSet(sPageNo);
        return this;
    }

    /**
     * 设置第几页和按字段{sort}进行升序
     *
     * @param sort    排序字段
     * @param sPageNo 第几页
     * @return
     */
    public PagingRequest pageNoAndAsc(String sort, String sPageNo) {
        this.ascSet(sort);
        this.pageNoSet(sPageNo);
        return this;
    }
}
