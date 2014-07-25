package wtDesktop;

import javax.swing.JOptionPane;

import wackyTracky.clientbindings.java.WtConnMonitor;
import wackyTracky.clientbindings.java.WtResponse;
import wackyTracky.clientbindings.java.api.SyncManager;
import wackyTracky.clientbindings.java.model.Item;
import wackyTracky.clientbindings.java.model.ItemList;
import wackyTracky.clientbindings.java.model.PendingAction;

public abstract class Actioner {
	private static final SyncManager syncManager = new SyncManager(Main.datastore, Main.session);

	public static void deleteSelectedItem() {
		Item i = WindowMain.instance.panelItems.getselectedItem();
		i.pendingAction = PendingAction.DELETE;

		WindowMain.instance.panelItems.updateUI();

		syncManager.syncNow();

		WindowMain.instance.panelItems.updateUI();
	}

	public static void deleteSelectedList() {
		ItemList list = WindowMain.instance.panelLists.getSelectedList();
		list.pendingAction = PendingAction.DELETE;

		syncManager.syncNow();

		WindowMain.instance.panelLists.updateUI();
	}

	public static void newList() {
		String listName = JOptionPane.showInputDialog("List name?");

		ItemList itemList = new ItemList();
		itemList.title = listName;

		Main.datastore.listOfLists.add(itemList);

		syncManager.syncNow();
	}

	public static void refreshLists() {
		try {
			Main.datastore.listOfLists.merge(Main.session.getListLists());
		} catch (Exception e) {
		}
	}

	public static void renameCurrent() {
		ItemList list = WindowMain.instance.panelLists.getSelectedList();
		String newName = JOptionPane.showInputDialog(null, list.title, list.title);

		Main.session.reqListUpdate(list, newName).submit();

		Actioner.refreshLists();
	}

	public static void toggleForceOffline() {
		WtConnMonitor.toggleForceOffline();

		if (!WtConnMonitor.offline) {
			try {
				WtResponse resp = Main.session.reqAuthenticate(Main.username, Main.password).response();
				resp.assertStatusOkAndJson();
				resp.saveCookiesInSession();
			} catch (Exception e) {
				return;
			}

			Main.syncManager.syncNow();
		}
	}

}
