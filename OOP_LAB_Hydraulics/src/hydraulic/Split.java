package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element {
	private Element output1;
	/**
	 * Constructor
	 * @param name
	 */
	public Split(String name) {
		super(name);
		//TODO: complete
	}
    
	/**
	 * returns the downstream elements
	 * @return array containing the two downstream element
	 */
    public Element[] getOutputs(){
        return new Element[]{getOutput(),output1};
    }

	public void connect(Element elem, int noutput){
		switch(noutput){
			case 0:		connect(elem);
						break;
			case 1: 	output1=elem;

		}
	}
}
