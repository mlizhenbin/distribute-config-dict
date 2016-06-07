package org.lzbruby.config.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.lzbruby.config.dao.model.ConfigDict;
import org.lzbruby.config.domain.ConfigDictValidFlagType;
import org.lzbruby.config.service.ConfigDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 功能描述：配置字典加载器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 16/6/1 Time: 12:26
 */
public class ConfigLoaderContext {

    /**
     * LOG
     */
    private static final Logger LOG = LoggerFactory.getLogger(ConfigLoaderContext.class);

    /**
     * 单实例.
     */
    private static ConfigLoaderContext context = null;

    /**
     * CodeType和key的连接符号.
     */
    private static final String MAGIC_SEPERATOR = "@#$%^&*";

    /**
     * 配置字典db操作service
     */
    public static final String CONFIG_DICT_DB_SERVICE = "configDictService";

    /**
     * 配置字典注册操作service
     */
    public static final String CONFIG_DICT_REGISTER_SERVICE = "configDictVersionRegister";

    /**
     * 刷新时间, 默认为1小时刷新一次
     */
    private static long refreshTime = 24 * 60 * 60 * 1000l;

    /**
     * 最后刷新时间
     */
    private static long lastRefreshTime = 0;

    /**
     * 存放配置session.
     */
    private Map<String, Object> session = Maps.newLinkedHashMap();

    /**
     * 单例模式.
     *
     * @return 接口
     */
    public static ConfigLoaderContext getContext() {
        synchronized (ConfigLoaderContext.class) {
            if (context == null) {
                context = new ConfigLoaderContext();
            }
        }

        long currentTime = System.currentTimeMillis();
        // 如果超过刷新时间，则刷新数据字典
        try {
            if ((currentTime - lastRefreshTime) > refreshTime) {
                LOG.info("正在更新配置信息(更新时间:" + new Date(currentTime) + ")");

                context.refresh();

                // 更新最后刷新时间
                lastRefreshTime = System.currentTimeMillis();

                ConfigDictVersionRegister register = (ConfigDictVersionRegister) ConfigLoader.getBean(CONFIG_DICT_REGISTER_SERVICE);
                register.refresh();

                LOG.info("更新配置信息完毕！");
            }
        } catch (Exception e) {
            LOG.error("更新数据字典出错，出错原因:", e);
            context = null;
        }

        return context;
    }

    /**
     * 设置刷新时间，使用者可以通过此接口改变默认的刷新时间。
     *
     * @param refreshTime
     */
    public static void setRefreshTime(long refreshTime) {
        ConfigLoaderContext.refreshTime = refreshTime;
    }

    /**
     * 私有构造.
     */
    private ConfigLoaderContext() {
        // 第一次创建类时最后刷新时间为当前时间
        lastRefreshTime = System.currentTimeMillis();
        refresh();
    }

    /**
     * 得到数据字典DO.
     *
     * @param configType 编码类型
     * @param key        编码key
     * @return 数据字典DO
     */
    public ConfigDict getCodeDict(String configType, String key) {
        if (StringUtils.isBlank(configType) || StringUtils.isBlank(key)) {
            return null;
        }

        // 以configType + MAGIC_SEPERATOR + key做为HashMap的key键
        String magicKey = getMagicKey(configType, key);
        return (ConfigDict) session.get(magicKey);
    }

    /**
     * 得到全部数据字典DO列表（可见+不可见）.
     *
     * @param configType 编码类型
     * @return 数据字典DO列表
     */
    @SuppressWarnings("unchecked")
    public List<ConfigDict> getCodeDicts(String configType) {
        if (StringUtils.isBlank(configType)) {
            return null;
        }
        return (List<ConfigDict>) session.get(configType);
    }

    /**
     * 得到有效数据字典DO列表.
     *
     * @param configType 编码类型
     * @return 数据字典DO列表
     */
    @SuppressWarnings("unchecked")
    public List<ConfigDict> getValidCodeDicts(String configType) {
        if (StringUtils.isBlank(configType)) {
            return Lists.newArrayList();
        }

        // 过滤掉无效的数据字典（前台下拉框不可见的）
        List<ConfigDict> configDicts = (List<ConfigDict>) session.get(configType);
        if (CollectionUtils.isEmpty(configDicts)) {
            return Lists.newArrayList();
        }

        // 把过滤之后的放入另外一个list中返回
        List<ConfigDict> valids = Lists.newArrayList();
        for (ConfigDict configDict : configDicts) {
            if (StringUtils.equals(ConfigDictValidFlagType.VALID_FLAG_YES.getType(), configDict.getValidFlag())) {
                valids.add(configDict);
            }
        }
        return valids;
    }

    /**
     * 根据数据类型,获得KEY最大值
     * <p/>
     * 注意：该情况只适合定义的VALUE是数字
     *
     * @param configType 数据字典类型
     * @return Integer
     */
    public Integer getMaxIntegerKey(String configType) {
        if (StringUtils.isEmpty(configType)) {
            return null;
        }
        List<ConfigDict> listCodeDictDo = ConfigLoaderContext.getContext().getCodeDicts(configType);
        if (CollectionUtils.isEmpty(listCodeDictDo)) {
            return null;
        }
        Integer returnVal = null;
        for (ConfigDict configDict : listCodeDictDo) {
            String key = configDict.getKey();
            if (StringUtils.isNotEmpty(key)) {
                Integer tempVal = Integer.valueOf(key);
                if (null == returnVal) {
                    returnVal = tempVal;
                } else if (returnVal < tempVal) {
                    returnVal = tempVal;
                }
            }
        }
        return returnVal;
    }

    /**
     * 根据数据类型,获取KEY对应的VALUE
     * <p/>
     * <p>只匹配可见数据字典</p>
     *
     * @param configType 数据字典类型
     * @param key        数据类型KEY
     * @return VALUE
     */
    public final String getValidValue(String configType, String key) {
        String returnKey = "";
        if (StringUtils.isEmpty(configType) || StringUtils.isEmpty(key)) {
            return returnKey;
        }
        List<ConfigDict> listCodeDictDo = ConfigLoaderContext.getContext().getValidCodeDicts(configType);
        returnKey = getValueByKey(listCodeDictDo, key);

        return returnKey;
    }

    /**
     * 获取配置类型下的所有配置
     *
     * @param configType
     * @return
     */
    public final List<ConfigDict> listByConfigType(String configType) {
        if (StringUtils.isBlank(configType)) {
            return Lists.newArrayList();
        }
        return ConfigLoaderContext.getContext().getValidCodeDicts(configType);
    }

    /**
     * 根据KEY找出匹配VALUE
     *
     * @param listCodeDictDo 数据字典集合
     * @param key            值
     * @param key            键
     */
    private String getValueByKey(List<ConfigDict> listCodeDictDo, String key) {
        String returnKey = "";
        for (ConfigDict configDict : listCodeDictDo) {
            String keyInDo = configDict.getKey();
            if (key.equals(keyInDo)) {
                returnKey = configDict.getValue();
                break;
            }
        }
        return returnKey;
    }

    /**
     * 根据数据类型,获取KEY对应的VALUE
     * <p/>
     * <p>匹配可见和不可见数据字典</p>
     *
     * @param configType 数据字典类型
     * @param key        数据类型KEY
     * @return VALUE
     */
    public final String getValue(String configType, String key) {
        String returnKey = "";
        if (StringUtils.isEmpty(configType) || StringUtils.isEmpty(key)) {
            return returnKey;
        }
        List<ConfigDict> listCodeDictDo = ConfigLoaderContext.getContext().getCodeDicts(configType);
        return getValueByKey(listCodeDictDo, key);
    }

    /**
     * 刷新配置下的当前在内存的值
     *
     * @param id
     */
    public void reload(long id) {
        ConfigDictService configDictService = (ConfigDictService) ConfigLoader.getBean(CONFIG_DICT_DB_SERVICE);
        ConfigDict configDict = configDictService.findConfigDictById(id);
        if (configDict == null) {
            // 通过ID去刷新, 如果通过ID查不到配置, 做全量更新
            refresh();
        } else {
            String magicKey = getMagicKey(configDict.getConfigType(), configDict.getKey());
            session.put(magicKey, configDict);
            LOG.info("ConfigDict从DB读取配置信息, configDict={}", configDict);
        }
    }

    /**
     * context重新从DB加载配置文件,刷新上下文信息
     */
    public void reload() {
        refresh();
    }

    /**
     * 刷新数据字典.
     */
    @SuppressWarnings("unchecked")
    private void refresh() {
        ConfigDictService configDictService = (ConfigDictService) ConfigLoader.getBean(CONFIG_DICT_DB_SERVICE);
        List<ConfigDict> configDicts = configDictService.listAllConfigDict();
        LOG.debug("ConfigDict从DB读取配置信息.");
        for (ConfigDict configDict : configDicts) {
            LOG.debug("config=" + configDict);
        }

        // 将数据构造到tmp的HashMap中
        Map<String, Object> tmpMap = Maps.newLinkedHashMap();

        // 循环处理所有CodeDict列表
        for (Iterator<?> it = configDicts.iterator(); it.hasNext(); ) {
            ConfigDict configDict = (ConfigDict) it.next();
            List<ConfigDict> configTypeList = (List<ConfigDict>) tmpMap.get(configDict.getConfigType());

            // 将所有的configType做为key保存列表
            if (configTypeList == null) {
                configTypeList = new ArrayList<ConfigDict>();
                configTypeList.add(configDict);
                tmpMap.put(configDict.getConfigType(), configTypeList);
            } else {
                configTypeList.add(configDict);
            }

            // 以configType + MAGIC_SEPERATOR + key做为HashMap的key键
            String magicKey = getMagicKey(configDict.getConfigType(), configDict.getKey());
            tmpMap.put(magicKey, configDict);
        }

        // 替换现有的map数据.
        Map<String, Object> oldMap = session;
        session = tmpMap;
        oldMap.clear();
    }

    /**
     * 得到configType + MAGIC_SEPERATOR + key
     *
     * @param configType 编码
     * @param key        键
     * @return magic键
     */
    private String getMagicKey(String configType, String key) {
        return configType + MAGIC_SEPERATOR + key;
    }
}
