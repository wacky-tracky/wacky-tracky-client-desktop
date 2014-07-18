import groovy.swing.SwingBuilder
import java.awt.BorderLayout as BL

public class Main {
	public Main() {
		println "Main()"; 
		
		def swing = new SwingBuilder();
		
		swing.edt {
			count = 0;
		
			frame(title: "foo", size: [300, 300], show: true) {
				borderLayout();
				textLabel = label(text: "hi", constraints: BL.CENTER);
							
				button(
					text: "click",
					constraints: BL.SOUTH,
					actionPerformed: {count++; textLabel.text = "Clicked " + count + " times."}
				)
			}
		}	
	}
}