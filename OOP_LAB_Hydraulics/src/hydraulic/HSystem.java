package hydraulic;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	
	private Collection<Element> elems = new LinkedList<>();
	/**
	 * Adds a new element to the system
	 * @param elem
	 */
	public void addElement(Element elem){
		elems.add(elem);
	}
	
	/**
	 * returns the element added so far to the system
	 * @return an array of elements whose length is equal to the number of added elements
	 */
	public Element[] getElements(){		
		return elems.toArray(new Element[]{});
	}
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		StringBuffer strPath = new StringBuffer("");
		for(Element elem: elems)
			if(elem instanceof Source)
				makeLayout(strPath.append("\n"), elem);
		return strPath.toString();
	}
	
	/**
	 * starts the simulation of the system
	 */
	public void simulate(){
		for(Element elem: elems)
			if(elem instanceof Source)
				flowCount(new StringBuffer(""), elem, ((Source)elem).getFlow());
	}
	
	private boolean reverseFlowCount(Element elem, double flow){
		if(elem == null) return false;
		if(elem instanceof Sink){
			System.out.println(elem.getName() + " " + flow);
			return true;
		}else if(elem instanceof Tap){
			if(((Tap)elem).getOpen() &&
				reverseFlowCount(elem.getOutput(),flow)){
				System.out.println(elem.getName() + " " + flow + " " + flow);
				return true;
			}				
		}else if(elem instanceof Split){
			boolean ok=false;
			for(int i=0;i<2;i++)
				if(reverseFlowCount(((Split)elem).getOutputs()[i],flow/2))
					ok=true;
			if(ok==true){
				System.out.println(elem.getName() + " " + flow + " " + flow/2);
				return true;
			}
		}else if(elem instanceof Source){
			if(reverseFlowCount(elem.getOutput(),
					(((Source)elem).getFlow()<flow)?((Source)elem).getFlow():flow)){
				System.out.println(elem.getName() + " " + flow + " " + 
						((((Source)elem).getFlow()<flow)?((Source)elem).getFlow():flow));
			}
		}
		return false;			
	}
	
	private boolean flowCount(StringBuffer strPath, Element elem, double flow){
		if(elem == null) return false;
		if(elem instanceof Sink){
			strPath.append(elem.getName() + " " +flow);
			System.out.println(strPath.toString());
			return true;
		}else if(elem instanceof Tap){
			if(((Tap)elem).getOpen()){
				strPath.append(elem.getName() + " " + flow + " " + flow + "\n");
				if(flowCount(strPath,elem.getOutput(),flow))
					return true;	
			}
		}else if(elem instanceof Split){
			strPath.append(elem.getName() + " " + flow + " " +flow/2 + "\n");
			StringBuffer tmpStrPath = new StringBuffer(strPath.toString());
			boolean printed = false;
			for(int i=0;i<2;i++)
				if(flowCount(((printed)?new StringBuffer(""):tmpStrPath),
						((Split)elem).getOutputs()[i],flow/2))
					printed=true;
			return printed;
			
		}else if(elem instanceof Source){
			strPath.append(elem.getName() + " " + flow + " " + 
					((((Source)elem).getFlow()<flow)?((Source)elem).getFlow():flow) + "\n");
			if(flowCount(strPath,elem.getOutput(),
					(((Source)elem).getFlow()<flow)?((Source)elem).getFlow():flow))
				return true;
		}
		return false;
	}

	private String makeLayout(StringBuffer strPath, Element elem){
		if(elem == null) return null;
		strPath.append("[" + elem.getName() + "]" + elem.getClass().getSimpleName() + " ");
		if(elem instanceof Sink) strPath.append("|\n");
		else if(elem instanceof Tap || elem instanceof Source){
			if(elem.getOutput()==null) strPath.append("|\n");
			else {
				strPath.append("-> ");
				makeLayout(strPath,elem.getOutput());
			}
		}else if(elem instanceof Split){
			int length = strPath.length()-strPath.lastIndexOf("\n")-1;
			int index = strPath.length();
			boolean skip = true;
			if(((Split) elem).getOutputs().length==0) strPath.append("|\n");
			else if(((Split) elem).getOutputs()[0]!=null){
				strPath.append("+-> ");
				makeLayout(strPath,((Split) elem).getOutputs()[0]); 
				if(((Split) elem).getOutputs()[1]!=null){
					while(strPath.indexOf("\n",index)!=strPath.lastIndexOf("\n")){
						if(skip == false){
							strPath.insert(index + length + 1, "|");
							strPath.deleteCharAt(index + length + 2);
						}
						index = strPath.indexOf("\n", index + 1);
						skip = false;
					}
					for(int i=0;i<length;i++) strPath.append(" ");
					strPath.append("|\n");
					for(int i=0;i<length;i++) strPath.append(" ");
				}
			}
			if(((Split) elem).getOutputs()[1]!=null){
				strPath.append("+-> ");
				makeLayout(strPath,((Split) elem).getOutputs()[1]); 				
			}
		}
		return strPath.toString();
	}
}
