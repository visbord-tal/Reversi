package bina.project.alphaBeta;

 public enum Turn{
	 
		 MIN(-1), MAX(1);
		 
		 private int value;
		 
		 Turn(int value){
			 this.value = value;
		 }
		 
		 int getValue(){
			 return value;
		 }
		 
		 public Turn next(){
			 if(this == MIN){
				return MAX;
			}
			else{
				return MIN;
			} 
		 }
		 
		 public boolean isMax(){
			 return this == MAX;
		 }
		 
		 public boolean isMin(){
			 return this == MIN;
		 }
	 }