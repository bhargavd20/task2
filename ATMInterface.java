import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    String name;
    String userName;
    String password;
    String accountNo;
    float balance = 10000f;
    int transactions = 0;
    String transactionHistory = "";

    public void register(String name, String userName, String password, String accountNo) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.accountNo = accountNo;
    }

    public boolean login(String userName, String password) {
        return userName.equals(this.userName) && password.equals(this.password);
    }

    public void withdraw(float amount) {
        if (balance >= amount) {
            transactions++;
            balance -= amount;
            transactionHistory = transactionHistory.concat(amount + " Rs Withdrawn\n");
        }
    }

    public void deposit(float amount) {
        transactions++;
        balance += amount;
        transactionHistory = transactionHistory.concat(amount + " Rs Deposited\n");
    }

    public void transfer(float amount, String recipient) {
        if (balance >= amount) {
            transactions++;
            balance -= amount;
            transactionHistory = transactionHistory.concat(amount + " Rs Transferred to " + recipient + "\n");
        }
    }

    public float checkBalance() {
        return balance;
    }

    public String transHistory() {
        if (transactions == 0) {
            return "No Transactions happened";
        } else {
            return transactionHistory;
        }
    }
}

public class ATMInterface extends JFrame {
    BankAccount account = new BankAccount();

    // UI Components
    JTextField nameField;
    JTextField userNameField;
    JPasswordField passwordField;
    JTextField accountNoField;
    JTextArea outputArea;
    JTextField amountField;
    JTextField recipientField;

    public ATMInterface() {
        // Frame settings
        setTitle("ATM Interface");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new CardLayout());

        // Panels for different screens
        JPanel registerPanel = new JPanel(new GridBagLayout());
        JPanel loginPanel = new JPanel(new GridBagLayout());
        JPanel mainPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        JPanel transactionPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Registration Panel
        nameField = new JTextField(20);
        userNameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        accountNoField = new JTextField(20);
        JButton registerButton = new JButton("Register");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        registerPanel.add(new JLabel("Register"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        registerPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        registerPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(userNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        registerPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        registerPanel.add(new JLabel("Account No:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(accountNoField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        registerPanel.add(registerButton, gbc);

        // Login Panel
        JTextField loginUserNameField = new JTextField(20);
        JPasswordField loginPasswordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(new JLabel("Login"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        loginPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(loginUserNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        loginPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(loginPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        // Main Panel
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton transferButton = new JButton("Transfer");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton transactionHistoryButton = new JButton("Transaction History");
        JButton logoutButton = new JButton("Logout");

        mainPanel.add(withdrawButton);
        mainPanel.add(depositButton);
        mainPanel.add(transferButton);
        mainPanel.add(checkBalanceButton);
        mainPanel.add(transactionHistoryButton);
        mainPanel.add(logoutButton);

        // Transaction Panel
        amountField = new JTextField();
        recipientField = new JTextField();
        JButton executeButton = new JButton("Execute");

        gbc.gridx = 0;
        gbc.gridy = 0;
        transactionPanel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        transactionPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        transactionPanel.add(new JLabel("Recipient (for transfer only):"), gbc);
        gbc.gridx = 1;
        transactionPanel.add(recipientField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        transactionPanel.add(executeButton, gbc);

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Adding panels to the frame
        add(registerPanel, "Register");
        add(loginPanel, "Login");
        add(mainPanel, "Main");
        add(transactionPanel, "Transaction");
        add(scrollPane, "Output");

        // Button Actions
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                account.register(nameField.getText(), userNameField.getText(), new String(passwordField.getPassword()), accountNoField.getText());
                outputArea.setText("Registration Successful. Please log in.\n");
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Login");
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (account.login(loginUserNameField.getText(), new String(loginPasswordField.getPassword()))) {
                    outputArea.setText("Login Successful.\n");
                    ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Main");
                } else {
                    outputArea.setText("Login Failed. Try Again.\n");
                }
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Transaction");
                outputArea.setText(""); // Clear previous output
                amountField.setText(""); // Clear amount field
                recipientField.setText(""); // Clear recipient field
                executeButton.setActionCommand("Withdraw");
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Transaction");
                outputArea.setText(""); // Clear previous output
                amountField.setText(""); // Clear amount field
                recipientField.setText(""); // Clear recipient field
                executeButton.setActionCommand("Deposit");
            }
        });

        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Transaction");
                outputArea.setText(""); // Clear previous output
                amountField.setText(""); // Clear amount field
                recipientField.setText(""); // Clear recipient field
                executeButton.setActionCommand("Transfer");
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                outputArea.setText("Current Balance: " + account.checkBalance() + " Rs\n");
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Output");
            }
        });

        transactionHistoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                outputArea.setText(account.transHistory());
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Output");
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Login");
            }
        });

        executeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String command = executeButton.getActionCommand();
                float amount = Float.parseFloat(amountField.getText());
                String recipient = recipientField.getText();

                if ("Withdraw".equals(command)) {
                    account.withdraw(amount);
                    outputArea.setText("Withdraw Successful. Current Balance: " + account.checkBalance() + " Rs\n");
                } else if ("Deposit".equals(command)) {
                    account.deposit(amount);
                    outputArea.setText("Deposit Successful. Current Balance: " + account.checkBalance() + " Rs\n");
                } else if ("Transfer".equals(command)) {
                    account.transfer(amount, recipient);
                    outputArea.setText("Transfer Successful. Current Balance: " + account.checkBalance() + " Rs\n");
                }




                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Output");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ATMInterface().setVisible(true);
            }
        });
    }
}
