package server;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import server.model.players.Client;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	private JFrame frame;
	@SuppressWarnings("rawtypes")
	private JList playerList;
	private JTextField portField;
	
	public static void main(String[] args) {
		JFrame gui = new GUI();
		gui.setVisible(true);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	public GUI() {
		setSize(515, 387);
		setResizable(false);
		setTitle("RuneLocus Starter Pack Panel");
		frame = this;
		 try {
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	    }
	    catch (ClassNotFoundException e) {
	    }
	    catch (InstantiationException e) {
	    }
	    catch (IllegalAccessException e) {
	    }

		getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 509, 354);
		
		JPanel homePane = new JPanel();
		homePane.setLayout(null);
		final JCheckBox showPasswordCheck = new JCheckBox("Passwords");
		showPasswordCheck.setBounds(12, 247, 113, 25);
		homePane.add(showPasswordCheck);
		
		JButton refreshButton = new JButton("Refresh List");
		refreshButton.setBounds(12, 281, 167, 25);
		homePane.add(refreshButton);
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultListModel listModel = new DefaultListModel();
				for(int index = 0; index < Server.playerHandler.players.length; index++) {
					Client player = (Client) Server.playerHandler.players[index];
					if(player != null)
						listModel.addElement(player.playerName + (showPasswordCheck.isSelected() ? ", "+player.playerPass : ""));
				}
				playerList.setModel(listModel);
			}
		});
		homePane.add(refreshButton);
		
		DefaultListModel listModel = new DefaultListModel();
		JScrollPane scrollPane = new JScrollPane();
		for(int index = 0; index < Server.playerHandler.players.length; index++) {
			Client player = (Client) Server.playerHandler.players[index];
			if(player != null)
				listModel.addElement(player.playerName + (showPasswordCheck.isSelected() ? ", "+player.playerPass : ""));
		}
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(12, 29, 167, 205);
		homePane.add(scrollPane);
		playerList = new JList();
		homePane.add(playerList);
		playerList.setModel(listModel);
		playerList.setBounds(12, 27, 165, 205);
		JButton saveButton = new JButton("Save & Compile");
		saveButton.setBounds(224, 137, 233, 42);
		homePane.add(saveButton);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								String command = 
										"java -d bin -cp ./deps/*; -sourcepath src src/server/GUI.java"
										;
								Process process = Runtime.getRuntime().exec(command);
								process.waitFor();
								JOptionPane.showMessageDialog(frame, "Server was compiled!");
							} catch (IOException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}).start();
			}
		});
		
		JButton runButton = new JButton("Run Server");
		runButton.setBounds(224, 192, 233, 42);
		homePane.add(runButton);
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						int port = -1;
						if(!portField.getText().isEmpty() && portField.getText() != null)
							port = Integer.valueOf(portField.getText());
						try {
							Server.runServer(port);
						} catch (NullPointerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}).start();
			}
			
		});
		
		JLabel portLabel = new JLabel("Set Port:");
		portLabel.setBounds(224, 78, 70, 16);
		homePane.add(portLabel);
		
		portField = new JTextField();
		portField.setBounds(306, 75, 151, 22);
		homePane.add(portField);
		portField.setColumns(10);
		
		statusField = new JEditorPane();
		updatePane();
		statusField.setBackground(frame.getBackground());
		statusField.setEditable(false);
		statusField.setBounds(224, 267, 233, 25);
		homePane.add(statusField);
		
		tabbedPane.addTab("Home", null, homePane, null);
		
		final String forums = "http://www.runelocus.com/forums/forum.php";
		JTextPane helpField = new JTextPane();
		helpField.setContentType("text/html");
		helpField.setBackground(getBackground());
		helpField.setText("<a href=\""+forums+"\">Need help on your server? Visit our forums!</a>");
		helpField.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.browse(new URL(forums).toURI());
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		helpField.setEditable(false);
		helpField.setBounds(220, 29, 257, 25);
		homePane.add(helpField);
		JPanel commands = new JPanel();
		commands.setLayout(null);
		
		final JTextField usernameField = new JTextField();
		usernameField.setBounds(88, 54, 128, 22);
		commands.add(usernameField);
		usernameField.setColumns(10);
		
		final JComboBox punishList = new JComboBox();
		punishList.addItem("Kick");
		punishList.addItem("Mute");
		punishList.addItem("Ban");
		punishList.addItem("IP Ban");
		punishList.addItem("Unmute");
		punishList.addItem("Unban");
		punishList.addItem("Un IP Ban");
		punishList.setBounds(234, 56, 116, 22);
		commands.add(punishList);
		
		JButton punishButton = new JButton("Punish");
		punishButton.setBounds(362, 53, 116, 25);
		punishButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String punishment = new StringBuilder().append(punishList.getSelectedItem()).toString();
				String username = usernameField.getText();
				for(int index = 0; index < Server.playerHandler.players.length; index++) {
					Client player = (Client) Server.playerHandler.players[index];
					if(player != null) {
						if(player.playerName.equalsIgnoreCase(username)) {
							if(punishment.equals("Kick")) {
								player.disconnected = true;
								System.out.println("You have kicked "+username+".");
							} else if(punishment.equals("Mute")) {
								Connection.addNameToMuteList(player.playerName);
								System.out.println("You have muted "+username+".");
							} else if(punishment.equals("Unmute")) {
								Connection.unMuteUser(player.playerName);
								System.out.println("You have muted "+username+".");
							} else if(punishment.equals("Ban")) {
								Connection.addNameToBanList(player.playerName);
								Connection.addNameToFile(player.playerName);
								System.out.println("You have banned "+username+".");
								player.disconnected = true;
							} else if(punishment.equals("IP Ban")) {
								Connection.addIpToBanList(player.connectedFrom);
								Connection.addIpToFile(player.connectedFrom);
								System.out.println("You have IP banned the user: "+player.playerName+" with the host: "+player.connectedFrom);
								player.disconnected = true;
							} else if(punishment.equals("Un IP Ban")) {
								Connection.removeIpFromBanList(player.playerName);
								System.out.println(username + " has been unip banned.");
							} else if(punishment.equals("Unban")) {
								Connection.removeNameFromBanList(player.playerName);
								System.out.println(username+" has been unbanned.");
							}
						}
					}	
				}
			}
		});
		commands.add(punishButton);
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(12, 57, 67, 16);
		commands.add(usernameLabel);
		
		final JComboBox teleList = new JComboBox();
		teleList.addItem("Lumbridge");
		teleList.addItem("Varrock");
		teleList.addItem("Camelot");
		teleList.addItem("Falador");
		teleList.addItem("Edgeville");
		teleList.setBounds(231, 125, 116, 22);
		commands.add(teleList);
		
		final JTextField xCoordField = new JTextField();
		xCoordField.setBounds(12, 123, 60, 22);
		commands.add(xCoordField);
		xCoordField.setColumns(10);
		
		final JTextField yCoordField = new JTextField();
		yCoordField.setColumns(10);
		yCoordField.setBounds(84, 123, 60, 22);
		commands.add(yCoordField);
		
		final JTextField heightField = new JTextField();
		heightField.setColumns(10);
		heightField.setBounds(156, 123, 60, 22);
		commands.add(heightField);
		
		JLabel xCoordLabel = new JLabel("X Coord:");
		xCoordLabel.setBounds(12, 96, 56, 16);
		commands.add(xCoordLabel);
		
		JLabel yLabelCoord = new JLabel("Y Coord:");
		yLabelCoord.setBounds(88, 96, 56, 16);
		commands.add(yLabelCoord);
		
		JLabel heightLabel = new JLabel("Height:");
		heightLabel.setBounds(156, 96, 56, 16);
		commands.add(heightLabel);
		
		JLabel cityLabel = new JLabel("Or City:");
		cityLabel.setBounds(231, 96, 56, 16);
		commands.add(cityLabel);
		
		JButton teleButton = new JButton("Teleport");
		teleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!xCoordField.getText().isEmpty() && !yCoordField.getText().isEmpty() &&
						!heightField.getText().isEmpty()) {
					int xCoord = Integer.valueOf(xCoordField.getText());
					int yCoord = Integer.valueOf(yCoordField.getText());
					int height = Integer.valueOf(heightField.getText());
					for(int index = 0; index < Server.playerHandler.players.length; index++) {
						Client player = (Client) Server.playerHandler.players[index];
						if(player != null) {
							if(player.playerName.equalsIgnoreCase(usernameField.getText())) {
								player.getPA().movePlayer(xCoord, yCoord, height);
								System.out.println("You have teleported "+player.playerName+" to coordinates {"+xCoord+
										", "+yCoord+", "+height+"}.");
							}
						}
					}
				} else {
					String city = new StringBuilder().append(teleList.getSelectedItem()).toString();
					for(int index = 0; index < Server.playerHandler.players.length; index++) {
						Client player = (Client) Server.playerHandler.players[index];
						if(player != null) {
							if(player.playerName.equalsIgnoreCase(usernameField.getText())) {
								if(city.equalsIgnoreCase("Lumbridge")) {
									player.getPA().movePlayer(3222, 3217, 0);
									System.out.println("You have teleported "+player.playerName+" to coordinates {3222, 3217, 0}.");
								} else if(city.equalsIgnoreCase("Varrock")) {
									player.getPA().movePlayer(3211, 3424, 0);
									System.out.println("You have teleported "+player.playerName+" to coordinates {3211, 3424, 0}.");
								} else if(city.equalsIgnoreCase("Falador")) {
									player.getPA().movePlayer(2966, 3379, 0);
									System.out.println("You have teleported "+player.playerName+" to coordinates {2966, 3379, 0}.");
								} else if(city.equalsIgnoreCase("Edgeville")) {
									player.getPA().movePlayer(3093, 3493, 0);
									System.out.println("You have teleported "+player.playerName+" to coordinates {3093, 3493, 0}.");
								} else if(city.equalsIgnoreCase("Camelot")) {
									player.getPA().movePlayer(2758, 3479, 0);
									System.out.println("You have teleported "+player.playerName+" to coordinates {2758, 3479, 0}.");
								}
							}
						}
					}

				}
				
			}
			
		});
		teleButton.setBounds(362, 122, 116, 25);
		commands.add(teleButton);
		
		tabbedPane.addTab("Commands", null, commands, null);
		
		getContentPane().add(tabbedPane);
	}
	
	public static void updatePane() {
		statusField.setContentType("text/html");
		statusField.setText("Server is currently "+((Server.isRunning == true) ? "<font color=\"green\"> online</font>" : "<font color=\"red\"> offline</font>")+".");
	}
	
	public static JEditorPane statusField;
}
