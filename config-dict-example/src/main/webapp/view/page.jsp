<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div class="l-bar-group p-page-btn">
    <div class="l-bar-group p-page-other pageStatus">
        总 <span>${paging.totalCount}</span> 条。每页显示：<em>${paging.pageSize}</em>
    </div>
    <div class="l-bar-group p-page-btn">
        <div class="l-bar-group l-bar-selectpagesize">
            <select name="rp" class="_page_select_pagesize">
                <option value="5"
                        <c:if test="${paging.pageSize==5}">selected="selected"</c:if> >5
                </option>
                <option value="10"
                        <c:if test="${paging.pageSize==10}">selected="selected"</c:if> >10
                </option>
                <option value="20"
                        <c:if test="${paging.pageSize==20}">selected="selected"</c:if> >20
                </option>
                <option value="30"
                        <c:if test="${paging.pageSize==30}">selected="selected"</c:if> >30
                </option>
                <option value="40"
                        <c:if test="${paging.pageSize==40}">selected="selected"</c:if> >40
                </option>
                <option value="50"
                        <c:if test="${paging.pageSize==50}">selected="selected"</c:if> >50
                </option>
            </select>
        </div>
        <div class="l-bar-separator"></div>
        <div class="l-bar-group">
            <div class="l-bar-button l-bar-btnfirst">
                <span class="l-disabled" onclick="goToQueryPage(1)"></span>
            </div>
            <div class="l-bar-button l-bar-btnprev"
                 onclick="goToQueryPage(
                 <c:choose>
                     <c:when test="${paging.currentPageNo > 1}">${paging.currentPageNo -1}</c:when>
                 <c:otherwise>1</c:otherwise>
                 </c:choose>)">
                <span class="l-disabled"></span>
            </div>
        </div>
        <div class="l-bar-separator"></div>
        <div class="l-bar-group">
        <span class="pcontrol">
            <input class="_page_goto_query" title="输入页码后按回车键" type="text" maxlength="4" style="width: 40px"
                   value="${paging.getCurrentPageNo()}" size="4">
            / <span>${paging.getLastPageNo()}</span>
        </span>
        </div>
        <div class="l-bar-separator"></div>
        <div class="l-bar-group">
            <div class="l-bar-button l-bar-btnnext">
                <span class="l-disabled" onclick="goToQueryPage(
                    <c:choose>
                        <c:when test="${paging.currentPageNo != paging.totalCount}">${paging.nextPageNo}</c:when>
                    <c:otherwise>${paging.lastPageNo}</c:otherwise>
                    </c:choose>)"></span>
            </div>
            <div class="l-bar-button l-bar-btnlast">
                <span class="l-disabled" onclick="goToQueryPage(${paging.lastPageNo})"></span>
            </div>
            <div class="l-bar-button l-bar-btnload" onclick="goToQueryPage(${paging.currentPageNo})">
                <span class=""></span>
            </div>
        </div>
    </div>
</div>

<%-- JSP分页公用JS事件--%>
<script type="text/javascript">
    // 回车执行查询
    $("._page_goto_query").keydown(function (event) {
        if (event.keyCode == 13) {
            var goToNum = $(this).val();
            goToQueryPage(goToNum);
        }
    });

    // 修改查询的分页数量
    $("._page_select_pagesize").change(function () {
        var selectSize = $(this).val();
        $("#_pageSize").val(selectSize);

        var select = $(this).parent('div').parent('div');
        var goToNum = select.find('._page_goto_query').val();
        goToQueryPage(goToNum);
    })
</script>