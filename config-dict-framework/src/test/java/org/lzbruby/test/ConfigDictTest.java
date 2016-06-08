package org.lzbruby.test;

import org.lzbruby.config.common.ConfigDictHelper;
import org.lzbruby.config.dao.model.ConfigDict;
import org.lzbruby.config.domain.ConfigDictType;

import java.util.List;

/**
 * 功能描述：
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 16/6/8 Time: 15:59
 */
public class ConfigDictTest {

    public static void main(String[] args) {

        ConfigDict configDict = ConfigDictHelper.getConfigDict(ConfigDictType.TEST);
        String value = ConfigDictHelper.getValue(ConfigDictType.TEST);
        List<ConfigDict> configDicts = ConfigDictHelper.listConfigDicts(ConfigDictType.TEST.getConfigKey());
    }
}
