/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Lenovo
 */
public class Pagination {

    private int totalRecords;
    private int currentPage;
    private int pageSize;
    private int maxPageNumbersToShow;

    private int totalPages;
    private int startPage;
    private int endPage;

    public Pagination(int totalRecords, int currentPage, int pageSize, int maxPageNumbersToShow) {
        this.totalRecords = totalRecords;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.maxPageNumbersToShow = maxPageNumbersToShow;

        this.totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        int start, end;
        if (this.totalPages <= maxPageNumbersToShow) {
            start = 1;
            end = this.totalPages;
        } else {
            int pagesBeforeCurrent = maxPageNumbersToShow / 2;
            int pagesAfterCurrent = maxPageNumbersToShow - pagesBeforeCurrent - 1;

            if (currentPage <= pagesBeforeCurrent) {
                start = 1;
                end = maxPageNumbersToShow;
            } else if (currentPage + pagesAfterCurrent >= this.totalPages) {
                start = this.totalPages - maxPageNumbersToShow + 1;
                end = this.totalPages;
            } else {
                start = currentPage - pagesBeforeCurrent;
                end = currentPage + pagesAfterCurrent;
            }
        }
        this.startPage = start;
        this.endPage = end;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public boolean hasPrevious() {
        return currentPage > 1;
    }

    public boolean hasNext() {
        return currentPage < totalPages;
    }
}
