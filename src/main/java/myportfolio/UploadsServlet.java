package myportfolio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * プロフィール画像などを UPLOAD_DIR（Docker では webapps 外）から配信する。
 * JSP は {@code /uploads/ファイル名} を参照するため、この Servlet で同一 URL を処理する。
 */
@WebServlet("/uploads/*")
public class UploadsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if (pathInfo == null || pathInfo.isEmpty() || "/".equals(pathInfo)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String relative = pathInfo.startsWith("/") ? pathInfo.substring(1) : pathInfo;
		if (relative.isEmpty() || relative.indexOf("..") >= 0 || relative.indexOf('\\') >= 0) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		Path base = Paths.get(UploadUtil.getUploadDir(getServletContext())).toAbsolutePath().normalize();
		UploadUtil.ensureUploadDir(base.toString());
		Path file = base.resolve(relative).normalize();
		if (!file.startsWith(base) || !Files.isRegularFile(file)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		String contentType = Files.probeContentType(file);
		if (contentType != null) {
			response.setContentType(contentType);
		} else {
			response.setContentType("application/octet-stream");
		}
		response.setContentLengthLong(Files.size(file));
		response.setHeader("Cache-Control", "public, max-age=3600");

		try (InputStream in = Files.newInputStream(file); OutputStream out = response.getOutputStream()) {
			in.transferTo(out);
		}
	}
}
