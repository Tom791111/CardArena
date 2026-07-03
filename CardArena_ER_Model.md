# CardArena 球員卡交易系統 ER Model

> 依據 `sql/player_card_db.sql` 與 Java `model / dao` 檔案整理。

```mermaid
erDiagram
    members ||--o{ favorites : "member_id"
    player_cards ||--o{ favorites : "card_id"
    members ||--o{ cart_items : "member_id"
    player_cards ||--o{ cart_items : "card_id"
    members ||--o{ orders : "member_id"
    orders ||--o{ order_items : "order_id"
    player_cards ||--o{ order_items : "card_id"
    members ||--o{ scan_records : "member_id"
    player_cards ||--o{ price_history : "card_id"
    members ||--o{ trade_posts : "推定 member_id"
    player_cards ||--o{ trade_posts : "推定 card_id"
    members ||--o{ login_logs : "推定 member_id"
    members ||--o{ admin_logs : "推定 admin_id"
    members {
        INT id PK
        VARCHAR_50 account
        VARCHAR_100 password
        VARCHAR_100 name
        VARCHAR_100 email
        VARCHAR_30 phone
        VARCHAR_20 role
        TIMESTAMP created_at
    }
    player_cards {
        INT id PK
        VARCHAR_100 card_name
        VARCHAR_100 player_name
        VARCHAR_50 sport
        VARCHAR_100 team
        INT card_year
        VARCHAR_100 brand
        VARCHAR_50 card_number
        VARCHAR_20 grade
        DECIMAL_10_2 price
        INT quantity
        TEXT description
        VARCHAR_255 image_path
        TIMESTAMP created_at
    }
    favorites {
        INT id PK
        INT member_id FK
        INT card_id FK
        TIMESTAMP created_at
    }
    cart_items {
        INT id PK
        INT member_id FK
        INT card_id FK
        INT quantity
        DECIMAL_10_2 price
        TIMESTAMP created_at
    }
    orders {
        INT id PK
        INT member_id FK
        DECIMAL_10_2 total
        VARCHAR_30 status
        TIMESTAMP created_at
    }
    order_items {
        INT id PK
        INT order_id FK
        INT card_id FK
        INT quantity
        DECIMAL_10_2 price
    }
    scan_records {
        INT id PK
        INT member_id FK
        VARCHAR_255 image_path
        VARCHAR_100 keyword
        TEXT result_text
        TIMESTAMP created_at
    }
    price_history {
        INT id PK
        INT card_id FK
        DECIMAL_10_2 market_price
        DATE record_date
        VARCHAR_255 note
    }
    sports_categories {
        INT id PK
        VARCHAR_50 name
    }
    grading_companies {
        INT id PK
        VARCHAR_50 name
    }
    trade_posts {
        INT id PK
        INT member_id FK
        INT card_id FK
        VARCHAR_100 title
        TEXT content
        VARCHAR_20 status
        TIMESTAMP created_at
    }
    login_logs {
        INT id PK
        INT member_id FK
        VARCHAR_50 account
        BOOLEAN success
        TIMESTAMP created_at
    }
    admin_logs {
        INT id PK
        INT admin_id FK
        VARCHAR_255 action
        TIMESTAMP created_at
    }
```

## 補充說明

- 實線關聯：SQL 檔案中已設定 `FOREIGN KEY`。

- 虛線/推定關聯：`trade_posts.member_id`、`trade_posts.card_id`、`login_logs.member_id`、`admin_logs.admin_id` 在欄位命名上看起來應該關聯到 `members` 或 `player_cards`，但 SQL 原檔尚未加上 `FOREIGN KEY`。
