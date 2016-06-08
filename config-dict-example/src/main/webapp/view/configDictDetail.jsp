<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="zh-cn">
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>配置管理</title>
</head>
<body class="wms">
<div id="dataListDiv">
    <div class="search-box">
        <span class="p-ladder-shaped-down">配置管理</span>

        <form id="addConfigDictForm" onsubmit="return false">
            <input type="hidden" id="id" name="id" value="${configDict.id}">
            <table width="99%" border="0" cellspacing="0" cellpadding="0" class="noclick" style="text-align: center">
                <tbody>
                <tr>
                    <td align="right" width="25%">配置类型：<span style="color: red">*</span></td>
                    <td align="left" width="75%">
                        <input type="text" id="configType" name="configType" style="width: 350px" value="${configDict.configType}">
                    </td>
                </tr>
                <tr>
                    <td align="right">配置KEY：<span style="color: red">*</span></td>
                    <td align="left">
                        <input type="text" id="key" name="key"style="width: 350px"  value="${configDict.key}">
                    </td>
                </tr>
                <tr>
                    <td align="right">配置值：<span style="color: red">*</span></td>
                    <td align="left">
                        <input type="text" id="value" name="value" style="width: 350px"  value="${configDict.value}">
                    </td>
                </tr>
                <tr>
                    <td align="right">描述：<span style="color: red">*</span></td>
                    <td align="left">
                        <input type="text" id="configDesc" name="configDesc" style="width: 350px"  value="${configDict.configDesc}">
                    </td>
                </tr>
                <tr>
                    <td align="right">排序：<span style="color: red">*</span></td>
                    <td align="left">
                        <input type="text" id="orderId" name="orderId" style="width: 150px"  value="${configDict.orderId}"><span
                            style="color: red">&nbsp;&nbsp;值越大排序靠前</span>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>

        <div style="height: 50px"></div>
        <div style="text-align: center">
            <input class="l-button" type="reset" value="确认" onclick="submitAddConfig();"/>
            <input class="l-button" type="button" value="关闭" onclick="javascript:parent.closeDialog()"/>
        </div>

        <div style="height: 70px"></div>
    </div>
</div>
</body>

<script type="application/javascript">

    function submitAddConfig() {
        var configType = $("#configType").val();
        var key = $("#key").val();
        var value = $("#value").val();
        var configDesc = $("#configDesc").val();
        var orderId = $("#orderId").val();

        if (!configType) {
            $.ligerDialog.alert("配置类型不允许为空!");
            return;
        }

        if (!key) {
            $.ligerDialog.alert("配置KEY不允许为空!");
            return;
        }

        if (!value) {
            $.ligerDialog.alert("配置值不允许为空!");
            return;
        }

        if (!configDesc) {
            $.ligerDialog.alert("配置描述不允许为空!");
            return;
        }

        if (!orderId) {
            $.ligerDialog.alert("配置排序不允许为空!");
            return;
        }

        if (isNaN(orderId)) {
            $.ligerDialog.alert("配置排序必须填写数字!");
            return;
        }

        var id = $("#id").val();
        if (id) {
            $.ligerDialog.confirm('确定修改配置字典吗？', function (yes) {
                if (yes) {
                    $.ajax({
                        url: '/config/updateConfigDict',
                        async: false,
                        type: 'POST',
                        data: {
                            id: id,
                            configType: configType,
                            key: key,
                            value: value,
                            configDesc: configDesc,
                            orderId: orderId
                        },
                        dataType: 'json',
                        success: function (json) {
                            if (json.ret == 1) {
                                alert("修改配置字典成功!");
                                javascript:parent.closeDialog()
                            } else {
                                $.ligerDialog.error("修改配置字典失败!, errorCode=" + json.errMsg);
                            }
                        },
                        error: function () {
                            $.ligerDialog.error('修改时，网络连接出错!');
                        }
                    });
                }
            });
        } else {
            $.ligerDialog.confirm('确定增加配置字典吗？', function (yes) {
                if (yes) {
                    $.ajax({
                        url: '/config/addConfigDict',
                        async: false,
                        type: 'POST',
                        data: {
                            configType: configType,
                            key: key,
                            value: value,
                            configDesc: configDesc,
                            orderId: orderId
                        },
                        dataType: 'json',
                        success: function (json) {
                            if (json.ret == 1) {
                                alert("增加配置字典成功!");
                                javascript:parent.closeDialog()
                            } else {
                                $.ligerDialog.error("增加配置字典失败!, errorCode=" + json.errMsg);
                            }
                        },
                        error: function () {
                            $.ligerDialog.error('增加时，网络连接出错!');
                        }
                    });
                }
            });
        }
    }

</script>
</html>
