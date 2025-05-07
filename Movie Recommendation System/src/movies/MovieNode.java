package movies;

public class MovieNode {
    private final ComparableMovie data;
    private MovieNode next;
    private MovieNode prev;

    public MovieNode(ComparableMovie data) {
        this.data = data;
    }

    public ComparableMovie getData() {
        return data;
    }

    public MovieNode getNext() {
        return next;
    }

    public void setNext(MovieNode next) {
        this.next = next;
    }

    public MovieNode getPrev() {
        return prev;
    }

    public void setPrev(MovieNode prev) {
        this.prev = prev;
    }
}