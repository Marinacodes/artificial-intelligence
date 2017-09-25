public class StateWaterJugs
{    
    int jugsArray[];
    
    public StateWaterJugs(int[] jugsArray) { 
		this.jugsArray = jugsArray; 
	}
    
    //It has to be a copy of values not reference because we will 
    //create many states and don't want to overwrite the same array.
    public StateWaterJugs(StateWaterJugs state) {
    	jugsArray = new int[3];
        
		for(int i = 0; i < 3; i++) {
            this.jugsArray[i] = state.jugsArray[i];
		}
    }
    
    public boolean equals(Object o) {
        StateWaterJugs state = (StateWaterJugs) o;
        
        for(int i = 0; i < 3; i++) {
            if(this.jugsArray[i] != state.jugsArray[i]) {
                return false;
			}
		}
        
        return true;
    }
    
    public int hashCode() {
        return jugsArray[0]*100 + jugsArray[1]*10 + jugsArray[2];
    }    
    
    public String toString() {
        String ret = "";
        for (int i = 0; i < 3; i++) {
            ret += " " + this.jugsArray[i];
		}
        return ret;
    }
}