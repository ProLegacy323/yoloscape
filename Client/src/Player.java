// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class Player extends Entity {

	public Model getRotatedModel() {
		Model model = getPlayerModel();
		return model;
	}

	public void updatePlayer(Stream stream)
	{
		stream.currentOffset = 0;
		anInt1702 = stream.readUnsignedByte();
		headIcon = stream.readUnsignedByte();
		skullIcon = stream.readUnsignedByte();
		desc = null;
		team = 0;
		for(int j = 0; j < 12; j++)
		{
			int k = stream.readUnsignedByte();
			if(k == 0)
			{
				equipment[j] = 0;
				continue;
			}
			int i1 = stream.readUnsignedByte();
			equipment[j] = (k << 8) + i1;
			if(j == 0 && equipment[0] == 65535)
			{
				desc = EntityDef.forID(stream.readUnsignedWord());
				break;
			}
			if(equipment[j] >= 512 && equipment[j] - 512 < ItemDef.totalItems)
			{
				int l1 = ItemDef.forID(equipment[j] - 512).team;
				if(l1 != 0)
					team = l1;
			}
		}

		for(int l = 0; l < 5; l++)
		{
			int j1 = stream.readUnsignedByte();
			if(j1 < 0 || j1 >= client.anIntArrayArray1003[l].length)
				j1 = 0;
			anIntArray1700[l] = j1;
		}

		super.anInt1511 = stream.readUnsignedWord();
		if(super.anInt1511 == 65535)
			super.anInt1511 = -1;
		super.anInt1512 = stream.readUnsignedWord();
		if(super.anInt1512 == 65535)
			super.anInt1512 = -1;
		super.anInt1554 = stream.readUnsignedWord();
		if(super.anInt1554 == 65535)
			super.anInt1554 = -1;
		super.anInt1555 = stream.readUnsignedWord();
		if(super.anInt1555 == 65535)
			super.anInt1555 = -1;
		super.anInt1556 = stream.readUnsignedWord();
		if(super.anInt1556 == 65535)
			super.anInt1556 = -1;
		super.anInt1557 = stream.readUnsignedWord();
		if(super.anInt1557 == 65535)
			super.anInt1557 = -1;
		super.anInt1505 = stream.readUnsignedWord();
		if(super.anInt1505 == 65535)
			super.anInt1505 = -1;
		name = TextClass.fixName(TextClass.nameForLong(stream.readQWord()));
		combatLevel = stream.readUnsignedByte();
		skill = stream.readUnsignedWord();
		visible = true;
		aLong1718 = 0L;
		for(int k1 = 0; k1 < 12; k1++)
		{
			aLong1718 <<= 4;
			if(equipment[k1] >= 256)
				aLong1718 += equipment[k1] - 256;
		}

		if(equipment[0] >= 256)
			aLong1718 += equipment[0] - 256 >> 4;
		if(equipment[1] >= 256)
			aLong1718 += equipment[1] - 256 >> 8;
		for(int i2 = 0; i2 < 5; i2++)
		{
			aLong1718 <<= 3;
			aLong1718 += anIntArray1700[i2];
		}

		aLong1718 <<= 1;
		aLong1718 += anInt1702;
	}

	public Model getPlayerModel() {
		long l = aLong1718;
		int renderAnimation = -1;
		int i1 = -1;
		int j1 = -1;
		int k1 = -1;
		if(super.anInt1517 >= 0)
			renderAnimation = Animation.anims[super.anInt1517].anIntArray353[super.anInt1518];
		Model model_1 = (Model) mruNodes.insertFromCache((long) 10);
		if(model_1 == null) {
			Model aclass30_sub2_sub4_sub6s[] = new Model[12];
			int j2 = 0;
			for(int l2 = 0; l2 < 12; l2++) {
				int i3 = equipment[l2];
				if(k1 >= 0 && l2 == 3)
					i3 = k1;
				if(j1 >= 0 && l2 == 5)
					i3 = j1;
				if(i3 >= 256 && i3 < 512) {
					Model model_3 = IDK.cache[i3 - 256].method538();
					if(model_3 != null)
						aclass30_sub2_sub4_sub6s[j2++] = model_3;
				}
				if(i3 >= 512) {
					Model model_4 = ItemDef.forID(i3 - 512).method196(anInt1702);
					if(model_4 != null)
						aclass30_sub2_sub4_sub6s[j2++] = model_4;
				}
			}
			model_1 = new Model(j2, aclass30_sub2_sub4_sub6s);
			for(int j3 = 0; j3 < 5; j3++)
				if(anIntArray1700[j3] != 0) {
					model_1.method476(client.anIntArrayArray1003[j3][0], client.anIntArrayArray1003[j3][anIntArray1700[j3]]);
					if(j3 == 1)
						model_1.method476(client.anIntArray1204[0], client.anIntArray1204[anIntArray1700[j3]]);
				}
			model_1.method469();
			model_1.method479(64, 850, -30, -50, -30, true);
			mruNodes.removeFromCache(model_1, l);
			aLong1697 = l;
		}
		if(aBoolean1699)
			return model_1;
		Model model_2 = Model.aModel_1621;
		model_2.method464(model_1, Class36.method532(renderAnimation) & Class36.method532(i1));
		if(renderAnimation != -1 && i1 != -1)
			model_2.method471(Animation.anims[super.anim].anIntArray357, i1, renderAnimation);
		else
		if(renderAnimation != -1)
			model_2.method470(renderAnimation);
		model_2.method466();
		model_2.anIntArrayArray1658 = null;
		model_2.anIntArrayArray1657 = null;
		return model_2;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public int privelage;
	public Model method453()
	{
		if(!visible)
			return null;
		if(desc != null)
			return desc.method160();
		boolean flag = false;
		for(int i = 0; i < 12; i++)
		{
			int j = equipment[i];
			if(j >= 256 && j < 512 && !IDK.cache[j - 256].method539())
				flag = true;
			if(j >= 512 && !ItemDef.forID(j - 512).method192(anInt1702))
				flag = true;
		}

		if(flag)
			return null;
		Model aclass30_sub2_sub4_sub6s[] = new Model[12];
		int k = 0;
		for(int l = 0; l < 12; l++)
		{
			int i1 = equipment[l];
			if(i1 >= 256 && i1 < 512)
			{
				Model model_1 = IDK.cache[i1 - 256].method540();
				if(model_1 != null)
					aclass30_sub2_sub4_sub6s[k++] = model_1;
			}
			if(i1 >= 512)
			{
				Model model_2 = ItemDef.forID(i1 - 512).method194(anInt1702);
				if(model_2 != null)
					aclass30_sub2_sub4_sub6s[k++] = model_2;
			}
		}

		Model model = new Model(k, aclass30_sub2_sub4_sub6s);
		for(int j1 = 0; j1 < 5; j1++)
			if(anIntArray1700[j1] != 0)
			{
				model.method476(client.anIntArrayArray1003[j1][0], client.anIntArrayArray1003[j1][anIntArray1700[j1]]);
				if(j1 == 1)
					model.method476(client.anIntArray1204[0], client.anIntArray1204[anIntArray1700[j1]]);
			}

		return model;
	}

	Player()
	{
		aLong1697 = -1L;
		aBoolean1699 = false;
		anIntArray1700 = new int[5];
		visible = false;
		anInt1715 = 9;
		equipment = new int[12];
	}

	private long aLong1697;
	public EntityDef desc;
	boolean aBoolean1699;
	final int[] anIntArray1700;
	public int team;
	private int anInt1702;
	public String name;
	static MRUNodes mruNodes = new MRUNodes(260);
	public int combatLevel;
	public int headIcon;
	public int skullIcon;
	public int hintIcon;
	public int anInt1707;
	int anInt1708;
	int anInt1709;
	boolean visible;
	int anInt1711;
	int anInt1712;
	int anInt1713;
	Model aModel_1714;
	private int anInt1715;
	public final int[] equipment;
	private long aLong1718;
	int anInt1719;
	int anInt1720;
	int anInt1721;
	int anInt1722;
	int skill;

}
