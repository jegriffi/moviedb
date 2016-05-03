package objects;

public class MutableInt {

	  int value = 1; // note that we start at 1 since we're counting
	  public void increment () { ++value;      }
	  public int  get ()       { return value; }
	  public void set(int n) { value = n; }
	  public int getValue() {return value; }
	@Override
	public String toString() {
		return "MutableInt [value=" + value + "]";
	}
		
}
