package org.apache.log4j.chainsaw.vfs;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.commons.vfs.FileObject;
import org.apache.log4j.chainsaw.icons.ChainsawIcons;

/**
 * @author psmith
 *
 */
class FileSystemTreePanel extends JPanel {
	
	private final DefaultTreeModel treeModel;
	private final JTree tree = new JTree();
	private final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("VFS");
	
	private static final String TOOLTIP = "Displays all the known VFS repositories";
    
	FileSystemTreePanel(){
		treeModel = new DefaultTreeModel(rootNode);
		initGUI();
	}
	
	/**
	 * 
	 */
	private void initGUI() {
		tree.setModel(treeModel);
        tree.expandPath(new TreePath(rootNode.getPath()));
        
		tree.setRootVisible(false);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(150,400));
		add(new JScrollPane(tree), BorderLayout.CENTER);
		
        // We make the Leaf Icons a nice Server-style icon to represent the repository.
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        renderer.setLeafIcon(ChainsawIcons.ICON_SERVER);
        
		setToolTipText(TOOLTIP);
		tree.setToolTipText(TOOLTIP);
	}
	
	/**
     * Adds a FileObject with a label to the list of known VFS repositories, and makes sure the 
     * Tree gets updated.
	 * @param fileObject
	 */
	public void addFileObject(String name, FileObject fileObject) {
		VFSNode vfsNode = new VFSNode(name, fileObject);
		final DefaultMutableTreeNode node = new DefaultMutableTreeNode(vfsNode);
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				rootNode.add(node);
                tree.makeVisible(new TreePath(node.getPath()));
			}});
	}
	
}