package org.lzbruby.config.common.page.adapter;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：重写DefaultListAdapter查询默认条数限制，此Adapter最大的查询数据量默认Java Long MAX_VALUE
 * ListMaxSizeAdapter查询的数据量比较大，在正常的分页查询等不可使用，适用于通过外键查询所有信息
 *
 * @author: Zhenbin.Li
 * Date: 13-9-10 Time：下午3:21
 */
public class ListMaxSizeAdapter<T> extends DefaultListAdapter<T> {

    //每页条数
    protected Long pageMaxSize = Long.MAX_VALUE;

    @Override
    protected Map<String, Object> covertListParater(Map<String, Object> dynamicFileds) {
        super.covertListParater(dynamicFileds);

        if (dynamicFileds == null) {
            dynamicFileds = new HashMap<String, Object>();
        }
        dynamicFileds.put("begin", getBegin());
        dynamicFileds.put("pageSize", pageMaxSize);
        dynamicFileds.put("orderItem", orderItem);
        dynamicFileds.put("orderType", orderType);
        return dynamicFileds;
    }
}
