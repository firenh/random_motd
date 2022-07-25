# <img src="https://github.com/firenh/random_motd/blob/master/src/main/resources/assets/modid/logo_md.png?raw=true" alt="RandomMOTD">

## RandomMOTD ~ Make your server pop from the multiplayer window!

### _Server Side Mod: Does not need installation on client; compatible with vanilla clients!_

<br>

Minecraft Servers have a few precious options available to them to display information on the multiplayer screen--the most important of which are the Server icon and the MOTD, or 'Message of the Day', the two lines of text underneath the server's name giving a descriptor. The problem is, with the MOTD being static, a player may simply not care about what is being displayed as it's typically just the server's name plus a mediocre announcement. 

With RandomMOTD, a new MOTD picked randomly from a list can be sent to the player upon each refresh of the multiplayer screen. This can be used in a variety of ways: you could change the styling of the server's name on every refresh, add easter eggs below the server's name, or cycle through various server announcements. In the config MOTDs are a list of lists of strings: each MOTD sent to the client picks a random MOTD from each sublist and concatenates them together. This enables servers to include both static information and randomized information, such as having the server name on the top line and an easter egg on the bottom line.

In addition, RandomMOTD uses the [PlaceholderAPI library by Patbox](https://placeholders.pb4.eu/) which allows better custom styling including custom color codes and gradients, and in a future update access to information on the server such as Player Count and TPS.

Finally, RandomMOTD also has one more optional feature: randomized server icons. These are off by default, but allow for multiple server icons that are updated with every refresh. These can be used to create variants of the server icon, or just full-on randomized pictures. There is no limit to the amount of MOTDs and icons one can add.

<br>

<details>
    <summary>Config</summary>
The Config file, found in `./config/random_motd.json`, includes 4 options. The config can be reloaded by running the `/reload` command ingame. (Does not modify the vanilla `/reload` command, just hooks on to it).
<br>

* `"motds"`: A list of lists of possible MOTDs. The mod will pick a random MOTD from every part of the list and concatenate them, producing a full MOTD. This feature is mainly so, if you would like to contain a static part of information independent from a random one, you can.

* `"use_randomized_icons"`: A boolean either enabling or disabling the randomized icon feature. Defaults to `false`.

* `"icons"`: A list of paths to server icons. Much like default server icons, they must be 64x64 PNG images.

* `"log_when_loaded"`: A boolean telling whether or not to log for every reload. On by default.

* `"CONFIG_VERSION_DO_NOT_TOUCH_PLS"`: Mod version--please do not touch it.

</details>
<br>
<br>

### Example
Two refreshes of the server with a randomized MOTD and Server Icon

<img src="https://github.com/firenh/random_motd/blob/master/src/main/resources/assets/modid/example.png?raw=true" alt="A server's MOTD and Icon updating dynamically">

_Originally created for my private SMP, but I figured more people may want to use it_