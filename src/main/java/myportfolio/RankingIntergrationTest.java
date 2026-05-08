package myportfolio;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class RankingIntergrationTest {
		@Test
		public void testLikeToRankingFrow() {
		LikeDao likeDao = new LikeDao();
		
		//テスト用のいいねを挿入
		try {
			likeDao.likeInsert(1,2);
		//ランキングを取得し、データが含まれているか確認
		List<User> rankingList = likeDao.getMonthlyLikeRanking();
		
		assertNotNull(rankingList,"ランキングリスト辞退がnullです");
		assertFalse(rankingList.isEmpty(),"ランキングに誰も表示されていません");
		System.out.println("結合テスト:いいねランキング反映されています");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		}
		
		
}
