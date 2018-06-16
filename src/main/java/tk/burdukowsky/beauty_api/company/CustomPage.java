package tk.burdukowsky.beauty_api.company;

import java.util.List;

public class CustomPage<T> {
    private List<T> data;
    private long size;
    private long totalElements;
    private long totalPages;
    private long number;

    CustomPage(List<T> data, long size, long totalElements, long totalPages, long number) {
        this.data = data;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.number = number;
    }

    public List<T> getData() {
        return data;
    }

    public long getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public long getNumber() {
        return number;
    }
}
