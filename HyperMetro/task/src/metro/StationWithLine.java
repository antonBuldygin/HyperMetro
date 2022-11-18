package metro;

import java.util.Arrays;

public class StationWithLine {
    private Junction[] transfer;
    private String name;
    private String line;
    private Integer time;

    private String [] prev;

    private String [] next;


    public StationWithLine(Junction[] transfer, String name, String line, Integer time, String[] prev, String[] next) {
        this.transfer = transfer;
        this.name = name;
        this.line = line;
        this.time = time;
        this.prev = prev;
        this.next = next;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }



    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public StationWithLine(String name, String line, Junction[] transfer, Integer time) {
        this.name = name;
        this.line = line;
        this.transfer = transfer;
        this.time = time;
    }

    public StationWithLine(String name) {
        this.name = name;
        this.transfer = transfer;
    }

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

    public StationWithLine() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Junction[] getTransfer() {
        return transfer;
    }

    public void setTransfer(Junction[] transfer) {
        this.transfer = transfer;
    }

    @Override
    public String toString() {
        return "StationWithLine{" +
                "transfer=" + Arrays.toString(transfer) +
                ", name='" + name + '\'' +
                ", line='" + line + '\'' +
                ", time=" + time +
                '}';
    }
}
