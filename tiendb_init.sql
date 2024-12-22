SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chitietsanpham
-- ----------------------------
DROP TABLE IF EXISTS `chitietsanpham`;
CREATE TABLE `chitietsanpham`  (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `anhchitiet` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `motachitiet` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                   `id_sp` int NOT NULL,
                                   PRIMARY KEY (`id`) USING BTREE,
                                   INDEX `fk`(`id_sp` ASC) USING BTREE,
                                   CONSTRAINT `fk` FOREIGN KEY (`id_sp`) REFERENCES `sanpham` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chitietsanpham
-- ----------------------------
INSERT INTO `chitietsanpham` VALUES
                                 (1, 'img_1/2.jpg', 'Tiền con gà Úc được phát hành bởi thành phố Kamberra trong lãnh thổ Úc, tiền được phát hành nhân dịp tết Đinh Dậu dành cho các nước châu Á sử dụng Âm lịch, trong đó có Việt Nam...', 1),
                                 (2, 'img_1/2.jpg', 'Cứ mỗi dịp sang năm mới, các nước lại tranh nhau phát hành tiền mới từ loại lưu hành cho đến loại kỷ niệm và Kamberra...', 2),
                                 (3, 'img_1/3.jpg', 'Cứ mỗi dịp sang năm mới, các nước lại tranh nhau phát hành tiền mới từ loại lưu hành cho đến loại kỷ niệm và Kamberra...', 3),
                                 (4, 'img_1/4.jpg', 'Năm 2018 Mậu Tuất là năm con chó trong quan niệm dân gian Á Đông, chó là một linh vật nằm trong bộ 12 con giáp...', 4),
                                 (5, 'img_1/5.jpg', 'Tiền in hình Chúa Giesu của Nagorno-Karabakh ngoài mệnh giá 2 drams vẫn còn một loại mệnh giá 10 drams...', 5),
                                 (6, 'img_1/6.jpg', 'Tiền hình Thiên chúa Jesus mang trên mình mệnh giá 2 drams của vùng lãnh thổ Nagorno-Karabakh thuộc Armenia...', 6),
                                 (7, 'img_1/7.jpg', 'Phượng hoàng (hay còn gọi là con phụng) đã xuất hiện cách đây 7000 năm trước trong văn hóa TQ...', 7),
                                 (8, 'img_1/8.jpg', 'Rùa là con vật nằm trong bộ tứ linh Long-Lân-Quy-Phụng, là linh vật của cát tường, may mắn...', 8),
                                 (9, 'img_1/9.jpg', 'Kỳ lân là một con vật nửa rồng nửa thú, xuất hiện trong thần thoại của các nước phương Đông...', 9),
                                 (10, 'img_1/10.jpg', 'Dù chỉ con vật trong tưởng tượng của người cổ đại, nhưng Rồng được xem là có thể sống trên mặt đất, bầu trời...', 10),
                                 (11, 'img_1/11.jpg', '200 MALOTI LESOTHO 2023 KỶ NIỆM SINH NHẬT 60 TUỔI CỦA VUA LETSIE III', 11),
                                 (12, 'img_1/12.jpg', '100 CENTS FIJI 2023 KỶ NIỆM QUAN HỆ NGOẠI GIAO VỚI TRUNG QUỐC', 12),
                                 (13, 'img_1/13.jpg', '20 HRYVEN UKRAINE 2023 KỶ NIỆM 1 NĂM CUỘC CHIẾN TRANH VỆ QUỐC', 13),
                                 (14, 'img_1/14.jpg', '100 NGULTRUM BHUTAN 2016 KỶ NIỆM THÁI TỬ TRÒN 1 TUỔI', 14),
                                 (15, 'img_1/15.jpg', '20 ZLOTYCH POLAND 2023 KỶ NIỆM 550 NĂM NGÀY SINH NHÀ THIÊN VĂN HỌC NICOLAUS COPERNICUS', 15),
                                 (16, 'img_1/16.jpg', '50000 LIVRES LIBAN 2015 KỶ NIỆM 70 NĂM THÀNH LẬP QUÂN ĐỘI', 16),
                                 (17, 'img_1/17.jpg', '50000 LIVRES LIBAN 2014 KỶ NIỆM 50 NĂM THÀNH LẬP NGÂN HÀNG', 17),
                                 (18, 'img_1/18.jpg', '50000 LIVRES LIBAN 2013 KỶ NIỆM 70 NĂM ĐỘC LẬP', 18),
                                 (19, 'img_1/19.jpg', '100 TAKA BANGLADESH 2022 KỶ NIỆM KHÁNH THÀNH CẦU PADMA', 19),
                                 (20, 'img_1/20.jpg', '50 TAKA BANGLADESH 2022 KỶ NIỆM 50 NĂM HOÀN THÀNH HIẾN PHÁP VÀ TỐI CAO PHÁP VIỆN', 20),
                                 (21, 'img_1/21.jpg', '50 TAKA BANGLADESH 2022 KỶ NIỆM KHÁNH THÀNH TUYẾN METRO DHAKA', 21),
                                 (22, 'img_1/22.jpg', '88 CENTS FIJI 2022 KỶ NIỆM QUAN HỆ NGOẠI GIAO VỚI TRUNG QUỐC', 22),
                                 (23, 'img_1/23.jpg', '22 RIYALS QATAR 2022 KỶ NIỆM WORLD CUP', 23),
                                 (24, 'img_1/24.jpg', '2000 DINARS ALGERIA 2022 KỶ NIỆM HỘI NGHỊ LIÊN ĐOÀN Ả RẬP LẦN THỨ 31', 24),
                                 (25, 'img_1/25.jpg', '100000 RIELS CAMBODIA 2012 MỪNG THỌ 60 TUỔI CỦA VUA SIHAMONI', 25),
                                 (26, 'img_1/26.jpg', '300000 RIELS CAMBODIA 2021 KỶ NIỆM HIỆP ĐỊNH PARIS', 26),
                                 (27, 'img_1/27.jpg', '150000 RIELS CAMBODIA 2019 KỶ NIỆM 15 LÊN NGÔI CỦA VUA SIHAMONI', 27),
                                 (28, 'img_1/28.jpg', 'TIỀN KỶ NIỆM NHÀ DU HÀNH VŨ TRỤ LEONID KADENYUK', 28),
                                 (29, 'img_1/29.jpg', '100 KARBOVANTSIV UKRAINE 2017 KỶ NIỆM TỜ TIỀN ĐẦU TIÊN THỜI LẬP QUỐC', 29),
                                 (30, 'img_1/30.jpg', '100 HRYVEN UKRAINE 2018 KỶ NIỆM THAY ĐỔI ĐƠN VỊ TIỀN TỆ', 30),
                                 (31, 'img_1/31.1.jpg', '1 YEN NHẬT 1948 – 1950', 31),
                                 (32, 'img_1/32.1.jpg', '50 SEN NHẬT 1947 – 1948', 32),
                                 (33, 'img_1/33.1.jpg', '50 SEN NHẬT 1946 – 1947', 33),
                                 (34, 'img_1/34.1.jpg', '5 SEN NHẬT 1944 – 1945', 34),
                                 (35, 'img_1/35.1.jpg', '10 SEN NHẬT 1944', 35),
                                 (36, 'img_1/36.1.jpg', 'XU 1 FARTHING GEORGE V 1911 – 1936', 36),
                                 (37, 'img_1/37.1.jpg', 'XU 3 PENCE ELIZABETH II 1953 – 1963', 37),
                                 (38, 'img_1/38.1.jpg', 'XU 6 PENCE GEORGE VII 1949 – 1952', 38),
                                 (39, 'img_1/39.1.jpg', 'XU 6 PENCE GEORGE VII 1947 – 1948', 39),
                                 (40, 'img_1/40.1.jpg', 'Xu con tàu vua George VI có ý nghĩa như Thuận buồm xuôi gió, rất thích hợp để làm phong thủy...', 40);


-- ----------------------------
-- Table structure for ct_hoadon
-- ----------------------------
DROP TABLE IF EXISTS `ct_hoadon`;
CREATE TABLE `ct_hoadon`  (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `id_hoadon` int NOT NULL,
                              `id_sanpham` int NOT NULL,
                              `soluong` int NOT NULL,
                              `dongia` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                              PRIMARY KEY (`id`) USING BTREE,
                              INDEX `id_hoadon`(`id_hoadon` ASC) USING BTREE,
                              INDEX `id_sanpham`(`id_sanpham` ASC) USING BTREE,
                              CONSTRAINT `id_hoadon` FOREIGN KEY (`id_hoadon`) REFERENCES `hoadon` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                              CONSTRAINT `id_sanpham` FOREIGN KEY (`id_sanpham`) REFERENCES `sanpham` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `user_id` int NOT NULL,
                        `product_id` int NOT NULL,
                        `quantity` int NOT NULL DEFAULT 1,
                        `price` double NOT NULL,
                        `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`),
                        UNIQUE (`user_id`, `product_id`),
                        FOREIGN KEY (`user_id`) REFERENCES `nguoidung` (`id`),
                        FOREIGN KEY (`product_id`) REFERENCES `sanpham` (`id`),
                        CHECK (`quantity` > 0)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci;

-- ----------------------------
-- Records of ct_hoadon
-- ----------------------------

-- ----------------------------
-- Table structure for hoadon
-- ----------------------------
DROP TABLE IF EXISTS `hoadon`;
CREATE TABLE `hoadon`  (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `ngaylap_hd` date NOT NULL,
                           `id_ngdung` int NOT NULL,
                           `ten` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                           `dia_chi_giao_hang` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                           `tongtien` double NOT NULL,
                           `pt_thanhtoan` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                           `ghichu` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                           `hash` varchar(500) NOT NULL,
                           `signature` varchar(500) NOT NULL,
                           PRIMARY KEY (`id`) USING BTREE,
                           INDEX `id_ngdung`(`id_ngdung` ASC) USING BTREE,
                           CONSTRAINT `id_ngdung` FOREIGN KEY (`id_ngdung`) REFERENCES `nguoidung` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hoadon
-- ----------------------------

-- ----------------------------
-- Table structure for loaisp
-- ----------------------------
DROP TABLE IF EXISTS `loaisp`;
CREATE TABLE `loaisp`  (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `tenloai` varchar(150) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of loaisp
-- ----------------------------
INSERT INTO `loaisp` VALUES (1, 'Tiền Phong Thủy');
INSERT INTO `loaisp` VALUES (2, 'Tiền Sưu Tầm');
INSERT INTO `loaisp` VALUES (3, 'Tiền Xu');

-- ----------------------------
-- Table structure for nguoidung
-- ----------------------------
DROP TABLE IF EXISTS `nguoidung`;
CREATE TABLE `nguoidung`  (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `hoten` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                              `sdt` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                              `diachi` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                              `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                              `ten_dangnhap` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                              `matkhau` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                              `admin` int NOT NULL DEFAULT 1,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of nguoidung
-- ----------------------------
INSERT INTO `nguoidung` VALUES (1, 'dat', '017458234', 'bl', 'cd@gmail.com', 'cd', '123', 1);
INSERT INTO `nguoidung` VALUES (2, 'chieu', '0345682702', 'tg', 'tranhaichieu2002@gmail.com', 'chieu', '123123', 0);
INSERT INTO `nguoidung` VALUES (3, 'ctd', '0376505735', 'bl', 'ctd@gmail.com', 'ctd', '123', 1);
INSERT INTO `nguoidung` VALUES (4, 'demo', '0376505735', 'bl', 'cd1@gmail.com', 'demo', '123', 1);
INSERT INTO `nguoidung` VALUES (5, 'nm', '0376505735', 'bk', 'cd001@gmail.com', 'nm', '123', 1);

-- ----------------------------
-- Table structure for public_keys
-- ----------------------------
DROP TABLE IF EXISTS `public_keys`;
CREATE TABLE `public_keys`  (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `user_id` int NOT NULL,
                                `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                                `key_value` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                                `created_at` timestamp NOT NULL DEFAULT current_timestamp,
                                `end_at` timestamp NULL,
                                `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                                PRIMARY KEY (`id`) USING BTREE,
                                INDEX `user_id`(`user_id` ASC) USING BTREE,
                                CONSTRAINT `public_keys_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `nguoidung` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of public_keys
-- ----------------------------
INSERT INTO `public_keys` VALUES (6, 1, 'dat', 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAst4pJSyTQbxMNaUee4dhXIHz/d2+HmYbFdNHGJI6kB3mqogGeydmu+qNIpwAbI+aEHdbsgBeAGgrCWBfOWw4rDEpifQX52G6LTTfybyUlO23cnAcgSZ4hmTEMN8KeEK/s1tJeyOX1hhRiShj096VZuob92wPFJQgDKMCfd/U61o6KdPiQW0QCSsSStCYm0hGa4nzWwtd5VFlmm3LV1RvBywjUo14hxjL15EnQ62wi5R7x+DGW6wPEM0t8NIOdLnaJVzJTumqYGciioYz3JJgblxXmGl9uXM+vlPAA0td37MLIOgOe3PWNdvOT2qHYlGM46AJUtJnfc6uLcsxMLUnKwIDAQAB', '2023-12-27 13:13:58', '0000-00-00 00:00:00','Xac thuc');
INSERT INTO `public_keys` VALUES (7, 2, 'chieu', 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw+IJgoMD57WDAN4JaDXPkS4ZKSKvX3XvGochdsWDzk6w4TCRDo+1hmoNxQtoP2Xzg448REe3lvY94F9ZtI1tvsGaLuKmPyRUJfG4dRW3K3LEC9gafQNR7K3EJiSCscRtRJ50tj1sCkxbbIL3LX3wb1hJzCjQWZpbrh6uNymV075YxpcJamhy8TmjPSmxiPhBdr1vLxnyojgSQ+pzro4mj51muuFyMjrBQ4evVSf2pKuQX9cDbR9gZLr8DIbmd3pkrHZTS+fTyQxomrjJqvroFzdBJSP4festWDS6oQd890KuQCVw+2CLUOfdzaTSSgAi0PpKBKi5DYPHb6ko2eEENwIDAQAB', '2023-12-27 13:13:58', '0000-00-00 00:00:00','Xac thuc');
INSERT INTO `public_keys` VALUES (8, 3, 'ctd', 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk0vz2Xa5dmPwv6yOkDJe1ixdurFO0qxxzE4kH5W9lPikDwYx5jaqVzKA/zZ9P8juWE5lnivIGrLdVXXUHQAAQ0SOdFWeU9D8fS4YSnVgAUlCPxlnaDEfLuyr8YmCILniaYvY0PkLHzSnHLwegHyy+mv8+L6VSK0BF8nI4YRGTWBQgXPaZ6TyBS8AG2Ta3b9+bxsKTUJHnOTblpHpUR3Y+4pR5eHd4+iqbo2MdQyDSPbWiTdxD2AkXdFXs2+zvY037ym4EASxlWeTRsKH3+z/rtBMlOwT/nhp9ff42n04n+PhmWRSs0TD0ZuUjNB47HjHfFqShAS2R3Z+I9Fw9CuaaQIDAQAB', '2023-12-27 13:13:58', '0000-00-00 00:00:00','Xac thuc');
INSERT INTO `public_keys` VALUES (9, 4, 'demo', 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8dVPq6VKxB6zlKQtdwKsR1JU/F4aV+6kfD6vnlxpJsfEjqVcAdZStFPvr024hom231YIMCSSKPASGqT/iCg/+HEmAgHTeHgZqBcar9yFdSkzrCl5TdJ+9jfZR97xCn0PE9K8rpxmHDIrxRQftHv4kIe+rgryLIJpHoZvJcUfIcUT4HcaV8tVGUGmWdSecaJQISL9eqLDJXXUZtgU7GO2ayeMHCa4plBmZhZBnLXnCpQqj9uDwpk1QlX8FpEqSJJ2Z2CBfcUYyL1HUGiTcHd1Fh4EurxUrLGCBzNv2JGiqfZd5swrps7DOG3AY+aHjlpSKtr18lOY+bELVRfh2SR5GQIDAQAB', '2023-12-27 13:13:59', '0000-00-00 00:00:00','Xac thuc');

-- ----------------------------
-- Table structure for sanpham
-- ----------------------------
DROP TABLE IF EXISTS `sanpham`;
CREATE TABLE `sanpham`  (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `tensp` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                            `mota` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                            `hinhanh` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                            `giagoc` double NOT NULL,
                            `giakm` double NOT NULL,
                            `id_loaisp` int NOT NULL,
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `loaisp`(`id_loaisp` ASC) USING BTREE,
                            CONSTRAINT `loaisp` FOREIGN KEY (`id_loaisp`) REFERENCES `loaisp` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sanpham
-- ----------------------------
INSERT INTO `sanpham` VALUES (1, 'TIỀN CON GÀ KAMBERRA 2017', 'Tiền con gà Úc được phát hành bởi thành phố Kamberra trong lãnh thổ Úc, tiền được phát hành nhân dịp tết Đinh Dậu dành cho các nước châu Á sử dụng Âm lịch, trong đó có Việt Nam. Tiền Kamberra có chất liệu polymer, màu sắc tươi mới cùng kỹ thuật in ấn tuyệt hảo của Úc sẽ đem lại sự khác biệt so với những dòng tiền lì xì khác.

Giống như mỗi năm Tết đến, mỗi con giáp sẽ được phát hành tiền hình con linh vật đó. Hình ảnh chú gà trống trên mặt trước tờ tiền là hình ảnh thật, riêng chú gà mặt sau được các họa sĩ vẽ nên cùng vòng kim can 12 con giáp tương ứng với mỗi năm.

Để đáp ứng nhu cầu tiền lì xì may mắn mỗi năm. Tiền con gà luôn trong tình trạng cháy hàng do số lượng có hạn, shop sẽ đáp ứng đầy đủ cho khách hàng những sản phẩm tốt nhất ', 'img_1/2.jpg', 120000, 0, 1);
INSERT INTO `sanpham` VALUES
    (2, 'TIỀN CON HEO KAMBERRA 2019',
     'Cứ mỗi dịp sang năm mới, các nước lại tranh nhau phát hành tiền mới từ loại lưu hành cho đến loại kỷ niệm và Kamberra (Canberra - một thành phố của Úc) cũng phát hành định kì mỗi năm một lần. Nhân dịp sắp bước sang năm 2019, Kamberra đã cho ra đời tiền con heo kỷ niệm năm mới 2019, lấy hình tượng con heo trong 12 con giáp làm chủ đề của tiền.

     Tiền con heo Kamberra có chất liệu polymer, màu sắc sặc sỡ, vẫn sử dụng mệnh giá 50 như những tờ phát hành trước đó như cọp, thỏ, rồng, rắn, ngựa, dê, khỉ, gà... ứng với những con giáp trong văn hóa Á Đông. Vẻ ngoài bắt mắt cùng với chú heo dễ thương chắc chắn sẽ khiến bạn không thể nán lại để sở hữu, tiền cũng là vật phẩm mang ý nghĩa phong thủy thích hợp làm quà tặng cho bạn bè, người thân của bạn.

     Tiền lì xì 2019 có vẻ khá phong phú về chủng loại, bạn có thể thêm các sản phẩm tiền hình con heo khác như Tiền con heo Belarus, tiền con heo Lithuania.',
     'img_1/1.jpg', 120000, 0, 1);
INSERT INTO `sanpham` VALUES (3, 'TIỀN CON CHÓ KAMBERRA 2018', 'Cứ mỗi dịp sang năm mới, các nước lại tranh nhau phát hành tiền mới từ loại lưu hành cho đến loại kỷ niệm và Kamberra (Canberra - một thành phố của Úc) cũng phát hành định kì mỗi năm một lần. Nhân dịp sắp bước sang năm 2018, Kamberra đã cho ra đời tiền con chó kỷ niệm năm mới 2018, lấy hình tượng con chó trong 12 con giáp làm chủ đề của tiền.

Tiền con chó Kamberra có chất liệu polymer, màu sắc sặc sỡ, vẫn sử dụng mệnh giá 50 như những tờ phát hành trước đó như cọp, thỏ, rồng, rắn, ngựa, dê, khỉ, gà... ứng với những con giáp trong văn hóa Á Đông. Vẻ ngoài bắt mắt cùng với chú chó (sói) dễ thương chắc chắn sẽ khiến bạn không thể nán lại để sở hữu, tiền cũng là vật phẩm mang ý nghĩa phong thủy thích hợp làm quà tặng cho bạn bè, người thân của bạn.

Tiền lì xì 2018 có vẻ khá phong phú về chủng loại, bạn có thể thêm các sản phẩm tiền hình con chó khác như Tiền con chó Belarus, tiền con chó Lithuania', 'img_1/3.jpg', 120000, 0, 1);
INSERT INTO `sanpham` VALUES (4, 'TIỀN CON CHÓ BELARUS', 'Năm 2018 Mậu Tuất là năm con chó trong quan niệm dân gian Á Đông, chó là một linh vật nằm trong bộ 12 con giáp nằm ở vị trí thứ 11. Trong quan niệm của người Việt thì chó là con vật có thể đem đến những điều may mắn, mang đến thuận lợi và nhiều niềm vui. Hình tượng chó mang ý nghĩa trung thành, khai hóa, trân quý tình bạn. Các vật phẩm phong thủy liên quan đến chó biểu trưng cho sự tận tụy, cố gắng bền bỉ, xua đuổi điều xấu và cải thiện các mối quan hệ. Trên thế giới, chó cũng được thờ phụng và được xem như khai tổ của các nền văn hóa. Tượng chó được đặt ở khắp nơi, với người Việt đặt một cặp chó trước cửa nhà với ý nghĩa trừ tà, cầu phúc.

Tiền mệnh giá 5 ruble Belarus này được thiết kế đơn giản, hoa văn nhẹ nhàng, mặt trước gồm hai con chó màu xanh, mặt sau có biểu tượng hiệp sĩ cưỡi ngựa sắt.

Tiền rất thích hợp để làm quà tặng cho bạn bè, người thân, và nếu người tuổi Tuất nhận được chúng trong dịp sinh nhật, mừng tuổi thì niềm vui sẽ gấp đôi bộn phần, đây là một món quà đầy thú vị và đặc sắc.

Tiền belarus được chúng tôi chọn làm tiền lì xì 2018 nhân dịp chào đón năm Mậu Tuất, món quà mới mẻ này rất thích hợp để thay thế cho những món quà cũ kĩ đã trở nên nhàm chán và thiếu sức sống. Hãy liên hệ với shop để đặt ngay tiền in hình chó hấp dẫn và lạ lẫm này nhé.
', 'img_1/4.jpg', 30000, 0, 1);
INSERT INTO `sanpham` VALUES (5, 'TIỀN HÌNH CHÚA JESUS MỆNH GIÁ 10 DRAMS', 'Tiền in hình Chúa Giesu của Nagorno-Karabakh ngoài mệnh giá 2 drams vẫn còn một loại mệnh giá 10 drams, vẫn đơn giản về màu sắc, không cầu kì về họa tiết, phác họa về đức tin của người dân Armenia với Đức chúa Trời.

Nagorno-Karabakh là một vùng lãnh thổ không giáp biển, nằm lọt thỏm trong lãnh thổ Azerbaijan, vùng này hầu hết là đồi núi và rừng và có diện tích 8.223 km2. Đa số cư dân bản địa là người Armenia và theo tôn giáo Ki-tô hữu, theo giáo hội Tông truyền Armenia. Khu vực trở thành một vấn đề tranh chấp giữa 2 quốc gia Armenia-Azerbaijan kể từ sau khi Liên Xô sụp đổ.

Đây chắc hẳn là món quà mang ý nghĩa rất đặc biệt trong mùa Giáng sinh và Lễ phục sinh. Nếu bạn có theo đạo Công giáo và yêu thích tờ tiền này hoặc cần số lượng tiền hình chúa jesus để lì xì thì hãy gọi ngay cho shop sdt 0933645494', 'img_1/5.jpg', 60000, 0, 1);
INSERT INTO `sanpham` VALUES (6, 'TIỀN HÌNH THIÊN CHÚA JESUS MỆNH GIÁ 2 DRAMS', 'Tiền hình Thiên chúa Jesus mang trên mình mệnh giá 2 drams của vùng lãnh thổ Nagorno-Karabakh thuộc Armenia.

Nagorno-Karabakh là một vùng lãnh thổ không giáp biển, nằm lọt thỏm trong lãnh thổ Azerbaijan, vùng này hầu hết là đồi núi và rừng và có diện tích 8.223 km2. Đa số cư dân bản địa là người Armenia và theo tôn giáo Ki-tô hữu, theo giáo hội Tông truyền Armenia. Khu vực trở thành một vấn đề tranh chấp giữa 2 quốc gia Armenia-Azerbaijan kể từ sau khi Liên Xô sụp đổ.

Tiền chúa Giesu được in rất đơn giản, gần như tối giản về màu mực, mặt sau của rửa tội tại sông Jordan, nhưng không vì thế mà người Armenia lại rất trân trọng tờ tiền này.

Hãy gọi ngay cho shop sdt 0933645494 để được đặt hàng và tư vấn
', 'img_1/6.jpg', 60000, 0, 1);
INSERT INTO `sanpham` VALUES (7, 'TIỀN CON PHỤNG BHUTAN', 'Phượng hoàng (hay còn gọi là con phụng) đã xuất hiện cách đây 7000 năm trước trong văn hóa TQ, thuở ban đầu nó là tín ngưỡng của các bộ lạc vùng Hoa Hạ. Đến thời Hán, nó được xem như biểu tượng của hoàng hậu cặp đôi với rồng - biểu tượng của hoàng đế.
Phượng hoàng tượng trưng cho đức hạnh, duyên dáng, thanh nhã và biểu thị cho sự hòa hợp âm dương. Bạn có thể nhìn thấy chúng trong các trang trí của đám cưới hay trong hoàng tộc, tượng trưng cho cuộc sống lứa đôi hạnh phúc yên lành. Nó là vua của các loài chim, có khả năng phục hồi sau các thất bại, ý chí kiên cường, sức mạnh vô địch, đề cao hòa bình và sự thịnh vượng.
Tiền con phụng Bhutan cũng xuất từ trong văn hóa tín ngưỡng của người Tây Tạng, hình ảnh con phụng in lên trên mệnh giá 5 ngultrum của mình sau 1 ngultrum (con rồng) với ngụ ý rồng-phụng đứng đầu thì quốc gia yên ấm hạnh phúc, và họ cũng rất yêu quý quốc vương và hoàng hậu.
Tiền hiện đang được bán tại shop, quý khách hàng có thể dùng làm quà tặng tiền may mắn, hoặc có thể dùng làm tiền lì xì, trưng bày phong thủy dành tặng người thân, bạn bè, đối tác,... và cũng không thể thiếu trong bộ sưu tập tiền thế giới của quý khách.
', 'img_1/7.jpg', 30000, 0, 1);
INSERT INTO `sanpham` VALUES (8, 'TIỀN CON RÙA BRAZIL', 'Rùa là con vật nằm trong bộ tứ linh Long-Lân-Quy-Phụng, là linh vật của cát tường, may mắn, là tượng trưng cho sự trường thọ. Hình ảnh quy xuất hiện rất nhiều trong truyền thuyết người Việt, chuyện nỏ thần Kim Quy của An Dương Vương, chuyện hồ Hoàn Kiếm của vua Lê Lợi,... hay lão rùa chuyên chở thầy trò Đường Tăng qua sông trong tác phẩm Tây Du Ký của Ngô Thừa Ân (Trung Quốc).
Đa số rùa có tuổi thọ rất cao, thậm chí có con gần 200 năm nên người ta xem chúng là hiện thân của sự trường tồn vĩnh cữu. Theo quan niệm của phong thủy Á Đông nếu đặt rùa theo cá hướng khác nhau sẽ mang lại những lợi ích khác nhau như: mang điềm lành đến nhà, cải thiện sức khỏe, gia tăng thu nhập, gia tăng tích lũy, cải thiện sự nghiệp,...
Brazil tuy không phải là một quốc gia Á Đông nhưng các nhà sưu tập châu Á lại xem tiền con rùa là một vật phẩm mang lại sự may mắn cho họ, họ treo tiền trong phòng khách hoặc bày biện trên bàn làm việc. Tiền cũng có thể để vào ví như một vật thu hút tài lộc, sự nghiệp hạnh thông.
', 'img_1/8.jpg', 80000, 0, 1);
INSERT INTO `sanpham` VALUES (9, 'TIỀN CON LÂN MYANMAR', 'Kỳ lân là một con vật nửa rồng nửa thú, xuất hiện trong thần thoại của các nước phương Đông. Theo văn hóa của mỗi dân tộc, chúng có thể có sừng hoặc không có sừng, sừng của nó như sừng nai, mắt tinh, mũi sư tử, miệng rộng, trán cao, chân ngựa, đuôi bò, toàn thân săn chắc, toát lên vẻ kiêu hãnh dữ tợn. Kỳ lân cũng là con vật báo điềm lành sắp đến, biểu tượng cho sự nguy nga, trường thọ, hạnh phúc, tuy bề ngoài trông to lớn hung dữ nhưng lại là một con vật hiền lành.
Nó được sử dụng như những con vật thiêng bảo vệ các lăng, miếu, chùa, đình, ... chúng thường được đặt chung thành một cặp ở hai bên cửa ra vào. Lân trấn giữ tà khí, bảo vệ cho chủ nhân trước những hiểm họa không ngờ tới, vì thế nhiều người xem chúng là một phước lành, bùa hộ mạng cho gia chủ.
Tiền hình con lân xuất hiện trên tất cả các tờ tiền Myanmar, điều này chứng tỏ quốc gia phật giáo này có tính ngưỡng rất cao, họ xem kỳ lân bảo vệ quốc gia của họ khỏi các thế lực bên ngoài, nhất là với đạo phật luôn mong muốn những điều an lành tốt đẹp.
Tiền mang lại phước lành cho gia chủ, kích thước nhỏ gọn nằm gọn trong tầm tay, ngoài mục đích phong thủy ra tiền còn mang bên mình văn hóa đặc trưng của đất nước Myanmar qua hình ảnh chùa chiền của dân tộc Burma.
Tiền hiện đang có bán tại shop, bạn có thể lựa chọn làm tiền lì xì hoặc tiền may mắn đều phù hợp, hoặc có thể nằm gọn trong một ngăn album bộ sưu tập tiền thế giới chẳng hạn, ngoài ra cũng là vật phẩm phong thủy không thể thiếu trên bàn làm việc của bạn.
', 'img_1/9.jpg', 30000, 0, 1);
INSERT INTO `sanpham` VALUES (10, 'TIỀN CON RỒNG BHUTAN', 'Dù chỉ con vật trong tưởng tượng của người cổ đại, nhưng Rồng được xem là có thể sống trên mặt đất, bầu trời, dưới nước và hiện thân cho sức mạnh, quyền lực. Tất cả những khả năng hoàn hảo Rồng đều có thể đảm nhận được, chính vì thế nên nó biểu tượng của những vị vua châu Á. Rồng tương đương với Thiên Tử, là hình ảnh của Thiên Tử - con của trời nên mọi việc trong thiên hạ là bá chủ.
Khi so sánh các nền văn hóa phong kiến của châu Á: Việt Nam - Trung Quốc - Triều Tiên - Nepal - Ấn Độ,... nơi đâu cũng có sự hiện diện của linh vật này. Bhutan không ngoại lệ, họ đã đem Rồng vào văn hóa từ ngàn xưa và ngay trên tiền giấy của mình, 2 con Rồng đã chễm chệ từ lúc phát hành tờ tiền lần đầu tiên.
Tiền may mắn con rồng Bhutan mang phong cách Phật giáo Tây Tạng, quốc gia này sử dụng chữ Tạng làm quốc ngữ của mình. Tiếng Anh xuất hiện trên tờ tiền do quốc vương Jigme Singye Wangchuck đã từng du học ở nước ngoài nên muốn quốc gia của mình phải hội nhập văn minh thế giới.
Năm đó, Thái tử du học về nước đã được vua cha truyền ngôi và ngay lập tức nhà vua đòi... dân chủ. Hiến pháp mới của quốc gia được ban hành năm 2005, và đến năm 2008, Bhutan từ một quốc gia quân chủ chuyên chế chuyển sang quân chủ lập hiến, trở thành quốc gia dân chủ non trẻ nhất thế giới.
Tiền là một minh chứng của Quốc Vương muốn giữ vững bản sắc văn hóa của nước mình cũng như muốn giới thiệu đến bạn bè quốc tế về một thiên đường hạnh phúc nhất thế giới.
Hiện tại đây là vật phẩm tiền lì xì bán chạy nhất tại shop, hoặc nếu bạn cần ráp bộ tiền thế giới mỗi tờ đại diện một quốc gia thì đừng nên bỏ qua tờ tiền này', 'img_1/10.jpg', 30000, 0, 1);
INSERT INTO `sanpham` VALUES (11, '200 MALOTI LESOTHO 2023 KỶ NIỆM SINH NHẬT 60 TUỔI CỦA VUA LETSIE III', '200 MALOTI LESOTHO 2023 KỶ NIỆM SINH NHẬT 60 TUỔI CỦA VUA LETSIE III', 'img_1/11.jpg', 800000, 0, 2);
INSERT INTO `sanpham` VALUES (12, '100 CENTS FIJI 2023 KỶ NIỆM QUAN HỆ NGOẠI GIAO VỚI TRUNG QUỐC', '100 CENTS FIJI 2023 KỶ NIỆM QUAN HỆ NGOẠI GIAO VỚI TRUNG QUỐC', 'img_1/12.jpg', 600000, 0, 2);
INSERT INTO `sanpham` VALUES (13, '20 HRYVEN UKRAINE 2023 KỶ NIỆM 1 NĂM CUỘC CHIẾN TRANH VỆ QUỐC', '20 HRYVEN UKRAINE 2023 KỶ NIỆM 1 NĂM CUỘC CHIẾN TRANH VỆ QUỐC', 'img_1/13.jpg', 600000, 0, 2);
INSERT INTO `sanpham` VALUES (14, '100 NGULTRUM BHUTAN 2016 KỶ NIỆM THÁI TỬ TRÒN 1 TUỔI', '100 NGULTRUM BHUTAN 2016 KỶ NIỆM THÁI TỬ TRÒN 1 TUỔI', 'img_1/14.jpg', 350000, 0, 2);
INSERT INTO `sanpham` VALUES (15, '20 ZLOTYCH POLAND 2023 KỶ NIỆM 550 NĂM NGÀY SINH NHÀ THIÊN VĂN HỌC NICOLAUS COPERNICUS', '20 ZLOTYCH POLAND 2023 KỶ NIỆM 550 NĂM NGÀY SINH NHÀ THIÊN VĂN HỌC NICOLAUS COPERNICUS', 'img_1/15.jpg', 1600000, 0, 2);
INSERT INTO `sanpham` VALUES (16, '50000 LIVRES LIBAN 2015 KỶ NIỆM 70 NĂM THÀNH LẬP QUÂN ĐỘI', '50000 LIVRES LIBAN 2015 KỶ NIỆM 70 NĂM THÀNH LẬP QUÂN ĐỘI', 'img_1/16.jpg', 650000, 0, 2);
INSERT INTO `sanpham` VALUES (17, '50000 LIVRES LIBAN 2014 KỶ NIỆM 50 NĂM THÀNH LẬP NGÂN HÀNG', '50000 LIVRES LIBAN 2014 KỶ NIỆM 50 NĂM THÀNH LẬP NGÂN HÀNG', 'img_1/17.jpg', 650000, 0, 2);
INSERT INTO `sanpham` VALUES (18, '50000 LIVRES LIBAN 2013 KỶ NIỆM 70 NĂM ĐỘC LẬP', '50000 LIVRES LIBAN 2013 KỶ NIỆM 70 NĂM ĐỘC LẬP', 'img_1/18.jpg', 650000, 0, 2);
INSERT INTO `sanpham` VALUES (19, '100 TAKA BANGLADESH 2022 KỶ NIỆM KHÁNH THÀNH CẦU PADMA', '100 TAKA BANGLADESH 2022 KỶ NIỆM KHÁNH THÀNH CẦU PADMA', 'img_1/19.jpg', 150000, 0, 2);
INSERT INTO `sanpham` VALUES (20, '50 TAKA BANGLADESH 2022 KỶ NIỆM 50 NĂM HOÀN THÀNH HIẾN PHÁP VÀ TỐI CAO PHÁP VIỆN', '50 TAKA BANGLADESH 2022 KỶ NIỆM 50 NĂM HOÀN THÀNH HIẾN PHÁP VÀ TỐI CAO PHÁP VIỆN', 'img_1/20.jpg', 80000, 0, 2);
INSERT INTO `sanpham` VALUES (21, '50 TAKA BANGLADESH 2022 KỶ NIỆM KHÁNH THÀNH TUYẾN METRO DHAKA', '50 TAKA BANGLADESH 2022 KỶ NIỆM KHÁNH THÀNH TUYẾN METRO DHAKA', 'img_1/21.jpg', 80000, 0, 2);
INSERT INTO `sanpham` VALUES (22, '88 CENTS FIJI 2022 KỶ NIỆM QUAN HỆ NGOẠI GIAO VỚI TRUNG QUỐC', '88 CENTS FIJI 2022 KỶ NIỆM QUAN HỆ NGOẠI GIAO VỚI TRUNG QUỐC', 'img_1/22.jpg', 500000, 0, 2);
INSERT INTO `sanpham` VALUES (23, '22 RIYALS QATAR 2022 KỶ NIỆM WORLD CUP', '22 RIYALS QATAR 2022 KỶ NIỆM WORLD CUP', 'img_1/23.jpg', 1300000, 0, 2);
INSERT INTO `sanpham` VALUES (24, '2000 DINARS ALGERIA 2022 KỶ NIỆM HỘI NGHỊ LIÊN ĐOÀN Ả RẬP LẦN THỨ 31', '2000 DINARS ALGERIA 2022 KỶ NIỆM HỘI NGHỊ LIÊN ĐOÀN Ả RẬP LẦN THỨ 31', 'img_1/24.jpg', 1200000, 0, 2);
INSERT INTO `sanpham` VALUES (25, '100000 RIELS CAMBODIA 2012 MỪNG THỌ 60 TUỔI CỦA VUA SIHAMONI', ' 100000 RIELS CAMBODIA 2012 MỪNG THỌ 60 TUỔI CỦA VUA SIHAMONI', 'img_1/25.jpg', 1300000, 0, 2);
INSERT INTO `sanpham` VALUES (26, '300000 RIELS CAMBODIA 2021 KỶ NIỆM HIỆP ĐỊNH PARIS', '300000 RIELS CAMBODIA 2021 KỶ NIỆM HIỆP ĐỊNH PARIS', 'img_1/26.jpg', 450000, 0, 2);
INSERT INTO `sanpham` VALUES (27, '150000 RIELS CAMBODIA 2019 KỶ NIỆM 15 LÊN NGÔI CỦA VUA SIHAMONI', '150000 RIELS CAMBODIA 2019 KỶ NIỆM 15 LÊN NGÔI CỦA VUA SIHAMONI', 'img_1/27.jpg', 300000, 0, 2);
INSERT INTO `sanpham` VALUES (28, 'TIỀN KỶ NIỆM NHÀ DU HÀNH VŨ TRỤ LEONID KADENYUK', 'TIỀN KỶ NIỆM NHÀ DU HÀNH VŨ TRỤ LEONID KADENYUK', 'img_1/28.jpg', 250000, 0, 2);
INSERT INTO `sanpham` VALUES (29, '100 KARBOVANTSIV UKRAINE 2017 KỶ NIỆM TỜ TIỀN ĐẦU TIÊN THỜI LẬP QUỐC', '100 KARBOVANTSIV UKRAINE 2017 KỶ NIỆM TỜ TIỀN ĐẦU TIÊN THỜI LẬP QUỐC', 'img_1/29.jpg', 250000, 0, 2);
INSERT INTO `sanpham` VALUES (30, '100 HRYVEN UKRAINE 2018 KỶ NIỆM THAY ĐỔI ĐƠN VỊ TIỀN TỆ', '100 HRYVEN UKRAINE 2018 KỶ NIỆM THAY ĐỔI ĐƠN VỊ TIỀN TỆ', 'img_1/30.jpg', 250000, 0, 2);
INSERT INTO `sanpham` VALUES (31, '1 YEN NHẬT 1948 – 1950', '1 YEN NHẬT 1948 – 1950', 'img_1/31.1.jpg', 30000, 0, 3);
INSERT INTO `sanpham` VALUES (32, '50 SEN NHẬT 1947 – 1948', '50 SEN NHẬT 1947 – 1948', 'img_1/32.1.jpg', 30000, 0, 3);
INSERT INTO `sanpham` VALUES (33, '50 SEN NHẬT 1946 – 1947', '50 SEN NHẬT 1946 – 1947', 'img_1/33.1.jpg', 80000, 0, 3);
INSERT INTO `sanpham` VALUES (34, '5 SEN NHẬT 1944 – 1945', '5 SEN NHẬT 1944 – 1945', 'img_1/34.1.jpg', 50000, 0, 3);
INSERT INTO `sanpham` VALUES (35, '10 SEN NHẬT 1944', '10 SEN NHẬT 1944', 'img_1/35.1.jpg', 60000, 0, 3);
INSERT INTO `sanpham` VALUES (36, 'XU 1 FARTHING GEORGE V 1911 – 1936', 'XU 1 FARTHING GEORGE V 1911 – 1936', 'img_1/36.1.jpg', 120000, 0, 3);
INSERT INTO `sanpham` VALUES (37, 'XU 3 PENCE ELIZABETH II 1953 – 1963', 'XU 3 PENCE ELIZABETH II 1953 – 1963', 'img_1/37.1.jpg', 50000, 0, 3);
INSERT INTO `sanpham` VALUES (38, 'XU 6 PENCE GEORGE VII 1949 – 1952', 'XU 6 PENCE GEORGE VII 1949 – 1952', 'img_1/38.1.jpg', 50000, 0, 3);
INSERT INTO `sanpham` VALUES (39, 'XU 6 PENCE GEORGE VII 1947 – 1948', 'XU 6 PENCE GEORGE VII 1947 – 1948', 'img_1/39.1.jpg', 50000, 0, 3);
INSERT INTO `sanpham` VALUES (40, 'XU HALF PENNY GEORGE VI 1937 – 1952', 'Xu con tàu vua George VI có ý nghĩa như Thuận buồm xuôi gió, rất thich hợp để làm phong thủy. Xu được phát hành lúc vua George tại ngôi từ năm 1937 đến 1952.', 'img_1/40.1.jpg', 100000, 0, 3);

-- ----------------------------
-- Table structure for tintuc
-- ----------------------------
DROP TABLE IF EXISTS `tintuc`;
CREATE TABLE `tintuc`  (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `tentintuc` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                           `nguoidang` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                           `ngaydang` date NOT NULL,
                           `mota` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                           `noidung` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                           `id_nguoidung` int NOT NULL,
                           PRIMARY KEY (`id`) USING BTREE,
                           INDEX `idtaikhoan`(`id_nguoidung` ASC) USING BTREE,
                           CONSTRAINT `idtaikhoan` FOREIGN KEY (`id_nguoidung`) REFERENCES `nguoidung` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tintuc
-- ----------------------------
INSERT INTO `tintuc` (`tentintuc`, `nguoidang`, `ngaydang`, `mota`, `noidung`, `id_nguoidung`)
VALUES
    ('Giới thiệu tiền con gà Kamberra 2017', 'Admin', '2024-12-13',
     'Tìm hiểu về tiền con gà Kamberra - quà tặng độc đáo nhân dịp Tết Đinh Dậu.',
     'Tiền con gà Úc được phát hành bởi thành phố Kamberra, là sản phẩm độc đáo nhân dịp Tết Đinh Dậu, phù hợp làm quà tặng phong thủy.', 1),

    ('Tiền con chó Kamberra 2018 - Vật phẩm phong thủy', 'Admin', '2024-12-13',
     'Khám phá tiền con chó Kamberra 2018, biểu tượng của sự trung thành và may mắn.',
     'Tiền con chó Kamberra mang màu sắc tươi sáng, phù hợp làm quà tặng phong thủy hoặc lì xì đầu năm mới.', 1),

    ('Tiền hình Chúa Jesus - Sản phẩm đặc biệt', 'Admin', '2024-12-13',
     'Tìm hiểu tiền hình Chúa Jesus của vùng Nagorno-Karabakh.',
     'Tiền hình Chúa Jesus với thiết kế đơn giản nhưng mang ý nghĩa sâu sắc, là lựa chọn hoàn hảo cho mùa Giáng sinh.', 1),

    ('Tiền con phụng Bhutan - Biểu tượng hòa hợp', 'Admin', '2024-12-13',
     'Khám phá ý nghĩa tiền con phụng Bhutan trong phong thủy.',
     'Tiền con phụng Bhutan mang đến thông điệp về hòa hợp âm dương, thích hợp làm quà tặng phong thủy.', 1),

    ('Tiền con rồng Bhutan - Sự mạnh mẽ quyền uy', 'Admin', '2024-12-13',
     'Tiền con rồng Bhutan - linh vật biểu tượng cho quyền uy và thịnh vượng.',
     'Tiền con rồng Bhutan thể hiện sức mạnh và sự bảo hộ của linh vật trong văn hóa Á Đông.', 1);

SET FOREIGN_KEY_CHECKS = 1;
