package com.joker.model.dto;

import com.joker.model.TableHistory;

import java.util.List;

public class HistoryResponse {
    private long tableId;

    private String name1;
    private String name2;
    private String name3;
    private String name4;

    private double score1;
    private double score2;
    private double score3;
    private double score4;

    public HistoryResponse(TableHistory history) {
        this.score1 = history.getScore1();
        this.score2 = history.getScore2();
        this.score3 = history.getScore3();
        this.score4 = history.getScore4();
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getName4() {
        return name4;
    }

    public void setName4(String name4) {
        this.name4 = name4;
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
}
