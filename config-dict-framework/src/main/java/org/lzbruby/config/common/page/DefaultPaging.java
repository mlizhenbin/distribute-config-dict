package org.lzbruby.config.common.page;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 功能描述：默认的分页对象
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.cn
 * company：org.lzbruby
 * Date: 15/9/9 Time: 10:50
 */
public class DefaultPaging<T> implements Paging<T> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3794190153745275134L;

    /**
     * 当前页数据信息
     */
    private List<T> result;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 当前页数
     */
    private int pageNo;

    /**
     * 总条数
     */
    private int totalCount = 0;

    public DefaultPaging(int pageNo, int pageSize, int totalCount) {
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        int lastPageNo = getLastPageNo();
        this.pageNo = (pageNo < lastPageNo ? pageNo : lastPageNo);
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public boolean isFirstPage() {
        return getCurrentPageNo() == 1;
    }

    public boolean isLastPage() {
        return getCurrentPageNo() >= getLastPageNo();
    }

    public boolean hasNextPage() {
        return getLastPageNo() > getCurrentPageNo();
    }

    public boolean hasPreviousPage() {
        return getCurrentPageNo() > 1;
    }

    public int getLastPageNo() {
        int lastPageNo = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize
                + 1;
        if (lastPageNo <= 1)
            lastPageNo = 1;
        return lastPageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCurrentPageFirstElementNo() {
        return (getCurrentPageNo() - 1) * getPageSize() + 1;
    }

    public int getCurrentPageLastElementNo() {
        int fullPage = getCurrentPageFirstElementNo() + getPageSize() - 1;
        return getTotalCount() < fullPage ? getTotalCount() : fullPage;
    }

    public int getNextPageNo() {
        return getCurrentPageNo() + 1;
    }

    public int getPreviousPageNo() {
        return getCurrentPageNo() - 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPageNo() {
        return pageNo;
    }

    /**
     * @see Iterable#iterator()
     */
    public Iterator<T> iterator() {
        return result == null ? Collections.<T>emptyList().iterator() : result.iterator();
    }
}
