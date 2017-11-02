package chap26

object EMail {
  // 注入メソッド(オプション)
  def apply(user: String, domain: String) = user + "@" + domain

  // 抽出メソッド(必須)
  def unapply(str: String): Option[(String, String)] = {
    val parts = str split "@"
    if (parts.length == 2) Some(parts(0), parts(1)) else None
  }
}
