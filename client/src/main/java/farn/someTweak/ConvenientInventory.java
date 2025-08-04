package farn.someTweak;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.screen.inventory.menu.SurvivalInventoryScreen;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.slot.InventorySlot;
import net.minecraft.item.ItemStack;
import net.minecraft.unmapped.C_4065940;
import org.lwjgl.input.Keyboard;

public class ConvenientInventory {
	private static final int MOVE_ONE_AND_STACK = 3;
	private static final int MOVE_ONE = 4;
	private static final int MOVE_STACK_AND_STACK = 1;
	private static final int MOVE_STACK = 2;
	private static final int MOVE_ALL_AND_STACK = 5;
	private static final int MOVE_ALL = 6;
	private static final int SORT = 7;
	private static final int DROP_ONE = 8;
	private static final int DROP_STACK = 9;
	private static final int DROP_ALL = 10;
	private static final int NORMAL_LEFT_CLICK = 11;
	private static final int NORMAL_RIGHT_CLICK = 12;
	private static final int NONE = 0;
	static List[][] actionMap = new List[13][];
	static boolean initialized = false;

	private static void initialize() {
		try {
			File file0 = new File(FarnSomeTweak.mc.getRunDirectory(), "mod_convenientInventory.txt");
			if(file0.exists()) {
				BufferedReader bufferedReader1 = new BufferedReader(new FileReader(file0));

				String string2;
				while((string2 = bufferedReader1.readLine()) != null) {
					if(string2.startsWith("MOVE_ONE_AND_STACK")) {
						actionMap[3] = putOnKeyMap(string2);
					} else if(string2.startsWith("MOVE_ONE")) {
						actionMap[4] = putOnKeyMap(string2);
					} else if(string2.startsWith("MOVE_STACK_AND_STACK")) {
						actionMap[1] = putOnKeyMap(string2);
					} else if(string2.startsWith("MOVE_STACK")) {
						actionMap[2] = putOnKeyMap(string2);
					} else if(string2.startsWith("MOVE_ALL_AND_STACK")) {
						actionMap[5] = putOnKeyMap(string2);
					} else if(string2.startsWith("MOVE_ALL")) {
						actionMap[6] = putOnKeyMap(string2);
					} else if(string2.startsWith("SORT")) {
						actionMap[7] = putOnKeyMap(string2);
					} else if(string2.startsWith("DROP_ONE")) {
						actionMap[8] = putOnKeyMap(string2);
					} else if(string2.startsWith("DROP_STACK")) {
						actionMap[9] = putOnKeyMap(string2);
					} else if(string2.startsWith("DROP_ALL")) {
						actionMap[10] = putOnKeyMap(string2);
					} else if(string2.startsWith("NORMAL_RIGHT_CLICK")) {
						actionMap[12] = putOnKeyMap(string2);
					} else if(string2.startsWith("NORMAL_LEFT_CLICK")) {
						actionMap[11] = putOnKeyMap(string2);
					}
				}

				bufferedReader1.close();
			} else {
				actionMap[3] = putOnKeyMap("= 29 M0, 157 M0");
				actionMap[4] = putOnKeyMap("= 29 M1, 157 M1");
				actionMap[1] = putOnKeyMap("= 42 M0,  54 M0");
				actionMap[2] = putOnKeyMap("= 42 M1,  54 M1");
				actionMap[5] = putOnKeyMap("= 56 M0, 184 M0");
				actionMap[6] = putOnKeyMap("= 56 M1, 184 M1");
				actionMap[7] = putOnKeyMap("= M2");
				actionMap[8] = putOnKeyMap("= 16 M0");
				actionMap[9] = putOnKeyMap("= 16 M1");
				actionMap[10] = putOnKeyMap("= 42 16 M0, 54 16 M0");
				actionMap[11] = putOnKeyMap("= M0");
				actionMap[12] = putOnKeyMap("= M1");
				BufferedWriter bufferedWriter4 = new BufferedWriter(new FileWriter(file0));
				bufferedWriter4.write("# Configure key/mouse combinations for actions:");
				bufferedWriter4.newLine();
				bufferedWriter4.write("# Seperate multiple combinations with ,");
				bufferedWriter4.newLine();
				bufferedWriter4.write("# each combination must consist of a mouse button and an arbitrary");
				bufferedWriter4.newLine();
				bufferedWriter4.write("# amount of keyboard keys. The mouse button is defined by \"M#\" with #");
				bufferedWriter4.newLine();
				bufferedWriter4.write("# being the number 0 (left button), 1 (right button), 2 (middle button), aso.");
				bufferedWriter4.newLine();
				bufferedWriter4.write("# This http://www.lwjgl.org/javadoc/constant-values.html#org.lwjgl.input.Keyboard.KEY_1 is");
				bufferedWriter4.newLine();
				bufferedWriter4.write("# a list of all possible keyboard keys you can use for your combinations.");
				bufferedWriter4.newLine();
				bufferedWriter4.write(" ");
				bufferedWriter4.newLine();
				bufferedWriter4.write("MOVE_ONE_AND_STACK = 29 M0, 157 M0");
				bufferedWriter4.newLine();
				bufferedWriter4.write("MOVE_ONE = 29 M1, 157 M1");
				bufferedWriter4.newLine();
				bufferedWriter4.write("MOVE_STACK_AND_STACK = 42 M0,  54 M0");
				bufferedWriter4.newLine();
				bufferedWriter4.write("MOVE_STACK = 42 M1,  54 M1");
				bufferedWriter4.newLine();
				bufferedWriter4.write("MOVE_ALL_AND_STACK = 56 M0, 184 M0");
				bufferedWriter4.newLine();
				bufferedWriter4.write("MOVE_ALL = 56 M1, 184 M1");
				bufferedWriter4.newLine();
				bufferedWriter4.write("SORT = M2");
				bufferedWriter4.newLine();
				bufferedWriter4.write("DROP_ONE = 16 M0");
				bufferedWriter4.newLine();
				bufferedWriter4.write("DROP_STACK = 16 M1");
				bufferedWriter4.newLine();
				bufferedWriter4.write("DROP_ALL = 42 16 M0, 54 16 M0");
				bufferedWriter4.newLine();
				bufferedWriter4.write("NORMAL_LEFT_CLICK = M0");
				bufferedWriter4.newLine();
				bufferedWriter4.write("NORMAL_RIGHT_CLICK = M1");
				bufferedWriter4.flush();
				bufferedWriter4.close();
			}
		} catch (Exception exception3) {
			System.out.println(exception3.getMessage());
		}

		initialized = true;
	}

	private static List[] putOnKeyMap(String string0) {
		String[] string1 = string0.split("=");
		LinkedList linkedList2 = new LinkedList();
		if(string1.length > 1) {
			String[] string3 = string1[1].split(",");

			for(int i4 = 0; i4 < string3.length; ++i4) {
				String[] string5 = string3[i4].split(" ");
				int i6 = -1;
				LinkedList linkedList7 = new LinkedList();

				for(int i8 = 0; i8 < string5.length; ++i8) {
					if(string5[i8].trim().startsWith("M")) {
						try {
							i6 = Integer.parseInt(string5[i8].trim().substring(1));
						} catch (Exception exception11) {
						}
					} else if(string5[i8].length() > 0) {
						try {
							linkedList7.add(new Integer(Integer.parseInt(string5[i8].trim())));
						} catch (Exception exception10) {
						}
					}
				}

				if(i6 >= 0) {
					linkedList7.addFirst(new Integer(i6));
					linkedList2.add(linkedList7);
				}
			}
		}

		return linkedList2.size() > 0 ? (List[])((List[])((List[])((List[])((List[])linkedList2.toArray(new List[0]))))) : null;
	}

	public static int getAction(int i0) {
		int i1 = 0;
		int i2 = 0;

		for(int i3 = 0; i3 < actionMap.length; ++i3) {
			for(int i4 = 0; actionMap[i3] != null && i4 < actionMap[i3].length; ++i4) {
				boolean z5 = actionMap[i3][i4] != null;

				for(int i6 = 0; z5 && i6 < actionMap[i3][i4].size(); ++i6) {
					if(i6 == 0) {
						if(((Integer)actionMap[i3][i4].get(i6)).intValue() != i0) {
							z5 = false;
						}
					} else if(!Keyboard.isKeyDown(((Integer)actionMap[i3][i4].get(i6)).intValue())) {
						z5 = false;
					}
				}

				if(z5 && actionMap[i3][i4].size() > i2) {
					i2 = actionMap[i3][i4].size();
					i1 = i3;
				}
			}
		}

		return i1;
	}

	public static void mod_convenientInventory_handleClickOnSlot(int i0, int i1, boolean z2, Minecraft minecraft3, List craftingInventoryCB4) {
		if(!initialized) {
			initialize();
		}

		List list5 = craftingInventoryCB4;
		if(FarnSomeTweak.mc.player != null) {
			if(i0 < 0) {
				if((i1 == 0 || i1 == 1) && i0 == -999) {
					sendClick(craftingInventoryCB4, minecraft3, i0, i1);
				}
			} else if(isCraftingSlot(list5.get(i0)) && null != getStackOfSlot(list5.get(i0)) && getItemStackId(getStackOfSlot(list5.get(i0))) == 358) {
				if(i1 == 0 || i1 == 1) {
					sendClick(craftingInventoryCB4, minecraft3, i0, i1);
				}
			} else {
				int i6 = getAction(i1);
				int i7 = 0;
				int i8 = 0;
				int i9 = list5.size();
				int i10 = i9 - 9;
				int i11;
				int i12;
				int i13;
				int i14 = i13 = i11 = i12 = list5.size() - getInventorySize(minecraft3);
				if(i0 < i11) {
					if(isPlayerInventory(minecraft3)) {
						i13 -= 4;
						i7 = i13;
						i8 = i13 + 4;
					}

					i12 = list5.size();
					i14 = 0;
				} else {
					if(isPlayerInventory(minecraft3)) {
						i12 -= 4;
						i7 = i12;
						i8 = i12 + 4;
					}

					i11 = 0;
					i13 = list5.size();
				}

				boolean z15 = i7 <= i0 && i8 > i0;
				int i16 = getPressedNumberKey();
				if(!isCraftingSlot(list5.get(i0)) && !z15 && i16 > 0) {
					sendClick(craftingInventoryCB4, minecraft3, i0, 0);
					sendClick(craftingInventoryCB4, minecraft3, i10 + i16 - 1, 0);
					if(getStackInHand(minecraft3) != null && !isCraftingSlot(list5.get(i0))) {
						sendClick(craftingInventoryCB4,minecraft3,  i0, 0);
					}
				} else if(!isCraftingSlot(list5.get(i0)) && !z15 && i16 == 0) {
					sendClick(craftingInventoryCB4, minecraft3, i0, 0);
					dropOnExistingStack(minecraft3, craftingInventoryCB4, list5, i10, i9, true);
					if(getStackInHand(minecraft3) != null && !isCraftingSlot(list5.get(i0))) {
						sendClick(craftingInventoryCB4, minecraft3, i0, 0);
					}
				} else if(i6 == 0) {
					if(i1 == 0 || i1 == 1) {
						sendClick(craftingInventoryCB4, minecraft3, i0, i1);
					}
				} else if(i6 == 11) {
					sendClick(craftingInventoryCB4,minecraft3, i0, 0);
				} else if(i6 == 12) {
					sendClick(craftingInventoryCB4, minecraft3, i0, 1);
				} else {
					int i17;
					int i18;
					if(i6 == 7) {
						i18 = i17 = list5.size() - getInventorySize(minecraft3);
						if(i0 < i18) {
							i18 = 0;
						} else if(i0 < list5.size() - 9) {
							i17 = list5.size() - 9;
						} else {
							i17 = list5.size();
							i18 = i17 - 9;
						}

						sortInventory(list5, minecraft3, craftingInventoryCB4, i18, i17);
					} else {
						boolean z19;
						if(i6 != 3 && i6 != 4 && i6 != 8) {
							Object object20;
							if(i6 != 2 && i6 != 1 && i6 != 9) {
								if(i6 != 6 && i6 != 5 && i6 != 10) {
									System.out.println("ConvenientInventory: Something is broken");
								} else {
									i17 = -1;
									i18 = -1;
									object20 = getStackOfSlot(list5.get(i0));
									z19 = false;
									if(object20 != null) {
										i17 = getItemStackId(object20);
										i18 = getItemStackDamage(object20);
										z19 = getMaxStackSize(object20) == 1;
									}

									if(i17 == -1) {
										return;
									}

									boolean z24 = false;

									while(getStackInHand(minecraft3) == null && !z24) {
										z24 = true;

										for(int i22 = i14; i22 < i13; ++i22) {
											Object object23 = getStackOfSlot(list5.get(i22));
											if(object23 != null && getItemStackId(object23) == i17 && (z19 || getItemStackDamage(object23) == i18) && (i6 == 10 || hasFreeSlot(minecraft3, list5, i11, i12, i17, i18, getStackSize(object23), i6 == 5, isCraftingSlot(list5.get(i0))))) {
												sendClick(craftingInventoryCB4, minecraft3, i22, 0);
												i22 = i13;
												z24 = false;
											}
										}

										if(i6 == 6) {
											dropIntoFreeSlot(minecraft3, craftingInventoryCB4, list5, i11, i12, true);
										} else if(i6 == 5) {
											dropOnExistingStack(minecraft3, craftingInventoryCB4, list5, i11, i12, true);
										} else if(i6 == 10) {
											sendClick(craftingInventoryCB4, minecraft3, -999, 0);
										}
									}

									if(getStackInHand(minecraft3) != null && !isCraftingSlot(list5.get(i0))) {
										dropIntoFreeSlot(minecraft3, craftingInventoryCB4, list5, i14, i13, true);
									}
								}
							} else {
								if(isCraftingSlot(list5.get(i0))) {
									object20 = null;
									z19 = false;
									int i21 = 0;

									do {
										++i21;
										i18 = object20 != null ? getStackSize(object20) : 0;
										sendClick(craftingInventoryCB4, minecraft3, i0, 0);
										object20 = getStackInHand(minecraft3);
									} while(object20 != null && i18 < getStackSize(object20) && i21 < 70);
								} else {
									sendClick(craftingInventoryCB4, minecraft3, i0, 0);
								}

								if(!z15 && isPlayerInventory(minecraft3) && i6 != 9) {
									dropIntoFreeSlot(minecraft3, craftingInventoryCB4, list5, i7, i8, false);
								}

								if(i6 == 2) {
									dropIntoFreeSlot(minecraft3, craftingInventoryCB4, list5, i11, i12, true);
								} else if(i6 == 1) {
									dropOnExistingStack(minecraft3, craftingInventoryCB4, list5, i11, i12, true);
								} else if(i6 == 9) {
									sendClick(craftingInventoryCB4, minecraft3, -999, 0);
								}

								if(getStackInHand(minecraft3) != null && !isCraftingSlot(list5.get(i0))) {
									sendClick(craftingInventoryCB4, minecraft3, i0, 0);
								}
							}
						} else {
							sendClick(craftingInventoryCB4, minecraft3, i0, 0);
							if(!z15 && isPlayerInventory(minecraft3) && i6 != 8) {
								dropIntoFreeSlot(minecraft3, craftingInventoryCB4, list5, i7, i8, false);
							}

							z19 = isCraftingSlot(list5.get(i0));
							if(i6 == 4) {
								dropIntoFreeSlot(minecraft3, craftingInventoryCB4, list5, i11, i12, z19);
							} else if(i6 == 3) {
								dropOnExistingStack(minecraft3, craftingInventoryCB4, list5, i11, i12, z19);
							} else if(i6 == 8) {
								sendClick(craftingInventoryCB4, minecraft3, -999, 1);
							}

							if(getStackInHand(minecraft3) != null && !isCraftingSlot(list5.get(i0))) {
								sendClick(craftingInventoryCB4, minecraft3, i0, 0);
							}
						}
					}
				}
			}
		}

	}

	private static int getPressedNumberKey() {
		return !Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0) && !Keyboard.isKeyDown(Keyboard.KEY_0) ? (!Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1) && !Keyboard.isKeyDown(Keyboard.KEY_1) ? (!Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2) && !Keyboard.isKeyDown(Keyboard.KEY_2) ? (!Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3) && !Keyboard.isKeyDown(Keyboard.KEY_3) ? (!Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4) && !Keyboard.isKeyDown(Keyboard.KEY_4) ? (!Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5) && !Keyboard.isKeyDown(Keyboard.KEY_5) ? (!Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6) && !Keyboard.isKeyDown(Keyboard.KEY_6) ? (!Keyboard.isKeyDown(Keyboard.KEY_NUMPAD7) && !Keyboard.isKeyDown(Keyboard.KEY_7) ? (!Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8) && !Keyboard.isKeyDown(Keyboard.KEY_8) ? (!Keyboard.isKeyDown(Keyboard.KEY_NUMPAD9) && !Keyboard.isKeyDown(Keyboard.KEY_9) ? -1 : 9) : 8) : 7) : 6) : 5) : 4) : 3) : 2) : 1) : 0;
	}

	private static void dropIntoFreeSlot(Minecraft minecraft0, List object1, List list2, int i3, int i4, boolean z5) {
		Object object6 = getStackInHand(minecraft0);

		for(int i7 = i3; i7 < i4 && object6 != null && getStackSize(object6) > 0; ++i7) {
			Object object8 = list2.get(i7);
			Object object9 = object8 != null ? getStackOfSlot(object8) : null;
			if(object8 != null && object9 == null && !isCraftingSlot(object8) && isItemStackValidInSlot(object6, object8)) {
				if(!z5) {
					sendClick(object1, minecraft0, i7, 1);
					return;
				}

				sendClick(object1, minecraft0, i7, 0);
			}

			object6 = getStackInHand(minecraft0);
		}

	}

	private static boolean hasFreeSlot(Minecraft minecraft0, List list1, int i2, int i3, int i4, int i5, int i6, boolean z7, boolean z8) {
		int i9;
		Object object10;
		Object object11;
		for(i9 = i2; i9 < i3; ++i9) {
			object10 = list1.get(i9);
			object11 = object10 != null ? getStackOfSlot(object10) : null;
			if(object10 != null && object11 == null && !isCraftingSlot(object10)) {
				return true;
			}
		}

		for(i9 = i2; i9 < i3 && z7; ++i9) {
			object10 = list1.get(i9);
			object11 = object10 != null ? getStackOfSlot(object10) : null;
			if(object10 != null && object11 != null && getItemStackId(object11) == i4 && getItemStackDamage(object11) == i5 && getStackSize(object11) + (z8 ? i6 : 0) <= getMaxStackSize(object11) && !isCraftingSlot(object10)) {
				return true;
			}
		}

		return false;
	}

	private static void dropOnExistingStack(Minecraft minecraft0, List object1, List list2, int i3, int i4, boolean z5) {
		Object object6 = getStackInHand(minecraft0);

		for(int i7 = i3; i7 < i4 && object6 != null && getStackSize(object6) > 0; ++i7) {
			Object object8 = list2.get(i7);
			Object object9 = object8 != null ? getStackOfSlot(object8) : null;
			if(object9 != null && !isCraftingSlot(object8) && equalTo(object9, object6) && getMaxStackSize(object9) > getStackSize(object9)) {
				if(!z5) {
					sendClick(list2, minecraft0, i7, 1);
					return;
				}

				sendClick(list2, minecraft0, i7, 0);
			}

			object6 = getStackInHand(minecraft0);
		}

		dropIntoFreeSlot(minecraft0, object1, list2, i3, i4, z5);
	}

	private static void sortInventory(List list4, Minecraft minecraft0, List object1, int i2, int i3) {
		if(getStackInHand(minecraft0) == null) {
			if(isCraftingSlot(list4.get(i2))) {
				++i2;
			}

			int i5;
			Object object6;
			int i7;
			Object object8;
			for(i5 = i2; i5 < i3; ++i5) {
				object6 = getStackOfSlot(list4.get(i5));
				if(object6 != null && getStackSize(object6) < getMaxStackSize(object6)) {
					for(i7 = i3 - 1; i7 > i5; --i7) {
						object8 = getStackOfSlot(list4.get(i7));
						if(object8 != null && getItemStackId(object8) == getItemStackId(object6) && getItemStackDamage(object8) == getItemStackDamage(object6)) {
							sendClick(object1, minecraft0, i7, 0);
							sendClick(object1, minecraft0, i5, 0);
							if(getStackInHand(minecraft0) != null) {
								sendClick(object1, minecraft0, i7, 0);
							}

							if(getStackSize(getStackOfSlot(list4.get(i5))) >= getMaxStackSize(getStackOfSlot(list4.get(i5)))) {
								i7 = i5;
							}
						}
					}
				}
			}

			for(i5 = i2; i5 < i3 - 1; ++i5) {
				boolean z9 = true;
				Object object10 = getStackOfSlot(list4.get(i5));

				int i11;
				for(i11 = i5 + 1; i11 < i3; ++i11) {
					Object object12 = getStackOfSlot(list4.get(i11));
					if(lowerThan(object12, object10)) {
						z9 = false;
					}
				}

				if(!z9) {
					sendClick(object1, minecraft0, i5, 0);
					i11 = i5 + 1;

					for(int i15 = 0; i11 != i5 && i15 < 100; ++i15) {
						Object object13 = getStackInHand(minecraft0);
						i11 = i5;

						for(int i14 = i5 + 1; i14 < i3; ++i14) {
							if(lowerThan(getStackOfSlot(list4.get(i14)), object13)) {
								++i11;
							}
						}

						while(i11 < i3 && equalTo(getStackOfSlot(list4.get(i11)), object13)) {
							++i11;
						}

						if(i11 < i3) {
							sendClick(object1, minecraft0, i11, 0);
						}
					}
				}
			}

			for(i5 = i2; i5 < i3; ++i5) {
				object6 = getStackOfSlot(list4.get(i5));
				if(object6 != null && getStackSize(object6) < getMaxStackSize(object6)) {
					for(i7 = i3 - 1; i7 > i5; --i7) {
						object8 = getStackOfSlot(list4.get(i7));
						if(object8 != null && getItemStackId(object8) == getItemStackId(object6) && getItemStackDamage(object8) == getItemStackDamage(object6)) {
							sendClick(object1, minecraft0, i7, 0);
							sendClick(object1, minecraft0, i5, 0);
							if(getStackInHand(minecraft0) != null) {
								sendClick(object1, minecraft0, i7, 0);
							}

							if(getStackSize(getStackOfSlot(list4.get(i5))) >= getMaxStackSize(getStackOfSlot(list4.get(i5)))) {
								i7 = i5;
							}
						}
					}
				}
			}
		}

	}

	private static boolean lowerThan(Object object0, Object object1) {
		return object0 != null && object1 == null ? true : (object0 != null && object1 != null ? (getItemStackId(object0) < getItemStackId(object1) ? true : getItemStackId(object0) == getItemStackId(object1) && getMaxStackSize(object0) > 1 && getItemStackDamage(object0) < getItemStackDamage(object1)) : false);
	}

	private static boolean equalTo(Object object0, Object object1) {
		return object0 == null && object1 == null ? true : (object0 != null && object1 != null ? (getItemStackId(object0) != getItemStackId(object1) ? false : getMaxStackSize(object0) == 1 || getItemStackDamage(object0) == getItemStackDamage(object1)) : false);
	}

	private static Object getStackInHand(Minecraft minecraft0) {
		return minecraft0.player.inventory.cursorStack;
	}

	private static ItemStack[] getInventoryList(Minecraft minecraft0) {
		return minecraft0.player.inventory.f_7351377;
	}

	private static boolean isPlayerInventory(Minecraft minecraft0) {
		return minecraft0.screen instanceof SurvivalInventoryScreen;
	}

	public static void sendClick(List list, Minecraft minecraft, int i1, int i2) {
		PlayerEntity entityPlayer3 = minecraft.player;
		ItemStack itemStack4 = null;
		if(i2 == 0 || i2 == 1) {
			PlayerInventory inventoryPlayer5 = entityPlayer3.inventory;
			if(i1 == -999) {
				if(inventoryPlayer5.cursorStack != null && i1 == -999) {
					if(i2 == 0) {
						entityPlayer3.dropItem(inventoryPlayer5.cursorStack);
						inventoryPlayer5.cursorStack = null;
					}

					if(i2 == 1) {
						entityPlayer3.dropItem(inventoryPlayer5.cursorStack.split(1));
						if(inventoryPlayer5.cursorStack.size == 0) {
							inventoryPlayer5.cursorStack = null;
						}
					}
				}
			} else {
				InventorySlot slot6 = (InventorySlot)list.get(i1);
				if(slot6 != null) {
					slot6.markDirty();
					ItemStack itemStack7 = slot6.getStack();
					if(itemStack7 != null) {
						itemStack4 = itemStack7.copy();
					}

					if(itemStack7 != null || inventoryPlayer5.cursorStack != null) {
						int i8;
						if(itemStack7 != null && inventoryPlayer5.cursorStack == null) {
							i8 = i2 == 0 ? itemStack7.size : (itemStack7.size + 1) / 2;
							inventoryPlayer5.cursorStack = ((StackDecreaser)slot6).decrStackSize(i8);
							if(itemStack7.size == 0) {
								slot6.setStack((ItemStack)null);
							}
							slot6.onStackRemovedByPlayer();
						} else if(itemStack7 == null && inventoryPlayer5.cursorStack != null && slot6.canSetStack(inventoryPlayer5.cursorStack)) {
							i8 = i2 == 0 ? inventoryPlayer5.cursorStack.size : 1;
							if(i8 > slot6.inventory.getMaxStackSize()) {
								i8 = slot6.inventory.getMaxStackSize();
							}

							slot6.setStack(inventoryPlayer5.cursorStack.split(i8));
							if(inventoryPlayer5.cursorStack.size == 0) {
								inventoryPlayer5.cursorStack = null;
							}
						} else if(itemStack7 != null && inventoryPlayer5.cursorStack != null) {
							if(slot6.canSetStack(inventoryPlayer5.cursorStack)) {
								if(itemStack7.itemId != inventoryPlayer5.cursorStack.itemId) {
									if(inventoryPlayer5.cursorStack.size <= slot6.inventory.getMaxStackSize()) {
										slot6.setStack(inventoryPlayer5.cursorStack);
										inventoryPlayer5.cursorStack = itemStack7;
									}
								} else if(itemStack7.itemId == inventoryPlayer5.cursorStack.itemId) {
									if(i2 == 0) {
										i8 = inventoryPlayer5.cursorStack.size;
										if(i8 > slot6.inventory.getMaxStackSize() - itemStack7.size) {
											i8 = slot6.inventory.getMaxStackSize() - itemStack7.size;
										}

										if(i8 > inventoryPlayer5.cursorStack.getMaxSize() - itemStack7.size) {
											i8 = inventoryPlayer5.cursorStack.getMaxSize() - itemStack7.size;
										}

										inventoryPlayer5.cursorStack.split(i8);
										if(inventoryPlayer5.cursorStack.size == 0) {
											inventoryPlayer5.cursorStack = null;
										}

										itemStack7.size += i8;
									} else if(i2 == 1) {
										i8 = 1;
										if(i8 > slot6.inventory.getMaxStackSize() - itemStack7.size) {
											i8 = slot6.inventory.getMaxStackSize() - itemStack7.size;
										}

										if(i8 > inventoryPlayer5.cursorStack.getMaxSize() - itemStack7.size) {
											i8 = inventoryPlayer5.cursorStack.getMaxSize() - itemStack7.size;
										}

										inventoryPlayer5.cursorStack.split(i8);
										if(inventoryPlayer5.cursorStack.size == 0) {
											inventoryPlayer5.cursorStack = null;
										}

										itemStack7.size += i8;
									}
								}
							} else if(itemStack7.itemId == inventoryPlayer5.cursorStack.itemId && inventoryPlayer5.cursorStack.getMaxSize() > 1) {
								i8 = itemStack7.size;
								if(i8 > 0 && i8 + inventoryPlayer5.cursorStack.size <= inventoryPlayer5.cursorStack.getMaxSize()) {
									ItemStack itemStack10000 = inventoryPlayer5.cursorStack;
									itemStack10000.size += i8;
									itemStack7.split(i8);
									System.out.println("test");
									if(itemStack7.size == 0) {
										slot6.setStack((ItemStack)null);
									}

									slot6.onStackRemovedByPlayer();
								}
							}
						}
					}
				}
			}
		}
	}

	private static boolean isCraftingSlot(Object object0) {
		return object0 instanceof C_4065940;
	}

	private static int getInventorySize(Minecraft minecraft0) {
		return minecraft0.player.inventory.getSize() - 4;
	}

	private static int getMaxStackSize(Object object0) {
		return ((ItemStack)object0).getMaxSize();
	}

	private static int getStackSize(Object object0) {
		return ((ItemStack)object0).size;
	}

	private static Object getStackOfSlot(Object object0) {
		return ((InventorySlot)object0).getStack();
	}

	private static int getItemStackId(Object object0) {
		return ((ItemStack)object0).itemId;
	}

	private static int getItemStackDamage(Object object0) {
		return ((ItemStack)object0).metadata;
	}

	private static boolean isItemStackValidInSlot(Object object0, Object object1) {
		return ((InventorySlot)object1).canSetStack((ItemStack)object0);
	}

	public static ItemStack decrStackSize(PlayerInventory inventory, InventorySlot slot, int i1) {
		return inventory.removeStack(slot.slot, i1);
	}
}
