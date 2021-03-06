package common.nw.creator.gui_legacy;

import common.nw.creator.Creator;
import common.nw.creator.gui.pages.PanelInit;
import common.nw.creator.gui_legacy.pages.*;
import common.nw.creator.properties.CreatorProperties;
import common.nw.gui.PageHolder;
import common.nw.utils.log.NwLogger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatorWindow {

	private JFrame window;
	private PageHolder pageHolder;
	
	private PanelInit page0;
	private PanelSettings page1;
	private PanelMinecraftSettings page2;
	private PanelLoading page3;
	private PanelEditMods page4;
	private PanelFinish page5;
	
	private JButton btnBack;
	private JButton btnNext;
	private JLabel lblHeader;

	private Creator creator;

	public static final boolean DEBUG = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					try {
						UIManager.setLookAndFeel(UIManager
								.getSystemLookAndFeelClassName());

					} catch (Throwable t) {
						NwLogger.CREATOR_LOGGER.error("Error when setting Look and Feel!", t);
					}
					CreatorWindow window = new CreatorWindow();
					window.window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreatorWindow() {
		CreatorProperties.readProperties();
		initData();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//settings window props
		window = new JFrame();
		window.setTitle("Modpack Creator");
		window.setBounds(100, 100, 450, 400);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(new BorderLayout(0, 0));

		//btn panel
		JPanel panelHead = new JPanel();
		window.getContentPane().add(panelHead, BorderLayout.NORTH);
		panelHead.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		//header
		lblHeader = new JLabel("Header here!");
		panelHead.add(lblHeader);

		
		//content panel
		pageHolder = new PageHolder();
		JPanel contentPanel = pageHolder.getPanel();
		contentPanel.setBorder(new EmptyBorder(2, 5, 2, 5));
		window.getContentPane().add(contentPanel, BorderLayout.CENTER);

		page0 = new PanelInit(creator);
		page0.setBorder(new LineBorder(new Color(0, 0, 0)));
		pageHolder.addPage(page0.getPanel(), page0, (String) page0.getProperty(Reference.KEY_NAME));

		page1 = new PanelSettings(creator);
		page1.setBorder(new LineBorder(new Color(0, 0, 0)));
		pageHolder.addPage(page1, (String) page1.getProperty(Reference.KEY_NAME));

		page2 = new PanelMinecraftSettings(creator, window);
		pageHolder.addPage(page2, (String) page2.getProperty(Reference.KEY_NAME));

		page3 = new PanelLoading(creator);
		GridBagLayout gbl_page3 = (GridBagLayout) page3.getLayout();
		gbl_page3.rowHeights = new int[] { 64, 0, 0, 0 };
		pageHolder.addPage(page3, (String) page3.getProperty(Reference.KEY_NAME));

		page4 = new PanelEditMods(creator, window);
		pageHolder.addPage(page4, (String) page4.getProperty(Reference.KEY_NAME));

		page5 = new PanelFinish(creator);
		pageHolder.addPage(page5, (String) page5.getProperty(Reference.KEY_NAME));
		

		JPanel panelButtons = new JPanel();
		panelButtons.setBorder(new EmptyBorder(2, 5, 2, 5));
		window.getContentPane().add(panelButtons, BorderLayout.SOUTH);
		GridBagLayout gbl_panelButtons = new GridBagLayout();
		gbl_panelButtons.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelButtons.rowHeights = new int[] { 0, 0 };
		gbl_panelButtons.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panelButtons.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelButtons.setLayout(gbl_panelButtons);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 0;
		panelButtons.add(btnCancel, gbc_btnCancel);

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				previousPage();
			}
		});
		//disable back button
		btnBack.setEnabled(false);
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBack.gridx = 2;
		gbc_btnBack.gridy = 0;
		panelButtons.add(btnBack, gbc_btnBack);

		btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				nextPage();
			}
		});
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.gridx = 3;
		gbc_btnNext.gridy = 0;
		panelButtons.add(btnNext, gbc_btnNext);

		updatePage();
	}

	private void cancel() {
		if(page0.shouldSaveProperties()) {
			CreatorProperties.saveProperties();
		}
		window.dispose();
	}

	private void updatePage() {
		// updateButtons
		Object o = pageHolder.getCurrentPageProperty(Reference.KEY_TURNABLE);
		if(o != null && o instanceof Boolean) {
			if (!(Boolean)o) {
				btnNext.setEnabled(false);
				btnBack.setEnabled(false);
			} else {
				btnNext.setEnabled(true);
				if (pageHolder.isLastPage() || pageHolder.getCurrentPageIndex() == 4) { // last pages
					btnNext.setText("Finish");
				} else {
					btnNext.setText("Next");
				}
				if (pageHolder.isFirstPage()) {
					btnBack.setEnabled(false);
				} else {
					btnBack.setEnabled(true);
				}
			}
		}
		
		String s = "Step " + (pageHolder.getCurrentPageIndex() + 1) + " of " + (pageHolder.getLastPageIndex() + 1) + ".";
		if (DEBUG) {
			s += "\n..." + pageHolder.getCurrentPageProperty(Reference.KEY_NAME);
		}
		lblHeader.setText(s);
	}
	
	private void nextPage() {
		if(pageHolder.getCurrentPageIndex() == pageHolder.getLastPageIndex()) {
			cancel();
			return;
		}
		pageHolder.nextPage();
		updatePage();
	}
	
	private void previousPage() {
		if(pageHolder.getCurrentPageIndex() == 0) {
			return;
		}
		pageHolder.previousPage();
		updatePage();
	}
	
	private void initData() {
		creator = new Creator();
	}

}
