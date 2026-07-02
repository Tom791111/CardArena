# 🏀 Player Card Management System（球員卡管理系統）

> **Java｜Swing｜MVC｜MySQL**

![Java](https://img.shields.io/badge/Java-JDK%208+-orange)
![Swing](https://img.shields.io/badge/GUI-Java%20Swing-blue)
![MySQL](https://img.shields.io/badge/Database-MySQL-4479A1)
![Architecture](https://img.shields.io/badge/Architecture-MVC-success)
![License](https://img.shields.io/badge/License-MIT-green)

---

# 📖 專案介紹

Player Card Management System 是一套以 **Java Swing** 開發的桌面管理系統，採用 **MVC（Model-View-Controller）架構**，並搭配 **MySQL** 資料庫，提供完整的球員卡資料管理功能。

系統可協助使用者建立會員帳號、管理球員卡資訊、查詢運動數據，並透過圖形化介面提升操作便利性。

---

# 🎯 專案特色

* 🔐 會員登入 / 註冊
* 👤 會員管理
* 🏀 球員卡管理
* 📊 運動數據查詢
* 🗄️ MySQL 資料庫
* 🖥️ Java Swing GUI
* 🏗️ MVC 三層式架構

---

# ✨ 系統功能

## 👤 會員系統

提供：

* 使用者註冊
* 使用者登入
* 帳號管理
* 個人資料維護

---

## 🏀 球員卡管理

可管理：

* 球員姓名
* 球隊
* 球員位置
* 卡片資訊
* 收藏資料

支援：

* 新增
* 修改
* 查詢
* 刪除

---

## 📊 運動數據

可查看：

* 球員基本資料
* 球員數據
* 收藏紀錄

---

## 📋 CRUD 管理

系統支援完整 CRUD 操作：

* Create（新增）
* Read（查詢）
* Update（修改）
* Delete（刪除）

---

# 🏗️ 系統架構

本專案採用 MVC 架構。

```text
PlayerCardSystem
│
├── Controller
├── Service
├── DAO
├── Model
├── Util
├── Exception
└── MySQL Database
```

---

# 📂 專案結構

```text
src
│
├── controller
├── service
│   └── impl
├── dao
│   └── impl
├── model
├── util
└── exception
```

---

# 💻 開發技術

| 技術      | 說明        |
| ------- | --------- |
| Java    | JDK 8+    |
| Swing   | GUI 使用者介面 |
| MVC     | 系統架構      |
| JDBC    | 資料庫連線     |
| MySQL   | 關聯式資料庫    |
| Eclipse | 開發工具      |
| Git     | 版本控制      |
| GitHub  | 專案管理      |

---

# 🗄️ 資料庫

主要資料表：

* Member
* PlayerCard
* Session

---

# 🚀 執行方式

## 1.專案

```bash
git clone https://github.com/你的GitHub帳號/PlayerCardSystem.git
```

---

## 2. 建立 MySQL 資料庫

```sql
CREATE DATABASE playercard;
```

---

## 3. 修改資料庫設定

請修改 `DBUtil.java` 中的資料庫連線資訊：

```properties
db.url=jdbc:mysql://localhost:3306/playercard
db.user=root
db.password=你的密碼
```

---

## 4. 執行程式

執行：

```text
Main.java
```

即可啟動系統。

---

# 🔮 未來規劃

* 球員卡圖片上傳
* 收藏清單管理
* 球員數據 API 串接
* 卡片搜尋與篩選
* 匯出 PDF 報表
* 收藏排行榜
* 雲端同步
* Android App 版本

---

# 👨‍💻 作者

**甘少棠**

Java Developer｜UI / UX Designer

專案名稱：

**Player Card Management System（球員卡管理系統）**

---

# 📄 License

MIT License

Copyright © 2026

