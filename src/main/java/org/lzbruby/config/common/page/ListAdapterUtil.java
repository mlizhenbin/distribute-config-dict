package org.lzbruby.config.common.page;

import org.apache.commons.lang.StringUtils;
import org.lzbruby.config.common.page.adapter.DefaultListAdapter;
import org.lzbruby.config.common.page.adapter.ListAdapter;

import java.util.Map;


/**
 * DAO层列表适配器工具类
 *
 * @author lizhenbin
 */
public class ListAdapterUtil {

    /**
     * 将分页请求转换成列表适配器
     * <p>分页请求必须存在，且当前页数和每页条数必须设置</p>
     *
     * @param <T>           数据对象
     * @param pagingRequest 分页请求，不允许为<code>null</code>
     * @return 列表适配器
     */
    public static final <T> ListAdapter<T> convert2ListAdapter(PagingRequest pagingRequest) {
        DefaultListAdapter<T> adapter = new DefaultListAdapter<T>();
        if (StringUtils.isNotBlank(pagingRequest.getOrderItem())) {
            adapter.setOrderItem(pagingRequest.getOrderItem());
        }
        if (StringUtils.isNotBlank(pagingRequest.getOrderType())) {
            adapter.setOrderType(pagingRequest.getOrderType());
        }
        if (pagingRequest.getStartTime() != null) {
            adapter.setFiled("startTime", pagingRequest.getStartTime());
        }
        if (pagingRequest.getEndTime() != null) {
            adapter.setFiled("endTime", pagingRequest.getEndTime());
        }
        adapter.setPageNo(pagingRequest.getPageNo());
        adapter.setPageSize(pagingRequest.getPageSize());

        Map<String, Object> dynamicQueryFields = pagingRequest.getDynamicQueryFields();
        for (String key : dynamicQueryFields.keySet()) {
            adapter.setFiled(key, dynamicQueryFields.get(key));
        }
        return adapter;
    }
}
