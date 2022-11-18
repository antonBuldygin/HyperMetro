package metro;

public class Junction {
   private String line;
   private String station;

    public Junction(String line, String station) {
        this.line = line;
        this.station = station;
    }

    public Junction() {
    }

    public String getLine() {
        return line;
    }


    public void setLine(String line) {
        this.line = line;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "{" +
                "line='" + line + '\'' +
                ", station='" + station + '\'' +
                '}';
    }
}
