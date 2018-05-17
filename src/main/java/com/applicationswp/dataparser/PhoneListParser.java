package com.applicationswp.dataparser;

import com.google.gson.Gson;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.json.stream.JsonParser;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexwerff on 27.02.17.
 */
public class PhoneListParser {

    public static TreeNode getNodeFromJSON(String json){
        try {
            DataNode root = new Gson().fromJson(json, DataNode.class);
            TreeNode tRoot = new DefaultTreeNode(root.getUserID());
            tRoot.setExpanded(true);
            dataNodeToTreeNode(root, tRoot);
            return tRoot;
        }
        catch (Exception ex){
            DefaultTreeNode node = new DefaultTreeNode("Root");
            node.setExpanded(true);
            node.getChildren().add(new DefaultTreeNode("Unknown"));
            return node;
        }
    }

    private static void dataNodeToTreeNode(DataNode dataNode,TreeNode rootNode){
        for(DataNode node:dataNode.getChildren()){
            TreeNode tNode = new DefaultTreeNode(node.getUserID());
            tNode.setExpanded(true);
            rootNode.getChildren().add(tNode);
            dataNodeToTreeNode(node,tNode);
        }
    }



    public static String getJSONFromTreeNode(TreeNode treeNode){
        DataNode dataNode = new DataNode();
        dataNode.setUserID(treeNode.getData().toString());
        treeNodeToDataNode(dataNode,treeNode);
        return new Gson().toJson(dataNode);
    }

    private static void treeNodeToDataNode(DataNode dataNode,TreeNode rootNode){
        for(TreeNode treeNode:rootNode.getChildren()){
            DataNode newNode = new DataNode();
            newNode.setUserID(treeNode.getData().toString());
            dataNode.getChildren().add(newNode);
            treeNodeToDataNode(newNode,treeNode);
        }
    }




    public static List<TreeNode> getNodesFromNode(TreeNode treeNode){
        List<TreeNode> result = new ArrayList<>();
        getNodes(treeNode,result);
        return result;
    }

    private static void getNodes(TreeNode root, List<TreeNode> result){
        for(TreeNode treeNode:root.getChildren()){
            result.add(treeNode);
            getNodes(treeNode,result);
        }
    }

}
