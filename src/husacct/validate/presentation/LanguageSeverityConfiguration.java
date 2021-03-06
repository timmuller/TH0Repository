package husacct.validate.presentation;

import husacct.validate.abstraction.language.ValidateTranslator;
import husacct.validate.domain.validation.Severity;
import husacct.validate.domain.validation.ViolationType;
import husacct.validate.domain.validation.ruletype.RuleType;
import husacct.validate.presentation.tableModels.ComboBoxTableModel;
import husacct.validate.task.TaskServiceImpl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.apache.log4j.Logger;

public class LanguageSeverityConfiguration extends JPanel {
	private static final long serialVersionUID = 4125846168658642242L;
	
	private final ComboBoxTableModel ruletypeModel;
	private final ComboBoxTableModel violationtypeModel;
	private final DefaultTableModel avtViolationtypeModel;
	private final DefaultListModel rtsCategoryModel;
	private final DefaultListModel vtsCategoryModel;
	private final DefaultListModel avtCategoryModel;
	private final DefaultListModel avtRuletypeModel;
	private final String language;
	private final HashMap<String, List<RuleType>> ruletypes;
	private final Map<String, List<ViolationType>> violationTypes;
	private final TaskServiceImpl taskServiceImpl;
	
	private static Logger logger = Logger.getLogger(LanguageSeverityConfiguration.class);

	private JPanel activeViolationtype, ruletypeSeverity, violationtypeSeverity;
	private JButton avtApply, avtDeselectAll, avtSelectAll, rtsApply, rtsRestore,
			rtsRestoreAll, vtsApply, vtsRestore, vtsRestoreAll;
	private JList avtCategory, avtRuletype, rtsCategory, vtsCategory;
	private JScrollPane avtCategoryScrollpane, avtRuletypeScrollpane,
			avtViolationtypeScrollpane, rtsCategoryScrollpane, rtsRuletypeScrollpane,
			vtsCategoryScrollpane, vtsViolationtypeScrollpane;
	private JTable avtViolationtypeTable, rtsRuletypeTable, vtsViolationtypeTable;
	private JTabbedPane tabbedPane;

	public LanguageSeverityConfiguration(String language, Map<String, List<ViolationType>> violationTypes,
			HashMap<String, List<RuleType>> ruletypes, List<Severity> severityNames,
			TaskServiceImpl ts) {
		this.language = language;
		this.ruletypes = ruletypes;
		this.taskServiceImpl = ts;
		this.violationTypes = violationTypes;
		String[] ruletypeColumnNames = {ValidateTranslator.getValue("Ruletype"), ValidateTranslator.getValue("Severity")};
		ruletypeModel = new ComboBoxTableModel(ruletypeColumnNames, 0,
											   severityNames);
		ruletypeModel.setTypes(new Class[]{String.class, Severity.class});
		ruletypeModel.setCanEdit(new Boolean[]{false, true});
		
		
		String[] violationtypeModelHeaders = {ValidateTranslator.getValue("Violationtype"), ValidateTranslator.getValue("Severity")};
		violationtypeModel = new ComboBoxTableModel(violationtypeModelHeaders, 0, severityNames);
		violationtypeModel.setTypes(new Class[]{String.class, Severity.class});
		violationtypeModel.setCanEdit(new Boolean[]{false, true});

		String[] avtViolationtypeModelHeaders = {ValidateTranslator.getValue("Violationtype"), ValidateTranslator.getValue("Active")};
		avtViolationtypeModel = new DefaultTableModel(avtViolationtypeModelHeaders, 0){

			private static final long serialVersionUID = -2752815747553087143L;

			Class<?>[] types = new Class[]{String.class, Boolean.class};
			boolean[] canEdit = new boolean[]{false, true};

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		};

		rtsCategoryModel = new DefaultListModel();
		vtsCategoryModel = new DefaultListModel();
		avtCategoryModel = new DefaultListModel();
		avtRuletypeModel = new DefaultListModel();

		initComponents();
		
		TableColumnModel tcm = rtsRuletypeTable.getColumnModel();
		tcm.getColumn(1).setCellEditor(ruletypeModel.getEditor());

		TableColumnModel tcm2 = vtsViolationtypeTable.getColumnModel();
		tcm2.getColumn(1).setCellEditor(violationtypeModel.getEditor());
		initializeEverything();
	}

	private void initComponents() {		
		tabbedPane = new JTabbedPane();
		ruletypeSeverity = new JPanel();
		rtsCategoryScrollpane = new JScrollPane();
		rtsCategory = new JList();
		rtsRuletypeScrollpane = new JScrollPane();
		rtsRuletypeTable = new JTable();
		rtsApply = new JButton();
		rtsRestore = new JButton();
		rtsRestoreAll = new JButton();
		violationtypeSeverity = new JPanel();
		vtsCategoryScrollpane = new JScrollPane();
		vtsCategory = new JList();
		vtsViolationtypeScrollpane = new JScrollPane();
		vtsViolationtypeTable = new JTable();
		vtsRestore = new JButton();
		vtsRestoreAll = new JButton();
		vtsApply = new JButton();
		activeViolationtype = new JPanel();
		avtCategoryScrollpane = new JScrollPane();
		avtCategory = new JList();
		avtRuletypeScrollpane = new JScrollPane();
		avtViolationtypeTable = new JTable();
		avtViolationtypeScrollpane = new JScrollPane();
		avtRuletype = new JList();
		avtSelectAll = new JButton();
		avtDeselectAll = new JButton();
		avtApply = new JButton();
		
		rtsCategory.setBorder(BorderFactory.createTitledBorder(ValidateTranslator.getValue("Category")));
		rtsCategory.setModel(rtsCategoryModel);
		rtsCategory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rtsCategory.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent evt) {
				 if (evt.getValueIsAdjusting()) {
					 return;
				 } 
				rtsCategoryValueChanged();
			}
		});
		rtsCategoryScrollpane.setViewportView(rtsCategory);

		rtsRuletypeTable.setModel(ruletypeModel);
		rtsRuletypeTable.setFillsViewportHeight(true);
		rtsRuletypeTable.getTableHeader().setReorderingAllowed(false);
		rtsRuletypeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		rtsRuletypeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					return;
				}
				checkRestoreButtonEnabled();
			}
		});
		
		rtsRuletypeScrollpane.setViewportView(rtsRuletypeTable);

		rtsApply.setText(ValidateTranslator.getValue("Apply"));
		rtsApply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				rtsApplyActionPerformed();
			}
		});

		rtsRestore.setText(ValidateTranslator.getValue("RestoreToDefault"));
		rtsRestore.setActionCommand("Restore to default");
		rtsRestore.setEnabled(false);
		rtsRestore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				rtsRestoreActionPerformed();
			}
		});

		rtsRestoreAll.setText(ValidateTranslator.getValue("RestoreAllToDefault"));
		rtsRestoreAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				rtsRestoreAllActionPerformed();
			}
		});

		GroupLayout ruletypeSeverityLayout = new GroupLayout(ruletypeSeverity);
		ruletypeSeverityLayout.setHorizontalGroup(
			ruletypeSeverityLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(ruletypeSeverityLayout.createSequentialGroup()
					.addComponent(rtsCategoryScrollpane, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rtsRuletypeScrollpane, GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(ruletypeSeverityLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(ruletypeSeverityLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(rtsRestore, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
							.addComponent(rtsRestoreAll, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
						.addComponent(rtsApply, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		ruletypeSeverityLayout.setVerticalGroup(
			ruletypeSeverityLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(rtsCategoryScrollpane)
				.addComponent(rtsRuletypeScrollpane, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
				.addGroup(ruletypeSeverityLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(rtsRestore, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rtsRestoreAll, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
					.addComponent(rtsApply)
					.addContainerGap())
		);
		ruletypeSeverity.setLayout(ruletypeSeverityLayout);

		tabbedPane.addTab(ValidateTranslator.getValue("SetRuletypeSeverity"), ruletypeSeverity);

		vtsCategory.setBorder(BorderFactory.createTitledBorder(ValidateTranslator.getValue("Category")));
		vtsCategory.setModel(vtsCategoryModel);
		vtsCategory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		vtsCategory.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent evt) {
				vtsCategoryValueChanged();
			}
		});
		vtsCategoryScrollpane.setViewportView(vtsCategory);

		vtsViolationtypeTable.setModel(violationtypeModel);
		vtsViolationtypeTable.setFillsViewportHeight(true);
		vtsViolationtypeTable.getTableHeader().setReorderingAllowed(false);
		vtsViolationtypeTable.setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		
		vtsViolationtypeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					return;
				}
				checkRestoreButtonEnabled();
			}
		});
		
		vtsViolationtypeScrollpane.setViewportView(vtsViolationtypeTable);

		vtsRestore.setText(ValidateTranslator.getValue("RestoreToDefault"));
		vtsRestore.setEnabled(false);
		vtsRestore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				vtsRestoreActionPerformed();
			}
		});

		vtsRestoreAll.setText(ValidateTranslator.getValue("RestoreAllToDefault"));
		vtsRestoreAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				vtsRestoreAllActionPerformed();
			}
		});

		vtsApply.setText(ValidateTranslator.getValue("Apply"));
		vtsApply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				vtsApplyActionPerformed();
			}
		});

		GroupLayout violationtypeSeverityLayout = new GroupLayout(
				violationtypeSeverity);
		violationtypeSeverityLayout.setHorizontalGroup(
			violationtypeSeverityLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(violationtypeSeverityLayout.createSequentialGroup()
					.addComponent(vtsCategoryScrollpane, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(vtsViolationtypeScrollpane, GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(violationtypeSeverityLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(violationtypeSeverityLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(vtsRestore, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
							.addComponent(vtsRestoreAll, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
						.addComponent(vtsApply, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		violationtypeSeverityLayout.setVerticalGroup(
			violationtypeSeverityLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(vtsCategoryScrollpane)
				.addComponent(vtsViolationtypeScrollpane, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
				.addGroup(violationtypeSeverityLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(vtsRestore, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(vtsRestoreAll, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
					.addComponent(vtsApply)
					.addContainerGap())
		);
		violationtypeSeverity.setLayout(violationtypeSeverityLayout);

		tabbedPane.addTab(ValidateTranslator.getValue("SetViolationSeverity"), violationtypeSeverity);

		avtCategory.setBorder(BorderFactory.createTitledBorder(ValidateTranslator.getValue("Category")));
		avtCategory.setModel(avtCategoryModel);
		avtCategory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		avtCategory.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent evt) {
				avtCategoryValueChanged();
			}
		});
		avtCategoryScrollpane.setViewportView(avtCategory);

		avtViolationtypeTable.setModel(avtViolationtypeModel);
		avtViolationtypeTable.getTableHeader().setReorderingAllowed(false);
		avtViolationtypeTable.setFillsViewportHeight(true);
		avtViolationtypeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		avtRuletypeScrollpane.setViewportView(avtViolationtypeTable);

		avtRuletype.setBorder(BorderFactory.createTitledBorder(
				ValidateTranslator.getValue("Ruletypes")));
		avtRuletype.setModel(avtRuletypeModel);
		vtsViolationtypeTable.getTableHeader().setReorderingAllowed(false);
		avtRuletype.setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		avtRuletype.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent evt) {
				avtViolationtypeTableValueChanged();
			}
		});
		avtViolationtypeScrollpane.setViewportView(avtRuletype);

		avtSelectAll.setText(ValidateTranslator.getValue("SelectAll"));
		avtSelectAll.setActionCommand(ValidateTranslator.getValue("RestoreToDefault"));
		avtSelectAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				avtSelectAllActionPerformed();
			}
		});

		avtDeselectAll.setText(ValidateTranslator.getValue("DeselectAll"));
		avtDeselectAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				avtDeselectAllActionPerformed();
			}
		});

		avtApply.setText(ValidateTranslator.getValue("Apply"));
		avtApply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				avtApplyActionPerformed();
			}
		});

		GroupLayout activeViolationtypeLayout = new GroupLayout(
				activeViolationtype);
		activeViolationtypeLayout.setHorizontalGroup(
			activeViolationtypeLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(activeViolationtypeLayout.createSequentialGroup()
					.addComponent(avtCategoryScrollpane, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(avtViolationtypeScrollpane, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(avtRuletypeScrollpane, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
					.addGap(6)
					.addGroup(activeViolationtypeLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(avtSelectAll, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
						.addGroup(activeViolationtypeLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(avtDeselectAll, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
							.addComponent(avtApply, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		activeViolationtypeLayout.setVerticalGroup(
			activeViolationtypeLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(avtRuletypeScrollpane, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
				.addGroup(Alignment.LEADING, activeViolationtypeLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(avtSelectAll)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(avtDeselectAll)
					.addPreferredGap(ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
					.addComponent(avtApply)
					.addContainerGap())
				.addComponent(avtCategoryScrollpane, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
				.addComponent(avtViolationtypeScrollpane, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
		);
		activeViolationtype.setLayout(activeViolationtypeLayout);

		tabbedPane.addTab(ValidateTranslator.getValue("SetViolationtypeActivePerRuletype"),
						  activeViolationtype);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING).
				addComponent(tabbedPane));
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING).
				addGroup(GroupLayout.Alignment.TRAILING, layout.
				createSequentialGroup().addContainerGap().addComponent(
				tabbedPane)));
	}
	
	private void initializeEverything() {
		loadRuleTypeCategories();
		loadViolationTypeCategories();
	}

	private void rtsRestoreActionPerformed() {		
		taskServiceImpl.restoreToDefault(language, ValidateTranslator.getKey((String) ruletypeModel.getValueAt(rtsRuletypeTable.getSelectedRow(), 0)));
		rtsCategoryValueChanged();
	}

	private void rtsRestoreAllActionPerformed() {
		taskServiceImpl.restoreAllToDefault(language);
		rtsCategoryValueChanged();
	}

	private void rtsApplyActionPerformed() {
		checkRestoreButtonEnabled();
		updateRuletypeSeverities();
	}

	private void vtsRestoreActionPerformed() {		
		taskServiceImpl.restoreToDefault(language, ValidateTranslator.getKey((String) violationtypeModel.getValueAt(vtsViolationtypeTable.getSelectedRow(), 0)));
		vtsCategoryValueChanged();
	}

	private void vtsRestoreAllActionPerformed() {
		taskServiceImpl.restoreAllToDefault(language);
		vtsCategoryValueChanged();
	}

	private void vtsApplyActionPerformed() {
		checkRestoreButtonEnabled();
		updateViolationtypeSeverities();
	}
	
	private void avtSelectAllActionPerformed() {
		for(int i = 0; i < avtViolationtypeModel.getRowCount(); i++){
			avtViolationtypeModel.setValueAt(true, i, 1);
		}
	}
	
	private void avtDeselectAllActionPerformed() {
		for(int i = 0; i < avtViolationtypeModel.getRowCount(); i++){
			avtViolationtypeModel.setValueAt(false, i, 1);
		}
	}

	private void avtApplyActionPerformed() {
		//TODO Make apply for last tab!!!
	}

	private void avtCategoryValueChanged() {
		avtLoadRuletypes((String) avtCategory.getSelectedValue());
	}

	private void avtViolationtypeTableValueChanged() {
		avtLoadViolationtypes((String) avtCategory.getSelectedValue(), (String) avtRuletype.getSelectedValue());
	}

	private void rtsCategoryValueChanged() {
		loadRuleTypes((String) rtsCategory.getSelectedValue());
	}

	private void vtsCategoryValueChanged() {
		checkRestoreButtonEnabled();
		loadViolationType((String) vtsCategory.getSelectedValue());
	}

	private void updateRuletypeSeverities() {
		HashMap<String, Severity> map = new HashMap<String, Severity>();

		for(int i = 0; i < ruletypeModel.getRowCount(); i++){
			map.put(ValidateTranslator.getKey((String) ruletypeModel.getValueAt(i, 0)), (Severity) ruletypeModel.getValueAt(i, 1));
		}
		
		taskServiceImpl.updateSeverityPerType(map, language);
	}
	
	private void updateViolationtypeSeverities() {
		HashMap<String, Severity> map = new HashMap<String, Severity>();

		for(int i = 0; i < violationtypeModel.getRowCount(); i++){
			map.put(ValidateTranslator.getKey((String) violationtypeModel.getValueAt(i, 0)), (Severity) violationtypeModel.getValueAt(i, 1));
		}

		taskServiceImpl.updateSeverityPerType(map, language);
	}
	
	private void loadRuleTypeCategories() {
		rtsCategoryModel.clear();
		for (String categoryString : ruletypes.keySet()) {
			rtsCategoryModel.addElement(ValidateTranslator.getValue(categoryString));
			avtCategoryModel.addElement(ValidateTranslator.getValue(categoryString));
		}
	}

	private void loadRuleTypes(String category) {
		ruletypeModel.clear();
		for (String categoryString : ruletypes.keySet()) {
			if (ValidateTranslator.getValue(categoryString).equals(category)){
				List<RuleType> rules = ruletypes.get(categoryString);
				for(RuleType ruletype: rules){
					Severity severity;
					try{
						severity = taskServiceImpl.getSeverityFromKey(language, ruletype.getKey());
					} catch (Exception e){
						severity = taskServiceImpl.getAllSeverities().get(0);
					}
					ruletypeModel.addRow(new Object[]{ValidateTranslator.getValue(ruletype.getKey()), severity});
				}
			}

		}
		ruletypeModel.checkValuesAreValid();	
	}

	private void loadViolationTypeCategories() {
		vtsCategoryModel.clear();
		for (String categoryString : violationTypes.keySet()) {
			vtsCategoryModel.addElement(ValidateTranslator.getValue(categoryString));
		}

	}

	private void loadViolationType(String category) {
		violationtypeModel.clear();
		for (String categoryString : violationTypes.keySet()) {
			if (ValidateTranslator.getValue(categoryString).equals(category)){
				List<ViolationType> violationtypes = violationTypes.get(categoryString);
				for(ViolationType violationtype: violationtypes){
					Severity severity;
					try{
						severity = taskServiceImpl.getSeverityFromKey(language, violationtype.getViolationtypeKey());
					} catch (Exception e){
						logger.error(e);
						severity = taskServiceImpl.getAllSeverities().get(0);
					}
					violationtypeModel.addRow(new Object[]{ValidateTranslator.getValue(violationtype.getViolationtypeKey()), severity});
				}
			}

		}
		violationtypeModel.checkValuesAreValid();
	}
	
	private void checkRestoreButtonEnabled(){
		if(rtsRuletypeTable.getSelectedRow() > -1){
			rtsRestore.setEnabled(true);
		} else{
			rtsRestore.setEnabled(false);
		}
		
		if(vtsViolationtypeTable.getSelectedRow() > -1){
			vtsRestore.setEnabled(true);
		} else{
			vtsRestore.setEnabled(false);
		}
	}

	private void avtLoadRuletypes(String category) {
		avtRuletypeModel.clear();
		for (String categoryString : ruletypes.keySet()) {
			if (ValidateTranslator.getValue(categoryString).equals(category)){
				List<RuleType> rules = ruletypes.get(categoryString);
				for(RuleType ruletype: rules){
					avtRuletypeModel.addElement(ValidateTranslator.getValue(ruletype.getKey()));
				}
			}

		}
		ruletypeModel.checkValuesAreValid();
	}

	private void avtLoadViolationtypes(String category, String ruletypekey) {
		while(avtViolationtypeModel.getRowCount() > 0){
			avtViolationtypeModel.removeRow(0);
		}
		for (String categoryString : ruletypes.keySet()) {
			if (ValidateTranslator.getValue(categoryString).equals(category)){
				List<RuleType> rules = ruletypes.get(categoryString);
				for(RuleType ruletype: rules){
					if (ValidateTranslator.getValue(ruletype.getKey()).equals(ruletypekey)){
						for(ViolationType violationtype : ruletype.getViolationTypes()){
							avtViolationtypeModel.addRow(new Object[]{ValidateTranslator.getValue(violationtype.getViolationtypeKey()), violationtype.isActive()});
						}
					}
				}
			}
		}
	}
}
