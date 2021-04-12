package com.testtask.filecomparison;

import difflib.Delta;

import java.util.Objects;

public class ResultComparison {
    private int position;
    private Delta.TYPE type;
    private String line;

    public void setPosition(int position) {
        this.position = position;
    }

    public void setType(Delta.TYPE type) {
        this.type = type;
    }

    public void setLine(String line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return position + " | "
                + type + " | "
                + line;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultComparison that = (ResultComparison) o;
        return position == that.position && type == that.type && Objects.equals(line, that.line);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, type, line);
    }
}
