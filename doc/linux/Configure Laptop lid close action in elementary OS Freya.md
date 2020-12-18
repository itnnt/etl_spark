Configure Laptop lid close action in elementary OS Freya
=============================
Laptop users running elementary OS Freya may notice that there is no options in the power panel to configure the laptop lid close action. By default settings of elementary OS, when you close the lid, the laptop goes into suspend mode, which is nothing much sleep mode. Personally, I prefer to leave the laptop do nothing when closed with AC adapter plugged in, and hibernate when closed in battery state. If you are in the same boat as me or you may want to hibernate to save power or something else, you can tweak the laptop lid-close action to whatever you want using dconf editor.

If you have not installed it yet, go ahead and launch ‘Terminal’ and type the following commands one at a time.

```
sudo apt-get update
sudo apt-get install nautilus dconf-tools
```
Go to ‘Applications’ and launch ‘dconf-editor’.

In the left pane, go to : org > gnome > settings daemon > plugins > power. Then in the right pane, click on ‘lid-close-ac-action’ to get several options including blank, suspend, shutdown, hibernate, interactive, nothing, and logout. Select whatever you want from the list and this gets applied immediately for lid when closed with adapter (AC) connected.

In the similar way do the same on item ‘lid-close-battery-action’ and this gets applied when laptop’s lid is closed while on battery.

![](https://cdn.fosslinux.com/wp-content/uploads/2016/03/06234921/Configure-Laptop-Lid-Close-Action-on-elementary-OS.jpg)



