package org.lzbruby.config.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.lzbruby.config.common.page.DefaultPaging;
import org.lzbruby.config.common.page.ListAdapterUtil;
import org.lzbruby.config.common.page.Paging;
import org.lzbruby.config.common.page.PagingRequest;
import org.lzbruby.config.common.page.adapter.ListAdapter;
import org.lzbruby.config.common.utils.DistributeConfigDictUtils;
import org.lzbruby.config.common.utils.QueryMapperUtils;
import org.lzbruby.config.core.ConfigDictVersionRegister;
import org.lzbruby.config.dao.ConfigDictMapper;
import org.lzbruby.config.domain.ConfigDict;
import org.lzbruby.config.domain.ConfigDictVO;
import org.lzbruby.config.domain.ConfigDictValidFlagType;
import org.lzbruby.config.service.ConfigDictService;
import org.lzbruby.config.service.result.ConfigDictException;
import org.lzbruby.config.service.result.ConfigDictResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：WMS分布式动态配置字段操作统一接口实现
 *
 * @author: lizhenbin
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 2015/06/15 Time：17:19:16
 */
@Service("configDictService")
public class ConfigDictServiceImpl implements ConfigDictService {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigDictServiceImpl.class);

    @Autowired
    private ConfigDictMapper configDictMapper;

    @Autowired
    private ConfigDictVersionRegister configDictVersionRegister;

    @Autowired
    protected TransactionTemplate configTransactionTemplate;

    @Override
    public void addConfigDict(ConfigDict configDict) {
        DistributeConfigDictUtils.validAddConfigDict(configDict);

        // 校验是否重复
        Map<String, Object> queryMap = QueryMapperUtils.toQueryMap();
        queryMap.put("configType", configDict.getConfigType());
        queryMap.put("key", configDict.getKey());
        Integer count = configDictMapper.listConfigDictCount(queryMap);
        if (count > 0) {
            LOGGER.warn("该配置已经存在,不允许重复添加配置!, configDict={}", configDict);
            throw new ConfigDictException(ConfigDictResult.CONFIG_DICT_TYPE_AND_KEY_EXIST);
        }

        // 状态默认都是未审核
        configDict.setValidFlag(ConfigDictValidFlagType.VALID_FLAG_WAIT.getType());

        final ConfigDict addConfigDict = configDict;
        configTransactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {

                configDictMapper.addConfigDict(addConfigDict);

                // ZK version升级
                configDictVersionRegister.refresh(addConfigDict.getId());

                LOGGER.info("增加ConfigDict成功, configDict={}", addConfigDict);

                return null;
            }
        });
    }

    @Override
    public void updateConfigDict(ConfigDict configDict) {
        DistributeConfigDictUtils.validUpdateConfigDict(configDict);
        DistributeConfigDictUtils.validAddConfigDict(configDict);
        // 校验是否重复
        Map<String, Object> queryMap = QueryMapperUtils.toQueryMap();
        queryMap.put("configType", configDict.getConfigType());
        queryMap.put("key", configDict.getKey());
        queryMap.put("filterId", configDict.getId());
        Integer count = configDictMapper.listConfigDictCount(queryMap);
        if (count > 0) {
            LOGGER.warn("该配置已经存在,不允许重复修改配置!, configDict={}", configDict);
            throw new ConfigDictException(ConfigDictResult.CONFIG_DICT_TYPE_AND_KEY_EXIST);
        }

        updateConfigDictInTransaction(configDict);
    }

    @Override
    public void deleteConfigDict(long configDictId) {
        final long id = configDictId;
        configTransactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                configDictMapper.deleteConfigDict(id);

                // ZK version升级
                configDictVersionRegister.refresh(id);

                LOGGER.info("删除ConfigDict成功, configDictId={}", id);
                return null;
            }
        });
    }

    @Override
    public ConfigDict findConfigDictById(Long id) {
        if (id == null) {
            return null;
        }

        ConfigDict configDict = configDictMapper.findById(id);
        return configDict;
    }

    @Override
    public Paging<ConfigDict> findConfigDictByPage(ConfigDictVO configDictVO, PagingRequest pagingRequest) {
        // 查询请求参数转换
        ListAdapter<ConfigDictVO> listAdapter = ListAdapterUtil.<ConfigDictVO>convert2ListAdapter(pagingRequest);
        Map<String, Object> queryMap = listAdapter.convert(configDictVO);

        // 分页查询数量
        Integer count = configDictMapper.listConfigDictCount(queryMap);

        DefaultPaging<ConfigDict> paging = new DefaultPaging<ConfigDict>
                (pagingRequest.getPageNo(), pagingRequest.getPageSize(), count);
        if (count <= 0) {
            paging.setResult(new ArrayList<ConfigDict>(0));
            return paging;
        }

        listAdapter.setPageNo(paging.getCurrentPageNo());
        queryMap.putAll(listAdapter.convert(null));

        List<ConfigDict> configDicts = configDictMapper.listConfigDict(queryMap);
        if (CollectionUtils.isEmpty(configDicts)) {
            paging.setResult(new ArrayList<ConfigDict>(0));
            return paging;
        }

        paging.setResult(configDicts);
        return paging;
    }

    @Override
    public List<ConfigDict> listAllConfigDict() {
        PagingRequest request = new PagingRequest();
        request.setPageSize(Integer.MAX_VALUE);
        Paging<ConfigDict> paging = findConfigDictByPage(new ConfigDictVO(), request);
        return paging.getResult();
    }

    @Override
    public void updateConfigDictStatus(long id, ConfigDictValidFlagType flagType) {
        if (flagType == null) {
            LOGGER.warn("配置状态为空,更新失败!, id={}", id);
            throw new ConfigDictException(ConfigDictResult.CONFIG_DICT_VALID_FLAG_NULL);
        }

        // 构建update对象
        ConfigDict configDict = new ConfigDict();
        configDict.setId(id);
        configDict.setValidFlag(flagType.getType());

        updateConfigDictInTransaction(configDict);
    }

    /**
     * 事务中修改配置字典
     *
     * @param uptConfigDict
     */
    protected void updateConfigDictInTransaction(final ConfigDict uptConfigDict) {
        configTransactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {

                configDictMapper.updateConfigDict(uptConfigDict);

                // ZK version升级
                configDictVersionRegister.refresh(uptConfigDict.getId());

                LOGGER.info("更新ConfigDict成功, configDict={}", uptConfigDict);
                return null;
            }
        });
    }
}