package metro;

import java.util.Arrays;

public class Station {
    private Integer time;
    private String name;

    private String [] prev;

    private String [] next;

    private Junction [] transfer;

    public String[] getPrev() {
        return prev;
    }

    public void setPrev(String[] prev) {
        this.prev = prev;
    }

    public String[] getNext() {
        return next;
    }

    public void setNext(String[] next) {
        this.next = next;
    }

    public Station(Integer time, String name, String[] prev, String[] next, Junction[] transfer) {
        this.time = time;
        this.name = name;
        this.prev = prev;
        this.next = next;
        this.transfer = transfer;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Station(String name, Junction [] transfer, Integer time) {
        this.name = name;
        this.transfer = transfer;
        this.time =time;
    }

    public Station(String name) {
        this.name = name;
        this.transfer = transfer;
    }

    public Station() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Junction [] getTransfer() {
        return transfer;
    }

    public void setTransfer(Junction []transfer) {
        this.transfer = transfer;
    }

    @Override
    public String toString() {
        return "Station{" +
                "time=" + time +
                ", name='" + name + '\'' +
                ", prev=" + Arrays.toString(prev) +
                ", next=" + Arrays.toString(next) +
                ", transfer=" + Arrays.toString(transfer) +
                '}';
    }
}
