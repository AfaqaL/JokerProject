package com.joker.model;

public class TableHistory {
    private long tableId;
    private long id1, id2, id3, id4;
    private double score1, score2, score3, score4;

    public TableHistory() {}

    public TableHistory(long tableId, long id1, double score1,
                        long id2, double score2,
                        long id3, double score3,
                        long id4, double score4) {
        this.tableId = tableId;

        this.id1 = id1;
        this.id2 = id2;
        this.id3 = id3;
        this.id4 = id4;

        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.score4 = score4;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public long getId1() {
        return id1;
    }

    public void setId1(long id1) {
        this.id1 = id1;
    }

    public long getId2() {
        return id2;
    }

    public void setId2(long id2) {
        this.id2 = id2;
    }

    public long getId3() {
        return id3;
    }

    public void setId3(long id3) {
        this.id3 = id3;
    }

    public long getId4() {
        return id4;
    }

    public void setId4(long id4) {
        this.id4 = id4;
    }

    public double getScore1() {
        return score1;
    }

    public void setScore1(double score1) {
        this.score1 = score1;
    }

    public double getScore2() {
        return score2;
    }

    public void setScore2(double score2) {
        this.score2 = score2;
    }

    public double getScore3() {
        return score3;
    }

    public void setScore3(double score3) {
        this.score3 = score3;
    }

    public double getScore4() {
        return score4;
    }

    public void setScore4(double score4) {
        this.score4 = score4;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof TableHistory)) return false;

        TableHistory other = (TableHistory) obj;

        if (this.tableId != other.tableId) return false;
        if (this.id1 != other.id1) return false;
        if (this.id2 != other.id2) return false;
        if (this.id3 != other.id3) return false;
        if (this.id4 != other.id4) return false;

        if (this.score1 != other.score1) return false;
        if (this.score2 != other.score2) return false;
        if (this.score3 != other.score3) return false;
        if (this.score4 != other.score4) return false;

        return true;
    }
}
