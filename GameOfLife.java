import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GameOfLife extends JFrame implements ActionListener
{
	private LifeBoard board;
	private Thread lifeThread;
	private boolean paused;
	private boolean running;
	
	JButton startBtn	 = new JButton("Start");
	JButton resetBtn	 = new JButton("Reset");
	JButton clearBtn	 = new JButton("Clear");
	JButton pauseBtn	 = new JButton("Pause/Resume");
	JButton speedBtn 	 = new JButton("Speed");
	JButton updateBtn	 = new JButton("Update");
	
	int defaultWidth = 1580;
	int defaultHeight = 780;
	int defaultDensity = 50;
	
	int width;
	int height;
	int density;
	
	TextField wText;
	TextField hText;
	TextField dText;
	
	public GameOfLife()
	{
		width 	= defaultWidth;
		height 	= defaultHeight;
		density = defaultDensity;
		
		paused 	= false;
		running = false;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("The Game of Life");
		
		board = (new LifeBoard(defaultWidth, defaultHeight, defaultDensity));
		lifeThread = new Thread(board);
		
		add(mkTopMenu(), BorderLayout.NORTH);
		add(board);
		
		pack();	
		Dimension		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2 );
		setVisible(true);
		
		board.setStartState();
	}
	public JPanel mkTopMenu()
	{	
		JPanel controlPanel = new JPanel();
		
		startBtn.addActionListener(this);
		resetBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		pauseBtn.addActionListener(this);
		speedBtn.addActionListener(this);
		updateBtn.addActionListener(this);

		wText = new TextField(defaultWidth + "", 5);
		hText = new TextField(defaultHeight + "", 5);
		dText = new TextField(defaultDensity + "", 5);
			
		controlPanel.add(startBtn);
		controlPanel.add(resetBtn);
		controlPanel.add(clearBtn);
		controlPanel.add(pauseBtn);
		controlPanel.add(speedBtn);
		controlPanel.add(new JLabel("Width"));
		//controlPanel.add(wText);
		controlPanel.add(new JLabel("Height"));
		//controlPanel.add(hText);
		controlPanel.add(new JLabel("Density"));
		//controlPanel.add(dText);
		controlPanel.add(updateBtn);
		
		return controlPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if (source == startBtn)
		{
			if(!running)
			{
				board.storeStartState();
				running = true;
				board.keepGoing();
				lifeThread.start();
			}
		}
		else if (source == clearBtn)
		{
			board.terminate();
			remove(board);
			paused = false;
			running = false;
			board = new LifeBoard(width, height, density);
			lifeThread = new Thread(board);
			add(board, BorderLayout.CENTER);
			pack();
			repaint();
			setVisible(true);
		}
		else if (source == resetBtn)
		{
			board.terminate();
			board.reset();
			lifeThread = new Thread(board);
			paused = false;
			running = false;
		}
		else if (source == pauseBtn)
		{
			if(paused)
			{
				board.keepGoing();
				paused = false;
				lifeThread = new Thread(board);
				lifeThread.start();
				
			}
			else
			{
				board.terminate();
				paused = true;
			}
		}
		else if (source == speedBtn)
		{
			int delay = Integer.parseInt(JOptionPane.showInputDialog("This is the delay in milliseconds between each iteration", board.getDelay()));
			board.setDelay(delay);
		}
		else if (source == updateBtn)
		{
			try{
				height = Integer.parseInt(hText.getText().trim());
				width = Integer.parseInt(wText.getText().trim());
				density = Integer.parseInt(dText.getText().trim());
			} catch (NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(this, "Unable to convert text to number",
						"Input Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			board.terminate();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			if(!lifeThread.isAlive())
			{
				remove(board);
			}

			board = new LifeBoard(width, height, density);
			lifeThread = new Thread(board);
			add(board);
			pack();
			repaint();
		}
		
		
	}
	public static void main(String[] args)
	{
		new GameOfLife();
	}
}
















