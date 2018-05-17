package com.applicationswp.dataparser;

import com.applicationswp.data.EditablePage;

import javax.faces.context.FacesContext;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexwerff on 19.02.17.
 */
public final class EditablePageFactory {

    private EditablePageFactory(){

    }

    public static List<EditablePage> getEditablePages(){
        List<EditablePage> editablePages = new ArrayList<>();
        EditablePage schoolRules = new EditablePage();
        schoolRules.setFile("schoolrules.xhtml");
        schoolRules.setTitle("Schulordnung");
        schoolRules.setDescription("Schulordnung Beschreibung");
        schoolRules.setContent(getContentFromPage(schoolRules));
        editablePages.add(schoolRules);

        EditablePage roomOverview = new EditablePage();
        roomOverview.setFile("roomoverview.xhtml");
        roomOverview.setTitle("Raumübersicht");
        roomOverview.setDescription("Raumübersicht Beschreibung");
        roomOverview.setContent(getContentFromPage(roomOverview));
        editablePages.add(roomOverview);

        return editablePages;
    }

    public static byte[] getContentFromPage(EditablePage page){
        if(page == null){
            return new byte[]{};
        }
        try {
            String absoluteWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
            File f = new File(absoluteWebPath, page.getFile());
            if (f.exists()) {
                try {
                    byte[] encoded = Files.readAllBytes(Paths.get(f.getPath()));
                    return encoded;
                } catch (IOException ex) {
                    return new byte[]{};
                }
            }
        }
        catch (Exception expected){

        }
        return new byte[]{};

    }

    public static boolean setContentForPage(EditablePage page, byte[] content){
        try {
            if (page == null) {
                return false;
            }
            String absoluteWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
            File f = new File(absoluteWebPath, page.getFile());
            if (f.exists()) {
                try {
                    Files.write(Paths.get(f.getPath()), content);
                } catch (IOException ex) {
                    return false;
                }
            }
        }
        catch (Exception ex){
            return false;
        }
        return true;
    }


    public static String pageContentWithoutJSF(String content){
        String result = content;
        int startIndex = result.indexOf("<ui:define name=\"content\">");
        if(startIndex > 0){
            result = result.substring(startIndex+"<ui:define name=\"content\">".length(),result.length());
        }
        int endIndex = result.indexOf("</ui:define>");
        if(endIndex > 0){
            result = result.substring(0,endIndex);
        }
        return result;
    }

    public static String pageContentWithJSF(String content){
        String result = content;
        result = getJSFHeaderContent()+result;
        result = result + getJSFFooterContent();
        return result;
    }

    public static String getJSFHeaderContent(){
        return "<ui:composition template=\"/index.xhtml\"\n" +
                "                xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "                xmlns:f=\"http://java.sun.com/jsf/core\"\n" +
                "                xmlns:h=\"http://java.sun.com/jsf/html\"\n" +
                "                xmlns:ui=\"http://java.sun.com/jsf/facelets\"\n" +
                "                xmlns:b=\"http://bootsfaces.net/ui\"\n" +
                ">\n" +
                "    <ui:define name=\"title\">hello</ui:define>\n" +
                "\n" +
                "    <ui:define name=\"content\">";
    }

    public static String getJSFFooterContent(){
        return "    </ui:define>\n" +
                "</ui:composition>";
    }
}
