# 脚本运行顺序

ICE_tables  --->  ICE_sequences  --->  ICE_basicData



# 缺失数据

### publishers

- logo_path
- description



### users

- birthday
- tel
- avatar_path
- address



### game

- cover_path
- average_rate
- description



### chart, has_review, has_tag, orders, rate_game, reviews,  wishlist, write_review无数据



执行sequences时可能报错：ERROR 1418 (HY000): This function has none of DETERMINISTIC, NO SQL, or READS SQL DATA in its declaration and binary logging is enabled (you *might* want to use the less safe log_bin_trust_function_creators variable)

需要提前执行                          SET GLOBAL log_bin_trust_function_creators = 1;