package br.com.digitalsignatureapp.applet;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import netscape.javascript.JSObject;
import br.com.digitalsignature.bus.DigitalSigner;
import br.com.digitalsignature.bus.impl.DigitalSignerBO;
import br.com.digitalsignature.entity.KeyStoreTO;
import br.com.digitalsignature.entity.MessageTO;
import br.com.digitalsignature.entity.SignatureTO;
import br.com.digitalsignature.exception.KeyStoreException;
import br.com.digitalsignature.exception.NoneMessageException;

/**
 * Applet for digital signing documents. The applet is intended to be placed in a HTML
 * document containing a single HTML form that is used for applet input/output. The
 * applet accepts several parameters - the name of the field in the HTML form that
 * contains the file name to be signed and the names of the fields in the HTML form,
 * where the certification chain and signature should be stored.
 *
 * If the signing process is sucecssfull, the signature and certification chain fields
 * in the HTML form are filled. Otherwise an error message explaining the failure
 * reason is shown to the user.
 *
 * The applet asks the user to locate in his local file system a PFX file (PKCS#12
 * keystore), that holds his certificate (with the corresponding certification chain)
 * and its private key. Also the applet asks the user to enter his password for
 * accessing the keystore and the private key. If the specified file contains a
 * certificate and a corresponding private key that is accessible with supplied
 * password, the signature of the file is calculated and is placed in the HTML form.
 *
 * The applet considers taht the password for the keystore and the password for the
 * private key in it are the same (this is typical for the PFX files).
 *
 * In addition to the calculated signature the certification chain is extracted from
 * the PFX file and is placed in the HTML form too. The digital signature is stored as
 * Base64-encoded sequence of characters. The certification chain is stored as ASN.1
 * DER-encoded sequence of bytes, additionally encoded in Base64.
 *
 * In case the PFX file contains only one certificate without its full certification
 * chain, a chain consisting of this single certificate is extracted and stored in the
 * HTML form instead of the full certification chain.
 *
 * Digital singature algorithm used is SHA1withRSA. The length of the private key and
 * respectively the length of the calculated singature depend on the length of the
 * private key in the PFX file.
 *
 * The applet should be able to access the local machine's file system for reading and
 * writing. Reading the local file system is required for the applet to access the file
 * that should be signed and the PFX keystore file. Writing the local file system is
 * required for the applet to save its settings in the user's home directory.
 *
 * Accessing the local file system is not possible by default, but if the applet is
 * digitally signed (with jarsigner), it runs with no security restrictions. This
 * applet should be signed in order to run.
 *
 * A JRE version 1.4 or hihger is required for accessing the cryptography
 * functionality, so the applet will not run in any other Java runtime environment.
 *
 * This file is part of NakovDocumentSigner digital document
 * signing framework for Java-based Web applications:
 * http://www.nakov.com/documents-signing/
 *
 * Copyright (c) 2003 by Svetlin Nakov - http://www.nakov.com
 * National Academy for Software Development - http://academy.devbg.org
 * All rights reserved. This code is freeware. It can be used
 * for any purpose as long as this copyright statement is not
 * removed or modified.
 */
@SuppressWarnings("serial")
public class DigitalSignerApplet extends ControledApplet {

    // ID FIELDS HTML FORM
    public static final String ID_FIELD_RADIO_TYPE_MESSAGE			= "selectRadioTypeMessage";
    public static final String ID_FIELD_FREE_MESSAGE				= "fieldFreeMessage";
    public static final String ID_FIEDL_DIGITAL_FILE				= "fieldDigitalMessage";
    public static final String ID_FIEDL_CERTIFICATION_CHAIN			= "fiedlCertificationChain";
    public static final String ID_FIEDL_DIGITAL_SIGNATURE			= "fieldDigitalSignature";
    
    public static final String PARAM_SIGN_BUTTON_CAPTION 			= "signButtonCaption";
    public static final String PARAM_NAME_POPUP 					= "namePopupBox";
    public static final String PARAM_MSG_SELECT_KEYSTORE 			= "msgSelectKeystore";
    public static final String PARAM_BROWSE_BUTTON_NAME 			= "browseButtonName";
    public static final String PARAM_MSG_ENTER_PASSWORD 			= "enterPassword";
    public static final String PARAM_LABEL_ACTION_SIGN 				= "labelActionSign";
    public static final String PARAM_LABEL_ACTION_CANCEL 			= "labelActionCancel";
    public static final String PARAM_RESTRICT_EXTENSIONS 			= "msgRestrictExtensionFiles";
    
    private Button mSignButton;
    
    protected Button protect;
    
    // the textFields to get information from users
    private JDialog popUp;
    private JTextField pathKeystore;
    private JTextField passwordKeystore;
    
    /**
     * Initializes the applet - creates and initializes its graphical user interface.
     * Actually the applet consists of a single button, that fills its surface. The
     * button's caption comes from the applet parameter SIGN_BUTTON_CAPTION_PARAM.
     */
    public void init() {
    	this.browserWindow = JSObject.getWindow(this);
    	
        String signButtonCaption = this.getParameter(PARAM_SIGN_BUTTON_CAPTION);
        mSignButton = new Button(signButtonCaption);
        mSignButton.setLocation(0, 0);
        Dimension appletSize = this.getSize();
        mSignButton.setSize(appletSize);
        mSignButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                loadPopup();
            }
        });
        this.setLayout(null);
        this.add(mSignButton);
    }

    /**
     * this method has the job for to construct the popup.
     */
    @SuppressWarnings("deprecation")
	private void loadPopup() {
    	// Initialize the dialog
    	this.popUp = new JDialog();
    	this.popUp.getContentPane().setLayout(null);
    	this.popUp.setSize(new Dimension(426, 165));
    	this.popUp.setBackground(SystemColor.control);
    	this.popUp.setTitle(this.getParameter(PARAM_NAME_POPUP));
    	this.popUp.setResizable(false);
    	
    	// Center the dialog in the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dialogSize = this.getSize();
        int centerPosX = (screenSize.width - dialogSize.width) / 2;
        int centerPosY = (screenSize.height - dialogSize.height) / 2;
        this.popUp.setLocation(centerPosX, centerPosY);
        
        // Initialize certificate keystore file label
        JLabel mChooseCertFileLabel = new JLabel();
        mChooseCertFileLabel.setText(this.getParameter(PARAM_MSG_SELECT_KEYSTORE));
        mChooseCertFileLabel.setBounds(new Rectangle(10, 5, 350, 15));
        mChooseCertFileLabel.setFont(new Font("Dialog", 0, 12));
    	
        // Initialize certificate keystore file name text field
        this.pathKeystore = new JTextField();
        this.pathKeystore.setBounds(new Rectangle(10, 25, 315, 20));
        this.pathKeystore.setFont(new Font("DialogInput", 0, 12));
        this.pathKeystore.setEditable(false);
        this.pathKeystore.setBackground(SystemColor.control);
        
        // Initialize browse button
        JButton mBrowseForCertButton = new JButton();
        mBrowseForCertButton.setText(this.getParameter(PARAM_BROWSE_BUTTON_NAME));
        mBrowseForCertButton.setBounds(new Rectangle(330, 25, 80, 20));
        mBrowseForCertButton.addActionListener(new BrowseButtonAction(this, this.popUp, this.pathKeystore));
        
        // Initialize password label
        JLabel mEnterPasswordLabel = new JLabel();
        mEnterPasswordLabel.setText(this.getParameter(PARAM_MSG_ENTER_PASSWORD));
        mEnterPasswordLabel.setBounds(new Rectangle(10, 55, 350, 15));
        mEnterPasswordLabel.setFont(new Font("Dialog", 0, 12));
        
        // Initialize password text field
        this.passwordKeystore = new JPasswordField();
        this.passwordKeystore.setBounds(new Rectangle(10, 75, 400, 20));
        this.passwordKeystore.setFont(new Font("DialogInput", 0, 12));
        
        // Initialize sign button
        JButton mSignButton = new JButton();
        mSignButton.setText(this.getParameter(PARAM_LABEL_ACTION_SIGN));
        mSignButton.setBounds(new Rectangle(110, 105, 90, 25));
        mSignButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sign();
			}
		});
        
        // Initialize cancel button
        JButton mCancelButton = new JButton();
        mCancelButton.setText(this.getParameter(PARAM_LABEL_ACTION_CANCEL));
        mCancelButton.setBounds(new Rectangle(220, 105, 90, 25));
        mCancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popUp.hide();
			}
		});
        
        // Add the initialized components into the dialog's content pane
        this.popUp.getContentPane().add(mChooseCertFileLabel, null);
        this.popUp.getContentPane().add(this.pathKeystore, null);
        this.popUp.getContentPane().add(mBrowseForCertButton, null);
        this.popUp.getContentPane().add(mEnterPasswordLabel, null);
        this.popUp.getContentPane().add(this.passwordKeystore, null);
        this.popUp.getContentPane().add(mSignButton, null);
        this.popUp.getContentPane().add(mCancelButton, null);
        this.popUp.getRootPane().setDefaultButton(mSignButton);
        
        // Add some functionality for focusing the most appropriate
        // control when the dialog is shown
        this.popUp.addWindowListener(new WindowAdapterDS(this.pathKeystore, this.passwordKeystore, mBrowseForCertButton));
        
        this.popUp.setModal(true);
        this.popUp.show();
	}

    /**
     * 
     * @return
     */
	private void sign() {
    	String pathKeystore = this.pathKeystore.getText();
    	String passwordKeyStore = this.passwordKeystore.getText();
    	Character typeMessage = ((String) ((JSObject) this.browserWindow.call("getCurrentTypeMessage", null)).getMember("value")).toCharArray()[0];
    	MessageTO message = null;

    	try {
    		if (typeMessage.equals('f')) {
        		message = new MessageTO(this.getValue(ID_FIELD_FREE_MESSAGE));
        	} else if (typeMessage.equals('d')) {
        		message = new MessageTO(new File(this.getValue(ID_FIEDL_DIGITAL_FILE)));
        	} else throw new IllegalStateException("The variable called messageType it's wrong.");

        	DigitalSigner dsBusiness = new DigitalSignerBO();
        	dsBusiness.setMessage(message);
    		
			KeyStoreTO keystore = new KeyStoreTO(passwordKeyStore, pathKeystore);
			SignatureTO signature = dsBusiness.sign(keystore);
			
			this.showResponse(signature);
		} catch (GeneralSecurityException e) {
			this.responseException(e);
			e.printStackTrace();
		} catch (IOException e) {
			this.responseException(e);
			e.printStackTrace();
		} catch (NoneMessageException e) {
			this.responseException(e);
			e.printStackTrace();
		} catch (KeyStoreException e) {
			this.responseException(e);
			e.printStackTrace();
		}
		
    	this.popUp.hide();
    }
    
    /**
     * 
     * @param signature
     */
	private void showResponse(SignatureTO signature) {
		this.setValue(ID_FIEDL_CERTIFICATION_CHAIN, signature.getCertificationChainDecoded());
		this.setValue(ID_FIEDL_DIGITAL_SIGNATURE, signature.getMessageDigestDecoded());
	}

	/**
     * 
     * @author jean
     *
     */
    private class WindowAdapterDS extends WindowAdapter {
    	private JTextField 	certFileNameTextField;
    	private JTextField 	passwordTextField;
    	private JButton		browseButton;
    	
    	/**
    	 * 
    	 * @param certFileNameTextField
    	 * @param passwordTextField
    	 * @param browseButton
    	 */
    	WindowAdapterDS(JTextField certFileNameTextField, JTextField passwordTextField, JButton browseButton) {
    		this.certFileNameTextField = certFileNameTextField;
    		this.passwordTextField = passwordTextField;
    		this.browseButton = browseButton;
    	}
    	
    	/**
    	 * 
    	 */
        public void windowOpened(WindowEvent windowEvent) {
            String certFileName = this.certFileNameTextField.getText();
            if (certFileName != null && certFileName.length() != 0)
                this.passwordTextField.requestFocus();
            else
                this.browseButton.requestFocus();
        }
    }
    
    /**
     * 
     * @author jean
     *
     */
    private class BrowseButtonAction implements ActionListener {
    	private JFileChooser fileChooser = new JFileChooser();
    	private JTextField certFileNameTextField;
    	private JDialog mainJDialog;
    	private Applet mainApplet;
    	
    	public BrowseButtonAction(Applet mainApplet, JDialog mainJDialog, JTextField certFileNameTextField) {
    		this.mainApplet = mainApplet;
    		this.certFileNameTextField = certFileNameTextField;
    		this.mainJDialog = mainJDialog;
    	}
    	
        public void actionPerformed(ActionEvent e) {
            PFXFileFilter pfxFileFilter = new PFXFileFilter(this.mainApplet);
            fileChooser.addChoosableFileFilter(pfxFileFilter);
            String certFileName = certFileNameTextField.getText();
            File directory = new File(certFileName).getParentFile();
            fileChooser.setCurrentDirectory(directory);
            if (fileChooser.showOpenDialog(mainJDialog) == JFileChooser.APPROVE_OPTION) {
                String selectedCertFile = fileChooser.getSelectedFile().getAbsolutePath();
                certFileNameTextField.setText(selectedCertFile);
            }
        }
    }
    
    /**
     * File filter class, intended to accept only .PFX and .P12 files.
     */
    private static class PFXFileFilter extends FileFilter {
    	Applet mainApplet;
    	
    	public PFXFileFilter(Applet mainApplet) {
    		this.mainApplet = mainApplet;
    	}
    	
        public boolean accept(File aFile) {
            if (aFile.isDirectory()) {
                return true;
            }

            String fileName = aFile.getName().toUpperCase();
            boolean accepted = (fileName.endsWith(".PFX") || fileName.endsWith(".P12"));
            return accepted;
        }

        public String getDescription() {
            return this.mainApplet.getParameter(DigitalSignerApplet.PARAM_RESTRICT_EXTENSIONS);
        }
    }
}