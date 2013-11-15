package ui.panel;

import game.Session;
import game.state.GameState;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.BackListener;
import ui.SimpleFocusListener;
import ui.Window;

import core.db.DB;

import core.StateSelector;

@SuppressWarnings("serial")
public class LoadPanel extends JPanel
{	
	private JLabel prompt;
	private JTextField idTextField;
	
	private JButton doneBtn;

	public LoadPanel() 
	{   
		prompt = new JLabel();
		add(prompt);
		
		idTextField = new JTextField(40);
		idTextField.setText("game id");
		idTextField.addFocusListener(new SimpleFocusListener("game id"));
		add(idTextField);
		
		doneBtn = new JButton("load");
		doneBtn.addActionListener(new DoneListener());
        add(doneBtn);
        
        JButton backBtn = new JButton("back");
        backBtn.addActionListener(new BackListener(new MenuPanel()));
        add(backBtn);
	}

	private class DoneListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			String id = idTextField.getText();
			id = id.trim();
			
			DB db = DB.getInstance();
			Session session = db.load(id);
			
			if (session == null)
			{
				idTextField.setText("invalid");
			}
			else
			{
				GameState state = new GameState(session);
				StateSelector stateSelector = StateSelector.getInstance();
				stateSelector.setState(state);
				
				GamePanel panel = new GamePanel();
				Window window = Window.getInstance();
				window.setPanel(panel);
			}
		}
	}
}
