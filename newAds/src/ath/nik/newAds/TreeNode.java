package ath.nik.newAds;

import java.util.ArrayList;

public class TreeNode {
	private String title;
	private String id;
	private String count;
	private String idFather;
	
	private ArrayList<TreeNode> subNodes=new ArrayList<TreeNode>();
	
	TreeNode(){
		 this.title="";
		 this.id="";
		 this.count="";
		 this.idFather="0";
	}
	TreeNode(String title, String id){
		 this.title=title;
		 this.id=id;
		 this.count="0";
		 this.idFather="0";
	}
	TreeNode(String title, String id, String count){
		 this.title=title;
		 this.id=id;
		 this.count=count;
		 this.idFather="0";
	}
	TreeNode(String title, String id, String father, String count){
		 this.title=title;
		 this.id=id;
		 this.count=count;
		 this.idFather=father;
	}
	
	
	// set/get data
	
	public String getTitle() {
		return this.title;
	}
	public String getCount() {
		return this.count;
	}
	public String getId() {
		return this.id;
	}
	public String getIdFather() {
		return this.idFather;
	}
	public void setSubNode(TreeNode tn){
		subNodes.add(tn);
	}
	public ArrayList<TreeNode> getSubNodes() {
		return this.subNodes;
	}
	
	//
	
	public boolean isLeaf(){
		if (subNodes.size()==0)
			return true;
		else
			return false;
	}
	
}
