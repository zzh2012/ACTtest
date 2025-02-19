package act.View;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import act.Controller.*;
import act.Model.math;
import act.Model.reading;

import java.awt.*;

public class InstructionView extends JPanel{
	/**
	 This is for instruction view. 
	 */
	private static final long serialVersionUID = 5L;
	private JTextPane textPane = new JTextPane();
	private math mathBrain;
	private reading readingBrain;
	public InstructionView(){
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(ViewConstants.MAINCONTENT_WIDTH,ViewConstants.MAINCONTENT_HEIGHT));
		this.setSize(new Dimension(ViewConstants.MAINPANEL_WIDTH,ViewConstants.MAINPANEL_HEIGHT));
	}
	
	
	public void init(){
//		textPane.setOpaque(false);
//		textPane.setOpaque(true);
		textPane.setBackground(Color.WHITE);
		textPane.setContentType("text/html");
		textPane.setPreferredSize(new Dimension(ViewConstants.MAINCONTENT_WIDTH,ViewConstants.MAINCONTENT_HEIGHT));
		textPane.setText("<html><body><h1>Instruction</h1><p>This is the fuck you instruction for this fucking stupid test. I really hope you can enjoy that but I'm pretty sure you won't. So , pretending enjoy it. GOOD LUCK!</p></body></html>");
		String bodyRule = "body {padding:20; font-family: " +  ViewConstants.instructionBodyFont.getFamily() + "; " +
	            "font-size: " + ViewConstants.instructionBodyFont.getSize() + "pt; }"+" h1 { text-align:center;font-family: " + ViewConstants.instructionTitleFont.getFamily() + "; " +
	    	            "font-size: " + ViewConstants.instructionTitleFont.getSize() + "pt; }";
		
		HTMLDocument document = (HTMLDocument)textPane.getDocument();
		document.getStyleSheet().addRule(bodyRule);
		JScrollPane scrollPane = new JScrollPane(textPane,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(ViewConstants.MAINCONTENT_WIDTH,ViewConstants.MAINCONTENT_HEIGHT));
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	public void setMathBrain(math m){
		mathBrain = m;
	}
	public void setReadingBrain(reading r){
		readingBrain = r;
	}
	public void requestUpdate(int testIndex,int partIndex){
//		System.out.println("DEBUG INFO: in InstructionView "+partIndex);
		textPane.setText(readingBrain.readDirection(testIndex,partIndex));
	}
}
