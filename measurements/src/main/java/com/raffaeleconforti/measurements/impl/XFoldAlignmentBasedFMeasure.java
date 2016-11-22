package com.raffaeleconforti.measurements.impl;

import com.raffaeleconforti.measurements.Measure;
import com.raffaeleconforti.measurements.MeasurementAlgorithm;
import com.raffaeleconforti.wrapper.MiningAlgorithm;
import com.raffaeleconforti.wrapper.PetrinetWithMarking;
import org.deckfour.xes.classification.XEventClassifier;
import org.deckfour.xes.factory.XFactory;
import org.deckfour.xes.factory.XFactoryNaiveImpl;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.processmining.contexts.uitopia.UIPluginContext;

import java.util.Random;

/**
 * Created by Adriano on 23/11/2016.
 */
public class XFoldAlignmentBasedFMeasure implements MeasurementAlgorithm {

    private int fold = 3;
    private XFactory factory = new XFactoryNaiveImpl();
    private XLog log;
    private Random r = new Random(123456789);

    @Override
    public boolean isMultimetrics() { return true; }

    @Override
    public Measure computeMeasurement(UIPluginContext pluginContext, XEventClassifier xEventClassifier, PetrinetWithMarking petrinetWithMarking, MiningAlgorithm miningAlgorithm, XLog log) {
        Measure measure = new Measure();
        double precision = 0.0;
        double fitness = 0.0;
        double f_measure;
        this.log = log;
        XLog[] logs = createdXFolds();

        AlignmentBasedFitness alignmentBasedFitness = new AlignmentBasedFitness();
        AlignmentBasedPrecision alignmentBasedPrecision = new AlignmentBasedPrecision();

        for(int i = 0; i < fold; i++) {
            XLog log1 = factory.createLog(log.getAttributes());
            for (int j = 0; j < fold; j++) {
                if (j != i) {
                    log1.addAll(logs[j]);
                }
            }

            try {
                petrinetWithMarking = miningAlgorithm.minePetrinet(pluginContext, logs[i], false);

                Double f = (alignmentBasedFitness.computeMeasurement(pluginContext, xEventClassifier, petrinetWithMarking, miningAlgorithm, log1)).getValue();
                fitness += (f != null)?f:0.0;

                Double p = (alignmentBasedPrecision.computeMeasurement(pluginContext, xEventClassifier, petrinetWithMarking, miningAlgorithm, log1)).getValue();
                precision += (p != null)?p:0.0;
            } catch( Exception e ) { return measure; }
        }

        fitness = fitness / (double) fold;
        precision = precision / (double) fold;
        f_measure = 2*(fitness*precision)/(fitness+precision);

        measure.addMeasure(getMeasurementName(), f_measure);
        measure.addMeasure(alignmentBasedFitness.getMeasurementName(), fitness);
        measure.addMeasure(alignmentBasedPrecision.getMeasurementName(), precision);

        return measure;
    }

    @Override
    public String getMeasurementName() {
        return fold+"-Fold Alignment-Based f-Measure";
    }

    private XLog[] createdXFolds() {

        if(log.size() < fold) fold = log.size();
        XLog[] logs = new XLog[fold];

        for(int i = 0; i < fold; i++) {
            logs[i] = factory.createLog(log.getAttributes());
        }

        if(log.size() == fold) {
            int pos = 0;
            for (XTrace t : log) {
                logs[pos].add(t);
                pos++;
            }
        }else {
            boolean finish = false;
            while (!finish) {
                finish = true;
                for (XTrace t : log) {
                    int pos = r.nextInt(fold);
                    logs[pos].add(t);
                }
                for (int i = 0; i < logs.length; i++) {
                    if (logs[i].size() == 0) {
                        finish = false;
                    }
                }
                if(!finish) {
                    for(int i = 0; i < fold; i++) {
                        logs[i].clear();
                    }
                }
            }
        }

        return logs;
    }
}