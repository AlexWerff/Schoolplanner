package com.applicationswp.datacontroller;

import com.applicationswp.data.RegisterCredit;

/**
 * Created by alexwerff on 27.02.17.
 */
public class RegisterCredits extends BasicEntryList<RegisterCredit> {
    @Override
    protected Class<RegisterCredit> getEntityClass() {
        return RegisterCredit.class;
    }
}
