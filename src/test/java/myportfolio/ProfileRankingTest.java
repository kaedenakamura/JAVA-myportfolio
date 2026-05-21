package myportfolio;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * ProfileRankingのユニットテスト
 */


@IntegrationTest
public class ProfileRankingTest {
	@Test
	public void testProfileUpdate() {
        UserDao dao = new UserDao();
        // プロフィール更新のみを検証
        boolean result = dao.updateAccount(1, "test_rank@example.com", "Pass1234");
        assertTrue(result, "プロフィールの更新処理（UserDao.updateAccount）が失敗しました");
        System.out.println("プロフィール更新可能"+result);
    }

    @Test
    public void testLikeCount() {
        LikeDao likeDao = new LikeDao();
        // いいね集計ロジックのみを検証
		try {
			int count = likeDao.countLikesByToUserId(1);
			  assertTrue(count >= 0, "いいね数取得（LikeDao.countLikesByToUserId）に失敗しました");
			  System.out.println("いいねcount"+ count);
		} catch (Exception e) {
			e.printStackTrace();
		}
   
    }
    
    @Test
    public void testMonthlyRanking() {
    	LikeDao likeDao =new LikeDao();
    	
    	try {
			List<User> ranking = likeDao.getMonthlyLikeRanking();
			//データ検証nullかどうか
			assertNotNull(ranking,"ランキングリストがnullです");
			
			//データが１件以上ある場合
			if(!ranking.isEmpty()) {
				System.out.println("月間ランキング結果");
				for(int i = 0; i<ranking.size(); i++) {
					User u =ranking.get(i);
					System.out.println((i + 1 )+"位"+ u.getName()+"いいね数:"+u.getLikeCount());
					
			//順位チェック 前の人よりいいね数が多くないかチェック
			if(i>0) {
				assertTrue(ranking.get(i-1).getLikeCount() >=u.getLikeCount(),"ランキングの順序が不正です(降順になっていない)");
			}
				}
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	
    	
    }
}
