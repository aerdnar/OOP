import hydraulic.*;

public class ExampleApp {

	public static void main(String args[]){
		
		HSystem s = new HSystem();
	
		// 1) the elements of the system are defined and added
		Source src = new Source("Src");
		s.addElement(src);
		Tap r = new Tap("R");
		s.addElement(r);
		Split t = new Split("T");
		s.addElement(t);
		Sink sink1 = new Sink("sink A");
		s.addElement(sink1);
		Sink sink2 = new Sink("sink B");
		s.addElement(sink2);
		
		// 2) elements are then connected
		src.connect(r);
		r.connect(t);
		t.connect(sink1,0);
		t.connect(sink2,1);
		
		// 3) simulation parameters are then defined
		src.setFlow(20);
		r.setOpen(true);
		
		//-------------------------------
		Source src1 = new Source("Src1");
		Source src2 = new Source("Src2");
		Split t1 = new Split("T1");
		Split t2 = new Split("T2");
		t.connect(src1,0);
		src1.connect(t1);
		t1.connect(t2,0);
		t1.connect(sink2,1);
		t2.connect(sink1,0);
		t2.connect(sink2, 1);
		s.addElement(src1);
		s.addElement(src2);
		//-------------------------------
		
		// 4) simulation starts
		s.simulate();
		
		// 5) print the system layout
		System.out.println(s.layout());
	}
}
