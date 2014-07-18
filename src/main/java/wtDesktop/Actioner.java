package wtDesktop;

import javax.swing.JOptionPane;

import net.minidev.json.JSONObject;
import wackyTracky.clientbindings.java.WtResponse;
import wackyTracky.clientbindings.java.model.DataStore;
import wackyTracky.clientbindings.java.model.ItemList;

public class Actioner {
	public static void newList() {
		String listName = JOptionPane.showInputDialog("List name?");

		ItemList itemList = new ItemList();
		itemList.existsOnServer = false;
		itemList.title = listName;

		DataStore.instance.listOfLists.add(itemList);

		Actioner.sendLocalLists();
	}

	public static void refreshLists() {
		try {
			DataStore.instance.listOfLists.merge(Main.session.getListLists());
		} catch (Exception e) {
		}
	}

	public static void renameCurrent() {
		ItemList list = WindowMain.instance.panelLists.getSelectedList();
		String newName = JOptionPane.showInputDialog(null, list.title, list.title);

		Main.session.reqListUpdate(list, newName).submit();

		Actioner.refreshLists();
	}

	private static void sendLocalLists() {
		for (ItemList l : DataStore.instance.listOfLists.getLists()) {
			if (!l.existsOnServer) {

				try {
					WtResponse resCreate = Main.session.reqCreateList(l.title).response();
					resCreate.assertStatusOkAndJson();

					JSONObject o = resCreate.getContentJsonObject();

					ItemList serverVersion = Main.session.reqGetList(Integer.parseInt(o.get("newListId").toString()));
					l.merge(serverVersion);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
	}
}
