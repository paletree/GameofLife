package GameOfLife;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import javax.swing.JLabel;

public class WorldFrame extends JFrame {
	 static World world;

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WorldFrame window = new WorldFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WorldFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 545, 342);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu options = new JMenu("  ѡ��   ");
		menuBar.add(options);
		
		JMenuItem start = new JMenuItem("��ʼ");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				world.setBackground(Color.LIGHT_GRAY);
				world.diy=false;
				world.clean=false;
				world.setShape();
			}
		});
		options.add(start);
		
		JMenuItem stop = new JMenuItem("ֹͣ");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//world.time=0;
				world.setBackground(Color.LIGHT_GRAY);
				world.diy=false;
				world.clean=false;
				world.setStop();
			}
		});
		options.add(stop);
		
		JMenuItem pause = new JMenuItem("��ͣ");
		options.add(pause);
		
		JMenuItem add = new JMenuItem("���");
		options.add(add);
		
		JMenuItem delete = new JMenuItem("ɾ��");
		options.add(delete);
		
		JMenuItem randam = new JMenuItem("���");
		options.add(randam);
		
		JMenu SpeedControl = new JMenu("  �ٶȿ���   ");
		menuBar.add(SpeedControl);
		
		JMenuItem lowSpeed = new JMenuItem("���ٶ�");
		SpeedControl.add(lowSpeed);
		
		JMenuItem middleSpeed = new JMenuItem("���ٶ�");
		SpeedControl.add(middleSpeed);
		
		JMenuItem highSpeed = new JMenuItem("���ٶ�");
		SpeedControl.add(highSpeed);
		
		JLabel stillAlive = new JLabel("       �д棺               ");
		menuBar.add(stillAlive);
		
		
		
	
			}	


}
