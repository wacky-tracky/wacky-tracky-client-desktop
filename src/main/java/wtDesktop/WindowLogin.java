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

import wackyTracky.clientbindings.java.WtCallbackHandler;
import wackyTracky.clientbindings.java.WtConnMonitor;
import wackyTracky.clientbindings.java.WtRequest;
import wackyTracky.clientbindings.java.WtRequest.ConnError;
import wackyTracky.clientbindings.java.WtRequest.ConnException;

import com.google.gson.JsonObject;

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

	public void clickLogin() {
		this.disableLogin();

		final WtRequest req = Main.session.reqAuthenticate(this.txtUsername.getText(), new String(this.txtPassword.getPassword()));
		req.handle(new WtCallbackHandler() {
			@Override
			public void onException(ConnException connException) {
				WindowLogin.this.onLoginException(connException);
			}

			@Override
			public void onSuccess() {
				JsonObject authObj = this.request.response().getContentJsonObject();
				System.out.println(authObj);

				Main.configFileManager.configuration.lastSessionId = authObj.get("id").toString();
				String username = authObj.get("username").toString();

				WindowLogin.this.onLoginSuccess(username);
			}

			@Override
			public void submit() throws ConnException {
				req.response().assertStatusOkAndJson();
				req.response().saveCookiesInSession();
			}
		});
	}

	private void disableLogin() {
		this.txtUsername.setBackground(Color.GRAY);
		this.txtPassword.setBackground(Color.GRAY);

		this.txtUsername.setEnabled(false);
		this.txtPassword.setEnabled(false);
		this.btnLogin.setEnabled(false);
	}

	public void onLoginException(ConnException e) {
		if (e.isOneOf(ConnError.UNKNOWN_HOST_DNS, ConnError.REQ_WHILE_OFFLINE)) {
			WtConnMonitor.goOffline();
		} else if (e.isOneOf(ConnError.USER_NOT_FOUND)) {
			JOptionPane.showMessageDialog(null, "user not found", "Login failure", JOptionPane.ERROR_MESSAGE);
			this.resetLogin();
			return;
		} else if (e.isOneOf(ConnError.USER_WRONG_PASSWORD)) {
			JOptionPane.showMessageDialog(null, "Your password is incorrect.", "Login failure", JOptionPane.ERROR_MESSAGE);
			this.requestFocus();
			this.txtPassword.requestFocus();

			this.resetLogin();
			return;
		} else {
			e.printStackTrace();
			this.resetLogin();
			return;
		}
	}

	public void onLoginSuccess(String username) {
		Main.username = username;

		this.setVisible(false);
		WindowMain.instance.onLoggedIn();
	}

	private void resetLogin() {
		this.btnLogin.setEnabled(true);
		this.txtUsername.setEnabled(true);
		this.txtPassword.setEnabled(true);

		this.txtUsername.setBackground(Color.WHITE);
		this.txtPassword.setBackground(Color.WHITE);
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

		JLabel lblVersion = new JLabel("Version: " + Main.getVersion());
		gbc.gridy++;
		gbc.gridx = 0;
		lblVersion.setEnabled(false);
		this.add(lblVersion, gbc);

		this.btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				WindowLogin.this.clickLogin();
			}
		});

		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx++;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		this.add(this.btnLogin, gbc);
	}
}
