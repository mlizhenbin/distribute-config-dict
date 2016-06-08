package org.lzbruby.config.common.page.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 功能描述：默认的查询适配器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 15/9/9 Time: 11:14
 */
public class DefaultFindAdapter<T> implements FindAdapter<T> {

    private static final transient Logger logger = LoggerFactory.getLogger(DefaultFindAdapter.class);

    @Override
    public Map<String, Object> convert(T clazz) {
        //把Bean转化成Map
        Map<String, Object> rtnMap = bean2ParamterMap(clazz);
        //动态自动的内容设置到Paramter Map中
        if (dynamicFileds != null && dynamicFileds.size() > 0) {
            Iterator<String> it = dynamicFileds.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                rtnMap.put("dynamicFileds_" + key, dynamicFileds.get(key));
            }
        }
        return rtnMap;
    }

    /**
     * 把对象属性设置到参数中去
     *
     * @param clazz
     * @return
     * @throws Exception
     */
    public Map<String, Object> bean2ParamterMap(T clazz) {
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        //修复 NullpointException Bug
        if (clazz == null) {
            return rtnMap;
        }
        try {
            BeanInfo bi = Introspector.getBeanInfo(clazz.getClass());
            PropertyDescriptor[] pd = bi.getPropertyDescriptors();
            for (int i = 0; i < pd.length; i++) {
                String name = pd[i].getName();
                Method method = pd[i].getReadMethod();
                Object value = method.invoke(clazz);
                if (!"class".equals(name)) {
                    rtnMap.put(name, value);
                }
            }
        } catch (IntrospectionException ie) {
            logger.error("BeanInfo error :", ie);
        } catch (Exception e) {
            logger.error("Method error :", e);
        }
        return rtnMap;
    }


    /**
     * 动态字段
     */
    protected Map<String, Object> dynamicFileds;

    public FindAdapter<T> setDynamicFileds(Map<String, Object> dynamicFileds) {
        if (dynamicFileds == null) {
            dynamicFileds = new HashMap<String, Object>();
        }
        this.dynamicFileds.putAll(dynamicFileds);
        return this;
    }

    public Map<String, Object> getDynamicFileds() {
        if (null != dynamicFileds && dynamicFileds.size() > 0) {
            return dynamicFileds;
        } else {
            return new HashMap<String, Object>();
        }
    }

    /**
     * 向动态字段中添加内容
     *
     * @param key
     * @param value
     */
    public FindAdapter<T> setFiled(String key, Object value) {
        if (null == dynamicFileds) {
            dynamicFileds = new HashMap<String, Object>();
        }
        dynamicFileds.put(key, value);
        return this;
    }

    /**
     * 获取动态字段的内容
     *
     * @param key
     * @return
     */
    public Object getFiled(String key) {
        if (null == dynamicFileds) {
            return null;
        }
        return dynamicFileds.get(key);
    }
}
