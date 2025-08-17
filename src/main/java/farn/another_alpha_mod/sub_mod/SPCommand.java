package farn.another_alpha_mod.sub_mod;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.mob.SlimeEntity;
import net.minecraft.entity.living.mob.hostile.*;
import net.minecraft.entity.living.mob.passive.animal.ChickenEntity;
import net.minecraft.entity.living.mob.passive.animal.CowEntity;
import net.minecraft.entity.living.mob.passive.animal.PigEntity;
import net.minecraft.entity.living.mob.passive.animal.SheepEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SPCommand {
	public Minecraft minecraft;
	public PlayerEntity playerEntity;
	public World world;
	public float speed = 1.0F;
	public int lastkey;
	public boolean godmode = false;
	public boolean fly = false;
	public boolean instamine = false;
	public boolean helpmsg = true;
	public static HashMap waypoints;
	private final Map itemMap = this.initializeItemMap();
	private final Map blockMap = this.initializeBlockMap();
	public static final String[] direction_tags = new String[]{"south", "west", "north", "east"};

	public SPCommand(Minecraft minecraft1, PlayerEntity entityPlayer2, World world3) {
		waypoints = new HashMap();
		this.world = world3;
		this.minecraft = minecraft1;
		this.playerEntity = entityPlayer2;
		this.loadSettings();
		this.firstModLoad();
	}

	public void saveSettings() {
		try {
			FileWriter fileWriter1 = new FileWriter("Commands_options.txt");
			Throwable throwable2 = null;

			try {
				BufferedWriter bufferedWriter3 = new BufferedWriter(fileWriter1);
				bufferedWriter3.write("fly:" + this.fly);
				bufferedWriter3.newLine();
				bufferedWriter3.write("noclip:" + this.playerEntity.noClip);
				bufferedWriter3.newLine();
				bufferedWriter3.write("godmode:" + this.godmode);
				bufferedWriter3.newLine();
				bufferedWriter3.write("speed:" + this.speed);
				bufferedWriter3.newLine();
				bufferedWriter3.write("instamine:" + this.instamine);
				bufferedWriter3.newLine();
				bufferedWriter3.write("helpmsg:" + this.helpmsg);
				bufferedWriter3.newLine();
				bufferedWriter3.newLine();
				bufferedWriter3.write("Note: This file saves the states of certain commands before quitting a world.");
				bufferedWriter3.newLine();
				bufferedWriter3.write("The command states are reapplied as soon as you load back into a world.");
				bufferedWriter3.close();
			} catch (Throwable throwable12) {
				throwable2 = throwable12;
				throw throwable12;
			} finally {
				if(fileWriter1 != null) {
					if(throwable2 != null) {
						try {
							fileWriter1.close();
						} catch (Throwable throwable11) {
							throwable2.addSuppressed(throwable11);
						}
					} else {
						fileWriter1.close();
					}
				}

			}
		} catch (IOException iOException14) {
			iOException14.printStackTrace();
		}

	}

	public void loadSettings() {
		try {
			FileReader fileReader1 = new FileReader("Commands_options.txt");
			Throwable throwable2 = null;

			try {
				BufferedReader bufferedReader3 = new BufferedReader(fileReader1);

				String string4;
				while((string4 = bufferedReader3.readLine()) != null) {
					String[] string5 = string4.split(":");
					if(string5.length == 2) {
						String string6 = string5[0];
						byte b7 = -1;
						switch(string6.hashCode()) {
						case -1040193391:
							if(string6.equals("noclip")) {
								b7 = 1;
							}
							break;
						case 101491:
							if(string6.equals("fly")) {
								b7 = 0;
							}
							break;
						case 29073838:
							if(string6.equals("instamine")) {
								b7 = 4;
							}
							break;
						case 109641799:
							if(string6.equals("speed")) {
								b7 = 3;
							}
							break;
						case 197143583:
							if(string6.equals("godmode")) {
								b7 = 2;
							}
							break;
						case 805831840:
							if(string6.equals("helpmsg")) {
								b7 = 5;
							}
						}

						switch(b7) {
						case 0:
							this.fly = Boolean.parseBoolean(string5[1]);
							break;
						case 1:
							this.playerEntity.noClip = Boolean.parseBoolean(string5[1]);
							break;
						case 2:
							this.godmode = Boolean.parseBoolean(string5[1]);
							break;
						case 3:
							this.speed = Float.parseFloat(string5[1]);
							break;
						case 4:
							this.instamine = Boolean.parseBoolean(string5[1]);
							break;
						case 5:
							this.helpmsg = Boolean.parseBoolean(string5[1]);
						}
					}
				}

				bufferedReader3.close();
			} catch (Throwable throwable17) {
				throwable2 = throwable17;
				throw throwable17;
			} finally {
				if(fileReader1 != null) {
					if(throwable2 != null) {
						try {
							fileReader1.close();
						} catch (Throwable throwable16) {
							throwable2.addSuppressed(throwable16);
						}
					} else {
						fileReader1.close();
					}
				}

			}
		} catch (FileNotFoundException fileNotFoundException19) {
		} catch (IOException iOException20) {
			iOException20.printStackTrace();
		}

	}

	public void processCommand(String string1) {
		string1 = string1.toLowerCase();
		if(string1.startsWith("/")) {
			string1 = string1.substring(1);
			String[] string2 = string1.trim().split(" ");
			String string5 = string2[0].toLowerCase();
			byte b6 = -1;
			switch(string5.hashCode()) {
			case -1360201941:
				if(string5.equals("teleport")) {
					b6 = 26;
				}
				break;
			case -1339126929:
				if(string5.equals("damage")) {
					b6 = 23;
				}
				break;
			case -1221262756:
				if(string5.equals("health")) {
					b6 = 12;
				}
				break;
			case -1040749301:
				if(string5.equals("setworldspawn")) {
					b6 = 2;
				}
				break;
			case -1040193391:
				if(string5.equals("noclip")) {
					b6 = 19;
				}
				break;
			case -906336856:
				if(string5.equals("search")) {
					b6 = 33;
				}
				break;
			case -891207455:
				if(string5.equals("summon")) {
					b6 = 37;
				}
				break;
			case -712227102:
				if(string5.equals("killmob")) {
					b6 = 34;
				}
				break;
			case -712226109:
				if(string5.equals("killnpc")) {
					b6 = 35;
				}
				break;
			case 3302:
				if(string5.equals("gm")) {
					b6 = 22;
				}
				break;
			case 3364:
				if(string5.equals("im")) {
					b6 = 25;
				}
				break;
			case 3509:
				if(string5.equals("nc")) {
					b6 = 20;
				}
				break;
			case 3677:
				if(string5.equals("sp")) {
					b6 = 17;
				}
				break;
			case 3682:
				if(string5.equals("su")) {
					b6 = 38;
				}
				break;
			case 3708:
				if(string5.equals("tp")) {
					b6 = 27;
				}
				break;
			case 101491:
				if(string5.equals("fly")) {
					b6 = 18;
				}
				break;
			case 107589:
				if(string5.equals("lwp")) {
					b6 = 14;
				}
				break;
			case 111188:
				if(string5.equals("pos")) {
					b6 = 8;
				}
				break;
			case 112794:
				if(string5.equals("rem")) {
					b6 = 6;
				}
				break;
			case 113762:
				if(string5.equals("set")) {
					b6 = 4;
				}
				break;
			case 114319:
				if(string5.equals("sws")) {
					b6 = 3;
				}
				break;
			case 3173137:
				if(string5.equals("give")) {
					b6 = 31;
				}
				break;
			case 3178851:
				if(string5.equals("goto")) {
					b6 = 5;
				}
				break;
			case 3198440:
				if(string5.equals("heal")) {
					b6 = 11;
				}
				break;
			case 3198785:
				if(string5.equals("help")) {
					b6 = 39;
				}
				break;
			case 3208415:
				if(string5.equals("home")) {
					b6 = 7;
				}
				break;
			case 3237038:
				if(string5.equals("info")) {
					b6 = 0;
				}
				break;
			case 3242771:
				if(string5.equals("item")) {
					b6 = 32;
				}
				break;
			case 3291998:
				if(string5.equals("kill")) {
					b6 = 9;
				}
				break;
			case 3526257:
				if(string5.equals("seed")) {
					b6 = 30;
				}
				break;
			case 3556266:
				if(string5.equals("tele")) {
					b6 = 28;
				}
				break;
			case 3560141:
				if(string5.equals("time")) {
					b6 = 29;
				}
				break;
			case 29073838:
				if(string5.equals("instamine")) {
					b6 = 24;
				}
				break;
			case 94746189:
				if(string5.equals("clear")) {
					b6 = 10;
				}
				break;
			case 109638523:
				if(string5.equals("spawn")) {
					b6 = 36;
				}
				break;
			case 109641799:
				if(string5.equals("speed")) {
					b6 = 16;
				}
				break;
			case 197143583:
				if(string5.equals("godmode")) {
					b6 = 21;
				}
				break;
			case 1433904217:
				if(string5.equals("setspawn")) {
					b6 = 1;
				}
				break;
			case 1433907493:
				if(string5.equals("setspeed")) {
					b6 = 15;
				}
				break;
			case 1770461620:
				if(string5.equals("listwaypoints")) {
					b6 = 13;
				}
				break;
			case -1407425048:
				if(string5.equals("wintermode")) {
					b6 = 40;
				}
				break;
			}

			int i3;
			String string9;
			String string28;
			Iterator iterator30;
			String string36;
			byte b38;
			long j55;
			String string62;
			Iterator iterator75;
			String string79;
			boolean z81;
			switch(b6) {
			case 0:
				this.minecraft.gui.addChatMessage("\u00a7eMod version - v1.4");
				this.minecraft.gui.addChatMessage("\u00a7bSingleplayer Command Made by Skellz64");
				this.minecraft.gui.addChatMessage("\u00a7dSomeTweak made by farnfarn02");
				break;
			case 1:
			case 2:
			case 3:
				int i7 = this.minecraft.world.spawnpointX;
				int i4 = this.minecraft.world.spawnpointY;
				int i8 = this.minecraft.world.spawnpointZ;
				if(string2.length == 1) {
					i7 = (int)this.playerEntity.x;
					i4 = (int)this.playerEntity.y;
					i8 = (int)this.playerEntity.z;
				} else {
					if(string2.length != 4) {
						return;
					}

					try {
						i7 = (int)Double.parseDouble(string2[1]);
						i4 = (int)Double.parseDouble(string2[2]);
						i8 = (int)Double.parseDouble(string2[3]);
					} catch (Exception exception46) {
						return;
					}
				}

				this.minecraft.world.spawnpointX = i7;
				this.minecraft.world.spawnpointY = i4;
				this.minecraft.world.spawnpointZ = i8;
				this.minecraft.gui.addChatMessage("Spawn set to x: " + i7 + " y: " + i4 + " z: " + i8);
				break;
			case 4:
				if(string2.length < 2) {
					return;
				}

				string9 = string1.substring(4).trim();
				waypoints.put(string9, new double[]{this.playerEntity.x, this.playerEntity.y, this.playerEntity.z});
				this.minecraft.gui.addChatMessage("Waypoint \"" + string9 + "\" set at: " + this.func_15006_positionAsString());
				break;
			case 5:
				if(string2.length < 2) {
					return;
				}

				string9 = string1.substring(5).trim();
				if(waypoints.containsKey(string9)) {
					double[] d50 = (double[])waypoints.get(string9);
					this.playerEntity.setPosition(d50[0], d50[1], d50[2]);
					this.minecraft.gui.addChatMessage("Moved Player to waypoint \"" + string9 + "\" \u00a77" + this.func_15006_positionAsString());
				} else {
					this.minecraft.gui.addChatMessage("Invalid waypoint name");
				}
				break;
			case 6:
				if(string2.length < 2) {
					return;
				}

				String string10 = string1.substring(4).trim();
				if(waypoints.containsKey(string10)) {
					waypoints.remove(string10);
					this.minecraft.gui.addChatMessage("Waypoint \"" + string10 + "\" removed");
				} else {
					this.minecraft.gui.addChatMessage("Invaid waypoint name");
				}
				break;
			case 7:
				this.playerEntity.setPosition((double)this.minecraft.world.spawnpointX, (double)(this.minecraft.world.spawnpointY - 1), (double)this.minecraft.world.spawnpointZ);
				this.minecraft.gui.addChatMessage("Sent Player to world spawn");
				break;
			case 8:
				String string11 = this.func_15006_positionAsString();
				int i12 = MathHelper.floor((double)(this.minecraft.player.yaw * 4.0F / 360.0F) + 0.5D) & 3;
				this.minecraft.gui.addChatMessage("Current position " + string11 + " \u00a77(facing: " + direction_tags[i12] + ")");
				break;
			case 9:
				this.playerEntity.health = 0;
				float f13 = 1.0F;
				float f14 = 1.0F;
				this.minecraft.world.playSound(this.playerEntity, "random.hurt", f13, f14);
				break;
			case 10:
				for(i3 = 0; i3 < 50; ++i3) {
					this.minecraft.gui.addChatMessage("");
				}

				return;
			case 11:
				if(string2.length < 2) {
					return;
				}

				try {
					this.playerEntity.heal(Integer.parseInt(string2[1]));
					this.minecraft.gui.addChatMessage("Healed " + string2[1] + " health points");
					break;
				} catch (Exception exception45) {
					this.minecraft.gui.addChatMessage("Invalid heal value");
					return;
				}
			case 12:
				if(string2.length < 2) {
					return;
				}

				String string15 = string2[1];

				try {
					int i51 = Integer.parseInt(string15);
					this.playerEntity.health = i51;
					this.minecraft.gui.addChatMessage("Health set to " + i51);
				} catch (NumberFormatException numberFormatException48) {
					if(string15.equalsIgnoreCase("max")) {
						this.playerEntity.health = 20;
						this.minecraft.gui.addChatMessage("Health set to maximum");
					} else if(string15.equalsIgnoreCase("min")) {
						this.playerEntity.health = 1;
						this.minecraft.gui.addChatMessage("Health set to minimum");
					} else if(string15.equalsIgnoreCase("infinite")) {
						this.playerEntity.health = 32767;
						this.minecraft.gui.addChatMessage("Health set to infinite");
					} else {
						this.minecraft.gui.addChatMessage("Invalid health value or term");
					}
				}
				break;
			case 13:
			case 14:
				i3 = waypoints.size();
				if(i3 == 0) {
					this.minecraft.gui.addChatMessage("Create a waypoint first");
					return;
				}

				Iterator iterator16 = waypoints.keySet().iterator();
				String[] string17 = new String[5];
				int i18 = 0;

				while(iterator16.hasNext()) {
					if(string17[i18] == null) {
						string17[i18] = "";
					}

					String string53 = (String)iterator16.next();
					if((string53 + string17[i18]).length() > 98) {
						++i18;
						if(i18 >= string17.length) {
							break;
						}

						string17[i18] = string53;
					} else {
						if(!string17[i18].isEmpty()) {
							string17[i18] = string17[i18] + ", ";
						}

						string17[i18] = string17[i18] + string53;
					}
				}

				for(int i54 = 0; i54 < string17.length && string17[i54] != null; ++i54) {
					this.minecraft.gui.addChatMessage("Waypoints (" + i3 + "): " + string17[i54]);
				}

				return;
			case 15:
			case 16:
			case 17:
				this.speed = 1.0F;
				if(string2.length < 2) {
					return;
				}

				if(string2[1].equalsIgnoreCase("reset")) {
					this.speed = 1.0F;
					this.minecraft.gui.addChatMessage("Speed set to " + this.speed);
					this.saveSettings();
				} else {
					try {
						Float float52 = Float.parseFloat(string2[1]);
						this.speed = float52.floatValue() > 1.0F ? float52.floatValue() : 1.0F;
						this.minecraft.gui.addChatMessage("Speed set to " + this.speed);
						if((double)this.speed == 100.0D) {
							this.minecraft.gui.addChatMessage("\u00a77Warning: extreme speed value");
						}

						this.saveSettings();
					} catch (Exception exception44) {
						this.minecraft.gui.addChatMessage("Invalid speed value");
						return;
					}
				}
				break;
			case 18:
				if(this.playerEntity.noClip) {
					this.minecraft.gui.addChatMessage("\u00a77Turn off noclip first");
				} else {
					this.fly = !this.fly;
					if(this.fly) {
						this.minecraft.gui.addChatMessage("Flying enabled");
						if(this.speed <= 1.0F) {
							this.minecraft.gui.addChatMessage("\u00a77Use /speed to fly faster");
						}

						this.saveSettings();
					} else {
						this.minecraft.gui.addChatMessage("Flying disabled");
						this.saveSettings();
					}
				}
				break;
			case 19:
			case 20:
				this.playerEntity.noClip = !this.playerEntity.noClip;
				if(this.playerEntity.noClip) {
					this.fly = true;
					this.minecraft.gui.addChatMessage("Noclip enabled");
					this.saveSettings();
				} else {
					this.fly = false;
					this.minecraft.gui.addChatMessage("Noclip disabled");
					this.saveSettings();
				}
				break;
			case 21:
			case 22:
				this.godmode = !this.godmode;
				if(this.godmode) {
					this.minecraft.gui.addChatMessage("Godmode enabled");
					this.saveSettings();
				} else {
					this.minecraft.gui.addChatMessage("Godmode disabled");
					this.saveSettings();
				}
				break;
			case 23:
				this.godmode = !this.godmode;
				if(this.godmode) {
					this.minecraft.gui.addChatMessage("Godmode enabled");
				} else {
					this.minecraft.gui.addChatMessage("Godmode disabled");
				}

				this.saveSettings();
				break;
			case 24:
			case 25:
				this.instamine = !this.instamine;
				if(this.instamine) {
					this.minecraft.gui.addChatMessage("Instant mining enabled");
				} else {
					this.minecraft.gui.addChatMessage("Instant mining disabled");
				}

				this.saveSettings();
				break;
			case 26:
			case 27:
			case 28:
				if(string2.length < 4) {
					return;
				}

				double d19;
				double d21;
				double d23;
				try {
					d19 = Double.parseDouble(string2[1]);
					d21 = Double.parseDouble(string2[2]);
					d23 = Double.parseDouble(string2[3]);
				} catch (NumberFormatException numberFormatException43) {
					this.minecraft.gui.addChatMessage("Invalid format/pos");
					return;
				}

				this.playerEntity.setPosition(d19, d21, d23);
				String string25 = this.func_15006_positionAsString();
				this.minecraft.gui.addChatMessage("Teleported Player to " + string25);
				if(d19 >= 1.25005E7D || d19 <= -1.25005E7D || d23 >= 1.25005E7D || d23 <= -1.25005E7D) {
					this.minecraft.gui.addChatMessage("\u00a77Warning: falling block entities may crash the game");
				}
				break;
			case 29:
				if(string2.length == 3 && string2[1].equalsIgnoreCase("set")) {
					if(string2[2].equalsIgnoreCase("day")) {
						this.minecraft.world.ticks = 1000L;
					} else if(string2[2].equalsIgnoreCase("noon")) {
						this.minecraft.world.ticks = 6000L;
					} else if(string2[2].equalsIgnoreCase("night")) {
						this.minecraft.world.ticks = 13000L;
					} else if(string2[2].equalsIgnoreCase("midnight")) {
						this.minecraft.world.ticks = 18000L;
					} else {
						try {
							j55 = Long.parseLong(string2[2]);
							this.minecraft.world.ticks = j55;
						} catch (NumberFormatException numberFormatException42) {
							this.minecraft.gui.addChatMessage("Invalid time value");
							return;
						}
					}

					this.minecraft.gui.addChatMessage("Set the time to " + this.minecraft.world.ticks);
				} else if(string2.length == 2) {
					if(string2[1].equalsIgnoreCase("day")) {
						this.minecraft.world.ticks = 1000L;
					} else if(string2[1].equalsIgnoreCase("noon")) {
						this.minecraft.world.ticks = 6000L;
					} else if(string2[1].equalsIgnoreCase("night")) {
						this.minecraft.world.ticks = 13000L;
					} else if(string2[1].equalsIgnoreCase("midnight")) {
						this.minecraft.world.ticks = 18000L;
					} else {
						try {
							j55 = Long.parseLong(string2[1]);
							this.minecraft.world.ticks = j55;
						} catch (NumberFormatException numberFormatException41) {
							this.minecraft.gui.addChatMessage("Invalid time value");
							return;
						}
					}

					this.minecraft.gui.addChatMessage("Set the time to " + this.minecraft.world.ticks);
				}
				break;
			case 30:
				if(string2.length > 1 && "copy".equalsIgnoreCase(string2[1])) {
					j55 = this.world.seed;
					string28 = String.valueOf(j55);
					StringSelection stringSelection60 = new StringSelection(string28);
					Clipboard clipboard65 = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard65.setContents(stringSelection60, (ClipboardOwner)null);
					this.minecraft.gui.addChatMessage("\u00a77Seed copied to clipboard");
				} else {
					j55 = this.world.seed;
					this.minecraft.gui.addChatMessage("Seed: \u00a7a" + j55);
				}
				break;
			case 31:
			case 32:
				if(string2.length < 2) {
					this.minecraft.gui.addChatMessage("Enter a block/item name or valid ID");
					return;
				}

				int i27 = 1;
				if(string2.length > 2) {
					try {
						i27 = Integer.parseInt(string2[2]);
						if(i27 >= 999) {
							this.minecraft.gui.addChatMessage("\u00a77Warning: lots of items");
						}
					} catch (NumberFormatException numberFormatException40) {
						this.minecraft.gui.addChatMessage("Invalid quantity");
						return;
					}
				}

				try {
					int i26 = Integer.parseInt(string2[1]);
					if(i26 >= 0 && i26 < Block.BY_ID.length && Block.BY_ID[i26] != null) {
						Block block56 = Block.BY_ID[i26];
						String string58 = null;
						iterator30 = this.blockMap.entrySet().iterator();

						while(iterator30.hasNext()) {
							Entry map$Entry67 = (Entry)iterator30.next();
							if(map$Entry67.getValue() == block56) {
								string58 = (String)map$Entry67.getKey();
								break;
							}
						}

						ItemStack itemStack64 = new ItemStack(block56, i27);
						this.minecraft.gui.addChatMessage("Gave " + i27 + " [" + string58 + "] to Player");
						boolean z69 = this.playerEntity.inventory.insertStack(itemStack64);
						if(!z69) {
							this.playerEntity.dropItem(itemStack64);
							this.minecraft.gui.addChatMessage("Inventory full, dropped item");
						} else {
							float f78 = 0.5F;
							float f83 = ((this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.7F + 1.0F) * 2.0F;
							this.minecraft.world.playSound(this.playerEntity, "random.pop", f78, f83);
						}
						break;
					}

					throw new NumberFormatException();
				} catch (NumberFormatException numberFormatException49) {
					StringBuilder stringBuilder57 = new StringBuilder();
					int i61 = 1;

					while(i61 < string2.length) {
						try {
							i27 = Integer.parseInt(string2[i61]);
							break;
						} catch (NumberFormatException numberFormatException47) {
							if(stringBuilder57.length() > 0) {
								stringBuilder57.append(" ");
							}

							stringBuilder57.append(string2[i61]);
							++i61;
						}
					}

					string62 = stringBuilder57.toString().trim().toLowerCase();
					Item item76 = (Item)this.itemMap.get(string62);
					Block block82 = (Block)this.blockMap.get(string62);
					if(item76 == null && block82 == null) {
						this.minecraft.gui.addChatMessage("Invalid item/block name");
						return;
					}

					ItemStack itemStack80;
					if(item76 != null) {
						itemStack80 = new ItemStack(item76, i27);
						string79 = string62;
					} else {
						itemStack80 = new ItemStack(block82, i27);
						string79 = string62;
					}

					this.minecraft.gui.addChatMessage("Gave " + i27 + " [" + string79 + "] to Player");
					z81 = this.playerEntity.inventory.insertStack(itemStack80);
					if(!z81) {
						this.playerEntity.dropItem(itemStack80);
						this.minecraft.gui.addChatMessage("Inventory full, dropped item");
					} else {
						float f84 = 0.5F;
						float f86 = ((this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.7F + 1.0F) * 2.0F;
						this.minecraft.world.playSound(this.playerEntity, "random.pop", f84, f86);
					}
					break;
				}
			case 33:
				if(string2.length < 2) {
					this.minecraft.gui.addChatMessage("Please enter a keyword to search");
					return;
				}

				string28 = String.join(" ", (CharSequence[])Arrays.copyOfRange(string2, 1, string2.length)).toLowerCase();
				ArrayList arrayList29 = new ArrayList();
				iterator30 = this.itemMap.keySet().iterator();

				while(iterator30.hasNext()) {
					string62 = (String)iterator30.next();
					if(string62.toLowerCase().contains(string28)) {
						arrayList29.add(string62);
					}
				}

				ArrayList arrayList59 = new ArrayList();
				Iterator iterator63 = this.blockMap.keySet().iterator();

				while(iterator63.hasNext()) {
					String string68 = (String)iterator63.next();
					if(string68.toLowerCase().contains(string28)) {
						arrayList59.add(string68);
					}
				}

				if(!arrayList29.isEmpty() || !arrayList59.isEmpty()) {
					byte b66 = 75;
					StringBuilder stringBuilder70 = new StringBuilder();
					int i73 = 0;
					this.minecraft.gui.addChatMessage("\u00a77-- \u00a7dsearch results for keyword: \u00a7b\"" + string28 + "\"\u00a77 --");

					for(iterator75 = arrayList29.iterator(); iterator75.hasNext(); i73 += string36.length()) {
						string79 = (String)iterator75.next();
						string36 = "\u00a7e" + string79 + " \u00a77| ";
						if(i73 + string36.length() > b66) {
							this.minecraft.gui.addChatMessage(stringBuilder70.toString());
							stringBuilder70 = new StringBuilder();
							i73 = 0;
						}

						stringBuilder70.append(string36);
					}

					for(iterator75 = arrayList59.iterator(); iterator75.hasNext(); i73 += string36.length()) {
						string79 = (String)iterator75.next();
						string36 = "\u00a7e" + string79 + " \u00a77| ";
						if(i73 + string36.length() > b66) {
							this.minecraft.gui.addChatMessage(stringBuilder70.toString());
							stringBuilder70 = new StringBuilder();
							i73 = 0;
						}

						stringBuilder70.append(string36);
					}

					if(i73 > 0) {
						if(stringBuilder70.length() > 3) {
							stringBuilder70.setLength(stringBuilder70.length() - 3);
						}

						this.minecraft.gui.addChatMessage(stringBuilder70.toString());
					}
				}
				break;
			case 34:
			case 35:
				List list31 = this.minecraft.world.getEntities(this.playerEntity, Box.of(this.playerEntity.x - 50.0D, this.playerEntity.y - 50.0D, this.playerEntity.z - 50.0D, this.playerEntity.x + 50.0D, this.playerEntity.y + 50.0D, this.playerEntity.z + 50.0D));
				int i32 = 0;
				if(string2.length < 2) {
					Iterator iterator71 = list31.iterator();

					while(iterator71.hasNext()) {
						Entity entity74 = (Entity)iterator71.next();
						if(entity74 instanceof LivingEntity) {
							((LivingEntity)entity74).health -= 32767;
							++i32;
						}
					}

					if(i32 > 0) {
						this.minecraft.gui.addChatMessage("Killed all nearby entities");
					} else {
						this.minecraft.gui.addChatMessage("No nearby entities found");
					}
				} else {
					String string72 = string2[1].toLowerCase();
					iterator75 = list31.iterator();

					while(iterator75.hasNext()) {
						Entity entity77 = (Entity)iterator75.next();
						if(entity77 instanceof LivingEntity) {
							z81 = false;
							b38 = -1;
							switch(string72.hashCode()) {
							case -895953179:
								if(string72.equals("spider")) {
									b38 = 7;
								}
								break;
							case -696355290:
								if(string72.equals("zombie")) {
									b38 = 8;
								}
								break;
							case 98699:
								if(string72.equals("cow")) {
									b38 = 1;
								}
								break;
							case 110990:
								if(string72.equals("pig")) {
									b38 = 3;
								}
								break;
							case 98347461:
								if(string72.equals("giant")) {
									b38 = 9;
								}
								break;
							case 109403483:
								if(string72.equals("sheep")) {
									b38 = 4;
								}
								break;
							case 109526728:
								if(string72.equals("slime")) {
									b38 = 6;
								}
								break;
							case 746007989:
								if(string72.equals("chicken")) {
									b38 = 0;
								}
								break;
							case 1028669806:
								if(string72.equals("creeper")) {
									b38 = 2;
								}
								break;
							case 2027747405:
								if(string72.equals("skeleton")) {
									b38 = 5;
								}
							}

							switch(b38) {
							case 0:
								z81 = entity77 instanceof ChickenEntity;
								break;
							case 1:
								z81 = entity77 instanceof CowEntity;
								break;
							case 2:
								z81 = entity77 instanceof CreeperEntity;
								break;
							case 3:
								z81 = entity77 instanceof PigEntity;
								break;
							case 4:
								z81 = entity77 instanceof SheepEntity;
								break;
							case 5:
								z81 = entity77 instanceof SkeletonEntity;
								break;
							case 6:
								z81 = entity77 instanceof SlimeEntity;
								break;
							case 7:
								z81 = entity77 instanceof SpiderEntity;
								break;
							case 8:
								z81 = entity77 instanceof ZombieEntity;
								break;
							case 9:
								z81 = entity77 instanceof GiantEntity;
								break;
							default:
								this.minecraft.gui.addChatMessage("Unknown entity: " + string72);
								return;
							}

							if(z81) {
								((LivingEntity)entity77).health -= 32767;
								++i32;
							}
						}
					}

					if(i32 > 0) {
						this.minecraft.gui.addChatMessage("Killed all nearby " + string72 + " entities");
					} else {
						this.minecraft.gui.addChatMessage("No nearby " + string72 + " entities found");
					}
				}
				break;
			case 36:
			case 37:
			case 38:
				if(string2.length < 2) {
					return;
				}

				Object object33 = null;
				int i34 = 1;
				Integer integer35 = this.minecraft.world.difficulty;

				do {
					string36 = string2[i34];
					Object object37 = null;
					String string85 = string36.toLowerCase();
					byte b39 = -1;
					switch(string85.hashCode()) {
					case -1993439786:
						if(string85.equals("herobrine")) {
							b39 = 10;
						}
						break;
					case -985752863:
						if(string85.equals("player")) {
							b39 = 11;
						}
						break;
					case -895953179:
						if(string85.equals("spider")) {
							b39 = 7;
						}
						break;
					case -696355290:
						if(string85.equals("zombie")) {
							b39 = 8;
						}
						break;
					case 98699:
						if(string85.equals("cow")) {
							b39 = 1;
						}
						break;
					case 110990:
						if(string85.equals("pig")) {
							b39 = 3;
						}
						break;
					case 98347461:
						if(string85.equals("giant")) {
							b39 = 9;
						}
						break;
					case 109403483:
						if(string85.equals("sheep")) {
							b39 = 4;
						}
						break;
					case 109526728:
						if(string85.equals("slime")) {
							b39 = 6;
						}
						break;
					case 746007989:
						if(string85.equals("chicken")) {
							b39 = 0;
						}
						break;
					case 1028669806:
						if(string85.equals("creeper")) {
							b39 = 2;
						}
						break;
					case 2027747405:
						if(string85.equals("skeleton")) {
							b39 = 5;
						}
					}

					switch(b39) {
					case 0:
						object37 = new ChickenEntity(this.minecraft.world);
						break;
					case 1:
						object37 = new CowEntity(this.minecraft.world);
						break;
					case 2:
						object37 = new CreeperEntity(this.minecraft.world);
						if(integer35.intValue() == 0) {
							this.minecraft.gui.addChatMessage("\u00a77Difficulty is currently set to peaceful");
						}
						break;
					case 3:
						object37 = new PigEntity(this.minecraft.world);
						break;
					case 4:
						object37 = new SheepEntity(this.minecraft.world);
						break;
					case 5:
						object37 = new SkeletonEntity(this.minecraft.world);
						if(integer35.intValue() == 0) {
							this.minecraft.gui.addChatMessage("\u00a77Difficulty is currently set to peaceful");
						}
						break;
					case 6:
						object37 = new SlimeEntity(this.minecraft.world);
						break;
					case 7:
						object37 = new SpiderEntity(this.minecraft.world);
						if(integer35.intValue() == 0) {
							this.minecraft.gui.addChatMessage("\u00a77Difficulty is currently set to peaceful");
						}
						break;
					case 8:
						object37 = new ZombieEntity(this.minecraft.world);
						if(integer35.intValue() == 0) {
							this.minecraft.gui.addChatMessage("\u00a77Difficulty is currently set to peaceful");
						}
						break;
					case 9:
						object37 = new GiantEntity(this.minecraft.world);
						if(integer35.intValue() == 0) {
							this.minecraft.gui.addChatMessage("\u00a77Difficulty is currently set to peaceful");
						}
						break;
					case 10:
						this.minecraft.gui.addChatMessage("\u00a7cHerobrine joined the game");
						break;
					case 11:
						object37 = new PlayerEntity(this.minecraft.world);
						this.minecraft.gui.addChatMessage("\u00a7ePlayer joined the game");
						this.minecraft.gui.addChatMessage("\u00a77Warning: killing player will throw NullPointerException");
						break;
					default:
						this.minecraft.gui.addChatMessage("Unknown entity: " + string36);
					}

					if(object37 != null) {
						((LivingEntity)object37).teleport(this.playerEntity.x, this.playerEntity.y, this.playerEntity.z, this.playerEntity.yaw, 0.0F);
						this.minecraft.world.addEntity((Entity)object37);
						if(object33 != null) {
							((LivingEntity)object37).startRiding((Entity)object33);
						}

						object33 = object37;
					}

					++i34;
				} while(i34 < string2.length);

				return;
			case 39:
				if(string2.length > 1) {
					string36 = string2[1].toLowerCase();
					b38 = -1;
					switch(string36.hashCode()) {
					case -2086047308:
						if(string36.equals("instantmine")) {
							b38 = 25;
						}
						break;
					case -2078905201:
						if(string36.equals("time set")) {
							b38 = 38;
						}
						break;
					case -1360201941:
						if(string36.equals("teleport")) {
							b38 = 34;
						}
						break;
					case -1339126929:
						if(string36.equals("damage")) {
							b38 = 5;
						}
						break;
					case -1221262756:
						if(string36.equals("health")) {
							b38 = 9;
						}
						break;
					case -1040749301:
						if(string36.equals("setworldspawn")) {
							b38 = 28;
						}
						break;
					case -1040193391:
						if(string36.equals("noclip")) {
							b38 = 22;
						}
						break;
					case -906336856:
						if(string36.equals("search")) {
							b38 = 3;
						}
						break;
					case -891207455:
						if(string36.equals("summon")) {
							b38 = 40;
						}
						break;
					case -712227102:
						if(string36.equals("killmob")) {
							b38 = 14;
						}
						break;
					case -712226109:
						if(string36.equals("killnpc")) {
							b38 = 15;
						}
						break;
					case 3302:
						if(string36.equals("gm")) {
							b38 = 6;
						}
						break;
					case 3364:
						if(string36.equals("im")) {
							b38 = 26;
						}
						break;
					case 3509:
						if(string36.equals("nc")) {
							b38 = 23;
						}
						break;
					case 3677:
						if(string36.equals("sp")) {
							b38 = 32;
						}
						break;
					case 3682:
						if(string36.equals("su")) {
							b38 = 41;
						}
						break;
					case 3708:
						if(string36.equals("tp")) {
							b38 = 35;
						}
						break;
					case 101491:
						if(string36.equals("fly")) {
							b38 = 21;
						}
						break;
					case 107589:
						if(string36.equals("lwp")) {
							b38 = 17;
						}
						break;
					case 111188:
						if(string36.equals("pos")) {
							b38 = 18;
						}
						break;
					case 112794:
						if(string36.equals("rem")) {
							b38 = 19;
						}
						break;
					case 113762:
						if(string36.equals("set")) {
							b38 = 20;
						}
						break;
					case 114319:
						if(string36.equals("sws")) {
							b38 = 29;
						}
						break;
					case 3173137:
						if(string36.equals("give")) {
							b38 = 1;
						}
						break;
					case 3178851:
						if(string36.equals("goto")) {
							b38 = 7;
						}
						break;
					case 3198440:
						if(string36.equals("heal")) {
							b38 = 8;
						}
						break;
					case 3198785:
						if(string36.equals("help")) {
							b38 = 10;
						}
						break;
					case 3208415:
						if(string36.equals("home")) {
							b38 = 11;
						}
						break;
					case 3237038:
						if(string36.equals("info")) {
							b38 = 12;
						}
						break;
					case 3242771:
						if(string36.equals("item")) {
							b38 = 2;
						}
						break;
					case 3291998:
						if(string36.equals("kill")) {
							b38 = 13;
						}
						break;
					case 3526257:
						if(string36.equals("seed")) {
							b38 = 33;
						}
						break;
					case 3556266:
						if(string36.equals("tele")) {
							b38 = 36;
						}
						break;
					case 3560141:
						if(string36.equals("time")) {
							b38 = 37;
						}
						break;
					case 29073838:
						if(string36.equals("instamine")) {
							b38 = 24;
						}
						break;
					case 94746189:
						if(string36.equals("clear")) {
							b38 = 0;
						}
						break;
					case 109638523:
						if(string36.equals("spawn")) {
							b38 = 39;
						}
						break;
					case 109641799:
						if(string36.equals("speed")) {
							b38 = 31;
						}
						break;
					case 197143583:
						if(string36.equals("godmode")) {
							b38 = 4;
						}
						break;
					case 1433904217:
						if(string36.equals("setspawn")) {
							b38 = 27;
						}
						break;
					case 1433907493:
						if(string36.equals("setspeed")) {
							b38 = 30;
						}
						break;
					case 1770461620:
						if(string36.equals("listwaypoints")) {
							b38 = 16;
						}
					}

					switch(b38) {
					case 0:
						this.func_15004_helpMessage("Clears the chat console", "clear", "/clear", "clear");
						return;
					case 1:
					case 2:
						this.func_15004_helpMessage("Gives player item, if quantity isn\u2019t specified 1 of that item", "give/item <ITEMCODE|ITEMNAME> <QUANTITY>", "/give 1 64, /item diamond_sword", "give");
						this.minecraft.gui.addChatMessage("\u00a77(Item names use underscores w/modern naming conventions, ");
						this.minecraft.gui.addChatMessage("\u00a77use \u00a7e/search \u00a77if you are having trouble finding an item)");
						return;
					case 3:
						this.minecraft.gui.addChatMessage("\u00a77-- search --");
						this.minecraft.gui.addChatMessage("\u00a73Description:");
						this.minecraft.gui.addChatMessage("Search for an item name using a keyword");
						this.minecraft.gui.addChatMessage("\u00a73Syntax:");
						this.minecraft.gui.addChatMessage("search");
						this.minecraft.gui.addChatMessage("\u00a73Example:");
						this.minecraft.gui.addChatMessage("/search diamond");
						return;
					case 4:
					case 5:
					case 6:
						this.minecraft.gui.addChatMessage("\u00a77-- godmode --");
						this.minecraft.gui.addChatMessage("\u00a73Description:");
						this.minecraft.gui.addChatMessage("Disables all forms of damage");
						this.minecraft.gui.addChatMessage("\u00a73Syntax:");
						this.minecraft.gui.addChatMessage("godmode/damage/gm");
						this.minecraft.gui.addChatMessage("\u00a73Example:");
						this.minecraft.gui.addChatMessage("/godmode \u00a77(retype /godmode to disable)");
						return;
					case 7:
						this.func_15004_helpMessage("Warps the player to a set waypoint", "goto <NAME>", "/goto ...", "goto");
						return;
					case 8:
						this.func_15004_helpMessage("Heals the player by a specified number of health points", "heal <VALUE>", "/heal 5", "heal");
						return;
					case 9:
						this.func_15004_helpMessage("Sets the health of the player using a value or query", "health <min|max|infinite|VALUE>", "/health max, /health 20", "health");
						return;
					case 10:
						this.func_15004_helpMessage("Brings up a help message", "help <COMMANDNAME>", "/help give", "help");
						return;
					case 11:
						this.func_15004_helpMessage("Teleport to the world spawn point", "home", "/home", "home");
						return;
					case 12:
						this.minecraft.gui.addChatMessage("\u00a77-- info --");
						this.minecraft.gui.addChatMessage("\u00a73Description:");
						this.minecraft.gui.addChatMessage("Contains general information about the mod");
						this.minecraft.gui.addChatMessage("\u00a77(version, game version, and credits)");
						this.minecraft.gui.addChatMessage("\u00a73Syntax:");
						this.minecraft.gui.addChatMessage("info");
						this.minecraft.gui.addChatMessage("\u00a73Example:");
						this.minecraft.gui.addChatMessage("/info");
						return;
					case 13:
						this.func_15004_helpMessage("Kills the current player", "kill", "/kill", "kill");
						return;
					case 14:
					case 15:
						this.func_15004_helpMessage("Kills all mobs around the player, or specify a mob type to kill", "killmob/killnpc", "/killmob, /killmob pig", "killmob");
						return;
					case 16:
					case 17:
						this.func_15004_helpMessage("Lists all the waypoints currently configured", "listwaypoints/lwp", "/listwaypoints", "listwaypoints");
						return;
					case 18:
						this.func_15004_helpMessage("Gives the current player position, and direction facing", "pos", "/pos", "pos");
						return;
					case 19:
						this.func_15004_helpMessage("Removes a previously set waypoint", "rem <NAME>", "/rem ...", "rem");
						return;
					case 20:
						this.func_15004_helpMessage("Sets a named waypoint in the world using current position", "set <NAME>", "/set ...", "set");
						return;
					case 21:
						this.minecraft.gui.addChatMessage("\u00a77-- fly --");
						this.minecraft.gui.addChatMessage("\u00a73Description:");
						this.minecraft.gui.addChatMessage("Allows the player to fly \u00a77(up = space, down = r_shift)");
						this.minecraft.gui.addChatMessage("\u00a73Syntax:");
						this.minecraft.gui.addChatMessage("fly");
						this.minecraft.gui.addChatMessage("\u00a73Example:");
						this.minecraft.gui.addChatMessage("/fly \u00a77(retype /fly to disable)");
						return;
					case 22:
					case 23:
						this.minecraft.gui.addChatMessage("\u00a77-- noclip --");
						this.minecraft.gui.addChatMessage("\u00a73Description:");
						this.minecraft.gui.addChatMessage("Disables block collision and suffocation");
						this.minecraft.gui.addChatMessage("\u00a77(fly set to on automatically)");
						this.minecraft.gui.addChatMessage("\u00a73Syntax:");
						this.minecraft.gui.addChatMessage("noclip/nc");
						this.minecraft.gui.addChatMessage("\u00a73Example:");
						this.minecraft.gui.addChatMessage("/noclip \u00a77(retype /noclip to disable)");
						return;
					case 24:
					case 25:
					case 26:
						this.minecraft.gui.addChatMessage("\u00a77-- instamine --");
						this.minecraft.gui.addChatMessage("\u00a73Description:");
						this.minecraft.gui.addChatMessage("Instantly break blocks");
						this.minecraft.gui.addChatMessage("\u00a73Syntax:");
						this.minecraft.gui.addChatMessage("instamine/instantmine/im");
						this.minecraft.gui.addChatMessage("\u00a73Example:");
						this.minecraft.gui.addChatMessage("/instamine \u00a77(retype /instamine to disable)");
						return;
					case 27:
					case 28:
					case 29:
						this.func_15004_helpMessage("Sets the current position as the spawn point, or specify XYZ   to set a specific spawn location", "setspawn/setworldspawn/sws <X> <Y> <Z>", "/setspawn 0 66 0", "setspawn");
						return;
					case 30:
					case 31:
					case 32:
						this.func_15004_helpMessage("Sets the players movement speed", "setspeed/speed/sp <VALUE|reset>", "/speed 5", "speed");
						return;
					case 33:
						this.func_15004_helpMessage("Gives the current seed, or use \"copy\" to copy the seed to     the clipboard", "seed <\u00a77null\u00a7f|copy>", "/seed, /seed copy", "seed");
						return;
					case 34:
					case 35:
					case 36:
						this.func_15004_helpMessage("Teleport to a location using XYZ coordinates", "teleport/tele/tp <X> <Y> <Z>", "/teleport 0 70 0", "teleport");
						return;
					case 37:
					case 38:
						this.func_15004_helpMessage("Sets the world time using a value or query", "time/time set <day|noon|night|midnight|VALUE>", "/time day, /time set 1000", "time");
						return;
					case 39:
					case 40:
					case 41:
						this.minecraft.gui.addChatMessage("\u00a77-- spawn --");
						this.minecraft.gui.addChatMessage("\u00a73Description:");
						this.minecraft.gui.addChatMessage("Spawns the specified creature");
						this.minecraft.gui.addChatMessage("\u00a77(chicken, cow, creeper, pig, sheep, skeleton,");
						this.minecraft.gui.addChatMessage("\u00a77slime, spider, zombie, giant, player)");
						this.minecraft.gui.addChatMessage("\u00a73Syntax:");
						this.minecraft.gui.addChatMessage("spawn/summon/su <CREATURENAME>");
						this.minecraft.gui.addChatMessage("\u00a73Example:");
						this.minecraft.gui.addChatMessage("/spawn zombie");
						return;
					default:
						this.minecraft.gui.addChatMessage("Unknown command for help: " + string36);
					}
				} else {
					this.minecraft.gui.addChatMessage(" ");
					this.minecraft.gui.addChatMessage("\u00a7eCommands: \u00a77/<COMMANDNAME>");
					this.minecraft.gui.addChatMessage("clear, fly, give, godmode, goto, heal, health, help, home,");
					this.minecraft.gui.addChatMessage("info, instamine, kill, killmob, listwaypoints, noclip, pos, rem,");
					this.minecraft.gui.addChatMessage("search, seed, set, setspawn, speed, spawn, teleport, time, wintermode");
					this.minecraft.gui.addChatMessage(" ");
					this.minecraft.gui.addChatMessage("\u00a77Use \"/help <COMMANDNAME>\" for more information about a   ");
					this.minecraft.gui.addChatMessage("\u00a77command");
					this.minecraft.gui.addChatMessage(" ");
				}
				break;
			case 40:
				this.world.f_3034762 = !this.world.f_3034762;
				if(this.world.f_3034762) {
					this.minecraft.gui.addChatMessage("Enabled winter mode");
				} else {
					this.minecraft.gui.addChatMessage("Disabled winter mode");
				}
				break;
			default:
				this.minecraft.gui.addChatMessage("Unknown command name");
			}
		} else {
			this.minecraft.gui.addChatMessage("Must use \"/\" for commands");
		}

	}

	public void func_21034_c(String string1) {
		this.minecraft.gui.addChatMessage("\u00a74" + string1);
	}

	public void sendModLoadedMessage() {
		if(this.minecraft != null && this.minecraft.gui != null) {
			this.minecraft.gui.addChatMessage("\u00a7aOld-MC-Commands \u00a77-- \u00a7bgithub.com/Skellz64");
		}

	}

	public void firstModLoad() {
		if(this.helpmsg) {
			this.minecraft.gui.addChatMessage("\u00a77Press T and type /help to begin");
			this.helpmsg = false;
			this.saveSettings();
		}

	}

	private String func_15006_positionAsString() {
		int i1 = (int)Math.floor(this.playerEntity.x);
		int i2 = (int)Math.floor(this.playerEntity.y);
		int i3 = (int)Math.floor(this.playerEntity.z);
		return "" + i1 + ", " + i2 + ", " + i3;
	}

	public void func_15004_helpMessage(String string1, String string2, String string3, String string4) {
		this.minecraft.gui.addChatMessage("\u00a77-- " + string4 + " --");
		this.minecraft.gui.addChatMessage("\u00a73Description:");
		this.minecraft.gui.addChatMessage("\t" + string1);
		this.minecraft.gui.addChatMessage("\u00a73Syntax:");
		this.minecraft.gui.addChatMessage("\t" + string2);
		this.minecraft.gui.addChatMessage("\u00a73Example:");
		this.minecraft.gui.addChatMessage("\t" + string3);
	}

	public void func_15005_readWaypointsFromNBT(File file1) {
		File file2 = new File(file1, "waypoints.dat");
		if(!file2.exists()) {
			waypoints.clear();
		} else {
			NbtCompound nBTTagCompound3;
			try {
				nBTTagCompound3 = NbtIo.readCompressed(new FileInputStream(file2));
			} catch (Exception exception14) {
				return;
			}

			NbtList nBTTagList4 = nBTTagCompound3.getList("waypoints");

			for(int i5 = 0; i5 < nBTTagList4.size(); ++i5) {
				NbtCompound nBTTagCompound6 = (NbtCompound)nBTTagList4.get(i5);
				String string7 = nBTTagCompound6.getString("Name");
				double d8 = nBTTagCompound6.getDouble("X");
				double d10 = nBTTagCompound6.getDouble("Y");
				double d12 = nBTTagCompound6.getDouble("Z");
				waypoints.put(string7, new double[]{d8, d10, d12});
			}

		}
	}

	public void func_15003_crc_entry(File file1) {
		if(waypoints.size() != 0) {
			NbtList nBTTagList2 = new NbtList();
			Iterator iterator3 = waypoints.keySet().iterator();

			NbtCompound nBTTagCompound4;
			while(iterator3.hasNext()) {
				nBTTagCompound4 = new NbtCompound();
				String string5 = (String)iterator3.next();
				nBTTagCompound4.putString("Name", string5);
				nBTTagCompound4.putDouble("X", ((double[])waypoints.get(string5))[0]);
				nBTTagCompound4.putDouble("Y", ((double[])waypoints.get(string5))[1]);
				nBTTagCompound4.putDouble("Z", ((double[])waypoints.get(string5))[2]);
				nBTTagList2.add(nBTTagCompound4);
			}

			nBTTagCompound4 = new NbtCompound();
			nBTTagCompound4.put("waypoints", nBTTagList2);
			File file10 = new File(file1, "waypoints.dat_new");
			File file6 = new File(file1, "waypoints.dat_old");
			File file7 = new File(file1, "waypoints.dat");

			try {
				NbtIo.writeCompressed(nBTTagCompound4, new FileOutputStream(file10));
				if(file6.exists()) {
					file6.delete();
				}

				file7.renameTo(file6);
				if(file7.exists()) {
					file7.delete();
				}

				file10.renameTo(file7);
				if(file10.exists()) {
					file10.delete();
				}
			} catch (Exception exception9) {
			}
		}

	}

	private Map initializeItemMap() {
		HashMap hashMap1 = new HashMap();
		hashMap1.put("iron_shovel", Item.IRON_SHOVEL);
		hashMap1.put("iron_pickaxe", Item.IRON_PICKAXE);
		hashMap1.put("iron_axe", Item.IRON_AXE);
		hashMap1.put("flint_and_steel", Item.FLINT_AND_STEEL);
		hashMap1.put("apple", Item.APPLE);
		hashMap1.put("bow", Item.BOW);
		hashMap1.put("arrow", Item.ARROW);
		hashMap1.put("coal", Item.COAL);
		hashMap1.put("diamond", Item.DIAMOND);
		hashMap1.put("iron_ingot", Item.IRON_INGOT);
		hashMap1.put("gold_ingot", Item.GOLD_INGOT);
		hashMap1.put("iron_sword", Item.IRON_SWORD);
		hashMap1.put("wooden_sword", Item.WOODEN_SWORD);
		hashMap1.put("wooden_shovel", Item.WOODEN_SHOVEL);
		hashMap1.put("wooden_pickaxe", Item.WOODEN_PICKAXE);
		hashMap1.put("wooden_axe", Item.WOODEN_AXE);
		hashMap1.put("stone_sword", Item.STONE_SWORD);
		hashMap1.put("stone_shovel", Item.STONE_SHOVEL);
		hashMap1.put("stone_pickaxe", Item.STONE_PICKAXE);
		hashMap1.put("stone_axe", Item.STONE_AXE);
		hashMap1.put("diamond_sword", Item.DIAMOND_SWORD);
		hashMap1.put("diamond_shovel", Item.DIAMOND_SHOVEL);
		hashMap1.put("diamond_pickaxe", Item.DIAMOND_PICKAXE);
		hashMap1.put("diamond_axe", Item.DIAMOND_AXE);
		hashMap1.put("stick", Item.STICK);
		hashMap1.put("bowl", Item.BOWL);
		hashMap1.put("mushroom_stew", Item.MUSHROOM_STEW);
		hashMap1.put("golden_sword", Item.GOLDEN_SWORD);
		hashMap1.put("golden_shovel", Item.GOLDEN_SHOVEL);
		hashMap1.put("golden_pickaxe", Item.GOLDEN_PICKAXE);
		hashMap1.put("golden_axe", Item.GOLDEN_AXE);
		hashMap1.put("sign", Item.SIGN);
		hashMap1.put("string", Item.STRING);
		hashMap1.put("feather", Item.FEATHER);
		hashMap1.put("gunpowder", Item.GUNPOWDER);
		hashMap1.put("wooden_hoe", Item.WOODEN_HOE);
		hashMap1.put("stone_hoe", Item.STONE_HOE);
		hashMap1.put("iron_hoe", Item.IRON_HOE);
		hashMap1.put("diamond_hoe", Item.DIAMOND_HOE);
		hashMap1.put("golden_hoe", Item.GOLDEN_HOE);
		hashMap1.put("wheat_seeds", Item.WHEAT_SEEDS);
		hashMap1.put("wheat", Item.WHEAT);
		hashMap1.put("bread", Item.BREAD);
		hashMap1.put("leather_helmet", Item.LEATHER_HELMET);
		hashMap1.put("leather_chestplate", Item.LEATHER_CHESTPLATE);
		hashMap1.put("leather_leggings", Item.LEATHER_LEGGINGS);
		hashMap1.put("leather_boots", Item.LEATHER_BOOTS);
		hashMap1.put("chainmail_helmet", Item.CHAINMAIL_HELMET);
		hashMap1.put("chainmail_chestplate", Item.CHAINMAIL_CHESTPLATE);
		hashMap1.put("chainmail_leggings", Item.CHAINMAIL_LEGGINGS);
		hashMap1.put("chainmail_boots", Item.CHAINMAIL_BOOTS);
		hashMap1.put("iron_helmet", Item.IRON_HELMET);
		hashMap1.put("iron_chestplate", Item.IRON_CHESTPLATE);
		hashMap1.put("iron_leggings", Item.IRON_LEGGINGS);
		hashMap1.put("iron_boots", Item.IRON_BOOTS);
		hashMap1.put("diamond_helmet", Item.DIAMOND_HELMET);
		hashMap1.put("diamond_chestplate", Item.DIAMOND_CHESTPLATE);
		hashMap1.put("diamond_leggings", Item.DIAMOND_LEGGINGS);
		hashMap1.put("diamond_boots", Item.DIAMOND_BOOTS);
		hashMap1.put("golden_helmet", Item.GOLDEN_HELMET);
		hashMap1.put("golden_chestplate", Item.GOLDEN_CHESTPLATE);
		hashMap1.put("golden_leggings", Item.GOLDEN_LEGGINGS);
		hashMap1.put("golden_boots", Item.GOLDEN_BOOTS);
		hashMap1.put("flint", Item.FLINT);
		hashMap1.put("porkchop", Item.PORKCHOP);
		hashMap1.put("cooked_porkchop", Item.COOKED_PORKCHOP);
		hashMap1.put("painting", Item.PAINTING);
		hashMap1.put("golden_apple", Item.GOLDEN_APPLE);
		hashMap1.put("door", Item.WOODEN_DOOR);
		hashMap1.put("bucket", Item.BUCKET);
		hashMap1.put("water_bucket", Item.WATER_BUCKET);
		hashMap1.put("lava_bucket", Item.LAVA_BUCKET);
		hashMap1.put("minecart", Item.MINECART);
		hashMap1.put("saddle", Item.SADDLE);
		hashMap1.put("iron_door", Item.IRON_DOOR);
		hashMap1.put("redstone", Item.REDSTONE);
		hashMap1.put("snowball", Item.SNOWBALL);
		hashMap1.put("boat", Item.BOAT);
		hashMap1.put("leather", Item.LEATHER);
		hashMap1.put("milk", Item.MILK_BUCKET);
		hashMap1.put("brick", Item.BRICK);
		hashMap1.put("clay", Item.CLAY);
		hashMap1.put("reed", Item.REEDS);
		hashMap1.put("paper", Item.PAPER);
		hashMap1.put("book", Item.BOOK);
		hashMap1.put("slimeball", Item.SLIMEBALL);
		hashMap1.put("minecart_chest", Item.CHEST_MINECART);
		hashMap1.put("minecart_furnace", Item.FURNACE_MINECART);
		hashMap1.put("egg", Item.EGG);
		hashMap1.put("compass", Item.COMPASS);
		hashMap1.put("fishing_rod", Item.FISHING_ROD);
		hashMap1.put("13disc", Item.RECORD_13);
		hashMap1.put("cat", Item.RECORD_CAT);
		return hashMap1;
	}

	private Map initializeBlockMap() {
		HashMap hashMap1 = new HashMap();
		hashMap1.put("stone", Block.STONE);
		hashMap1.put("grass", Block.GRASS);
		hashMap1.put("dirt", Block.DIRT);
		hashMap1.put("cobblestone", Block.COBBLESTONE);
		hashMap1.put("plank", Block.PLANKS);
		hashMap1.put("sapling", Block.SAPLING);
		hashMap1.put("bedrock", Block.BEDROCK);
		hashMap1.put("water", Block.WATER);
		hashMap1.put("water_moving", Block.FLOWING_WATER);
		hashMap1.put("lava", Block.LAVA);
		hashMap1.put("lava_moving", Block.FLOWING_LAVA);
		hashMap1.put("sand", Block.SAND);
		hashMap1.put("gravel", Block.GRAVEL);
		hashMap1.put("gold_ore", Block.GOLD_ORE);
		hashMap1.put("iron_ore", Block.IRON_ORE);
		hashMap1.put("coal_ore", Block.COAL_ORE);
		hashMap1.put("oak_log", Block.LOG);
		hashMap1.put("oak_leaves", Block.LEAVES);
		hashMap1.put("sponge", Block.SPONGE);
		hashMap1.put("glass", Block.GLASS);
		hashMap1.put("wool", Block.WOOL);
		hashMap1.put("dandelion", Block.YELLOW_FLOWER);
		hashMap1.put("rose", Block.RED_FLOWER);
		hashMap1.put("brown_mushroom", Block.BROWN_MUSHROOM);
		hashMap1.put("red_mushroom", Block.RED_MUSHROOM);
		hashMap1.put("block_of_gold", Block.GOLD_BLOCK);
		hashMap1.put("block_of_iron", Block.IRON_BLOCK);
		hashMap1.put("slab", Block.STONE_SLAB);
		hashMap1.put("double_slab", Block.DOUBLE_STONE_SLAB);
		hashMap1.put("bricks", Block.BRICKS);
		hashMap1.put("tnt", Block.TNT);
		hashMap1.put("bookshelf", Block.BOOKSHELF);
		hashMap1.put("mossy_cobblestone", Block.MOSSY_COBBLESTONE);
		hashMap1.put("obsidian", Block.OBSIDIAN);
		hashMap1.put("torch", Block.TORCH);
		hashMap1.put("fire", Block.FIRE);
		hashMap1.put("monster_spawner", Block.MOB_SPAWNER);
		hashMap1.put("oak_stairs", Block.OAK_STAIRS);
		hashMap1.put("chest", Block.CHEST);
		hashMap1.put("redstone_dust", Block.REDSTONE_WIRE);
		hashMap1.put("diamond_ore", Block.DIAMOND_ORE);
		hashMap1.put("block_of_diamond", Block.DIAMOND_BLOCK);
		hashMap1.put("crafting_table", Block.CRAFTING_TABLE);
		hashMap1.put("crops", Block.WHEAT);
		hashMap1.put("farmland", Block.FARMLAND);
		hashMap1.put("furnace", Block.FURNACE);
		hashMap1.put("furnace_active", Block.LIT_FURNACE);
		hashMap1.put("oak_sign_block", Block.STANDING_SIGN);
		hashMap1.put("oak_door_half", Block.WOODEN_DOOR);
		hashMap1.put("ladder", Block.LADDER);
		hashMap1.put("rail", Block.RAIL);
		hashMap1.put("cobblestone_stairs", Block.STONE_STAIRS);
		hashMap1.put("oak_sign_wall", Block.WALL_SIGN);
		hashMap1.put("lever", Block.LEVER);
		hashMap1.put("stone_pressure_plate", Block.STONE_PRESSURE_PLATE);
		hashMap1.put("iron_door_half", Block.IRON_DOOR);
		hashMap1.put("oak_pressure_plate", Block.WOODEN_PRESSURE_PLATE);
		hashMap1.put("redstone_ore", Block.REDSTONE_ORE);
		hashMap1.put("redstone_ore_glowing", Block.LIT_REDSTONE_ORE);
		hashMap1.put("redstone_torch", Block.REDSTONE_TORCH);
		hashMap1.put("redstone_torch_active", Block.LIT_REDSTONE_ORE);
		hashMap1.put("stone_button", Block.STONE_BUTTON);
		hashMap1.put("snow", Block.SNOW);
		hashMap1.put("ice", Block.ICE);
		hashMap1.put("snow_block", Block.SNOW);
		hashMap1.put("cactus", Block.CACTUS);
		hashMap1.put("clay", Block.CLAY);
		hashMap1.put("reed_block", Block.REEDS);
		hashMap1.put("jukebox", Block.JUKEBOX);
		hashMap1.put("oak_fence", Block.FENCE);
		return hashMap1;
	}

	private int getHashCode(String string) {
		return string.hashCode();
	}
}
