package ath.nik.newAds;


import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;

public class TreeMenu {
	private TreeNode root;
	private int count;
	
	TreeMenu(){
		root=new TreeNode("Start","-1","");
		count=0;
	}
	
	public TreeNode getRoot(){
		return root;
	}
	
	public TreeNode find(String id){
		ArrayList<TreeNode> search=new ArrayList<TreeNode>();
		search.add(root);
		while (!search.isEmpty()){
			if(search.get(0).getId().equals(id))
				return search.get(0);
			else{
				if (!search.get(0).isLeaf())
					search.addAll(search.get(0).getChildren());
				search.remove(0);
			}
		}
		return null;
	}
	
	public void addNode(TreeNode newNode, String father){
		TreeNode temp=find(father);
		if (temp!=null){
			newNode.setFather(temp);
			temp.setChildren(newNode);	
		}
		count++;
	}
	
	public ArrayList<TreeNode> getChosen(){
		ArrayList<TreeNode> temp=new ArrayList<TreeNode>();
		getCheckedItem(root,temp);
		return temp;
	}
	
	private void getCheckedItem(TreeNode tn, ArrayList<TreeNode> temp){
		if (tn.isChecked())
			temp.add(tn);
		if (!tn.isLeaf())
			for(int i=0; i<tn.getChildren().size();i++)
				getCheckedItem(tn.getChildren().get(i),temp);		
	}
	
	public void unCheckAll(){
		ArrayList<TreeNode> temp=getChosen();
		for(int i=0;i<temp.size();i++)
			temp.get(i).unCheck();			
	}
	
	public void unCheckNode(TreeNode tn){
		find(tn.getId()).unCheck();
	}
	
	public boolean isNodeChecked(TreeNode tn){
		return tn.isChecked();
	}
	
	public int size(){
		return count;
	}
	
	public ArrayList<TreeNode> getAllData(){
		ArrayList<TreeNode> temp=new ArrayList<TreeNode>();
		getItem(root,temp);
		return temp;
	}
	
	private void getItem(TreeNode tn, ArrayList<TreeNode> temp){
		temp.add(tn);
		if (!tn.isLeaf())
			for(int i=0; i<tn.getChildren().size();i++)
				getItem(tn.getChildren().get(i),temp);		
	}
	
	public ArrayList<TreeNode> getSubMenu(String id){
		return find(id).getChildren();
	}
	
	public void organizeWebData(SoapObject so){
		for(int i=0;i<so.getPropertyCount()-1;i++){
			SoapObject temp = (SoapObject) so.getProperty(i);
			if(temp.getPropertyCount()==2){
				addNode(new TreeNode(temp.getProperty(0).toString(),temp.getProperty(1).toString(),"0"),"-1");
			}
			else{
				addNode(new TreeNode(temp.getProperty(0).toString(),temp.getProperty(1).toString(),"0"),temp.getProperty(2).toString());
			}
		}
	}
		
}

