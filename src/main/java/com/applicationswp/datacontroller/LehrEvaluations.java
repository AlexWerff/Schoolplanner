package com.applicationswp.datacontroller;

import com.applicationswp.data.LehrEvaluation;

/**
 * Created by alexandre on 20.02.17.
 */
public class LehrEvaluations extends BasicEntryList<LehrEvaluation> {

    @Override
    protected Class<LehrEvaluation> getEntityClass() {
        return LehrEvaluation.class;
    }

}
