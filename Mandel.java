import	java.awt.*;
import	java.awt.event.*;
import	javax.swing.*;


public class Mandel extends JFrame implements
					WindowListener,		// close window
					ActionListener,		// Buton presses
					MouseListener,		// Mouse button events
					MouseMotionListener	// sorta explains itself
{
	static	int		width = 600;			// frame size
	static	int		height = 400;

		JButton		draw = new JButton("Draw");
		JButton		close = new JButton("Close");
		JButton		reset = new JButton("Reset");

		double		defaultx0 = -2.25;		// default Mandelbrot
		double		defaultx1 = 0.75;		// set area
		double		defaulty0 = -1.5;
		double		defaulty1 = 1.5;

		double		x0 = defaultx0;			// updated or magnified
		double		x1 = defaultx1;			// Mandelbrot set area
		double		y0 = defaulty0;
		double		y1 = defaulty1;

		double		dx = (x1 - x0) / width;		// initia increment, delta,
		double		dy = (y1 - y0) / height;	// or step size

		int		xstart;				// starting corner for
		int		ystart;				// rubber band box
		int		xend;				// ending corner for
		int		yend;				// rubber band box

		JTextField	textX0;				// input and output
		JTextField	textX1;				// text fields
		JTextField	textY0;
		JTextField	textY1;

		MandelPanel	mandelbrotSet;			// image on a JPanel


	public Mandel()
	{
		setTitle("Mandelbrot Set");

		addWindowListener(this);

		// build the controls

		JPanel controlPanel = new JPanel();

		controlPanel.add(draw);
		controlPanel.add(close);
		controlPanel.add(reset);

		add(controlPanel, BorderLayout.NORTH);		// to JFrame

		draw.addActionListener(this);
		close.addActionListener(this);
		reset.addActionListener(this);

		// build the input

		JPanel inputPanel = new JPanel();

		textX0 = new JTextField(defaultx0 + "", 5);
		textX1 = new JTextField(defaultx1 + "", 5);
		textY0 = new JTextField(defaulty0 + "", 5);
		textY1 = new JTextField(defaulty1 + "", 5);

		inputPanel.add(new JLabel("x0: ", JLabel.RIGHT));
		inputPanel.add(textX0);
		inputPanel.add(new JLabel("y0: ", JLabel.RIGHT));
		inputPanel.add(textY0);
		inputPanel.add(new JLabel("x1: ", JLabel.RIGHT));
		inputPanel.add(textX1);
		inputPanel.add(new JLabel("y1: ", JLabel.RIGHT));
		inputPanel.add(textY1);

		add(inputPanel, BorderLayout.SOUTH);		// to JFrame


		// build the mandelbrot display

		mandelbrotSet = new MandelPanel();
		mandelbrotSet.addMouseListener(this);		// JPanel listens to mouse
		mandelbrotSet.addMouseMotionListener(this);

		add(mandelbrotSet);				// to JFrame

		pack();
		setVisible(true);
	}



	// --------- JButton events

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if (source == draw)
		{						// read from text fields
			try
			{
				x0 = Double.parseDouble(textX0.getText().trim());
				y0 = Double.parseDouble(textY0.getText().trim());
				x1 = Double.parseDouble(textX1.getText().trim());
				y1 = Double.parseDouble(textY1.getText().trim());
			}
			catch (NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(this, "Unable to convert text to number",
						"Input Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			dx = (x1 - x0) / width;			// update the per pixel
			dy = (y1 - y0) / height;		// increment

			mandelbrotSet.repaint();
		}
		else if (source == reset)
		{
			x0 = defaultx0;				// reset to default and redraw
			y0 = defaulty0;
			x1 = defaultx1;
			y1 = defaulty1;

			textX1.setText(x1 + "");		// update values shown
			textY1.setText(y1 + "");		// in the text fields
			textX0.setText(x0 + "");
			textY0.setText(y0 + "");

			dx = (x1 - x0) / width;			// update the per pixel
			dy = (y1 - y0) / height;		// increment

			mandelbrotSet.repaint();
		}
		else if (source == close)
			System.exit(0);
	}



	// -------- Mouse button events

	public void mousePressed(MouseEvent e)
	{
		xend = xstart = e.getX();
		yend = ystart = e.getY();
	}


	public void mouseReleased(MouseEvent e)
	{
		xend = e.getX();				// final x, y where mouse
		yend = e.getY();				// button was released

								// final rubber band coords
		int xul = (xstart < xend) ? xstart : xend;	// upper left corner x
		int yul = (ystart < yend) ? ystart : yend;	// upper left corner y
		int xlr = (xstart > xend) ? xstart : xend;	// lower right corner x
		int ylr = (ystart > yend) ? ystart : yend;	// lower right corner y

		x1 = x0 + xlr * dx;				// calculate new area for
		y1 = y0 + ylr * dy;				// the Mandelbrot set
		x0 = x0 + xul * dx;
		y0 = y0 + yul * dy;

		textX1.setText(x1 + "");			// update values shown
		textY1.setText(y1 + "");			// in the text fields
		textX0.setText(x0 + "");
		textY0.setText(y0 + "");

		dx = (x1 - x0) / width;				// update the per pixel
		dy = (y1 - y0) / height;			// increment

		mandelbrotSet.repaint();			// redraw the Mandelbrot set
	}


	public void mouseClicked(MouseEvent e) {}		// unneeded methods required
	public void mouseEntered(MouseEvent e) {}		// by the MouseListener
	public void mouseExited(MouseEvent e) {}		// interface




	// -------- Mouse Movement events

	public void mouseDragged(MouseEvent e)			// draws a "rubber band" box
	{	
		Graphics	g = mandelbrotSet.getGraphics();
		g.setXORMode(Color.white);

		// old rubber band box coordinates
		int xul = (xstart < xend) ? xstart : xend;	// upper left x
		int yul = (ystart < yend) ? ystart : yend;	// upper left y
		int xlr = (xstart > xend) ? xstart : xend;	// lower right x
		int ylr = (ystart > yend) ? ystart : yend;	// lower right y

		g.drawRect(xul, yul, xlr - xul, ylr - yul);	// erase

		xend = e.getX();
		yend = e.getY();

		// new rubber band box coordinates
		xul = (xstart < xend) ? xstart : xend;
		yul = (ystart < yend) ? ystart : yend;
		xlr = (xstart > xend) ? xstart : xend;
		ylr = (ystart > yend) ? ystart : yend;

		g.drawRect(xul, yul, xlr -xul , ylr - yul);	// redraw
	}


	public void mouseMoved(MouseEvent e) {}			// required by interface




	// --------- Window Listener events

	public	void	windowClosing(WindowEvent e) { System.exit(0); }
	public	void	windowClosed(WindowEvent e) {}
	public	void	windowIconified(WindowEvent e) {}
	public	void	windowOpened(WindowEvent e) {}
	public	void	windowDeiconified(WindowEvent e) {}
	public	void	windowActivated(WindowEvent e) {}
	public	void	windowDeactivated(WindowEvent e) {}



	class MandelPanel extends JPanel
	{
		final	int		nColors = 256;			// number of colors
			Color[]		palette = new Color[nColors];	// array of colors


		MandelPanel()
		{
			setPreferredSize(new Dimension(width, height));

			for (int i = 0; i < nColors; i++)		// generate random
			{						// palette of colors
				palette[i] = new Color((float)Math.random(),
					(float)Math.random(), (float)Math.random());
			}
		}


		// Calculate and display the Mandelbrot set

		public void paintComponent(Graphics g)
		{
			int	n;
			double	dx = (x1 - x0) / width;
			double	dy = (y1 - y0) / height;

			super.paintComponent(g);


			for (int i = 0; i < height; i++)
				for (int j = 0; j < width; j++)
				{
					double r0 = 0;
					double i0 = 0;
					double X = x0 + j * dx;
					double Y = y0 + i * dy;
					for (n = 0; n < nColors-1; n++)
					{
						double i1 = 2.0 * r0 * i0 + Y;
						double r1 = r0 * r0 - i0 * i0 + X;
						if (Math.sqrt(r1 * r1 + i1 * i1) > 2.0)
							break;
						else
						{	r0 = r1;
							i0 = i1;
						}
					}

					g.setColor(palette[n]);
					g.drawLine(j, i, j, i);		// draws a single "point"
				}
		}
	}



	public static void main(String[] args)
	{
		new Mandel();
	}
}
