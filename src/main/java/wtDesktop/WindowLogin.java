package wtDesktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import wackyTracky.clientbindings.java.WtConnMonitor;
import wackyTracky.clientbindings.java.WtRequest;
import wackyTracky.clientbindings.java.WtRequest.ConnError;
import wackyTracky.clientbindings.java.WtRequest.ConnException;

public class WindowLogin extends JFrame {
	public JTextField txtUsername = new JTextField();
	public JPasswordField txtPassword = new JPasswordField();
	public JButton btnLogin = new JButton("Login");

	public WindowLogin() {
		this.setTitle("Login");
		this.setBounds(100, 100, 480, 640);
		this.setMinimumSize(new Dimension(320, 40));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(Main.getIcon());

		this.setupComponents();
		this.pack();

		if (Args.username != null) {
			this.txtUsername.setText(Args.username);
			Main.username = Args.username;
		}

		if (Args.password != null) {
			this.txtPassword.setText(Args.password);
			Main.password = Args.password;
		}

		this.txtPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					WindowLogin.this.clickLogin();
				} else {
					super.keyReleased(e);
				}
			}
		});
	}
	
	private void disableLogin() {
		txtUsername.setBackground(Color.GRAY);
		txtPassword.setBackground(Color.GRAY); 
		
		txtUsername.setEnabled(false);
		txtPassword.setEnabled(false);
		this.btnLogin.setEnabled(false);
	}

	public void clickLogin() {
		try {
			disableLogin();

			WtRequest req = Main.session.reqAuthenticate(this.txtUsername.getText(), new String(this.txtPassword.getPassword()));
			req.response().assertStatusOkAndJson();
			req.response().saveCookiesInSession();

			Main.username = req.response().getContentJsonObject().get("username").toString();
		} catch (ConnException e) {
			if (e.isOneOf(ConnError.UNKNOWN_HOST_DNS, ConnError.REQ_WHILE_OFFLINE)) {
				WtConnMonitor.goOffline();
			} else if (e.isOneOf(ConnError.USER_NOT_FOUND)) {
				JOptionPane.showMessageDialog(null, "user not found");
				return;
			} else {
				e.printStackTrace();
				this.resetLogin();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.resetLogin();
			return;
		}

		this.setVisible(false);
		WindowMain.instance.onLoggedIn();
	}

	private void resetLogin() {
		this.btnLogin.setEnabled(true);
		this.txtUsername.setEnabled(true);
		this.txtPassword.setEnabled(true);
		
		txtUsername.setBackground(Color.WHITE);
		txtPassword.setBackground(Color.WHITE);
	}

	public void setupComponents() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = Util.getNewGbc();

		gbc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(new ComponentLogo(), gbc);

		gbc.gridwidth = 1;
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.weighty = 0;
		gbc.gridy++;
		gbc.weightx = 0;
		this.add(new JLabel("Username: "), gbc);

		gbc.gridx++;
		gbc.weighty = 1;
		this.add(this.txtUsername, gbc);

		gbc.weighty = 0;
		gbc.gridx = 0;
		gbc.gridy++;
		this.add(new JLabel("Password: "), gbc);

		gbc.weighty = 1;
		gbc.gridx++;
		this.add(this.txtPassword, gbc);

		this.btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				WindowLogin.this.clickLogin();
			}
		});

		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridy++;
		this.add(this.btnLogin, gbc);
	}
}
