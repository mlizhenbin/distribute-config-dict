package org.lzbruby.web;

import org.apache.log4j.Logger;
import org.lzbruby.config.common.Result;
import org.lzbruby.config.common.page.Paging;
import org.lzbruby.config.common.page.PagingRequest;
import org.lzbruby.config.common.utils.JacksonUtil;
import org.lzbruby.config.core.ConfigDictVersionRegister;
import org.lzbruby.config.dao.model.ConfigDict;
import org.lzbruby.config.domain.ConfigDictVO;
import org.lzbruby.config.domain.ConfigDictValidFlagType;
import org.lzbruby.config.service.ConfigDictService;
import org.lzbruby.config.service.result.ConfigDictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * 功能描述: ConfigDict控制器
 *
 * @author: lizhenbin
 * email: lizhenbin08@sina.cn
 * company: org.lzbruby
 * Date: 2016/06/02 Time: 11:11:53
 */
@Controller
public class ConfigDictController {

    /**
     * sl4j
     */
    private Logger LOGGER = Logger.getLogger(ConfigDictController.class);

    @Autowired
    private ConfigDictService configDictService;

    @Autowired
    private ConfigDictVersionRegister configDictVersionRegister;

    /**
     * 分页查询ConfigDict
     *
     * @param configDictVO
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/config/findConfigDictByPage", method = {RequestMethod.GET, RequestMethod.POST})
    public String findConfigDictByPage(ConfigDictVO configDictVO, ModelMap modelMap) {
        // 分页查询请求
        PagingRequest pagingRequest = new PagingRequest();
        pagingRequest.setPageNo(configDictVO.getPageNo());
        pagingRequest.setPageSize(configDictVO.getPageSize());
        pagingRequest.setOrderItem("id");
        pagingRequest.setOrderType(" DESC,order_id ASC");

        // 分页查询
        Paging<ConfigDict> paging = configDictService.findConfigDictByPage(configDictVO, pagingRequest);
        modelMap.addAttribute("paging", paging);
        modelMap.addAttribute("configDictVO", configDictVO);
        modelMap.addAttribute("configDictValidFlagType", ConfigDictValidFlagType.buildConfigDictValidFlagTypeMap());

        return "/view/configDictlist";
    }

    /**
     * 增加ConfigDict
     *
     * @param configDictVO
     * @return
     */
    @RequestMapping(value = "/config/addConfigDict", method = RequestMethod.POST)
    @ResponseBody
    public Object addConfigDict(@Valid ConfigDictVO configDictVO) {
        ConfigDict configDict = assemblyConfigDict(configDictVO);
        try {
            configDictService.addConfigDict(configDict);
            LOGGER.info("增加configDict成功, configDict=" + configDict);
        } catch (ConfigDictException ex) {
            return JacksonUtil.beanToJson(new Result(Result.FAIL, "增加configDict失败, error=" + ex.getConfigDictResult().getType()));
        } catch (Exception ex) {
            LOGGER.warn("增加configDict异常, configDict=" + configDict, ex);
            return JacksonUtil.beanToJson(new Result(Result.FAIL, "系统异常! error=" + ex.getMessage()));
        }

        return JacksonUtil.beanToJson(new Result(Result.SUCCESS, "success"));
    }

    /**
     * 修改ConfigDict
     *
     * @param configDictVO
     * @return
     */
    @RequestMapping(value = "/config/updateConfigDict", method = RequestMethod.POST)
    @ResponseBody
    public Object updateConfigDict(ConfigDictVO configDictVO) {
        ConfigDict configDict = assemblyConfigDict(configDictVO);
        try {
            configDictService.updateConfigDict(configDict);
            LOGGER.info("修改configDict成功, configDict=" + configDict);
        } catch (ConfigDictException ex) {
            LOGGER.warn("修改configDict失败, configDict=" + configDict + ", errorCode=" + ex.getConfigDictResult());
            return JacksonUtil.beanToJson(new Result(Result.FAIL, "修改configDict失败, error=" + ex.getConfigDictResult().getType()));
        } catch (Exception ex) {
            LOGGER.warn("修改configDict异常, configDict=" + configDict, ex);
            return JacksonUtil.beanToJson(new Result(Result.FAIL, "系统异常! error=" + ex.getMessage()));
        }

        return JacksonUtil.beanToJson(new Result(Result.SUCCESS, "success"));
    }

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/config/updateConfigDictStatus", method = RequestMethod.POST)
    @ResponseBody
    public Object updateConfigDictStatus(@RequestParam Long id, @RequestParam String status) {
        try {
            configDictService.updateConfigDictStatus(id, ConfigDictValidFlagType.getByType(status));
            LOGGER.info("修改configDict状态成功, id=" + id + ", status=" + status);
        } catch (ConfigDictException ex) {
            LOGGER.warn("修改configDict状态失败, id=" + id + ", status=" + status + ", errorCode=" + ex.getConfigDictResult());
            return JacksonUtil.beanToJson(new Result(Result.FAIL, "修改configDict失败, error=" + ex.getConfigDictResult().getType()));
        } catch (Exception ex) {
            LOGGER.warn("修改configDict状态异常, id=" + id + ", status=" + status, ex);
            return JacksonUtil.beanToJson(new Result(Result.FAIL, "系统异常! error=" + ex.getMessage()));
        }

        return JacksonUtil.beanToJson(new Result(Result.SUCCESS, "success"));
    }

    /**
     * 删除配置ConfigDict
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/config/deleteConfigDict", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteConfigDict(@RequestParam Long id) {
        try {
            configDictService.deleteConfigDict(id);
            LOGGER.info("删除configDict成功, configDictId=" + id);
        } catch (ConfigDictException ex) {
            LOGGER.warn("删除configDict失败, configDictId=" + id + ", errorCode=" + ex.getConfigDictResult());
            return JacksonUtil.beanToJson(new Result(Result.FAIL, "删除configDict失败, error=" + ex.getConfigDictResult().getType()));
        } catch (Exception ex) {
            LOGGER.warn("删除configDict异常, configDictId=" + id, ex);
            return JacksonUtil.beanToJson(new Result(Result.FAIL, "系统异常, error=" + ex.getMessage()));
        }
        return JacksonUtil.beanToJson(new Result(Result.SUCCESS, "success"));
    }

    /**
     * 刷新WMS数据库配置文件Config数据字典内容
     *
     * @return
     */
    @RequestMapping(value = "/config/refreshConfigLoaderContext", method = RequestMethod.POST)
    @ResponseBody
    public Object refreshConfigLoaderContext() {
        configDictVersionRegister.refresh();
        LOGGER.info("手动刷新ConfigDict成功!");
        return JacksonUtil.beanToJson(new Result(Result.SUCCESS, "手动刷新ConfigDict成功!"));
    }

    /**
     * 加载创建配置文件页面
     *
     * @return
     */
    @RequestMapping(value = "/config/initConfigDict", method = RequestMethod.GET)
    public String initConfigDict(Long id, ModelMap modelMap) {
        if (id != null) {
            ConfigDictVO configDictVO = new ConfigDictVO();
            configDictVO.setId(id);
            Paging<ConfigDict> paging = configDictService.findConfigDictByPage(configDictVO, new PagingRequest());
            modelMap.addAttribute("configDict", paging.getResult().get(0));
        }
        return "/view/configDictDetail";
    }

    /**
     * 组装配置字典对象
     *
     * @param configDictVO
     * @return
     */
    private static ConfigDict assemblyConfigDict(ConfigDictVO configDictVO) {
        if (configDictVO == null) {
            return null;
        }

        ConfigDict configDict = new ConfigDict();
        configDict.setId(configDictVO.getId());
        configDict.setConfigType(configDictVO.getConfigType());
        configDict.setKey(configDictVO.getKey());
        configDict.setValue(configDictVO.getValue());
        configDict.setConfigDesc(configDictVO.getConfigDesc());
        configDict.setValidFlag(configDictVO.getValidFlag());
        configDict.setOrderId(configDictVO.getOrderId());
        return configDict;
    }
}