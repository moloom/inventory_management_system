package com.mo.pojo;

public class Page {

    private Integer totalCount;
    private Integer currentPageNo;
    private Integer totalPageCount;
    private Integer aheadStart;
    private Integer aheadEnd;
    private Integer afterStart;
    private Integer afterEnd;
    private Integer viewNum = 10;//显示记录条数
    private Integer sqlSelectPageStart;

    public Integer getTotalCount() {
        return totalCount;
    }

    public Page() {
    }

    //根据前端传过来的页数，计算sql中的start变量的值 和页数
    public Page(Integer sqlSelectPageStart) {
        this.sqlSelectPageStart = CalculatePageIndex(sqlSelectPageStart);
        this.setCurrentPageNo(this.sqlSelectPageStart + 1);
    }

    /**
     * 此值为sql 查询时，页数起始值
     *
     * @param sqlSelectPageStart
     * @return
     */
    private Integer CalculatePageIndex(Integer sqlSelectPageStart) {
        if (sqlSelectPageStart == null || sqlSelectPageStart == 0) return 0;
        return sqlSelectPageStart *= 10;
    }

    // 当设置多少条记录时，计算有几页，并保存到totalPageCount
    public void setTotalCount(Integer totalCount) {
        if (totalCount != null) {
            this.totalCount = totalCount;
            if (totalCount > viewNum) {
                if (totalCount % viewNum == 0) {
                    this.totalPageCount = totalCount / viewNum;
                } else {
                    this.totalPageCount = (totalCount / viewNum) + 1;
                }
                /*因修改翻页样式需要而附加的 */
                if (totalPageCount > 6) {
                    if (currentPageNo > 2) {
                        aheadStart = currentPageNo - 2;
                        aheadEnd = currentPageNo - 1;
                        if (currentPageNo + 3 > totalPageCount) {
                            afterEnd = totalPageCount;
                        } else {
                            afterEnd = currentPageNo + 3;
                        }
                        if (currentPageNo + 1 > totalPageCount) {
                            afterStart = totalPageCount;
                        } else {
                            afterStart = currentPageNo + 1;
                        }
                    } else {
                        aheadStart = 1;
                        if (currentPageNo - 1 > 0) {
                            aheadEnd = currentPageNo - 1;
                        } else {
                            aheadEnd = currentPageNo;
                        }
                        afterStart = currentPageNo + 1;
                        if (currentPageNo + 3 < totalPageCount) {
                            afterEnd = currentPageNo + 3;
                        } else {
                            afterEnd = totalPageCount;
                        }
                    }
                } else {
                    if (currentPageNo - 2 > 0) {
                        aheadStart = currentPageNo;
                    } else {
                        aheadStart = 1;
                    }
                    if (currentPageNo - 1 > 0) {
                        aheadEnd = currentPageNo - 1;
                    } else {
                        aheadEnd = 1;
                    }
                    afterStart = currentPageNo + 1;
                    afterEnd = totalPageCount;
                }
                /*end*/
            } else if (totalCount <= viewNum && totalCount > 0) {
                this.totalPageCount = 1;
                aheadStart = 1;
                aheadEnd = 1;
                afterStart = 1;
                afterEnd = 1;
            }
            return;
        }
        this.totalPageCount = 0;
        aheadStart = 0;
        aheadEnd = 0;
        afterStart = 0;
        afterEnd = 0;
        currentPageNo = 0;
        this.totalCount = 0;
    }

    public Integer getSqlSelectPageStart() {
        return sqlSelectPageStart;
    }

    public Integer getAheadStart() {
        return aheadStart;
    }

    public void setAheadStart(Integer aheadStart) {
        this.aheadStart = aheadStart;
    }

    public Integer getAheadEnd() {
        return aheadEnd;
    }

    public void setAheadEnd(Integer aheadEnd) {
        this.aheadEnd = aheadEnd;
    }

    public Integer getAfterStart() {
        return afterStart;
    }

    public void setAfterStart(Integer afterStart) {
        this.afterStart = afterStart;
    }

    public Integer getAfterEnd() {
        return afterEnd;
    }

    public void setAfterEnd(Integer afterEnd) {
        this.afterEnd = afterEnd;
    }

    public Integer getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(Integer currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public Integer getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(Integer totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    @Override
    public String toString() {
        return "Page{" +
                "totalCount=" + totalCount +
                ", currentPageNo=" + currentPageNo +
                ", totalPageCount=" + totalPageCount +
                ", aheadStart=" + aheadStart +
                ", aheadEnd=" + aheadEnd +
                ", afterStart=" + afterStart +
                ", afterEnd=" + afterEnd +
                ", viewNum=" + viewNum +
                "sqlSelectPageStart=" + sqlSelectPageStart +
                '}';
    }
}
