--CUSTOMER
insert into customer (customer_id, contract_date, locale, name, owner_name) values (default, '2021-10-01', '강남', '맘스터치', '김맘스');
insert into customer (customer_id, contract_date, locale, name, owner_name) values (default, '2021-11-01', '서초', '롯데리아', '박롯데');
insert into customer (customer_id, contract_date, locale, name, owner_name) values (default, '2021-11-05', '역삼', '베스킨라빈스', '베스킨');
insert into customer (customer_id, contract_date, locale, name, owner_name) values (default, '2021-11-03', '삼성', '본죽', '최본죽');

--COLLECT-HISTORY
insert into collect_history (collect_history_id, can_count, quantaty, collect_time, customer_id, image_count, notes) values (default, 4, 450, '2021-11-03 01:15:13', 1,  3, '새벽에 수거 해야함');
insert into collect_history (collect_history_id, can_count, quantaty, collect_time, customer_id, image_count, notes) values (default, 5, 900, '2021-11-03 04:15:13', 1,  3, '새벽에 수거 해야함');
insert into collect_history (collect_history_id, can_count, quantaty, collect_time, customer_id, image_count, notes) values (default, 8, 320, '2021-11-07 12:14:13', 1,  3, '새벽에 수거 해야함');
insert into collect_history (collect_history_id, can_count, quantaty, collect_time, customer_id, image_count, notes) values (default, 12, 520, '2022-01-03 01:15:13', 1, 3, '새벽에 수거 해야함');
insert into collect_history (collect_history_id, can_count, quantaty, collect_time, customer_id, image_count, notes) values (default, 20, 1000, '2022-05-03 01:15:13', 1, 3, '새벽에 수거 해야함');
insert into collect_history (collect_history_id, can_count, quantaty, collect_time, customer_id, image_count, notes) values (default, 1, 110, '2021-11-03 13:25:36', 2, 1, '없음');
insert into collect_history (collect_history_id, can_count, quantaty, collect_time, customer_id, image_count, notes) values (default, 3, 362, '2021-11-03 12:25:14', 3, 2, '하루 2번 수거');
insert into collect_history (collect_history_id, can_count, quantaty, collect_time, customer_id, image_count, notes) values (default, 6, 651, '2021-11-04 01:10:13', 4, 1, '매일 수거');

--COLLECT_IMAGE_META
insert into collect_image_meta (collect_image_meta_id, collect_history_id, create_time, image_format, modified_time, name) values (default, 1, '2021-11-03 01:15:13', 'JPG',  '2021-11-03 01:15:13', '맘스터치_01');
insert into collect_image_meta (collect_image_meta_id, collect_history_id, create_time, image_format, modified_time, name) values (default, 1, '2021-11-03 01:15:14', 'JPG',  '2021-11-03 01:15:13', '맘스터치_02');
insert into collect_image_meta (collect_image_meta_id, collect_history_id, create_time, image_format, modified_time, name) values (default, 1, '2021-11-03 01:15:15', 'JPG',  '2021-11-03 01:15:13', '맘스터치_03');
insert into collect_image_meta (collect_image_meta_id, collect_history_id, create_time, image_format, modified_time, name) values (default, 2, '2021-11-03 13:25:36', 'PNG',  '2021-11-03 01:15:13', '롯데리아_01');
insert into collect_image_meta (collect_image_meta_id, collect_history_id, create_time, image_format, modified_time, name) values (default, 3, '2021-11-03 12:25:14', 'JPEG', '2021-11-03 01:15:13', '베스킨라빈스_01');
insert into collect_image_meta (collect_image_meta_id, collect_history_id, create_time, image_format, modified_time, name) values (default, 3, '2021-11-03 12:25:20', 'JPEG', '2021-11-03 01:15:13', '베스킨라빈스_02');
insert into collect_image_meta (collect_image_meta_id, collect_history_id, create_time, image_format, modified_time, name) values (default, 4, '2021-11-04 01:10:13', 'JPG' , '2021-11-03 01:15:13', '본죽_01');