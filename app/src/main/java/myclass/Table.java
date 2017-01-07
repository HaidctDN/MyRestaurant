package myclass;

import java.io.Serializable;

public class Table implements Serializable, Comparable<Table> {
    private int numberTable;
    private int capacity;
    private String colorTable;
    private String status;

    public Table() {
    }

    public Table(int numberTable, int capacity, String status) {
        this.numberTable = numberTable;
        this.status = status;
        this.capacity = capacity;
        if (status.equals("BT")) {
            this.colorTable = "mauxanh";
        } else if (status.equals("DD")) {
            this.colorTable = "mauvang";
        } else
            this.colorTable = "mauxam";
    }

    public void setTable(Table table) {
        this.numberTable = table.numberTable;
        this.status = table.status;
        this.capacity = table.capacity;
        this.colorTable = table.colorTable;
    }

    public int getNumberTable() {
        return numberTable;
    }

    public void setNumberTable(int numberTable) {
        this.numberTable = numberTable;
    }

    public String getColorTable() {
        return colorTable;
    }

    public void setColorTable(String colorTable) {
        this.colorTable = colorTable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Table:" + numberTable + " - Capacity:" + capacity
                + " - Status:" + status;
    }

    @Override
    public int compareTo(Table table) {
        if (this.getNumberTable() > table.getNumberTable())
            return 1;
        if (this.getNumberTable() < table.getNumberTable())
            return -1;
        return 0;
    }

}
