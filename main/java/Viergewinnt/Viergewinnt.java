package Viergewinnt;

import de.plugin.extra.Inventories;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Viergewinnt {
    public Player player1;
    public Player player2;
    public int inventorySize;
    public String gui_name;
    public ItemStack[] content;
    public ArrayList<Player> hasMove;

    public Viergewinnt(int inventorySize, String gui_name, ItemStack[] content, Player player1, Player player2, ArrayList<Player> hasMove) {
        this.inventorySize = inventorySize;
        this.gui_name = gui_name;
        this.content = content;
        this.player1 = player1;
        this.player2 = player2;
        this.hasMove = hasMove;
    }

    public void openGUI(Player player1, Player player2, Inventory inv) {
        player1.openInventory(inv);
        player2.openInventory(inv);

    }

    public ArrayList<Player> switchPlayerWhoHasMove(ArrayList<Player> hasMove, Player clickedplayer, Player playerwithoutMove) {
        hasMove.remove(clickedplayer);
        hasMove.add(playerwithoutMove);
        return hasMove;
    }

    public ArrayList<Player> initializeHasMove(ArrayList<Player> hasMove, Player player1) {
        player1.sendMessage("bin in initialize");
        hasMove.add(player1);

        return hasMove;
    }

    public Inventory createInventory(int inventorySize, ItemStack[] content, String gui_name) {
        Inventory inv = Bukkit.createInventory(null, inventorySize, gui_name);
        int i = 0;
        for (ItemStack m : content) {
            inv.setItem(i, m);
            i++;
        }
        return inv;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public ArrayList<Player> getHasMove() {
        return hasMove;
    }

    public void setHasMove(ArrayList<Player> noMove) {
        this.hasMove = noMove;
    }

    public void setInventorySize(int inventorySize) {
        this.inventorySize = inventorySize;
    }

    public String getGui_name() {
        return gui_name;
    }

    public void setGui_name(String gui_name) {
        this.gui_name = gui_name;
    }

    public ItemStack[] getContent() {
        return content;
    }

    public void setContent(ItemStack[] content) {
        this.content = content;
    }

    public ItemStack[] editContent(int Slot, ItemStack mat, ItemStack[] content) {
        content[Slot] = mat;
        return content;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public ItemStack createPlayerHead(Player player) {
        ItemStack itemstack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) itemstack.getItemMeta();
        meta.setDisplayName(player.getName());
        meta.setOwningPlayer(player);
        itemstack.setItemMeta(meta);
        return itemstack;
    }

    public void checkForWin(ItemStack[] content, Player player1, Player player2) {
        Player winner = null;
        winner = checkForSameRow(player1, player2, content);
        winner = checkForSameColoumn(player1, player2, content);
        if (winner != null) {
            player1.sendMessage("Gewinner ist " + winner.getName());
            player2.sendMessage("Gewinner ist " + winner.getName());
        }

    }

    private Player checkForSameColoumn(Player player1, Player player2, ItemStack[] content) {
        Player winner = null;
        for (int x = 0; x < 54; x++) {
            if (content[x].equals(content[x + 9]) && content[x].equals(content[x + 18]) && content[x].equals(content[x + 27])) {
                winner = checkforWinner(content[x], player1, player2);
            }
        }

        return winner;
    }

    public Player checkForSameRow(Player player1, Player player2, ItemStack[] content) {
        Player winner = null;
        for (int x = 0; x < 54; x++) {
            if (content[x].equals(content[x + 1]) && content[x].equals(content[x + 2]) && content[x].equals(content[x + 3])) {
                winner = checkforWinner(content[x], player1, player2);
            }
        }
        return winner;
    }

    public Player checkforWinner(ItemStack is, Player player1, Player player2) {
        Player winner = null;
        if (is.equals(new ItemStack(Material.BLUE_STAINED_GLASS_PANE))) {
            winner = player1;
        } else {
            winner = player2;
        }
        return winner;
    }

    public Player checkForDiagonaltoRight() {
        Player winner = null;
        for (int x = 0; x < 54; x++) {
            if (content[x].equals(content[x + 9]) && content[x].equals(content[x + 18]) && content[x].equals(content[x + 27])) {
                winner = checkforWinner(content[x], player1, player2);
            }

        }
        return winner;
    }

    public ItemStack[] setPlayersPoint(ItemStack[] content, Player player, int Slot) {
        int Slotspalte = Slot % 9;
        while (content[Slotspalte].equals(new ItemStack(Material.WHITE_STAINED_GLASS_PANE))) {
            Slotspalte = Slotspalte + 9;
            if (Slotspalte > 54) {
                break;
            }
        }
        Slotspalte -= 9;
        player.sendMessage("geht weiter");
        if (player.equals(this.player1)) {
            content[Slotspalte] = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        } else {
            content[Slotspalte] = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        }
        return content;
    }

    public void renewInventory(Player player1, Player player2, ItemStack[] content, int inventorySize, String gui_name) {
        Inventory inv = Bukkit.createInventory(null, inventorySize, gui_name);
        inv.setContents(content);
        player1.openInventory(inv);
        player2.openInventory(inv);
    }

    public ItemStack[] resetInventory() {
        ItemStack[] content = new ItemStack[54];
        for (int i = 0; i < 54; i++) {
            content[i] = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
            if (i % 9 == 8) {
                content[i] = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            }
            if (i % 9 == 0) {
                content[i] = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            }


        }
        return content;
    }

    public void setBorderforPlayerWithMove(ItemStack[] content, Player playerwithMove) {
        if (this.player1.equals(playerwithMove)) {
            for (int i = 9; i < 54; ) {
                content[i] = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                i += 9;
            }
            for (int i = 17; i < 54; ) {
                content[i] = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                i += 9;
            }
        } else {
            for (int i = 17; i < 54; ) {
                content[i] = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                i += 9;
            }
            for (int i = 9; i < 54; ) {
                content[i] = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                i += 9;
            }
            this.content = content;
        }
    }
}
// Z X X X X X X X Z // 0 1 2 3 4 5 6 7 8
// Y X X X X X X X Y // 9 10 11 12 13 14 15 16 17
// Y X X X X X X X Y // 18 19 20 21 22 23 24 25 26
// Y X X X X X X X Y // 27 28 29 30 31 32 33 34 35
// Y X X X X X X X Y // 36 37 38 39 40 41 42 43 44
// Y X X X X X X X Y//  45 46 47 48 49 50 51 52 53