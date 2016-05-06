package com.handy.androidclub.places;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiatse on 5/5/16.
 */
public class PlacePredictionResponse implements Serializable {
    ArrayList<PlacePrediction> predictions;

    public void filter() {
        for (int i = predictions.size() - 1; i >= 0; i--) {
            PlacePrediction p = predictions.get(i);
            boolean isAddress = false;
            for (String s : p.types) {
                if (s.contains("address")) {
                    isAddress = true;
                    break;
                }
            }
            if (!isAddress) {
                predictions.remove(p);
            }
        }
    }

    public List<String> getDescriptions() {
        ArrayList<String> rval = new ArrayList();

        if (predictions == null || predictions.isEmpty()) {
            return rval;
        }

        for (PlacePrediction p : predictions) {
            rval.add(p.description);
        }

        return rval;

    }

}
