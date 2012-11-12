package ath.nik.newAds;

public class WSResults {
	 private String title;
	 private String id;
	 private String count;
	 private String idFather;
	 
	 WSResults(){
		 this.title="";
		 this.id="";
		 this.count="";
		 this.idFather="0";
	 }
	 WSResults(String title, String id, String count){
		 this.title=title;
		 this.id=id;
		 this.count=count;
		 this.idFather="0";
	 }
	 WSResults(String title, String id, String father, String count){
		 this.title=title;
		 this.id=id;
		 this.count=count;
		 this.idFather=father;
	 }
	 public void setTitle(String title) {
		  this.title = title;
		 }

		 public String getTitle() {
		  return this.title;
		 }
		 public void setCount(String c) {
			  this.count = c;
		 }

		public String getCount() {
			return this.count;
		}

		 public void setId(String id) {
		  this.id = id;
		 }

		 public String getId() {
		  return this.id;
		 }

		 public void setIdFather(String Father) {
		  this.idFather = Father;
		 }

		 public String getIdFather() {
		  return this.idFather;
		 }
}
