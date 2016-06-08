package org.lzbruby.config.service;

import org.lzbruby.config.common.page.Paging;
import org.lzbruby.config.common.page.PagingRequest;
import org.lzbruby.config.dao.model.ConfigDict;
import org.lzbruby.config.domain.ConfigDictVO;
import org.lzbruby.config.domain.ConfigDictValidFlagType;

import java.util.List;

/**
 * 功能描述：WMS分布式动态配置字段操作统一接口
 *
 * @author: lizhenbin
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 2015/06/15 Time：17:12:23
 */
public interface ConfigDictService {

    /**
     * 创建ConfigDict
     *
     * @param configDict ConfigDict对象
     */
    public void addConfigDict(ConfigDict configDict);

    /**
     * 更新ConfigDict
     *
     * @param configDict ConfigDict对象
     */
    public void updateConfigDict(ConfigDict configDict);

    /**
     * 删除配置
     *
     * @param configDictId ConfigDict ID
     */
    public void deleteConfigDict(long configDictId);

    /**
     * 通过主键Long查询ConfigDict
     *
     * @param id ConfigDict主键
     * @return ConfigDict对象
     */
    public ConfigDict findConfigDictById(Long id);

    /**
     * 分页查询ConfigDict
     *
     * @param configDictVO  ConfigDictVO对象
     * @param pagingRequest 分页请求
     * @return paging对象
     */
    public Paging<ConfigDict> findConfigDictByPage(ConfigDictVO configDictVO, PagingRequest pagingRequest);

    /**
     * 获取所有的配置信息
     *
     * @return
     */
    public List<ConfigDict> listAllConfigDict();

    /**
     * 修改配置状态
     *
     * @param id
     * @param flagType
     */
    public void updateConfigDictStatus(long id, ConfigDictValidFlagType flagType);

}