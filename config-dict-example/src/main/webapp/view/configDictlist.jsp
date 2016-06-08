<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html lang="zh-cn">
<%@ page isELIgnored="false" %>
<head>
    <title>配置管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="/public/js/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="/public/css/common.css" rel="stylesheet" >
    <script src="/public/js/vendor/jquery-1.9.1.js" type="text/javascript"></script>
    <script src="/public/js/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
    <script src="/public/js/plugin/underscore.js" type="text/javascript"></script>
    <script src="/public/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div id="dataListDiv">
    <div class="search-box">
        <span class="p-ladder-shaped-down">搜索区</span>

        <div id="search-form">
            <form id="queryForm" action="/config/findConfigDictByPage" method="POST">
                <input id="_pageNo" type="hidden" name="pageNo" value="${configDictVO.pageNo}">
                <input id="_pageSize" type="hidden" name="pageSize" value="${configDictVO.pageSize}">
                <table width="98%" border="0" cellspacing="0" cellpadding="0" class="noclick">
                    <tr>
                        <td align="right">配置类型：</td>
                        <td><input type="text" id="_configType" name="configTypeLike"
                                   value="${configDictVO.configTypeLike}"/>
                        </td>
                        <td align="right">配置KEY：</td>
                        <td><input type="text" id="_key" name="keyLike" value="${configDictVO.keyLike}"/></td>
                        <td align="right">配置值：</td>
                        <td><input type="text" id="_value" name="valueLike" value="${configDictVO.valueLike}"/></td>
                    </tr>
                    <tr>
                        <td align="right">配置ID：</td>
                        <td><input type="text" id="_id" name="id" value="${configDictVO.id}"/></td>
                        <td align="right">状态：</td>
                        <td>
                            <select id="_validFlag" name="validFlag" style="width: 130px">
                                <option value="">全部</option>
                                <c:forEach var="item" items="${configDictValidFlagType}">
                                    <option value="${item.key}"
                                            <c:if test="${configDictVO.validFlag==item.key}">selected="selected"</c:if> >${item.value}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td colspan="2">
                            <input class="l-button l-button-submit" type="submit" value="搜索" id="searchBtn"/>
                            <input class="l-button" type="button" value="重置" id="reset_query"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

    <div ligeruiid="toptoolbar" class="l-toolbar" id="toptoolbar">
        <div toolbarid="item-2" class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon" onclick="addConfigDict();">
            <div class="l-icon l-icon-add"></div>
            <span>新增</span>
        </div>
        <div toolbarid="item-1" class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon" onclick="refresh();">
            <div class="l-icon l-icon-refresh"></div>
            <span>刷新</span>
        </div>
        <%@include file="page.jsp" %>
    </div>

    <div class="in-order-list">
        <table id="" width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="p-table">
            <thead class="p-table-header">
            <tr class="p-table-header">
                <td width="30">ID</td>
                <td width="50">配置类型</td>
                <td width="100">配置KEY</td>
                <td width="230">配置值</td>
                <td width="230">描述</td>
                <td width="50">状态</td>
                <td width="30">排序</td>
                <td width="80">操作</td>
            </tr>
            </thead>
            <tbody id="dataListTbody">
            <c:choose>
                <c:when test="${paging.result != null && paging.result.size() > 0}">
                    <c:forEach items="${paging.result}" var="item">
                        <c:choose>
                            <c:when test="${item.validFlag == '0'}">
                                <tr style="color: red">
                            </c:when>
                            <c:when test="${item.validFlag == '2'}">
                                <tr style="color: blue;">
                            </c:when>
                            <c:otherwise>
                                <tr>
                            </c:otherwise>
                        </c:choose>
                        <td>${item.id}</td>
                        <td>${item.configType}</td>
                        <td style="text-align: left">${item.key}</td>
                        <td style="text-align: left">${item.value}</td>
                        <td style="text-align: left">${item.configDesc}</td>
                        <td>${configDictValidFlagType[item.validFlag]}</td>
                        <td>${item.orderId}</td>
                        <td>
                            <a href="javascript:;" onclick="updateConfigDict(${item.id});">修改</a>
                            <c:if test="${item.validFlag == '2'}">
                                <a href="javascript:;" onclick="updateStatus(${item.id}, '1', '审核');">审核</a>
                            </c:if>
                            <c:if test="${item.validFlag == '0'}">
                                <a href="javascript:;" onclick="updateStatus(${item.id}, '1', '启用');">启用</a>
                            </c:if>
                            <c:if test="${item.validFlag == '1'}">
                                <a href="javascript:;" onclick="updateStatus(${item.id}, '0', '停用');">停用</a>
                            </c:if>
                            <a href="javascript:;" onclick="deleteConfig(${item.id});">删除</a>
                        </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="8">暂无配置字典数据</td>
                    </tr>
                </c:otherwise>
            </c:choose>

            </tbody>
        </table>
    </div>

    <%--引入分页--%>
    <%@include file="page.jsp" %>
</div>
</body>

<script type="application/javascript">

    // 初始化
    $("#searchBtn").click(function () {
        $("#queryForm").submit();
    });

    // 查询跳转
    function goToQueryPage(pageNo) {
        $("#_pageNo").val(pageNo);
        $("#queryForm").submit();
    }

    // 删除配置
    function deleteConfig(id) {
        $.ligerDialog.confirm('确定删除配置字典吗？', function (yes) {
            if (yes) {
                $.ajax({
                    url: '/config/deleteConfigDict',
                    async: false,
                    type: 'POST',
                    data: {
                        id: id
                    },
                    dataType: 'json',
                    success: function (json) {
                        if (json.ret == 1) {
                            alert("删除配置字典成功!");
                            $("#queryForm").submit();
                        } else {
                            $.ligerDialog.error("删除配置字典失败!, errorCode=" + json.errMsg);
                        }
                    },
                    error: function () {
                        $.ligerDialog.error('删除时，网络连接出错!');
                    }
                });
            }
        })
    }

    // 刷新配置
    function refresh() {
        $.ligerDialog.confirm('确定刷新配置字典吗？', function (yes) {
            if (yes) {
                $.ajax({
                    url: '/config/refreshConfigLoaderContext',
                    async: false,
                    type: 'POST',
                    dataType: 'json',
                    success: function (json) {
                        if (json.ret == 1) {
                            alert("刷新配置字典成功!");
                            $("#queryForm").submit();
                        } else {
                            $.ligerDialog.error("刷新配置字典失败!, errorCode=" + json.errMsg);
                        }
                    },
                    error: function () {
                        $.ligerDialog.error('刷新时，网络连接出错!');
                    }
                });
            }
        })
    }

    // 修改订单状态
    function updateStatus(id, validFlag, msg) {
        $.ligerDialog.confirm('确定该条' + msg + '配置字典记录吗？', function (yes) {
            $.ajax({
                url: '/config/updateConfigDictStatus',
                async: false,
                type: 'POST',
                data: {
                    id: id,
                    status: validFlag
                },
                dataType: 'json',
                success: function (json) {
                    if (json.ret == 1) {
                        alert(msg + "配置字典成功!");
                        $("#queryForm").submit();
                    } else {
                        $.ligerDialog.error(msg + "配置字典失败!, errorCode=" + json.errMsg);
                    }
                },
                error: function () {
                    $.ligerDialog.error(msg + '时，网络连接出错!');
                }
            });
        })
    }

    // 重置查询条件
    $("#reset_query").click(function () {
        $("#_id").val('');
        $("#_key").val('');
        $("#_value").val('');
        $("#_validFlag").val('');
        $("#_configType").val('');
    })

    $(function () {
        $("#checkAll").prop("checked", false);
        $(".checkMe").prop("checked", false);
        $("#checkAll").live('click', function () {
            if (this.checked) {
                $("input[name='checkList']").prop("checked", true);
            } else {
                $("input[name='checkList']").prop("checked", false);
            }
        });
    });

    // 增加配置
    function addConfigDict() {
        $.ligerDialog.open({
            url: '../config/initConfigDict',
            height: 400,
            width: 600,
            title: '增加配置字典'
        })
    }

    // 修改配置
    function updateConfigDict(id) {
        $.ligerDialog.open({
            url: '../config/initConfigDict?id=' + id,
            height: 400,
            width: 600,
            title: '修改配置字典'
        })
    }

    // 关闭弹出框
    function closeDialog() {
        $.ligerDialog.close();
        $(".l-dialog,.l-window-mask").css("display", "none");
        $("#queryForm").submit();
    }

</script>
</html>
