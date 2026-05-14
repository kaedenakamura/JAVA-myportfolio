# Tomcat 11をベースにする
FROM tomcat:11.0-jdk17

# デフォルトのアプリを削除
RUN rm -rf /usr/local/tomcat/webapps/*

# ビルドしたWARファイル（またはWebContentの中身）を配置
# コンテキストパスをROOTにするため、ROOT.warとしてコピー
COPY target/myportfolio.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]