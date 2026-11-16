# 万叶（Manye）Android 本地数字档案库

**整合版本：4.0.0 · versionCode 13 · 数据库 schema v6**

万叶是 Android 本地个人档案库。档案保存在应用私有目录，不要求注册账号或使用云同步。项目交付完整源码和构建说明，不包含 APK；安装包由项目使用者自行构建。

## 主要功能

### 保存和归档
- 手动保存文字，支持标题、正文、分类、标签、来源和备注。
- 从 Android 系统分享菜单接收文字、图片、视频、PDF 和其他文件。
- 使用系统文件选择器导入单个或多个文件；原件复制到应用私有目录。
- 图文分享可合并为一个档案；独立文件可分别建档。
- 原始附件只读保存；同一内容可被多个档案引用，系统按 SHA-256 尝试复用底层文件。

### 管理、搜索和阅读
- 编辑档案资料与编辑正文分开；正文编辑生成衍生笔记，不覆盖原始附件。
- 支持收藏、置顶、回收站、恢复、永久删除和批量操作。
- 支持分类、标签、集合和时间筛选；一个档案可以加入多个集合。
- 双列瀑布流主页；图片/视频展示封面，长文本显示摘要。
- 支持图片预览、视频播放、PDF 阅读、文本阅读和 DOCX/XLSX/PPTX 离线文本预览。
- DOCX 提取文本可另存为独立笔记；提取历史可以保留和切换。
- 衍生笔记支持全屏阅读、编辑、删除确认、自动草稿恢复和 TXT 导出。
- 本地全文搜索覆盖标题、正文、文件名、标签、分类、来源、备注、PDF 提取文本及衍生笔记。

### 备份和数据安全
- 支持整库 ZIP 备份和可选密码加密备份。
- 备份包含数据库、档案元数据、原始附件、衍生笔记、提取历史、集合关系和编辑草稿。
- 新备份带有全局文件清单，按文件大小和 SHA-256 校验。
- 恢复前检查数据库、附件路径、档案清单和文件指纹；发现缺失或损坏时拒绝导入。
- 完整恢复和合并恢复是两个独立入口。完整恢复会保留恢复前档案库，支持撤销最近一次完整恢复。
- 合并恢复按档案稳定 ID 匹配，不以相同标题合并；本机已有资料优先，附件按 SHA-256 去重。
- 存储检查可以逐项查看缺失文件、校验异常和重复附件，并定位到所属档案。

## OCR 说明

万叶不包含图片 OCR：图片仅保存、预览和按文件名搜索。旧版遗留的图片 OCR 提取记录不会参与搜索。PDF 原生文本层和 Office 文档文本提取不属于图片 OCR，仍保留。

## 隐私和联网

档案默认保存在应用私有目录，不自动上传。项目保留可选的 AI 助手：只有使用者主动配置 API、主动选择资料并发起请求时，选中的内容才会发送给所配置的服务商。API Key 使用 Android Keystore 加密保存。若不使用 AI，可不配置 API。

卸载应用会删除应用私有目录中的档案。请定期导出备份，并把备份复制到手机以外的位置。加密备份密码无法找回。

## 项目结构

```text
Manye/
  app/
    build.gradle.kts
    src/main/AndroidManifest.xml
    src/main/java/cn/shicang/app/
  tools/
    source_audit.py
  .github/workflows/main.yml
  build.gradle.kts
  settings.gradle.kts
  gradle.properties
  BUILD_GUIDE.md
  BUILD_STATUS.md
  FINAL_CHECKLIST.md
```

## 构建环境

| 项目 | 版本 |
|---|---:|
| JDK | 17 |
| Android SDK Platform | 37 |
| Android Build Tools | 36.0.0 |
| Android Gradle Plugin | 9.3.0 |
| Kotlin Compose Gradle Plugin | 2.4.21 |
| Gradle | 9.6.0 |
| minSdk / targetSdk | 26 / 35 |

本项目没有附带 Gradle Wrapper。首次打开请按 `BUILD_GUIDE.md` 配置 Android Studio 的 Gradle 9.6.0 和 JDK 17。

## 交付范围

交付完整 Android Studio 源码工程、构建配置、版本说明和自测清单。不生成、不交付 APK；由项目使用者自行打包。
