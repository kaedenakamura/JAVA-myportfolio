package myportfolio;

import java.io.File;

import jakarta.servlet.ServletContext;

/**
 * プロフィール画像の保存先（本番 Docker では UPLOAD_DIR + ボリュームマウントを使用）
 */
public final class UploadUtil {

	private UploadUtil() {
	}

	public static String getUploadDir(ServletContext context) {
		String env = System.getenv("UPLOAD_DIR");
		if (env != null && !env.isBlank()) {
			return env;
		}
		String uploads = context.getRealPath("/uploads");
		if (uploads != null && !uploads.isBlank()) {
			return uploads;
		}
		String root = context.getRealPath("/");
		if (root != null && !root.isBlank()) {
			return root + "uploads" + File.separator;
		}
		return "/data/uploads";
	}

	public static void ensureUploadDir(String uploadPath) {
		File dir = new File(uploadPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
}
