package ath.nik.newAds;


import java.util.ArrayList;

public class TreeNode {
	private String title;
	private String id;
	private String count;
	private TreeNode father;
	private boolean checked;
	
	private ArrayList<TreeNode> subNodes=new ArrayList<TreeNode>();
	
	TreeNode(){
		 this.title="";
		 this.id="";
		 this.count="";
	}
	TreeNode(String title, String id,String count){
		 this.title=title;
		 this.id=id;
		 this.count=count;
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
	public TreeNode getFather() {
		return this.father;
	}
	public void setChildren(TreeNode tn){
		subNodes.add(tn);
	}
	public ArrayList<TreeNode> getChildren() {
		return this.subNodes;
	}
	public void setFather(TreeNode t){
		this.father=t;
	}
	public void check(){
		this.checked=true;
		for(int i=0;i<subNodes.size();i++)
			subNodes.get(i).check();
	}
	public void unCheck(){
		this.checked=false;
		for(int i=0;i<subNodes.size();i++)
			subNodes.get(i).unCheck();
	}
	public boolean isChecked(){
		return this.checked;
	}
	
	//
	
	public boolean isLeaf(){
		if (subNodes.size()==0)
			return true;
		else
			return false;
	}
	
	public String toString(){
		return title+" "+id+" "+count;
	}
	
}

