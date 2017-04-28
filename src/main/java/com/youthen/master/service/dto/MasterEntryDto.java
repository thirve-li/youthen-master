package com.youthen.master.service.dto;

import com.youthen.framework.common.annotation.Dto;
import com.youthen.framework.service.dto.BaseDto;

@Dto
public abstract class MasterEntryDto extends BaseDto {

    private static final long serialVersionUID = -7751169917296105247L;

    // 第几页
    int gotoPage = 1;

    // 每页几条数据,默认15
    int pageSize = 15;

    // 总页数
    int pages;

    // 记录总条数
    int listSize;

    /**
     * getter for gotoPage.
     * 
     * @return gotoPage
     */
    public int getGotoPage() {

        if (this.gotoPage == 0) {
            this.gotoPage = 1;
        }

        return this.gotoPage;
    }

    /**
     * setter for gotoPage.
     * 
     * @param aGotoPage gotoPage
     */
    public void setGotoPage(final int aGotoPage) {
        this.gotoPage = aGotoPage;
    }

    /**
     * getter for pageSize.
     * 
     * @return pageSize
     */
    public int getPageSize() {
        if (this.pageSize == 0) {
            return 15;
        }
        return this.pageSize;
    }

    /**
     * setter for pageSize.
     * 
     * @param aPageSize pageSize
     */
    public void setPageSize(final int aPageSize) {
        this.pageSize = aPageSize;
    }

    /**
     * getter for pages.
     * 
     * @return pages
     */
    public int getPages() {
        return this.pages;
    }

    /**
     * setter for pages.
     * 
     * @param aPages pages
     */
    public void setPages(final int aPages) {
        this.pages = aPages;
    }

    /**
     * getter for listSize.
     * 
     * @return listSize
     */
    public int getListSize() {
        return this.listSize;
    }

    /**
     * setter for listSize.
     * 
     * @param aListSize listSize
     */
    public void setListSize(final int aListSize) {
        this.listSize = aListSize;
    }

}
