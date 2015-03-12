package com.dh.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Encoder;

import com.dh.dao.LegionMapper;
import com.dh.enums.GMIOEnum;
import com.dh.game.constant.AlertEnum;
import com.dh.game.vo.base.Reward;
import com.dh.game.vo.user.BossLogVO;
import com.dh.game.vo.user.LegionLogVO;
import com.dh.main.InitLoad;
import com.dh.resconfig.BaseRes;
import com.dh.util.CodeTool;
import com.dh.util.SqlBuild;

public class EveryThing {
	private static final Class<? extends BaseRes<? extends Object>> Class = null;
	private SqlBuild sqlBuild;

	private LegionMapper legionMapper;
	private static ApplicationContext ctx = null;

	public static void main(String[] args) throws Exception {
		PropertyConfigurator.configure("config/log4j.properties");
		// ctx = new
		// FileSystemXmlApplicationContext("config/applicationContext.xml");
		// InitLoad initLoad = (InitLoad) ctx.getBean("initLoad");
		// initLoad.init();
		// EveryThing testEveryThing = (EveryThing) ctx.getBean("everyThing");
		// testEveryThing.testLegionDonate("轩辕传说", 1000001);
		// // testEveryThing.sqlBuild = (SqlBuild) ctx.getBean("sqlBuild");
		// Integer i = 114;
		// System.out.println(Integer.toBinaryString(i));
		// i |= (0x1 << 2);
		// System.out.println(Integer.toBinaryString(i));
		testTreeSet2();
		
	}

	/**
	 * 计算玩家贡献值
	 * 
	 * @param name
	 * @param legionId
	 */
	public void testLegionDonate(String name, int legionId) {
		LegionMapper legionMapper = (LegionMapper) ctx.getBean("legionMapper");
		List<LegionLogVO> Logs = legionMapper.getLogs(1000001);
		int total = 0;
		for (LegionLogVO legionLogVO : Logs) {
			if (legionLogVO.getType() == 1 || legionLogVO.getType() == 2) {
				if (legionLogVO.getDataStr().contains(name)) {
					legionLogVO.setDataList(CodeTool.decodeStrList(legionLogVO.getDataStr()));
					total += Integer.parseInt(legionLogVO.getDataList().get(2));
				}
			}
		}
		System.out.println("玩家" + name + "所有贡献值为" + total);
	}

	public static void transDate() {

		java.util.Date now = new java.util.Date();
		java.util.Date monthAgo = DateUtils.addMonths(now, -1);
		StringBuilder sql = new StringBuilder();
		int diffHour = (int) ((now.getTime() - monthAgo.getTime()) / TimeUnit.HOURS.toMillis(1));
		Random random = new Random();
		java.util.Date tempDate = null;
		for (int i = 1; i < diffHour; i++) {
			tempDate = DateUtils.addHours(monthAgo, i);
			int hour = (int) (tempDate.getTime() / TimeUnit.HOURS.toMillis(1));
			sql.append("insert into `t_gm_online`(hour,count) values(").append(hour).append(",").append(random.nextInt(1000)).append(") on duplicate key update count=values(count);\n");
		}
		System.out.println(sql);
	}

	public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		String str1 = base64en.encode(md5.digest(str.getBytes("utf-8")));
		return str1;
	}

	public static void testEnum() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(EncoderByMd5("100167"));
		System.out.println("722B62A0CF9BF3F599B8838E3DED701E".length());
		System.out.println("722B62A0CF9BF3F599B8838E3DED701E");
		System.out.println(AlertEnum.ACCOUNT_RE_REG.toString());
	}

	public static RankVO createRankVO(int playerId, int hurt) {
		RankVO rankVO = new RankVO();
		rankVO.setPlayerId(playerId);
		rankVO.setHurt(hurt);
		return rankVO;
	}

	public static void testString() {
		int[] nums = new int[20];
		String s = Arrays.toString(nums);
		System.out.println(s);
		String str = "192.168.1.1.";
		System.out.println(str.matches("^\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}$"));
	}

	public static void testStringArray() {
		String[] strArray = { "1,fjasdkff", "fjkafasd,=", "jfafd+," };
		System.out.println(CodeTool.encodeStrArray(strArray));
		for (int i = 0; i < strArray.length; i++) {
			System.out.println(CodeTool.decodeStrArray(CodeTool.encodeStrArray(strArray))[i]);
		}
	}

	public static void transSql() {
		String sql = "INSERT INTO `t_legion_log` (`id`,　`legionId`, `date`, `type`,　`dataStr`) VALUES ('id', 'legionId',   'date','type', 'dataStr')";
		System.out.println(transInsert(sql));
	}

	private static String transInsert(String sql) {
		StringBuilder sb = new StringBuilder();
		int index = 0;
		List<String> list = Arrays.asList(sql.split("',"));
		for (String string : list) {
			sb.append(string);
			if (index++ < list.size() - 1) {
				sb.append("},");
			}
		}
		sql = sb.toString();
		sb = new StringBuilder();
		list = Arrays.asList(sql.split("'"));
		new ArrayList<>(list);
		index = 0;
		for (String string : list) {
			if (index++ > 0 && index < list.size()) {
				sb.append("#{");
			}
			sb.append(string);
		}
		sql = sb.toString();
		return sql;

	}

	private String transUpdate(String sql) {
		StringBuilder sb = new StringBuilder();
		int index = 0;
		List<String> list = Arrays.asList(sql.split("= '"));
		for (String string : list) {
			sb.append(string);
			if (index++ < list.size() - 1) {
				sb.append("= #{");
			}
		}
		sql = sb.toString();
		sb = new StringBuilder();
		list = Arrays.asList(sql.split("'"));
		index = 0;
		for (String string : list) {
			sb.append(string);
			if (index++ < list.size()) {
				sb.append("}");
			}
		}
		sql = sb.toString();
		return sql;
	}

	public static void testList() {
		List<Reward> list1 = new ArrayList<Reward>();

		for (int i = 0; i < 10; i++) {
			Reward r = new Reward();
			r.setType(i);
			r.setContent(2);
			r.setNumber(3);
			list1.add(r);
		}
		List<Reward> list2 = new ArrayList<Reward>(list1);
		int j = 0;
		for (int i = 10; i < 20; i++) {
			list2.get(j).setType(list2.get(j).getType() + i);
			j++;
		}
		for (Reward eo : list2) {
			System.out.print(eo.hashCode() + "\t");
		}
		System.out.println();
		for (Reward eo : list1) {
			System.out.print(eo.hashCode() + "\t");
		}

	}

	public static void testTreeSet() {
		TreeSet<BossLogVO> RANK = new TreeSet<BossLogVO>(new MyCompator());
		for (int i = 2; i < 8; i++) {
			RANK.add(createLogVO2(i, 100 - i));
		}
		System.out.println(RANK.remove(createLogVO2(2, 1)));
		RANK.remove(createLogVO2(2, 100));
		RANK.add(createLogVO2(2, 100));
		Iterator<BossLogVO> it = RANK.iterator();
		while (it.hasNext()) {
			BossLogVO logVO = it.next();
			System.err.println("name:" + logVO.getName() + "\tplayerId:" + logVO.getPlayerId() + "\thunt:" + logVO.getHunt());
		}
	}

	//
	public static BossLogVO createLogVO2(int playerId, int hunt) {
		BossLogVO bossLogVO3 = new BossLogVO();
		bossLogVO3.setName("vince1");
		bossLogVO3.setPlayerId(playerId);
		bossLogVO3.setAddtion(0);
		bossLogVO3.setHunt(hunt);

		return bossLogVO3;
	}

	public static void testTreeSet2() {
		TreeSet<RankVO> RANK = new TreeSet<RankVO>(new MyComparator());
		Map<Integer, RankVO> rankMap = new HashMap<Integer, RankVO>();
		add(RANK, createRankVO(2, 100), rankMap);
		add(RANK, createRankVO(2, 99), rankMap);
		add(RANK, createRankVO(2, 98), rankMap);
		add(RANK, createRankVO(2, 97), rankMap);
		add(RANK, createRankVO(5, 101), rankMap);
		add(RANK, createRankVO(6, 101), rankMap);
		add(RANK, createRankVO(7, 101), rankMap);
		add(RANK, createRankVO(6, 101), rankMap);
		add(RANK, createRankVO(7, 101), rankMap);
		add(RANK, createRankVO(8, 101), rankMap);
		for (int i = 0; i < 10; i++) {
			add(RANK, createRankVO(i, 100 - i), rankMap);
		}
		add(RANK, createRankVO(6, 101), rankMap);
		add(RANK, createRankVO(7, 101), rankMap);
		// Collections.max(new ArrayList<RankVO>(), new MyComparator());

		Iterator<RankVO> it = RANK.iterator();
		int i = 1;
		while (it.hasNext()) {
			RankVO logVO = it.next();
			System.err.println("index:\t" + (i++) + "\tplayerId:" + logVO.getPlayerId() + "\thunt:" + logVO.getHurt());
		}
	}

	public static void add(TreeSet<RankVO> RANK, RankVO rankVO, Map<Integer, RankVO> rankMap) {
		RankVO oldRank = rankMap.get(rankVO.getPlayerId());
		if (oldRank != null) {
			RANK.remove(oldRank);
		}
		RANK.add(rankVO);
		rankMap.put(rankVO.getPlayerId(), rankVO);
	}

}

class EveryObj<A> {

}

class EveryObject {
	private int index;

	public EveryObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EveryObject(int index) {
		super();
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}

class MyCompator implements Comparator<BossLogVO> {

	@Override
	public int compare(BossLogVO arg0, BossLogVO arg1) {
		if (arg0.getPlayerId() == arg1.getPlayerId()) {
			return 0;
		}

		// if (arg0.getPlayerId() > arg1.getPlayerId()) {
		// return 1;
		// }
		//
		// return -1;

		if (arg0.getHunt() != arg1.getHunt()) {
			return arg0.getHunt() > arg1.getHunt() ? -1 : 1;
		}
		if (arg0.getAddtion() != arg1.getAddtion()) {
			return arg0.getAddtion() > arg1.getAddtion() ? 1 : -1;
		}

		return arg0.getName().compareTo(arg1.getName());
	}

}

class RankVO {
	private int playerId;
	private String name;
	private int hurt;
	private int addtion;

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAddtion() {
		return addtion;
	}

	public void setAddtion(int addtion) {
		this.addtion = addtion;
	}

	public int getHurt() {
		return hurt;
	}

	public void setHurt(int hurt) {
		this.hurt = hurt;
	}

	@Override
	public boolean equals(Object obj) {
		RankVO rankvo = (RankVO) obj;
		if (this.getPlayerId() == 2) {
			if (rankvo.getPlayerId() == this.playerId) {
				System.out.println(rankvo.getHurt());
			}
		}
		return obj instanceof RankVO && this.getPlayerId() == ((RankVO) obj).getPlayerId();
	}

}

class MyComparator implements Comparator<RankVO> {

	@Override
	public int compare(RankVO o1, RankVO o2) {
		if (o1.getPlayerId() == o2.getPlayerId()) {
			return 0;
		} else {
			if (o1.getHurt() != o2.getHurt()) {
				return o2.getHurt() - o1.getHurt();
			} else {
				return o1.getPlayerId() - o2.getPlayerId();
			}
		}
	}

}