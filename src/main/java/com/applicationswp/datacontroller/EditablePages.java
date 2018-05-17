package com.applicationswp.datacontroller;

import com.applicationswp.data.EditablePage;

/**
 * Created by alexwerff on 20.02.17.
 */
public class EditablePages extends BasicEntryList<EditablePage> {
    @Override
    protected Class<EditablePage> getEntityClass() {
        return EditablePage.class;
    }
}
