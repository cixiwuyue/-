# 万叶 4.0.0 打包说明

本项目只有源码，没有 APK。你需要在自己的电脑上用 Android Studio 打包。

## 1. 解压并打开

1. 解压 `manye-android-source-v4.0.0-final.zip`。
2. 打开 Android Studio。
3. 选择 **Open**。
4. 选中解压后最外层的 `Manye` 文件夹。这个文件夹里面应该能看到 `settings.gradle.kts`。

不要只打开 `app` 文件夹。

## 2. 配置环境

项目需要以下版本：

- JDK 17
- Gradle 9.6.0
- Android SDK Platform 37
- Android SDK Build Tools 36.0.0

在 Android Studio 的 Gradle 设置中选择 JDK 17，并使用 Gradle 9.6.0。若提示缺少 Android SDK 组件，在 SDK Manager 中安装上面列出的 Platform 和 Build Tools。

## 3. 等待项目同步

打开项目后，Android Studio 会进行 Gradle Sync。首次同步需要下载 Android 构建依赖，请保持网络可用。

如果出现红色错误提示，先不要删除或改动源码。保存错误窗口截图，连同 `Build` 窗口最上方的错误文字一起提供，便于定位。

## 4. 自行打包

同步成功后，在 Android Studio 顶部菜单选择：

**Build → Build Bundle(s) / APK(s) → Build APK(s)**

打包成功后，Android Studio 会显示 APK 的保存位置。默认 Debug APK 路径为：

`app/build/outputs/apk/debug/app-debug.apk`

本项目不会替你生成或交付 APK。

## 5. 升级旧版前

第一次安装 4.0.0 前，建议：

1. 在旧版万叶中导出一次完整备份。
2. 把备份复制到手机以外的位置。
3. 安装新版本后先检查档案数量和重要附件是否正常。

不要为了升级而先卸载旧版。卸载应用会清除应用私有目录中的档案数据。
