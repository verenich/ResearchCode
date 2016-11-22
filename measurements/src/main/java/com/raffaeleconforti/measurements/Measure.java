package com.raffaeleconforti.measurements;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adriano on 22/11/2016.
 */
public class Measure {
    private Map<String, String> measures;
    private double value;

    public Measure() {
        measures = new HashMap<>();
        value = Double.NaN;
    }

    public Measure(double value) {
        measures = new HashMap<>();
        setValue(value);
    }

    public Measure(String metric, String value) {
        measures = new HashMap<>();
        measures.put(metric, value);
        try { this.value = Double.valueOf(value); }
        catch( NumberFormatException nfe ) { this.value = Double.NaN; }
    }

    public Measure(String metric, double value) {
        measures = new HashMap<>();
        measures.put(metric, Double.toString(value));
        this.value = value;
    }

    public void addMeasure(String metric, String value) {
        try { if( this.value == Double.NaN ) this.value = Double.valueOf(value); }
        catch( NumberFormatException nfe ) { this.value = Double.NaN; }
        measures.put(metric, value);
    }

    public void addMeasure(String metric, double value) {
        if( this.value == Double.NaN ) this.value = value;
        measures.put(metric, Double.toString(value));
    }

    public void setValue(double value) {
        this.value = value;
        addMeasure("", value);
    }

    public double getValue() { return this.value; }
    public Map<String, String> getMeasures() { return measures; }

}
